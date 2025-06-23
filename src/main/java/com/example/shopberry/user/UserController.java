package com.example.shopberry.user;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.common.constants.messages.PasswordMessages;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.dto.request.ChangePasswordRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Change password (only when logged in)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Password changed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to change password",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @PatchMapping("/change-password")
    public ResponseEntity<MessageResponseDto> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto, Principal connectedUser) {
        userService.changePassword(changePasswordRequestDto, connectedUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto(PasswordMessages.PASSWORD_CHANGED_SUCCESSFULLY));
    }

}
