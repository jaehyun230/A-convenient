package myProject.service.oauth.service;

import lombok.RequiredArgsConstructor;
import myProject.service.oauth.domain.OauthMember;
import myProject.service.oauth.domain.OauthMemberRepository;
import myProject.service.oauth.domain.OauthServerType;
import myProject.service.oauth.domain.authcode.AuthCodeRequestUrlProviderComposite;
import myProject.service.oauth.domain.client.OauthMemberClientComposite;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final OauthMemberRepository oauthMemberRepository;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    public Long login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        OauthMember saved = oauthMemberRepository.findByOauthId(oauthMember.oauthId())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return saved.id();
    }
}
