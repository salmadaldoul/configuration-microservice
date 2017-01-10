package com.madar.tracking.configuration.web.rest;

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.ATTACHMENT_CLASS_NAME;

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

import com.madar.tracking.configuration.domain.Attachment;
import com.madar.tracking.configuration.domain.City;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.AttachmentRepository;
import com.madar.tracking.configuration.services.AttachmentService;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
import com.madar.tracking.configuration.web.rest.util.HeaderUtil;

/**
 * @author Salma
 *
 */

/**
 * REST controller for managing Attachment.
 */
@RestController
@RequestMapping("/v1")
public class AttachmentResource {
	private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

	@Inject
	private AttachmentRepository attachmentRepository;

	@Inject
	private AttachmentService attachmentService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /attachments : Create a new attachment.
	 *
	 * @param attachment
	 *            the attachment to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new attachment, or with status 400 (Bad Request) if the
	 *         attachment has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/attachments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) throws URISyntaxException {
		log.debug("REST request to save Attachment : {}", attachment);
		if (attachment.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("attachment", "idexists",
					"A new attachment cannot already have an ID")).body(null);
		}

		PlatformConfiguration platformConfigurationAttachment = platformConfigurationService
				.addOneToLastSystemId(ATTACHMENT_CLASS_NAME);
		System.err.println(platformConfigurationAttachment);
		attachment.setDateCreated(System.currentTimeMillis());
		attachment
				.setAttachmentSystemId(ATTACHMENT_CLASS_NAME + platformConfigurationAttachment.getLastSystemClassId());

		Attachment result = attachmentRepository.save(attachment);

		return ResponseEntity.created(new URI("/v1/attachments/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("attachment", result.getId().toString())).body(result);
	}

	/**
	 * PUT /attachments : Updates an existing attachment.
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
	@RequestMapping(value = "/attachments", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Attachment> updateAttachment(@RequestBody Attachment attachment) throws URISyntaxException {
		log.debug("REST request to update Attachment : {}", attachment);
		if (attachment.getId() == null) {
			return createAttachment(attachment);
		}
		Attachment result = attachmentRepository.save(attachment);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("attachment", attachment.getId().toString())).body(result);
	}

	/**
	 * GET /attachments/ : get all the attachments
	 * 
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         attachments in body
	 */
	@RequestMapping(value = "/attachments/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Attachment> getAllAttachments() {
		log.debug("REST request to get all Attachments");
		List<Attachment> attachments = attachmentRepository.findAll();
		return attachments;
	}

	/**
	 * GET /attachmentsByOrganisationId/:organisationId : get all the attachments by
	 * organisationId.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         attachments in body
	 */
	@RequestMapping(value = "/attachmentsByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Attachment> getAllAttachmentsByOrganisationSystemId(@PathVariable String organisationId) {
		log.debug("REST request to get all Attachments");
		List<Attachment> attachments = attachmentService.findByOrganisationSystemId(organisationId);
		return attachments;
	}

	/**
	 * GET /attachmentsByOrganisationId/:organisationId/:id : get the "id" attachment.
	 *
	 * @param id
	 *            the id of the attachment to retrieve
	 * @param organisationId
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         attachment, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/attachmentsByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attachment> getAttachment(@PathVariable String organisationId, @PathVariable String id) {
		log.debug("REST request to get Attachment : {}", id);
		Attachment attachment = attachmentService.findOneByOrganisationSystemIdAndId(organisationId, id);
		return Optional.ofNullable(attachment).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /attachments/:id : get the "id" attachment.
	 *
	 * @param id
	 *            the id of the attachment to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         attachment, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/attachments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attachment> getAttachmentByOrganisationSystemIdAndId(@PathVariable String id) {
		log.debug("REST request to get Attachment : {}", id);
		Attachment attachment = attachmentRepository.findOne(id);
		return Optional.ofNullable(attachment).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /attachments/:id : delete the "id" attachment.
	 *
	 * @param id
	 *            the id of the attachment to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/attachments/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAttachment(@PathVariable String id) {
		log.debug("REST request to delete Attachment : {}", id);
		attachmentRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attachment", id.toString())).build();
	}
	
	/**
	 * DELETE /regions/ : delete all attachment.
	 *
	 * @param organizationId
	 *            the id of the organization's attachment to delete 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/deleteAttachments/{organizationSystemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAllByOrganizationId(@PathVariable String organizationSystemId) {
		log.debug("REST request to delete all attachments for  :organizationSystemId {}", organizationSystemId);
		
		List <Attachment> attachmentsToDelete = attachmentRepository.findByOrganizationSystemId(organizationSystemId);
		if (attachmentsToDelete != null){
			for (Attachment attachment : attachmentsToDelete) {
				attachmentRepository.delete(attachment);
			}
		}
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organizationSystemId", organizationSystemId.toString())).build();
	}
	
	/**
	 * DELETE /attachments/ : delete all attachments.
	 *
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/attachments", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAll() {
		log.debug("REST request to delete all attachments ");
		attachmentRepository.deleteAll();
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attachments", "")).build();
	}

}
