package myProject.service.cookie.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import myProject.service.cookie.dto.QueryDTO;
import myProject.service.cookie.entity.CookieEntity;
import myProject.service.cookie.repository.CookieRepository;
import myProject.service.cookie.util.CookieUtill;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static myProject.service.cookie.util.CookieUtill.ExpiredTimeSeconds;

@Service
@RequiredArgsConstructor
public class CookieService {

    public static final String DIRECTORY_PATH = "./user_data";
    private final CookieRepository cookieRepository;
    private final CookieUtill cookieUtill;

    /**
     * {setCookieValue(String cookieValue, QueryDTO queryDTO):
     * 새로운 CookieEntity를 생성하고, 주어진 쿠키 값과 QueryDTO 정보로 초기화한 후 저장합니다.
     */
    public void setCookieValue(String cookieValue, QueryDTO queryDTO) {
        CookieEntity cookieEntity = new CookieEntity();
        cookieEntity.setCookieValue(cookieValue);
        cookieEntity.setTimestamp(LocalDateTime.now());
        cookieEntity.setTarget(queryDTO.getTarget());


        cookieRepository.save(cookieEntity);
    }

    /**
     * findCookieById(String cookieValue):
     * 주어진 쿠키 값으로 CookieEntity를 찾아 반환합니다.
     */
    public Optional<CookieEntity> findCookieById(String cookieValue) {
        return cookieRepository.findById(cookieValue);
    }

    /**
     * cleanOldCookies():
     * 설정된 만료 시간을 기준으로 오래된 쿠키를 찾아, 관련 파일을 삭제하고 데이터베이스에서 쿠키 엔티티를 제거합니다.
     * 이 메서드는 스케줄러에 의해 주기적으로 실행됩니다.
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void cleanOldCookies() {
        LocalDateTime BaseCreatedTime = LocalDateTime.now().minusSeconds(ExpiredTimeSeconds);

        List<CookieEntity> oldCookies = cookieRepository.findByTimestampBefore(BaseCreatedTime);

        for (CookieEntity cookie : oldCookies) {
            deleteSpecificFile(cookie.getCookieValue());
        }
        cookieRepository.deleteByTimestampBefore(BaseCreatedTime);
    }

    /**
     * deleteSpecificFile(String filename):
     * 주어진 파일 이름에 해당하는 파일을 시스템에서 삭제합니다.
     */
    public void deleteSpecificFile(String filename) {
        Path directoryPath = Paths.get(DIRECTORY_PATH);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath, path -> path.getFileName().toString().startsWith(filename))) {
            for (Path entry : stream) {
                Files.delete(entry);
            }
        } catch (IOException e) {
            System.out.println("File delete fail: " + e.getMessage());
        }
    }

    /**
     * cookieSet(QueryDTO queryDto, HttpServletResponse response, HttpServletRequest request):
     * HTTP 요청으로부터 쿠키 값을 검사하고, 쿠키가 없으면 새로 생성하여 설정한 후, 해당 쿠키 값을 반환합니다.
     */
    public String cookieSet(QueryDTO queryDto, HttpServletResponse response, HttpServletRequest request) {
        String cookieValue = cookieUtill.hasCookie(request, "SuperOfficeCookie");
        if (cookieValue == ""){
            cookieValue = cookieUtill.createCookie(response);
        }
        setCookieValue(cookieValue, queryDto);
        return cookieValue;
    }

}