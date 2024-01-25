package myProject.service.cookie.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import myProject.service.cookie.dto.QueryDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *  CookieUtil 클래스는 HTTP 요청과 응답에 쿠키를 관리하는 유틸리티 메서드를 제공합니다.
 * 이 클래스는 쿠키 생성 및 검사 작업을 수행합니다.
 */
@Component
public class CookieUtill {
    public static final int ExpiredTimeSeconds = 3600;

    /**
     * hasCookie(HttpServletRequest request, String cookieName):
     * HTTP 요청에서 특정 이름의 쿠키를 찾고, 해당 쿠키가 존재하면 그 값을 반환합니다.
     * 쿠키가 없으면 빈 문자열을 반환합니다.
     */
    public static String hasCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("cookie name = " + cookie.getName());
                if (cookieName.equals(cookie.getName())) {

                    System.out.println("cookie find = " + cookie);
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    /**
     * createCookie(HttpServletResponse response):
     * 새로운 UUID 값을 가진 'SuperOfficeCookie' 이름의 쿠키를 생성하고,
     * 정해진 만료 시간(ExpiredTimeSeconds)으로 설정한 후 HTTP 응답에 추가합니다.
     */
    public String createCookie(QueryDTO queryDTO, HttpServletResponse response) {

        String uuid_cookie = UUID.randomUUID().toString();

        // 쿠키 문자열을 직접 조합합니다. 여기에 Path 설정을 포함시킵니다.
        String cookieString = String.format("A-Convenient-image=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=None",
                uuid_cookie, CookieUtill.ExpiredTimeSeconds);

        // 'Set-Cookie' 헤더에 쿠키 문자열을 추가합니다.
        response.setHeader("Set-Cookie", cookieString);

        System.out.println("create Cookie end and Cookie String = " + cookieString);
        return uuid_cookie;
    }

}