package wx.application.infra.notice.sms;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import wx.common.data.code.ApiErrorCode;
import wx.common.data.infra.message.MessageTemplate;
import wx.common.data.infra.notice.NoticeSendChannel;
import wx.common.data.infra.notice.NoticeType;
import wx.common.data.mq.MessageQueueName;
import wx.common.data.mq.notice.SendUserMessage;
import wx.common.data.shared.id.UserId;
import wx.domain.account.User;
import wx.domain.account.UserCommandService;
import wx.domain.account.UserRepository;
import wx.domain.infra.message.MessageType;
import wx.domain.infra.message.MessageTypeRepository;
import wx.domain.infra.verificationcode.VerificationCodeCommandService;
import wx.infra.common.exception.NotAcceptException;
import wx.infra.common.exception.NotFoundException;
import wx.infra.common.util.RandomUtils;
import wx.infra.tunnel.db.infra.verificationcode.VerificationCodeDO;
import wx.infra.tunnel.db.infra.verificationcode.VerificationCodeTunnel;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

  @Getter private MessageTypeRepository messageTypeRepository;

  @Getter private UserRepository userRepository;

  private AmqpTemplate amqpTemplate;

  private VerificationCodeCommandService verificationCodeCommandService;

  private UserCommandService userCommandService;

  private wx.infra.service.sms.SmsService smsService;

  private VerificationCodeTunnel verificationCodeTunnel;

  public SmsServiceImpl(
      MessageTypeRepository messageTypeRepository,
      UserRepository userRepository,
      AmqpTemplate amqpTemplate,
      VerificationCodeCommandService verificationCodeCommandService,
      UserCommandService userCommandService,
      @Qualifier("smsService") wx.infra.service.sms.SmsService smsService,
      VerificationCodeTunnel verificationCodeTunnel) {
    this.messageTypeRepository = messageTypeRepository;
    this.userRepository = userRepository;
    this.amqpTemplate = amqpTemplate;
    this.smsService = smsService;
    this.verificationCodeCommandService = verificationCodeCommandService;
    this.userCommandService = userCommandService;
    this.verificationCodeTunnel = verificationCodeTunnel;
  }

  @Override
  public void send(String dst, String subject, NoticeType noticeType, Map<String, String> param) {

    // ??????????????????
    String smsTemplateId =
        Optional.ofNullable(getMessageTypeById(noticeType))
            .map(MessageType::getTemplate)
            .map(MessageTemplate::getSmsTemplateId)
            .orElse(null);

    // ???????????????
    smsService.send(dst, smsTemplateId, param);
  }

  @Override
  public void sendLoginCode(String phoneNumber) {
    // ??????????????????????????????
    int sendCount =
        verificationCodeCommandService.getSendCount(
            60, phoneNumber, NoticeType.SMS_LOGIN, NoticeSendChannel.SMS);
    if (sendCount != 0) {
      throw new NotAcceptException("??????????????????????????????????????????");
    }

    log.info("????????????????????????:{}", phoneNumber);
    // ??????????????????
    Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
    if (!userOptional.isPresent()) {
      throw new NotFoundException("????????????, ???????????????????????????");
    }

    User user = userOptional.get();
    String code = RandomUtils.randomCode(6);

    Map<String, String> param = new HashMap<>();
    param.put("code", code);

    SendUserMessage msg = new SendUserMessage();
    msg.setUserId(user.getId())
        .setNoticeType(NoticeType.SMS_LOGIN)
        .setChannel(NoticeSendChannel.SMS)
        .setParam(param);
    amqpTemplate.convertAndSend(MessageQueueName.SEND_MESSAGE, msg);

    // ????????????????????????
    VerificationCodeDO entity = new VerificationCodeDO();
    entity
        .setCode(code)
        .setSendDst(phoneNumber)
        .setChannel(NoticeSendChannel.SMS)
        .setSentAt(LocalDateTime.now())
        .setType(NoticeType.SMS_LOGIN)
        .setExpireAt(LocalDateTime.now().plusMinutes(SMS_EFFECTIVE_TIME));
    verificationCodeTunnel.save(entity);
    log.info("???????????????????????????,code = {}", code);
  }

  @Override
  public String sentBindNotice(UserId userId, String target, String password) {
    // ???????????????????????????????????????
    Optional<User> userOptional = userRepository.findByUsername(target);
    if (userOptional.isPresent()) {
      throw new NotAcceptException("??????????????????????????????????????????");
    }

    // ??????????????????????????????
    int sendCount =
        verificationCodeCommandService.getSendCount(
            60, target, NoticeType.BIND_PHONE, NoticeSendChannel.SMS);
    if (sendCount != 0) {
      throw new NotAcceptException("??????????????????????????????????????????", ApiErrorCode.SEND_RATE_FAST);
    }

    Boolean passwordRight = userCommandService.checkPassword(userId, password);
    if (!passwordRight) {
      throw new NotAcceptException("????????????, ????????????????????????", ApiErrorCode.PASS_NOT_MATCH);
    }

    // ??????6????????????
    String code = RandomUtils.randomCode(6);

    Map<String, String> param = new HashMap<>();
    param.put("code", code);

    SendUserMessage msg = new SendUserMessage();
    msg.setNoticeType(NoticeType.BIND_PHONE)
        .setDest(target)
        .setUserId(userId)
        .setChannel(NoticeSendChannel.SMS)
        .setParam(param);
    amqpTemplate.convertAndSend(MessageQueueName.SEND_MESSAGE, msg);

    // ???????????????????????? & ????????????????????????
    verificationCodeTunnel.cleanUnused(userId, NoticeType.BIND_PHONE, NoticeSendChannel.SMS);
    VerificationCodeDO entity = new VerificationCodeDO();
    entity
        .setCode(code)
        .setChannel(NoticeSendChannel.SMS)
        .setSentAt(LocalDateTime.now())
        .setType(NoticeType.BIND_PHONE)
        .setSendDst(target)
        .setExpireAt(LocalDateTime.now().plusMinutes(SMS_EFFECTIVE_TIME))
        .setUserId(userId.getId());
    verificationCodeTunnel.save(entity);
    return code;
  }
}
