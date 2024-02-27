### 前后端分离项目library

##### 文件结构

```markdown
.
├── backend
│   ├── Dockerfile
│   ├── pom.xml
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── abc
│   │   │   │           └── library
│   │   │   │               ├── commom
│   │   │   │               │   ├── MybatisPlusConfig.java
│   │   │   │               │   ├── Result.java
│   │   │   │               │   └── WebAppConfigure.java
│   │   │   │               ├── controller
│   │   │   │               │   ├── BookController.java
│   │   │   │               │   ├── BookWithUserController.java
│   │   │   │               │   ├── DashboardController.java
│   │   │   │               │   ├── LendRecordController.java
│   │   │   │               │   └── UserController.java
│   │   │   │               ├── entity
│   │   │   │               │   ├── Book.java
│   │   │   │               │   ├── BookWithUser.java
│   │   │   │               │   ├── LendRecord.java
│   │   │   │               │   └── User.java
│   │   │   │               ├── LibraryApplication.java
│   │   │   │               ├── LoginUser.java
│   │   │   │               ├── mapper
│   │   │   │               │   ├── BookMapper.java
│   │   │   │               │   ├── BookWithUserMapper.java
│   │   │   │               │   ├── LendRecordMapper.java
│   │   │   │               │   └── UserMapper.java
│   │   │   │               └── utils
│   │   │   │                   └── TokenUtils.java
│   │   │   └── resources
│   │   │       ├── application.yml
│   │   │       ├── com
│   │   │       │   └── library
│   │   │       │       └── mapper
│   │   │       ├── static
│   │   │       └── templates
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── abc
│   │                   └── library
│   │                       └── LibraryApplicationTests.java
│   └── target
├── bookdb
│   ├── Dockerfile
│   ├── init.sh
│   └── springboot_vue.sql
├── front
│   ├── babel.config.js
│   ├── Dockerfile
│   ├── package.json
│   ├── public
│   │   ├── favicon.ico
│   │   └── index.html
│   ├── src
│   │   ├── App.vue
│   │   ├── assets
│   │   │   ├── css
│   │   │   │   ├── global.css
│   │   │   │   └── style.css
│   │   │   ├── icon
│   │   │   │   ├── iconfont.css
│   │   │   │   ├── iconfont.js
│   │   │   │   └── login.png
│   │   │   └── logo.png
│   │   ├── components
│   │   │   ├── Aside.vue
│   │   │   ├── Header.vue
│   │   │   └── Validate.vue
│   │   ├── img
│   │   │   └── bg2.svg
│   │   ├── layout
│   │   │   └── Layout.vue
│   │   ├── main.js
│   │   ├── router
│   │   │   └── index.js
│   │   ├── store
│   │   │   └── index.js
│   │   ├── utils
│   │   │   └── request.js
│   │   └── views
│   │       ├── Book.vue
│   │       ├── BookWithUser.vue
│   │       ├── Dashboard.vue
│   │       ├── LendRecord.vue
│   │       ├── Login.vue
│   │       ├── Password.vue
│   │       ├── Person.vue
│   │       ├── Register.vue
│   │       └── User.vue
│   └── vue.config.js
├── Jenkinsfile
└── yaml
    ├── backend.yaml
    ├── bookdb.yaml
    └── front.yaml

39 directories, 61 files
```

##### 文件夹说明

**yaml**： 存放`CI/CD`部署文件

**Jenkinfile**：`jenkins Pipeline`流水线文件

**front**：前端文件，包括源文件和`Dockerfile`文件，前端`API`修改文件`front/src/utils/request.js`，`baseURL`改为自己的

**bookdb**：数据库文件，初始化数据库文件`init.sh`

**backend**：后端文件，`jdbc`连接信息修改`backend/src/main/resources/application.yml`

##### 操作使用

- 常规部署

  使用常规方式即可，导入数据库，修改相关信息，运行即可

- CI/CD

  将`Jenkinsfile`中的信息修改为自己的即可

##### 运行效果