package com.picpay.challenge.controller;

import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.DTO.*;
import com.picpay.challenge.domain.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid PostUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        User user = new User(data);

        userRepository.save(user);

        URI uri = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UserReturnDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        User user = userRepository.getReferenceById(id);

        return ResponseEntity.ok(new GetUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<GetUserDTO>> findAll(@PageableDefault(sort = { "firstName" }) Pageable pagination) {
        Page<GetUserDTO> page = userRepository
                .findAllByActiveTrue(pagination)
                .map(GetUserDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid UpdateUserDTO data) {
        User user = userRepository.getReferenceById(id);

        user.updateInfo(data);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        User user = userRepository.getReferenceById(id);

        if (!user.isActive()) {
            return ResponseEntity.badRequest().body("User is inactive");
        }

        user.delete();

        return ResponseEntity.noContent().build();
    }
}