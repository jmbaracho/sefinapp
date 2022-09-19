package br.com.sefin.web.rest;

import static br.com.sefin.web.rest.TestUtil.sameInstant;
import static br.com.sefin.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.Room;
import br.com.sefin.repository.RoomRepository;
import br.com.sefin.service.dto.RoomDTO;
import br.com.sefin.service.mapper.RoomMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RoomResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoomResourceIT {

    private static final Integer DEFAULT_NUMBER_OF_PEOPLE = 1;
    private static final Integer UPDATED_NUMBER_OF_PEOPLE = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_PERIOD_TYPE = 1;
    private static final Integer UPDATED_PERIOD_TYPE = 2;

    private static final LocalDate DEFAULT_CHECK_IN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_IN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHECK_OUT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_OUT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoomMockMvc;

    private Room room;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity(EntityManager em) {
        Room room = new Room()
            .numberOfPeople(DEFAULT_NUMBER_OF_PEOPLE)
            .price(DEFAULT_PRICE)
            .periodType(DEFAULT_PERIOD_TYPE)
            .checkInDate(DEFAULT_CHECK_IN_DATE)
            .checkOutDate(DEFAULT_CHECK_OUT_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return room;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createUpdatedEntity(EntityManager em) {
        Room room = new Room()
            .numberOfPeople(UPDATED_NUMBER_OF_PEOPLE)
            .price(UPDATED_PRICE)
            .periodType(UPDATED_PERIOD_TYPE)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return room;
    }

    @BeforeEach
    public void initTest() {
        room = createEntity(em);
    }

    @Test
    @Transactional
    void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();
        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);
        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getNumberOfPeople()).isEqualTo(DEFAULT_NUMBER_OF_PEOPLE);
        assertThat(testRoom.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testRoom.getPeriodType()).isEqualTo(DEFAULT_PERIOD_TYPE);
        assertThat(testRoom.getCheckInDate()).isEqualTo(DEFAULT_CHECK_IN_DATE);
        assertThat(testRoom.getCheckOutDate()).isEqualTo(DEFAULT_CHECK_OUT_DATE);
        assertThat(testRoom.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRoom.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createRoomWithExistingId() throws Exception {
        // Create the Room with an existing ID
        room.setId(1L);
        RoomDTO roomDTO = roomMapper.toDto(room);

        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get all the roomList
        restRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfPeople").value(hasItem(DEFAULT_NUMBER_OF_PEOPLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].periodType").value(hasItem(DEFAULT_PERIOD_TYPE)))
            .andExpect(jsonPath("$.[*].checkInDate").value(hasItem(DEFAULT_CHECK_IN_DATE.toString())))
            .andExpect(jsonPath("$.[*].checkOutDate").value(hasItem(DEFAULT_CHECK_OUT_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        // Get the room
        restRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId().intValue()))
            .andExpect(jsonPath("$.numberOfPeople").value(DEFAULT_NUMBER_OF_PEOPLE))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.periodType").value(DEFAULT_PERIOD_TYPE))
            .andExpect(jsonPath("$.checkInDate").value(DEFAULT_CHECK_IN_DATE.toString()))
            .andExpect(jsonPath("$.checkOutDate").value(DEFAULT_CHECK_OUT_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findById(room.getId()).get();
        // Disconnect from session so that the updates on updatedRoom are not directly saved in db
        em.detach(updatedRoom);
        updatedRoom
            .numberOfPeople(UPDATED_NUMBER_OF_PEOPLE)
            .price(UPDATED_PRICE)
            .periodType(UPDATED_PERIOD_TYPE)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        RoomDTO roomDTO = roomMapper.toDto(updatedRoom);

        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getNumberOfPeople()).isEqualTo(UPDATED_NUMBER_OF_PEOPLE);
        assertThat(testRoom.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testRoom.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testRoom.getCheckInDate()).isEqualTo(UPDATED_CHECK_IN_DATE);
        assertThat(testRoom.getCheckOutDate()).isEqualTo(UPDATED_CHECK_OUT_DATE);
        assertThat(testRoom.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRoom.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .price(UPDATED_PRICE)
            .periodType(UPDATED_PERIOD_TYPE)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getNumberOfPeople()).isEqualTo(DEFAULT_NUMBER_OF_PEOPLE);
        assertThat(testRoom.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testRoom.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testRoom.getCheckInDate()).isEqualTo(UPDATED_CHECK_IN_DATE);
        assertThat(testRoom.getCheckOutDate()).isEqualTo(DEFAULT_CHECK_OUT_DATE);
        assertThat(testRoom.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRoom.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateRoomWithPatch() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room using partial update
        Room partialUpdatedRoom = new Room();
        partialUpdatedRoom.setId(room.getId());

        partialUpdatedRoom
            .numberOfPeople(UPDATED_NUMBER_OF_PEOPLE)
            .price(UPDATED_PRICE)
            .periodType(UPDATED_PERIOD_TYPE)
            .checkInDate(UPDATED_CHECK_IN_DATE)
            .checkOutDate(UPDATED_CHECK_OUT_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoom))
            )
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getNumberOfPeople()).isEqualTo(UPDATED_NUMBER_OF_PEOPLE);
        assertThat(testRoom.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testRoom.getPeriodType()).isEqualTo(UPDATED_PERIOD_TYPE);
        assertThat(testRoom.getCheckInDate()).isEqualTo(UPDATED_CHECK_IN_DATE);
        assertThat(testRoom.getCheckOutDate()).isEqualTo(UPDATED_CHECK_OUT_DATE);
        assertThat(testRoom.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRoom.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roomDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();
        room.setId(count.incrementAndGet());

        // Create the Room
        RoomDTO roomDTO = roomMapper.toDto(room);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoomMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.saveAndFlush(room);

        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Delete the room
        restRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, room.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
