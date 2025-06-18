package qanh.indentityservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qanh.indentityservice.dto.request.UserCreationRequest;
import qanh.indentityservice.dto.request.UserUpdateRequest;
import qanh.indentityservice.dto.response.UserResponse;
import qanh.indentityservice.entity.User;
import qanh.indentityservice.enums.Role;
import qanh.indentityservice.exception.AppException;
import qanh.indentityservice.exception.ErrorCode;
import qanh.indentityservice.mapper.UserMapper;
import qanh.indentityservice.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(UserCreationRequest userReq) {

        if (userRepository.existsByUsername(userReq.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);

        }
        User user = userMapper.toUser(userReq);

        user.setPassword(passwordEncoder.encode(userReq.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser() {
        log.info("In method getAllUser");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id) {
        log.info("In method get user by ID");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay user")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdateRequest userUpdateRequest) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(userEntity, userUpdateRequest);

        return userMapper.toUserResponse(userRepository.save(userEntity));
    }

    public void deleteUser(String id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            System.out.println("Khong tim thay user");
        }
    }
}
