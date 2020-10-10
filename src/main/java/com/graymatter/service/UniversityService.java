/**
 * 
 */
package com.graymatter.service;

import java.io.IOException;
import java.util.Base64;
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
public class UniversityService implements IUniversityService {

	private UniversityRepository universityRepository;
	private DegreeRepository degreeRepository;
	private EventRepository eventRepository;

	@Autowired
	public UniversityService(UniversityRepository universityRepository, DegreeRepository degreeRepository, EventRepository eventRepository) {
		this.universityRepository = universityRepository;
		this.degreeRepository = degreeRepository;
		this.eventRepository = eventRepository;
	}

	public Optional<University> findById(int id) {
		return this.universityRepository.findById(id);
	}

	public List<University> findByName(String name) {
		return this.universityRepository.findByName(name);
	}

	public List<University> findAll() {
		return StreamSupport.stream(this.universityRepository.findAll().spliterator(), true)
				.collect(Collectors.toList());

	}

	public University add(University university) {
		return this.universityRepository.save(university);
	}

	public University update(University university) {
		return add(university);
	}

	public boolean delete(int id) {
		this.degreeRepository.findByUniversityId(id)
			.forEach(deg -> this.degreeRepository.deleteById(deg.getId()));
		this.eventRepository.findByUniversityId(id)
			.forEach(event -> this.eventRepository.deleteById(event.getId()));
		
		this.universityRepository.deleteById(id);
		return true;
	}

	public List<Degree> getDegreeList(int universityId) {
		return this.degreeRepository.findByUniversityId(universityId);
	}

	public University getUniversityFromDTO(UniversityDTO universityDTO) throws IOException {
		University university = null;
		if (universityDTO != null && universityDTO.getId() != 0) {
			university = findById(universityDTO.getId()).get();
		} else {
			university = new University();			
		}
		
		//university.setId(universityDTO.getId());
		university.setName(universityDTO.getName());
		university.setCountry(universityDTO.getCountry());
		university.setDescription(universityDTO.getDescription());
		if (universityDTO.getImage() != null && !universityDTO.getImage().isEmpty()) {
			university.setImage(Base64.getEncoder().encodeToString(universityDTO.getImage().getBytes()));
		}

		return university;
	}
}
