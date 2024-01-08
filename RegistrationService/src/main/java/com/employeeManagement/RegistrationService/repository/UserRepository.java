package com.employeeManagement.RegistrationService.repository;

import com.employeeManagement.RegistrationService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserid(Long userId);

    @Transactional
    void deleteByUserid(Long userId);

}
