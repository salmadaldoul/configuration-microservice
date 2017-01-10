/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.PointOfInterst;

/**
 * @author Salma
 *
 */
public interface PointOfInterstRepository extends CrudRepository<PointOfInterst, String> {

	List<PointOfInterst> findAll();
}
