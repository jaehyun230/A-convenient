package myProject.service.oauth.domain.authcode;

import myProject.service.oauth.domain.OauthServerType;

public interface AuthCodeRequestUrlProvider {

    OauthServerType supportServer();

    String provide();
}
