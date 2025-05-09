package com.directa24.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

class DirectorServiceTest {

    private DirectorService directorService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        directorService = new DirectorService(restTemplate);
        // Set the value of MOVIE_API_URL
        ReflectionTestUtils.setField(directorService, "MOVIE_API_URL", "https://mocked-api.com/movies?page=");
    }

    @Test
    void testGetDirectors() {
        // Mock API response
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("total_pages", 1);
        mockResponse.put("data", Arrays.asList(
                Collections.singletonMap("Director", "Director A"),
                Collections.singletonMap("Director", "Director B"),
                Collections.singletonMap("Director", "Director A")
        ));

        // Mock the RestTemplate behavior
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        // Call the service method
        List<String> result = directorService.getDirectors(1);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals("Director A", result.get(0));

        // Verify interactions
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));
    }
}
