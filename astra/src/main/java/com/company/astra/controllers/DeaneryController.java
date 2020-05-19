package com.company.astra.controllers;

import com.company.astra.models.Groups;
import com.company.astra.models.Marks;
import com.company.astra.models.People;
import com.company.astra.models.Subjects;
import com.company.astra.repo.IGroupRepository;
import com.company.astra.repo.IMarksRepository;
import com.company.astra.repo.IPeopleRepository;
import com.company.astra.repo.ISubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
		if (!groupRepository.existsById(id)) {
			return "redirect:/deanery";
		}

		Optional<Groups> groupDetail = groupRepository.findById(id);
		ArrayList<Groups> res = new ArrayList<>();
		groupDetail.ifPresent(res::add);
		model.addAttribute("groupDetail", res);
		return "detail";
	}

	@PostMapping("/deanery/{id}/edit")
	public String groupEdit(@PathVariable(value = "id") Long id,
							@RequestParam(required = false) String first,
							@RequestParam(required = false) String second,
							@RequestParam(required = false) String third,
							@RequestParam(required = false) String type,
							Model model) {



		Optional<Groups> groups = groupRepository.findById(id);
//		var groupID = groups.get();
//		model.addAttribute("groupID", groupID);

		if (groups.isPresent()) {
			People people = new People(first, second, third, type, id, groups.get());
			peopleRepository.save(people);
			return "edit";
		}
		return "edit";
	}

	@PostMapping("/deanery/{id}/showAllInfo")
	public String groupAllInfo(@PathVariable(value = "id") Long id,
							   Model model) {
		Iterable<People> peopleInGroup = peopleRepository.getAllByGroups(groupRepository.findById(id));
		model.addAttribute("peopleInGroup", peopleInGroup);
		return "showAllInfo";
	}

	@PostMapping("/deanery/findPeopleInGroup")
	public String findPeopleByName(@RequestParam String namePeople, Model model) {
		Iterable<People> people = peopleRepository.findPeopleByFirstName(namePeople);
		model.addAttribute("people", people);
		return "findPeopleInGroup";
	}

	@GetMapping("/deanery/findPeopleInGroup")
	public String findPeopleByName(Model model) {

		return "findPeopleInGroup";
	}


	@PostMapping("/deanery/{id}/schedule")
	public String groupSchedule(@PathVariable(value = "id") Long id, Model model) {
		return "schedule";
	}

	@PostMapping("/deanery/{id}/remove")
	public String groupDelete(@PathVariable(value = "id") Long id, Model model) {
		Groups groups = groupRepository.findById(id).orElseThrow();
		groupRepository.delete(groups);
		return "redirect:/deanery";
	}

	@GetMapping("/deanery/find")
	public String find(Model model) {
		return "find";
	}

	@PostMapping("/deanery/find")
	public String findGroup(@RequestParam String nameGroup, Model model) {
		Iterable<Groups> groups = groupRepository.findByName(nameGroup);
		model.addAttribute("groups", groups);
		return "find";
	}


	@GetMapping("/deanery/{id}/progress")
	public String peopleProgress(@PathVariable(value = "id") Long id,
								 Model model) {
		Optional<People> uniquePeople = peopleRepository.findById(id);

		if (uniquePeople.isPresent()) {
			var one = uniquePeople.get();
			model.addAttribute("one", one);
		}
		List<Marks> marks = marksRepository.findByStudentId(id);
		model.addAttribute("marks", marks);


		return "progress";
	}

	@GetMapping("/deanery/{id}/progress/addRating")
	public String addRating(@PathVariable(value = "id") Long id, Model model) {
		return "addRating";
	}

	@PostMapping("/deanery/{id}/progress/addRating")
	public String addRating(@PathVariable(value = "id") Long id,
							@RequestParam(required = false) String subject,
							@RequestParam(required = false) Long rating,
							Model model) {

		Marks marks = new Marks(rating);
		marks.setStudentId(id);
		Random random = new Random();
		long num = random.nextInt(100);

		marks.setTeacher_id(num);

		Subjects subjects = new Subjects(subject);
		subjectsRepository.save(subjects);

		Subjects subjects1 = subjectsRepository.findByNameOfSubjects(subject);
		marks.setSubjectId(subjects1.getId());
		marks.setSubjects(subjects1);

		marksRepository.save(marks);

		return "redirect:/deanery/{id}/progress";
	}

	@GetMapping("/deanery/{id}/progress/deleteRating")
	public String deleteRating(@PathVariable(value = "id") Long id,
							   Model model) {
		return "deleteRating";
	}


	@Transactional
	@PostMapping("/deanery/{id}/progress/deleteRating")
	public String deleteRating(@PathVariable(value = "id") Long id,
							   @RequestParam String subject) {
		if (peopleRepository.findById(id).isPresent()) {
			subjectsRepository.deleteByNameOfSubjects(subject);
		}

		return "deleteRating";
	}

	@PostMapping("/deanery/{id}/deletePeople")
	public String deletePeople(@PathVariable(value = "id") Long id, Model model) {
		People people = peopleRepository.findById(id).orElseThrow();
		peopleRepository.delete(people);

		return "redirect:/deanery";
	}

}
