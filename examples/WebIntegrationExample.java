package io.github.hezw86.example;

import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaResult;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebIntegrationExample {
    
    private final Map<String, String> captchaStore = new HashMap<>();
    private final CaptchaGenerator generator = CaptchaGenerator.create();
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== Web集成示例 ===\n");
        
        WebIntegrationExample example = new WebIntegrationExample();
        
        Map<String, Object> response1 = example.generateCaptcha();
        System.out.println("生成验证�?");
        System.out.println("  Key: " + response1.get("captchaKey"));
        System.out.println("  Code: " + response1.get("captchaCode"));
        System.out.println("  Base64长度: " + ((String)response1.get("imageBase64")).length());
        
        String key = (String) response1.get("captchaKey");
        String code = (String) response1.get("captchaCode");
        
        boolean valid1 = example.verifyCaptcha(key, code);
        System.out.println("\n验证正确验证�? " + (valid1 ? "成功" : "失败"));
        
        boolean valid2 = example.verifyCaptcha(key, "0000");
        System.out.println("验证错误验证�? " + (valid2 ? "成功" : "失败"));
        
        boolean valid3 = example.verifyCaptcha(key, code);
        System.out.println("再次验证(已过�?: " + (valid3 ? "成功" : "失败"));
    }
    
    public Map<String, Object> generateCaptcha() throws Exception {
        String captchaKey = UUID.randomUUID().toString();
        CaptchaResult result = generator.generate();
        
        captchaStore.put(captchaKey, result.getCode());
        
        Map<String, Object> response = new HashMap<>();
        response.put("captchaKey", captchaKey);
        response.put("captchaCode", result.getCode());
        response.put("imageBase64", generator.toBase64WithPrefix(result.getImage()));
        
        return response;
    }
    
    public boolean verifyCaptcha(String captchaKey, String userInput) {
        if (captchaKey == null || userInput == null) {
            return false;
        }
        
        String storedCode = captchaStore.remove(captchaKey);
        
        if (storedCode == null) {
            return false;
        }
        
        return storedCode.equalsIgnoreCase(userInput.trim());
    }
}