# central-publishing-maven-plugin 配置说明

## 🎯 什么是 central-publishing-maven-plugin？

`central-publishing-maven-plugin` 是 Sonatype 在 2024 年推出的新一代 Maven 中央仓库发布插件，用于替代传统的 `nexus-staging-maven-plugin`。

## ✨ 新插件的优势

### 1. 配置更简单
```xml
<!-- 旧方式：需要复杂的配置 -->
<plugin>
    <groupId>org.sonatype.plugins</groupId>
    <artifactId>nexus-staging-maven-plugin</artifactId>
    <configuration>
        <serverId>ossrh</serverId>
        <nexusUrl>https://s01.oss.sonatype.org/</nexusUrl>
        <autoReleaseAfterClose>true</autoReleaseAfterClose>
        <stagingProgressTimeoutMinutes>20</stagingProgressTimeoutMinutes>
    </configuration>
</plugin>

<!-- 新方式：配置简洁 -->
<plugin>
    <groupId>org.sonatype.central</groupId>
    <artifactId>central-publishing-maven-plugin</artifactId>
    <configuration>
        <publishingServerId>central</publishingServerId>
        <tokenAuth>true</tokenAuth>
        <autoPublish>true</autoPublish>
        <waitUntil>published</waitUntil>
    </configuration>
</plugin>
```

### 2. 自动化程度更高
- ✅ 自动处理 staging 过程
- ✅ 自动发布到中央仓库
- ✅ 自动等待发布完成

### 3. 使用新的 Central Portal API
- ✅ 支持令牌认证（Token Auth）
- ✅ 更安全的认证方式
- ✅ 更快的发布速度

## 📦 当前项目配置

### pom.xml 配置

```xml
<build>
    <plugins>
        <!-- GPG 签名插件 -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-gpg-plugin</artifactId>
            <executions>
                <execution>
                    <id>sign-artifacts</id>
                    <phase>verify</phase>
                    <goals>
                        <goal>sign</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        
        <!-- 中央仓库发布插件 -->
        <plugin>
            <groupId>org.sonatype.central</groupId>
            <artifactId>central-publishing-maven-plugin</artifactId>
            <version>0.4.0</version>
            <extensions>true</extensions>
            <configuration>
                <publishingServerId>central</publishingServerId>
                <tokenAuth>true</tokenAuth>
                <autoPublish>true</autoPublish>
                <waitUntil>published</waitUntil>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 配置参数说明

| 参数 | 说明 | 默认值 |
|------|------|--------|
| `publishingServerId` | Maven settings.xml 中配置的服务器ID | central |
| `tokenAuth` | 是否使用令牌认证 | true |
| `autoPublish` | 是否自动发布 | true |
| `waitUntil` | 等待状态（published/validated） | published |

## 🔐 Maven Settings 配置

### 方式一：使用令牌认证（推荐）

1. **获取令牌**
   - 访问 https://central.sonatype.com/
   - 登录后进入 "View Account" → "User Token"
   - 点击 "Generate User Token"
   - 复制生成的用户名和密码

2. **配置 settings.xml**
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

### 方式二：使用传统账号密码

```xml
<settings>
    <servers>
        <server>
            <id>central</id>
            <username>YOUR_SONATYPE_USERNAME</username>
            <password>YOUR_SONATYPE_PASSWORD</password>
        </server>
    </servers>
</settings>
```

## 🚀 发布流程

### 1. 本地发布

```bash
# 清理并编译
mvn clean compile

# 运行测试
mvn test

# 发布到中央仓库
mvn deploy
```

### 2. GitHub Actions 自动发布

项目已配置 GitHub Actions，推送到 main 分支会自动发布：

```yaml
# .github/workflows/ci.yml
- name: Deploy to Maven Central
  run: mvn clean deploy
  env:
    MAVEN_USERNAME: ${{ secrets.CENTRAL_TOKEN_USERNAME }}
    MAVEN_PASSWORD: ${{ secrets.CENTRAL_TOKEN_PASSWORD }}
    MAVEN_GPG_PASSPHRASE: ${{ secrets.GPG_PASSPHRASE }}
```

## 📊 发布流程对比

### 旧方式（nexus-staging-maven-plugin）
```
1. mvn deploy
2. 上传到 OSSRH Staging Repository
3. Close Staging Repository
4. Release Staging Repository
5. 等待同步到中央仓库（2-4小时）
```

### 新方式（central-publishing-maven-plugin）
```
1. mvn deploy
2. 自动上传并发布
3. 等待同步到中央仓库（通常更快）
```

## ⚠️ 注意事项

### 1. 服务器ID必须匹配

pom.xml 中的 `publishingServerId` 必须与 settings.xml 中的 `server.id` 一致：

```xml
<!-- pom.xml -->
<publishingServerId>central</publishingServerId>

<!-- settings.xml -->
<server>
    <id>central</id>  <!-- 必须匹配 -->
    ...
</server>
```

### 2. GPG 密钥要求

- 必须生成 GPG 密钥对
- 公钥必须发布到密钥服务器
- 私钥密码必须在 settings.xml 中配置

### 3. 项目坐标要求

- `groupId` 必须是已验证的命名空间
- 对于 `io.github.hezw86`，需要在 Sonatype 申请并验证

## 🔗 相关链接

- [Central Portal](https://central.sonatype.com/)
- [插件官方文档](https://central.sonatype.org/publish/publish-portal-api/)
- [令牌生成指南](https://central.sonatype.org/publish/generate-portal-token/)
- [GPG 密钥配置](https://central.sonatype.org/publish/requirements/gpg/)

## 📝 常见问题

### Q: 为什么不需要 distributionManagement？
A: 新插件会自动处理发布目标，不需要手动配置 distributionManagement。

### Q: tokenAuth 有什么作用？
A: 使用令牌认证比传统账号密码更安全，推荐使用。

### Q: waitUntil 参数有什么用？
A: 控制发布等待状态：
- `published`: 等待完全发布到中央仓库
- `validated`: 等待验证通过即可

### Q: 发布失败怎么办？
A: 检查以下几点：
1. settings.xml 配置是否正确
2. GPG 密钥是否有效
3. 项目坐标是否已验证
4. 网络连接是否正常