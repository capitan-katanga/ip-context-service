package org.practice.dev.msglmelipracticetherevenge.controller;

import lombok.AllArgsConstructor;
import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.CountryInformationDto;
import org.practice.dev.msglmelipracticetherevenge.dto.countryinformation.GetInformationDto;
import org.practice.dev.msglmelipracticetherevenge.service.CountryInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class CountryInformationController {

    private final CountryInformationService countryInformationService;

    @GetMapping("/getIpInfo")
    public ResponseEntity<CountryInformationDto> getIpInfo(@Valid @RequestBody GetInformationDto getInformationDto) {
        return new ResponseEntity<>(countryInformationService.getCountryInformation(getInformationDto.getIpAddress()), HttpStatus.OK);
    }
}
