package com.fc.v2.common.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author fuce
 */
@Component
@ConfigurationProperties(prefix = "fuce")
public class V2Config
{
    /** 项目名称 */
    private String name;
    /** 版本 */
    private String version;
    /** 版权年份 */
    private String copyrightYear;
    /** 邮箱发送smtp */
    private String emailSmtp;
    /** 发送邮箱端口 */
    private String emailPort;
    /** 发送邮箱登录账号 */
    private String emailAccount;
    /** 发送邮箱登录密码 */
    private String emailPassword;
    /** 演示模式 **/
    private String demoEnabled;
    /** 滚动验证码 **/
    private Boolean rollVerification;
    /** 文件路径 **/
    private String profile;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCopyrightYear() {
		return copyrightYear;
	}
	public void setCopyrightYear(String copyrightYear) {
		this.copyrightYear = copyrightYear;
	}
	public String getEmailSmtp() {
		return emailSmtp;
	}
	public void setEmailSmtp(String emailSmtp) {
		this.emailSmtp = emailSmtp;
	}
	public String getEmailPort() {
		return emailPort;
	}
	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}
	public String getEmailAccount() {
		return emailAccount;
	}
	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}
	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}
	public String getDemoEnabled() {
		return demoEnabled;
	}
	public void setDemoEnabled(String demoEnabled) {
		this.demoEnabled = demoEnabled;
	}
	public Boolean getRollVerification() {
		return rollVerification;
	}
	public void setRollVerification(Boolean rollVerification) {
		this.rollVerification = rollVerification;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * 获取头像上传路径
	 */
	public String getAvatarPath()
	{
		return this.profile + "/avatar";
	}

	/**
	 * 获取下载路径
	 */
	public String getDownloadPath()
	{
		return this.profile + "/download/";
	}

	/**
	 * 获取上传路径
	 */
	public String getUploadPath()
	{
		return this.profile+ "/upload";
	}
}
