package io.github.hezw86;

import java.awt.image.BufferedImage;

/**
 * 验证码生成结果
 * <p>
 * 包含验证码文本和对应的图片。
 * </p>
 *
 * @author hezw86
 * @since 1.0.0
 */
public class CaptchaResult {
    
    private final String code;
    private final BufferedImage image;
    
    /**
     * 构造函数
     *
     * @param code 验证码文本
     * @param image 验证码图片
     */
    public CaptchaResult(String code, BufferedImage image) {
        this.code = code;
        this.image = image;
    }
    
    /**
     * 获取验证码文本
     *
     * @return 验证码文本
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 获取验证码图片
     *
     * @return 验证码图片
     */
    public BufferedImage getImage() {
        return image;
    }
}