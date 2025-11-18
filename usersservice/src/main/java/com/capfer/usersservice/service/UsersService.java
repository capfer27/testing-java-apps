package com.capfer.usersservice.service;

import com.capfer.usersservice.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getUsers(int page, int limit);
    UserDto getUser(String email);
}
