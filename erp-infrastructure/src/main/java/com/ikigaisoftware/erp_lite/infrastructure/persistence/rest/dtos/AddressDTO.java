package com.ikigaisoftware.erp_lite.infrastructure.persistence.rest.dtos;

public record AddressDTO(
        String street,
        String suite,
        String city,
        String zipcode,
        GeoDTO geo
) {}
