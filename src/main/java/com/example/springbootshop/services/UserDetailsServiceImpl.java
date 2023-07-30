package com.example.springbootshop.services;

import com.example.springbootshop.entities.ERole;
import com.example.springbootshop.entities.User;
import com.example.springbootshop.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found: " + username));

        return build(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<ERole> roles) {
        return roles.stream().map(eRole -> new SimpleGrantedAuthority(eRole.name())).toList();
    }

    private  UserDetails build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(eRole -> new SimpleGrantedAuthority(eRole.name()))
                .collect(Collectors.toList());
        return new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("Username not found: " + userId));
    }
}
