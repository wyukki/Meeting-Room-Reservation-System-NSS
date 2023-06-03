package fel.cvut.cz.reservation_management_system.service;

import fel.cvut.cz.reservation_management_system.dto.UserDto;

public interface UserService {

    UserDto getOrCreateUserByEmail(String email);

}
