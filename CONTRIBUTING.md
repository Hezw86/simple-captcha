# 贡献指南

感谢您考虑为 Simple Captcha 项目做出贡献！

## 行为准则

本项目采用贡献者公约作为行为准则。请阅读 [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md) 了解详情。

## 如何贡献

### 报告Bug

如果您发现了bug，请创建一个Issue，并包含以下信息：

- Bug的详细描述
- 复现步骤
- 期望行为
- 实际行为
- 环境信息（Java版本、操作系统等）
- 如果可能，提供代码示例或截图

### 提出新功能

如果您想提出新功能，请创建一个Issue，并包含：

- 功能的详细描述
- 使用场景
- 可能的实现方案

### 提交代码

1. Fork本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建一个Pull Request

### 代码规范

- 遵循Java编码规范
- 为所有公共API添加JavaDoc注释
- 为新功能编写单元测试
- 确保所有测试通过：`mvn clean test`
- 确保代码覆盖率不低于80%

### 提交信息规范

使用清晰、有意义的提交信息：

- `feat: 添加新功能`
- `fix: 修复bug`
- `docs: 更新文档`
- `test: 添加测试`
- `refactor: 重构代码`
- `style: 代码格式调整`
- `chore: 构建/工具变动`

## 开发环境设置

### 前置要求

- JDK 8 或更高版本
- Maven 3.6 或更高版本
- Git

### 构建项目

```bash
git clone https://github.com/hezw86/simple-captcha.git
cd simple-captcha
mvn clean install
```

### 运行测试

```bash
mvn test
```

### 生成测试覆盖率报告

```bash
mvn jacoco:report
```

报告将生成在 `target/site/jacoco/index.html`

## 发布流程

（仅限维护者）

1. 更新版本号和CHANGELOG.md
2. 执行 `mvn clean deploy -P release`
3. 在GitHub创建Release

## 许可证

通过贡献代码，您同意您的代码将在MIT许可证下授权。
