package org.example.myextension.Security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myextension.dto.UserRequestDto;
import org.example.myextension.dto.UserResponseDto;
import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.example.myextension.enums.Role;
import org.example.myextension.exceptions.UserException;
import org.example.myextension.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    private final AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterReq request) throws UserException {
        var user= UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        WishListEntity build = WishListEntity.builder()
                .name(user.getUsername() + "'s Wishlist")
                .user(user)
                .build();


        user.setWishlist(build);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) throws UserException{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            System.out.println("user authenticated");
        } catch (Exception e) {
            e.printStackTrace();
        }


        var user=repository.findByUsername(request.getUsername());
        System.out.println("found user: {}"+ user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .build();
    }
}