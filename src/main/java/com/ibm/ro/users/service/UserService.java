package com.ibm.ro.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ibm.ro.users.ui.dto.UserDTO;

public interface UserService extends UserDetailsService {

	UserDTO getUser(String userId);

	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserByName(String name);

	UserDTO getUserByEmail(String email);

	void deleteUser(String email);

}
