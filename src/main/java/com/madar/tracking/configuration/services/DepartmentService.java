package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.Department;
import com.madar.tracking.configuration.repository.DepartmentRepository;

/**
 * @author Salma
 *
 */

@Service
public class DepartmentService {

	@Inject
	private DepartmentRepository departmentRepository;

	public List<Department> findByOrganisationSystemId(String organizationSystemId) {
		List<Department> departmentsAll = departmentRepository.findAll();
		List<Department> departmentsByOrganisationSystemId = new ArrayList<>();
		for (Department department : departmentsAll) {
			if (department.getOrganizationSystemId().equals(organizationSystemId)) {
				departmentsByOrganisationSystemId.add(department);
			}
		}

		return departmentsByOrganisationSystemId;
	}

	public Department findOneByOrganisationSystemIdAndId(String organizationSystemId, String id) {
		List<Department> departmentsAll = departmentRepository.findAll();
		Department departmentByOrganisationSystemId = null;
		for (Department department : departmentsAll) {
			if (department.getOrganizationSystemId().equals(organizationSystemId) && department.getId().equals(id)) {
				departmentByOrganisationSystemId = department;
			}

		}

		return departmentByOrganisationSystemId;
	}

}
