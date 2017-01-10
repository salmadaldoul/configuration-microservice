package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.domain.PointOfInterst;

import com.madar.tracking.configuration.repository.PointOfInterstRepository;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
import com.madar.tracking.configuration.services.PointOfInterstService;
import com.madar.tracking.configuration.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.POINT_OF_INTERST_CLASS_NAME;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma
 *
 */

/**
 * REST controller for managing PointOfInterst.
 */
@RestController
@RequestMapping("/v1")
public class PointOfInterstResource {

	private final Logger log = LoggerFactory.getLogger(PointOfInterstResource.class);

	@Inject
	private PointOfInterstRepository pointOfInterstRepository;

	@Inject
	private PointOfInterstService pointOfInterstService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /point-of-intersts : Create a new pointOfInterst.
	 *
	 * @param pointOfInterst
	 *            the pointOfInterst to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new pointOfInterst, or with status 400 (Bad Request) if the
	 *         pointOfInterst has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/point-of-intersts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<PointOfInterst> createPointOfInterst(@RequestBody PointOfInterst pointOfInterst)
			throws URISyntaxException {
		log.debug("REST request to save PointOfInterst : {}", pointOfInterst);
		if (pointOfInterst.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pointOfInterst", "idexists",
					"A new pointOfInterst cannot already have an ID")).body(null);
		}

		PlatformConfiguration platformConfigurationPoi = platformConfigurationService
				.addOneToLastSystemId(POINT_OF_INTERST_CLASS_NAME);
		System.err.println(platformConfigurationPoi);
		pointOfInterst.setDateCreated(System.currentTimeMillis());
		pointOfInterst.setPoiSystemId(POINT_OF_INTERST_CLASS_NAME + platformConfigurationPoi.getLastSystemClassId());

		PointOfInterst result = pointOfInterstRepository.save(pointOfInterst);
		return ResponseEntity.created(new URI("/v1/point-of-intersts/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("pointOfInterst", result.getId().toString()))
				.body(result);
	}

	/**
	 * PUT /point-of-intersts : Updates an existing pointOfInterst.
	 *
	 * @param pointOfInterst
	 *            the pointOfInterst to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         pointOfInterst, or with status 400 (Bad Request) if the
	 *         pointOfInterst is not valid, or with status 500 (Internal Server
	 *         Error) if the pointOfInterst couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/point-of-intersts", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<PointOfInterst> updatePointOfInterst(@RequestBody PointOfInterst pointOfInterst)
			throws URISyntaxException {
		log.debug("REST request to update PointOfInterst : {}", pointOfInterst);
		if (pointOfInterst.getId() == null) {
			return createPointOfInterst(pointOfInterst);
		}
		PointOfInterst result = pointOfInterstRepository.save(pointOfInterst);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("pointOfInterst", pointOfInterst.getId().toString()))
				.body(result);
	}

	/**
	 * GET /point-of-intersts : get all the pointOfIntersts.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         pointOfIntersts in body
	 */
	@RequestMapping(value = "/point-of-intersts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<PointOfInterst> getAllPointOfIntersts() {
		log.debug("REST request to get all PointOfIntersts");
		List<PointOfInterst> pointOfIntersts = pointOfInterstRepository.findAll();
		return pointOfIntersts;
	}

	/**
	 * GET /point-of-interstsByOrganisationId : get all the pointOfIntersts by organisationId.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         pointOfIntersts in body
	 */
	@RequestMapping(value = "/point-of-interstsByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<PointOfInterst> getAllPointOfIntersts(@PathVariable String organisationId) {
		log.debug("REST request to get all PointOfIntersts");
		List<PointOfInterst> pointOfIntersts = pointOfInterstService.findByOrganisationSystemId(organisationId);
		return pointOfIntersts;
	}

	/**
	 * GET /point-of-intersts/:id : get the "id" pointOfInterst.
	 *
	 * @param id
	 *            the id of the pointOfInterst to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         pointOfInterst, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/point-of-intersts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<PointOfInterst> getPointOfInterst(@PathVariable String id) {
		log.debug("REST request to get PointOfInterst : {}", id);
		PointOfInterst pointOfInterst = pointOfInterstRepository.findOne(id);
		return Optional.ofNullable(pointOfInterst).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /point-of-interstsByOrganisationId/:organisationId/:id : get the "id" pointOfInterst.
	 *
	 * @param id
	 *            the id of the pointOfInterst to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         pointOfInterst, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/point-of-interstsByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PointOfInterst> getPointOfInterstByOrganisationId(@PathVariable String organisationId,
			@PathVariable String id) {
		log.debug("REST request to get PointOfInterst : {}", id);
		PointOfInterst pointOfInterst = pointOfInterstService.findOneByOrganisationSystemIdAndId(organisationId, id);
		return Optional.ofNullable(pointOfInterst).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /point-of-intersts/:id : delete the "id" pointOfInterst.
	 *
	 * @param id
	 *            the id of the pointOfInterst to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/point-of-intersts/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Void> deletePointOfInterst(@PathVariable String id) {
		log.debug("REST request to delete PointOfInterst : {}", id);
		pointOfInterstRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pointOfInterst", id.toString()))
				.build();
	}

}
