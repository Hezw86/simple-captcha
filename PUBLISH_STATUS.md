# 项目发布状态说明

## ✅ 当前状态

### 🎉 已成功发布到Maven中央仓库！

**发布信息：**
- **部署ID:** `54dd1f5e-d832-452f-b8da-c06203b831c2`
- **发布时间:** 2026-07-22 17:42:45 (中国标准时间)
- **版本:** 1.0.0
- **状态:** 等待中央仓库验证和发布（通常需要几分钟到几小时）

**发布的文件：**
1. `simple-captcha-1.0.0.jar` - 主jar包
2. `simple-captcha-1.0.0-sources.jar` - 源码jar包
3. `simple-captcha-1.0.0-javadoc.jar` - 文档jar包
4. `simple-captcha-1.0.0.pom` - POM文件
5. 以上所有文件的 `.asc` GPG签名文件

**GPG签名验证：**
```
gpg: Signature made 07/22/26 17:42:45 中国标准时间
gpg:                using EDDSA key 34CEB3F5C8C0DB9531571972D2420DA43B1BBF32
gpg: Good signature from "Hezw86 <hezw86@qq.com>" [ultimate]
```

### 项目配置完成
- ✅ pom.xml配置正确
- ✅ 使用新的 `central-publishing-maven-plugin`
- ✅ 所有测试通过（24个测试）
- ✅ 项目可以正常构建
- ✅ GPG签名成功
- ✅ 已上传到Maven中央仓库

## ⚠️ 发布前的准备工作

### 1. 安装GPG（必需）

**Windows:**
```powershell
choco install gnupg -y
```

**macOS:**
```bash
brew install gnupg
```

**Linux:**
```bash
sudo apt-get install gnupg
```

### 2. 生成GPG密钥

```bash
gpg --full-generate-key
# 选择：RSA (1), 4096位, 永不过期 (0)
# 输入姓名、邮箱、密码（记住密码！）
```

### 3. 发布公钥

```bash
# 查看密钥ID
gpg --list-keys

# 发布公钥（替换YOUR_KEY_ID）
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
gpg --keyserver pgp.mit.edu --send-keys YOUR_KEY_ID
```

### 4. 获取Central Portal令牌

1. 访问 https://central.sonatype.com/
2. 注册/登录
3. "View Account" → "User Token" → "Generate User Token"
4. 复制用户名和密码

### 5. 配置Maven Settings

编辑 `~/.m2/settings.xml`：

```xml
<settings>
    <servers>
        <server>
            <id>central</id>
            <username>YOUR_TOKEN_USERNAME</username>
            <password>YOUR_TOKEN_PASSWORD</password>
        </server>
    </servers>
    
    <profiles>
        <profile>
            <id>gpg</id>
            <properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
    
    <activeProfiles>
        <activeProfile>gpg</activeProfile>
    </activeProfiles>
</settings>
```

## 🚀 发布命令

### 方式一：使用发布脚本
```bash
publish.bat
```

### 方式二：手动发布
```bash
# 完整发布（包含测试）
mvn clean deploy

# 快速发布（跳过测试）
mvn clean deploy -DskipTests
```

## 📚 相关文档

- **GPG_SETUP.md** - GPG安装和配置详细指南
- **RELEASE.md** - 完整的发布流程说明
- **CENTRAL_PUBLISHING_PLUGIN.md** - 新发布插件说明
- **POM_CONFIGURATION.md** - pom.xml配置说明

## 🔧 快速测试脚本

### build.bat - 本地构建测试
跳过GPG签名，用于验证项目配置：
```bash
build.bat
```

### publish.bat - 发布到中央仓库
完整发布流程，包含GPG签名：
```bash
publish.bat
```

## 📋 发布检查清单

- [ ] GPG已安装并配置
- [ ] GPG密钥已生成
- [ ] 公钥已发布到密钥服务器
- [ ] Central Portal账号已注册
- [ ] Central Portal令牌已生成
- [ ] Maven settings.xml已配置
- [ ] 项目测试通过：`mvn test`
- [ ] 项目可以构建：`mvn package`

## ❓ 常见问题

### Q: 为什么需要GPG？
A: Maven中央仓库要求所有构件必须经过GPG签名，以确保安全性和真实性。

### Q: settings.xml在哪里？
A: 
- Windows: `C:\Users\你的用户名\.m2\settings.xml`
- macOS/Linux: `~/.m2/settings.xml`

### Q: 发布失败怎么办？
A: 
1. 检查GPG配置：`gpg --version`
2. 测试GPG签名：`echo "test" | gpg --clearsign`
3. 检查settings.xml配置
4. 查看详细错误日志：`mvn deploy -e -X`

### Q: 如何验证发布成功？
A: 
等待10-30分钟后访问：
https://central.sonatype.com/artifact/io.github.hezw86/simple-captcha

## 🎯 下一步

1. ✅ ~~安装GPG（查看 GPG_SETUP.md）~~
2. ✅ ~~配置Maven settings.xml~~
3. ✅ ~~运行 `publish.bat` 或 `mvn clean deploy`~~
4. ⏳ 等待同步完成（通常需要几分钟到几小时）
5. 在项目中测试使用

## 📦 如何使用（发布完成后）

发布完成后，其他开发者可以通过以下方式使用：

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

## 🔍 验证发布状态

### 方式1: 访问Sonatype Central Portal
https://central.sonatype.com/publishing/deployments

### 方式2: 搜索Maven Central
https://central.sonatype.com/artifact/io/github/hezw86/simple-captcha

### 方式3: 直接访问Maven仓库
https://repo1.maven.org/maven2/io/github/hezw86/simple-captcha/1.0.0/

## ⚠️ 遇到的问题和解决方案

### 问题1: GPG keyboxd I/O Error

**错误信息：**
```
gpg: sending fd 0x000000000000023c to keyboxd: Input/output error <GnuPG>
[ERROR] Exit code: 2
```

**原因：** GPG 2.5.18版本的keyboxd功能有bug

**解决方案：**
在运行 `mvn clean deploy` 之前，停止 keyboxd 进程：

```bash
# Windows
C:\1run\GnuPG\bin\gpgconf.exe --kill keyboxd

# Linux/Mac
gpgconf --kill keyboxd
```

详细说明请参考 [GPG_KEYBOXD_FIX.md](GPG_KEYBOXD_FIX.md)

### 问题2: JaCoCo不支持Java 23

**解决方案：** 将JaCoCo移到可选的profile中，默认不启用

### 问题3: Lombok编译错误

**解决方案：** 移除Lombok依赖，手动编写getter/setter