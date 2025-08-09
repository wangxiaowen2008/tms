# TMS系统

## 项目介绍
TMS系统是一个基于Spring Boot 3和MyBatis-Plus 3的后端权限管理系统，包含菜单管理、角色管理和用户管理功能。

## 技术栈
- Spring Boot 3.1.0
- MyBatis-Plus 3.5.3.1
- PostgreSQL
- Maven
- Java 17

## 功能特性
- 菜单管理：支持菜单的增删改查，可配置菜单按钮权限
- 角色管理：支持角色的增删改查，可分配菜单和按钮权限
- 用户管理：支持用户的增删改查，可分配多个角色

## 环境要求
- JDK 17+
- Maven 3.6+
- PostgreSQL 12+

## 快速开始

### 1. 数据库配置
- 创建PostgreSQL数据库：tms
- 执行`src/main/resources/db/migration/V1__create_permission_tables.sql`脚本创建表结构

### 2. 修改配置
- 修改`src/main/resources/application.yml`中的数据库连接信息

### 3. 编译打包
```bash
mvn clean package
```

### 4. 运行项目
```bash
java -jar target/tms-0.0.1-SNAPSHOT.jar
```

## API接口

### 菜单管理
- 查询菜单列表：GET /api/menu/list
- 新增菜单：POST /api/menu/add
- 更新菜单：POST /api/menu/update

### 角色管理
- 查询角色列表：GET /api/role/list
- 新增角色：POST /api/role/add
- 更新角色：POST /api/role/update

### 用户管理
- 查询用户列表：GET /api/user/list
- 新增用户：POST /api/user/add
- 更新用户：POST /api/user/update # tms
