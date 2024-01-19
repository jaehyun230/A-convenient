package myProject.service.resizeimage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class ImageResizeService {

    public byte[] resizeImage(MultipartFile file, int width, int height) throws IOException {
        // 입력 이미지 읽기
        BufferedImage inputImage = ImageIO.read(file.getInputStream());

        // 출력 이미지와 Graphics2D 객체 생성
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();

        // 이미지 리사이징
        g2d.drawImage(inputImage, 0, 0, width, height, null);
        g2d.dispose();

        // 리사이즈된 이미지 파일 저장
        String outputFilename = "resized_" + file.getOriginalFilename();
        File outputFile = new File(outputFilename);
        ImageIO.write(outputImage, "jpg", outputFile);

        // 로그 남기기 (예: 파일 경로)
        System.out.println("Resized image saved to: " + outputFile.getAbsolutePath());

        // ByteArrayOutputStream을 사용하여 출력 이미지를 바이트 배열로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }
}