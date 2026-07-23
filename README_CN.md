# Simple Captcha

[![Maven Central](https://img.shields.io/maven-central/v/io.github.hezw86/simple-captcha.svg)](https://central.sonatype.com/artifact/io.github.hezw86/simple-captcha)
[![Build Status](https://github.com/hezw86/simple-captcha/workflows/CI/badge.svg)](https://github.com/Hezw86/simple-captcha/actions)
[![Codecov](https://codecov.io/gh/hezw86/simple-captcha/branch/main/graph/badge.svg)](https://codecov.io/gh/hezw86/simple-captcha)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8%2B-green.svg)](https://openjdk.org/)

[English](README.md) | **中文**

一个简单、轻量级的纯数字验证码生成器，纯色背景，无干扰元素。**智能对比色计算**，零依赖，线程安全。

## ✨ 特性

- ✅ 纯数字验证码，简单易识别
- ✅ 纯色背景，无干扰线、噪点
- ✅ **智能对比色计算**（基于WCAG 2.0标准）
- ✅ 轻量级，零第三方依赖
- ✅ 完全可自定义（尺寸、颜色、字体等）
- ✅ 支持Base64编码，方便Web使用
- ✅ 线程安全，支持并发访问
- ✅ Java 8+ 支持
- ✅ 完整的JavaDoc文档
- ✅ 单元测试覆盖率 > 80%

## 🎯 智能对比色

**核心特性！** 彻底解决深色背景配黑字看不清的问题。

```java
// 深蓝色背景？没问题！
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(0, 0, 139))  // 深蓝色
    .autoContrast(true);                     // 自动计算文字颜色

// 结果：深蓝色背景配白色文字（完美对比！）
```

库使用WCAG 2.0亮度算法自动计算任何背景色的最佳文字颜色。

## 📦 安装

### Maven

```xml
<dependency>
    <groupId>io.github.hezw86</groupId>
    <artifactId>simple-captcha</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.hezw86:simple-captcha:1.0.0'
```

### Gradle (Kotlin DSL)

```kotlin
implementation("io.github.hezw86:simple-captcha:1.0.0")
```

## 🚀 快速开始

### 基础使用

```java
import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaResult;
import javax.imageio.ImageIO;
import java.io.File;

// 使用默认配置创建生成器
CaptchaGenerator generator = CaptchaGenerator.create();

// 生成验证码
CaptchaResult result = generator.generate();

// 获取验证码文本
String code = result.getCode();  // 例如: "1234"

// 获取图片
BufferedImage image = result.getImage();

// 转换为Base64（用于Web显示）
String base64 = generator.toBase64WithPrefix(image);

// 保存为文件
ImageIO.write(image, "png", new File("captcha.png"));
```

### 自定义配置

```java
import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaConfig;
import java.awt.Color;

// 创建自定义配置
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .width(200)                    // 图片宽度：200像素
    .height(80)                    // 图片高度：80像素
    .codeCount(6)                  // 验证码位数：6位
    .backgroundColor(Color.BLUE)   // 背景颜色：蓝色
    .textColor(Color.WHITE)        // 文字颜色：白色
    .fontSize(40)                  // 字体大小：40
    .autoContrast(true);           // 启用自动对比色（推荐）

// 创建生成器
CaptchaGenerator generator = CaptchaGenerator.create(config);

// 生成验证码
CaptchaResult result = generator.generate();
```

### 智能对比色示例

```java
// 示例1：深色背景自动配浅色文字
CaptchaConfig darkConfig = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(0, 0, 139))  // 深蓝色
    .autoContrast(true);                     // 自动配白色文字

// 示例2：浅色背景自动配深色文字
CaptchaConfig lightConfig = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(255, 255, 200))  // 浅黄色
    .autoContrast(true);                         // 自动配黑色文字

// 示例3：随机背景色 + 自动对比色
Random random = new Random();
Color randomBg = new Color(
    random.nextInt(256),
    random.nextInt(256),
    random.nextInt(256)
);
CaptchaConfig randomConfig = CaptchaConfig.defaultConfig()
    .backgroundColor(randomBg)
    .autoContrast(true);  // 总是最佳对比度
```

## 📖 API 文档

### CaptchaGenerator

主要的验证码生成器类。

```java
// 创建默认配置的生成器
CaptchaGenerator generator = CaptchaGenerator.create();

// 创建自定义配置的生成器
CaptchaGenerator generator = CaptchaGenerator.create(config);

// 生成验证码
CaptchaResult result = generator.generate();

// 图片转Base64（无前缀）
String base64 = generator.toBase64(image);

// 图片转Base64（带前缀，可直接用于HTML img src）
String base64WithPrefix = generator.toBase64WithPrefix(image);
```

### CaptchaConfig

配置类，使用建造者模式。

| 方法 | 说明 | 默认值 |
|------|------|--------|
| `width(int)` | 图片宽度（像素） | 120 |
| `height(int)` | 图片高度（像素） | 40 |
| `codeCount(int)` | 验证码位数 | 4 |
| `backgroundColor(Color)` | 背景颜色 | 白色 |
| `textColor(Color)` | 文字颜色 | 黑色 |
| `fontSize(int)` | 字体大小 | 28 |
| `fontName(String)` | 字体名称 | Arial |
| `autoContrast(boolean)` | 自动对比色 | false |

### CaptchaResult

验证码生成结果。

```java
// 获取验证码文本
String code = result.getCode();

// 获取图片
BufferedImage image = result.getImage();

// 获取生成时间戳
long timestamp = result.getTimestamp();
```

### ColorUtils

颜色工具类，用于计算对比色。

```java
import io.github.hezw86.ColorUtils;

// 计算最佳对比色
Color bgColor = new Color(0, 0, 139);  // 深蓝色
Color textColor = ColorUtils.getContrastColor(bgColor);  // 返回白色

// 计算亮度（WCAG 2.0标准）
double luminance = ColorUtils.getLuminance(bgColor);

// 判断是否为深色
boolean isDark = ColorUtils.isDark(bgColor);  // true
```

## 🎨 示例

### Web应用示例（Spring Boot）

```java
@RestController
public class CaptchaController {
    
    private final CaptchaGenerator generator = CaptchaGenerator.create();
    
    @GetMapping("/captcha")
    public Map<String, String> getCaptcha() {
        CaptchaResult result = generator.generate();
        
        Map<String, String> response = new HashMap<>();
        response.put("code", result.getCode());
        response.put("image", generator.toBase64WithPrefix(result.getImage()));
        
        return response;
    }
}
```

### 验证示例

```java
public class LoginService {
    
    private final CaptchaGenerator generator = CaptchaGenerator.create();
    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();
    
    // 生成验证码
    public String generateCaptcha(String sessionId) {
        CaptchaResult result = generator.generate();
        captchaStore.put(sessionId, result.getCode());
        return generator.toBase64WithPrefix(result.getImage());
    }
    
    // 验证验证码
    public boolean verifyCaptcha(String sessionId, String userInput) {
        String correctCode = captchaStore.remove(sessionId);
        return correctCode != null && correctCode.equals(userInput);
    }
}
```

## 🔧 高级配置

### 自定义字体

```java
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .fontName("Courier New")  // 使用等宽字体
    .fontSize(32)
    .fontStyle(Font.BOLD);
```

### 调整验证码难度

```java
// 简单：4位数字
CaptchaConfig easy = CaptchaConfig.defaultConfig().codeCount(4);

// 中等：6位数字
CaptchaConfig medium = CaptchaConfig.defaultConfig().codeCount(6);

// 困难：8位数字
CaptchaConfig hard = CaptchaConfig.defaultConfig().codeCount(8);
```

## � 性能

- 生成速度：约 10,000 次/秒（单线程）
- 内存占用：极小（每次生成约 1KB）
- 线程安全：支持多线程并发调用
- 无需连接池或缓存

## 🤝 贡献

欢迎贡献代码！请查看 [贡献指南](CONTRIBUTING.md)。

## � 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- 感谢所有贡献者
- 灵感来自简单即美的设计理念

## � 联系方式

- 作者：He Zhuowei
- 邮箱：hezw86@qq.com
- GitHub：https://github.com/hezw86

## 📝 更新日志

### 1.0.0 (2026-07-22)
- ✨ 首次发布
- ✨ 智能对比色计算功能
- ✨ 完整的API文档
- ✨ 单元测试覆盖率 > 80%
- ✨ 发布到Maven中央仓库
