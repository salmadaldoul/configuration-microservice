package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.ConfigurationMicroserviceApplication;

import com.madar.tracking.configuration.domain.City;
import com.madar.tracking.configuration.domain.PlatformConfiguration;
import com.madar.tracking.configuration.repository.CityRepository;
import com.madar.tracking.configuration.services.PlatformConfigurationService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author Salma
 *
 */
/**
 * Test class for the CityResource REST controller.
 *
 * @see CityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationMicroserviceApplication.class)
public class CityResourceIntTest {

    private static final String DEFAULT_CITY_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_CITY_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_REGION_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_REGION_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NOTE = "AAAAA";
    private static final String UPDATED_NOTE = "BBBBB";

    @Inject
    private CityRepository cityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCityMockMvc;

    private City city;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CityResource cityResource = new CityResource();
        PlatformConfiguration platformConfiguration = new PlatformConfiguration();
        ReflectionTestUtils.setField(cityResource, "cityRepository", cityRepository);
        this.restCityMockMvc = MockMvcBuilders.standaloneSetup(cityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity() {
        City city = new City()
                .citySystemId(DEFAULT_CITY_SYSTEM_ID)
                .regionSystemId(DEFAULT_REGION_SYSTEM_ID)
                .createdBy(DEFAULT_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .notes(DEFAULT_NOTE);
        return city;
    }

    @Before
    public void initTest() {
        cityRepository.deleteAll();
        city = createEntity();
    }

    @Test
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City

        restCityMockMvc.perform(post("/v1/cities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(city)))
                .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cities.get(cities.size() - 1);
        assertThat(testCity.getCitySystemId()).isNotNull();
        assertThat(testCity.getRegionSystemId()).isEqualTo(DEFAULT_REGION_SYSTEM_ID);
        assertThat(testCity.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCity.getDateCreated()).isNotNull();
        assertThat(testCity.getNotes()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.save(city);
        // Get all the cities
        restCityMockMvc.perform(get("/v1/cities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId())))
                .andExpect(jsonPath("$.[*].citySystemId").value(hasItem(DEFAULT_CITY_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].regionSystemId").value(hasItem(DEFAULT_REGION_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);

        // Get the city
        restCityMockMvc.perform(get("/v1/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId()))
            .andExpect(jsonPath("$.citySystemId").value(DEFAULT_CITY_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.regionSystemId").value(DEFAULT_REGION_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/v1/cities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findOne(city.getId());
        updatedCity
                .citySystemId(UPDATED_CITY_SYSTEM_ID)
                .regionSystemId(UPDATED_REGION_SYSTEM_ID)
                .createdBy(UPDATED_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .notes(UPDATED_NOTE);

        restCityMockMvc.perform(put("/v1/cities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCity)))
                .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(databaseSizeBeforeUpdate);
        City testCity = cities.get(cities.size() - 1);
        assertThat(testCity.getCitySystemId()).isEqualTo(UPDATED_CITY_SYSTEM_ID);
        assertThat(testCity.getRegionSystemId()).isEqualTo(UPDATED_REGION_SYSTEM_ID);
        assertThat(testCity.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCity.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCity.getNotes()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    public void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.save(city);
        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Get the city
        restCityMockMvc.perform(delete("/v1/cities/{id}", city.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<City> cities = cityRepository.findAll();
        assertThat(cities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
