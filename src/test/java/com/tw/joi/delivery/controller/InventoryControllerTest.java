package com.tw.joi.delivery.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tw.joi.delivery.dto.response.InventoryHealthReport;
import com.tw.joi.delivery.service.InventoryHealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    InventoryHealthService inventoryService;

    @Test
    void shouldReturnTheHealthOfTheStore() throws Exception {
        String storeId = "store101";
        String getUrl = "/inventory/health?storeId={" + storeId + "}";
        //add required mocking.
        InventoryHealthReport result = new InventoryHealthReport("store101", List.of());
        when(inventoryService.fetchInventoryHealthReport(storeId)).thenReturn(result);

        //act
        mockMvc.perform(MockMvcRequestBuilders.get(getUrl, "store101")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //put meaning assertions

    }
}