package io.saim.dash.account.general.coupon.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class QrCodeGeneratorUtil {

	@Value("${app.domain}")
	private String domain;

	public String generateQRCode(Long couponId) throws Exception {
		String qrCodePath = "qrcodes/";
		String filePath = qrCodePath + "qrcode_" + couponId + ".png";

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

		String qrContent = domain + "/payment/verify?couponId=" + couponId;

		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix bitMatrix = new MultiFormatWriter().encode(
			qrContent, BarcodeFormat.QR_CODE, width, height, hints);

		Path path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);

		return domain + "/qrcodes/qrcode_" + couponId + ".png";
	}
}
