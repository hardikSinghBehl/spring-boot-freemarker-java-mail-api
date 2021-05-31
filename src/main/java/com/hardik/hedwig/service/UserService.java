package com.hardik.hedwig.service;

import org.json.JSONObject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.hedwig.constant.ApiConstant;
import com.hardik.hedwig.dto.UserCreationRequestDto;
import com.hardik.hedwig.entity.User;
import com.hardik.hedwig.exception.DuplicateEmailIdException;
import com.hardik.hedwig.mail.event.UserAccountCreationEvent;
import com.hardik.hedwig.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	private boolean userExists(final String emailId) {
		return userRepository.existsByEmailId(emailId);
	}

	public ResponseEntity<?> create(final UserCreationRequestDto userCreationRequestDto) {

		if (userExists(userCreationRequestDto.getEmailId()))
			throw new DuplicateEmailIdException();

		final var user = new User();
		final var response = new JSONObject();

		// Create User Account
		user.setEmailId(userCreationRequestDto.getEmailId());
		user.setFullName(userCreationRequestDto.getFullName());
		final var savedUser = userRepository.save(user);

		log.info("Account Created: {}", savedUser.getEmailId());

		// Publish User Account Creation Event Occurrence
		applicationEventPublisher.publishEvent(new UserAccountCreationEvent(userCreationRequestDto));

		response.put(ApiConstant.MESSAGE,
				ApiConstant.ACCOUNT_CREATION_SUCCESS_RESPONSE.replace("{}", savedUser.getEmailId()));
		response.put(ApiConstant.TIMESTAMP, savedUser.getCreatedAt().toString());
		return ResponseEntity.ok(response.toString());
	}

}
