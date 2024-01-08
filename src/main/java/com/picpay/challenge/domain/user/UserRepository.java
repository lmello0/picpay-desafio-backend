package com.picpay.challenge.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAllByActiveTrue(Pageable pagination);

    UserDetails findByEmail(String email);
}
