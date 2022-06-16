package org.example.common.starter.keycloak.bearer.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInfo {

    String username;
    String name;
    String givenName;
    String familyName;
    String middleName;
    String nickName;
    String preferredUsername;
    String profile;
    String picture;
    String website;
    String email;
    Boolean emailVerified;
    String gender;
    String birthdate;
    String zoneinfo;
    String locale;
    String phoneNumber;
    Boolean phoneNumberVerified;
    Set<String> roles;
    UserAddress address;
}
