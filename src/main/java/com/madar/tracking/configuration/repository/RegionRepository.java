/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.Region;

/**
 * @author Salma
 *
 */
public interface RegionRepository extends CrudRepository<Region, String> {

	List<Region> findAll();
	
	List<Region> findByOrganizationSystemId(String organizationSystemId) ;
	
//	@Query("DELETE FROM Region where OrganizationSystemId")
	void deleteByOrganizationSystemId(String organizationSystemId) ;
}

