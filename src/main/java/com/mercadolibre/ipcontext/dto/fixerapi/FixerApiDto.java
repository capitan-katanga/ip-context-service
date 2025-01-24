package com.mercadolibre.ipcontext.dto.fixerapi;

import java.time.LocalDate;
import java.util.Map;


public record FixerApiDto(Boolean success,
                          Long timestamp,
                          String base,
                          LocalDate date,
                          Map<String, Double> rates) {
}
