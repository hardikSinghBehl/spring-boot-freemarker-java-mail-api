package com.hardik.hedwig.mail.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hardik.hedwig.constant.EmailTemplate;
import com.hardik.hedwig.dto.UserCreationRequestDto;
import com.hardik.hedwig.mail.event.UserAccountCreationEvent;
import com.hardik.hedwig.mail.service.EmailService;
import com.hardik.hedwig.utility.MapUtility;

import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserAccountCreationListener {

	private final EmailService emailService;

	@EventListener
	@Async
	public void listenToUserAccountCreationEvent(UserAccountCreationEvent userAccountCreationEvent) {
		final var userCreationRequestDto = (UserCreationRequestDto) userAccountCreationEvent.getSource();
		try {
			emailService.sendEmail(userCreationRequestDto.getEmailId(),
					EmailTemplate.USER_ACCOUNT_CREATION.getSubject(), MapUtility.convert(userCreationRequestDto),
					EmailTemplate.USER_ACCOUNT_CREATION.getTemplateName());
		} catch (MessagingException | IOException | TemplateException exception) {
			log.error("UNABLE TO SEND USER ACCOUNT CREATON EMAIL: {}", userCreationRequestDto.getEmailId());
		}
	}

}