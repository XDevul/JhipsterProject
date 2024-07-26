package com.jhipster.test.web.rest;

import static com.jhipster.test.domain.AlloyHttpAsserts.*;
import static com.jhipster.test.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhipster.test.IntegrationTest;
import com.jhipster.test.domain.AlloyHttp;
import com.jhipster.test.repository.AlloyHttpRepository;
import com.jhipster.test.service.dto.AlloyHttpDTO;
import com.jhipster.test.service.mapper.AlloyHttpMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AlloyHttpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlloyHttpResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MODUL = "AAAAAAAAAA";
    private static final String UPDATED_MODUL = "BBBBBBBBBB";

    private static final String DEFAULT_API = "AAAAAAAAAA";
    private static final String UPDATED_API = "BBBBBBBBBB";

    private static final String DEFAULT_ENV = "AAAAAAAAAA";
    private static final String UPDATED_ENV = "BBBBBBBBBB";

    private static final String DEFAULT_HOSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSTNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/alloy-https";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AlloyHttpRepository alloyHttpRepository;

    @Autowired
    private AlloyHttpMapper alloyHttpMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlloyHttpMockMvc;

    private AlloyHttp alloyHttp;

    private AlloyHttp insertedAlloyHttp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlloyHttp createEntity(EntityManager em) {
        AlloyHttp alloyHttp = new AlloyHttp()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .modul(DEFAULT_MODUL)
            .api(DEFAULT_API)
            .env(DEFAULT_ENV)
            .hostname(DEFAULT_HOSTNAME)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return alloyHttp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlloyHttp createUpdatedEntity(EntityManager em) {
        AlloyHttp alloyHttp = new AlloyHttp()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .modul(UPDATED_MODUL)
            .api(UPDATED_API)
            .env(UPDATED_ENV)
            .hostname(UPDATED_HOSTNAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return alloyHttp;
    }

    @BeforeEach
    public void initTest() {
        alloyHttp = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAlloyHttp != null) {
            alloyHttpRepository.delete(insertedAlloyHttp);
            insertedAlloyHttp = null;
        }
    }

    @Test
    @Transactional
    void createAlloyHttp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);
        var returnedAlloyHttpDTO = om.readValue(
            restAlloyHttpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AlloyHttpDTO.class
        );

        // Validate the AlloyHttp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAlloyHttp = alloyHttpMapper.toEntity(returnedAlloyHttpDTO);
        assertAlloyHttpUpdatableFieldsEquals(returnedAlloyHttp, getPersistedAlloyHttp(returnedAlloyHttp));

        insertedAlloyHttp = returnedAlloyHttp;
    }

    @Test
    @Transactional
    void createAlloyHttpWithExistingId() throws Exception {
        // Create the AlloyHttp with an existing ID
        alloyHttp.setId(1L);
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlloyHttpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alloyHttp.setName(null);

        // Create the AlloyHttp, which fails.
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        restAlloyHttpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alloyHttp.setAddress(null);

        // Create the AlloyHttp, which fails.
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        restAlloyHttpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModulIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alloyHttp.setModul(null);

        // Create the AlloyHttp, which fails.
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        restAlloyHttpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        alloyHttp.setCreateTime(null);

        // Create the AlloyHttp, which fails.
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        restAlloyHttpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAlloyHttps() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList
        restAlloyHttpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alloyHttp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].modul").value(hasItem(DEFAULT_MODUL)))
            .andExpect(jsonPath("$.[*].api").value(hasItem(DEFAULT_API)))
            .andExpect(jsonPath("$.[*].env").value(hasItem(DEFAULT_ENV)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }

    @Test
    @Transactional
    void getAlloyHttp() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get the alloyHttp
        restAlloyHttpMockMvc
            .perform(get(ENTITY_API_URL_ID, alloyHttp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alloyHttp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.modul").value(DEFAULT_MODUL))
            .andExpect(jsonPath("$.api").value(DEFAULT_API))
            .andExpect(jsonPath("$.env").value(DEFAULT_ENV))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    void getAlloyHttpsByIdFiltering() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        Long id = alloyHttp.getId();

        defaultAlloyHttpFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAlloyHttpFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAlloyHttpFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where name equals to
        defaultAlloyHttpFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where name in
        defaultAlloyHttpFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where name is not null
        defaultAlloyHttpFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where name contains
        defaultAlloyHttpFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where name does not contain
        defaultAlloyHttpFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where address equals to
        defaultAlloyHttpFiltering("address.equals=" + DEFAULT_ADDRESS, "address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where address in
        defaultAlloyHttpFiltering("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS, "address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where address is not null
        defaultAlloyHttpFiltering("address.specified=true", "address.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByAddressContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where address contains
        defaultAlloyHttpFiltering("address.contains=" + DEFAULT_ADDRESS, "address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where address does not contain
        defaultAlloyHttpFiltering("address.doesNotContain=" + UPDATED_ADDRESS, "address.doesNotContain=" + DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByModulIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where modul equals to
        defaultAlloyHttpFiltering("modul.equals=" + DEFAULT_MODUL, "modul.equals=" + UPDATED_MODUL);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByModulIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where modul in
        defaultAlloyHttpFiltering("modul.in=" + DEFAULT_MODUL + "," + UPDATED_MODUL, "modul.in=" + UPDATED_MODUL);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByModulIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where modul is not null
        defaultAlloyHttpFiltering("modul.specified=true", "modul.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByModulContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where modul contains
        defaultAlloyHttpFiltering("modul.contains=" + DEFAULT_MODUL, "modul.contains=" + UPDATED_MODUL);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByModulNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where modul does not contain
        defaultAlloyHttpFiltering("modul.doesNotContain=" + UPDATED_MODUL, "modul.doesNotContain=" + DEFAULT_MODUL);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByApiIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where api equals to
        defaultAlloyHttpFiltering("api.equals=" + DEFAULT_API, "api.equals=" + UPDATED_API);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByApiIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where api in
        defaultAlloyHttpFiltering("api.in=" + DEFAULT_API + "," + UPDATED_API, "api.in=" + UPDATED_API);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByApiIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where api is not null
        defaultAlloyHttpFiltering("api.specified=true", "api.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByApiContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where api contains
        defaultAlloyHttpFiltering("api.contains=" + DEFAULT_API, "api.contains=" + UPDATED_API);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByApiNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where api does not contain
        defaultAlloyHttpFiltering("api.doesNotContain=" + UPDATED_API, "api.doesNotContain=" + DEFAULT_API);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByEnvIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where env equals to
        defaultAlloyHttpFiltering("env.equals=" + DEFAULT_ENV, "env.equals=" + UPDATED_ENV);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByEnvIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where env in
        defaultAlloyHttpFiltering("env.in=" + DEFAULT_ENV + "," + UPDATED_ENV, "env.in=" + UPDATED_ENV);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByEnvIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where env is not null
        defaultAlloyHttpFiltering("env.specified=true", "env.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByEnvContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where env contains
        defaultAlloyHttpFiltering("env.contains=" + DEFAULT_ENV, "env.contains=" + UPDATED_ENV);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByEnvNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where env does not contain
        defaultAlloyHttpFiltering("env.doesNotContain=" + UPDATED_ENV, "env.doesNotContain=" + DEFAULT_ENV);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByHostnameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where hostname equals to
        defaultAlloyHttpFiltering("hostname.equals=" + DEFAULT_HOSTNAME, "hostname.equals=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByHostnameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where hostname in
        defaultAlloyHttpFiltering("hostname.in=" + DEFAULT_HOSTNAME + "," + UPDATED_HOSTNAME, "hostname.in=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByHostnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where hostname is not null
        defaultAlloyHttpFiltering("hostname.specified=true", "hostname.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByHostnameContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where hostname contains
        defaultAlloyHttpFiltering("hostname.contains=" + DEFAULT_HOSTNAME, "hostname.contains=" + UPDATED_HOSTNAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByHostnameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where hostname does not contain
        defaultAlloyHttpFiltering("hostname.doesNotContain=" + UPDATED_HOSTNAME, "hostname.doesNotContain=" + DEFAULT_HOSTNAME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByCreateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where createTime equals to
        defaultAlloyHttpFiltering("createTime.equals=" + DEFAULT_CREATE_TIME, "createTime.equals=" + UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByCreateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where createTime in
        defaultAlloyHttpFiltering(
            "createTime.in=" + DEFAULT_CREATE_TIME + "," + UPDATED_CREATE_TIME,
            "createTime.in=" + UPDATED_CREATE_TIME
        );
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByCreateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where createTime is not null
        defaultAlloyHttpFiltering("createTime.specified=true", "createTime.specified=false");
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where updateTime equals to
        defaultAlloyHttpFiltering("updateTime.equals=" + DEFAULT_UPDATE_TIME, "updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where updateTime in
        defaultAlloyHttpFiltering(
            "updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME,
            "updateTime.in=" + UPDATED_UPDATE_TIME
        );
    }

    @Test
    @Transactional
    void getAllAlloyHttpsByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        // Get all the alloyHttpList where updateTime is not null
        defaultAlloyHttpFiltering("updateTime.specified=true", "updateTime.specified=false");
    }

    private void defaultAlloyHttpFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAlloyHttpShouldBeFound(shouldBeFound);
        defaultAlloyHttpShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlloyHttpShouldBeFound(String filter) throws Exception {
        restAlloyHttpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alloyHttp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].modul").value(hasItem(DEFAULT_MODUL)))
            .andExpect(jsonPath("$.[*].api").value(hasItem(DEFAULT_API)))
            .andExpect(jsonPath("$.[*].env").value(hasItem(DEFAULT_ENV)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));

        // Check, that the count call also returns 1
        restAlloyHttpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlloyHttpShouldNotBeFound(String filter) throws Exception {
        restAlloyHttpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlloyHttpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAlloyHttp() throws Exception {
        // Get the alloyHttp
        restAlloyHttpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlloyHttp() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alloyHttp
        AlloyHttp updatedAlloyHttp = alloyHttpRepository.findById(alloyHttp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAlloyHttp are not directly saved in db
        em.detach(updatedAlloyHttp);
        updatedAlloyHttp
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .modul(UPDATED_MODUL)
            .api(UPDATED_API)
            .env(UPDATED_ENV)
            .hostname(UPDATED_HOSTNAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(updatedAlloyHttp);

        restAlloyHttpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alloyHttpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alloyHttpDTO))
            )
            .andExpect(status().isOk());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAlloyHttpToMatchAllProperties(updatedAlloyHttp);
    }

    @Test
    @Transactional
    void putNonExistingAlloyHttp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alloyHttp.setId(longCount.incrementAndGet());

        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlloyHttpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alloyHttpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alloyHttpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlloyHttp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alloyHttp.setId(longCount.incrementAndGet());

        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlloyHttpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alloyHttpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlloyHttp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alloyHttp.setId(longCount.incrementAndGet());

        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlloyHttpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlloyHttpWithPatch() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alloyHttp using partial update
        AlloyHttp partialUpdatedAlloyHttp = new AlloyHttp();
        partialUpdatedAlloyHttp.setId(alloyHttp.getId());

        partialUpdatedAlloyHttp.modul(UPDATED_MODUL).api(UPDATED_API).env(UPDATED_ENV).updateTime(UPDATED_UPDATE_TIME);

        restAlloyHttpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlloyHttp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAlloyHttp))
            )
            .andExpect(status().isOk());

        // Validate the AlloyHttp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlloyHttpUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAlloyHttp, alloyHttp),
            getPersistedAlloyHttp(alloyHttp)
        );
    }

    @Test
    @Transactional
    void fullUpdateAlloyHttpWithPatch() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alloyHttp using partial update
        AlloyHttp partialUpdatedAlloyHttp = new AlloyHttp();
        partialUpdatedAlloyHttp.setId(alloyHttp.getId());

        partialUpdatedAlloyHttp
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .modul(UPDATED_MODUL)
            .api(UPDATED_API)
            .env(UPDATED_ENV)
            .hostname(UPDATED_HOSTNAME)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restAlloyHttpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlloyHttp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAlloyHttp))
            )
            .andExpect(status().isOk());

        // Validate the AlloyHttp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlloyHttpUpdatableFieldsEquals(partialUpdatedAlloyHttp, getPersistedAlloyHttp(partialUpdatedAlloyHttp));
    }

    @Test
    @Transactional
    void patchNonExistingAlloyHttp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alloyHttp.setId(longCount.incrementAndGet());

        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlloyHttpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alloyHttpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alloyHttpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlloyHttp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alloyHttp.setId(longCount.incrementAndGet());

        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlloyHttpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alloyHttpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlloyHttp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        alloyHttp.setId(longCount.incrementAndGet());

        // Create the AlloyHttp
        AlloyHttpDTO alloyHttpDTO = alloyHttpMapper.toDto(alloyHttp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlloyHttpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(alloyHttpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AlloyHttp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlloyHttp() throws Exception {
        // Initialize the database
        insertedAlloyHttp = alloyHttpRepository.saveAndFlush(alloyHttp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the alloyHttp
        restAlloyHttpMockMvc
            .perform(delete(ENTITY_API_URL_ID, alloyHttp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return alloyHttpRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected AlloyHttp getPersistedAlloyHttp(AlloyHttp alloyHttp) {
        return alloyHttpRepository.findById(alloyHttp.getId()).orElseThrow();
    }

    protected void assertPersistedAlloyHttpToMatchAllProperties(AlloyHttp expectedAlloyHttp) {
        assertAlloyHttpAllPropertiesEquals(expectedAlloyHttp, getPersistedAlloyHttp(expectedAlloyHttp));
    }

    protected void assertPersistedAlloyHttpToMatchUpdatableProperties(AlloyHttp expectedAlloyHttp) {
        assertAlloyHttpAllUpdatablePropertiesEquals(expectedAlloyHttp, getPersistedAlloyHttp(expectedAlloyHttp));
    }
}
