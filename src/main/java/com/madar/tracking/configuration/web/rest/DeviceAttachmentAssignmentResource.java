package com.madar.tracking.configuration.web.rest;

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.DEVICE_ATTACHMENT_ASSIGNMENT_CLASS_NAME;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.madar.tracking.configuration.domain.DeviceAttachmentAssignment;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.DeviceAttachmentAssignmentRepository;
import com.madar.tracking.configuration.services.DeviceAttachmentAssignmentService;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
import com.madar.tracking.configuration.web.rest.util.HeaderUtil;

/**
 * @author Salma
 *
 */

/**
 * REST controller for managing Device DeviceAttachmentAssignment Assignment .
 */
@RestController
@RequestMapping("/v1")
public class DeviceAttachmentAssignmentResource {

	private final Logger log = LoggerFactory.getLogger(DeviceAttachmentAssignmentResource.class);

	@Inject
	private DeviceAttachmentAssignmentRepository attachmentRepository;

	@Inject
	private DeviceAttachmentAssignmentService attachmentService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /deviceAttachmentAssignments : Create a new attachment.
	 *
	 * @param attachment
	 *            the attachment to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new attachment, or with status 400 (Bad Request) if the
	 *         attachment has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/deviceAttachmentAssignments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeviceAttachmentAssignment> createDeviceAttachmentAssignment(
			@RequestBody DeviceAttachmentAssignment attachment) throws URISyntaxException {
		log.debug("REST request to save DeviceAttachmentAssignment : {}", attachment);
		if (attachment.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("attachment", "idexists",
					"A new attachment cannot already have an ID")).body(null);
		}

		PlatformConfiguration platformConfigurationDeviceAttachmentAssignment = platformConfigurationService
				.addOneToLastSystemId(DEVICE_ATTACHMENT_ASSIGNMENT_CLASS_NAME);
		System.err.println(platformConfigurationDeviceAttachmentAssignment);
		attachment.setDateCreated(System.currentTimeMillis());
		attachment.setDeviceAttachmentAssignmentSystemId(DEVICE_ATTACHMENT_ASSIGNMENT_CLASS_NAME
				+ platformConfigurationDeviceAttachmentAssignment.getLastSystemClassId());

		DeviceAttachmentAssignment result = attachmentRepository.save(attachment);

		return ResponseEntity.created(new URI("/v1/deviceAttachmentAssignments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("attachment", result.getId().toString())).body(result);
	}

	/**
	 * PUT /deviceAttachmentAssignments : Updates an existing attachment.
	 *
	 * @param attachment
	 *            the attachment to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         attachment, or with status 400 (Bad Request) if the attachment is
	 *         not valid, or with status 500 (Internal Server Error) if the
	 *         attachment couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/deviceAttachmentAssignments", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<DeviceAttachmentAssignment> updateDeviceAttachmentAssignment(
			@RequestBody DeviceAttachmentAssignment attachment) throws URISyntaxException {
		log.debug("REST request to update DeviceAttachmentAssignment : {}", attachment);
		if (attachment.getId() == null) {
			return createDeviceAttachmentAssignment(attachment);
		}
		DeviceAttachmentAssignment result = attachmentRepository.save(attachment);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("attachment", attachment.getId().toString())).body(result);
	}

	/**
	 * GET /deviceAttachmentAssignments : get all the
	 * deviceAttachmentAssignments.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         deviceAttachmentAssignments in body
	 */
	@RequestMapping(value = "/deviceAttachmentAssignments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<DeviceAttachmentAssignment> getAllDeviceAttachmentAssignments() {
		log.debug("REST request to get all DeviceAttachmentAssignments");
		List<DeviceAttachmentAssignment> deviceAttachmentAssignments = attachmentRepository.findAll();
		return deviceAttachmentAssignments;
	}

	/**
	 * GET /deviceAttachmentAssignmentsByOrganisationId/:organisationId : get all the
	 * deviceAttachmentAssignments.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         deviceAttachmentAssignments in body
	 */
	@RequestMapping(value = "/deviceAttachmentAssignmentsByOrganisationIdByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeviceAttachmentAssignment> getAllDeviceAttachmentAssignmentsByOrganisationId(
			@PathVariable String organisationId) {
		log.debug("REST request to get all DeviceAttachmentAssignments");
		List<DeviceAttachmentAssignment> deviceAttachmentAssignments = attachmentService
				.findByOrganisationSystemId(organisationId);
		return deviceAttachmentAssignments;
	}
	/**
	 * GET /deviceAttachmentAssignments/:id : get the "id" attachment.
	 *
	 * @param id
	 *            the id of the attachment to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         attachment, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/deviceAttachmentAssignments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<DeviceAttachmentAssignment> getDeviceAttachmentAssignment(@PathVariable String id) {
		log.debug("REST request to get DeviceAttachmentAssignment : {}", id);
		DeviceAttachmentAssignment attachment = attachmentRepository.findOne(id);
		return Optional.ofNullable(attachment).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /deviceAttachmentAssignmentsByOrganisationId/:id/ : get the "id" attachment.
	 *
	 * @param id
	 *            the id of the attachment to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         attachment, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/deviceAttachmentAssignmentsByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<DeviceAttachmentAssignment> getDeviceAttachmentAssignmentByOrganisationId(
			@PathVariable String organisationId, @PathVariable String id) {
		log.debug("REST request to get DeviceAttachmentAssignment : {}", id);
		DeviceAttachmentAssignment attachment = attachmentService.findOneByOrganisationSystemIdAndId(organisationId,
				id);
		return Optional.ofNullable(attachment).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /deviceAttachmentAssignments/:id : delete the "id" attachment.
	 *
	 * @param id
	 *            the id of the attachment to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/deviceAttachmentAssignments/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Void> deleteDeviceAttachmentAssignment(@PathVariable String id) {
		log.debug("REST request to delete DeviceAttachmentAssignment : {}", id);
		attachmentRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attachment", id.toString())).build();
	}

}
