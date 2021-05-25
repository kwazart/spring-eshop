package com.polozov.springeshop.service;

import com.polozov.springeshop.domain.User;
import com.polozov.springeshop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService { // security
	boolean save(UserDTO userDTO);
	void save(User user);
	List<UserDTO> getAll();

	User findByName(String name);
	void updateProfile(UserDTO userDTO);
}
