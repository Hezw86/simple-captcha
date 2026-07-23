package io.github.hezw86;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CaptchaGeneratorTest {
    
    @Test
    public void testGenerateDefault() {
        CaptchaGenerator generator = CaptchaGenerator.create();
        CaptchaResult result = generator.generate();
        
        assertNotNull(result);
        assertNotNull(result.getCode());
        assertNotNull(result.getImage());
        assertEquals(4, result.getCode().length());
        assertTrue(result.getCode().matches("\\d+"));
    }
    
    @Test
    public void testGenerateCode() {
        CaptchaGenerator generator = CaptchaGenerator.create();
        String code = generator.generateCode();
        
        assertNotNull(code);
        assertEquals(4, code.length());
        assertTrue(code.matches("\\d+"));
    }
    
    @Test
    public void testGenerateImage() {
        CaptchaGenerator generator = CaptchaGenerator.create();
        String code = "1234";
        BufferedImage image = generator.generateImage(code);
        
        assertNotNull(image);
        assertEquals(120, image.getWidth());
        assertEquals(40, image.getHeight());
    }
    
    @Test
    public void testCustomConfig() {
        CaptchaConfig config = CaptchaConfig.defaultConfig()
            .width(200)
            .height(60)
            .codeCount(6)
            .backgroundColor(Color.WHITE)
            .textColor(Color.BLACK)
            .fontSize(32);
        
        CaptchaGenerator generator = CaptchaGenerator.create(config);
        CaptchaResult result = generator.generate();
        
        assertNotNull(result);
        assertEquals(6, result.getCode().length());
        assertEquals(200, result.getImage().getWidth());
        assertEquals(60, result.getImage().getHeight());
    }
    
    @Test
    public void testToBytes() throws IOException {
        CaptchaGenerator generator = CaptchaGenerator.create();
        CaptchaResult result = generator.generate();
        
        byte[] bytes = generator.toBytes(result.getImage());
        
        assertNotNull(bytes);
        assertTrue(bytes.length > 0);
    }
    
    @Test
    public void testToBase64() throws IOException {
        CaptchaGenerator generator = CaptchaGenerator.create();
        CaptchaResult result = generator.generate();
        
        String base64 = generator.toBase64(result.getImage());
        
        assertNotNull(base64);
        assertTrue(base64.length() > 0);
    }
    
    @Test
    public void testToBase64WithPrefix() throws IOException {
        CaptchaGenerator generator = CaptchaGenerator.create();
        CaptchaResult result = generator.generate();
        
        String base64 = generator.toBase64WithPrefix(result.getImage());
        
        assertNotNull(base64);
        assertTrue(base64.startsWith("data:image/png;base64,"));
    }
}