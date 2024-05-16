package de.alex.blogster_rest_api.security.service;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.alex.blogster_rest_api.security.authentication.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JwtToPrincipalConverterService {

    public UserPrincipal convert(DecodedJWT jwt) {
        return new UserPrincipal(
                UUID.fromString(jwt.getSubject()),
                jwt.getClaim("u").asString(),
                extractAuthoritiesFromClaim(jwt)
        );
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        Claim claim = jwt.getClaim("r");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
