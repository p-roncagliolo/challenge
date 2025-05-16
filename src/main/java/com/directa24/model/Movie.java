package com.directa24.model;

public record Movie (
    String Title,
    String Year,
    String Rated,
    String Released,
    String Runtime,
    String Genre,
    String Director,
    String Writer,
    String Actors
) {}