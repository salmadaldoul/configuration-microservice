package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.domain.Device;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.DeviceRepository;
import com.madar.tracking.configuration.services.DeviceService;
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

import static com.madar.tracking.configuration.technicalservice.tool.ImportConstants.DEVICE_CLASS_NAME;

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
 * REST controller for managing Device.
 */
@RestController
@RequestMapping("/v1")
public class DeviceResource {

	private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

	@Inject
	private DeviceRepository deviceRepository;

	@Inject
	private DeviceService deviceService;

	@Autowired
	private PlatformConfigurationService platformConfigurationService;

	/**
	 * POST /devices : Create a new device.
	 *
	 * @param device
	 *            the device to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new device, or with status 400 (Bad Request) if the device has
	 *         already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Device> createDevice(@RequestBody Device device) throws URISyntaxException {
		log.debug("REST request to save Device : {}", device);
		if (device.getId() != null) {
			return ResponseEntity.badRequest().headers(
					HeaderUtil.createFailureAlert("device", "idexists", "A new device cannot already have an ID"))
					.body(null);
		}

		PlatformConfiguration platformConfigurationDevice = platformConfigurationService
				.addOneToLastSystemId(DEVICE_CLASS_NAME);
		System.err.println(platformConfigurationDevice);
		device.setDateCreated(System.currentTimeMillis());
		device.setDeviceSystemId(DEVICE_CLASS_NAME + platformConfigurationDevice.getLastSystemClassId());

		Device result = deviceRepository.save(device);
		return ResponseEntity.created(new URI("/v1/devices/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("device", result.getId().toString())).body(result);
	}

	/**
	 * PUT /devices : Updates an existing device.
	 *
	 * @param device
	 *            the device to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         device, or with status 400 (Bad Request) if the device is not
	 *         valid, or with status 500 (Internal Server Error) if the device
	 *         couldnt be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Device> updateDevice(@RequestBody Device device) throws URISyntaxException {
		log.debug("REST request to update Device : {}", device);
		if (device.getId() == null) {
			return createDevice(device);
		}
		Device result = deviceRepository.save(device);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("device", device.getId().toString()))
				.body(result);
	}

	/**
	 * GET /devices : get all the devices.
	 *
	 * @return the ResponseEntity with status 200 (OK) and the list of devices
	 *         in body
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Device> getAllDevices() {
		log.debug("REST request to get all Devices");
		List<Device> devices = deviceRepository.findAll();
		return devices;
	}

	/**
	 * GET /devicesByOrganisationId/:organisationId : get all the devices by organisationId.
	 *
	 * @param organisationId
	 * @return the ResponseEntity with status 200 (OK) and the list of devices
	 *         in body
	 */
	@RequestMapping(value = "/devicesByOrganisationId/{organisationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Device> getAllDevicesByOrganisationId(@PathVariable String organisationId) {
		log.debug("REST request to get all Devices by organisationId");
		List<Device> devices = deviceService.findByOrganisationSystemId(organisationId);
		return devices;
	}

	/**
	 * GET /devices/:id : get the "id" device.
	 *
	 * @param id
	 *            the id of the device to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the device,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Device> getDevice(@PathVariable String id) {
		log.debug("REST request to get Device : {}", id);
		Device device = deviceRepository.findOne(id);
		return Optional.ofNullable(device).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * GET /devicesByOrganisationId/:organisationId/:id : get the "id" device.
	 *
	 * @param id
	 *            the id of the device to retrieve
	 * @param organisationId
	 * @return the ResponseEntity with status 200 (OK) and with body the device,
	 *         or with status 404 (Not Found)
	 */
	@RequestMapping(value = "/devicesByOrganisationId/{organisationId}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Device> getDeviceByOrganisationId(@PathVariable String organisationId,
			@PathVariable String id) {
		log.debug("REST request to get Device : {}", id);
		Device device = deviceService.findOneByOrganisationSystemIdAndId(organisationId, id);
		return Optional.ofNullable(device).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /devices/:id : delete the "id" device.
	 *
	 * @param id
	 *            the id of the device to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@RequestMapping(value = "/devices/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
		log.debug("REST request to delete Device : {}", id);
		deviceRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("device", id.toString())).build();
	}

}
