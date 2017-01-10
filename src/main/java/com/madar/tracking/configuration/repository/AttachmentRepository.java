package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.Attachment;

/**
 * @author Salma
 *
 */
/**
 * The Attachment Repository.
 */
public interface AttachmentRepository extends CrudRepository<Attachment, String> {

	public List<Attachment> findAll();

	List<Attachment> findByOrganizationSystemId(String organisationId);
}
