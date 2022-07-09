package org.drod.cloud.email.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "drod.mail")
public class DrodMailProperties {

	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	/**
	 * SMTP 服务器. 例如qq邮箱： 'smtp.qq.com'.
	 */
	private String host;

	/**
	 * SMTP 服务端口，qq邮箱：465
	 */
	private Integer port;

	/**
	 * 登录 SMTP server用户名
	 */
	private String username;

	/**
	 * 登录 SMTP server密码
	 */
	private String password;

	/**
	 * 发送人邮箱
	 */
	private String from;

	/**
	 * SMTP 服务器使用的协议，smtp、smtps(更安全)
	 */
	private String protocol = "smtp";

	/**
	 * 默认消息编码
	 */
	private Charset defaultEncoding = DEFAULT_CHARSET;

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Charset getDefaultEncoding() {
		return this.defaultEncoding;
	}

	public void setDefaultEncoding(Charset defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

}
