////package com.example.project.authentication.user;
////
////import org.springframework.data.jpa.repository.JpaRepository;
////import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
////import org.springframework.stereotype.Repository;
////
////import java.util.Optional;
////
////
////@EnableJpaRepositories
////@Repository
////public interface UserRepository extends JpaRepository<User, Long> {
////
////    Optional<User> findOneByUsernameAndPassword(String username, String password);
////
////    User findByUsername(String username);
////
////}
////
////
//////
//////@EnableJpaRepositories
//////@Repository
//////public interface EmployeeRepo extends JpaRepository<Employee,Integer>
//////{
//////    Optional<Employee> findOneByEmailAndPassword(String email, String password);
//////
//////    Employee findByEmail(String email);
//////}
//
//package com.example.project.authentication.user;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@EnableJpaRepositories
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//
//    Optional<User> findOneByEmailAndPassword(String email, String password);
//
//    User findByEmail(String email);
//
//}

package com.example.project.authentication.user;

import com.example.project.authentication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}