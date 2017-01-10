package com.madar.tracking.configuration.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.Device;
import com.madar.tracking.configuration.repository.DeviceRepository;

/**
 * @author Salma
 *
 */

@Service
public class DeviceService {

	@Inject
	private DeviceRepository deviceRepository;

	public List<Device> findByOrganisationSystemId(String organisationSystemId) {
		List<Device> devicesAll = deviceRepository.findAll();
		List<Device> devicesByOrganisationSystemId = new ArrayList<>();
		for (Device device : devicesAll) {
			if (device.getOrganisationSystemId().equals(organisationSystemId)) {
				devicesByOrganisationSystemId.add(device);
			}
		}

		return devicesByOrganisationSystemId;
	}

	public Device findOneByOrganisationSystemIdAndId(String organisationSystemId, String id) {
		List<Device> devicesAll = deviceRepository.findAll();
		Device deviceByOrganisationSystemId = null;
		for (Device device : devicesAll) {
			if (device.getOrganisationSystemId().equals(organisationSystemId) && device.getId().equals(id)) {
				deviceByOrganisationSystemId = device;
			}

		}

		return deviceByOrganisationSystemId;
	}

}
