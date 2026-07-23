# 如何使用 Simple Captcha 工具包

## 1. 安装到本地Maven仓库

```bash
mvn clean install
```

## 2. 在项目中添加依赖

### Maven项目

在 `pom.xml` 中添加：

```xml
<dependency>
    <groupId>io.github.hezw86</groupId>
    <artifactId>simple-captcha</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle项目

在 `build.gradle` 中添加：

```groovy
implementation 'io.github.hezw86:simple-captcha:1.0.0'
```

## 3. 基础使用

```java
import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaResult;
import javax.imageio.ImageIO;
import java.io.File;

public class Example {
    public static void main(String[] args) throws Exception {
        // 创建生成器（使用默认配置）
        CaptchaGenerator generator = CaptchaGenerator.create();
        
        // 生成验证码
        CaptchaResult result = generator.generate();
        
        // 获取验证码文本
        String code = result.getCode();
        System.out.println("验证码: " + code);
        
        // 保存图片到文件
        ImageIO.write(result.getImage(), "png", new File("captcha.png"));
        
        // 转换为Base64（用于Web显示）
        String base64 = generator.toBase64WithPrefix(result.getImage());
        System.out.println("Base64: " + base64);
    }
}
```

## 4. 自定义配置

```java
import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaConfig;
import java.awt.Color;

// 创建自定义配置
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .width(200)                    // 图片宽度：200像素
    .height(80)                    // 图片高度：80像素
    .codeCount(6)                  // 验证码位数：6位
    .backgroundColor(Color.WHITE)  // 背景颜色：白色
    .textColor(Color.BLACK)        // 文字颜色：黑色
    .fontSize(40)                  // 字体大小：40
    .fontName("Courier New");      // 字体：Courier New

// 使用自定义配置创建生成器
CaptchaGenerator generator = CaptchaGenerator.create(config);
```

## 5. Spring Boot集成

```java
@RestController
public class CaptchaController {
    
    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();
    private final CaptchaGenerator generator = CaptchaGenerator.create();
    
    @GetMapping("/captcha")
    public Map<String, Object> generate() throws Exception {
        String key = UUID.randomUUID().toString();
        CaptchaResult result = generator.generate();
        
        captchaStore.put(key, result.getCode());
        
        Map<String, Object> data = new HashMap<>();
        data.put("key", key);
        data.put("image", generator.toBase64WithPrefix(result.getImage()));
        return data;
    }
    
    @PostMapping("/verify")
    public Map<String, Object> verify(@RequestBody Map<String, String> req) {
        String code = captchaStore.remove(req.get("key"));
        boolean valid = code != null && code.equals(req.get("code"));
        return Collections.singletonMap("valid", valid);
    }
}
```

## 6. 发布到Maven中央仓库（可选）

如需发布到Maven中央仓库，需要：

1. 注册Sonatype JIRA账号
2. 配置GPG签名
3. 在pom.xml中添加发布配置
4. 执行 `mvn clean deploy`

详细步骤请参考：https://central.sonatype.org/pages/requirements.html
