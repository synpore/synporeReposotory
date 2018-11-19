package com.synpore.Utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * 
 * @author      hurenxian  
 * @desription  二维码工具
 * @date 2018/4/24 下午4:13
 */  
public class QrCodeUtils {
    /**
     * 生成二维码
     * @param content 内容
     * @param prePath
     * @param qrCodeName
     * @return
     */
    public static String encodeQrCode(String content, String prePath, String qrCodeName) {
        try {
            int width = 300; // 二维码图片宽度
            int height = 300; // 二维码图片高度
            String format = "png";// 二维码的图片格式

            Hashtable<EncodeHintType, String> hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
            hints.put(EncodeHintType.MARGIN, "2");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 生成二维码
            Path path = Paths.get(prePath, qrCodeName);
            MatrixToImageWriter.writeToPath(bitMatrix, format, path);
            System.out.println("success:-------------------生成二维码成功");
        } catch (Exception e) {
            System.out.println("error:-------------------生成二维码失败" + e.getMessage());
            return null;
        }
        return qrCodeName;
    }

    /**
     * 生成二维码并输出到流
     *
     * @param content
     * @param outputStream
     * @param format
     */
    public static void encodeQrCode(String content, OutputStream outputStream, String format) {
        try {
            int width = 300; // 二维码图片宽度
            int height = 300; // 二维码图片高度
            Hashtable<EncodeHintType, String> hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
            hints.put(EncodeHintType.MARGIN, "2");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);
            System.out.println("success:-------------------生成二维码成功");
        } catch (Exception e) {
            System.out.println("error:-------------------生成二维码失败" + e.getMessage());
        }
    }

    /**
     * 二维码解析
     * @param qrCodeUrl
     * @return
     */
    public static String decodeQrCode(String qrCodeUrl) {
        String retStr = "";
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(qrCodeUrl));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<>();
            hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(bitmap, hintTypeObjectHashMap);
            retStr = result.getText();
            System.out.println("success:-------------------二维码解析成功");
        } catch (Exception e) {
            System.out.println("error:-------------------二维码解析失败" + e.getMessage());
        }
        return retStr;
    }

    /**
     * 生成带log的二维码
     * @param content
     * @param prePath
     * @param qrCodeName
     * @param logoPath
     * @return
     */
    public static String encodeLogoQrCode(String content, String prePath, String qrCodeName, String logoPath) {
        try {
            // 基本二维码生成
            int width = 300; // 二维码图片宽度
            int height = 300; // 二维码图片高度
            String format = "png";// 二维码的图片格式

            Hashtable<EncodeHintType, String> hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
            hints.put(EncodeHintType.MARGIN, "2");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage bufferQrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);// 普通二维码的图片流
            BufferedImage bufferLogoImage = ImageIO.read(new File(logoPath));// logo文件
            Graphics2D g2 = bufferQrCodeImage.createGraphics();
            int matrixWidth = bufferQrCodeImage.getWidth();
            int matrixHeigh = bufferQrCodeImage.getHeight();
            g2.drawImage(bufferLogoImage, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5,null);// logo绘制
            BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke);// 设置笔画对象
            // 指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2,matrixWidth / 5, matrixHeigh / 5, 20, 20);
            g2.setColor(Color.white);
            g2.draw(round);// 绘制圆弧矩形

            // 设置logo 有一道灰色边框
            BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke2);// 设置笔画对象
            RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2,
                    matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
            g2.setColor(new Color(128, 128, 128));
            g2.draw(round2);// 绘制圆弧矩形

            g2.dispose();
            bufferQrCodeImage.flush();
            ImageIO.write(bufferQrCodeImage, format, new File(prePath+qrCodeName));
            System.out.println("success:-------------------生成带logo二维码成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error:-------------------生成带logo二维码失败" + e.getMessage());
        }

        return "";
    }

    public static void main(String[] args) {
        encodeLogoQrCode("www.baidu.com","/Users/finup/Downloads/","aaa.jpg","/Users/finup/Downloads/znq.png");
    }
    
}
