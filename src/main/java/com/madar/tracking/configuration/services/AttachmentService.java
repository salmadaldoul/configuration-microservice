package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.Attachment;
import com.madar.tracking.configuration.repository.AttachmentRepository;

/**
 * @author Salma
 *
 */

@Service
public class AttachmentService {

	@Inject
	private AttachmentRepository attachmentRepository;

	public List<Attachment> findByOrganisationSystemId(String organisationSystemId) {
		List<Attachment> attachmentsAll = attachmentRepository.findAll();
		List<Attachment> attachmentsByOrganisationSystemId = new ArrayList<>();
		for (Attachment attachment : attachmentsAll) {
			if (attachment.getOrganizationSystemId().equals(organisationSystemId)) {
				attachmentsByOrganisationSystemId.add(attachment);
			}
		}

		return attachmentsByOrganisationSystemId;
	}

	public Attachment findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<Attachment> attachmentsAll = attachmentRepository.findAll();
		Attachment attachmentByOrganisationSystemId = null;
		for (Attachment attachment : attachmentsAll) {
			if (attachment.getOrganizationSystemId().equals(organisationSystemId) && attachment.getId().equals(id)) {
				attachmentByOrganisationSystemId = attachment;
			}
			
		}

		return attachmentByOrganisationSystemId;
	}

}
