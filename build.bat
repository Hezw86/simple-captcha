@echo off
echo ========================================
echo 快速测试脚本（跳过GPG签名）
echo ========================================
echo.

echo [1/3] 清理并编译...
call mvn clean compile -DskipTests
if errorlevel 1 (
    echo [错误] 编译失败！
    pause
    exit /b 1
)

echo.
echo [2/3] 运行测试...
call mvn test
if errorlevel 1 (
    echo [错误] 测试失败！
    pause
    exit /b 1
)

echo.
echo [3/3] 打包（不签名）...
call mvn package -DskipTests -Dgpg.skip=true
if errorlevel 1 (
    echo [错误] 打包失败！
    pause
    exit /b 1
)

echo.
echo ========================================
echo [成功] 项目构建成功！
echo ========================================
echo.
echo 生成的文件：
dir /b target\*.jar
echo.
echo 提示：发布到中央仓库需要GPG签名
echo 请查看 GPG_SETUP.md 配置GPG
echo.
pause