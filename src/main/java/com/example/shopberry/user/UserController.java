package com.example.shopberry.user;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.common.constants.messages.PasswordMessages;
import com.example.shopberry.user.dto.request.ChangePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/change-password")
    public ResponseEntity<MessageResponseDto> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto, Principal connectedUser) {
        userService.changePassword(changePasswordRequestDto, connectedUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PasswordMessages.PASSWORD_CHANGED_SUCCESSFULLY));
    }

}
