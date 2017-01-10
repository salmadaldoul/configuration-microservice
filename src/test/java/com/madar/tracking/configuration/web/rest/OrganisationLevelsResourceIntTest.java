package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.ConfigurationMicroserviceApplication;

import com.madar.tracking.configuration.domain.OrganisationLevels;
import com.madar.tracking.configuration.repository.OrganisationLevelsRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author Salma
 *
 */
/**
 * Test class for the OrganisationLevelsResource REST controller.
 *
 * @see OrganisationLevelsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationMicroserviceApplication.class)
public class OrganisationLevelsResourceIntTest {

    private static final String DEFAULT_ORGANISATION_LEVELS_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_ORGANISATION_LEVELS_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_LEVEL_1_ENGLISH = "AAAAA";
    private static final String UPDATED_LEVEL_1_ENGLISH = "BBBBB";
    private static final String DEFAULT_LEVEL_1_ARABIC = "AAAAA";
    private static final String UPDATED_LEVEL_1_ARABIC = "BBBBB";
    private static final String DEFAULT_LEVEL_2_ENGLISH = "AAAAA";
    private static final String UPDATED_LEVEL_2_ENGLISH = "BBBBB";
    private static final String DEFAULT_LEVEL_2_ARABIC = "AAAAA";
    private static final String UPDATED_LEVEL_2_ARABIC = "BBBBB";
    private static final String DEFAULT_LEVEL_3_ENGLISH = "AAAAA";
    private static final String UPDATED_LEVEL_3_ENGLISH = "BBBBB";
    private static final String DEFAULT_LEVEL_3_ARABIC = "AAAAA";
    private static final String UPDATED_LEVEL_3_ARABIC = "BBBBB";

    @Inject
    private OrganisationLevelsRepository organisationLevelsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrganisationLevelsMockMvc;

    private OrganisationLevels organisationLevels;
    

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrganisationLevelsResource organisationLevelsResource = new OrganisationLevelsResource();
        ReflectionTestUtils.setField(organisationLevelsResource, "organisationLevelsRepository", organisationLevelsRepository);
        this.restOrganisationLevelsMockMvc = MockMvcBuilders.standaloneSetup(organisationLevelsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganisationLevels createEntity() {
        OrganisationLevels organisationLevels = new OrganisationLevels()
                .organisationLevelsSystemId(DEFAULT_ORGANISATION_LEVELS_SYSTEM_ID)
                .level1English(DEFAULT_LEVEL_1_ENGLISH)
                .level1Arabic(DEFAULT_LEVEL_1_ARABIC)
                .level2English(DEFAULT_LEVEL_2_ENGLISH)
                .level2Arabic(DEFAULT_LEVEL_2_ARABIC)
                .level3English(DEFAULT_LEVEL_3_ENGLISH)
                .level3Arabic(DEFAULT_LEVEL_3_ARABIC);
        return organisationLevels;
    }

    @Before
    public void initTest() {
        organisationLevelsRepository.deleteAll();
        organisationLevels = createEntity();
    }

    @Test
    public void createOrganisationLevels() throws Exception {
        int databaseSizeBeforeCreate = organisationLevelsRepository.findAll().size();

        // Create the OrganisationLevels

        restOrganisationLevelsMockMvc.perform(post("/v1/organisation-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(organisationLevels)))
                .andExpect(status().isCreated());

        // Validate the OrganisationLevels in the database
        List<OrganisationLevels> organisationLevels = organisationLevelsRepository.findAll();
        assertThat(organisationLevels).hasSize(databaseSizeBeforeCreate + 1);
        OrganisationLevels testOrganisationLevels = organisationLevels.get(organisationLevels.size() - 1);
        assertThat(testOrganisationLevels.getOrganisationLevelsSystemId()).isEqualTo(DEFAULT_ORGANISATION_LEVELS_SYSTEM_ID);
        assertThat(testOrganisationLevels.getLevel1English()).isEqualTo(DEFAULT_LEVEL_1_ENGLISH);
        assertThat(testOrganisationLevels.getLevel1Arabic()).isEqualTo(DEFAULT_LEVEL_1_ARABIC);
        assertThat(testOrganisationLevels.getLevel2English()).isEqualTo(DEFAULT_LEVEL_2_ENGLISH);
        assertThat(testOrganisationLevels.getLevel2Arabic()).isEqualTo(DEFAULT_LEVEL_2_ARABIC);
        assertThat(testOrganisationLevels.getLevel3English()).isEqualTo(DEFAULT_LEVEL_3_ENGLISH);
        assertThat(testOrganisationLevels.getLevel3Arabic()).isEqualTo(DEFAULT_LEVEL_3_ARABIC);
    }

    @Test
    public void getAllOrganisationLevels() throws Exception {
        // Initialize the database
        organisationLevelsRepository.save(organisationLevels);

        // Get all the organisationLevels
        restOrganisationLevelsMockMvc.perform(get("/v1/organisation-levels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(organisationLevels.getId())))
                .andExpect(jsonPath("$.[*].organisationLevelsSystemId").value(hasItem(DEFAULT_ORGANISATION_LEVELS_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].level1English").value(hasItem(DEFAULT_LEVEL_1_ENGLISH.toString())))
                .andExpect(jsonPath("$.[*].level1Arabic").value(hasItem(DEFAULT_LEVEL_1_ARABIC.toString())))
                .andExpect(jsonPath("$.[*].level2English").value(hasItem(DEFAULT_LEVEL_2_ENGLISH.toString())))
                .andExpect(jsonPath("$.[*].level2Arabic").value(hasItem(DEFAULT_LEVEL_2_ARABIC.toString())))
                .andExpect(jsonPath("$.[*].level3English").value(hasItem(DEFAULT_LEVEL_3_ENGLISH.toString())))
                .andExpect(jsonPath("$.[*].level3Arabic").value(hasItem(DEFAULT_LEVEL_3_ARABIC.toString())));
    }

    @Test
    public void getOrganisationLevels() throws Exception {
        // Initialize the database
        organisationLevelsRepository.save(organisationLevels);

        // Get the organisationLevels
        restOrganisationLevelsMockMvc.perform(get("/v1/organisation-levels/{id}", organisationLevels.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organisationLevels.getId()))
            .andExpect(jsonPath("$.organisationLevelsSystemId").value(DEFAULT_ORGANISATION_LEVELS_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.level1English").value(DEFAULT_LEVEL_1_ENGLISH.toString()))
            .andExpect(jsonPath("$.level1Arabic").value(DEFAULT_LEVEL_1_ARABIC.toString()))
            .andExpect(jsonPath("$.level2English").value(DEFAULT_LEVEL_2_ENGLISH.toString()))
            .andExpect(jsonPath("$.level2Arabic").value(DEFAULT_LEVEL_2_ARABIC.toString()))
            .andExpect(jsonPath("$.level3English").value(DEFAULT_LEVEL_3_ENGLISH.toString()))
            .andExpect(jsonPath("$.level3Arabic").value(DEFAULT_LEVEL_3_ARABIC.toString()));
    }

    @Test
    public void getNonExistingOrganisationLevels() throws Exception {
        // Get the organisationLevels
        restOrganisationLevelsMockMvc.perform(get("/v1/organisation-levels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrganisationLevels() throws Exception {
        // Initialize the database
        organisationLevelsRepository.save(organisationLevels);
        int databaseSizeBeforeUpdate = organisationLevelsRepository.findAll().size();

        // Update the organisationLevels
        OrganisationLevels updatedOrganisationLevels = organisationLevelsRepository.findOne(organisationLevels.getId());
        updatedOrganisationLevels
                .organisationLevelsSystemId(UPDATED_ORGANISATION_LEVELS_SYSTEM_ID)
                .level1English(UPDATED_LEVEL_1_ENGLISH)
                .level1Arabic(UPDATED_LEVEL_1_ARABIC)
                .level2English(UPDATED_LEVEL_2_ENGLISH)
                .level2Arabic(UPDATED_LEVEL_2_ARABIC)
                .level3English(UPDATED_LEVEL_3_ENGLISH)
                .level3Arabic(UPDATED_LEVEL_3_ARABIC);

        restOrganisationLevelsMockMvc.perform(put("/v1/organisation-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedOrganisationLevels)))
                .andExpect(status().isOk());

        // Validate the OrganisationLevels in the database
        List<OrganisationLevels> organisationLevels = organisationLevelsRepository.findAll();
        assertThat(organisationLevels).hasSize(databaseSizeBeforeUpdate);
        OrganisationLevels testOrganisationLevels = organisationLevels.get(organisationLevels.size() - 1);
        assertThat(testOrganisationLevels.getOrganisationLevelsSystemId()).isEqualTo(UPDATED_ORGANISATION_LEVELS_SYSTEM_ID);
        assertThat(testOrganisationLevels.getLevel1English()).isEqualTo(UPDATED_LEVEL_1_ENGLISH);
        assertThat(testOrganisationLevels.getLevel1Arabic()).isEqualTo(UPDATED_LEVEL_1_ARABIC);
        assertThat(testOrganisationLevels.getLevel2English()).isEqualTo(UPDATED_LEVEL_2_ENGLISH);
        assertThat(testOrganisationLevels.getLevel2Arabic()).isEqualTo(UPDATED_LEVEL_2_ARABIC);
        assertThat(testOrganisationLevels.getLevel3English()).isEqualTo(UPDATED_LEVEL_3_ENGLISH);
        assertThat(testOrganisationLevels.getLevel3Arabic()).isEqualTo(UPDATED_LEVEL_3_ARABIC);
    }

    @Test
    public void deleteOrganisationLevels() throws Exception {
        // Initialize the database
        organisationLevelsRepository.save(organisationLevels);
        int databaseSizeBeforeDelete = organisationLevelsRepository.findAll().size();

        // Get the organisationLevels
        restOrganisationLevelsMockMvc.perform(delete("/v1/organisation-levels/{id}", organisationLevels.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrganisationLevels> organisationLevels = organisationLevelsRepository.findAll();
        assertThat(organisationLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
