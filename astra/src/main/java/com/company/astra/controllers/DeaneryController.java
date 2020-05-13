package com.company.astra.controllers;

import com.company.astra.models.Groups;
import com.company.astra.repo.IGroupRepository;
import com.company.astra.repo.IMarksRepository;
import com.company.astra.repo.IPeopleRepository;
import com.company.astra.repo.ISubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class DeaneryController {

	@Autowired
	private IGroupRepository groupRepository;

	@Autowired
	private IMarksRepository marksRepository;

	@Autowired
	private IPeopleRepository peopleRepository;

	@Autowired
	private ISubjectsRepository subjectsRepository;

	@GetMapping("/deanery")
	public String deaneryMain(Model model) {
		Iterable<Groups> groups = groupRepository.findAll();
		model.addAttribute("groups", groups);

		return "main";
	}

	@PostMapping("/deanery/add")
	public String groupAdd(@RequestParam String name, Model model) {
		Groups groups = new Groups(name);
		groupRepository.save(groups);
		return "redirect:/deanery";
	}

	@GetMapping("/deanery/add")
	public String add(Model model) {
		return "add";
	}


	@GetMapping("/deanery/{id}")
	public String groupDetail(@PathVariable(value = "id") Long id, Model model) {
		if (!groupRepository.existsById(id)){
			return "redirect:/deanery";
		}
		Optional<Groups> groupDetail = groupRepository.findById(id);
		ArrayList<Groups> res = new ArrayList<>();
		groupDetail.ifPresent(res::add);
		model.addAttribute( "groupDetail", res);
		return "detail";
	}

	@PostMapping("/deanery/{id}/edit")
	public String groupEdit(@PathVariable(value = "id") Long id, Model model) {
		return "edit";
	}

	@PostMapping("/deanery/{id}/showAllInfo")
	public String groupAllInfo(@PathVariable(value = "id") Long id, Model model) {
		return "showAllInfo";
	}


	@PostMapping("/deanery/{id}/schedule")
	public String groupSchedule(@PathVariable(value = "id") Long id, Model model) {
		return "schedule";
	}

	@PostMapping("/deanery/{id}/remove")
	public String groupDelete (@PathVariable(value = "id") Long id, Model model) {
		Groups groups = groupRepository.findById(id).orElseThrow();
		groupRepository.delete(groups);
		return "redirect:/deanery";
	}

	@GetMapping("/deanery/find")
	public String find(Model model){
		return "find";
	}

	@PostMapping("/deanery/find")
	public String findGroup(@RequestParam String nameGroup, Model model) {
		Iterable<Groups> groups = groupRepository.findByName(nameGroup);
		model.addAttribute("groups", groups);
		return "find";
	}



}
