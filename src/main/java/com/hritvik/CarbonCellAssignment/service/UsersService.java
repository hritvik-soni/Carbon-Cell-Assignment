package com.hritvik.CarbonCellAssignment.service;


import com.hritvik.CarbonCellAssignment.dto.UserRequestDto;
import com.hritvik.CarbonCellAssignment.dto.UsersResponseDto;
import com.hritvik.CarbonCellAssignment.exceptions.GlobalExceptionHandler;
import com.hritvik.CarbonCellAssignment.model.Users;
import com.hritvik.CarbonCellAssignment.repository.UsersRepository;
import com.hritvik.CarbonCellAssignment.utils.Response;
import com.hritvik.CarbonCellAssignment.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UsersService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ResponseUtil responseUtil;

    public Response createUser(UserRequestDto userRequestDto) {
        try {
            if (usersRepository.findByEmail(userRequestDto.getEmail()).isEmpty()) {
                log.info("start creating user");
                if (StringUtils.isAnyBlank(userRequestDto.getFirstName(), userRequestDto.getLastName(), userRequestDto.getEmail(), userRequestDto.getPassword(), userRequestDto.getPhoneNumber())) {
                    responseUtil.notFoundResponse("One or more fields are empty");
                }
                Users user = new Users();

                user.setFirstName(userRequestDto.getFirstName().substring(0, 1).toUpperCase() + userRequestDto.getFirstName().substring(1).toLowerCase());
                user.setLastName(userRequestDto.getLastName().substring(0, 1).toUpperCase() + userRequestDto.getLastName().substring(1).toLowerCase());
                user.setFullName(userRequestDto.getFirstName().substring(0, 1).toUpperCase() + userRequestDto.getFirstName().substring(1) + " " + userRequestDto.getLastName().substring(0, 1).toUpperCase() + userRequestDto.getLastName().substring(1).toLowerCase());
                user.setPhoneNumber(userRequestDto.getPhoneNumber());
                user.setEmail(userRequestDto.getEmail());
                user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

                user = usersRepository.save(user);
                UsersResponseDto userResponseDto = new UsersResponseDto();
                userResponseDto.setId(user.getId());
                userResponseDto.setFullName(user.getFullName());
                userResponseDto.setPhoneNumber(user.getPhoneNumber());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setCreatedAtTs(user.getCreatedAtTs());
                userResponseDto.setUpdatedAtTs(user.getUpdatedAtTs());

                log.info("User created successfully");
                return responseUtil.successResponse("User created successfully", userRequestDto);
            } else {
             return responseUtil.conflictResponse("User already exists with email: " + userRequestDto.getEmail());
            }
        }
        catch (Exception e) {
          return responseUtil.internalServerErrorResponse("Exception occurred: " + e.getMessage());
        }
    }

    public Response deleteUser(String id, String username) {
        try {
            log.info("start deleting user with id: {}", id);
            Optional<Users> user = usersRepository.findById(id);
            if (user.isEmpty()) {
                log.error("User not found with id: {}", id);
                responseUtil.notFoundResponse("User not found with id: " + id);
            }
            if (username.equals(user.get().getEmail())) {
                usersRepository.deleteById(id);
                log.info("user deleted successfully");
                return responseUtil.successResponse("User deleted successfully", null);
            }
            log.error("You are not authorized to delete this user");
            return responseUtil.badRequestResponse("you are not authorized to delete this user");
        } catch (Exception e) {
            return responseUtil.internalServerErrorResponse("Exception occurred: " + e.getMessage());
        }
    }

    public Response getUserById(String id) {
        try {
            log.info("start fetching user with id: {}", id);
            Optional<Users> user = usersRepository.findById(id);
            if (user.isEmpty()) {
                log.error("User not found with id: {}", id);
                responseUtil.notFoundResponse("User not found with id: " + id);
            }
            UsersResponseDto userResponseDto = new UsersResponseDto();
            userResponseDto.setId(user.get().getId());
            userResponseDto.setFullName(user.get().getFullName());
            userResponseDto.setPhoneNumber(user.get().getPhoneNumber());
            userResponseDto.setEmail(user.get().getEmail());
            userResponseDto.setCreatedAtTs(user.get().getCreatedAtTs());
            userResponseDto.setUpdatedAtTs(user.get().getUpdatedAtTs());
            log.info("User fetched successfully");
            return responseUtil.successResponse("User fetched successfully", userResponseDto);
        } catch (Exception e) {
            return responseUtil.internalServerErrorResponse("Exception occurred: " + e.getMessage());
        }
    }

    public Response getAllUsers() {
        try {
            log.info("start fetching all users");
            Iterable<Users> users = usersRepository.findAll();

            // Map Users entities to UsersResponseDto objects using Java 8 Streams
            List<UsersResponseDto> usersResponseList = StreamSupport.stream(users.spliterator(), false)
                    .map(user -> {
                        UsersResponseDto userResponseDto = new UsersResponseDto();
                        userResponseDto.setId(user.getId());
                        userResponseDto.setFullName(user.getFullName());
                        userResponseDto.setPhoneNumber(user.getPhoneNumber());
                        userResponseDto.setEmail(user.getEmail());
                        userResponseDto.setCreatedAtTs(user.getCreatedAtTs());
                        userResponseDto.setUpdatedAtTs(user.getUpdatedAtTs());
                        return userResponseDto;
                    })
                    .peek(userDto -> log.info("User with ID {} fetched successfully", userDto.getId()))
                    .collect(Collectors.toList());

            log.info("All users fetched successfully");
            return responseUtil.successResponse("Users fetched successfully", usersResponseList);
        } catch (Exception e) {
            log.error("Error occurred while fetching all users", e);
            return responseUtil.internalServerErrorResponse("Exception occurred: " + e.getMessage());
        }
    }
}
