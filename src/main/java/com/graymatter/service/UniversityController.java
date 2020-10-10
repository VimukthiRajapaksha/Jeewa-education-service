/**
 * 
 */
package com.graymatter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 *
 */

@Controller
@RequestMapping(value = "/universities")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UniversityController {

	private UniversityService universityService;
	private DegreeService degreeService;
	private EventService eventService;
	
	public UniversityController(UniversityService universityService, DegreeService degreeService, EventService eventService) {
		this.universityService = universityService;
		this.degreeService = degreeService;
		this.eventService = eventService;
	}

	@GetMapping
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

		return "admin/view_universities";
	}

	@GetMapping(value = "/{id}")
	public String getUniversityById(@PathVariable("id") int id, Model model) {
		System.out.println("id: " + id);
		try {
			System.out.println("result: " + this.universityService.findById(id));
			model.addAttribute("university", this.universityService.findById(id)
					.orElseThrow(() -> new Exception("University not found! ID:" + id)));
			model.addAttribute("degreeList", this.universityService.getDegreeList(id));
			// for events
			model.addAttribute("eventList", this.eventService.getNewEventsList(id, 1)); //change this 1 to student id
			model.addAttribute("appliedEventList", this.eventService.getAppliedEventsList(id, 1)); //change this 1 to student id
			model.addAttribute("student", new Student());
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}
		return "admin/view_university";
	}

	@PostMapping
	public String add(@ModelAttribute UniversityDTO universityDto, Model model) {
		System.out.println(universityDto);
		try {
			// save to database
			University university = this.universityService
					.add(this.universityService.getUniversityFromDTO(universityDto));
			if (university != null && university.getId() >= 0) {
				// save successful
				model.addAttribute(CommonUtil.SUCCESS_MESSAGE, university.getName() + " University has saved successfully !");
			} else {
				// save error
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : university has not saved successfully !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : university has not saved successfully !");
		}
		return "admin/add_university";
	}

	@PutMapping
	public String update(@ModelAttribute UniversityDTO universityDto, Model model) {
		System.out.println(universityDto);
		try {
			// save to database
			University updatedUniversity = this.universityService
					.update(this.universityService.getUniversityFromDTO(universityDto));
			if (updatedUniversity != null && universityDto.getId() == updatedUniversity.getId()) {
				// save successful
				model.addAttribute(CommonUtil.SUCCESS_MESSAGE, updatedUniversity.getName() + " University has updated successfully !");
			} else {
				// save error
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : university has not updated successfully !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : university has not updated successfully !");
		}
		return "admin/update_university";
	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		System.out.println(id);
		try {
			Optional<University> optionalUni = this.universityService.findById(id);
			if (optionalUni.isPresent()) {
				// Valid university id
				if (this.universityService.delete(id)) {
					// delete successful
					model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
							optionalUni.get().getName() + " University has deleted successfully !");
					model.addAttribute("universityList", this.universityService.findAll());
				} else {
					// save error
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : university has not deleted. Try again!");
				}
			} else {
				// Invalid University id
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "University not found! ID:" + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : university has not updated successfully !");
		}
		return "admin/view_universities";
	}

	@GetMapping(value = "/{id}/degrees")
	public String getDegreesByUniversityId(@PathVariable("id") int id, Model model) {
		System.out.println("id: " + id);
		try {
			List<Degree> degreeList = this.degreeService.findByUniversityId(id);
			degreeList.forEach(System.out::println);

			if (!degreeList.isEmpty()) {
				model.addAttribute("degreeList", degreeList);
			} else {
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "No degree found !!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}
		return "index";
	}

	/**
	 * For UI
	 */
	@GetMapping(value = "/add")
	public String addUniversityUI(Model model) {
		model.addAttribute("university", new University());
		return "admin/add_university";
	}
	
	@GetMapping(value = "/update/{id}")
	public String updateUniversityUI(@PathVariable("id") int id, Model model) {
		System.out.println(this.universityService.findById(id).get());
		model.addAttribute("university", this.universityService.findById(id).get());
		return "admin/update_university";
	}
}
