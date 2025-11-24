package com.capfer.bookapp.dto;

public record BookDTO(
        Long id,
        String title,
        String author,
        int publicationYear
) {
}
