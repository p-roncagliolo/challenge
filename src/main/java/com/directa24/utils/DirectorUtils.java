package com.directa24.utils;

import java.util.List;
import java.util.Map;

import com.directa24.model.Movie;
import com.directa24.model.MovieResponse;

public class DirectorUtils {

    private DirectorUtils() {
        // Private constructor to prevent instantiation
    }
    
        
    public static boolean isInvalidResponse(MovieResponse response) {
        return response == null || response.data() == null || response.data().isEmpty();
    }

    public static void updateDirectorCount(MovieResponse response, Map<String, Integer> directorCount) {
        List<Movie> movies = response.data();

        for (Movie movie : movies) {
            String director = movie.Director();
            if (director != null) {
                directorCount.put(director, directorCount.getOrDefault(director, 0) + 1);
            }
        }
    }

    public static List<String> extractResponse(int threshold, Map<String, Integer> directorCount) {
        return directorCount.entrySet().stream()
                .filter(entry -> entry.getValue() > threshold)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }
}
