package com.example.project.authentication.user;//package com.example.project.authentication.user;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class UserDTO {
//
//        private Long id;
//        private String username;
//        private String password;
//
//        public UserDTO() {
//        }
//
//        public UserDTO(Long id, String username, String password) {
//            this.id = id;
//            this.username = username;
//            this.password = password;
//        }
//
//        // Getters and setters
//}
//
////
////public class EmployeeDTO {
////
////    private int employeeid;
////    private String employeename;
////    private String email;
////    private String password;
////
////    public EmployeeDTO() {
////    }
////
////    public EmployeeDTO(int employeeid, String employeename, String email, String password) {
////        this.employeeid = employeeid;
////        this.employeename = employeename;
////        this.email = email;
////        this.password = password;
////    }
////
////} //create getters and setters
//


public record UserDto(Long id, String username, String name, String email, String role) {
}