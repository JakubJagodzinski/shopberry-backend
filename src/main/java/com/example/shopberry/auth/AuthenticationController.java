package com.example.shopberry.auth;

import com.example.shopberry.auth.dto.*;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.common.constants.messages.UserMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        service.register(registerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(UserMessages.USER_CREATED_SUCCESSFULLY));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto authenticationRequestDto) throws AccessDeniedException {
        AuthenticationResponseDto authenticationResponseDto = service.authenticate(authenticationRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationResponseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshTokenResponseDto refreshTokenResponseDto = service.refreshToken(refreshTokenRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(refreshTokenResponseDto);
    }

}
