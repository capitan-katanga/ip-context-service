package com.mercadolibre.ipcontext.controller;

import lombok.AllArgsConstructor;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.GetIpBlacklistDto;
import com.mercadolibre.ipcontext.service.IpBlacklistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@AllArgsConstructor
public class BlacklistController {
    public final IpBlacklistService ipBlacklistService;

    @PostMapping("/banIp")
    public ResponseEntity<GetIpBlacklistDto> adIpAddressToBlacklist(@Valid @RequestBody AddIpBlacklistDto addIpBlacklistDto) {
        return new ResponseEntity<>(ipBlacklistService.banIpAddress(addIpBlacklistDto.getIpAddress()), HttpStatus.OK);
    }

}
