package com.example.shopberry.auth.dto.response;

import com.example.shopberry.user.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"user_id", "first_name", "last_name", "role", "access_token", "refresh_token"})
public class AuthenticationResponseDto {

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Role role;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

}
