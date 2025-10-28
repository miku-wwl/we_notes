-- 创建学生表（对应Student实体）
CREATE TABLE IF NOT EXISTS t_student
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,

    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(255) NOT NULL UNIQUE,
    age       INTEGER,
    birth_day DATE
);