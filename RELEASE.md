# 发布到Maven中央仓库指南

本文档说明如何使用新的 `central-publishing-maven-plugin` 发布到Maven中央仓库。

## 🎯 新发布方式的优势

- ✅ 配置更简单
- ✅ 自动化程度更高
- ✅ 使用令牌认证（更安全）
- ✅ 发布速度更快

## 前置要求

### 1. Sonatype Central Portal 账号

1. 访问 https://central.sonatype.com/ 注册账号
2. 登录后，进入 "View Account" → "Namespaces"
3. 验证命名空间 `io.github.hezw86`（需要验证GitHub仓库所有权）
4. 等待审批（通常几分钟到几小时）

### 2. 生成用户令牌

1. 登录 https://central.sonatype.com/
2. 进入 "View Account" → "User Token"
3. 点击 "Generate User Token"
4. **重要：** 复制生成的用户名和密码（只显示一次）

### 3. GPG密钥

安装GPG并生成密钥：

```bash
# 安装GPG (Windows)
choco install gnupg

# 安装GPG (macOS)
brew install gnupg

# 安装GPG (Linux)
sudo apt-get install gnupg

# 生成密钥
gpg --full-generate-key
# 选择：RSA and RSA (default)
# 密钥长度：4096
# 过期时间：0 (不过期) 或根据需要设置
# 输入姓名、邮箱、密码（记住密码！）

# 查看密钥
gpg --list-keys
# 记录公钥ID（如：ABC12345）

# 发布公钥到密钥服务器
gpg --keyserver keyserver.ubuntu.com --send-keys ABC12345
gpg --keyserver pgp.mit.edu --send-keys ABC12345

# 验证公钥已发布
gpg --keyserver keyserver.ubuntu.com --recv-keys ABC12345
```

### 4. Maven Settings配置

编辑 `~/.m2/settings.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 
          http://maven.apache.org/xsd/settings-1.0.0.xsd">
    
    <!-- 配置 Central Portal 令牌 -->
    <servers>
        <server>
            <id>central</id>
            <!-- 替换为你的令牌用户名 -->
            <username>YOUR_TOKEN_USERNAME</username>
            <!-- 替换为你的令牌密码 -->
            <password>YOUR_TOKEN_PASSWORD</password>
        </server>
    </servers>
    
    <!-- 配置 GPG 签名 -->
    <profiles>
        <profile>
            <id>gpg</id>
            <properties>
                <!-- GPG可执行文件路径 -->
                <gpg.executable>gpg</gpg.executable>
                <!-- GPG密钥密码 -->
                <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
    
    <!-- 激活GPG配置 -->
    <activeProfiles>
        <activeProfile>gpg</activeProfile>
    </activeProfiles>
</settings>
```

**重要提示：**
- `server.id` 必须是 `central`（与pom.xml中的 `publishingServerId` 匹配）
- 用户名和密码使用 Central Portal 生成的令牌，不是Sonatype账号密码

## 发布流程

### 方式一：GitHub Actions自动发布（推荐）

项目已配置GitHub Actions自动发布。

1. **配置GitHub Secrets**

   在GitHub仓库设置中添加以下Secrets：
   
   - `CENTRAL_TOKEN_USERNAME`: Central Portal令牌用户名
   - `CENTRAL_TOKEN_PASSWORD`: Central Portal令牌密码
   - `GPG_PRIVATE_KEY`: GPG私钥（使用 `gpg --armor --export-secret-keys YOUR_KEY_ID` 导出）
   - `GPG_PASSPHRASE`: GPG密钥密码

2. **触发发布**
   
   ```bash
   # 方式1：推送标签
   git tag v1.0.0
   git push origin v1.0.0
   
   # 方式2：推送到main分支
   git push origin main
   ```

3. **查看发布状态**
   
   访问 https://github.com/hezw86/simple-captcha/actions 查看构建状态

### 方式二：手动发布

```bash
# 1. 清理并编译
mvn clean compile

# 2. 运行测试
mvn test

# 3. 发布到中央仓库
mvn deploy

# 或者一次性执行
mvn clean deploy
```

**发布过程：**
1. 编译项目
2. 运行测试
3. 打包jar、源码、JavaDoc
4. GPG签名
5. 上传到Central Portal
6. 自动发布（等待几分钟）

### 方式三：跳过测试发布（紧急情况）

```bash
mvn clean deploy -DskipTests
```

## 发布后验证

### 1. 检查中央仓库

等待10-30分钟后访问：
https://central.sonatype.com/artifact/io.github.hezw86/simple-captcha

### 2. 在项目中测试

```xml
<dependency>
    <groupId>io.github.hezw86</groupId>
    <artifactId>simple-captcha</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 3. 搜索验证

访问 https://central.sonatype.com/ 搜索 `simple-captcha`

## 常见问题

### Q: 发布失败，提示认证错误？
A: 
1. 检查 settings.xml 中的令牌是否正确
2. 确认令牌未过期（重新生成令牌）
3. 确认 server.id 是 `central`

### Q: 发布失败，提示签名错误？
A: 
1. 确认GPG密钥已生成
2. 确认公钥已发布到密钥服务器
3. 确认 settings.xml 中的密码正确
4. 测试GPG签名：`echo "test" | gpg --clearsign`

### Q: 发布成功但在中央仓库找不到？
A: 
1. 等待10-30分钟同步时间
2. 检查 https://central.sonatype.com/ 是否已显示
3. 查看GitHub Actions日志确认发布状态

### Q: 如何发布Snapshot版本？
A: 
修改版本号为 `1.0.1-SNAPSHOT`，然后执行 `mvn deploy`

### Q: 为什么不需要 distributionManagement？
A: 
新的 `central-publishing-maven-plugin` 会自动处理发布目标，不需要手动配置。

### Q: 如何查看发布日志？
A: 
访问 https://central.sonatype.com/ → "Deployments" 查看发布历史和日志

## 版本号规则

遵循语义化版本（Semantic Versioning）：

- `MAJOR.MINOR.PATCH`（如 `1.0.0`）
- Snapshot版本：`MAJOR.MINOR.PATCH-SNAPSHOT`（如 `1.0.1-SNAPSHOT`）

**版本递增规则：**
- MAJOR：不兼容的API变更
- MINOR：向后兼容的功能新增
- PATCH：向后兼容的问题修复

## 发布检查清单

发布前请确认：

- [ ] 更新版本号（pom.xml）
- [ ] 更新 CHANGELOG.md
- [ ] 运行所有测试通过：`mvn test`
- [ ] 代码覆盖率达标：`mvn jacoco:report`
- [ ] JavaDoc生成成功：`mvn javadoc:javadoc`
- [ ] GPG密钥有效且已发布
- [ ] Central Portal令牌有效
- [ ] settings.xml配置正确
- [ ] GitHub Secrets配置正确（自动发布）

## 参考文档

- [Central Portal 官方文档](https://central.sonatype.org/)
- [central-publishing-maven-plugin 文档](https://central.sonatype.org/publish/publish-portal-api/)
- [生成用户令牌](https://central.sonatype.org/publish/generate-portal-token/)
- [GPG签名配置](https://central.sonatype.org/publish/requirements/gpg/)
- [语义化版本](https://semver.org/lang/zh-CN/)
