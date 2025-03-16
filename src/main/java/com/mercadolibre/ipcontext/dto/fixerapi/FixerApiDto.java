package com.mercadolibre.ipcontext.dto.fixerapi;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Map;

@Builder
public record FixerApiDto(Boolean success,
                          Long timestamp,
                          String base,
                          LocalDate date,
                          Map<String, Double> rates) {
}
