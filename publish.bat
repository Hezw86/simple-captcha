@echo off
echo ========================================
echo Simple Captcha 发布脚本
echo ========================================
echo.

REM 检查GPG是否安装
gpg --version >nul 2>&1
if errorlevel 1 (
    echo [错误] GPG未安装！
    echo.
    echo 请先安装GPG：
    echo   Windows: choco install gnupg
    echo   macOS:   brew install gnupg
    echo   Linux:   sudo apt-get install gnupg
    echo.
    echo 详细说明请查看 GPG_SETUP.md
    pause
    exit /b 1
)

echo [1/4] 清理项目...
call mvn clean

echo.
echo [2/4] 运行测试...
call mvn test
if errorlevel 1 (
    echo [错误] 测试失败！
    pause
    exit /b 1
)

echo.
echo [3/4] 发布到Maven中央仓库...
echo 注意：需要正确配置GPG和Central Portal令牌
echo.
call mvn deploy
if errorlevel 1 (
    echo.
    echo [错误] 发布失败！
    echo.
    echo 请检查：
    echo   1. GPG是否正确配置（查看 GPG_SETUP.md）
    echo   2. Central Portal令牌是否有效
    echo   3. Maven settings.xml是否正确配置
    pause
    exit /b 1
)

echo.
echo ========================================
echo [成功] 发布完成！
echo ========================================
echo.
echo 等待10-30分钟后可在以下地址查看：
echo https://central.sonatype.com/artifact/io.github.hezw86/simple-captcha
echo.
pause