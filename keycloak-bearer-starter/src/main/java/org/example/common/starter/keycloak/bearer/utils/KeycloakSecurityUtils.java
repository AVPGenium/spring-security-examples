package org.example.common.starter.keycloak.bearer.utils;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AddressClaimSet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.example.common.starter.keycloak.bearer.dto.UserAddress;
import org.example.common.starter.keycloak.bearer.dto.UserInfo;

@UtilityClass
public class KeycloakSecurityUtils {
    public Optional<AccessToken> getAccessToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(getAuthenticationToken())
                .map(KeycloakAuthenticationToken::getCredentials)
                .map(KeycloakSecurityContext.class::cast)
                .map(KeycloakSecurityContext::getToken);
    }

    private Function<Authentication, KeycloakAuthenticationToken> getAuthenticationToken() {
        return authentication -> {
            if (authentication instanceof KeycloakAuthenticationToken) {
                return (KeycloakAuthenticationToken) authentication;
            }
            return null;
        };
    }

    public Optional<String> getUserName() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(getAuthenticationToken())
                .map(KeycloakAuthenticationToken::getName);
    }

    public Optional<UserInfo> getUser() {
        return getAccessToken()
                .map(token -> UserInfo.builder()
                        .username(getUserName().orElse(null))
                        .roles(getRoles().orElse(null))
                        .name(token.getName())
                        .givenName(token.getGivenName())
                        .familyName(token.getFamilyName())
                        .middleName(token.getMiddleName())
                        .nickName(token.getNickName())
                        .preferredUsername(token.getPreferredUsername())
                        .profile(token.getProfile())
                        .picture(token.getPicture())
                        .website(token.getWebsite())
                        .email(token.getEmail())
                        .emailVerified(token.getEmailVerified())
                        .gender(token.getGender())
                        .birthdate(token.getBirthdate())
                        .zoneinfo(token.getZoneinfo())
                        .locale(token.getLocale())
                        .phoneNumber(token.getPhoneNumber())
                        .phoneNumberVerified(token.getPhoneNumberVerified())
                        .address(toAddress(token.getAddress()).orElse(null))
                        .build());
    }

    private Optional<UserAddress> toAddress(final AddressClaimSet address) {
        return Optional.ofNullable(address)
                .map(addressClaimSet -> UserAddress.builder()
                        .formattedAddress(addressClaimSet.getFormattedAddress())
                        .streetAddress(addressClaimSet.getStreetAddress())
                        .locality(addressClaimSet.getLocality())
                        .region(addressClaimSet.getRegion())
                        .postalCode(addressClaimSet.getPostalCode())
                        .country(addressClaimSet.getCountry())
                        .build());
    }

    public Optional<Set<String>> getRoles() {
        return getAccessToken()
                .map(AccessToken::getRealmAccess)
                .map(AccessToken.Access::getRoles);
    }
}
