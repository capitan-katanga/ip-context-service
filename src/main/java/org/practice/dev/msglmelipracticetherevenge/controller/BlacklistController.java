package org.practice.dev.msglmelipracticetherevenge.controller;

import lombok.AllArgsConstructor;
import org.practice.dev.msglmelipracticetherevenge.dto.ipaddressblacklist.AddIpBlacklistDto;
import org.practice.dev.msglmelipracticetherevenge.dto.ipaddressblacklist.GetIpBlacklistDto;
import org.practice.dev.msglmelipracticetherevenge.service.IpAddressBlacklistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class BlacklistController {
    public final IpAddressBlacklistService ipAddressBlacklistService;

    @PostMapping("/banIp")
    public ResponseEntity<GetIpBlacklistDto> adIpAddressToBlacklist(@Valid @RequestBody AddIpBlacklistDto addIpBlacklistDto) {
        return new ResponseEntity<>(ipAddressBlacklistService.banIpAddress(addIpBlacklistDto.getIpAddress()), HttpStatus.OK);
    }

}
