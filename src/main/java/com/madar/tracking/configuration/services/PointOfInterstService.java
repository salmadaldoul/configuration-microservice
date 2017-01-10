package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.PointOfInterst;
import com.madar.tracking.configuration.repository.PointOfInterstRepository;

/**
 * @author Salma
 *
 */

@Service
public class PointOfInterstService {

	@Inject
	private PointOfInterstRepository pointOfInterstRepository;

	public List<PointOfInterst> findByOrganisationSystemId(String organisationSystemId) {
		List<PointOfInterst> pointOfInterstsAll = pointOfInterstRepository.findAll();
		List<PointOfInterst> pointOfInterstsByOrganisationSystemId = new ArrayList<>();
		for (PointOfInterst pointOfInterst : pointOfInterstsAll) {
			if (pointOfInterst.getOrganisationSystemId().equals(organisationSystemId)) {
				pointOfInterstsByOrganisationSystemId.add(pointOfInterst);
			}
		}

		return pointOfInterstsByOrganisationSystemId;
	}

	public PointOfInterst findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<PointOfInterst> pointOfInterstsAll = pointOfInterstRepository.findAll();
		PointOfInterst pointOfInterstByOrganisationSystemId = null;
		for (PointOfInterst pointOfInterst : pointOfInterstsAll) {
			if (pointOfInterst.getOrganisationSystemId().equals(organisationSystemId)
					&& pointOfInterst.getId().equals(id)) {
				pointOfInterstByOrganisationSystemId = pointOfInterst;
			}

		}

		return pointOfInterstByOrganisationSystemId;
	}
}
