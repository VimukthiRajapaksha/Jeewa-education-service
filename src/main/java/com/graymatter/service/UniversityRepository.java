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
public interface UniversityRepository extends CrudRepository<University, Integer> {

	@Query("FROM University U WHERE LOWER(U.name) LIKE LOWER(concat('%', ?1, '%'))")
	public List<University> findByName(String name);

}
