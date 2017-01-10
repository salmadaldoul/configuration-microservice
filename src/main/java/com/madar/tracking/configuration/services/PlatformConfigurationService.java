/**
 * 
 */
package com.madar.tracking.configuration.services;


import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.PlatformConfigurationRepository;

/**
 * @author Salma
 *
 */

@Service
public class PlatformConfigurationService {

	@Inject
	private PlatformConfigurationRepository platformConfigurationRepository;

	public PlatformConfiguration addOneToLastSystemId(String className) {

		PlatformConfiguration platformConfiguration = platformConfigurationRepository.findByClassName(className);
		
		
		if (platformConfiguration != null){
			platformConfiguration.setLastSystemClassId(platformConfiguration.getLastSystemClassId()+1);
		} else {
			platformConfiguration = new PlatformConfiguration(className, 1);
		}
		platformConfiguration = platformConfigurationRepository.save(platformConfiguration);
		return platformConfiguration;

	}
}
