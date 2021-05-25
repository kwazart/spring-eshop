package com.polozov.springeshop.service;

import com.polozov.springeshop.dao.UserRepository;
import com.polozov.springeshop.domain.Role;
import com.polozov.springeshop.domain.User;
import com.polozov.springeshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public boolean save(UserDTO userDTO) {
		if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
			throw new RuntimeException("Password is not equals");
		}
		User user = User.builder()
				.name(userDTO.getUsername())
				.password(passwordEncoder.encode(userDTO.getPassword()))
				.email(userDTO.getEmail())
				.role(Role.CLIENT)
				.build();
		userRepository.save(user);
		return true;
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public List<UserDTO> getAll() {
		return userRepository.findAll().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with name: " + username);
		}

		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(user.getRole().name()));

		return new org.springframework.security.core.userdetails.User(
				user.getName(),
				user.getPassword(),
				roles
		);
	}

	private UserDTO toDto(User user) {
		return UserDTO.builder()
				.username(user.getName())
				.email(user.getEmail())
				.build();
	}

	@Override
	public User findByName(String name) {
		return userRepository.findFirstByName(name);
	}

	@Override
	@Transactional
	public void updateProfile(UserDTO dto) {
		User savedUser = userRepository.findFirstByName(dto.getUsername());
		if (savedUser == null) {
			throw new RuntimeException("User not found by name " + dto.getUsername());
		}

		boolean isChanged = false;
		if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
			savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
			isChanged = true;
		}

		if (!Objects.equals(dto.getEmail(), savedUser.getEmail())) {
			savedUser.setEmail(dto.getEmail());
			isChanged = true;
		}

		if (isChanged) {
			userRepository.save(savedUser);
		}
	}
}
