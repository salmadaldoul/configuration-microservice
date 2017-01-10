/**
 * 
 */
package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.City;
import com.madar.tracking.configuration.repository.CityRepository;

/**
 * @author Salma
 *
 */

@Service
public class CityService {

	@Inject
	private CityRepository cityRepository;

	public List<City> findByOrganisationSystemId(String organisationSystemId) {
		List<City> citysAll = cityRepository.findAll();
		List<City> citysByOrganisationSystemId = new ArrayList<>();
		for (City city : citysAll) {
			if (city.getOrganizationSystemId().equals(organisationSystemId)) {
				citysByOrganisationSystemId.add(city);
			}
		}

		return citysByOrganisationSystemId;
	}

	public City findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<City> citysAll = cityRepository.findAll();
		City cityByOrganisationSystemId = null;
		for (City city : citysAll) {
			if (city.getOrganizationSystemId().equals(organisationSystemId) && city.getId().equals(id)) {
				cityByOrganisationSystemId = city;
			}

		}

		return cityByOrganisationSystemId;
	}
}
