package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Ingredient;
import com.mycompany.myapp.repository.IngredientRepository;
import com.mycompany.myapp.service.dto.IngredientDTO;
import com.mycompany.myapp.service.mapper.IngredientMapper;
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
 * Integration tests for the {@link IngredientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngredientResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ingredients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngredientMockMvc;

    private Ingredient ingredient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createEntity(EntityManager em) {
        Ingredient ingredient = new Ingredient()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .unit(DEFAULT_UNIT)
            .countryName(DEFAULT_COUNTRY_NAME);
        return ingredient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ingredient createUpdatedEntity(EntityManager em) {
        Ingredient ingredient = new Ingredient()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT)
            .countryName(UPDATED_COUNTRY_NAME);
        return ingredient;
    }

    @BeforeEach
    public void initTest() {
        ingredient = createEntity(em);
    }

    @Test
    @Transactional
    void createIngredient() throws Exception {
        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();
        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);
        restIngredientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isCreated());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate + 1);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testIngredient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredient.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testIngredient.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testIngredient.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void createIngredientWithExistingId() throws Exception {
        // Create the Ingredient with an existing ID
        ingredient.setId(1L);
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        int databaseSizeBeforeCreate = ingredientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngredientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngredients() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get all the ingredientList
        restIngredientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)));
    }

    @Test
    @Transactional
    void getIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        // Get the ingredient
        restIngredientMockMvc
            .perform(get(ENTITY_API_URL_ID, ingredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingredient.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME));
    }

    @Test
    @Transactional
    void getNonExistingIngredient() throws Exception {
        // Get the ingredient
        restIngredientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Update the ingredient
        Ingredient updatedIngredient = ingredientRepository.findById(ingredient.getId()).get();
        // Disconnect from session so that the updates on updatedIngredient are not directly saved in db
        em.detach(updatedIngredient);
        updatedIngredient
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT)
            .countryName(UPDATED_COUNTRY_NAME);
        IngredientDTO ingredientDTO = ingredientMapper.toDto(updatedIngredient);

        restIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testIngredient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredient.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testIngredient.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testIngredient.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void putNonExistingIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();
        ingredient.setId(count.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingredientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();
        ingredient.setId(count.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();
        ingredient.setId(count.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ingredientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngredientWithPatch() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Update the ingredient using partial update
        Ingredient partialUpdatedIngredient = new Ingredient();
        partialUpdatedIngredient.setId(ingredient.getId());

        partialUpdatedIngredient.uuid(UPDATED_UUID).quantity(UPDATED_QUANTITY).countryName(UPDATED_COUNTRY_NAME);

        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredient))
            )
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testIngredient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIngredient.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testIngredient.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testIngredient.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void fullUpdateIngredientWithPatch() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();

        // Update the ingredient using partial update
        Ingredient partialUpdatedIngredient = new Ingredient();
        partialUpdatedIngredient.setId(ingredient.getId());

        partialUpdatedIngredient
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT)
            .countryName(UPDATED_COUNTRY_NAME);

        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngredient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIngredient))
            )
            .andExpect(status().isOk());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
        Ingredient testIngredient = ingredientList.get(ingredientList.size() - 1);
        assertThat(testIngredient.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testIngredient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIngredient.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testIngredient.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testIngredient.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();
        ingredient.setId(count.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingredientDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();
        ingredient.setId(count.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ingredientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngredient() throws Exception {
        int databaseSizeBeforeUpdate = ingredientRepository.findAll().size();
        ingredient.setId(count.incrementAndGet());

        // Create the Ingredient
        IngredientDTO ingredientDTO = ingredientMapper.toDto(ingredient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngredientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ingredientDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ingredient in the database
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngredient() throws Exception {
        // Initialize the database
        ingredientRepository.saveAndFlush(ingredient);

        int databaseSizeBeforeDelete = ingredientRepository.findAll().size();

        // Delete the ingredient
        restIngredientMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingredient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        assertThat(ingredientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
