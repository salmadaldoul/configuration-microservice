package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.DeviceAttachmentAssignment;

/**
 * @author Salma
 *
 */
/**
 * The Device Attachment Assignment Repository.
 */
public interface DeviceAttachmentAssignmentRepository extends CrudRepository<DeviceAttachmentAssignment, String> {

	public List<DeviceAttachmentAssignment> findAll();
}