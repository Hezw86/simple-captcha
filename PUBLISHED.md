# 🎉 发布成功！

## 发布信息

- **项目名称:** Simple Captcha
- **版本:** 1.0.0
- **发布时间:** 2026-07-22 17:42:45 (中国标准时间)
- **部署ID:** `54dd1f5e-d832-452f-b8da-c06203b831c2`
- **状态:** ✅ 已上传，等待中央仓库验证和发布

## 发布的文件

1. `simple-captcha-1.0.0.jar` - 主jar包
2. `simple-captcha-1.0.0-sources.jar` - 源码jar包
3. `simple-captcha-1.0.0-javadoc.jar` - 文档jar包
4. `simple-captcha-1.0.0.pom` - POM文件
5. 以上所有文件的 `.asc` GPG签名文件

## GPG签名验证

✅ 签名验证通过：

```
gpg: Signature made 07/22/26 17:42:45 中国标准时间
gpg:                using EDDSA key 34CEB3F5C8C0DB9531571972D2420DA43B1BBF32
gpg: Good signature from "Hezw86 <hezw86@qq.com>" [ultimate]
```

## 如何使用（发布完成后）

等待中央仓库验证完成（通常需要几分钟到几小时）后，可以通过以下方式使用：

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

## 验证发布状态

### 方式1: Sonatype Central Portal
https://central.sonatype.com/publishing/deployments

### 方式2: Maven Central Search
https://central.sonatype.com/artifact/io/github/hezw86/simple-captcha

### 方式3: Maven仓库直接访问
https://repo1.maven.org/maven2/io/github/hezw86/simple-captcha/1.0.0/

## 遇到的关键问题和解决方案

### GPG keyboxd I/O Error

**问题：** GPG 2.5.18版本的keyboxd功能有bug，导致签名失败

**解决方案：**
```bash
# 在运行 mvn clean deploy 之前，停止 keyboxd 进程
C:\1run\GnuPG\bin\gpgconf.exe --kill keyboxd
```

详细说明：[GPG_KEYBOXD_FIX.md](GPG_KEYBOXD_FIX.md)

## 下一步

1. ⏳ 等待中央仓库验证和发布（几分钟到几小时）
2. 在 [Sonatype Central Portal](https://central.sonatype.com/publishing/deployments) 查看发布状态
3. 发布完成后，验证可以在 Maven Central 搜索到
4. 测试在其他项目中使用此依赖

## 相关文档

- [PUBLISH_STATUS.md](PUBLISH_STATUS.md) - 详细发布状态
- [GPG_KEYBOXD_FIX.md](GPG_KEYBOXD_FIX.md) - GPG keyboxd问题解决方案
- [RELEASE.md](RELEASE.md) - 发布流程说明
- [GPG_SETUP.md](GPG_SETUP.md) - GPG配置指南
- [README.md](README.md) - 项目说明
- [README_CN.md](README_CN.md) - 项目说明（中文）