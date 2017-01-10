package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.Region;
import com.madar.tracking.configuration.repository.RegionRepository;

/**
 * @author Salma
 *
 */

@Service
public class RegionService {

	@Inject
	private RegionRepository regionRepository;

	public List<Region> findByOrganisationSystemId(String organizationSystemId) {
		List<Region> regionsAll = regionRepository.findAll();
		List<Region> regionsByOrganisationSystemId = new ArrayList<>();
		for (Region region : regionsAll) {
			if (region.getOrganizationSystemId().equals(organizationSystemId)) {
				regionsByOrganisationSystemId.add(region);
			}
		}

		return regionsByOrganisationSystemId;
	}

	public Region findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<Region> regionsAll = regionRepository.findAll();
		Region regionByOrganizationSystemId = null;
		for (Region region : regionsAll) {
			if (region.getOrganizationSystemId().equals(organisationSystemId) && region.getId().equals(id)) {
				regionByOrganizationSystemId = region;
			}

		}

		return regionByOrganizationSystemId;
	}

}
