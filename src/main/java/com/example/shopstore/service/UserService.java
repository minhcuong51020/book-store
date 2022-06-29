package com.example.shopstore.service;

import com.example.shopstore.dto.UserDTO;
import com.example.shopstore.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public boolean addUser(UserDTO userDTO);

    public UserDTO getUserByUsername(String username);

    public UserDTO updateUser(UserDTO userDTO);

}
