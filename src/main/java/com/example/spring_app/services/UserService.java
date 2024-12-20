package com.example.spring_app.services;

import com.example.spring_app.dtos.UserDTO;
import com.example.spring_app.entities.User;

import com.example.spring_app.mappers.UserMapper;
import com.example.spring_app.tabledatagateways.UserTableDataGateway;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserTableDataGateway userGateway;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserTableDataGateway userGateway, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userGateway.findAll();
        return users.stream().map(userMapper::toDTO).toList();
    }

    public String registerUser(UserDTO userDTO) {
        if (userGateway.existsByUsername(userDTO.getUsername())) {
            return "Uživatel již existuje.";
        }

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userGateway.insert(user);

        return "Uživatel úspěšně registrován.";
    }

    public boolean loginUser(String username, String password) {
        try {
            String storedPassword = userGateway.findPasswordByUsername(username);
            return passwordEncoder.matches(password, storedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userGateway.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Uživatel nenalezen: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}
