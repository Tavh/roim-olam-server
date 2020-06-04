package com.roimolam.project

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.roimolam.project.controller.CatalogController
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.data.entities.CatalogItemPhotoEntity
import com.roimolam.project.enums.ItemType
import com.roimolam.project.logic.CatalogItemLogic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.Assert
import org.springframework.web.context.WebApplicationContext
import kotlin.test.assertEquals

@WebMvcTest(CatalogController::class)
class CatalogItemTests {

    @MockBean
    private lateinit var catalogItemLogic: CatalogItemLogic

    @MockBean
    private lateinit var photoDAL: PhotoDAL

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
          mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun getCatalogItemTest() {
        val catalogItem = CatalogItemEntity(1,
                "Item_1",
                10.0f,
                "item_description",
                ItemType.EYE_GLASSES,
                "SomeBrand",
                CatalogItemPhotoEntity(ByteArray(0), 0)
        )

        given(catalogItemLogic.getCatalogItem(catalogItem.id)).willReturn(catalogItem)

        val result = mockMvc.perform(get("/catalog/get-catalog-item/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andReturn()

        val jsonContent = result.response.contentAsString
        val objectMapper = jacksonObjectMapper()
        val output = objectMapper.readValue<CatalogItemEntity>(jsonContent, CatalogItemEntity::class.java)

        assertEquals(output.id, 1)
    }
}