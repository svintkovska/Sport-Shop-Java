package com.example.springbootshop.services;

import com.example.springbootshop.entities.ERole;
import com.example.springbootshop.entities.Role;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.exceptions.EntityNotFoundException;
import com.example.springbootshop.repositories.RoleRepository;
import com.example.springbootshop.repositories.UserRepository;
import com.example.springbootshop.security.requests.SignupRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService  {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username = " + username));
    }

    public User createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        //user.setPassword(signupRequest.getPassword());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username = " + username));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public User changeUserRoles(Long userId, Set<ERole> newRoles) {
        User user = getUserById(userId);
        Set<Role> roles = newRoles.stream()
                .map(role -> roleRepository.findByName(role).orElseThrow(() -> new EntityNotFoundException("Role not found: " + role)))
                .collect(Collectors.toSet());

        user.setRoles(roles);
        return userRepository.save(user);
    }
}

