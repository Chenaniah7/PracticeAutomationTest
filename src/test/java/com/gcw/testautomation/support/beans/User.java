package com.gcw.testautomation.support.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("website")
    private String website;
    @JsonProperty("company")
    private Company company;

    @NoArgsConstructor
    @Data
    public static class Address {
        @JsonProperty("street")
        private String street;
        @JsonProperty("suite")
        private String suite;
        @JsonProperty("city")
        private String city;
        @JsonProperty("zipcode")
        private String zipcode;
        @JsonProperty("geo")
        private Geo geo;

        @NoArgsConstructor
        @Data
        public static class Geo {
            @JsonProperty("lat")
            private String lat;
            @JsonProperty("lng")
            private String lng;
        }
    }

    @NoArgsConstructor
    @Data
    public static class Company {
        @JsonProperty("name")
        private String name;
        @JsonProperty("catchPhrase")
        private String catchPhrase;
        @JsonProperty("bs")
        private String bs;
    }
}
