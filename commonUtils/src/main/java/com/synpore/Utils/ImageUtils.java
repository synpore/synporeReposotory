package com.synpore.Utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

public class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 二维码图片的生成--去掉白边
     *
     * @param content       链接
     * @param qrcode_width  二维码宽
     * @param qrcode_height 二维码高
     * @return {BufferedImage}
     */
    public static BufferedImage createQrImage(String content, int qrcode_width, int qrcode_height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, qrcode_width, qrcode_height, hints);
        bitMatrix=deleteWhite(bitMatrix);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 去白边
     * @param matrix
     * @return
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }


    /**
     * 下载图片
     *
     * @param urlStr 一个url地址
     * @return {BufferedImage}
     */
    public static BufferedImage downloadImg(String urlStr) throws Exception {
        try {
            URL url = new URL(urlStr);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            return ImageIO.read(dataInputStream);
        } catch (Exception e) {
            LOGGER.error("请求路径：{}，下载图片异常", urlStr, e);
            throw new Exception(e);
        }
    }

    /**
     * 合成图片， 图片2会从图片1的(startX, startY)开始渲染
     *
     * @param img1   图片1
     * @param img2   图片2
     * @param startX
     * @param startY
     * @return {BufferedImage} 合成的图片
     */
    public static BufferedImage compositeImage(BufferedImage img1, BufferedImage img2, int startX, int startY) throws Exception {
        try {
            int alphaType = BufferedImage.TYPE_INT_RGB;
            BufferedImage result = new BufferedImage(img1.getWidth(null), img1.getHeight(null), alphaType);

            // 画图
            Graphics2D g = result.createGraphics();
            g.drawImage(img1, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
            g.drawImage(img2, startX, startY, null);

            g.dispose();

            //取消掉这行注释，你可以看见你合成图片的样子
//            new GraphicsDemo(result, img1.getWidth(), img1.getHeight());
            return result;
        } catch (Exception e) {
            LOGGER.error("合成图片异常", e);
            throw new Exception(e);
        }
    }

    /**
     * 将一个二维码合成进一个图片里面
     *
     * @param backImage  背景图片
     * @param url        二维码链接
     * @param startX     二维码在背景图片的X轴位置
     * @param startY     二维码在背景图片的Y轴位置
     * @param codeWidth  二维码宽度
     * @param codeHeight 二维码高度
     * @return 合成的图片
     * 一个例子：
     * BufferedImage img = ImageUtils.downloadImg("http://images.iqianjin.com/mimages/mobile/referral/share/appshare/share_01.jpg");
     * String url = "http://m.iqianjin.com/event/invite_reg_new.jsp?f=2&i=9467764&s=D464C061B3BF2AAC2E1195B36C6B511C&inviteCode=3M9M0I&sign=ECE1A8CBF1B960E329E1E5E0B7F0431BG15531BFC959BD31F3033CADF27D0332F&utmSource=1377&from=singlemessage&isappinstalled=0";
     * BufferedImage result = ImageUtils.compositeQRImage(img, url, 45, 620, 170,170);
     */
    public static BufferedImage compositeQRImage(BufferedImage backImage, String url, int startX, int startY, int codeWidth, int codeHeight) throws Exception {
        try {
            return compositeImage(backImage, createQrImage(url, codeWidth, codeHeight), startX, startY);
        } catch (Exception e) {
            LOGGER.error("二维码合成图片异常", e);
            throw new Exception(e);
        }
    }

    public static String encodeToString(BufferedImage image, String type) throws Exception {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            String imageString = encoder.encode(imageBytes);
            bos.close();
            return imageString;
        } catch (IOException e) {
            LOGGER.error("BufferedImage to String异常：{}", e);
            throw new Exception(e);
        }
    }

    public static byte[] decodeToByte(String imageString) throws Exception {//解码
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            return decoder.decodeBuffer(imageString);
        } catch (Exception e) {
            LOGGER.error("String to byte数组异常：{}", e);
            throw new Exception(e);
        }
    }

    public static BufferedImage decodeToImage(byte[] imageByte) throws Exception {//解码
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage image = ImageIO.read(bis);
            bis.close();
            return image;
        } catch (Exception e) {
            LOGGER.error("String to byte数组异常：{}", e);
            throw new Exception(e);
        }
    }
}

/**
 * 一个画板？
 */
class ImagePanel extends JPanel {

    private BufferedImage image;

    ImagePanel(BufferedImage bi) {
        image = bi;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

}

/**
 * 可以在本地将一个BufferedImage格式的东西显示出来
 */
class GraphicsDemo extends JFrame {
    GraphicsDemo(BufferedImage bi, int width, int height) {
        this.add(new ImagePanel(bi));
        setTitle("基本绘图方法演示");
        setSize(width, height);
        setVisible(true);
    }

}
