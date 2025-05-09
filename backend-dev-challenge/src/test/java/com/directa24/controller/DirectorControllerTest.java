package com.directa24.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.directa24.service.DirectorService;


class DirectorControllerTest {

    @InjectMocks
    private DirectorController directorController;

    @Mock
    private DirectorService directorService;

    final List<String> mockDirectors = Arrays.asList("Director A", "Director B");

    @Test
    void testGetDirectors() {
        MockitoAnnotations.openMocks(this);

        // Mock service response
        
        when(directorService.getDirectors(1)).thenReturn(mockDirectors);

        // Call the controller method
        ResponseEntity<List<String>> response = directorController.getDirectors(1);

        // Verify the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockDirectors, response.getBody());
    }
}
