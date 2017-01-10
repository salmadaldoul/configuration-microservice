package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.ConfigurationMicroserviceApplication;

import com.madar.tracking.configuration.domain.Department;
import com.madar.tracking.configuration.repository.DepartmentRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * @author Salma
 *
 */
/**
 * Test class for the DepartementResource REST controller.
 *
 * @see DepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationMicroserviceApplication.class)
public class DepartementResourceIntTest {

    private static final String DEFAULT_DEPARTEMENT_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_DEPARTEMENT_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_CITY_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_CITY_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_NOTE = "AAAAA";
    private static final String UPDATED_NOTE = "BBBBB";

    @Inject
    private DepartmentRepository departementRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDepartementMockMvc;

    private Department departement;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DepartmentResource departementResource = new DepartmentResource();
        ReflectionTestUtils.setField(departementResource, "departementRepository", departementRepository);
        this.restDepartementMockMvc = MockMvcBuilders.standaloneSetup(departementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createEntity() {
        Department departement = new Department()
//                .departmentSystemId(DEFAULT_DEPARTEMENT_SYSTEM_ID)
                .citySystemId(DEFAULT_CITY_SYSTEM_ID)
                .createdBy(DEFAULT_CREATED_BY)
                .dateCreated(DEFAULT_DATE_CREATED)
                .notes(DEFAULT_NOTE);
        return departement;
    }

    @Before
    public void initTest() {
        departementRepository.deleteAll();
        departement = createEntity();
    }

    @Test
    public void createDepartement() throws Exception {
        int databaseSizeBeforeCreate = departementRepository.findAll().size();

        // Create the Departement

        restDepartementMockMvc.perform(post("/v1/departements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(departement)))
                .andExpect(status().isCreated());

        // Validate the Departement in the database
        List<Department> departements = departementRepository.findAll();
        assertThat(departements).hasSize(databaseSizeBeforeCreate + 1);
        Department testDepartement = departements.get(departements.size() - 1);
        assertThat(testDepartement.getDepartmentSystemId()).isEqualTo(DEFAULT_DEPARTEMENT_SYSTEM_ID);
        assertThat(testDepartement.getCitySystemId()).isEqualTo(DEFAULT_CITY_SYSTEM_ID);
        assertThat(testDepartement.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDepartement.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDepartement.getNotes()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    public void getAllDepartements() throws Exception {
        // Initialize the database
        departementRepository.save(departement);

        // Get all the departements
        restDepartementMockMvc.perform(get("/v1/departements?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(departement.getId())))
                .andExpect(jsonPath("$.[*].departementSystemId").value(hasItem(DEFAULT_DEPARTEMENT_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].citySystemId").value(hasItem(DEFAULT_CITY_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    public void getDepartement() throws Exception {
        // Initialize the database
        departementRepository.save(departement);

        // Get the departement
        restDepartementMockMvc.perform(get("/v1/departements/{id}", departement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departement.getId()))
            .andExpect(jsonPath("$.departementSystemId").value(DEFAULT_DEPARTEMENT_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.citySystemId").value(DEFAULT_CITY_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    public void getNonExistingDepartement() throws Exception {
        // Get the departement
        restDepartementMockMvc.perform(get("/v1/departements/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDepartement() throws Exception {
        // Initialize the database
        departementRepository.save(departement);
        int databaseSizeBeforeUpdate = departementRepository.findAll().size();

        // Update the departement
        Department updatedDepartement = departementRepository.findOne(departement.getId());
        updatedDepartement
//                .departmentSystemId(UPDATED_DEPARTEMENT_SYSTEM_ID)
                .citySystemId(UPDATED_CITY_SYSTEM_ID)
                .createdBy(UPDATED_CREATED_BY)
                .dateCreated(UPDATED_DATE_CREATED)
                .notes(UPDATED_NOTE);

        restDepartementMockMvc.perform(put("/v1/departements")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDepartement)))
                .andExpect(status().isOk());

        // Validate the Departement in the database
        List<Department> departements = departementRepository.findAll();
        assertThat(departements).hasSize(databaseSizeBeforeUpdate);
        Department testDepartement = departements.get(departements.size() - 1);
        assertThat(testDepartement.getDepartmentSystemId()).isEqualTo(UPDATED_DEPARTEMENT_SYSTEM_ID);
        assertThat(testDepartement.getCitySystemId()).isEqualTo(UPDATED_CITY_SYSTEM_ID);
        assertThat(testDepartement.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDepartement.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDepartement.getNotes()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    public void deleteDepartement() throws Exception {
        // Initialize the database
        departementRepository.save(departement);
        int databaseSizeBeforeDelete = departementRepository.findAll().size();

        // Get the departement
        restDepartementMockMvc.perform(delete("/v1/departements/{id}", departement.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Department> departements = departementRepository.findAll();
        assertThat(departements).hasSize(databaseSizeBeforeDelete - 1);
    }
}
