package com.directa24.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.directa24.model.MovieResponse;

@Component
public class MovieApiClient {

    @Value("${movie.api.url}")
    private String movieApiUrl;

    private final RestTemplate restTemplate;

    public MovieApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MovieResponse fetchMovies(int page) {
        String url = movieApiUrl + page;
        ResponseEntity<MovieResponse> response = restTemplate.getForEntity(url, MovieResponse.class);

        return response.getBody();
    }
}