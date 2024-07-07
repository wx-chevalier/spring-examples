/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.auth.biz.strategy.user;

import com.google.common.base.MoreObjects;
import com.taotao.cloud.auth.biz.authentication.login.oauth2.social.handler.SocialUserDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import me.zhyd.oauth.enums.AuthUserGender;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

/**
 * <p>社会化登录用户 </p>
 *
 */
@Schema(title = "社会化登录用户")
@Entity
@Table(
        name = "sys_social_user",
        indexes = {@Index(name = "sys_social_user_id_idx", columnList = "social_id")})
@Cacheable
// @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region =
// UpmsConstants.REGION_SYS_SOCIAL_USER)
public class SysSocialUser implements SocialUserDetails {

    @Schema(title = "社会用户ID")
    @Id
    @UuidGenerator
    @Column(name = "social_id", length = 64)
    private String socialId;

    /**
     * JustAuth中的关键词
     * 以下内容了解后，将会使你更容易地上手JustAuth。
     * <p>
     * source JustAuth支持的第三方平台，比如：GITHUB、GITEE等
     * uuid 一般为第三方平台的用户ID。以下几个平台需特别注意：
     * 钉钉、抖音：uuid 为用户的 unionid
     * 微信公众平台登录、京东、酷家乐、美团：uuid 为用户的 openId
     * 微信开放平台登录、QQ：uuid 为用户的 openId，平台支持获取unionid， unionid 在 AuthToken 中（如果支持），在登录完成后，可以通过 response.getData().getToken().getUnionId() 获取
     * Google：uuid 为用户的 sub，sub为Google的所有账户体系中用户唯一的身份标识符，详见：OpenID Connect (opens new window)
     * 注：建议通过uuid + source的方式唯一确定一个用户，这样可以解决用户身份归属的问题。因为 单个用户ID 在某一平台中是唯一的，但不能保证在所有平台中都是唯一的。
     */
    @Schema(title = "用户第三方系统的唯一id", description = "在调用方集成该组件时，可以用uuid + source唯一确定一个用")
    @Column(name = "uuid", length = 64)
    private String uuid;

    @Schema(title = "用户名")
    @Column(name = "user_name", length = 50)
    private String userName;

    @Schema(title = "用户昵称")
    @Column(name = "nick_name", length = 50)
    private String nickName;

    @Schema(title = "用户头像")
    @Column(name = "avatar", length = 1000)
    private String avatar;

    @Schema(title = "用户网址")
    @Column(name = "blog", length = 100)
    private String blog;

    @Schema(title = "所在公司")
    @Column(name = "company", length = 256)
    private String company;

    @Schema(title = "位置")
    @Column(name = "location", length = 512)
    private String location;

    @Schema(title = "用户邮箱")
    @Column(name = "email", length = 50)
    private String email;

    @Schema(title = "用户邮箱")
    @Column(name = "remark", length = 512)
    private String remark;
    /**
     * 性别
     */
    @Schema(title = "性别")
    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    private AuthUserGender gender;

    @Schema(title = "第三方用户来源")
    @Column(name = "source")
    private String source;

    @Schema(title = "用户的授权令牌")
    @Column(name = "access_token", columnDefinition = "TEXT")
    private String accessToken;

    @Schema(title = "第三方用户的授权令牌的有效期", description = "部分平台可能没有")
    @Column(name = "expire_in")
    private Integer expireIn;

    @Schema(title = "刷新令牌", description = "部分平台可能没有")
    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;

    @Schema(title = "第三方用户的刷新令牌的有效期", description = "部分平台可能没有")
    @Column(name = "refresh_token_expire_in")
    private Integer refreshTokenExpireIn;

    @Schema(title = "第三方用户授予的权限", description = "部分平台可能没有")
    @Column(name = "scope", length = 1200)
    private String scope;

    @Schema(title = "个别平台的授权信息", description = "部分平台可能没有")
    @Column(name = "token_type", length = 100)
    private String tokenType;

    @Schema(title = "第三方用户的 ID", description = "部分平台可能没有")
    @Column(name = "uid", length = 64)
    private String uid;

    @Schema(title = "第三方用户的 open id", description = "部分平台可能没有")
    @Column(name = "open_id", length = 64)
    private String openId;

    @Schema(title = "个别平台的授权信息", description = "部分平台可能没有")
    @Column(name = "access_code", length = 64)
    private String accessCode;

    @Schema(title = "第三方用户的 union id", description = "部分平台可能没有")
    @Column(name = "union_id", length = 64)
    private String unionId;

    @Schema(title = "小程序Appid", description = "部分平台可能没有")
    @Column(name = "app_id", length = 64)
    private String appId;

    @Schema(title = "手机号码", description = "部分平台可能没有")
    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    //	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region =
    // UpmsConstants.REGION_SYS_USER)
    @Schema(title = "系统用户")
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "sys_social_sys_user",
            joinColumns = {@JoinColumn(name = "social_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"social_id", "user_id"})},
            indexes = {
                @Index(name = "sys_social_sys_user_oid_idx", columnList = "social_id"),
                @Index(name = "sys_social_sys_user_uid_idx", columnList = "user_id")
            })
    private Set<SysUser> users = new HashSet<>();

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AuthUserGender getGender() {
        return gender;
    }

    public void setGender(AuthUserGender gender) {
        this.gender = gender;
    }

    @Override
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public Integer getRefreshTokenExpireIn() {
        return refreshTokenExpireIn;
    }

    public void setRefreshTokenExpireIn(Integer refreshTokenExpireIn) {
        this.refreshTokenExpireIn = refreshTokenExpireIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<SysUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SysUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("socialId", socialId)
                .add("uuid", uuid)
                .add("userName", userName)
                .add("nickName", nickName)
                .add("avatar", avatar)
                .add("blog", blog)
                .add("company", company)
                .add("location", location)
                .add("email", email)
                .add("remark", remark)
                .add("gender", gender)
                .add("source", source)
                .add("accessToken", accessToken)
                .add("expireIn", expireIn)
                .add("refreshToken", refreshToken)
                .add("refreshTokenExpireIn", refreshTokenExpireIn)
                .add("scope", scope)
                .add("tokenType", tokenType)
                .add("uid", uid)
                .add("openId", openId)
                .add("accessCode", accessCode)
                .add("unionId", unionId)
                .add("appId", appId)
                .add("users", users)
                .toString();
    }
}
