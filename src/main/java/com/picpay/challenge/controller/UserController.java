package com.picpay.challenge.controller;

import com.picpay.challenge.DTO.user.GetUserDTO;
import com.picpay.challenge.DTO.user.PostUserDTO;
import com.picpay.challenge.DTO.user.UpdateUserDTO;
import com.picpay.challenge.DTO.user.UserReturnDTO;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.domain.user.UserRepository;
import com.picpay.challenge.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid PostUserDTO data, UriComponentsBuilder uriComponentsBuilder) {
        User user = service.createUser(data);

        URI uri = uriComponentsBuilder
                .path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new UserReturnDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        User user = service.findOne(id);

        return ResponseEntity.ok(new GetUserDTO(user));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(sort = { "firstName" }) Pageable pagination) {
        Page<GetUserDTO> page = service.findAllActive(pagination);

        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid UpdateUserDTO data) {
        User user = service.updateUser(id, data);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
