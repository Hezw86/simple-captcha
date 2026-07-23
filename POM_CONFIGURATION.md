# pom.xml Maven中央仓库发布配置说明

本文档详细说明了pom.xml中为发布到Maven中央仓库所配置的所有必需属性。

## 📋 必需的POM元素清单

### 1. 项目坐标信息 ✅

```xml
<groupId>io.github.hezw86</groupId>
<artifactId>simple-captcha</artifactId>
<version>1.0.0</version>
<packaging>jar</packaging>
```

### 2. 项目基本信息 ✅

```xml
<name>Simple Captcha</name>
<description>
    A simple, lightweight, pure numeric captcha generator with pure color background.
    Features automatic contrast color calculation, zero dependencies, and thread-safe design.
</description>
<url>https://github.com/hezw86/simple-captcha</url>
<inceptionYear>2026</inceptionYear>
```

**说明：**
- `name`: 项目名称，必填
- `description`: 项目描述，必填
- `url`: 项目主页URL，必填
- `inceptionYear`: 项目创建年份，推荐填写

### 3. 组织信息 ✅

```xml
<organization>
    <name>hezw86</name>
    <url>https://github.com/hezw86</url>
</organization>
```

**说明：**
- `organization`: 组织信息，推荐填写

### 4. 许可证信息 ✅

```xml
<licenses>
    <license>
        <name>MIT License</name>
        <url>https://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
    </license>
</licenses>
```

**说明：**
- `licenses`: 许可证信息，必填
- `name`: 许可证名称
- `url`: 许可证全文URL
- `distribution`: 分发方式，通常为"repo"

### 5. 开发者信息 ✅

```xml
<developers>
    <developer>
        <id>hezw86</id>
        <name>He Zhuowei</name>
        <email>hezw86@qq.com</email>
        <url>https://github.com/hezw86</url>
        <roles>
            <role>Developer</role>
            <role>Maintainer</role>
        </roles>
        <timezone>+8</timezone>
        <properties>
            <picUrl>https://github.com/hezw86.png</picUrl>
        </properties>
    </developer>
</developers>
```

**说明：**
- `developers`: 开发者信息，必填
- `id`: 开发者唯一标识（通常是GitHub用户名）
- `name`: 开发者姓名
- `email`: 开发者邮箱
- `url`: 开发者主页
- `roles`: 开发者角色
- `timezone`: 时区

### 6. 贡献者信息 ✅

```xml
<contributors>
    <contributor>
        <name>He Zhuowei</name>
        <email>hezw86@qq.com</email>
        <url>https://github.com/hezw86</url>
        <roles>
            <role>Developer</role>
        </roles>
    </contributor>
</contributors>
```

**说明：**
- `contributors`: 贡献者信息，推荐填写

### 7. SCM（源代码管理）信息 ✅

```xml
<scm>
    <connection>scm:git:git://github.com/hezw86/simple-captcha.git</connection>
    <developerConnection>scm:git:ssh://github.com:hezw86/simple-captcha.git</developerConnection>
    <url>https://github.com/hezw86/simple-captcha</url>
    <tag>HEAD</tag>
</scm>
```

**说明：**
- `scm`: 源代码管理信息，必填
- `connection`: 只读连接URL
- `developerConnection`: 开发者连接URL
- `url`: 可浏览的URL
- `tag`: 当前标签

### 8. Issue管理信息 ✅

```xml
<issueManagement>
    <system>GitHub Issues</system>
    <url>https://github.com/hezw86/simple-captcha/issues</url>
</issueManagement>
```

**说明：**
- `issueManagement`: 问题跟踪系统信息，推荐填写

### 9. CI管理信息 ✅

```xml
<ciManagement>
    <system>GitHub Actions</system>
    <url>https://github.com/hezw86/simple-captcha/actions</url>
</ciManagement>
```

**说明：**
- `ciManagement`: 持续集成系统信息，推荐填写

### 10. 分布管理（发布配置） ✅

```xml
<distributionManagement>
    <snapshotRepository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
    </snapshotRepository>
    <repository>
        <id>ossrh</id>
        <url>https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/</url>
    </repository>
</distributionManagement>
```

**说明：**
- `distributionManagement`: 发布仓库配置，必填
- `snapshotRepository`: 快照版本仓库
- `repository`: 正式版本仓库
- `id`: 服务器ID，需要在`~/.m2/settings.xml`中配置对应的认证信息

### 11. 构建配置 ✅

```xml
<build>
    <plugins>
        <!-- 编译插件 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.10.1</version>
        </plugin>
        
        <!-- 源码打包插件（必需） -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-source-plugin</artifactId>
            <version>3.2.1</version>
        </plugin>
        
        <!-- JavaDoc插件（必需） -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>3.4.1</version>
        </plugin>
        
        <!-- GPG签名插件（必需） -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-gpg-plugin</artifactId>
            <version>3.0.1</version>
        </plugin>
        
        <!-- Nexus Staging插件（必需） -->
        <plugin>
            <groupId>org.sonatype.plugins</groupId>
            <artifactId>nexus-staging-maven-plugin</artifactId>
            <version>1.6.13</version>
        </plugin>
    </plugins>
</build>
```

**说明：**
- `maven-source-plugin`: 打包源码，Maven中央仓库要求
- `maven-javadoc-plugin`: 生成JavaDoc，Maven中央仓库要求
- `maven-gpg-plugin`: GPG签名，Maven中央仓库要求
- `nexus-staging-maven-plugin`: 自动化发布流程

## 🔐 Maven Settings配置

在`~/.m2/settings.xml`中需要配置：

```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>YOUR_SONATYPE_USERNAME</username>
            <password>YOUR_SONATYPE_PASSWORD</password>
        </server>
    </servers>
    
    <profiles>
        <profile>
            <id>gpg</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
            </properties>
        </profile>
    </profiles>
</settings>
```

## ✅ 验证清单

发布前请确认：

- [x] groupId正确（`io.github.hezw86`）
- [x] 项目基本信息完整（name, description, url）
- [x] 许可证信息完整
- [x] 开发者信息完整
- [x] SCM信息正确
- [x] 分布管理配置正确
- [x] 包含maven-source-plugin
- [x] 包含maven-javadoc-plugin
- [x] 包含maven-gpg-plugin
- [x] 包含nexus-staging-maven-plugin
- [x] GPG密钥已生成并发布到密钥服务器
- [x] Sonatype账号已注册
- [x] Maven settings.xml已配置

## 📚 参考资料

- [Maven中央仓库发布指南](https://central.sonatype.org/pages/requirements.html)
- [Sonatype OSSRH指南](https://central.sonatype.org/pages/ossrh-guide.html)
- [GPG密钥生成](https://central.sonatype.org/pages/working-with-pgp-signatures.html)