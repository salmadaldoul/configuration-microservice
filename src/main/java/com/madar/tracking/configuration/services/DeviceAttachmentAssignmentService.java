package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.DeviceAttachmentAssignment;
import com.madar.tracking.configuration.repository.DeviceAttachmentAssignmentRepository;

/**
 * @author Salma
 *
 */

@Service
public class DeviceAttachmentAssignmentService {

	@Inject
	private DeviceAttachmentAssignmentRepository deviceAttachmentAssignmentRepository;

	public List<DeviceAttachmentAssignment> findByOrganisationSystemId(String organisationSystemId) {
		List<DeviceAttachmentAssignment> deviceAttachmentAssignmentsAll = deviceAttachmentAssignmentRepository
				.findAll();
		List<DeviceAttachmentAssignment> deviceAttachmentAssignmentsByOrganisationSystemId = new ArrayList<>();
		for (DeviceAttachmentAssignment deviceAttachmentAssignment : deviceAttachmentAssignmentsAll) {
			if (deviceAttachmentAssignment.getOrganisationSystemId().equals(organisationSystemId)) {
				deviceAttachmentAssignmentsByOrganisationSystemId.add(deviceAttachmentAssignment);
			}
		}

		return deviceAttachmentAssignmentsByOrganisationSystemId;
	}

	public DeviceAttachmentAssignment findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<DeviceAttachmentAssignment> deviceAttachmentAssignmentsAll = deviceAttachmentAssignmentRepository
				.findAll();
		DeviceAttachmentAssignment deviceAttachmentAssignmentByOrganisationSystemId = null;
		for (DeviceAttachmentAssignment deviceAttachmentAssignment : deviceAttachmentAssignmentsAll) {
			if (deviceAttachmentAssignment.getOrganisationSystemId().equals(organisationSystemId)
					&& deviceAttachmentAssignment.getId().equals(id)) {
				deviceAttachmentAssignmentByOrganisationSystemId = deviceAttachmentAssignment;
			}

		}

		return deviceAttachmentAssignmentByOrganisationSystemId;
	}

}
