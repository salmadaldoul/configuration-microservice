package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.domain.City;
import com.madar.tracking.configuration.domain.Department;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.DepartmentRepository;
import com.madar.tracking.configuration.services.DepartmentService;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
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

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.DEPARTEMENT_CLASS_NAME;

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
 * REST controller for managing Departement.
 */
@RestController
@RequestMapping("/v1")
public class DepartmentResource {

	private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

	@Inject
	private DepartmentRepository departmentRepository;

	@Inject
	private DepartmentService departmentService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /departements : Create a new departement.
	 *
	 * @param departement
	 *            the departement to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new departement, or with status 400 (Bad Request) if the
	 *         departement has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/departements", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Department> createDepartement(@RequestBody Department departement) throws URISyntaxException {
		log.debug("REST request to save Departement : {}", departement);
		if (departement.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("departement", "idexists",
					"A new departement cannot already have an ID")).body(null);
		}

		PlatformConfiguration platformConfigurationDepartement = platformConfigurationService
				.addOneToLastSystemId(DEPARTEMENT_CLASS_NAME);
		System.err.println(platformConfigurationDepartement);
		departement.setDateCreated(LocalDate.now(ZoneId.systemDefault()));
		departement.setCitySystemId(DEPARTEMENT_CLASS_NAME + platformConfigurationDepartement.getLastSystemClassId());

		Department result = departmentRepository.save(departement);
		return ResponseEntity.created(new URI("/v1/departements/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("departement", result.getId().toString())).body(result);
	}

	/**
	 * PUT /departements : Updates an existing departement.
	 *
	 * @param departement
	 *            the departement to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         departement, or with status 400 (Bad Request) if the departement
	 *         is not valid, or with status 500 (Internal Server Error) if the
	 *         departement couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/departements", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Department> updateDepartement(@RequestBody Department departement) throws URISyntaxException {
		log.debug("REST request to update Departement : {}", departement);
		if (departement.getId() == null) {
			return createDepartement(departement);
		}
		Department result = departmentRepository.save(departement);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert("departement", departement.getId().toString()))
				.body(result);
	}

	/**
	 * GET /departements : get all the departements.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         departements in body
	 */
	@RequestMapping(value = "/departements", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<Department> getAllDepartements() {
		log.debug("REST request to get all Departements");
		List<Department> departements = departmentRepository.findAll();
		return departements;
	}

	/**
	 * GET /departementsByOrganisationId/:organisationId : get all the departements by
	 * organisationId .
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         departements in body
	 */
	@RequestMapping(value = "/departementsByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<Department> getAllDepartements(@PathVariable String organisationId) {
		log.debug("REST request to get all Departements");
		List<Department> departements = departmentService.findByOrganisationSystemId(organisationId);
		return departements;
	}

	/**
	 * GET /departements/:id : get the "id" departement.
	 *
	 * @param id
	 *            the id of the departement to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         departement, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/departements/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Department> getDepartement(@PathVariable String id) {
		log.debug("REST request to get Departement : {}", id);
		Department departement = departmentRepository.findOne(id);
		return Optional.ofNullable(departement).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /departementsByOrganisationId/:organisationId/:id : get the "id" departement.
	 *
	 * @param id
	 *            the id of the departement to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         departement, or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/departementsByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Department> getDepartementByOrganisationId(@PathVariable String organisationId,
			@PathVariable String id) {
		log.debug("REST request to get Departement : {}", id);
		Department departement = departmentService.findOneByOrganisationSystemIdAndId(organisationId, id);
		return Optional.ofNullable(departement).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /departements/:id : delete the "id" departement.
	 *
	 * @param id
	 *            the id of the departement to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/departements/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteDepartement(@PathVariable String id) {
		log.debug("REST request to delete Departement : {}", id);
		departmentRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("departement", id.toString())).build();
	}

	/**
	 * DELETE /regions/ : delete all cities.
	 *
	 * @param organizationId
	 *            the id of the organization's region to delete 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/deleteDepartment/{organizationSystemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAllByOrganizationId(@PathVariable String organizationSystemId) {
		log.debug("REST request to delete all cities for  :organizationSystemId {}", organizationSystemId);
		
		List <Department> departmentsToDelete = departmentRepository.findByOrganizationSystemId(organizationSystemId);
		if (departmentsToDelete != null){
			for (Department region : departmentsToDelete) {
				departmentRepository.delete(region);
			}
		}
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organizationSystemId", organizationSystemId.toString())).build();
	}
	
	/**
	 * DELETE /departent/: delete all department.
	 *
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAll() {
		log.debug("REST request to delete all depatments ");
		departmentRepository.deleteAll();
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cities", "")).build();
	}
}
