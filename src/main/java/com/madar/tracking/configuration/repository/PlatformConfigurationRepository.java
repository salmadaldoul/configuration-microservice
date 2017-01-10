/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;

import com.madar.tracking.configuration.domain.PlatformConfiguration;

/**
 * @author Salma
 *
 */

public interface PlatformConfigurationRepository extends CrudRepository<PlatformConfiguration, String> {

	List<PlatformConfiguration> findAll();
	
	PlatformConfiguration findByClassName(String className);
	
//	@Modifying
//	@Query("UPDATE updatePlatformConfiguration c SET c.lastSystemClassId = :lastSystemClassId WHERE c.id = :id")
//	int updatePlatformConfiguration(@Param("id") String id, @Param("lastSystemClassId") Integer lastSystemClassId);


}
