-- 菜单表
CREATE TABLE sys_menu (
    id BIGSERIAL PRIMARY KEY,
    parent_id BIGINT,
    menu_name VARCHAR(50) NOT NULL,
    menu_level INT NOT NULL,
    menu_order INT NOT NULL,
    menu_url VARCHAR(200),
    menu_icon VARCHAR(50),
    menu_status SMALLINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 菜单按钮表
CREATE TABLE sys_menu_button (
    id BIGSERIAL PRIMARY KEY,
    menu_id BIGINT NOT NULL,
    button_name VARCHAR(50) NOT NULL,
    button_code VARCHAR(50) NOT NULL,
    button_status SMALLINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (menu_id) REFERENCES sys_menu(id)
);

-- 角色表
CREATE TABLE sys_role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_status SMALLINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 角色菜单关联表
CREATE TABLE sys_role_menu (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES sys_role(id),
    FOREIGN KEY (menu_id) REFERENCES sys_menu(id)
);

-- 角色按钮关联表
CREATE TABLE sys_role_button (
    id BIGSERIAL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    button_id BIGINT NOT NULL,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES sys_role(id),
    FOREIGN KEY (button_id) REFERENCES sys_menu_button(id)
);

-- 用户表
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    um_code VARCHAR(50) NOT NULL UNIQUE,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    department VARCHAR(100),
    user_status SMALLINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(50),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户角色关联表
CREATE TABLE sys_user_role (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_by VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES sys_user(id),
    FOREIGN KEY (role_id) REFERENCES sys_role(id)
);

-- 创建索引
CREATE INDEX idx_menu_parent_id ON sys_menu(parent_id);
CREATE INDEX idx_menu_button_menu_id ON sys_menu_button(menu_id);
CREATE INDEX idx_role_menu_role_id ON sys_role_menu(role_id);
CREATE INDEX idx_role_menu_menu_id ON sys_role_menu(menu_id);
CREATE INDEX idx_role_button_role_id ON sys_role_button(role_id);
CREATE INDEX idx_role_button_button_id ON sys_role_button(button_id);
CREATE INDEX idx_user_role_user_id ON sys_user_role(user_id);
CREATE INDEX idx_user_role_role_id ON sys_user_role(role_id); 