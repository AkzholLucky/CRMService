package com.backend.PhoneVerify.repositories;

import com.backend.PhoneVerify.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Component
public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
    Optional<User> findByActivationCode(String code);
    Optional<User> findByNameAndNumber(String name, String number);
}
