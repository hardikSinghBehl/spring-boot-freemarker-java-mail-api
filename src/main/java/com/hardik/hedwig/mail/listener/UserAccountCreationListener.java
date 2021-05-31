package com.hardik.hedwig.mail.listener;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

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
	public void listenToUserAccountCreationEvent(UserAccountCreationEvent userAccountCreationEvent) {
		final var userCreationRequestDto = (UserCreationRequestDto) userAccountCreationEvent.getSource();
		try {
			emailService.sendEmail(userCreationRequestDto.getEmailId(), "Account Created Successfully",
					MapUtility.convert(userCreationRequestDto), "account-creation-success");
		} catch (MessagingException | IOException | TemplateException exception) {
			log.error("UNABLE TO SEND USER ACCOUNT CREATON EMAIL: {}", userCreationRequestDto.getEmailId());
		}
	}

}