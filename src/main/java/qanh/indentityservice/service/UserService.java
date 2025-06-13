package qanh.indentityservice.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qanh.indentityservice.dto.request.UserCreationRequest;
import qanh.indentityservice.dto.request.UserUpdateRequest;
import qanh.indentityservice.dto.response.UserResponse;
import qanh.indentityservice.entity.User;
import qanh.indentityservice.exception.AppException;
import qanh.indentityservice.exception.ErrorCode;
import qanh.indentityservice.mapper.UserMapper;
import qanh.indentityservice.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUser(UserCreationRequest userReq) {

        if (userRepository.existsByUsername(userReq.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);

        }
        User user = userMapper.toUser(userReq);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        return userRepository.save(user);
    }

    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay user")));
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
