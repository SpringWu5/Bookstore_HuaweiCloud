# 项目介绍

本项目是一个基于 Vue 3 的校园二手交易平台前端实现。它利用 Vite 作为构建工具，Element Plus 作为 UI 组件库，实现了管理员后台和用户前台两大模块。

## 技术栈

-   **框架**: [Vue 3](https://cn.vuejs.org/)
-   **构建工具**: [Vite](https://cn.vitejs.dev/)
-   **路由**: [Vue Router](https://router.vuejs.org/zh/)
-   **状态管理**: [Pinia](https://pinia.vuejs.org/zh/)
-   **UI 库**: [Element Plus](https://element-plus.org/zh-CN/)
-   **HTTP 请求**: [Axios](https://axios-http.com/)
-   **CSS 预处理器**: [Sass](https://sass-lang.com/)

## 项目结构

```
.
├── public/              # 静态资源，不会被 Vite 处理
├── src/                 # 项目核心源代码
│   ├── api/             # API 请求模块，按业务划分
│   │   ├── goods.js     # 商品相关接口
│   │   ├── orders.js    # 订单相关接口
│   │   └── user.js      # 用户相关接口
│   ├── assets/          # 经 Vite 处理的静态资源 (图片, 全局样式)
│   │   └── main.scss    # 全局 SCSS 样式
│   ├── components/      # 可复用的全局组件
│   │   └── AiAssistant.vue # AI 助手组件
│   ├── router/          # 路由配置
│   │   └── index.js     # 路由定义文件
│   ├── stores/          # Pinia 状态管理
│   │   ├── token.js     # Token 状态
│   │   └── userInfo.js  # 用户信息状态
│   ├── utils/           # 工具函数
│   │   └── request.js   # Axios 实例封装和拦截器
│   ├── views/           # 页面级组件
│   │   ├── admin/       # 管理员后台页面
│   │   │   ├── GoodsManage.vue
│   │   │   └── OrdersManage.vue
│   │   ├── user/        # 用户前台页面
│   │   │   ├── Popularity.vue
│   │   │   ├── Shop.vue
│   │   │   └── Person.vue
│   │   ├── Admin.vue    # 管理员主布局
│   │   ├── User.vue     # 用户主布局
│   │   └── Login.vue    # 登录页面
│   ├── App.vue          # 根组件
│   └── main.js          # 应用入口文件
├── .gitignore           # Git 忽略配置
├── index.html           # 应用主 HTML 文件
├── package.json         # 项目依赖和脚本配置
└── vite.config.js       # Vite 配置文件 (含代理)
```

## 主要功能模块

1.  **用户端**
    *   用户登录与注册 ([`src/views/Login.vue`](src/views/Login.vue))
    *   商品广场与详情 ([`src/views/user/Popularity.vue`](src/views/user/Popularity.vue))
    *   帖子交流 ([`src/views/user/Shop.vue`](src/views/user/Shop.vue))
    *   求购信息 ([`src/views/user/Seek.vue`](src/views/user/Seek.vue))
    *   个人中心，包括我的商品、我的求购、我的收藏、地址管理等 ([`src/views/user/Person.vue`](src/views/user/Person.vue))
    *   下单与支付流程 ([`src/views/user/Collection.vue`](src/views/user/Collection.vue))
    *   AI 智能助手 ([`src/components/AiAssistant.vue`](src/components/AiAssistant.vue))

2.  **管理端**
    *   控制台 ([`src/views/admin/ConsoleManage.vue`](src/views/admin/ConsoleManage.vue))
    *   用户管理 ([`src/views/admin/MemberManage.vue`](src/views/admin/MemberManage.vue))
    *   商品、分类、订单、帖子、求购等信息的增删改查管理。
    *   个人资料修改 ([`src/views/admin/AdminInfo.vue`](src/views/admin/AdminInfo.vue))

## 启动项目

1.  安装依赖:
    ````sh
    npm install
    ````

2.  启动开发服务器:
    ````sh
    npm run dev
    ````

3.  编译打包:
    ````sh
    npm run build
    ````