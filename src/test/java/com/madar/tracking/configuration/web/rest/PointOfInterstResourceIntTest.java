package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.ConfigurationMicroserviceApplication;

import com.madar.tracking.configuration.domain.PointOfInterst;
import com.madar.tracking.configuration.repository.PointOfInterstRepository;

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
import org.springframework.util.Base64Utils;

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
 * Test class for the PointOfInterstResource REST controller.
 *
 * @see PointOfInterstResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationMicroserviceApplication.class)
public class PointOfInterstResourceIntTest {

    private static final String DEFAULT_POI_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_POI_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_POI_NAME = "AAAAA";
    private static final String UPDATED_POI_NAME = "BBBBB";

    private static final byte[] DEFAULT_POI_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POI_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_POI_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POI_ICON_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_COLOR = "AAAAA";
    private static final String UPDATED_COLOR = "BBBBB";
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LONGITUDE = "AAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBB";
    private static final String DEFAULT_LATITUDE = "AAAAA";
    private static final String UPDATED_LATITUDE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private PointOfInterstRepository pointOfInterstRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPointOfInterstMockMvc;

    private PointOfInterst pointOfInterst;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PointOfInterstResource pointOfInterstResource = new PointOfInterstResource();
        ReflectionTestUtils.setField(pointOfInterstResource, "pointOfInterstRepository", pointOfInterstRepository);
        this.restPointOfInterstMockMvc = MockMvcBuilders.standaloneSetup(pointOfInterstResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfInterst createEntity() {
        PointOfInterst pointOfInterst = new PointOfInterst()
                .poiSystemId(DEFAULT_POI_SYSTEM_ID)
                .poiName(DEFAULT_POI_NAME)
                .poiIcon(DEFAULT_POI_ICON)
                .poiIconContentType(DEFAULT_POI_ICON_CONTENT_TYPE)
                .color(DEFAULT_COLOR)
                .createdBy(DEFAULT_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .longitude(DEFAULT_LONGITUDE)
                .latitude(DEFAULT_LATITUDE)
                .description(DEFAULT_DESCRIPTION);
        return pointOfInterst;
    }

    @Before
    public void initTest() {
        pointOfInterstRepository.deleteAll();
        pointOfInterst = createEntity();
    }

    @Test
    public void createPointOfInterst() throws Exception {
        int databaseSizeBeforeCreate = pointOfInterstRepository.findAll().size();

        // Create the PointOfInterst

        restPointOfInterstMockMvc.perform(post("/v1/point-of-intersts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pointOfInterst)))
                .andExpect(status().isCreated());

        // Validate the PointOfInterst in the database
        List<PointOfInterst> pointOfIntersts = pointOfInterstRepository.findAll();
        assertThat(pointOfIntersts).hasSize(databaseSizeBeforeCreate + 1);
        PointOfInterst testPointOfInterst = pointOfIntersts.get(pointOfIntersts.size() - 1);
        assertThat(testPointOfInterst.getPoiSystemId()).isEqualTo(DEFAULT_POI_SYSTEM_ID);
        assertThat(testPointOfInterst.getPoiName()).isEqualTo(DEFAULT_POI_NAME);
        assertThat(testPointOfInterst.getPoiIcon()).isEqualTo(DEFAULT_POI_ICON);
        assertThat(testPointOfInterst.getPoiIconContentType()).isEqualTo(DEFAULT_POI_ICON_CONTENT_TYPE);
        assertThat(testPointOfInterst.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testPointOfInterst.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPointOfInterst.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPointOfInterst.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testPointOfInterst.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testPointOfInterst.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void getAllPointOfIntersts() throws Exception {
        // Initialize the database
        pointOfInterstRepository.save(pointOfInterst);

        // Get all the pointOfIntersts
        restPointOfInterstMockMvc.perform(get("/v1/point-of-intersts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pointOfInterst.getId())))
                .andExpect(jsonPath("$.[*].poiSystemId").value(hasItem(DEFAULT_POI_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].poiName").value(hasItem(DEFAULT_POI_NAME.toString())))
                .andExpect(jsonPath("$.[*].poiIconContentType").value(hasItem(DEFAULT_POI_ICON_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].poiIcon").value(hasItem(Base64Utils.encodeToString(DEFAULT_POI_ICON))))
                .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getPointOfInterst() throws Exception {
        // Initialize the database
        pointOfInterstRepository.save(pointOfInterst);

        // Get the pointOfInterst
        restPointOfInterstMockMvc.perform(get("/v1/point-of-intersts/{id}", pointOfInterst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pointOfInterst.getId()))
            .andExpect(jsonPath("$.poiSystemId").value(DEFAULT_POI_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.poiName").value(DEFAULT_POI_NAME.toString()))
            .andExpect(jsonPath("$.poiIconContentType").value(DEFAULT_POI_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.poiIcon").value(Base64Utils.encodeToString(DEFAULT_POI_ICON)))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingPointOfInterst() throws Exception {
        // Get the pointOfInterst
        restPointOfInterstMockMvc.perform(get("/v1/point-of-intersts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updatePointOfInterst() throws Exception {
        // Initialize the database
        pointOfInterstRepository.save(pointOfInterst);
        int databaseSizeBeforeUpdate = pointOfInterstRepository.findAll().size();

        // Update the pointOfInterst
        PointOfInterst updatedPointOfInterst = pointOfInterstRepository.findOne(pointOfInterst.getId());
        updatedPointOfInterst
                .poiSystemId(UPDATED_POI_SYSTEM_ID)
                .poiName(UPDATED_POI_NAME)
                .poiIcon(UPDATED_POI_ICON)
                .poiIconContentType(UPDATED_POI_ICON_CONTENT_TYPE)
                .color(UPDATED_COLOR)
                .createdBy(UPDATED_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .longitude(UPDATED_LONGITUDE)
                .latitude(UPDATED_LATITUDE)
                .description(UPDATED_DESCRIPTION);

        restPointOfInterstMockMvc.perform(put("/v1/point-of-intersts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPointOfInterst)))
                .andExpect(status().isOk());

        // Validate the PointOfInterst in the database
        List<PointOfInterst> pointOfIntersts = pointOfInterstRepository.findAll();
        assertThat(pointOfIntersts).hasSize(databaseSizeBeforeUpdate);
        PointOfInterst testPointOfInterst = pointOfIntersts.get(pointOfIntersts.size() - 1);
        assertThat(testPointOfInterst.getPoiSystemId()).isEqualTo(UPDATED_POI_SYSTEM_ID);
        assertThat(testPointOfInterst.getPoiName()).isEqualTo(UPDATED_POI_NAME);
        assertThat(testPointOfInterst.getPoiIcon()).isEqualTo(UPDATED_POI_ICON);
        assertThat(testPointOfInterst.getPoiIconContentType()).isEqualTo(UPDATED_POI_ICON_CONTENT_TYPE);
        assertThat(testPointOfInterst.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testPointOfInterst.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPointOfInterst.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPointOfInterst.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testPointOfInterst.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testPointOfInterst.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void deletePointOfInterst() throws Exception {
        // Initialize the database
        pointOfInterstRepository.save(pointOfInterst);
        int databaseSizeBeforeDelete = pointOfInterstRepository.findAll().size();

        // Get the pointOfInterst
        restPointOfInterstMockMvc.perform(delete("/v1/point-of-intersts/{id}", pointOfInterst.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PointOfInterst> pointOfIntersts = pointOfInterstRepository.findAll();
        assertThat(pointOfIntersts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
