package io.saim.dash.account.general.coupon.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QrCodeGeneratorUtil {

	public static String generateQRCode(Long couponId) throws Exception {
		String qrCodePath = "qrcodes/";
		String filePath = qrCodePath + "qrcode_" + couponId + ".png"; //파일 경로

		//폴더가 없으면 생성
		Path dirPath = FileSystems.getDefault().getPath(qrCodePath);
		if (!Files.exists(dirPath)) {
			try {
				Files.createDirectories(dirPath);
			} catch (IOException e) {
				throw new IOException("QR 코드 저장 경로를 생성할 수 없습니다: " + qrCodePath, e);
			}
		}

		int width = 300;
		int height = 300;
		String format = "png";

		//QR 코드 내용 (ex: 결제 검증 URL)
		String qrContent = "https://yourdomain.com/payment/verify?couponId=" + couponId;

		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		//QR 코드 생성
		BitMatrix bitMatrix = new MultiFormatWriter().encode(
			qrContent, BarcodeFormat.QR_CODE, width, height, hints);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);

		return "https://yourdomain.com/qrcodes/qrcode_" + couponId + ".png"; //QR 코드 이미지 URL 반환
	}
}
