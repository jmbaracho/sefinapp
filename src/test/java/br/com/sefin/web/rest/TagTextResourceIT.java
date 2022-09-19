package br.com.sefin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.sefin.IntegrationTest;
import br.com.sefin.domain.TagText;
import br.com.sefin.repository.TagTextRepository;
import br.com.sefin.service.TagTextService;
import br.com.sefin.service.dto.TagTextDTO;
import br.com.sefin.service.mapper.TagTextMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TagTextResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TagTextResourceIT {

    private static final Integer DEFAULT_START_POSITION = 1;
    private static final Integer UPDATED_START_POSITION = 2;

    private static final Integer DEFAULT_END_POSITION = 1;
    private static final Integer UPDATED_END_POSITION = 2;

    private static final String ENTITY_API_URL = "/api/tag-texts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TagTextRepository tagTextRepository;

    @Mock
    private TagTextRepository tagTextRepositoryMock;

    @Autowired
    private TagTextMapper tagTextMapper;

    @Mock
    private TagTextService tagTextServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagTextMockMvc;

    private TagText tagText;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagText createEntity(EntityManager em) {
        TagText tagText = new TagText().startPosition(DEFAULT_START_POSITION).endPosition(DEFAULT_END_POSITION);
        return tagText;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagText createUpdatedEntity(EntityManager em) {
        TagText tagText = new TagText().startPosition(UPDATED_START_POSITION).endPosition(UPDATED_END_POSITION);
        return tagText;
    }

    @BeforeEach
    public void initTest() {
        tagText = createEntity(em);
    }

    @Test
    @Transactional
    void createTagText() throws Exception {
        int databaseSizeBeforeCreate = tagTextRepository.findAll().size();
        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);
        restTagTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagTextDTO)))
            .andExpect(status().isCreated());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeCreate + 1);
        TagText testTagText = tagTextList.get(tagTextList.size() - 1);
        assertThat(testTagText.getStartPosition()).isEqualTo(DEFAULT_START_POSITION);
        assertThat(testTagText.getEndPosition()).isEqualTo(DEFAULT_END_POSITION);
    }

    @Test
    @Transactional
    void createTagTextWithExistingId() throws Exception {
        // Create the TagText with an existing ID
        tagText.setId(1L);
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        int databaseSizeBeforeCreate = tagTextRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagTextDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTagTexts() throws Exception {
        // Initialize the database
        tagTextRepository.saveAndFlush(tagText);

        // Get all the tagTextList
        restTagTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagText.getId().intValue())))
            .andExpect(jsonPath("$.[*].startPosition").value(hasItem(DEFAULT_START_POSITION)))
            .andExpect(jsonPath("$.[*].endPosition").value(hasItem(DEFAULT_END_POSITION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTagTextsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tagTextServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTagTextMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tagTextServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTagTextsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tagTextServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTagTextMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tagTextRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTagText() throws Exception {
        // Initialize the database
        tagTextRepository.saveAndFlush(tagText);

        // Get the tagText
        restTagTextMockMvc
            .perform(get(ENTITY_API_URL_ID, tagText.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tagText.getId().intValue()))
            .andExpect(jsonPath("$.startPosition").value(DEFAULT_START_POSITION))
            .andExpect(jsonPath("$.endPosition").value(DEFAULT_END_POSITION));
    }

    @Test
    @Transactional
    void getNonExistingTagText() throws Exception {
        // Get the tagText
        restTagTextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTagText() throws Exception {
        // Initialize the database
        tagTextRepository.saveAndFlush(tagText);

        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();

        // Update the tagText
        TagText updatedTagText = tagTextRepository.findById(tagText.getId()).get();
        // Disconnect from session so that the updates on updatedTagText are not directly saved in db
        em.detach(updatedTagText);
        updatedTagText.startPosition(UPDATED_START_POSITION).endPosition(UPDATED_END_POSITION);
        TagTextDTO tagTextDTO = tagTextMapper.toDto(updatedTagText);

        restTagTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tagTextDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tagTextDTO))
            )
            .andExpect(status().isOk());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
        TagText testTagText = tagTextList.get(tagTextList.size() - 1);
        assertThat(testTagText.getStartPosition()).isEqualTo(UPDATED_START_POSITION);
        assertThat(testTagText.getEndPosition()).isEqualTo(UPDATED_END_POSITION);
    }

    @Test
    @Transactional
    void putNonExistingTagText() throws Exception {
        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();
        tagText.setId(count.incrementAndGet());

        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tagTextDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tagTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTagText() throws Exception {
        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();
        tagText.setId(count.incrementAndGet());

        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tagTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTagText() throws Exception {
        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();
        tagText.setId(count.incrementAndGet());

        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagTextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tagTextDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTagTextWithPatch() throws Exception {
        // Initialize the database
        tagTextRepository.saveAndFlush(tagText);

        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();

        // Update the tagText using partial update
        TagText partialUpdatedTagText = new TagText();
        partialUpdatedTagText.setId(tagText.getId());

        partialUpdatedTagText.startPosition(UPDATED_START_POSITION);

        restTagTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTagText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTagText))
            )
            .andExpect(status().isOk());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
        TagText testTagText = tagTextList.get(tagTextList.size() - 1);
        assertThat(testTagText.getStartPosition()).isEqualTo(UPDATED_START_POSITION);
        assertThat(testTagText.getEndPosition()).isEqualTo(DEFAULT_END_POSITION);
    }

    @Test
    @Transactional
    void fullUpdateTagTextWithPatch() throws Exception {
        // Initialize the database
        tagTextRepository.saveAndFlush(tagText);

        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();

        // Update the tagText using partial update
        TagText partialUpdatedTagText = new TagText();
        partialUpdatedTagText.setId(tagText.getId());

        partialUpdatedTagText.startPosition(UPDATED_START_POSITION).endPosition(UPDATED_END_POSITION);

        restTagTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTagText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTagText))
            )
            .andExpect(status().isOk());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
        TagText testTagText = tagTextList.get(tagTextList.size() - 1);
        assertThat(testTagText.getStartPosition()).isEqualTo(UPDATED_START_POSITION);
        assertThat(testTagText.getEndPosition()).isEqualTo(UPDATED_END_POSITION);
    }

    @Test
    @Transactional
    void patchNonExistingTagText() throws Exception {
        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();
        tagText.setId(count.incrementAndGet());

        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tagTextDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tagTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTagText() throws Exception {
        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();
        tagText.setId(count.incrementAndGet());

        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tagTextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTagText() throws Exception {
        int databaseSizeBeforeUpdate = tagTextRepository.findAll().size();
        tagText.setId(count.incrementAndGet());

        // Create the TagText
        TagTextDTO tagTextDTO = tagTextMapper.toDto(tagText);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTagTextMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tagTextDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TagText in the database
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTagText() throws Exception {
        // Initialize the database
        tagTextRepository.saveAndFlush(tagText);

        int databaseSizeBeforeDelete = tagTextRepository.findAll().size();

        // Delete the tagText
        restTagTextMockMvc
            .perform(delete(ENTITY_API_URL_ID, tagText.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TagText> tagTextList = tagTextRepository.findAll();
        assertThat(tagTextList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
