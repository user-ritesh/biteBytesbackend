package com.ritesh.biteBytes.user.service;


import com.ritesh.biteBytes.user.dto.AuthRequestDto;
import com.ritesh.biteBytes.user.dto.AuthResponseDto;

public interface UserService {
    AuthResponseDto registerUser(AuthRequestDto requestDto);
    AuthResponseDto loginUser(AuthRequestDto requestDto);
}

