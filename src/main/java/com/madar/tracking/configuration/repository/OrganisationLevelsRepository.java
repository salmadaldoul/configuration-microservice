/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.OrganisationLevels;

/**
 * @author Salma
 *
 */
public interface OrganisationLevelsRepository extends CrudRepository<OrganisationLevels, String> {

	List<OrganisationLevels> findAll();
}