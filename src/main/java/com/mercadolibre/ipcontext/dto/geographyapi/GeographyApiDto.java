package com.mercadolibre.ipcontext.dto.geographyapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GeographyApiDto(
        String name,
        String alpha2code,
        String alpha3code,
        String capital,
        List<String> altSpellings,
        String region,
        String subregion,
        Long population,
        Double latitude,
        Double longitude,
        String demonym,
        Double area,
        Double gini,
        List<String> timezones,
        List<String> borders,
        @JsonProperty("native_name") String nativeName,
        @JsonProperty("numeric_code") String numericCode,
        String flag,
        @JsonProperty("top_level_domains") List<String> topLevelDomains,
        @JsonProperty("calling_codes") List<String> callingCodes,
        List<Currency> currencies,
        List<Language> languages,
        @JsonProperty("regional_blocs") List<RegionalBloc> regionalBlocs
) {
    public record Currency(
            String name,
            String code,
            String symbol
    ) {
    }

    public record Language(
            String name,
            @JsonProperty("native_name") String nativeName,
            @JsonProperty("iso639_1") String iso6391,
            @JsonProperty("iso639_2") String iso6392
    ) {
    }

    public record RegionalBloc(
            String acronym,
            String name
    ) {
    }

}
