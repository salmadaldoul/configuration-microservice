package com.madar.tracking.configuration.web.rest;

import com.madar.tracking.configuration.ConfigurationMicroserviceApplication;

import com.madar.tracking.configuration.domain.Device;
import com.madar.tracking.configuration.repository.DeviceRepository;

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
 * Test class for the DeviceResource REST controller.
 *
 * @see DeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationMicroserviceApplication.class)
public class DeviceResourceIntTest {

    private static final String DEFAULT_DEVICE_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_DEVICE_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_VEHICLE_SYSTEM_ID = "AAAAA";
    private static final String UPDATED_VEHICLE_SYSTEM_ID = "BBBBB";
    private static final String DEFAULT_DEVICE_NAME = "AAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBB";
    private static final String DEFAULT_DEVICE_IMEI = "AAAAA";
    private static final String UPDATED_DEVICE_IMEI = "BBBBB";
    private static final String DEFAULT_DEVICE_BRAND = "AAAAA";
    private static final String UPDATED_DEVICE_BRAND = "BBBBB";
    private static final String DEFAULT_VEHICLE_NAME = "AAAAA";
    private static final String UPDATED_VEHICLE_NAME = "BBBBB";
    private static final String DEFAULT_CREATED_BY = "AAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SECRIPTION = "AAAAA";
    private static final String UPDATED_SECRIPTION = "BBBBB";

    @Inject
    private DeviceRepository deviceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceResource deviceResource = new DeviceResource();
        ReflectionTestUtils.setField(deviceResource, "deviceRepository", deviceRepository);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Device createEntity() {
        Device device = new Device()
                .deviceSystemId(DEFAULT_DEVICE_SYSTEM_ID)
                .vehicleSystemId(DEFAULT_VEHICLE_SYSTEM_ID)
                .deviceName(DEFAULT_DEVICE_NAME)
                .deviceImei(DEFAULT_DEVICE_IMEI)
                .deviceBrand(DEFAULT_DEVICE_BRAND)
                .vehicleName(DEFAULT_VEHICLE_NAME)
                .createdBy(DEFAULT_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .decription(DEFAULT_SECRIPTION);
        return device;
    }

    @Before
    public void initTest() {
        deviceRepository.deleteAll();
        device = createEntity();
    }

    @Test
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device

        restDeviceMockMvc.perform(post("/v1/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(device)))
                .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = devices.get(devices.size() - 1);
        assertThat(testDevice.getDeviceSystemId()).isEqualTo(DEFAULT_DEVICE_SYSTEM_ID);
        assertThat(testDevice.getVehicleSystemId()).isEqualTo(DEFAULT_VEHICLE_SYSTEM_ID);
        assertThat(testDevice.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testDevice.getDeviceImei()).isEqualTo(DEFAULT_DEVICE_IMEI);
        assertThat(testDevice.getDeviceBrand()).isEqualTo(DEFAULT_DEVICE_BRAND);
        assertThat(testDevice.getVehicleName()).isEqualTo(DEFAULT_VEHICLE_NAME);
        assertThat(testDevice.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDevice.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDevice.getDecription()).isEqualTo(DEFAULT_SECRIPTION);
    }

    @Test
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.save(device);

        // Get all the devices
        restDeviceMockMvc.perform(get("/v1/devices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId())))
                .andExpect(jsonPath("$.[*].deviceSystemId").value(hasItem(DEFAULT_DEVICE_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].vehicleSystemId").value(hasItem(DEFAULT_VEHICLE_SYSTEM_ID.toString())))
                .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
                .andExpect(jsonPath("$.[*].deviceImei").value(hasItem(DEFAULT_DEVICE_IMEI.toString())))
                .andExpect(jsonPath("$.[*].deviceBrand").value(hasItem(DEFAULT_DEVICE_BRAND.toString())))
                .andExpect(jsonPath("$.[*].vehicleName").value(hasItem(DEFAULT_VEHICLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
                .andExpect(jsonPath("$.[*].secription").value(hasItem(DEFAULT_SECRIPTION.toString())));
    }

    @Test
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.save(device);

        // Get the device
        restDeviceMockMvc.perform(get("/v1/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId()))
            .andExpect(jsonPath("$.deviceSystemId").value(DEFAULT_DEVICE_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.vehicleSystemId").value(DEFAULT_VEHICLE_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()))
            .andExpect(jsonPath("$.deviceImei").value(DEFAULT_DEVICE_IMEI.toString()))
            .andExpect(jsonPath("$.deviceBrand").value(DEFAULT_DEVICE_BRAND.toString()))
            .andExpect(jsonPath("$.vehicleName").value(DEFAULT_VEHICLE_NAME.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.secription").value(DEFAULT_SECRIPTION.toString()));
    }

    @Test
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/v1/devices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceRepository.save(device);
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findOne(device.getId());
        updatedDevice
                .deviceSystemId(UPDATED_DEVICE_SYSTEM_ID)
                .vehicleSystemId(UPDATED_VEHICLE_SYSTEM_ID)
                .deviceName(UPDATED_DEVICE_NAME)
                .deviceImei(UPDATED_DEVICE_IMEI)
                .deviceBrand(UPDATED_DEVICE_BRAND)
                .vehicleName(UPDATED_VEHICLE_NAME)
                .createdBy(UPDATED_CREATED_BY)
                .dateCreated(System.currentTimeMillis())
                .decription(UPDATED_SECRIPTION);

        restDeviceMockMvc.perform(put("/v1/devices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDevice)))
                .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = devices.get(devices.size() - 1);
        assertThat(testDevice.getDeviceSystemId()).isEqualTo(UPDATED_DEVICE_SYSTEM_ID);
        assertThat(testDevice.getVehicleSystemId()).isEqualTo(UPDATED_VEHICLE_SYSTEM_ID);
        assertThat(testDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testDevice.getDeviceImei()).isEqualTo(UPDATED_DEVICE_IMEI);
        assertThat(testDevice.getDeviceBrand()).isEqualTo(UPDATED_DEVICE_BRAND);
        assertThat(testDevice.getVehicleName()).isEqualTo(UPDATED_VEHICLE_NAME);
        assertThat(testDevice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDevice.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDevice.getDecription()).isEqualTo(UPDATED_SECRIPTION);
    }

    @Test
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceRepository.save(device);
        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Get the device
        restDeviceMockMvc.perform(delete("/v1/devices/{id}", device.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Device> devices = deviceRepository.findAll();
        assertThat(devices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
