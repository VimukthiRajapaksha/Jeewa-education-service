/**
 * 
 */
package com.graymatter.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author
 *
 */
public interface DegreeRepository extends CrudRepository<Degree, Integer> {

	@Query("FROM Degree D WHERE LOWER(D.name) LIKE LOWER(concat('%', ?1, '%'))")
	public List<Degree> findByName(String name);

	public List<Degree> findByUniversityId(int uniId);

}
