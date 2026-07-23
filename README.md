# Simple Captcha

[![Maven Central](https://img.shields.io/maven-central/v/io.github.hezw86/simple-captcha.svg)](https://central.sonatype.com/artifact/io.github.hezw86/simple-captcha)
[![Build Status](https://github.com/hezw86/simple-captcha/workflows/CI/badge.svg)](https://github.com/hezw86/simple-captcha/actions)
[![Codecov](https://codecov.io/gh/hezw86/simple-captcha/branch/main/graph/badge.svg)](https://codecov.io/gh/hezw86/simple-captcha)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8%2B-green.svg)](https://openjdk.org/)

**English** | [‰∏≠Êñá](README_CN.md)

A simple, lightweight, pure numeric captcha generator with pure color background. Features **automatic contrast color calculation**, zero dependencies, and thread-safe design.

## ‚ú?Features

- ‚ú?Pure numeric captcha, easy to recognize
- ‚ú?Pure color background, no interference
- ‚ú?**Automatic contrast color calculation** (based on WCAG 2.0 standard)
- ‚ú?Lightweight, zero third-party dependencies
- ‚ú?Fully customizable (size, color, font, etc.)
- ‚ú?Base64 encoding support for web usage
- ‚ú?Thread-safe, supports concurrent access
- ‚ú?Java 8+ support
- ‚ú?Complete JavaDoc documentation
- ‚ú?Unit test coverage > 80%

## üéØ Automatic Contrast Color

**The killer feature!** No more invisible text on dark backgrounds.

```java
// Dark blue background? No problem!
CaptchaConfig config = CaptchaConfig.defaultConfig()
    .backgroundColor(new Color(0, 0, 139))  // Dark blue
    .autoContrast(true);                     // Auto-calculate text color

// Result: White text on dark blue background (perfect contrast!)
```

The library uses WCAG 2.0 luminance algorithm to automatically calculate the best text color for any background color.

## üì¶ Installation

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

## üöÄ Quick Start

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
    .width(200)                    // Image width: 200px
    .height(80)                    // Image height: 80px
    .codeCount(6)                  // Code length: 6 digits
    .backgroundColor(Color.BLUE)   // Background: blue
    .autoContrast(true)            // Auto text color (white)
    .fontSize(40)                  // Font size: 40
    .fontFamily("Courier New");    // Font family

// Create generator with custom config
CaptchaGenerator generator = CaptchaGenerator.create(config);
```

### Spring Boot Integration

```java
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
    
    private final Map<String, String> captchaStore = new ConcurrentHashMap<>();
    private final CaptchaGenerator generator = CaptchaGenerator.create();
    
    @GetMapping("/generate")
    public Map<String, Object> generate() throws Exception {
        String key = UUID.randomUUID().toString();
        CaptchaResult result = generator.generate();
        
        captchaStore.put(key, result.getCode());
        
        return Map.of(
            "captchaKey", key,
            "imageBase64", generator.toBase64WithPrefix(result.getImage())
        );
    }
    
    @PostMapping("/verify")
    public Map<String, Object> verify(@RequestBody Map<String, String> req) {
        String code = captchaStore.remove(req.get("captchaKey"));
        boolean valid = code != null && code.equals(req.get("userInput"));
        return Map.of("valid", valid);
    }
}
```

## üìñ API Documentation

### CaptchaGenerator

Core captcha generator class.

**Methods:**

| Method | Return Type | Description |
|--------|-------------|-------------|
| `generate()` | `CaptchaResult` | Generate captcha (code + image) |
| `generateCode()` | `String` | Generate code only |
| `generateImage(String code)` | `BufferedImage` | Generate image from code |
| `toBytes(BufferedImage image)` | `byte[]` | Convert image to bytes |
| `toBase64(BufferedImage image)` | `String` | Convert image to Base64 |
| `toBase64WithPrefix(BufferedImage image)` | `String` | Convert image to Base64 with prefix |

### CaptchaConfig

Configuration class with fluent API.

**Default Configuration:**

| Parameter | Default | Description |
|-----------|---------|-------------|
| width | 120 | Image width (px) |
| height | 40 | Image height (px) |
| codeCount | 4 | Code length |
| backgroundColor | (240, 240, 240) | Background color (light gray) |
| textColor | auto | Text color (auto-calculated) |
| fontSize | 28 | Font size |
| fontFamily | "Arial" | Font family |
| autoContrast | true | Auto contrast calculation |

### ColorUtils

Color utility class for contrast calculation.

```java
// Calculate contrast color
Color textColor = ColorUtils.calculateContrastColor(backgroundColor);

// Calculate contrast ratio
double ratio = ColorUtils.calculateContrastRatio(color1, color2);

// Check if color is dark
boolean dark = ColorUtils.isDark(color);
```

## üõ†Ô∏?Building

```bash
# Clone the repository
git clone https://github.com/hezw86/simple-captcha.git
cd simple-captcha

# Build
mvn clean install

# Run tests
mvn test

# Generate coverage report
mvn jacoco:report
```

## üìã Examples

See `examples` directory for more examples:

- `BasicExample.java` - Basic usage
- `CustomConfigExample.java` - Custom configuration
- `WebIntegrationExample.java` - Web integration

## ‚ù?FAQ

**Q: How to run in headless environment?**  
A: The library automatically sets `java.awt.headless=true`, no configuration needed.

**Q: Where to store captchas?**  
A: The library doesn't provide storage. Use memory, Redis, etc.

**Q: Is it thread-safe?**  
A: Yes, `CaptchaGenerator` is thread-safe.

**Q: Does it support non-numeric captchas?**  
A: Current version only supports numeric captchas. You can extend it for other types.

**Q: How does auto-contrast work?**  
A: It uses WCAG 2.0 luminance algorithm to calculate relative luminance of the background, then returns black or white text for maximum contrast.

## üìÑ License

This project is licensed under the [MIT License](LICENSE).

## ü§ù Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details.

## üìù Changelog

See [CHANGELOG.md](CHANGELOG.md) for a list of changes.

## üë• Author

**He Zhuowei**
- Email: hezw86@outlook.com
- GitHub: [@hezw86](https://github.com/hezw86)

## ‚≠?Star History

If you find this project useful, please consider giving it a star ‚≠?

[![Star History Chart](https://api.star-history.com/svg?repos=hezw86/simple-captcha&type=Date)](https://star-history.com/#hezw86/simple-captcha&Date)
