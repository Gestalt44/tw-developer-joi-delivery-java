package com.tw.joi.delivery.controller;

import com.tw.joi.delivery.dto.response.InventoryHealthReport;
import com.tw.joi.delivery.exception.JoiNotFoundException;
import com.tw.joi.delivery.service.InventoryHealthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class) // Only loads the Controller, not the whole app
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // Spring's version of mock()
    private InventoryHealthService inventoryHealthService;

    @Test
    void shouldReturn200AndReportWhenStoreExists() throws Exception {
        // Arrange
        String storeId = "store101";
        var mockReport = new InventoryHealthReport(storeId, Set.of());

        when(inventoryHealthService.fetchInventoryReport(storeId)).thenReturn(mockReport);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/inventory/health") // No trailing slash
                        .param("storeId", storeId)) // Explicitly send it as a Query Param
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.outletId").value(storeId));
    }

    @Test
    void shouldReturn404WhenStoreDoesNotExist() throws Exception {
        // Arrange
        String storeId = "unknown";
        when(inventoryHealthService.fetchInventoryReport(storeId))
                .thenThrow(new JoiNotFoundException("Store not found"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/health/" + storeId))
                .andExpect(status().isNotFound());
    }
}