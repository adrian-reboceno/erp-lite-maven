package com.ikigaisoftware.erp_lite.infrastructure.persistence.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyDTO(
        String name,
        @JsonProperty("catchPhrase")
        String cp,
        String bs
) {}
