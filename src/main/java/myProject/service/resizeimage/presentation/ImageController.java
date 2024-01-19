package myProject.service.resizeimage.presentation;

import myProject.service.resizeimage.service.ImageResizeService;
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

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageResizeService imageResizeService;

    public ImageController(ImageResizeService imageResizeService) {
        this.imageResizeService = imageResizeService;
    }

    @PostMapping("/resize")
    public ResponseEntity<?> resizeImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam("width") int width,
                                         @RequestParam("height") int height) {

        try {
            byte[] resizedImageBytes = imageResizeService.resizeImage(file, width, height);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(new ByteArrayInputStream(resizedImageBytes)));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error resizing image: " + e.getMessage());
        }
    }
}