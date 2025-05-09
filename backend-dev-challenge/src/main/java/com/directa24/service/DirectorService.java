package com.directa24.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DirectorService {
    @Value("${movie.api.url}")
    private String MOVIE_API_URL;

    private final RestTemplate restTemplate;

    // Constructor injection for RestTemplate
    public DirectorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method fetches movie data from an external API and counts the number of movies directed by each director.
     * It returns a list of directors who have directed more than a specified threshold of movies.
     *
     * @param threshold The minimum number of movies a director must have directed to be included in the result.
     * @return A list of directors who have directed more than the specified threshold of movies.
     */
    public List<String> getDirectors(int threshold) {
        
        Map<String, Integer> directorCount = new HashMap<>();
        int page = 1;
        int totalPages = 1;

        while (page <= totalPages) {
            String url = MOVIE_API_URL + page;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("data")) {
                break;
            }

            if (scanDirectors(response, directorCount)) {
                break;
            }

            totalPages = (int) response.get("total_pages");
            page++;
        }

        return extractResponse(threshold, directorCount);
    }

    /**
     * This method scans the movie data for directors and counts the number of movies directed by each director.
     *
     * @param response The response from the movie API containing movie data.
     * @param directorCount A map to store the count of movies directed by each director.
     * @return A boolean indicating whether the scan was successful or not.
     */
    private boolean scanDirectors(Map<String, Object> response, Map<String, Integer> directorCount) {
        List<Map<String, Object>> movies = (List<Map<String, Object>>) response.get("data");
        if (movies.isEmpty()) {
            return true;
        }
        for (Map<String, Object> movie : movies) {
            String director = (String) movie.get("Director");
            if (director != null) {
                directorCount.put(director, directorCount.getOrDefault(director, 0) + 1);
            }
        }
        return false;
    }

    /**
     * This method extracts the directors who have directed more than the specified threshold of movies.
     *
     * @param threshold The minimum number of movies a director must have directed to be included in the result.
     * @param directorCount A map containing the count of movies directed by each director.
     * @return A list of directors who have directed more than the specified threshold of movies.
     */
    private List<String> extractResponse(int threshold, Map<String, Integer> directorCount) {
        return directorCount.entrySet().stream()
                .filter(entry -> entry.getValue() > threshold)
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
}