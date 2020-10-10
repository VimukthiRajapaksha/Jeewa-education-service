/**
 * 
 */
package com.graymatter.service;

import java.util.List;

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
	@Autowired
	private UniversityService universityService;
	@Autowired
	private DegreeService degreeService;
	@Autowired
	private EventService eventService;
	
	@GetMapping("/apply_events")
	public String eventApply(@RequestParam(required = true) Integer eventId,
			@RequestParam(required = true) Integer studentId, Model model) {
		if (studentService.processApplyEvent(studentId, eventId)) {
			model.addAttribute(CommonUtil.SUCCESS_MESSAGE, "Successfully applied for the event!");
		} else {
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR: applying event failed!");
		}
		return "/students/view_universities";
	}
	
	@PostMapping
	public void addStudent(@RequestBody Student student) {
		studentRepository.save(student);
	}
	
	@GetMapping(value = "/universities")
	public String getAllUniversity(@RequestParam(required = false) String name, Model model) {
		System.out.println(" name: " + name);
		try {
			if (name != null && !name.isEmpty()) {
				List<University> universityList = this.universityService.findByName(name);
				universityList.forEach(System.out::println);

				if (!universityList.isEmpty()) {
					model.addAttribute("universityList", universityList);
				} else {
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "No university found for name:" + name);
				}
			} else {
				System.out.println(this.universityService.findAll());
				model.addAttribute("universityList", this.universityService.findAll());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}

		return "student/view_universities";
	}
	
	@GetMapping(value = "/degrees")
	public String getAllDegrees(@RequestParam(required = false) String name, @RequestParam(required = false) Integer uniId,
			Model model) {
		System.out.println(" name: " + name);
		try {
			if (name != null && !name.isEmpty()) {
				List<Degree> degreeList = this.degreeService.findByName(name);
				degreeList.forEach(System.out::println);

				if (!degreeList.isEmpty()) {
					model.addAttribute("degreeList", degreeList);
				} else {
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "No degree found for name:" + name);
				}
			} else if (uniId != null) {
				List<Degree> degreeList = this.degreeService.findByUniversityId(uniId);
				degreeList.forEach(System.out::println);

				if (!degreeList.isEmpty()) {
					model.addAttribute("degreeList", degreeList);
				} else {
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "No degree found for name:" + name);
				}
			} else {
				System.out.println(this.degreeService.findAll());
				model.addAttribute("degreeList", this.degreeService.findAll());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}

		return "student/view_degrees";
	}
	
	@GetMapping("/events")
	public String getAllEvents(@RequestParam(required = false) String name, @RequestParam(required = false) Integer uniId,
			Model model) {
		System.out.println(" name: " + name);
		try {
			if (name != null && !name.isEmpty()) {
				List<Event> eventList = this.eventService.findByName(name);
				eventList.forEach(System.out::println);

				if (!eventList.isEmpty()) {
					model.addAttribute("eventList", eventList);
				} else {
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "No event found for name:" + name);
				}
			} else if (uniId != null) {
				List<Event> eventList = this.eventService.findByUniversityId(uniId);
				eventList.forEach(System.out::println);

				if (!eventList.isEmpty()) {
					model.addAttribute("eventList", eventList);
				} else {
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "No event found for name:" + name);
				}
			} else {
				System.out.println(this.eventService.findAll());
				model.addAttribute("eventList", this.eventService.findAll());
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}

		return "student/view_events";
	}
}
