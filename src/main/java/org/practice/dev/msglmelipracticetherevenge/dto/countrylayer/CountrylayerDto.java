package org.practice.dev.msglmelipracticetherevenge.dto.countrylayer;

import lombok.Data;

import java.util.ArrayList;

@Data
public class CountrylayerDto {
    private String name;
    private ArrayList<String> topLevelDomain;
    private String alpha2Code;
    private String alpha3Code;
    private ArrayList<String> callingCodes;
    private String capital;
    private ArrayList<String> altSpellings;
    private String region;
}
