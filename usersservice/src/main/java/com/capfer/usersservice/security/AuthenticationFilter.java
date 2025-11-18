package com.capfer.usersservice.security;

import com.capfer.usersservice.service.UsersService;
import com.capfer.usersservice.shared.SpringApplicationContext;
import com.capfer.usersservice.shared.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        // 1. Initialize ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            byte[] inputStreamBytes = StreamUtils.copyToByteArray(req.getInputStream());
//            Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);
            Map<String, String> jsonRequest = objectMapper.readValue(inputStreamBytes, new TypeReference<>(){});

//            UserLoginRequestModel creds = new ObjectMapper()
//                    .readValue(jsonRequest.get("body"), UserLoginRequestModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jsonRequest.get("email"),
                            jsonRequest.get("password"),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((UserDetails) auth.getPrincipal()).getUsername();

        byte[] secretKeyBytes = SecurityConstants.TOKEN_SECRET.getBytes();
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        String token = Jwts.builder()
                .subject(userName)
                .expiration(new Date(System.currentTimeMillis() + (long) 864000000))
                .signWith(secretKey)
                .compact();

        UsersService userService = (UsersService) SpringApplicationContext.getBean("usersService");
        UserDto userDto = userService.getUser(userName);

        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("UserID", userDto.getUserId());

    }

}