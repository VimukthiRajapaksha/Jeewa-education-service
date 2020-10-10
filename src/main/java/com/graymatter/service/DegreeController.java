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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 *
 */

@Controller
@RequestMapping(value = "/degrees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DegreeController {

	private DegreeService degreeService;
	private UniversityService universityService;

	public DegreeController(DegreeService degreeService, UniversityService universityService) {
		this.degreeService = degreeService;
		this.universityService = universityService;
	}

	@GetMapping
	public String getAll(@RequestParam(required = false) String name, @RequestParam(required = false) Integer uniId,
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

		return "admin/view_degrees";
	}

	@GetMapping(value = "/{id}")
	public String getById(@PathVariable("id") int id, Model model) {
		System.out.println("id: " + id);
		try {
			System.out.println("result: " + this.degreeService.findById(id));
			model.addAttribute("degree",
					this.degreeService.findById(id).orElseThrow(() -> new Exception("Degree not found! ID:" + id)));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}
		return "index";
	}

	@PostMapping
	public String add(@ModelAttribute Degree degree, Model model) {
		System.out.println(degree);
		try {
			// save to database
			Optional<Degree> optionalDegree = this.degreeService.add(degree);
			if (optionalDegree.isPresent()) {
				// save successful
				model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
						optionalDegree.get().getName() + " degree has saved successfully !");
				model.addAttribute("degree_id", optionalDegree.get().getId());
			} else {
				// save error
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : degree has not saved successfully !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : degree has not saved successfully !");
		}
		return "admin/add_degree";
	}

	@PostMapping(value = "/update")
	public String update(@ModelAttribute Degree degree, Model model) {
		System.out.println(degree);
		try {
			// save to database
			Optional<Degree> optionalDegree = this.degreeService.update(degree);
			if (optionalDegree.isPresent()) {
				// save successful
				model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
						optionalDegree.get().getName() + " degree has updated successfully !");
				model.addAttribute("degree", optionalDegree.get());
			} else {
				// save error
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : degree has not updated successfully !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : degree has not updated successfully !");
		}
		return "admin/update_degree";
	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		System.out.println(id);
		try {
			Optional<Degree> optionalDegree = this.degreeService.findById(id);
			if (optionalDegree.isPresent()) {
				// Valid university id
				if (this.degreeService.delete(id)) {
					// delete successful
					model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
							optionalDegree.get().getName() + " University has deleted successfully !");
					model.addAttribute("degreeList", this.degreeService.findAll());
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
		return "admin/view_degrees";
	}

	/**
	 * For UI
	 */
	@GetMapping(value = "/add")
	public String addDegreeUI(Model model) {
		System.out.println(this.universityService.findAll());
		model.addAttribute("universityList", this.universityService.findAll());
		model.addAttribute("degree", new Degree());

		return "admin/add_degree";
	}

	@GetMapping(value = "/update/{id}")
	public String updateDegreeUI(@PathVariable(value = "id", required = false) int id, Model model) {
		Optional<Degree> optionalDegree = this.degreeService.findById(id);
		if (optionalDegree.isPresent()) {
			// Valid university id
			model.addAttribute("degree", optionalDegree.get());
			model.addAttribute("universityList", this.universityService.findAll());
		} else {
			// Invalid University id
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "Degree not found! Invalid ID:" + id);
		}

		return "admin/update_degree";
	}
}
