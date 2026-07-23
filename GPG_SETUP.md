# GPG 配置指南

发布到Maven中央仓库需要对构件进行GPG签名。本指南说明如何安装和配置GPG。

## 🔍 问题诊断

当前错误：
```
gpg: sending fd 0x000000000000023c to keyboxd: Input/output error
Exit code: 2
```

**原因：** GPG未安装或未正确配置。

## 📥 安装GPG

### Windows

#### 方式一：使用Chocolatey（推荐）
```powershell
# 安装Chocolatey（如果未安装）
Set-ExecutionPolicy Bypass -Scope Process -Force; 
[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; 
iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# 安装GPG
choco install gnupg -y

# 验证安装
gpg --version
```

#### 方式二：手动安装
1. 访问 https://www.gnupg.org/download/
2. 下载 Gpg4win (Windows版本)
3. 运行安装程序
4. 添加到PATH：`C:\Program Files (x86)\GnuPG\bin`

### macOS
```bash
# 使用Homebrew
brew install gnupg

# 验证安装
gpg --version
```

### Linux
```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install gnupg

# CentOS/RHEL
sudo yum install gnupg2

# 验证安装
gpg --version
```

## 🔑 生成GPG密钥

### 1. 生成密钥对

```bash
gpg --full-generate-key
```

**交互式配置：**
```
请选择要使用的密钥类型：
   (1) RSA 和 RSA （默认）
   (2) DSA 和 Elgamal
   (3) DSA （仅用于签名）
   (4) RSA （仅用于签名）
   (14) 卡中现有的密钥
您的选择？ 1  # 选择1

RSA 密钥的长度应在 1024 位到 4096 位之间。
您想要多大的密钥？ 4096  # 输入4096

请设定这个密钥的有效期限。
   0 = 密钥永不过期
      <n>  = 密钥在 n 天后过期
      <n>w = 密钥在 n 周后过期
      <n>m = 密钥在 n 月后过期
      <n>y = 密钥在 n 年后过期
密钥的有效期限是？ 0  # 输入0（永不过期）
密钥永不过期
这些内容正确吗？ y  # 输入y

GnuPG 需要构建用户 ID 以标识您的密钥。

真实姓名： He Zhuowei  # 输入姓名
电子邮件地址： hezw86@qq.com  # 输入邮箱
注释： Maven Central  # 可选注释
您选择了这个用户 ID：
    "He Zhuowei (Maven Central) <hezw86@qq.com>"
更改姓名 (N)、注释 (C)、电子邮件 (E) 还是确定 (O)/退出 (Q)？ O  # 输入O

我们需要生成大量的随机字节。此时您应该多做些琐事
（比如敲打键盘、移动鼠标、读写磁盘），这会让操作系统
有机会积累更多的熵，从而产生更好的随机字节。

请输入密码以保护您的新密钥：  # 输入密码（重要！记住这个密码）
请再次输入密码：  # 再次输入密码
```

### 2. 查看生成的密钥

```bash
gpg --list-keys
```

输出示例：
```
pub   rsa4096 2024-01-01 [SC]
      ABC12345DEF67890GHI12345JKL67890MNO12345P
uid           [ 绝对 ] He Zhuowei (Maven Central) <hezw86@qq.com>
sub   rsa4096 2024-01-01 [E]
```

**记录密钥ID：** `ABC12345DEF67890GHI12345JKL67890MNO12345P`（用于后续操作）

### 3. 发布公钥到密钥服务器

```bash
# 发布到多个密钥服务器（确保可用性）
gpg --keyserver keyserver.ubuntu.com --send-keys ABC12345DEF67890GHI12345JKL67890MNO12345P
gpg --keyserver pgp.mit.edu --send-keys ABC12345DEF67890GHI12345JKL67890MNO12345P
gpg --keyserver keys.openpgp.org --send-keys ABC12345DEF67890GHI12345JKL67890MNO12345P

# 验证公钥已发布
gpg --keyserver keyserver.ubuntu.com --recv-keys ABC12345DEF67890GHI12345JKL67890MNO12345P
```

## ⚙️ 配置Maven

### 1. 编辑 Maven Settings

编辑 `~/.m2/settings.xml`（Windows: `C:\Users\你的用户名\.m2\settings.xml`）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 
          http://maven.apache.org/xsd/settings-1.0.0.xsd">
    
    <servers>
        <!-- Central Portal 令牌 -->
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
                <!-- Windows: GPG可执行文件路径 -->
                <gpg.executable>C:\Program Files (x86)\GnuPG\bin\gpg.exe</gpg.executable>
                <!-- 或如果GPG在PATH中 -->
                <!-- <gpg.executable>gpg</gpg.executable> -->
                
                <!-- macOS/Linux -->
                <!-- <gpg.executable>gpg</gpg.executable> -->
                
                <!-- GPG密钥密码 -->
                <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
                
                <!-- 可选：指定密钥ID -->
                <!-- <gpg.keyname>ABC12345DEF67890GHI12345JKL67890MNO12345P</gpg.keyname> -->
            </properties>
        </profile>
    </profiles>
    
    <activeProfiles>
        <activeProfile>gpg</activeProfile>
    </activeProfiles>
</settings>
```

### 2. 测试GPG签名

```bash
# 创建测试文件
echo "test" > test.txt

# 测试签名
gpg --clearsign test.txt

# 如果成功，会生成 test.txt.asc 文件
# 删除测试文件
del test.txt test.txt.asc
```

## 🧪 测试Maven GPG配置

```bash
# 在项目目录运行
mvn clean verify -DskipTests
```

如果成功，说明GPG配置正确。

## 🔧 常见问题

### Q: gpg命令找不到？
A: 
1. 确认GPG已安装
2. 确认GPG在PATH中，或在settings.xml中指定完整路径
3. Windows: 重启Powerhell/CMD使PATH生效

### Q: 密码错误？
A: 
1. 确认settings.xml中的密码与生成密钥时的密码一致
2. 尝试手动签名测试：`echo "test" | gpg --clearsign`

### Q: 密钥未找到？
A: 
1. 运行 `gpg --list-keys` 确认密钥存在
2. 在settings.xml中添加 `<gpg.keyname>密钥ID</gpg.keyname>`

### Q: Windows下GPG路径问题？
A: 
在settings.xml中使用完整路径：
```xml
<gpg.executable>C:\Program Files (x86)\GnuPG\bin\gpg.exe</gpg.executable>
```

### Q: GPG 2.x vs GPG 1.x？
A: 
推荐使用GPG 2.x（gnupg2），现代系统默认安装此版本。

## 📋 完整检查清单

发布前确认：

- [ ] GPG已安装：`gpg --version`
- [ ] 密钥已生成：`gpg --list-keys`
- [ ] 公钥已发布到密钥服务器
- [ ] settings.xml已配置GPG密码
- [ ] settings.xml已配置Central Portal令牌
- [ ] 测试签名成功：`echo "test" | gpg --clearsign`
- [ ] Maven验证成功：`mvn clean verify -DskipTests`

## 🔗 相关链接

- [GPG官方网站](https://www.gnupg.org/)
- [Gpg4win下载](https://www.gpg4win.org/)
- [Maven GPG插件文档](https://maven.apache.org/plugins/maven-gpg-plugin/)
- [密钥服务器列表](https://wiki.gnupg.org/Keyserver)