package io.github.hezw86;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 验证码生成器
 * <p>
 * 简单、轻量级的纯数字验证码生成器，纯色背景，无干扰元素。
 * 支持自动对比色计算，解决深色背景配黑字看不清的问题。
 * </p>
 * 
 * @author hezw86
 * @since 1.0.0
 */
public class CaptchaGenerator {
    
    private static final Random random = new Random();
    private final CaptchaConfig config;
    
    static {
        System.setProperty("java.awt.headless", "true");
    }
    
    public CaptchaGenerator() {
        this.config = CaptchaConfig.defaultConfig();
    }
    
    public CaptchaGenerator(CaptchaConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("Config cannot be null");
        }
        this.config = config;
    }
    
    public static CaptchaGenerator create() {
        return new CaptchaGenerator();
    }
    
    public static CaptchaGenerator create(CaptchaConfig config) {
        return new CaptchaGenerator(config);
    }
    
    public CaptchaResult generate() {
        String code = generateCode();
        BufferedImage image = generateImage(code);
        return new CaptchaResult(code, image);
    }
    
    public String generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < config.getCodeCount(); i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    
    public BufferedImage generateImage(String code) {
        if (code == null || code.isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }
        
        BufferedImage image = new BufferedImage(
            config.getWidth(), 
            config.getHeight(), 
            BufferedImage.TYPE_INT_RGB
        );
        
        Graphics2D g2d = image.createGraphics();
        
        try {
            g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON
            );
            
            g2d.setColor(config.getBackgroundColor());
            g2d.fillRect(0, 0, config.getWidth(), config.getHeight());
            
            g2d.setColor(config.getTextColor());
            g2d.setFont(new Font(
                config.getFontFamily(), 
                Font.BOLD, 
                config.getFontSize()
            ));
            
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int totalWidth = fontMetrics.stringWidth(code);
            int startX = (config.getWidth() - totalWidth) / 2;
            int startY = (config.getHeight() - fontMetrics.getHeight()) / 2 
                        + fontMetrics.getAscent();
            
            g2d.drawString(code, startX, startY);
        } finally {
            g2d.dispose();
        }
        
        return image;
    }
    
    public byte[] toBytes(BufferedImage image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
    
    public String toBase64(BufferedImage image) throws IOException {
        byte[] bytes = toBytes(image);
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    public String toBase64WithPrefix(BufferedImage image) throws IOException {
        return "data:image/png;base64," + toBase64(image);
    }
    
    public CaptchaConfig getConfig() {
        return config;
    }
}