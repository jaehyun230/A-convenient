package myProject.service.cookie.repository;

import myProject.service.cookie.entity.CookieEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CookieRepositoryTest {

    @Autowired
    private CookieRepository cookieRepository;

    @Test
    public void testFindByTimestampBefore() {
        // 데이터 준비
        LocalDateTime now = LocalDateTime.now();
        CookieEntity cookie1 = CookieEntity.builder()
                .cookieValue("cookie1")
                .timestamp(now.minusDays(1)) // 하루 전 날짜 설정
                .target("target1")
                .build();
        cookieRepository.save(cookie1);

        CookieEntity cookie2 = CookieEntity.builder()
                .cookieValue("cookie2")
                .timestamp(now.plusDays(1)) // 하루 후 날짜 설정
                .target("target2")
                .build();
        cookieRepository.save(cookie2);

        // 테스트 실행
        List<CookieEntity> results = cookieRepository.findByTimestampBefore(now);

        // 검증
        assertThat(results).containsOnly(cookie1);
    }

    @Test
    public void testDeleteByTimestampBefore() {
        // 데이터 준비
        LocalDateTime now = LocalDateTime.now();
        CookieEntity cookie1 = CookieEntity.builder()
                .cookieValue("cookie1")
                .timestamp(now.minusDays(1)) // 하루 전 날짜 설정
                .target("target1")
                .build();
        cookieRepository.save(cookie1);

        CookieEntity cookie2 = CookieEntity.builder()
                .cookieValue("cookie2")
                .timestamp(now.plusDays(1)) // 하루 후 날짜 설정
                .target("target2")
                .build();
        cookieRepository.save(cookie2);

        // 테스트 실행
        int deletedCount = cookieRepository.deleteByTimestampBefore(now);

        // 검증
        assertThat(deletedCount).isEqualTo(1);
        List<CookieEntity> remainingCookies = cookieRepository.findAll();
        assertThat(remainingCookies).containsOnly(cookie2);
    }
}