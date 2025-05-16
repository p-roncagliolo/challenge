package com.directa24.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.directa24.client.MovieApiClient;
import com.directa24.exception.InvalidPageException;
import com.directa24.exception.ServiceException;
import com.directa24.model.Movie;
import com.directa24.model.MovieResponse;
import com.directa24.utils.DirectorUtils;


@PrepareForTest(DirectorUtils.class)
class DirectorServiceImplTest {

    private DirectorServiceImpl directorService;

    @Mock
    private MovieApiClient movieApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        directorService = new DirectorServiceImpl(movieApiClient);
    }

    @Test
    public void testGetDirectors_Success() {
        // Mock API response
        MovieResponse mockResponse = new MovieResponse(1, 10, 2, 1, Arrays.asList(
            new Movie("American Sniper", "2014", "R", "16 Jan 2015", "133 min", "Action, Biography, Drama", "Clint Eastwood", "Jason Hall, Chris Kyle, Scott McEwen", "Bradley Cooper, Sienna Miller, Kyle Gallner"),
            new Movie("Magic in the Moonlight", "2014", "PG-13", "15 Aug 2014", "97 min", "Comedy, Drama, Romance", "Woody Allen", "Woody Allen", "Colin Firth, Emma Stone, Marcia Gay Harden"),
            new Movie("The Irishman", "2019", "R", "27 Nov 2019", "209 min", "Biography, Crime, Drama", "Martin Scorsese", "Steven Zaillian, Charles Brandt", "Robert De Niro, Al Pacino, Joe Pesci")
        ));

        // Mock the behavior of MovieApiClient
        when(movieApiClient.fetchMovies(eq(1))).thenReturn(mockResponse);

        // Mock static methods of DirectorUtils
        PowerMockito.mockStatic(DirectorUtils.class);
        PowerMockito.when(DirectorUtils.isInvalidResponse(mockResponse)).thenReturn(false);
        PowerMockito.when(DirectorUtils.extractResponse(eq(1), anyMap())).thenReturn(List.of("Director A"));

        // Call the service method
        List<String> result = directorService.getDirectors(1);

        // Verify the result
        assertEquals(1, result.size());
        assertEquals("Director A", result.get(0));
    }

    @Test
    void testGetDirectors_InvalidPageException() {
        // Mock API response to throw an exception
        when(movieApiClient.fetchMovies(eq(1))).thenThrow(new InvalidPageException("Invalid page"));

        // Verify that the exception is thrown
        InvalidPageException exception = assertThrows(InvalidPageException.class, () -> directorService.getDirectors(1));
        assertEquals("Invalid page", exception.getMessage());

        // Verify interactions
        verify(movieApiClient, times(1)).fetchMovies(eq(1));
    }

    @Test
    void testGetDirectors_ServiceException() {
        // Mock API response to throw a generic exception
        when(movieApiClient.fetchMovies(eq(1))).thenThrow(new RuntimeException("Unexpected error"));

        // Verify that the exception is wrapped in a ServiceException
        ServiceException exception = assertThrows(ServiceException.class, () -> directorService.getDirectors(1));
        assertEquals("Unexpected error", exception.getMessage());

        // Verify interactions
        verify(movieApiClient, times(1)).fetchMovies(eq(1));
    }
}
