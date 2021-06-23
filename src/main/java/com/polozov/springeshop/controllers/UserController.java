package com.polozov.springeshop.controllers;

import com.polozov.springeshop.domain.User;
import com.polozov.springeshop.dto.UserDto;
import com.polozov.springeshop.service.UserService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String userList(Model model){
		model.addAttribute("users", userService.getAll());
		return "userList";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/new")
	public String newUser(Model model){
		System.out.println("Called method newUser");
		model.addAttribute("user", new UserDto());
		return "user";
	}

	@PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
	@GetMapping("/{name}/roles")
	@ResponseBody
	public String getRoles(@PathVariable("name") String username){
		System.out.println("Called method getRoles");
		User byName = userService.findByName(username);
		return byName.getRole().name();
	}

	@PostMapping("/new")
	public String saveUser(UserDto dto, Model model){
		if(userService.save(dto)){
			return "redirect:/users";
		}
		else {
			model.addAttribute("user", dto);
			return "user";
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/profile")
	public String profileUser(Model model, Principal principal){
		if(principal == null){
			throw new RuntimeException("You are not authorize");
		}
		User user = userService.findByName(principal.getName());

		UserDto dto = UserDto.builder()
				.username(user.getName())
				.email(user.getEmail())
				.build();
		model.addAttribute("user", dto);
		return "profile";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/profile")
	public String updateProfileUser(UserDto dto, Model model, Principal principal){
		if(principal == null
				|| !Objects.equals(principal.getName(), dto.getUsername())){
			throw new RuntimeException("You are not authorize");
		}
		if(dto.getPassword() != null
				&& !dto.getPassword().isEmpty()
				&& !Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
			model.addAttribute("user", dto);
			return "profile";
		}

		userService.updateProfile(dto);
		return "redirect:/users/profile";
	}

}
