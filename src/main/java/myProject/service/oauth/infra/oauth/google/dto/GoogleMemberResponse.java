package myProject.service.oauth.infra.oauth.google.dto;

import static myProject.service.oauth.domain.OauthServerType.GOOGLE;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import myProject.service.oauth.domain.OauthId;
import myProject.service.oauth.domain.OauthMember;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberResponse(
        String id,
        String name,
        String givenName,
        String picture,
        String locale
) {

    public OauthMember toDomain() {
        return OauthMember.builder()
                .oauthId(new OauthId(id, GOOGLE))
                .nickname(givenName)
                .profileImageUrl(picture)
                .build();
    }
}
