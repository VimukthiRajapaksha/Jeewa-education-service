/**
 * 
 */
package com.graymatter.service;

import java.util.List;
import java.util.Optional;

/**
 * @author
 *
 */
public interface IDegreeService {
	public Optional<Degree> findById(int id);

	public List<Degree> findByName(String name);

	public List<Degree> findByUniversityId(int uniId);

	public List<Degree> findAll();

	public Optional<Degree> add(Degree degree);

	public Optional<Degree> update(Degree degree);

	public boolean delete(int id);
}
