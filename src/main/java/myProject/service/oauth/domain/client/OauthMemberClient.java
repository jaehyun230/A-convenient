package myProject.service.oauth.domain.client;

import myProject.service.oauth.domain.OauthMember;
import myProject.service.oauth.domain.OauthServerType;

public interface OauthMemberClient {

    OauthServerType supportServer();

    OauthMember fetch(String code);
}
