/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.Attachment;
import com.madar.tracking.configuration.domain.Department;

/**
 * @author Salma
 *
 */
public interface DepartmentRepository extends CrudRepository<Department, String> {

	List<Department> findAll();
	
	List<Department> findByOrganizationSystemId(String organisationId);
}
