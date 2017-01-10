package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.OrganisationLevels;
import com.madar.tracking.configuration.repository.OrganisationLevelsRepository;

/**
 * @author Salma
 *
 */

@Service
public class OrganisationLevelsService {

	@Inject
	private OrganisationLevelsRepository organisationLevelsRepository;

	public List<OrganisationLevels> findByOrganisationSystemId(String organisationSystemId) {
		List<OrganisationLevels> organisationLevelssAll = organisationLevelsRepository.findAll();
		List<OrganisationLevels> organisationLevelssByOrganisationSystemId = new ArrayList<>();
		for (OrganisationLevels organisationLevels : organisationLevelssAll) {
			if (organisationLevels.getOrganisationSystemId().equals(organisationSystemId)) {
				organisationLevelssByOrganisationSystemId.add(organisationLevels);
			}
		}

		return organisationLevelssByOrganisationSystemId;
	}

	public OrganisationLevels findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<OrganisationLevels> organisationLevelssAll = organisationLevelsRepository.findAll();
		OrganisationLevels organisationLevelsByOrganisationSystemId = null;
		for (OrganisationLevels organisationLevels : organisationLevelssAll) {
			if (organisationLevels.getOrganisationSystemId().equals(organisationSystemId) && organisationLevels.getId().equals(id)) {
				organisationLevelsByOrganisationSystemId = organisationLevels;
			}

		}

		return organisationLevelsByOrganisationSystemId;
	}

}
