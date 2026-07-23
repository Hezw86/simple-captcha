package io.github.hezw86.example;

import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaConfig;
import io.github.hezw86.CaptchaResult;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

public class CustomConfigExample {
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== 自定义配置示�?===\n");
        
        CaptchaConfig config = CaptchaConfig.defaultConfig()
            .width(200)
            .height(80)
            .codeCount(6)
            .backgroundColor(255, 255, 255)
            .textColor(0, 0, 0)
            .fontSize(40)
            .fontFamily("Courier New");
        
        CaptchaGenerator generator = CaptchaGenerator.create(config);
        
        for (int i = 1; i <= 3; i++) {
            CaptchaResult result = generator.generate();
            
            System.out.println("验证�?" + i + ": " + result.getCode());
            
            File output = new File("captcha-custom-" + i + ".png");
            ImageIO.write(result.getImage(), "png", output);
            System.out.println("已保存到: " + output.getAbsolutePath());
        }
    }
}