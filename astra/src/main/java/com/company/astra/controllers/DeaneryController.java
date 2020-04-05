package com.company.astra.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeaneryController {



	@GetMapping("/deanery")
	public String deaneryMain(Model model){
		return "deanery-main";
	}
}
