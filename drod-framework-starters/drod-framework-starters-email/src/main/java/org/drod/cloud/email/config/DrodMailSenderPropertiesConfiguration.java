package org.drod.cloud.email.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "e9.mail", name = "host")
class DrodMailSenderPropertiesConfiguration {

	@Bean
	@ConditionalOnMissingBean(JavaMailSender.class)
	JavaMailSenderImpl mailSender(DrodMailProperties properties) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		applyProperties(properties, sender);
		return sender;
	}

	private void applyProperties(DrodMailProperties properties, JavaMailSenderImpl sender) {
		sender.setHost(properties.getHost());
		if (properties.getPort() != null) {
			sender.setPort(properties.getPort());
		}
		sender.setUsername(properties.getUsername());
		sender.setPassword(properties.getPassword());
		sender.setProtocol(properties.getProtocol());
		if (properties.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(properties.getDefaultEncoding().name());
		}
	}

}
