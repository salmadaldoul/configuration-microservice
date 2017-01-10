package com.madar.tracking.configuration.web.rest;

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.CITY_CLASS_NAME;
import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.REGION_CLASS_NAME;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.domain.Region;
import com.madar.tracking.configuration.domain.DTO.RegionIdNameDto;
import com.madar.tracking.configuration.repository.RegionRepository;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
import com.madar.tracking.configuration.services.RegionService;
import com.madar.tracking.configuration.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma
 *
 */

/**
 * REST controller for managing Region.
 */
@RestController
@RequestMapping("/v1")
public class RegionResource {

	private final Logger log = LoggerFactory.getLogger(RegionResource.class);

	@Inject
	private RegionRepository regionRepository;

	@Inject
	private RegionService regionService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /regions : Create a new region.
	 *
	 * @param region
	 *            the region to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new region, or with status 400 (Bad Request) if the region has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/regions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Region> createRegion(@RequestBody Region region) throws URISyntaxException {
		log.debug("REST request to save Region : {}", region);
		if (region.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("region", "idexists", "A new region cannot already have an ID"))
					.body(null);
		}
		PlatformConfiguration platformConfigurationRegion = platformConfigurationService.addOneToLastSystemId(REGION_CLASS_NAME);
		
		System.err.println(platformConfigurationRegion);
		
		System.err.println(">>>>>>getLastSystemClassId>>>>>>>>>>> "+platformConfigurationRegion.getLastSystemClassId());
		
		region.setDateCreated(System.currentTimeMillis());
		region.setRegionSystemId(REGION_CLASS_NAME + platformConfigurationRegion.getLastSystemClassId());
		Region result = regionRepository.save(region);
		return ResponseEntity.created(new URI("/v1/regions/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("region", result.getId().toString())).body(result);
	}

	/**
	 * PUT /regions : Updates an existing region.
	 *
	 * @param region
	 *            the region to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         region, or with status 400 (Bad Request) if the region is not
	 *         valid, or with status 500 (Internal Server Error) if the region
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/regions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Region> updateRegion(@RequestBody Region region) throws URISyntaxException {
		log.debug("REST request to update Region : {}", region);
		if (region.getId() == null) {
			return createRegion(region);
		}
		Region result = regionRepository.save(region);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("region", region.getId().toString()))
				.body(result);
	}

	/**
	 * GET /regions : get all the regions.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of regions
	 *         in body
	 */
	@RequestMapping(value = "/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<Region> getAllRegions() {
		log.debug("REST request to get all Regions");
		List<Region> regions = regionRepository.findAll();
		return regions;
	}

	/**
	 * GET /regionsByOrganisationId/:organisationId : get all the regions.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of regions
	 *         in body
	 */
	@RequestMapping(value = "/regionsByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<Region> getAllRegions(@PathVariable String organisationId) {
		log.debug("REST request to get all Regions for organisationId "+organisationId);
		List<Region> regions = regionRepository.findByOrganizationSystemId(organisationId);
		return regions;
	}

	/**
	 * GET /regions : get all the Id Name regions.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of regions
	 *         Id Name in body
	 */
	@RequestMapping(value = "/regionsIdName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<RegionIdNameDto> getAllRegionsIdName() {
		log.debug("REST request to get all Regions Id & Name");
		List<Region> regions = regionRepository.findAll();
		List<RegionIdNameDto> regionIdNameDtos = new ArrayList<>();
		for (Region region : regions) {
			RegionIdNameDto regionIdNameDto = new RegionIdNameDto();
			regionIdNameDto.setRegionName(region.getRegionName());
			regionIdNameDto.setRegionSystemId(region.getRegionSystemId());
			regionIdNameDtos.add(regionIdNameDto);

		}
		return regionIdNameDtos;
	}

	/**
	 * GET /regions/:id : get the "id" region.
	 *
	 * @param id
	 *            the id of the region to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the region,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/regions/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Region> getRegion(@PathVariable String id) {
		log.debug("REST request to get Region : {}", id);
		Region region = regionRepository.findOne(id);
		return Optional.ofNullable(region).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /regionsByOrganisationId/:organisationId/:id : get the "id" region.
	 *
	 * @param id
	 *            the id of the region to retrieve
	 * @param organisationId
	 * @return the ResponseEntity with status 200 (OK) and with body the region,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/regionsByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Region> getRegionByOrganisationId(@PathVariable String organisationId,
			@PathVariable String id) {
		log.debug("REST request to get Region : {}", id);
		Region region = regionService.findOneByOrganisationSystemIdAndId(organisationId, id);
		return Optional.ofNullable(region).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /regions/:id : delete the "id" region.
	 *
	 * @param id
	 *            the id of the region to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/regions/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteRegion(@PathVariable String id) {
		log.debug("REST request to delete Region : {}", id);
		regionRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("region", id.toString())).build();
	}
	
	/**
	 * DELETE /regions/ : delete all regions.
	 *
	 * @param organizationId
	 *            the id of the organization's region to delete 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/deleteRegions/{organizationSystemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAllByOrganizationId(@PathVariable String organizationSystemId) {
		log.debug("REST request to delete all Regions for  :organizationSystemId {}", organizationSystemId);
		
		List <Region> regionsToDelete = regionRepository.findByOrganizationSystemId(organizationSystemId);
		if (regionsToDelete != null){
			for (Region region : regionsToDelete) {
				regionRepository.delete(region);
			}
		}
//		regionRepository.deleteByOrganizationSystemId(organizationSystemId);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organizationSystemId", organizationSystemId.toString())).build();
	}
	
	/**
	 * DELETE /regions/ : delete all regions.
	 *
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/regions", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAll() {
		log.debug("REST request to delete all Regions ");
		regionRepository.deleteAll();
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("region", "")).build();
	}

}
