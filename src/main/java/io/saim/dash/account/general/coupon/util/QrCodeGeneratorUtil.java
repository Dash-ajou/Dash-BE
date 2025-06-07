package io.saim.dash.account.general.coupon.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Component
public class QrCodeGeneratorUtil {

	@Value("${app.domain}")
	private String domain;

	public String generateQRCodeBase64(String paymentCode) throws Exception {
		int width = 300;
		int height = 300;
		String qrContent = paymentCode;

		Map<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		BitMatrix bitMatrix = new MultiFormatWriter().encode(
			qrContent, BarcodeFormat.QR_CODE, width, height, hints
		);

		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);

		byte[] pngData = outputStream.toByteArray();
		return "data:image/png;base64," + Base64.getEncoder().encodeToString(pngData);
	}
}
