package org.example.myextension.service;


import lombok.RequiredArgsConstructor;


import org.example.myextension.dto.UserRequestDto;
import org.example.myextension.dto.UserResponseDto;
import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.example.myextension.exceptions.UserNotFoundException;
import org.example.myextension.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
    @Service
    public class UserService {

        private final UserRepository userRepository;
        private final ModelMapper modelMapper;

        private final AuthenticationManager authenticationManager;
        private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder() ;








    public UserResponseDto findUserById(Long id) {
            UserEntity userEntity = userRepository.findById(id).orElseThrow(()->(new UserNotFoundException("user not found for id "+id)));
            return UserResponseDto.builder().
                    username(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .id(userEntity.getId())
                    .build();
        }





        public UserResponseDto findUserByName(String username) {
            UserEntity userEntity = userRepository.findByUsername(username);
            return UserResponseDto.builder().
                    username(userEntity.getUsername())
                    .email(userEntity.getEmail())
                    .id(userEntity.getId())
                    .build();

        }




}
