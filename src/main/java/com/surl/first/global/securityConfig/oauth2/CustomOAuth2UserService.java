package com.surl.first.global.securityConfig.oauth2;

import com.surl.first.domain.member.member.entity.Member;
import com.surl.first.domain.member.member.service.MemberService;
import com.surl.first.global.securityConfig.SecurityUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    // OAuthLogin이 성공하여 userinfo를 불러오면 이것이 실행된다.
    @Override
    @Transactional
    public SecurityUser loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(request);

        String oauthId = oAuth2User.getName();
        Map<String,Object> attributes = oAuth2User.getAttributes();
        Map properties = (Map) attributes.get("properties");

        String nickname = (String) properties.get("nickname");
        String providerTypeCode = request.getClientRegistration().getClientId().toUpperCase();
        String username = providerTypeCode + "__%s".formatted(oauthId);
        Member member = memberService.whenSocialLogin(providerTypeCode, username,nickname);
        return new SecurityUser(member.getId(), member.getUsername(), member.getPassword(), member.getName(), member.getAuthorities());
    }
}
