package com.example.shopberry.user;

import com.example.shopberry.common.constants.messages.PasswordMessages;
import com.example.shopberry.user.dto.ChangePasswordRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto, Principal connectedUser) throws IllegalArgumentException {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        String currentPassword = changePasswordRequestDto.getCurrentPassword();
        String newPassword = changePasswordRequestDto.getNewPassword();
        String confirmationPassword = changePasswordRequestDto.getConfirmationPassword();

        if (currentPassword == null || newPassword == null || confirmationPassword == null) {
            throw new IllegalArgumentException(PasswordMessages.SOME_FIELDS_ARE_NULL);
        }

        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException(PasswordMessages.WRONG_PASSWORD);
        }

        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getConfirmationPassword())) {
            throw new IllegalArgumentException(PasswordMessages.PASSWORDS_DONT_MATCH);
        }

        if (changePasswordRequestDto.getNewPassword().isBlank()) {
            throw new IllegalArgumentException(PasswordMessages.PASSWORD_CANT_BE_BLANK);
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));

        userRepository.save(user);
    }

}
