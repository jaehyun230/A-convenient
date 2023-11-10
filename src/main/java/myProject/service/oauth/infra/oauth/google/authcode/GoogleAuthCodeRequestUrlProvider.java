package myProject.service.oauth.infra.oauth.google.authcode;

import lombok.RequiredArgsConstructor;
import myProject.service.oauth.domain.OauthServerType;
import myProject.service.oauth.domain.authcode.AuthCodeRequestUrlProvider;
import myProject.service.oauth.infra.oauth.google.GoogleOauthConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider {

    private final GoogleOauthConfig googleOauthConfig;

    @Override
    public OauthServerType supportServer() {
        return OauthServerType.GOOGLE;
    }

    @Override
    public String provide() {
        return UriComponentsBuilder
                .fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("response_type", "code")
                .queryParam("client_id", googleOauthConfig.clientId())
                .queryParam("redirect_uri", googleOauthConfig.redirectUri())
                .queryParam("scope", String.join(",", googleOauthConfig.scope()))
                .build()
                .toUriString();
    }
}
