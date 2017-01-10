package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.domain.OrganisationLevels;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.OrganisationLevelsRepository;
import com.madar.tracking.configuration.services.OrganisationLevelsService;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
import com.madar.tracking.configuration.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.ORGANISATION_LEVELS_CLASS_NAME;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma
 *
 */

/**
 * REST controller for managing OrganisationLevels.
 */
@RestController
@RequestMapping("/v1")
public class OrganisationLevelsResource {

	private final Logger log = LoggerFactory.getLogger(OrganisationLevelsResource.class);

	@Inject
	private OrganisationLevelsRepository organisationLevelsRepository;

	@Inject
	private OrganisationLevelsService organisationLevelsService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /organisation-levels : Create a new organisationLevels.
	 *
	 * @param organisationLevels
	 *            the organisationLevels to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new organisationLevels, or with status 400 (Bad Request) if the
	 *         organisationLevels has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/organisation-levels", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<OrganisationLevels> createOrganisationLevels(
			@RequestBody OrganisationLevels organisationLevels) throws URISyntaxException {
		log.debug("REST request to save OrganisationLevels : {}", organisationLevels);
		if (organisationLevels.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("organisationLevels", "idexists",
					"A new organisationLevels cannot already have an ID")).body(null);
		}

		PlatformConfiguration platformConfigurationOrganisationLevels = platformConfigurationService
				.addOneToLastSystemId(ORGANISATION_LEVELS_CLASS_NAME);
		System.err.println(platformConfigurationOrganisationLevels);
		organisationLevels.setOrganisationLevelsSystemId(
				ORGANISATION_LEVELS_CLASS_NAME + platformConfigurationOrganisationLevels.getLastSystemClassId());

		OrganisationLevels result = organisationLevelsRepository.save(organisationLevels);
		return ResponseEntity.created(new URI("/v1/organisation-levels/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("organisationLevels", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /organisation-levels : Updates an existing organisationLevels.
	 *
	 * @param organisationLevels
	 *            the organisationLevels to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         organisationLevels, or with status 400 (Bad Request) if the
	 *         organisationLevels is not valid, or with status 500 (Internal
	 *         Server Error) if the organisationLevels couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/organisation-levels", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<OrganisationLevels> updateOrganisationLevels(
			@RequestBody OrganisationLevels organisationLevels) throws URISyntaxException {
		log.debug("REST request to update OrganisationLevels : {}", organisationLevels);
		if (organisationLevels.getId() == null) {
			return createOrganisationLevels(organisationLevels);
		}
		OrganisationLevels result = organisationLevelsRepository.save(organisationLevels);
		return ResponseEntity.ok()
				.headers(
						HeaderUtil.createEntityUpdateAlert("organisationLevels", organisationLevels.getId().toString()))
				.body(result);
	}

	/**
	 * GET /organisation-levels : get all the organisationLevels.
	 *
	 * @param organisationId
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         organisationLevels in body
	 */
	@RequestMapping(value = "/organisation-levels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<OrganisationLevels> getAllOrganisationLevels() {
		log.debug("REST request to get all OrganisationLevels");
		List<OrganisationLevels> organisationLevels = organisationLevelsRepository.findAll();
		return organisationLevels;
	}

	/**
	 * GET /organisation-levelsByOrganisationId/:organisationId : get all the
	 * organisationLevels.
	 * 
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         organisationLevels in body
	 */
	@RequestMapping(value = "/organisation-levelsByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<OrganisationLevels> getAllOrganisationLevelsByOrganisationId(@PathVariable String organisationId) {
		log.debug("REST request to get all OrganisationLevels");
		List<OrganisationLevels> organisationLevels = organisationLevelsService
				.findByOrganisationSystemId(organisationId);
		return organisationLevels;
	}

	/**
	 * GET /organisation-levels/:id : get the "id" organisationLevels.
	 *
	 * @param id
	 *            the id of the organisationLevels to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         organisationLevels, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/organisation-levels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<OrganisationLevels> getOrganisationLevels(@PathVariable String id) {
		log.debug("REST request to get OrganisationLevels : {}", id);
		OrganisationLevels organisationLevels = organisationLevelsRepository.findOne(id);
		return Optional.ofNullable(organisationLevels).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /organisation-levelsByOrganisationId/:organisationId/:id : get the "id"
	 * organisationLevels.
	 *
	 * @param id
	 *            the id of the organisationLevels to retrieve
	 * @param organisationId
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         organisationLevels, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/organisation-levelsByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<OrganisationLevels> getOrganisationLevels(@PathVariable String organisationId,
			@PathVariable String id) {
		log.debug("REST request to get OrganisationLevels : {}", id);
		OrganisationLevels organisationLevels = organisationLevelsService
				.findOneByOrganisationSystemIdAndId(organisationId, id);
		return Optional.ofNullable(organisationLevels).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /organisation-levels/:id : delete the "id" organisationLevels.
	 *
	 * @param id
	 *            the id of the organisationLevels to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/organisation-levels/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Void> deleteOrganisationLevels(@PathVariable String id) {
		log.debug("REST request to delete OrganisationLevels : {}", id);
		organisationLevelsRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organisationLevels", id.toString()))
				.build();
	}

}
