package fel.cvut.cz.reservation_management_system.mapper;

import fel.cvut.cz.reservation_management_system.dto.UserDto;
import fel.cvut.cz.reservation_management_system.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);

    User dtoToEntity(UserDto userDto);
}
