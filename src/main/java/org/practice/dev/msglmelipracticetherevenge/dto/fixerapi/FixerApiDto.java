package org.practice.dev.msglmelipracticetherevenge.dto.fixerapi;

import lombok.Data;

import java.util.HashMap;

@Data
public class FixerApiDto {
    private Boolean success;
    private int timestamp;
    private String base;
    private String date;
    private HashMap<String, Double> rates;
}
