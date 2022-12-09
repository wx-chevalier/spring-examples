package com.cloud.user.controller;

import com.cloud.constants.SmsSendResponse;
import com.cloud.model.user.SmsVerify;
import com.cloud.user.service.ISmsVerifyService;
import com.cloud.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 验证码发送记录 前端控制器
 * </p>
 *
 * @author liugh123
 */
@RestController
@RequestMapping("/smsVerify")
@Api(description = "短信模块")
public class SmsVerifyController {

    @Autowired
    private ISmsVerifyService smsVerifyService;

    @ApiOperation(value = "获取验证码接口", notes = "路径参数,不需要Authorization</br>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsType", value = "AUTH/登录验证、REG/注册验证、FINDPASSWORD/修改密码、MODIFYINFO/修改账号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping
    public ResultHelper getCaptcha(@RequestParam String smsType, @RequestParam String mobile) throws Exception {

        if (!PhoneUtil.checkPhone(mobile)) {
            return ResultHelper.failed2Msg("手机号格式有误！");
        }
        String randNum = GenerationSequenceUtil.getRandNum(4);
        SmsSendResponse smsSendResponse = SmsSendUtil.sendMessage(mobile,
                "校验码: " + randNum + "。您正在进行" + SmsSendUtil.SMSType.getSMSType(smsType).toString() + "的操作,请在5分钟内完成验证，注意保密哦！");
        SmsVerify smsVerify = new SmsVerify(smsSendResponse.getMsgId()
                , mobile, randNum, SmsSendUtil.SMSType.getType(smsType), System.currentTimeMillis());
        smsVerifyService.insert(smsVerify);
        return ResultHelper.succeed(null);
    }


    @ApiOperation(value = "验证码验证接口", notes = "请求参数,不需要Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "smsType", value = "验证码类型"
                    , required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号"
                    , required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "captcha", value = "验证码"
                    , required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/captcha/check")
    public ResultHelper captchaCheck(@RequestParam String smsType,
                                     @RequestParam String mobile, @RequestParam String captcha) throws Exception {
        if (!PhoneUtil.checkPhone(mobile)) {
            return ResultHelper.failed2Msg("手机号格式有误！");
        }

        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(mobile,
                captcha, SmsSendUtil.SMSType.getType(smsType));
        if (ComUtil.isEmpty(smsVerifies)) {
            return ResultHelper.failed2Msg("验证码失效！");
        }
        if (SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())) {
            return ResultHelper.failed2Msg("验证码失效！");
        }
        return ResultHelper.succeed(null);
    }
}

