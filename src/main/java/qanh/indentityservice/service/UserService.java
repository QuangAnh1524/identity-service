package qanh.indentityservice.service;

import org.springframework.stereotype.Service;
import qanh.indentityservice.dto.request.UserCreationRequest;
import qanh.indentityservice.entity.User;
import qanh.indentityservice.exception.AppException;
import qanh.indentityservice.exception.ErrorCode;
import qanh.indentityservice.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreationRequest user) {
        User userEntity = new User();

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setDob(user.getDob());

        return userRepository.save(userEntity);
    }

    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay user"));
    }

    public User updateUser(String id, UserCreationRequest user) {
        User userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null) {
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setDob(user.getDob());
            return userRepository.save(userEntity);
        } else {
            userEntity = createUser(user);
            return userRepository.save(userEntity);
        }
    }

    public void deleteUser(String id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            System.out.println("Khong tim thay user");
        }
    }
}
