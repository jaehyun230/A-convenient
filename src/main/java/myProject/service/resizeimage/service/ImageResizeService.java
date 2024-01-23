package myProject.service.resizeimage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ImageResizeService {

    private void saveToFileSystem(byte[] data, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
        }
    }

    public byte[] resizeImage(MultipartFile[] files, int width, int height) throws IOException {
        byte[] resizedImage;

        if (files.length == 1) {
            // 단일 파일 처리
            resizedImage = processSingleImage(files[0], width, height);
            saveToFileSystem(resizedImage, "resized_" + files[0].getOriginalFilename());
        } else {
            // 여러 파일을 ZIP 파일로 처리
            resizedImage = processMultipleImagesAsZip(files, width, height);
            saveToFileSystem(resizedImage, "resized_images.zip");
        }

        return resizedImage;
    }

    private byte[] processSingleImage(MultipartFile file, int width, int height) throws IOException {
        BufferedImage inputImage = ImageIO.read(file.getInputStream());
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, width, height, null);
        g2d.dispose();

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

    private byte[] processMultipleImagesAsZip(MultipartFile[] files, int width, int height) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (MultipartFile file : files) {
                BufferedImage inputImage = ImageIO.read(file.getInputStream());
                BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());
                Graphics2D g2d = outputImage.createGraphics();
                g2d.drawImage(inputImage, 0, 0, width, height, null);
                g2d.dispose();

                ByteArrayOutputStream imageBaos = new ByteArrayOutputStream();
                ImageIO.write(outputImage, "jpg", imageBaos);
                ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
                zos.putNextEntry(zipEntry);
                zos.write(imageBaos.toByteArray());
                zos.closeEntry();
            }
        }
        return baos.toByteArray();
    }
}