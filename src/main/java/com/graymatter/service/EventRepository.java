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
public interface EventRepository extends CrudRepository<Event, Integer> {

	@Query("FROM Event E WHERE LOWER(E.name) LIKE LOWER(concat('%', ?1, '%'))")
	public List<Event> findByName(String name);

	public List<Event> findByUniversityId(int uniId);

}
