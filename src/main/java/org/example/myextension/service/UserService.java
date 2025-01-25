package org.example.myextension.service;


import lombok.RequiredArgsConstructor;


import org.example.myextension.dto.UserRequestDto;
import org.example.myextension.dto.UserResponseDto;
import org.example.myextension.entity.UserEntity;
import org.example.myextension.entity.WishListEntity;
import org.example.myextension.exceptions.UserNotFoundException;
import org.example.myextension.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
    @Service
    public class UserService {

        private final UserRepository userRepository;
        private final ModelMapper modelMapper;





        public UserResponseDto saveUser(UserRequestDto userRequestDto) {
            UserEntity userEntity = modelMapper.map(userRequestDto, UserEntity.class);
            WishListEntity build = WishListEntity.builder().
                    name(userEntity.getUsername() + "'s Wishlist")
                    .user(userEntity).build();

            userEntity.setWishlist(build);
           userRepository.save(userEntity);

            userRepository.save(userEntity);
             return UserResponseDto.builder().
                     username(userEntity.getUsername())
                     .email(userEntity.getEmail())
                     .id(userEntity.getId())
                     .build();
        }


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
