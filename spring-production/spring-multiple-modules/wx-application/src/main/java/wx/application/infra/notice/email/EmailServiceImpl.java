package wx.application.infra.notice.email;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wx.common.data.code.ApiErrorCode;
import wx.common.data.infra.notice.NoticeSendChannel;
import wx.common.data.infra.notice.NoticeType;
import wx.common.data.shared.id.UserId;
import wx.domain.account.User;
import wx.domain.account.UserCommandService;
import wx.domain.account.UserRepository;
import wx.domain.infra.message.MessageNoticeRepository;
import wx.domain.infra.message.MessageType;
import wx.domain.infra.message.MessageTypeRepository;
import wx.domain.infra.verificationcode.VerificationCodeCommandService;
import wx.infra.common.exception.NotAcceptException;
import wx.infra.common.util.RandomUtils;
import wx.infra.common.util.StringUtil;
import wx.infra.tunnel.db.infra.verificationcode.VerificationCodeDO;
import wx.infra.tunnel.db.infra.verificationcode.VerificationCodeTunnel;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  private wx.infra.service.email.EmailService emailService;

  private UserCommandService userCommandService;

  private VerificationCodeCommandService verificationCodeCommandService;

  private VerificationCodeTunnel verificationCodeTunnel;

  private MessageNoticeRepository messageNoticeRepository;

  @Getter private UserRepository userRepository;

  @Getter private MessageTypeRepository messageTypeRepository;

  public EmailServiceImpl(
      @Qualifier("emailService") wx.infra.service.email.EmailService emailService,
      VerificationCodeTunnel verificationCodeTunnel,
      UserCommandService userCommandService,
      UserRepository userRepository,
      VerificationCodeCommandService verificationCodeCommandService,
      MessageTypeRepository messageTypeRepository) {
    this.emailService = emailService;
    this.userRepository = userRepository;
    this.verificationCodeCommandService = verificationCodeCommandService;
    this.userCommandService = userCommandService;
    this.verificationCodeTunnel = verificationCodeTunnel;
    this.messageTypeRepository = messageTypeRepository;
  }

  @Override
  public void send(
      String emailDst, String subject, NoticeType noticeType, Map<String, String> param) {

    // ??????????????????
    MessageType messageType = getMessageTypeById(noticeType);

    // ????????????????????????
    String emailContent =
        Optional.ofNullable(messageType.getTemplate().getEmailTemplate())
            .map(template -> StringUtil.replaceAll(template, param))
            .orElse(null);
    // ??????
    emailService.send(emailDst, subject, emailContent);
  }

  @Override
  public void send(String emailDst, String subject, String content) {
    emailService.send(emailDst, subject, content);
  }

  @Override
  @Transactional
  public String sentBindNotice(UserId userId, String targetEmail, String password) {
    // ??????????????????????????????
    int sendCount =
        verificationCodeCommandService.getSendCount(
            60, targetEmail, NoticeType.BIND_EMAIL, NoticeSendChannel.EMAIL);
    if (sendCount != 0) {
      throw new NotAcceptException("??????????????????????????????????????????", ApiErrorCode.SEND_RATE_FAST);
    }

    Boolean passwordRight = userCommandService.checkPassword(userId, password);
    if (!passwordRight) {
      throw new NotAcceptException("????????????, ????????????", ApiErrorCode.PASS_NOT_MATCH);
    }

    User currentUser = getUserById(userId);
    // TODO ??????????????????
    String linkCode = String.format("validate_%s_%s", userId.getId(), UUID.randomUUID());

    MessageType messageType = getMessageTypeById(NoticeType.BIND_EMAIL);

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("nickName", currentUser.getNickname());
    paramMap.put("link", linkCode);

    String emailTemplate =
        Optional.ofNullable(messageType.getTemplate().getEmailTemplate())
            .map(template -> StringUtil.replaceAll(template, paramMap))
            .orElse(null);

    send(targetEmail, NoticeType.BIND_EMAIL.getDesc(), emailTemplate);

    // ???????????????????????? & ????????????????????????
    verificationCodeTunnel.cleanUnused(userId, NoticeType.BIND_EMAIL, NoticeSendChannel.EMAIL);
    verificationCodeTunnel.save(
        new VerificationCodeDO()
            .setCode(linkCode)
            .setChannel(NoticeSendChannel.EMAIL)
            .setSentAt(LocalDateTime.now())
            .setType(NoticeType.BIND_EMAIL)
            .setExpireAt(LocalDateTime.now().plusMinutes(EMAIL_EFFECTIVE_TIME))
            .setSendDst(targetEmail)
            .setUserId(userId.getId()));
    return linkCode;
  }

  @Override
  public void sendLoginCode(String emailDst) {

    // ????????????60s ?????????????????????????????????
    int sendCount =
        verificationCodeTunnel.getSendCount(
            60, NoticeType.SMS_LOGIN, NoticeSendChannel.SMS, emailDst);
    if (sendCount > 0) {
      log.info("????????????????????????????????????????????????{}?????????", emailDst);
      throw new NotAcceptException("????????????????????????????????????");
    }

    // ??????????????????,???????????????
    MessageType messageType = getMessageTypeById(NoticeType.EMAIL_LOGIN);

    String code = RandomUtils.randomCode(6);
    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("code", code);

    String emailTemplate =
        Optional.ofNullable(messageType.getTemplate().getEmailTemplate())
            .map(template -> StringUtil.replaceAll(template, paramMap))
            .orElse(null);
    emailService.send(emailDst, NoticeType.EMAIL_LOGIN.getDesc(), emailTemplate);

    // ???????????????,??????????????????

    verificationCodeTunnel.save(
        new VerificationCodeDO()
            .setCode(code)
            .setSendDst(emailDst)
            .setChannel(NoticeSendChannel.EMAIL)
            .setSentAt(LocalDateTime.now())
            .setType(NoticeType.BIND_EMAIL)
            .setExpireAt(LocalDateTime.now().plusMinutes(EMAIL_EFFECTIVE_TIME))
            .setSendDst(emailDst));
  }
}
