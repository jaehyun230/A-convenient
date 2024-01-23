package myProject.service.scraper.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import static java.lang.Thread.*;

@Service
public class ScraperService {

    public String fetchImageData() {
        // WebDriver 설정 (실제 경로로 변경)
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\path\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            // 웹 페이지로 이동
            String inputName = "푸바오";
            driver.get("https://search.naver.com/search.naver?where=image&sm=tab_jum&query=" + inputName);
            try{
                sleep(1000);
            } catch (Exception e){

            }

            WebElement media_element = driver.findElement(By.cssSelector("#main_pack > section.sc_new.sp_nimage._fe_image_viewer_prepend_target > div.api_subject_bx._fe_image_tab_list_root.ani_fadein > div > div > div.image_tile._fe_image_tab_grid > div:nth-child(1) > div > div > div > img "));

            String imageUrl = media_element.getAttribute("src");
            System.out.println("Image URL: " + imageUrl);

            // 웹 페이지에서 이미지 데이터 검색 (선택자 변경 가능)
//            WebElement imageElement = driver.findElement(By.cssSelector("img.someClass"));
//            return imageElement.getAttribute("src");
            return imageUrl;

        } finally {
            driver.quit(); // WebDriver 종료
        }
    }
}
