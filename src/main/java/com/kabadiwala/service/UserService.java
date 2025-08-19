package com.kabadiwala.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kabadiwala.dto.UserDto;
import com.kabadiwala.entity.User;
import com.kabadiwala.repository.UserRepository;
import com.kabadiwala.utils.EntityDtoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Lombok annotation for constructor injection
@Transactional // Ensures all methods are transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository; // Dependency is injected via the constructor

    /**
     * Finds a user by their contact number.
     * @param contactNumber The user's contact number.
     * @return An Optional of User.
     */
    public Optional<User> findByContactNumber(String contactNumber) {
        return userRepository.findByContactNumber(contactNumber);
    }

    /**
     * Saves a user entity to the database.
     * Useful for both creating new users and updating existing ones.
     * @param user The user object to save.
     * @return The saved user entity with updated fields (like id, createdAt).
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    /**
     * Finds an existing user or creates a new one if not found.
     * This is useful after successful OTP verification for a new user.
     * @param contactNumber The user's contact number.
     * @param fullName The user's full name (required for new users).
     * @return The existing or newly created user.
     */
    public User findOrCreateUser(String contactNumber, String fullName) {
        return userRepository.findByContactNumber(contactNumber)
                .orElseGet(() -> {
                    logger.info("No user found with contact number {}. Creating a new user.", contactNumber);
                    User newUser = User.builder()
                            .contactNumber(contactNumber)
                            .fullName(fullName)
                            .build();
                    return userRepository.save(newUser);
                });
    }

    public UserDto createUser(UserDto dto) {
        User user = EntityDtoMapper.toUserEntity(dto);
        return EntityDtoMapper.toUserDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(EntityDtoMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
