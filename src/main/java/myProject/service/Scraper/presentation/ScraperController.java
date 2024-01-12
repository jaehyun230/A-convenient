package myProject.service.Scraper.presentation;

import myProject.service.Scraper.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/imgdata")
public class ScraperController {

    @Autowired
    private ScraperService scraperService;

    @GetMapping
    public String fetchImageData() {
        return scraperService.fetchImageData();
    }
}
