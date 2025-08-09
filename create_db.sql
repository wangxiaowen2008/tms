-- 创建数据库
CREATE DATABASE tms
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Chinese (Simplified)_China.UTF-8'
    LC_CTYPE = 'Chinese (Simplified)_China.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- 连接到新创建的数据库
\c tms

-- 创建扩展（如果需要）
CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; 