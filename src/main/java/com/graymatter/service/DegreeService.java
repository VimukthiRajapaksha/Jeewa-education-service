/**
 * 
 */
package com.graymatter.service;

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
public class DegreeService implements IDegreeService {

	private DegreeRepository degreeRepository;
	private UniversityRepository universityRepository;

	@Autowired
	public DegreeService(DegreeRepository degreeRepository, UniversityRepository universityRepository) {
		this.degreeRepository = degreeRepository;
		this.universityRepository = universityRepository;
	}

	@Override
	public Optional<Degree> findById(int id) {
		return this.degreeRepository.findById(id);
	}

	@Override
	public List<Degree> findByName(String name) {
		return this.degreeRepository.findByName(name);
	}

	@Override
	public List<Degree> findByUniversityId(int uniId) {
		return this.degreeRepository.findByUniversityId(uniId);
	}

	@Override
	public List<Degree> findAll() {
		return StreamSupport.stream(this.degreeRepository.findAll().spliterator(), true).collect(Collectors.toList());

	}

	@Override
	public Optional<Degree> add(Degree degree) {
		Optional<University> optionalUni = this.universityRepository.findById(degree.getUniversity().getId());
		if (optionalUni.isPresent()) {
			degree.setUniversity(optionalUni.get());
			return Optional.ofNullable(this.degreeRepository.save(degree));
		} else {
			System.err.println("University not found!! ID: " + degree.getUniversity().getId());
			return Optional.empty();
		}
	}

	@Override
	public Optional<Degree> update(Degree degree) {
		return add(degree);
	}

	@Override
	public boolean delete(int id) {
		this.degreeRepository.deleteById(id);
		return true;
	}
}
