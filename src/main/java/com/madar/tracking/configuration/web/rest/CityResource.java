package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.domain.City;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.domain.Region;
import com.madar.tracking.configuration.domain.DTO.CityIdNameDto;
import com.madar.tracking.configuration.domain.DTO.RegionIdNameDto;
import com.madar.tracking.configuration.repository.CityRepository;
import com.madar.tracking.configuration.repository.PlatformConfigurationRepository;
import com.madar.tracking.configuration.services.CityService;
import com.madar.tracking.configuration.services.PlatformConfigurationService;
import com.madar.tracking.configuration.web.rest.util.HeaderUtil;
import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.CITY_CLASS_NAME;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Salma
 *
 */

/**
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/v1")
public class CityResource {

	private final Logger log = LoggerFactory.getLogger(CityResource.class);

	@Inject
	private CityRepository cityRepository;

	@Inject
	private CityService cityService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /cities : Create a new city.
	 *
	 * @param city
	 *            the city to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new city, or with status 400 (Bad Request) if the city has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<City> createCity(@RequestBody City city) throws URISyntaxException {
		log.debug("REST request to save City : {}", city);
		if (city.getId() != null) {
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert("city", "idexists", "A new city cannot already have an ID"))
					.body(null);
		}

		PlatformConfiguration platformConfigurationCity = platformConfigurationService.addOneToLastSystemId(CITY_CLASS_NAME);
		System.err.println(platformConfigurationCity);
		city.setDateCreated(System.currentTimeMillis());
		city.setCitySystemId(CITY_CLASS_NAME + platformConfigurationCity.getLastSystemClassId());

		City result = cityRepository.save(city);

		return ResponseEntity.created(new URI("/v1/cities/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("city", result.getId().toString())).body(result);
	}

	/**
	 * PUT /cities : Updates an existing city.
	 *
	 * @param city
	 *            the city to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         city, or with status 400 (Bad Request) if the city is not valid,
	 *         or with status 500 (Internal Server Error) if the city couldnt be
	 *         updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<City> updateCity(@RequestBody City city) throws URISyntaxException {
		log.debug("REST request to update City : {}", city);
		if (city.getId() == null) {
			return createCity(city);
		}
		City result = cityRepository.save(city);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("city", city.getId().toString()))
				.body(result);
	}

	/**
	 * GET /cities : get all the cities.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of cities in
	 *         body
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<City> getAllCities() {
		log.debug("REST request to get all Cities");
		List<City> cities = cityRepository.findAll();
		return cities;
	}

	/**
	 * GET /citiesByOrganisationId/ :organisationId : get all the cities by organisationId.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of cities in
	 *         body
	 */
	@RequestMapping(value = "/citiesByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<City> getAllCitiesByOrganisationId(@PathVariable String organisationId) {
		log.debug("REST request to get all Cities");
		List<City> cities = cityRepository.findByOrganizationSystemId(organisationId);
		return cities;
	}

	/**
	 * GET /cities : get all the Id Name cities.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of cities Id
	 *         Name in body
	 */
	@RequestMapping(value = "/citiesIdName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public List<CityIdNameDto> getAllCitiesIdName() {
		log.debug("REST request to get all Cities Id & Name");
		List<City> cities = cityRepository.findAll();
		List<CityIdNameDto> cityIdNameDtos = new ArrayList<>();
		for (City city : cities) {
			CityIdNameDto cityIdNameDto = new CityIdNameDto();
			cityIdNameDto.setCityName(city.getRegionName());
			cityIdNameDto.setCitySystemId(city.getRegionSystemId());
			cityIdNameDtos.add(cityIdNameDto);

		}
		return cityIdNameDtos;
	}

	/**
	 * GET /cities/:id : get the "id" city.
	 *
	 * @param id
	 *            the id of the city to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the city,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/cities/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<City> getCity(@PathVariable String id) {
		log.debug("REST request to get City : {}", id);
		City city = cityRepository.findOne(id);
		return Optional.ofNullable(city).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /citiesByOrganisationId/ :organisationId/ :id : get all the cities by organisationId
	 * & id.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of cities in
	 *         body
	 */
	@RequestMapping(value = "/citiesByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	public City getAllCitiesByOrganisationId(@PathVariable String organisationId, @PathVariable String id) {
		log.debug("REST request to get all Cities");
		City citie = cityService.findOneByOrganisationSystemIdAndId(organisationId, id);
		return citie;
	}

	/**
	 * DELETE /cities/:id : delete the "id" city.
	 *
	 * @param id
	 *            the id of the city to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/cities/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<Void> deleteCity(@PathVariable String id) {
		log.debug("REST request to delete City : {}", id);
		cityRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("city", id.toString())).build();
	}
	
	/**
	 * DELETE /regions/ : delete all cities.
	 *
	 * @param organizationId
	 *            the id of the organization's region to delete 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/deleteCities/{organizationSystemId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAllByOrganizationId(@PathVariable String organizationSystemId) {
		log.debug("REST request to delete all cities for  :organizationSystemId {}", organizationSystemId);
		
		List <City> citiesToDelete = cityRepository.findByOrganizationSystemId(organizationSystemId);
		if (citiesToDelete != null){
			for (City city : citiesToDelete) {
				cityRepository.delete(city);
			}
		}
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("organizationSystemId", organizationSystemId.toString())).build();
	}
	
	/**
	 * DELETE /cities/: delete all cities.
	 *
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/cities", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteAll() {
		log.debug("REST request to delete all cities ");
		cityRepository.deleteAll();
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cities", "")).build();
	}

}
