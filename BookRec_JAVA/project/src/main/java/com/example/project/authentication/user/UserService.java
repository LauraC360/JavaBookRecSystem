//package com.example.project.authentication.user;
//
//import com.example.project.authentication.user.UserDTO;
//import com.example.project.authentication.LoginDTO;
//import com.example.project.authentication.LoginMessage;
//
//// TODO: Add missing imports
//
//public interface UserService {
//    String addUser(UserDTO userDTO);
//
//    LoginMessage loginUser(LoginDTO loginDTO);
//
//    User findUserByUsername(String username);
//}
//
////
////package com.example.Registation.Service;
////
////import com.example.Registation.Dto.EmployeeDTO;
////import com.example.Registation.Dto.LoginDTO;
////import com.example.Registation.payload.response.LoginMesage;
////
////public interface EmployeeService {
////    String addEmployee(EmployeeDTO employeeDTO);
////
////    LoginMesage loginEmployee(LoginDTO loginDTO);
////
////}
//


package com.example.project.authentication.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    void deleteUser(User user);

    Optional<User> validUsernameAndPassword(String username, String password);
}
