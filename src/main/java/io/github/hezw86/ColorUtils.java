package io.github.hezw86;

import java.awt.*;

/**
 * 颜色工具类，提供智能对比色计算功能
 * 
 * @author hezw86
 * @since 1.0.0
 */
public class ColorUtils {
    
    private static final double DARK_THRESHOLD = 0.5;
    
    private ColorUtils() {
    }
    
    /**
     * 根据背景色自动计算对比色
     * <p>
     * 使用相对亮度算法（基于WCAG 2.0标准）计算背景色的亮度，
     * 然后返回黑色或白色作为对比色。
     * </p>
     *
     * @param backgroundColor 背景颜色
     * @return 对比色（黑色或白色）
     * @throws IllegalArgumentException 如果backgroundColor为null
     */
    public static Color calculateContrastColor(Color backgroundColor) {
        if (backgroundColor == null) {
            throw new IllegalArgumentException("Background color cannot be null");
        }
        
        double luminance = calculateLuminance(backgroundColor);
        
        if (luminance < DARK_THRESHOLD) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    
    /**
     * 根据背景色自动计算对比色（带自定义阈值）
     *
     * @param backgroundColor 背景颜色
     * @param threshold 亮度阈值（0.0-1.0），低于此值返回白色，高于此值返回黑色
     * @return 对比色（黑色或白色）
     * @throws IllegalArgumentException 如果backgroundColor为null或threshold不在有效范围内
     */
    public static Color calculateContrastColor(Color backgroundColor, double threshold) {
        if (backgroundColor == null) {
            throw new IllegalArgumentException("Background color cannot be null");
        }
        if (threshold < 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("Threshold must be between 0.0 and 1.0");
        }
        
        double luminance = calculateLuminance(backgroundColor);
        
        if (luminance < threshold) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    
    /**
     * 计算颜色的相对亮度（基于WCAG 2.0标准）
     * <p>
     * 公式: L = 0.2126 * R + 0.7152 * G + 0.0722 * B
     * 其中R、G、B是线性化的RGB值
     * </p>
     *
     * @param color 颜色
     * @return 相对亮度值（0.0-1.0）
     */
    public static double calculateLuminance(Color color) {
        double r = linearize(color.getRed() / 255.0);
        double g = linearize(color.getGreen() / 255.0);
        double b = linearize(color.getBlue() / 255.0);
        
        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
    
    /**
     * 线性化RGB值（sRGB转线性RGB）
     *
     * @param value sRGB值（0.0-1.0）
     * @return 线性RGB值（0.0-1.0）
     */
    private static double linearize(double value) {
        if (value <= 0.03928) {
            return value / 12.92;
        } else {
            return Math.pow((value + 0.055) / 1.055, 2.4);
        }
    }
    
    /**
     * 计算两个颜色之间的对比度
     * <p>
     * 基于WCAG 2.0标准计算对比度
     * </p>
     *
     * @param color1 第一个颜色
     * @param color2 第二个颜色
     * @return 对比度（1.0-21.0）
     */
    public static double calculateContrastRatio(Color color1, Color color2) {
        double luminance1 = calculateLuminance(color1);
        double luminance2 = calculateLuminance(color2);
        
        double lighter = Math.max(luminance1, luminance2);
        double darker = Math.min(luminance1, luminance2);
        
        return (lighter + 0.05) / (darker + 0.05);
    }
    
    /**
     * 判断颜色是否为深色
     *
     * @param color 颜色
     * @return 如果是深色返回true，否则返回false
     */
    public static boolean isDark(Color color) {
        return calculateLuminance(color) < DARK_THRESHOLD;
    }
    
    /**
     * 判断颜色是否为浅色
     *
     * @param color 颜色
     * @return 如果是浅色返回true，否则返回false
     */
    public static boolean isLight(Color color) {
        return !isDark(color);
    }
    
    /**
     * 反转颜色（获取互补色）
     *
     * @param color 原始颜色
     * @return 反转后的颜色
     */
    public static Color invert(Color color) {
        return new Color(
            255 - color.getRed(),
            255 - color.getGreen(),
            255 - color.getBlue()
        );
    }
    
    /**
     * 根据背景色自动计算最佳对比色（从多个候选色中选择）
     *
     * @param backgroundColor 背景颜色
     * @param candidates 候选颜色数组
     * @return 对比度最高的候选颜色
     * @throws IllegalArgumentException 如果backgroundColor或candidates为null，或candidates为空
     */
    public static Color selectBestContrast(Color backgroundColor, Color... candidates) {
        if (backgroundColor == null) {
            throw new IllegalArgumentException("Background color cannot be null");
        }
        if (candidates == null || candidates.length == 0) {
            throw new IllegalArgumentException("Candidates cannot be null or empty");
        }
        
        Color bestColor = candidates[0];
        double bestContrast = calculateContrastRatio(backgroundColor, bestColor);
        
        for (int i = 1; i < candidates.length; i++) {
            double contrast = calculateContrastRatio(backgroundColor, candidates[i]);
            if (contrast > bestContrast) {
                bestContrast = contrast;
                bestColor = candidates[i];
            }
        }
        
        return bestColor;
    }
}