package com.picpay.challenge.services;

import com.picpay.challenge.DTO.user.GetUserDTO;
import com.picpay.challenge.DTO.user.PostUserDTO;
import com.picpay.challenge.DTO.user.UpdateUserDTO;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import com.picpay.challenge.domain.user.exception.UserInactiveException;
import com.picpay.challenge.domain.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(PostUserDTO data) {
        User user = new User(data);

        user.setPassword(passwordEncoder.encode(data.password()));

        userRepository.save(user);

        return user;
    }

    public User findOne(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        return user.get();
    }

    public Page<GetUserDTO> findAllActive(Pageable pagination) {
        return userRepository
                .findAllByActiveTrue(pagination)
                .map(GetUserDTO::new);
    }

    public User updateUser(String id, UpdateUserDTO data) {
        User user = userRepository.getReferenceById(id);

        user.updateInfo(data);

        return user;
    }

    public void deleteUser(String id) {
        User user = userRepository.getReferenceById(id);

        if (!user.isActive()) {
            throw new UserInactiveException(id);
        }

        user.delete();
    }
}
