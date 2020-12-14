package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.ApplicationUser;
import io.bini.poker.pokerdujeudi.service.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        applicationUser.setPassword(this.bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        userRepository.save(applicationUser);
    }
}
