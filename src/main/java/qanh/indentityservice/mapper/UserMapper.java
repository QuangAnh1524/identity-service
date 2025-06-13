package qanh.indentityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import qanh.indentityservice.dto.request.UserCreationRequest;
import qanh.indentityservice.dto.request.UserUpdateRequest;
import qanh.indentityservice.dto.response.UserResponse;
import qanh.indentityservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest user);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest userReq);
}
