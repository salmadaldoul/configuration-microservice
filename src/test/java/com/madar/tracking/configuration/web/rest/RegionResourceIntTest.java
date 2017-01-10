package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.ConfigurationMicroserviceApplication;

import com.madar.tracking.configuration.domain.Region;
import com.madar.tracking.configuration.repository.RegionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
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

import java.text.SimpleDateFormat;
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
 * Test class for the RegionResource REST controller.
 *
 * @see RegionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationMicroserviceApplication.class)
public class RegionResourceIntTest {

    private static final String DEFAULT_REGION_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_REGION_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";
   
    private static final String DEFAULT_NOTE = "AAAAA";
    private static final String UPDATED_NOTE = "BBBBB";

    @Inject
    private RegionRepository regionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRegionMockMvc;

    private Region region;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegionResource regionResource = new RegionResource();
        ReflectionTestUtils.setField(regionResource, "regionRepository", regionRepository);
        this.restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Region createEntity() {
        Region region = new Region()
                .regionSystemId(DEFAULT_REGION_SYSTEM_ID)
                .createdBy(DEFAULT_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .notes(DEFAULT_NOTE);
        return region;
    }

    @Before
    public void initTest() {
        regionRepository.deleteAll();
        region = createEntity();
    }

    @Test
    public void createRegion() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // Create the Region

        restRegionMockMvc.perform(post("/v1/regions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(region)))
                .andExpect(status().isCreated());

        // Validate the Region in the database
        List<Region> regions = regionRepository.findAll();
        assertThat(regions).hasSize(databaseSizeBeforeCreate + 1);
        Region testRegion = regions.get(regions.size() - 1);
        assertThat(testRegion.getRegionSystemId()).isEqualTo(DEFAULT_REGION_SYSTEM_ID);
        assertThat(testRegion.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRegion.getDateCreated()).isEqualTo(sdf.format(new Date()));
        assertThat(testRegion.getNotes()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    public void getAllRegions() throws Exception {
        // Initialize the database
        regionRepository.save(region);

        // Get all the regions
        restRegionMockMvc.perform(get("/v1/regions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId())))
                .andExpect(jsonPath("$.[*].regionSystemId").value(hasItem(DEFAULT_REGION_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sdf.format(new Date()))))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    public void getRegion() throws Exception {
        // Initialize the database
        regionRepository.save(region);

        // Get the region
        restRegionMockMvc.perform(get("/v1/regions/{id}", region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(region.getId()))
            .andExpect(jsonPath("$.regionSystemId").value(DEFAULT_REGION_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.dateCreated").value(sdf.format(new Date())))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    public void getNonExistingRegion() throws Exception {
        // Get the region
        restRegionMockMvc.perform(get("/v1/regions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateRegion() throws Exception {
        // Initialize the database
        regionRepository.save(region);
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();
       
        
        // Update the region
        Region updatedRegion = regionRepository.findOne(region.getId());
        
        updatedRegion
                .regionSystemId(UPDATED_REGION_SYSTEM_ID)
                .createdBy(UPDATED_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .notes(UPDATED_NOTE);

        restRegionMockMvc.perform(put("/v1/regions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRegion)))
                .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regions = regionRepository.findAll();
        assertThat(regions).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regions.get(regions.size() - 1);
        assertThat(testRegion.getRegionSystemId()).isEqualTo(UPDATED_REGION_SYSTEM_ID);
        assertThat(testRegion.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRegion.getDateCreated()).isEqualTo(sdf.format(new Date()));
        assertThat(testRegion.getNotes()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    public void deleteRegion() throws Exception {
        // Initialize the database
        regionRepository.save(region);
        int databaseSizeBeforeDelete = regionRepository.findAll().size();

        // Get the region
        restRegionMockMvc.perform(delete("/v1/regions/{id}", region.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Region> regions = regionRepository.findAll();
        assertThat(regions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
