package myProject.service.resizeimage.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import myProject.service.cookie.entity.CookieEntity;
import myProject.service.cookie.service.CookieService;
import myProject.service.cookie.util.CookieUtill;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("download")
@RequiredArgsConstructor
public class ImageDownloadController {

    private final CookieService cookieService;
    private final CookieUtill CookieUtil;


    /**
     * downloadPPTFile(HttpServletRequest request):
     * HTTP 요청으로부터 쿠키를 추출하여 해당 쿠키 값으로 PPT 파일을 검색합니다.
     * 쿠키 값이 없거나 파일이 존재하지 않는 경우 오류를 반환합니다.
     * 파일이 존재하면, 사용자에게 다운로드할 수 있도록 파일 리소스와 함께 HTTP 응답을 구성합니다.
     * 응답에는 사용자의 Target을 값을 반영합니다.
     */
    @GetMapping("/image")
    public ResponseEntity<Resource> downloadPPTFile(HttpServletRequest request) throws Exception {

        String Cookie_value = CookieUtil.hasCookie(request, "A-Convenient-image");
        if (Cookie_value == "") {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
//        String fullPath = BASE_PATH + USER_SAVE_DIRECTORY + File.separator + FindFileName;
        System.out.println("filePath = ");
        Path filePath = Paths.get("./resized_thislife.jpg");

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new Exception("File not found!");
        }

        Optional<CookieEntity> userCookie = cookieService.findCookieById(Cookie_value);
        CookieEntity cookieEntity = userCookie.get();

        String desiredFileName = "resized.jpg";
        desiredFileName = URLEncoder.encode(desiredFileName, "UTF-8").replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + desiredFileName)
                .body(resource);
    }
}
