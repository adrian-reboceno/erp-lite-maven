package com.ikigaisoftware.erp_lite.infrastructure.persistence.rest.dtos;

public record UserDTO(
        Long id,
        String name,
        String username,
        String email,
        AddressDTO address,
        String phone,
        String website,
        CompanyDTO company
) {}
