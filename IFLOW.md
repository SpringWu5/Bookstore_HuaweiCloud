# 项目概述

这是一个校园二手商城项目，包含前后端完整的系统。后端采用Spring Boot多模块架构，前端使用Vue 3框架。项目旨在为校园用户提供二手物品交易、论坛交流、寻物启事等功能。

## 技术栈

### 后端技术栈
- **核心框架**: Spring Boot 2.7.3
- **构建工具**: Maven
- **持久层框架**: MyBatis-Plus
- **数据库**: MySQL 8.0
- **缓存**: Redis
- **认证授权**: JWT
- **文档工具**: Knife4j (Swagger)
- **文件存储**: 阿里云OSS
- **支付**: 微信支付
- **其他**: WebSocket, Druid连接池, PageHelper分页, Lombok, FastJSON, Apache POI

### 前端技术栈
- **框架**: Vue 3
- **构建工具**: Vite
- **UI组件库**: Element Plus
- **状态管理**: Pinia
- **HTTP客户端**: Axios
- **富文本编辑器**: Vue Quill
- **样式**: SASS

## 项目结构

```
campus-mall-repo/
├── backend/                    # 后端项目
│   ├── second-common/          # 公共模块
│   │   ├── constant/           # 常量定义
│   │   ├── context/            # 上下文
│   │   ├── enumeration/        # 枚举
│   │   ├── exception/          # 自定义异常
│   │   ├── json/               # JSON配置
│   │   ├── properties/         # 配置属性
│   │   ├── result/             # 统一结果封装
│   │   └── utils/              # 工具类
│   ├── second-pojo/            # POJO模块
│   │   ├── dto/                # 数据传输对象
│   │   ├── entity/             # 实体对象
│   │   └── vo/                 # 视图对象
│   └── second-server/          # 核心业务模块
│       ├── config/             # 配置类
│       ├── controller/         # 控制器层
│       ├── handler/            # 异常处理器
│       ├── interceptor/        # 拦截器
│       ├── mapper/             # 数据访问层
│       ├── service/            # 业务逻辑层
│       ├── task/               # 定时任务
│       └── websocket/          # WebSocket服务
├── frontend/                   # 前端项目
│   ├── src/
│   │   ├── api/                # API接口定义
│   │   ├── components/         # 公共组件
│   │   ├── router/             # 路由配置
│   │   ├── stores/             # Pinia状态管理
│   │   ├── utils/              # 工具函数
│   │   └── views/              # 页面视图
│   │       ├── admin/          # 管理员页面
│   │       └── user/           # 用户页面
├── docs/                       # 文档
│   └── campus_service.sql      # 数据库初始化脚本
```

## 数据库结构

数据库名为`campus_service`，包含以下主要表：

- **用户管理**:
  - `user`: 用户信息表（包含权限状态和账号状态）
  
- **分类管理**:
  - `category`: 物品分类表
  - `college`: 学院分类表
  - `dormitory`: 宿舍分类表
  - `forum`: 论坛分类表
  
- **内容管理**:
  - `goods`: 商品表（二手商品信息）
  - `seek`: 求购表（用户求购需求）
  - `posts`: 帖子表（论坛帖子）
  - `announcement`: 公告表
  
- **交易管理**:
  - `orders`: 订单表
  - `order_detail`: 订单明细表
  - `address_book`: 地址簿表
  
- **互动功能**:
  - `comment`: 评论表（支持商品、帖子、求购的评论）
  - `goods_detail`: 商品详细信息表（包含收藏、点赞、浏览等统计）
  
## 主要功能模块

### 后端功能
1. **用户管理**: 用户注册、登录、权限管理
2. **分类管理**: 商品分类、学院分类、宿舍分类、论坛分类管理
3. **商品管理**: 商品发布、编辑、上下架、搜索
4. **求购管理**: 用户发布求购需求
5. **论坛管理**: 论坛帖子发布、管理
6. **订单管理**: 订单创建、支付、配送、完成
7. **评论管理**: 评论发布、回复
8. **公告管理**: 系统公告发布
9. **地址管理**: 用户地址簿管理
10. **文件上传**: 通过阿里云OSS实现图片上传

### 前端功能
1. **用户界面**:
   - 登录/注册
   - 个人信息管理
   - 商品浏览与搜索
   - 商品发布与管理
   - 求购发布与管理
   - 订单管理（买方/卖方）
   - 评论互动
   - 地址管理
   - 收藏与点赞

2. **管理员界面**:
   - 用户管理
   - 商品管理
   - 求购管理
   - 论坛管理
   - 订单管理
   - 分类管理
   - 公告管理
   - 系统控制台

## 构建与运行

### 后端
1. 环境要求：JDK 8+, Maven 3.6+
2. 配置数据库连接信息（在`application-dev.yml`中）
3. 执行数据库脚本`docs/campus_service.sql`
4. 启动命令：
   ```bash
   cd backend
   mvn spring-boot:run -pl second-server
   ```

### 前端
1. 环境要求：Node.js 14+
2. 安装依赖：
   ```bash
   cd frontend
   npm install
   ```
3. 启动开发服务器：
   ```bash
   npm run dev
   ```
4. 前端运行在7000端口，通过代理转发/api请求到后端8080端口

## 开发约定

1. **编码规范**:
   - 后端使用UTF-8编码
   - 数据库表名和字段名使用下划线命名法
   - Java代码使用驼峰命名法

2. **数据库设计**:
   - 所有表都包含创建时间和更新时间字段
   - 使用逻辑删除字段`deleted`而非物理删除
   - 字段命名统一使用`create_time`, `update_time`, `create_user`, `update_user`

3. **API规范**:
   - 统一返回格式使用`Result`类封装
   - 使用JWT进行身份验证
   - 接口路径使用RESTful风格

4. **安全考虑**:
   - 密码使用MD5加密存储（虽然不推荐，但项目中使用）
   - 重要配置使用环境变量或配置文件管理
   - 上传文件进行类型和大小限制

## CI/CD配置

项目已配置GitHub Actions进行持续集成和部署，包含以下工作流：
- CI工作流：构建和测试
- PR测试工作流：带数据库服务的完整测试
- 部署工作流：标签发布时自动部署到生产环境
- Docker工作流：构建和推送Docker镜像