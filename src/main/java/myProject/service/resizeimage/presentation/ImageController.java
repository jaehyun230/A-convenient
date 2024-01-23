package myProject.service.resizeimage.presentation;

import myProject.service.resizeimage.service.ImageResizeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageResizeService imageResizeService;

    public ImageController(ImageResizeService imageResizeService) {
        this.imageResizeService = imageResizeService;
    }

    @PostMapping("/resize")
    public ResponseEntity<?> resizeImages(@RequestParam("files") MultipartFile[] files,
                                          @RequestParam("width") int width,
                                          @RequestParam("height") int height) {
        try {
            imageResizeService.resizeImage(files, width, height);
            return ResponseEntity.ok("이미지 리사이즈 완료");
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error resizing images: " + e.getMessage());
        }
    }
}