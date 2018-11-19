package com.synpore.Utils;

import java.awt.*;

/**
 *
 * @author      hurenxian
 * @desription  水印参数
 * @date 2018/4/3 下午6:02
 */
public class WaterMarkParam {
    //文字
    private String logText;
    //角度
    private int degree;
    //文字颜色
    private Color color;
    //文字透明度
    private float alpha;
    //字体
    private Font font;
    //文字x坐标
    private int x;
    //文字y坐标
    private int y;

    public WaterMarkParam(String logText, int degree, Color color, float alpha, Font font, int x, int y) {
        this.logText = logText;
        this.degree = degree;
        this.color = color;
        this.alpha = alpha;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public WaterMarkParam(String logText, Color color, Font font) {
        this(logText,0,color,1,font,0,0);
    }

    public WaterMarkParam(String logText, Color color, Font font, int x, int y) {
        this(logText,0,color,1,font,x,y);
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
