package com.example.project.authentication.user;//package com.example.project.authentication.user;
//
//import com.example.project.authentication.LoginDTO;
//import com.example.project.authentication.LoginMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import com.example.project.authentication.authority.Authority;
//import com.example.project.authentication.authority.AuthorityRepository;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/v1/public")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AuthorityRepository authorityRepository;
//
//    @PostMapping(value = "/save", consumes = "application/json", produces = "application/json")
//    @CrossOrigin(origins = "http://localhost:3000")
//    public String saveUser(@RequestBody UserDTO userDTO) {
//        String id = userService.addUser(userDTO);
//        return id;
//    }
//
//    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
//        LoginMessage loginMessage = userService.loginUser(loginDTO);
//        return ResponseEntity.ok(loginMessage);
//    }
//
//    @PostMapping(value = "/v2/login", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<?> logInUser(@RequestBody LoginDTO loginDTO) {
//        LoginMessage loginMessage = userService.loginUser(loginDTO);
//
//        // If login is successful, set the Authentication object in the SecurityContext
//        if (loginMessage.isSuccess()) {
//            User user = userService.findUserByUsername(loginDTO.getUsername());
//            if (user != null) {
//                Authority authority = authorityRepository.findAuthorityById(user.getId());
//                if (authority != null) {
//                    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(new SimpleGrantedAuthority(authority.getAuthority())));
//                    SecurityContextHolder.getContext().setAuthentication(auth);
//                }
//            }
//        }
//
//        return ResponseEntity.ok(loginMessage);
//    }
//}


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.project.authentication.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final UserRepository userRepository;

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(currentUser.getUsername()));
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @DeleteMapping("/{username}")
    public UserDto deleteUser(@PathVariable String username) {
        User user = userService.validateAndGetUserByUsername(username);
        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }
}
