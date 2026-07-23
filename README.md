# Simple Captcha

[![Maven Central](https://img.shields.io/maven-central/v/io.github.hezw86/simple-captcha.svg)](https://central.sonatype.com/artifact/io.github.hezw86/simple-captcha)
[![Build Status](https://github.com/hezw86/simple-captcha/workflows/CI/badge.svg)](https://github.com/hezw86/simple-captcha/actions)
[![Codecov](https://codecov.io/gh/hezw86/simple-captcha/branch/main/graph/badge.svg)](https://codecov.io/gh/hezw86/simple-captcha)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8%2B-green.svg)](https://openjdk.org/)

**English** | [中文](README_CN.md)

A simple, lightweight, pure numeric captcha generator with pure color background. Features **automatic contrast color calculation**, zero dependencies, and thread-safe design.

## ✨ Features

- ✅ Pure numeric captcha, easy to recognize
- ✅ Pure color background, no interference
- ✅ **Automatic contrast color calculation** (based on WCAG 2.0 standard)
- ✅ Lightweight, zero third-party dependencies
- ✅ Fully customizable (size, color, font, etc.)
- ✅ Base64 encoding support for web usage
- ✅ Thread-safe, supports concurrent access
- ✅ Java 8+ support
- ✅ Complete JavaDoc documentation
- ✅ Unit test coverage > 80%

## 🎯 Automatic Contrast Color

**The killer feature!** No more invisible text on dark backgrounds.

```java
// Dark blue background? No problem!
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(0, 0, 139))  // Dark blue
    .autoContrast(true);                     // Auto-calculate text color

// Result: White text on dark blue background (perfect contrast!)
```

The library uses WCAG 2.0 luminance algorithm to automatically calculate the best text color for any background color.

## 📦 Installation

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

## 🚀 Quick Start

### Basic Usage

```java
import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaResult;
import javax.imageio.ImageIO;
import java.io.File;

// Create generator with default config
CaptchaGenerator generator = CaptchaGenerator.create();

// Generate captcha
CaptchaResult result = generator.generate();

// Get captcha code
String code = result.getCode();  // e.g., "1234"

// Get image
BufferedImage image = result.getImage();

// Convert to Base64 (for web display)
String base64 = generator.toBase64WithPrefix(image);

// Save to file
ImageIO.write(image, "png", new File("captcha.png"));
```

### Custom Configuration

```java
import io.github.hezw86.CaptchaGenerator;
import io.github.hezw86.CaptchaConfig;
import java.awt.Color;

// Create custom config
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .width(200)                    // Width: 200px
    .height(80)                    // Height: 80px
    .codeCount(6)                  // Code length: 6 digits
    .backgroundColor(Color.BLUE)   // Background: blue
    .textColor(Color.WHITE)        // Text: white
    .fontSize(40)                  // Font size: 40
    .autoContrast(true);           // Enable auto contrast (recommended)

// Create generator
CaptchaGenerator generator = CaptchaGenerator.create(config);

// Generate captcha
CaptchaResult result = generator.generate();
```

### Auto Contrast Examples

```java
// Example 1: Dark background auto-gets light text
CaptchaConfig darkConfig = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(0, 0, 139))  // Dark blue
    .autoContrast(true);                     // Auto white text

// Example 2: Light background auto-gets dark text
CaptchaConfig lightConfig = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(255, 255, 200))  // Light yellow
    .autoContrast(true);                         // Auto black text

// Example 3: Random background + auto contrast
Random random = new Random();
Color randomBg = new Color(
    random.nextInt(256),
    random.nextInt(256),
    random.nextInt(256)
);
CaptchaConfig randomConfig = CaptchaConfig.defaultConfig()
    .backgroundColor(randomBg)
    .autoContrast(true);  // Always best contrast
```

## 📖 API Documentation

### CaptchaGenerator

Main captcha generator class.

```java
// Create with default config
CaptchaGenerator generator = CaptchaGenerator.create();

// Create with custom config
CaptchaGenerator generator = CaptchaGenerator.create(config);

// Generate captcha
CaptchaResult result = generator.generate();

// Image to Base64 (without prefix)
String base64 = generator.toBase64(image);

// Image to Base64 (with prefix, can be used directly in HTML img src)
String base64WithPrefix = generator.toBase64WithPrefix(image);
```

### CaptchaConfig

Configuration class using builder pattern.

| Method | Description | Default |
|--------|-------------|---------|
| `width(int)` | Image width (pixels) | 120 |
| `height(int)` | Image height (pixels) | 40 |
| `codeCount(int)` | Number of digits | 4 |
| `backgroundColor(Color)` | Background color | White |
| `textColor(Color)` | Text color | Black |
| `fontSize(int)` | Font size | 28 |
| `fontName(String)` | Font name | Arial |
| `autoContrast(boolean)` | Auto contrast | false |

### CaptchaResult

Captcha generation result.

```java
// Get captcha code
String code = result.getCode();

// Get image
BufferedImage image = result.getImage();

// Get generation timestamp
long timestamp = result.getTimestamp();
```

### ColorUtils

Color utility class for contrast color calculation.

```java
import io.github.hezw86.ColorUtils;

// Calculate best contrast color
Color bgColor = new Color(0, 0, 139);  // Dark blue
Color textColor = ColorUtils.getContrastColor(bgColor);  // Returns white

// Calculate luminance (WCAG 2.0 standard)
double luminance = ColorUtils.getLuminance(bgColor);

// Check if color is dark
boolean isDark = ColorUtils.isDark(bgColor);  // true
```

## 🎨 Examples

### Web Application Example (Spring Boot)

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

### Verification Example

```java
public class LoginService {
    
    private final CaptchaGenerator generator = CaptchaGenerator.create();
    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();
    
    // Generate captcha
    public String generateCaptcha(String sessionId) {
        CaptchaResult result = generator.generate();
        captchaStore.put(sessionId, result.getCode());
        return generator.toBase64WithPrefix(result.getImage());
    }
    
    // Verify captcha
    public boolean verifyCaptcha(String sessionId, String userInput) {
        String correctCode = captchaStore.remove(sessionId);
        return correctCode != null && correctCode.equals(userInput);
    }
}
```

## 🔧 Advanced Configuration

### Custom Font

```java
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .fontName("Courier New")  // Use monospace font
    .fontSize(32)
    .fontStyle(Font.BOLD);
```

### Adjust Difficulty

```java
// Easy: 4 digits
CaptchaConfig easy = CaptchaConfig.defaultConfig().codeCount(4);

// Medium: 6 digits
CaptchaConfig medium = CaptchaConfig.defaultConfig().codeCount(6);

// Hard: 8 digits
CaptchaConfig hard = CaptchaConfig.defaultConfig().codeCount(8);
```

## � Performance

- Generation speed: ~10,000 times/sec (single thread)
- Memory usage: Minimal (~1KB per generation)
- Thread-safe: Supports concurrent calls
- No connection pool or cache needed

## 🤝 Contributing

Contributions are welcome! Please check out our [Contributing Guide](CONTRIBUTING.md).

## � License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Thanks to all contributors
- Inspired by the principle that simplicity is beauty

## � Contact

- Author: He Zhuowei
- Email: hezw86@qq.com
- GitHub: https://github.com/hezw86

## 📝 Changelog

### 1.0.0 (2026-07-22)
- ✨ Initial release
- ✨ Automatic contrast color calculation
- ✨ Complete API documentation
- ✨ Unit test coverage > 80%
- ✨ Published to Maven Central Repository
