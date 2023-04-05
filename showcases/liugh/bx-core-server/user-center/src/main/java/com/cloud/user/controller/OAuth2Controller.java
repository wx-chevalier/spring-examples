package com.cloud.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.constants.Constant;
import com.cloud.utils.ComUtil;
import com.cloud.utils.ResultHelper;
import com.cloud.utils.SmsSendUtil;
import com.cloud.model.user.AppUser;
import com.cloud.model.user.LoginModel;
import com.cloud.model.user.SmsVerify;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.model.user.constants.RoleType;
import com.cloud.user.service.AppUserService;
import com.cloud.user.service.ISmsVerifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@Api(tags = "认证登录模块")
public class OAuth2Controller {

    /**
     * 当前登陆用户信息<br>
     * <p>
     * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()<br>
     * 返回值是接口org.springframework.security.core.Authentication，又继承了Principal<br>
     * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication<br>
     * <p>
     * 因此这只是一种写法，下面注释掉的三个方法也都一样，这四个方法任选其一即可，也只能选一个，毕竟uri相同，否则启动报错<br>
     *
     * @return
     */
    @GetMapping("/user-me")
    @ApiIgnore
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }



    @Autowired
    private  RestTemplate restTemplate;

    @ApiOperation(value = "用户名密码获取token,刷新token")
    @PostMapping("/oauth/user/token")
    public ResultHelper getUserTokenInfo(@RequestBody LoginModel loginModel) throws Exception {
        if( ComUtil.isEmpty(loginModel.getClientId()) || ComUtil.isEmpty(loginModel.getClientSecret())){
            throw new IllegalArgumentException("参数校验失败");
        }
        return getTokenResult(loginModel);

    }


    /**
     * 创建验证码
     *
     * @throws Exception
     */
    @ApiIgnore
    @GetMapping("/validata/code/{deviceId}")
    public void createCode(@PathVariable String deviceId, HttpServletResponse response) throws Exception {
        Assert.notNull(deviceId, "机器码不能为空");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
    }


    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * 注销登陆/退出
     * 移除access_token和refresh_token<br>
     * @param access_token
     */
    @ApiOperation(value = "注销登陆/退出,移除access_token和refresh_token")
    @DeleteMapping(value = "/remove_token", params = "access_token")
    public ResultHelper removeToken( @ApiParam(required = true, name = "access_token", value = "access_token")String access_token) {
        boolean flag = tokenServices.revokeToken(access_token);
        if (flag) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return ResultHelper.succeed(null);
//            saveLogoutLog(authentication.getName());
        }else {
            return ResultHelper.failed2Msg("注销失败");
        }
    }

//    @Autowired
//    private LogClient logClient;
//
//    /**
//     * 退出日志
//     *
//     * @param username
//     */
//    private void saveLogoutLog(String username) {
//        log.info("{}退出", username);
//        // 异步
//        CompletableFuture.runAsync(() -> {
//            try {
//                Log log = Log.builder().username(username).module(LogModule.LOGOUT).createTime(new Date()).build();
//                logClient.save(log);
//            } catch (Exception e) {
//                // do nothing
//            }
//
//        });
//    }

    private ResultHelper getTokenResult(LoginModel loginModel) {
        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //提交参数设置
        MultiValueMap<String, String> p = new LinkedMultiValueMap<>();
        String grantType = loginModel.getGrantType();
        p.add("client_secret",loginModel.getClientSecret());
        p.add("client_id",loginModel.getClientId());
        if(!ComUtil.isEmpty(loginModel.getRefreshToken()) &&  grantType.equals("refresh_token")){
            p.add("grant_type","refresh_token");
            p.add("refresh_token",loginModel.getRefreshToken());
        }else {
            p.add("username", loginModel.getUsername());

            String[] params = loginModel.getUsername().split("\\|");
            String username = params[0];// 真正的用户名或手机号

            if (params.length == 1 || (CredentialType.USERNAME == CredentialType.valueOf(params[1]))) {
                p.add("password", loginModel.getPassword());
            } else {
                // 登录类型
                CredentialType credentialType = CredentialType.valueOf(params[1]);
                if (CredentialType.PHONE == credentialType) {// 短信登录
                    String check = checkPhoneCaptcha(username, loginModel.getPassword());
                    if(!ComUtil.isEmpty(check)) {
                        return ResultHelper.failed2Msg(check);
                    }
                    p.add("password", username);
                }else if (CredentialType.THIRD == credentialType) {//第三方登录
                    p.add("password", loginModel.getPassword());
                }
            }

        }
        p.add("grant_type", !ComUtil.isEmpty(grantType)?grantType:"password");
        p.add("scope",!ComUtil.isEmpty(loginModel.getScope())?loginModel.getScope():"app");
        //提交请求
        try {
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(p, headers);
            ResponseEntity<String> exchange = restTemplate.exchange(URI.create("http://127.0.0.1:8000/oauth/token"),
                    HttpMethod.POST, entity, String.class);
            if (exchange.getStatusCodeValue() == HttpStatus.OK.value()) {
                return ResultHelper.succeed(JSONObject.parse(exchange.getBody()));
            }
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return ResultHelper.failed2Msg("用户名或密码错误");
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResultHelper.failed2Msg("用户名密码错误");
        }
        return ResultHelper.succeed(null);
    }

    @Autowired
    private ISmsVerifyService smsVerifyService;
    @Autowired
    private AppUserService appUserService;
    private String checkPhoneCaptcha(String phone, String captcha) {

        if (ComUtil.isEmpty(appUserService.findByPhone(phone))) {
            AppUser appUser = new AppUser();
            appUser.setPassword(Constant.PASSWORD);
            appUser.setRePassword(Constant.PASSWORD);
            appUser.setPhone(phone);
            appUser.setUsername("gfc"+phone);
            appUser.setRole(RoleType.USER.name());
            appUser.setType("4");
            try {
                appUserService.addAppUser(appUser);
            } catch (Exception e) {
                return "验证码错误！";
            }
        }

        List<SmsVerify> smsVerifies = smsVerifyService.getByMobileAndCaptchaAndType(phone,
                captcha, SmsSendUtil.SMSType.getType("AUTH"));
        if (ComUtil.isEmpty(smsVerifies)) {
            return "验证码错误！";
        } else if (SmsSendUtil.isCaptchaPassTime(smsVerifies.get(0).getCreateTime())) {
            return "验证码失效！";
        }
        return null;
    }

}
