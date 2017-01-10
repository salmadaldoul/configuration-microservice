package com.madar.tracking.configuration;

import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.madar.tracking.configuration.domain.Attachment;
import com.madar.tracking.configuration.repository.AttachmentRepository;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
public class ConfigurationMicroserviceApplication implements CommandLineRunner {

	@Inject
	private AttachmentRepository attachmentRepository;

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationMicroserviceApplication.class, args);

	}

	
	public void run(String... arg0) throws Exception {

		List<Attachment> attachments = attachmentRepository.findAll();
		for (Attachment attachment : attachments) {
			System.err.println(attachment.getOrganizationSystemId());
			
		}
		
	
	}
	
	 @Bean
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2).select().apis(
	           RequestHandlerSelectors.any()).paths(PathSelectors.ant("/v1/**")).build();
	    }
	    
}
