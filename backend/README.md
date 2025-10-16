# 校园二手商城后端

## 1. 项目简介

这是一个校园二手商城的后端项目，基于 Spring Boot 和 Maven 构建。项目采用了多模块化的结构，旨在实现高内聚、低耦合的开发模式，使得代码结构更加清晰，易于维护和扩展。

## 2. 技术栈

- **核心框架**: Spring Boot
- **构建工具**: Maven
- **持久层框架**: MyBatis
- **数据库**: MySQL (推断)
- **其他**: JWT, WebSocket 等

## 3. 项目结构

项目采用 Maven 多模块结构，主要分为以下几个模块：

```
campus-service-dev
├── second-common         # 公共工具模块
├── second-pojo           # POJO 模块（DTO, Entity, VO）
├── second-server         # 核心业务模块
├── pom.xml               # 父项目 POM 文件
└── README.md             # 项目说明文件
```

### 3.1 `second-common` 模块

此模块包含整个项目通用的工具类、常量、自定义异常、枚举等，为其他模块提供公共支持。

- `com.sky.constant`: 定义项目中使用的静态常量（如 JWT 字段名、消息提示等）。
- `com.sky.context`: 全局上下文，如用于在线程内获取和传递当前用户 ID 的 `BaseContext`。
- `com.sky.enumeration`: 定义业务操作相关的枚举类型（如操作类型 `OperationType`）。
- `com.sky.exception`: 自定义业务异常类，用于处理特定的业务逻辑错误。
- `com.sky.json`: JSON 序列化相关配置，如自定义 Jackson 的对象映射器。
- `com.sky.properties`: 用于读取和映射 `application.yml` 中自定义属性的配置类。
- `com.sky.result`: 统一的后端返回结果封装类 `Result`，规范了接口的数据输出格式。
- `com.sky.utils`: 常用的工具类，如日期处理、字符串操作、JWT 生成与校验等。

### 3.2 `second-pojo` 模块

此模块主要存放数据模型对象，作为数据在各层之间传递的载体。

- `com.sky.dto`: 数据传输对象 (Data Transfer Object)，用于接收前端传递的参数，封装了新增或修改操作所需的数据。
- `com.sky.entity`: 实体对象 (Entity)，与数据库中的表结构一一对应，是数据持久化的基础。
- `com.sky.vo`: 视图对象 (View Object)，用于向前端返回特定格式的数据，通常是业务处理后需要展示给用户的信息。

### 3.3 `second-server` 模块

这是项目的核心业务模块，包含了接口、业务逻辑、数据访问等所有核心功能。

- `src/main/java/com/sky/`:
    - `config`: Spring Boot 的配置类，如 Web MVC 配置、MyBatis 配置、WebSocket 配置等。
    - `controller`: 控制器层 (Controller)，负责接收前端 HTTP 请求，对参数进行校验，调用 Service 层处理业务，并最终将结果返回给前端。
    - `handler`: 全局处理器，如 `GlobalExceptionHandler` 用于捕获和处理全局异常，并返回统一的错误信息。
    - `interceptor`: 拦截器 (Interceptor)，用于处理一些通用的横切逻辑，如 `JwtTokenAdminInterceptor` 和 `JwtTokenUserInterceptor` 用于进行用户登录状态的校验。
    - `mapper`: 数据访问层接口 (DAO)，基于 MyBatis，定义了与数据库进行交互的 CRUD 操作。
    - `service`: 业务逻辑层 (Service)，包含了核心的业务处理逻辑。Controller 层会调用这里的接口来完成具体的业务功能。
    - `task`: 定时任务类，用于处理需要周期性执行的后台任务。
    - `websocket`: WebSocket 服务端点，用于实现与客户端的实时双向通信。

- `src/main/resources/`:
    - `application.yml`: 主配置文件。
    - `application-dev.yml`: 开发环境配置文件，包含数据库连接、服务器端口等配置。
    - `mapper/`: MyBatis 的 XML 映射文件，存放了具体的 SQL 语句。

## 4. 如何运行

1.  配置 `application-dev.yml` 中的数据库连接信息。
2.  执行 `second-server` 模块下的 `com.sky.SecondApplication` 启动类。
3.  项目默认运行在 `8080` 端口。

## 5. CI/CD 配置

本项目已配置 GitHub Actions 进行持续集成和部署，包含以下工作流：

### 工作流

- **CI 工作流**: 在每次推送到主分支或拉取请求时运行，包括构建和测试
- **PR 测试工作流**: 为拉取请求运行完整的测试套件，包括数据库服务
- **部署工作流**: 在创建标签时自动部署到生产环境
- **Docker 工作流**: 构建并推送 Docker 镜像到 DockerHub

### 配置密钥

在 GitHub 仓库设置中需要配置以下密钥：

- `HOST` - 生产服务器主机名/IP
- `USERNAME` - SSH 用户名
- `KEY` - SSH 私钥
- `SLACK_WEBHOOK_URL` - 用于通知的 Slack webhook（可选）
- `DOCKERHUB_USERNAME` - DockerHub 用户名
- `DOCKERHUB_TOKEN` - DockerHub 访问令牌

### 部署

创建格式为 `v*` 的标签（例如 `v1.0.0`）以触发部署到生产环境。

### 详细文档

参见 `CICD.md` 文件获取更详细的 CI/CD 配置和使用说明。
