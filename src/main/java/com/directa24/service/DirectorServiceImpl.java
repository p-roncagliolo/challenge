package com.directa24.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.directa24.client.MovieApiClient;
import com.directa24.exception.InvalidPageException;
import com.directa24.exception.ServiceException;
import com.directa24.model.MovieResponse;
import com.directa24.utils.DirectorUtils;

@Service
public class DirectorServiceImpl implements DirectorService {

    private static final Logger logger = LoggerFactory.getLogger(DirectorServiceImpl.class);
    private final MovieApiClient movieApiClient;

    public DirectorServiceImpl(MovieApiClient movieApiClient) {
        this.movieApiClient = movieApiClient;
    }

    @Override
    public List<String> getDirectors(int threshold) {
        Map<String, Integer> directorCount = new HashMap<>();
        processPages(directorCount);
        return DirectorUtils.extractResponse(threshold, directorCount);
    }
    
    private void processPages(Map<String, Integer> directorCount) {
        int page = 1;
        int totalPages = 1;
    
        while (page <= totalPages) {
            try {
                MovieResponse response = movieApiClient.fetchMovies(page);
                if (DirectorUtils.isInvalidResponse(response)) {
                    break;
                }
                DirectorUtils.updateDirectorCount(response, directorCount);
                totalPages = response.total_pages();
                logger.info("Page {} processed", page);
                page++;
            } catch (HttpClientErrorException e) {
                logger.error("HTTP error while fetching page {}: {}", page, e.getMessage());
                throw new InvalidPageException("HTTP error while fetching page " + page + ": " + e.getMessage());
            } catch (Exception e) {
                logger.error("Unexpected error while processing page {}: {}", page, e.getMessage());
                throw new ServiceException("Unexpected error while processing pages", e);
            }   
        }

        logger.info("Total processed pages: {} ", totalPages);
    }

}