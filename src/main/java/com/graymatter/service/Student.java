/**
 * 
 */
package com.graymatter.service;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vimukthi_r
 *
 */
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	University university;

	@ManyToMany
	List<Event> appliedEvents;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the appliedEvents
	 */
	public List<Event> getAppliedEvents() {
		return appliedEvents;
	}

	/**
	 * @param appliedEvents the appliedEvents to set
	 */
	public void setAppliedEvents(List<Event> appliedEvents) {
		this.appliedEvents = appliedEvents;
	}

	/**
	 * @return the university
	 */
	public University getUniversity() {
		return university;
	}

	/**
	 * @param university the university to set
	 */
	public void setUniversity(University university) {
		this.university = university;
	}

}
