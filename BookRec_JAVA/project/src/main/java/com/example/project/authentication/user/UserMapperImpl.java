//package com.example.project.authentication.user;
//
//import com.example.project.authentication.LoginDTO;
//import com.example.project.authentication.LoginMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class UserImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    UserDTO userDTO;
//
//    @Override
//    public String addUser(UserDTO userDTO) {
//        User user = new User(
//                userDTO.getId(),
//                userDTO.getUsername(),
//                this.passwordEncoder.encode(userDTO.getPassword())
//        );
//
//        userRepository.save(user);
//
//        return user.getUsername();
//    }
//
//    @Override
//    public LoginMessage loginUser(LoginDTO loginDTO) {
//        User user = userRepository.findByUsername(loginDTO.getUsername());
//        if (user != null) {
//            String password = loginDTO.getPassword();
//            String encodedPassword = user.getPassword();
//            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//            if (isPwdRight) {
//                return new LoginMessage("Login Success!", true);
//            } else {
//                return new LoginMessage("Login Failed!", false);
//            }
//        } else {
//            return new LoginMessage("Username does not exist!", false);
//        }
//    }
//
//    @Override
//    public User findUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//}
//

package com.example.project.authentication.user;

import com.example.project.authentication.user.User;
import com.example.project.authentication.user.UserDto;
import com.example.project.authentication.user.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole());
    }
}
