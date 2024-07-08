package com.example.demo;

import jakarta.validation.constraints.NotBlank;

public record Request(@NotBlank String test) {
}
