package com.example.shopberry.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AuthenticationRequestDto {

    private String email;

    private String password;

}
