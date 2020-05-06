package com.company.astra.controllers;

import com.company.astra.domein.Role;
import com.company.astra.domein.Users;
import com.company.astra.repo.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

	@Autowired
	private IUserRepository userRepository;

	@GetMapping("/registration")
	public String registration(){
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(Users user, Map<String, Object> model){
		Users userFromDB = userRepository.findByUsername(user.getUsername());
		if (userFromDB != null){
			model.put("message", "User exists");
			return "registration";
		}

		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepository.save(user);
		return "redirect:/login";
	}
}
