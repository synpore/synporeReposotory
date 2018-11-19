package com.synpore.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;


/**
 * 图片写水印
 * @author hurenxian  
 * @date 2018/4/3 下午6:08
 */    
public class WaterMarkUtils {

    private static final Logger log = Logger.getLogger(WaterMarkUtils.class);

    /**
     * 默认最大文字个数
     */
    public static  final int DEFUALT_MAX_NAME_LENGTH=12;
    /**
     * 默认超长文字后缀
     */
    public static final String DEFAULT_NAME_SUFFIX="***";

    /**
     * 给定路径图片添加水印文字,并输出到指定路径
     *
     * @param waterMarkParam 要写入的文字
     * @param srcImgPath     源图片路径
     * @param newImagePath   新图片路径
     * @param formatName     图片后缀
     */
    public static void markImageByText(WaterMarkParam waterMarkParam, String srcImgPath, String newImagePath, String formatName) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = markImageByText(waterMarkParam,srcImg);
            os = new FileOutputStream(newImagePath);
            ImageIO.write(buffImg, formatName, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
    }

    /**
     * 从指定url读取图片生成水印
     * @param waterMarkParam
     * @param imageUrl
     * @return
     */
    public static BufferedImage markImageByText(WaterMarkParam waterMarkParam, URL imageUrl){
        Image image= null;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
          log.error("url读取图片异常,logText{"+waterMarkParam.getLogText()+"}",e);
        }
        return  markImageByText(waterMarkParam,image);
    }

    /**
     * 将BufferedImage直接转换成输入流
     * @param bufferedImage
     * @return
     */
    public static InputStream converBufferedImageToInputStream(BufferedImage bufferedImage) throws IOException {
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
    }
    /**
     * 给图片打水印，支持字体、颜色、文字角度、字体透明度定制
     * @param waterMarkParam    水印定制参数
     * @param srcImg            源图片
     * @return 合成后的图片
     */
    public static BufferedImage markImageByText(WaterMarkParam waterMarkParam, Image srcImg) {
        try {
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
            // 设置水印旋转
            if (0!= waterMarkParam.getDegree()) {
                g.rotate(Math.toRadians(waterMarkParam.getDegree()), buffImg.getWidth() / 2, buffImg.getHeight() / 2);
            }
            // 设置水印文字颜色
            g.setColor(waterMarkParam.getColor());
            //自适应图片的文字大小
            Double toleranceSize= Math.floor(buffImg.getWidth()*1.7/ waterMarkParam.getLogText().length());
            //设置的字体大小超出图片宽度自适应
            if(waterMarkParam.getFont().getSize()>toleranceSize.intValue()){
                Font oldFont=waterMarkParam.getFont();
                Font newFont=new Font(oldFont.getFontName(),oldFont.getStyle(),toleranceSize.intValue());
                waterMarkParam.setFont(newFont);
            }
            // 设置水印文字Font
            g.setFont(waterMarkParam.getFont());
            // 设置水印文字透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, waterMarkParam.getAlpha()));
            //设置水印的坐标
            int x=waterMarkParam.getX();
            int y=waterMarkParam.getY();
            if (waterMarkParam.getX() == 0)
                x = (buffImg.getWidth() - getWatermarkLength(waterMarkParam.getLogText(), g)) / 2;
            if (waterMarkParam.getY() == 0)
                y = buffImg.getHeight() / 2;
            g.drawString(waterMarkParam.getLogText(), x, y);
            //释放资源
            g.dispose();
            return buffImg;
        } catch (Exception e) {
            log.error("合成水印图片异常，水印文字{" + waterMarkParam.getLogText() + "}", e);
            return null;
        }
    }

    /**
     * 获取水印文字所占长度
     *
     * @param waterMarkContent
     * @param g
     * @return
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }

    /**
     * 昵称处理
     * @param nickName
     * @return
     */
    public static String handleNickName(String nickName){
        if(nickName.length()>DEFUALT_MAX_NAME_LENGTH) {
            String name = StringUtils.substring(nickName, 0, DEFUALT_MAX_NAME_LENGTH);
            return name.concat(DEFAULT_NAME_SUFFIX);
        }
        return nickName;
    }

    /**
     * 获取图片名称
     * @param url
     * @return
     */
    public  static String getFileName(String url){
        String result="";
        if(StringUtils.isBlank(url))
            return result;
        int index= url.lastIndexOf("/");
        if(index>0){
            result=url.substring(index+1);
        }
        if(!result.endsWith(".jpg")&&result.endsWith(".JPG")){
            result=result.concat(".jpg");
        }
        return result;
    }


    public static void main(String[] args) {
        WaterMarkParam waterMarkParam=        new WaterMarkParam("中文昵称加长中文昵称加长中文昵称加长",new Color(255,255,255),new Font("宋体", Font.BOLD,35),0,130);
        waterMarkParam.setLogText(WaterMarkUtils.handleNickName(waterMarkParam.getLogText()));
        System.out.println(waterMarkParam.getLogText());
        markImageByText(waterMarkParam, "/Users/finup/Downloads/baiyangzuo.jpg", "/Users/finup/Downloads/baiyangzuo-g.jpg","jpg");

    }

}

