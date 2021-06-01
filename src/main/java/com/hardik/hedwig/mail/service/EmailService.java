package com.hardik.hedwig.mail.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.hardik.hedwig.configuration.properties.EmailConfigurationProperties;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(EmailConfigurationProperties.class)
public class EmailService {

	private final JavaMailSender javaMailSender;

	private final Configuration freemarkerConfiguration;

	private final EmailConfigurationProperties emailConfigurationProperties;

	public void sendEmail(String toMail, String subject, Map<String, String> model, String templateName)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException, MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		Template template = freemarkerConfiguration.getTemplate(templateName + ".ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
		helper.setTo(toMail);
		helper.setText(html, true);
		helper.setSubject(subject);
		helper.setFrom(emailConfigurationProperties.getUsername());
		javaMailSender.send(message);
	}

}