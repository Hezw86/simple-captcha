package io.github.hezw86;

import java.awt.*;

/**
 * 验证码配置类
 * <p>
 * 使用链式调用方式配置验证码的各项参数。
 * 支持自动对比色计算，解决深色背景配黑字看不清的问题。
 * </p>
 *
 * @author hezw86
 * @since 1.0.0
 */
public class CaptchaConfig {
    
    private int width = 120;
    private int height = 40;
    private int codeCount = 4;
    private Color backgroundColor = new Color(240, 240, 240);
    private Color textColor = null;
    private int fontSize = 28;
    private String fontFamily = "Arial";
    private boolean autoContrast = true;
    
    public CaptchaConfig() {
    }
    
    public static CaptchaConfig defaultConfig() {
        return new CaptchaConfig();
    }
    
    public CaptchaConfig width(int width) {
        this.width = width;
        return this;
    }
    
    public CaptchaConfig height(int height) {
        this.height = height;
        return this;
    }
    
    public CaptchaConfig codeCount(int codeCount) {
        this.codeCount = codeCount;
        return this;
    }
    
    public CaptchaConfig backgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public CaptchaConfig backgroundColor(int r, int g, int b) {
        this.backgroundColor = new Color(r, g, b);
        return this;
    }
    
    public CaptchaConfig textColor(Color textColor) {
        this.textColor = textColor;
        this.autoContrast = false;
        return this;
    }
    
    public CaptchaConfig textColor(int r, int g, int b) {
        this.textColor = new Color(r, g, b);
        this.autoContrast = false;
        return this;
    }
    
    public CaptchaConfig fontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }
    
    public CaptchaConfig fontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }
    
    public CaptchaConfig autoContrast(boolean autoContrast) {
        this.autoContrast = autoContrast;
        if (autoContrast) {
            this.textColor = null;
        }
        return this;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getCodeCount() {
        return codeCount;
    }
    
    public Color getBackgroundColor() {
        return backgroundColor;
    }
    
    public Color getTextColor() {
        if (autoContrast && textColor == null) {
            return ColorUtils.calculateContrastColor(backgroundColor);
        }
        return textColor != null ? textColor : new Color(50, 50, 50);
    }
    
    public int getFontSize() {
        return fontSize;
    }
    
    public String getFontFamily() {
        return fontFamily;
    }
    
    public boolean isAutoContrast() {
        return autoContrast;
    }
}