package com.mercadolibre.ipcontext.util;

import com.mercadolibre.ipcontext.dto.fixerapi.FixerApiDto;
import com.mercadolibre.ipcontext.dto.geographyapi.GeographyApiDto;
import com.mercadolibre.ipcontext.dto.ipaddressblacklist.AddIpBlacklistDto;
import com.mercadolibre.ipcontext.dto.ipapi.IpApiDto;
import com.mercadolibre.ipcontext.entity.IpAddressBlacklist;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@UtilityClass
public class DataMock {

    public static String IP_ADDRESS = "181.165.139.141";

    public static FixerApiDto fixerApiDtoMock() {
        return FixerApiDto.builder()
                .success(true)
                .timestamp(1738355356L)
                .base("USD")
                .date(LocalDate.now())
                .rates(Map.of("ARS", 1000D))
                .build();
    }

    public static GeographyApiDto geographyApiDtoMock() {
        return GeographyApiDto.builder()
                .name("Argentina")
                .alpha2code("AR")
                .alpha3code("ARG")
                .capital("Buenos Aires")
                .altSpellings(List.of("AR", "Argentine Republic", "República Argentina"))
                .region("Americas")
                .subregion("South America")
                .population(43590400L)
                .latitude(-34.0)
                .longitude(-64.0)
                .demonym("Argentinean")
                .area(2780400.0)
                .gini(44.5)
                .timezones(List.of("UTC-03:00"))
                .borders(List.of("BOL", "BRA", "CHL", "PRY", "URY"))
                .nativeName("Argentina")
                .numericCode("032")
                .flag("http://assets.promptapi.com/flags/AR.svg")
                .topLevelDomains(List.of(".ar"))
                .callingCodes(List.of("54"))
                .currencies(List.of(GeographyApiDto.Currency.builder()
                        .name("Argentine peso")
                        .code("ARS")
                        .symbol("$")
                        .build()))
                .languages(List.of(GeographyApiDto.Language.builder()
                                .name("Spanish")
                                .nativeName("Español")
                                .iso6391("es")
                                .iso6392("spa")
                                .build(),
                        GeographyApiDto.Language.builder()
                                .name("Guaraní")
                                .nativeName("Avañe'ẽ")
                                .iso6391("gn")
                                .iso6392("grn")
                                .build()))
                .regionalBlocs(List.of(GeographyApiDto.RegionalBloc.builder()
                        .acronym("USAN")
                        .name("Union of South American Nations")
                        .build()))
                .build();

    }

    public static IpApiDto ipApiDtoMock() {
        return IpApiDto.builder()
                .ip("181.165.139.141")
                .type("ipv4")
                .continentCode("SA")
                .continentName("South America")
                .countryCode("AR")
                .countryName("Argentina")
                .regionCode("X")
                .regionName("Cordoba")
                .city("Córdoba")
                .zip("X5000")
                .latitude(-31.443849563598633)
                .longitude(-64.1590805053711)
                .msa(null)
                .dma(null)
                .radius(null)
                .ipRoutingType("fixed")
                .connectionType("cable")
                .location(IpApiDto.Location.builder()
                        .geoNameId(3860259)
                        .capital("Buenos Aires")
                        .languages(List.of(IpApiDto.Location.Language.builder()
                                        .code("es")
                                        .name("Spanish")
                                        .nativeName("Español")
                                        .build(),
                                IpApiDto.Location.Language.builder()
                                        .code("gn")
                                        .name("Guarani")
                                        .nativeName("Avañe'ẽ")
                                        .build()))
                        .countryFlag("https://assets.ipstack.com/flags/ar.svg")
                        .countryFlagEmoji("🇦🇷")
                        .countryFlagEmojiUnicode("U+1F1E6 U+1F1F7")
                        .callingCode("54")
                        .isEu(false)
                        .build())
                .build();
    }

    public static IpAddressBlacklist ipAddressBlacklistMock() {
        return IpAddressBlacklist.builder()
                .id(1)
                .ipAddress(IP_ADDRESS)
                .banDateActivated(LocalDateTime.now())
                .build();
    }

    public static AddIpBlacklistDto addIpBlacklistDtoMock() {
        return AddIpBlacklistDto.builder()
                .ipAddress(IP_ADDRESS)
                .build();
    }

}
