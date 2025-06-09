package io.saim.dash.coupon.common.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

public class ImageUtil {

    private static final Region s3BucketRegion = Region.AP_NORTHEAST_2;

    @Getter
	private static class BucketAccessInfo {
        private S3Client s3Client;
        private String bucketName;
        private String savePath;

        public BucketAccessInfo(S3Client s3Client, String bucketName, String savePath) {
            this.s3Client = s3Client;
            this.bucketName = bucketName;
            this.savePath = savePath;
        }
	}

    @Getter @AllArgsConstructor
    public enum AccessType {
        FORM("dash-form-bucket"), // 양식등록 관련
        PAYMENT("dash-payment-bucket"), // 결제시 QR촬영 관련
        SIGN("dash-sign-bucket") // 결제시 QR촬영 관련
        ;

        private String bucketName;
    }

    private static BucketAccessInfo getImageAccessSettings(AccessType accessType) {
        // S3Client 인스턴스 생성 (보통 Bean으로 주입받음)
        S3Client s3Client = S3Client.builder()
            .region(s3BucketRegion)
            .build();

        String bucketName = accessType.getBucketName();
        String savePath = "uploads";

        return new BucketAccessInfo(s3Client, bucketName, savePath);
    }

    public static String saveImage(AccessType accessType, MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + fileExtension;
        return saveImage(accessType, file, filename);
    }

    public static String saveImage(AccessType accessType, MultipartFile file, String filename) throws IOException {
        BucketAccessInfo info = getImageAccessSettings(accessType);
        String key = info.getSavePath() + "/" + filename;

        info.getS3Client().putObject(
            PutObjectRequest.builder()
                .bucket(info.getBucketName())
                .key(key)
                .contentType(file.getContentType())
                .build(),
            RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return key;
    }

    public static BufferedImage readImage(AccessType accessType, String key) throws IOException {
        BucketAccessInfo info = getImageAccessSettings(accessType);

        var response = info.getS3Client().getObjectAsBytes(
            builder -> builder
                .bucket(info.getBucketName())
                .key(key)
        );

        try (var is = response.asInputStream()) {
            return ImageIO.read(is);
        }
    }

    private static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new IllegalArgumentException("Invalid file name: " + filename);
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
