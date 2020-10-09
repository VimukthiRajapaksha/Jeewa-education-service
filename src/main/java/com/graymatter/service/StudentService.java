/**
 * 
 */
package com.graymatter.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vimukthi_r
 *
 */
@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	EventRepository eventRepository;

	public boolean processApplyEvent(int studentId, int eventId) {
		studentRepository.findById(studentId).ifPresent(student -> {
			if (student.getAppliedEvents() == null) {
				List<Event> events = Arrays.asList(eventRepository.findById(eventId).get());
				student.setAppliedEvents(events);
			} else {
				student.getAppliedEvents().add(eventRepository.findById(eventId).get());
			}
			studentRepository.save(student);
		});
		return true;
	}
}
