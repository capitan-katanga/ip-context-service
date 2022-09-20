package org.practice.dev.msglmelipracticetherevenge.dto.geographyapi;

import lombok.Data;

import java.util.ArrayList;

@Data
public class GeographyApiDto {
    private String name;
    private String alpha2code;
    private String alpha3code;
    private String capital;
    private ArrayList<String> alt_spellings;
    private String region;
    private String subregion;
    private int population;
    private double latitude;
    private double longitude;
    private String demonym;
    private double area;
    private double gini;
    private ArrayList<String> timezones;
    private ArrayList<String> borders;
    private String native_name;
    private String numeric_code;
    private String flag;
    private ArrayList<String> top_level_domains;
    private ArrayList<String> calling_codes;
    private ArrayList<CurrencyDto> currencies;
    private ArrayList<LanguageDto> languages;
    private ArrayList<RegionalBlocDto> regional_blocs;
}
