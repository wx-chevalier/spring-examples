package wx.api.rest.common.verificationcode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wx.api.rest.shared.BaseController;
import wx.api.rest.shared.dto.envelope.ResponseEnvelope;
import wx.common.data.infra.notice.NoticeSendChannel;
import wx.infra.common.exception.DataValidationException;

@RestController
@Api(tags = "๐ ้ช่ฏ็ ็ธๅณๆฅๅฃ")
public class VerificationCodeController extends BaseController {

  @PostMapping("/noauth/verification_code")
  @ApiOperation(value = "ๅ้็ป้็ป้้ช่ฏ็ ่ฏทๆฑ")
  public ResponseEnvelope<Void> requestVerificationCode(
      @Valid @RequestBody VerificationCodeRequest request) {
    if (request.getChannel() == NoticeSendChannel.SMS) {
      smsService.sendLoginCode(request.getSendDst());
    } else {
      throw new DataValidationException("ๆไฝๅคฑ่ดฅ๏ผๆไธๆฏๆ็้ช่ฏ็ ๆธ ้");
    }
    return ResponseEnvelope.createOk();
  }

  @PostMapping("/bind_phone/verification_code")
  @ApiOperation(value = "ๅ้็ปๅฎๆๆบๅท็้ช่ฏ็ ")
  public ResponseEnvelope<Void> sendBoundPhoneVerificationCode(
      @Valid @RequestBody VerificationCodeRequest request) {
    smsService.sentBindNotice(getCurrentUserId(), request.getSendDst(), request.getPassword());
    return ResponseEnvelope.createOk();
  }

  @PostMapping("/bind_email/verification_code")
  @ApiOperation(value = "ๅ้็ปๅฎ้ฎ็ฎฑ็็ตๅญ้ฎไปถ")
  public ResponseEnvelope<Void> sendBoundEmailVerificationCode(
      @Valid @RequestBody VerificationCodeRequest request) {
    emailService.sentBindNotice(getCurrentUserId(), request.getSendDst(), request.getPassword());
    return ResponseEnvelope.createOk();
  }

  @PostMapping("/noauth/bind_email")
  @ApiOperation(value = "้ช่ฏ็ปๅฎ้ฎ็ฎฑ็้พๆฅ")
  public ResponseEnvelope<Void> verificationEmailLink(String code) {
    userCommandService.bindEmailAddress(code);
    return ResponseEnvelope.createOk();
  }
}
