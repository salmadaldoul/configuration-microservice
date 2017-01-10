/**
 * 
 */
package com.madar.tracking.configuration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.madar.tracking.configuration.domain.Device;

/**
 * @author Salma
 *
 */
public interface DeviceRepository extends CrudRepository<Device, String> {

	List<Device> findAll();
}