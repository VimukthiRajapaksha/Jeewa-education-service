/**
 * 
 */
package com.graymatter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author vimukthi_r
 *
 */
@Controller
@RequestMapping("/students")
public class StudentController {
	@Autowired
	StudentService studentService;
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/events")
	public String eventApply(@RequestParam(required = true) Integer eventId,
			@RequestParam(required = true) Integer studentId, Model model) {
		if (studentService.processApplyEvent(studentId, eventId)) {
			model.addAttribute(CommonUtil.SUCCESS_MESSAGE, "Successfully applied for the event!");
		} else {
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR: applying event failed!");
		}
		return "redirect:/universities";
	}
	
	@PostMapping
	public void addStudent(@RequestBody Student student) {
		studentRepository.save(student);
	}
}
