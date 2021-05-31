package com.hardik.hedwig.service;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hardik.hedwig.dto.UserCreationRequestDto;
import com.hardik.hedwig.entity.User;
import com.hardik.hedwig.exception.DuplicateEmailIdException;
import com.hardik.hedwig.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	private boolean userExists(final String emailId) {
		return userRepository.existsByEmailId(emailId);
	}

	public ResponseEntity<?> create(final UserCreationRequestDto userCreationRequestDto) {

		if (userExists(userCreationRequestDto.getEmailId()))
			throw new DuplicateEmailIdException();

		final var user = new User();
		final var response = new JSONObject();

		user.setEmailId(userCreationRequestDto.getEmailId());
		user.setFullName(userCreationRequestDto.getFullName());
		final var savedUser = userRepository.save(user);

		log.info("Account Created: {}", savedUser.getEmailId());

		response.put("message", "Account Created Successfully!, Kindly check your id: " + savedUser.getEmailId()
				+ " for a confirmation mail");
		response.put("timestamp", savedUser.getCreatedAt().toString());

		return ResponseEntity.ok(response.toString());
	}

}
