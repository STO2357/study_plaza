package sto.study_plaza.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CustomUserDetails {
    private final UUID memberId;
    private final String username;
}
