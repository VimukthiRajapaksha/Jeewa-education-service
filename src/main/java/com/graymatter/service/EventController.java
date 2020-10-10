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
@RequestMapping(value = "/events")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventController {

	private EventService eventService;
	private UniversityService universityService;

	public EventController(EventService eventService, UniversityService universityService) {
		this.eventService = eventService;
		this.universityService = universityService;
	}

	@GetMapping
	public String getAll(@RequestParam(required = false) String name, @RequestParam(required = false) Integer uniId,
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

		return "admin/view_events";
	}

	@GetMapping(value = "/{id}")
	public String getById(@PathVariable("id") int id, Model model) {
		System.out.println("id: " + id);
		try {
			System.out.println("result: " + this.eventService.findById(id));
			model.addAttribute("event",
					this.eventService.findById(id).orElseThrow(() -> new Exception("Event not found! ID:" + id)));
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, e.getMessage());
		}
		return "index";
	}

	@PostMapping
	public String add(@ModelAttribute Event event, Model model) {
		System.out.println(event);
		try {
			// save to database
			Optional<Event> optionalEvent = this.eventService.add(event);
			if (optionalEvent.isPresent()) {
				// save successful
				model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
						optionalEvent.get().getName() + " event has saved successfully !");
				model.addAttribute("event_id", optionalEvent.get().getId());
			} else {
				// save error
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : event has not saved successfully !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : event has not saved successfully !");
		}
		return "admin/add_event";
	}

	@PutMapping
	public String update(@ModelAttribute Event event, Model model) {
		System.out.println(event);
		try {
			// save to database
			Optional<Event> optionalEvent = this.eventService.update(event);
			if (optionalEvent.isPresent()) {
				// save successful
				model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
						optionalEvent.get().getName() + " event has updated successfully !");
				model.addAttribute("event",optionalEvent.get());
			} else {
				// save error
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : event has not updated successfully !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : event has not updated successfully !");
		}
		return "admin/update_event";
	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		System.out.println(id);
		try {
			Optional<Event> optionalEvent = this.eventService.findById(id);
			if (optionalEvent.isPresent()) {
				// Valid university id
				if (this.eventService.delete(id)) {
					// delete successful
					model.addAttribute(CommonUtil.SUCCESS_MESSAGE,
							optionalEvent.get().getName() + " event has deleted successfully !");
					model.addAttribute("eventList", this.eventService.findAll());
				} else {
					// save error
					model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : event has not deleted. Try again!");
				}
			} else {
				// Invalid University id
				model.addAttribute(CommonUtil.ERROR_MESSAGE, "event not found! ID:" + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "ERROR : event has not deleted successfully !");
		}
		return "admin/view_events";
	}

	/**
	 * For UI
	 */
	@GetMapping(value = "/add")
	public String addEventUI(Model model) {
		System.out.println(this.universityService.findAll());
		model.addAttribute("universityList", this.universityService.findAll());
		model.addAttribute("event", new Event());

		return "admin/add_event";
	}

	@GetMapping(value = "/update/{id}")
	public String updateEventUI(@PathVariable(value = "id", required = false) int id, Model model) {
		Optional<Event> optionalEvent = this.eventService.findById(id);
		if (optionalEvent.isPresent()) {
			// Valid university id
			model.addAttribute("event", optionalEvent.get());
			model.addAttribute("universityList", this.universityService.findAll());
		} else {
			// Invalid University id
			model.addAttribute(CommonUtil.ERROR_MESSAGE, "Degree not found! Invalid ID:" + id);
		}

		return "admin/update_event";
	}
}
