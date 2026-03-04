package user.service;


import user.dto.AuthRequestDto;
import user.dto.AuthResponseDto;

public interface UserService {
    AuthResponseDto registerUser(AuthRequestDto requestDto);
    AuthResponseDto loginUser(AuthRequestDto requestDto);
}

