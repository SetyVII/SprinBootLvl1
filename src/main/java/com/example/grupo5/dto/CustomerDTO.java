package com.example.grupo5.dto;

public record CustomerDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String password,
        String address,
        String phoneNumber
) {}
