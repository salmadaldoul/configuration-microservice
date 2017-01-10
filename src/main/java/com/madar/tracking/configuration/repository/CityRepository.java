/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.City;

/**
 * @author Salma
 *
 */
public interface CityRepository extends CrudRepository<City, String> {

	public List<City> findAll();
	
	List<City> findByOrganizationSystemId(String organisationId);
}



