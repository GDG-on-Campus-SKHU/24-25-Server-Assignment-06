package com.example.sanghwajwt.user.dto;

import lombok.Getter;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Getter
public class JoinDto {
    private String username;
    private String password;
}
