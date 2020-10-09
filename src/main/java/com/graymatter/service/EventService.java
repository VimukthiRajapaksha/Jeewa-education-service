/**
 * 
 */
package com.graymatter.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 *
 */
@Service
public class EventService implements IEventService {

	private EventRepository eventRepository;
	private UniversityRepository universityRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	public EventService(EventRepository eventRepository, UniversityRepository universityRepository) {
		this.eventRepository = eventRepository;
		this.universityRepository = universityRepository;
	}

	public Optional<Event> findById(int id) {
		return this.eventRepository.findById(id);
	}

	public List<Event> findByName(String name) {
		return this.eventRepository.findByName(name);
	}

	public List<Event> findByUniversityId(int uniId) {
		return this.eventRepository.findByUniversityId(uniId);
	}

	public List<Event> getLatestEventsList(int uniId, int studentId) {
		LocalDate today = LocalDate.now();
		LocalDate weekToday = today.plusWeeks(1);
		
		return this.eventRepository.findByUniversityId(uniId)
				.stream()
				// Check already registered event or not
				.filter(newEvent -> !studentRepository.findById(studentId).get().getAppliedEvents().contains(newEvent))
				// It's within the next 7 days
				.filter(newEvent -> (today.compareTo(newEvent.getStartDate()) <= 0 && newEvent.getStartDate().compareTo(weekToday) < 0))
				.collect(Collectors.toList());
	}
	
	public List<Event> findAll() {
		return StreamSupport
				.stream(this.eventRepository.findAll().spliterator(), true)
				.collect(Collectors.toList());

	}

	public Optional<Event> add(Event event) {
		Optional<University> optionalUni = this.universityRepository.findById(event.getUniversity().getId());
		if (optionalUni.isPresent()) {
			event.setUniversity(optionalUni.get());
			return Optional.ofNullable(this.eventRepository.save(event));
		} else {
			System.err.println("Event not found!! ID: " + event.getUniversity().getId());
			return Optional.empty();
		}
	}

	public Optional<Event> update(Event event) {
		return add(event);
	}

	public boolean delete(int id) {
		this.eventRepository.deleteById(id);
		return true;
	}
}
