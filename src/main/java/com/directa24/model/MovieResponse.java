package com.directa24.model;

import java.util.List;

public record MovieResponse(
    int page,
    int per_page,
    int total,
    int total_pages,
    List<Movie> data
) {}