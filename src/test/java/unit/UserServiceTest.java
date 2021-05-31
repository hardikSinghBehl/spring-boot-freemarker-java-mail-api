package unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hardik.hedwig.SpringBootFreemarkerJavaMailApiApplication;
import com.hardik.hedwig.constant.ApiConstant;
import com.hardik.hedwig.dto.UserCreationRequestDto;
import com.hardik.hedwig.entity.User;
import com.hardik.hedwig.exception.DuplicateEmailIdException;
import com.hardik.hedwig.mail.event.UserAccountCreationEvent;
import com.hardik.hedwig.mail.listener.UserAccountCreationListener;
import com.hardik.hedwig.repository.UserRepository;
import com.hardik.hedwig.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootFreemarkerJavaMailApiApplication.class)
public class UserServiceTest {

	private UserService userService;
	private UserRepository userRepository;

	@MockBean
	private UserAccountCreationListener userAccountCreationListener;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private static final String EMAIL_ID = "something@gmail.com";
	private static final String FULL_NAME = "something";

	@BeforeEach
	void setUp() {
		this.userRepository = mock(UserRepository.class);
		this.userService = new UserService(userRepository, applicationEventPublisher);
	}

	@Test
	@DisplayName("Create user account service is called with valid inputs")
	void create_providingValidInputs_success() {
		// PREPARE
		final var date = LocalDateTime.of(1999, 12, 23, 04, 40);
		final var userCreationRequestDto = mock(UserCreationRequestDto.class);
		final var user = mock(User.class);
		when(userCreationRequestDto.getEmailId()).thenReturn(EMAIL_ID);
		when(userCreationRequestDto.getFullName()).thenReturn(FULL_NAME);
		when(userRepository.existsByEmailId(EMAIL_ID)).thenReturn(false);
		when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		when(user.getCreatedAt()).thenReturn(date);
		when(user.getEmailId()).thenReturn(EMAIL_ID);

		// CALL
		final var response = userService.create(userCreationRequestDto);

		// VERIFY
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertThat(response.getBody().toString())
				.contains(ApiConstant.ACCOUNT_CREATION_SUCCESS_RESPONSE.replace("{}", EMAIL_ID));
		assertThat(response.getBody().toString()).contains(date.toString());
		verify(userCreationRequestDto, times(2)).getEmailId();
		verify(userCreationRequestDto).getFullName();
		verify(userAccountCreationListener)
				.listenToUserAccountCreationEvent(Mockito.any(UserAccountCreationEvent.class));
	}

	@Test
	@DisplayName("Create user account service is called with duplicate email-id")
	void create_providingDuplicateEmailId_forbidden() {
		// PREPARE
		final var userCreationRequestDto = mock(UserCreationRequestDto.class);
		when(userCreationRequestDto.getEmailId()).thenReturn(EMAIL_ID);
		when(userRepository.existsByEmailId(EMAIL_ID)).thenReturn(true);

		// CALL
		assertThrows(DuplicateEmailIdException.class, () -> userService.create(userCreationRequestDto));
	}

}
