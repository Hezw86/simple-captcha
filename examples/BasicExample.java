package io.github.hezw86.example;

import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaConfig;
import io.github.hezw86.CaptchaResult;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

public class BasicExample {
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== 基础使用示例 ===\n");
        
        CaptchaGenerator generator = CaptchaGenerator.create();
        
        CaptchaResult result = generator.generate();
        
        System.out.println("验证�? " + result.getCode());
        System.out.println("图片尺寸: " + result.getImage().getWidth() + "x" + result.getImage().getHeight());
        
        File output = new File("captcha-basic.png");
        ImageIO.write(result.getImage(), "png", output);
        System.out.println("图片已保存到: " + output.getAbsolutePath());
        
        String base64 = generator.toBase64(result.getImage());
        System.out.println("Base64长度: " + base64.length());
        
        String base64WithPrefix = generator.toBase64WithPrefix(result.getImage());
        System.out.println("带前缀的Base64: " + base64WithPrefix.substring(0, 50) + "...");
    }
}