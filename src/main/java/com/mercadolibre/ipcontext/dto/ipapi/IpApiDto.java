package com.mercadolibre.ipcontext.dto.ipapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record IpApiDto(
        String ip,
        String type,
        @JsonProperty("continent_code") String continentCode,
        @JsonProperty("continent_name") String continentName,
        @JsonProperty("country_code") String countryCode,
        @JsonProperty("country_name") String countryName,
        @JsonProperty("region_code") String regionCode,
        @JsonProperty("region_name") String regionName,
        String city,
        String zip,
        Double latitude,
        Double longitude,
        String msa,
        String dma,
        String radius,
        @JsonProperty("ip_routing_type") String ipRoutingType,
        @JsonProperty("connection_type") String connectionType,
        Location location
) {
    public record Location(
            @JsonProperty("geoname_id") Integer geoNameId,
            String capital,
            List<Language> languages,
            @JsonProperty("country_flag") String countryFlag,
            @JsonProperty("country_flag_emoji") String countryFlagEmoji,
            @JsonProperty("country_flag_emoji_unicode") String countryFlagEmojiUnicode,
            @JsonProperty("calling_code") String callingCode,
            @JsonProperty("is_eu") Boolean isEu
    ) {
        public record Language(
                String code,
                String name,
                @JsonProperty("native") String nativeName
        ) {
        }
    }
}
