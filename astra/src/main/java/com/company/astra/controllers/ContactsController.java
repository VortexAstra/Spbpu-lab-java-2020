package com.company.astra.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController {
	@GetMapping("/contacts")
	public String contactsMain(Model model){
		return "contacts";
	}
}
