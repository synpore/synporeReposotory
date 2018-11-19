package com.synpore.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 
 * @author      hurenxian  
 * @desription  图片处理工具
 * @date 2018/4/24 下午4:58
 */  
public class ImageHandleHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHandleHelper.class);

    /**
     * 截取图片
     *
     * @param srcFile
     * @param targetFile
     * @param startAcross
     * @param StartEndlong 开始截图位置纵坐标
     * @param width        截取的宽度
     * @param hight        截取的高度
     * @throws Exception
     */
    public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width,
                                int hight) throws Exception {
        // 取得图片读入器
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = readers.next();
        // 取得图片读入流
        InputStream source = new FileInputStream(srcFile);
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        // 图片参数对象
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
    }


    /**
     * 拼接图片，要求图片长宽一致
     *
     * @param files
     * @param type       type1  横向拼接， 2 纵向拼接
     * @param targetFile
     */
    public static void mergeImage(String[] files, int type, String targetFile) {
        int len = files.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(files[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }

        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                            images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight();
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 图片合成
     *
     * @param bigPath
     * @param smallPath
     * @param outFile
     */
    public static final void overlapImage(String bigPath, String smallPath, String outFile) {
        try {
            BufferedImage big = ImageIO.read(new File(bigPath));
            BufferedImage small = ImageIO.read(new File(smallPath));
            Graphics2D g = big.createGraphics();
            int x = (big.getWidth() - small.getWidth()) / 2;
            int y = (big.getHeight() - small.getHeight()) / 2;
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
