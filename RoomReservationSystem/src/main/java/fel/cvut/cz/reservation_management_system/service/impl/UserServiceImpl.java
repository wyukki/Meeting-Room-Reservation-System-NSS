package fel.cvut.cz.reservation_management_system.service.impl;

import fel.cvut.cz.reservation_management_system.dto.UserDto;
import fel.cvut.cz.reservation_management_system.entity.User;
import fel.cvut.cz.reservation_management_system.mapper.UserMapper;
import fel.cvut.cz.reservation_management_system.repository.UserRepository;
import fel.cvut.cz.reservation_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getOrCreateUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);

         if(user == null){
             user = new User();
             user.setEmail(email);
             user.setReservations(null);
         }

        return userMapper.entityToDto(user);
    }
}
