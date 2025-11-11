-- =============================================
-- IM Demo Schema (MySQL)
-- Character set: utf8mb4 for emoji / multilingual
-- Engine: InnoDB for FK + transactions
-- Requires MySQL 8.0+ for CHECK constraints
-- =============================================

-- 安全重复执行: 如已存在则不创建，可在初始化前手动 DROP 需要重建的表。

-- 说明改动摘要:
-- 1. 原表名 groups 为保留关键字，改为 chat_groups
-- 2. conversations 增加严格 CHECK 保证单聊/群聊互斥字段模式，并单聊双方 user_a_id < user_b_id 防止重复
-- 3. conversations 增加唯一 uk_conversations_group_type 保证每群唯一会话
-- 4. 添加若干复合索引优化常用查询
-- 5. friendships 添加状态检索复合索引
-- 6. group_members 添加角色检索索引
-- 7. messages 添加 (conversation_id, created_at) 覆盖检索索引
-- 8. read_receipts 添加 message_id 索引
-- 9. 保留 last_message_id 不加外键(避免循环)，可后续通过触发器或应用逻辑维护

-- =====================
-- users: 用户表
-- =====================
CREATE TABLE IF NOT EXISTS users (
    id            BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    username      VARCHAR(50) NOT NULL,
    password      VARCHAR(100) NOT NULL,
    nickname      VARCHAR(50) NOT NULL,
    avatar_url    VARCHAR(255) NULL,
    phone         VARCHAR(20) NULL,
    email         VARCHAR(100) NULL,
    status        TINYINT NOT NULL DEFAULT 1 COMMENT '1=active,0=disabled',
    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_users_username (username),
    UNIQUE KEY uk_users_phone (phone),
    UNIQUE KEY uk_users_email (email),
    KEY idx_users_status (status),
    CONSTRAINT chk_users_status CHECK (status IN (0,1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用用户';

-- =====================
-- friendships: 好友关系 (单向申请+状态)
-- =====================
CREATE TABLE IF NOT EXISTS friendships (
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT UNSIGNED NOT NULL,
    friend_id   BIGINT UNSIGNED NOT NULL,
    status      TINYINT NOT NULL DEFAULT 0 COMMENT '0=pending,1=accepted,2=blocked,3=deleted',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_friendships_user     FOREIGN KEY (user_id)   REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_friendships_friend   FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_friendships_not_self CHECK (user_id <> friend_id),
    CONSTRAINT chk_friendships_status CHECK (status IN (0,1,2,3)),
    UNIQUE KEY uk_friendships_pair (user_id, friend_id),
    KEY idx_friendships_status (status),
    KEY idx_friendships_friend (friend_id),
    KEY idx_friendships_user_status (user_id, status),
    KEY idx_friendships_friend_status (friend_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='好友关系与状态';

-- =====================
-- chat_groups: 群组 (避免使用保留字 groups)
-- =====================
CREATE TABLE IF NOT EXISTS chat_groups (
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(500) NULL,
    owner_id    BIGINT UNSIGNED NOT NULL,
    avatar_url  VARCHAR(255) NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_chat_groups_name (name),
    KEY idx_chat_groups_owner (owner_id),
    CONSTRAINT fk_chat_groups_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天群组';

-- =====================
-- group_members: 群成员
-- =====================
CREATE TABLE IF NOT EXISTS group_members (
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    group_id    BIGINT UNSIGNED NOT NULL,
    user_id     BIGINT UNSIGNED NOT NULL,
    role        TINYINT NOT NULL DEFAULT 0 COMMENT '0=member,1=admin,2=owner',
    joined_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_group_members_group FOREIGN KEY (group_id) REFERENCES chat_groups(id) ON DELETE CASCADE,
    CONSTRAINT fk_group_members_user  FOREIGN KEY (user_id)  REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT chk_group_members_role CHECK (role IN (0,1,2)),
    UNIQUE KEY uk_group_members (group_id, user_id),
    KEY idx_group_members_user (user_id),
    KEY idx_group_members_group_role (group_id, role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群成员关系';

-- =====================
-- conversations: 会话聚合 (私聊/群聊统一)
-- =====================
CREATE TABLE IF NOT EXISTS conversations (
    id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    type            TINYINT NOT NULL COMMENT '0=single,1=group',
    user_a_id       BIGINT UNSIGNED NULL COMMENT '单聊参与者A',
    user_b_id       BIGINT UNSIGNED NULL COMMENT '单聊参与者B',
    group_id        BIGINT UNSIGNED NULL COMMENT '群聊ID',
    last_message_id BIGINT UNSIGNED NULL COMMENT '最近一条消息ID(无外键避免循环)',
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_conversations_user_a FOREIGN KEY (user_a_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_conversations_user_b FOREIGN KEY (user_b_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_conversations_group  FOREIGN KEY (group_id)  REFERENCES chat_groups(id) ON DELETE CASCADE,
    UNIQUE KEY uk_conversations_single (user_a_id, user_b_id, type),
    UNIQUE KEY uk_conversations_group_type (group_id, type),
    KEY idx_conversations_group (group_id),
    KEY idx_conversations_updated (updated_at),
    KEY idx_conversations_type (type),
    CONSTRAINT chk_conversations_structure CHECK (
        (type = 0 AND user_a_id IS NOT NULL AND user_b_id IS NOT NULL AND group_id IS NULL AND user_a_id < user_b_id)
        OR
        (type = 1 AND group_id IS NOT NULL AND user_a_id IS NULL AND user_b_id IS NULL)
    )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='私聊与群聊统一会话';

-- =====================
-- messages: 消息
-- =====================
CREATE TABLE IF NOT EXISTS messages (
    id              BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    conversation_id BIGINT UNSIGNED NOT NULL,
    sender_id       BIGINT UNSIGNED NOT NULL,
    receiver_id     BIGINT UNSIGNED NULL COMMENT '仅单聊使用',
    message_type    TINYINT NOT NULL DEFAULT 0 COMMENT '0=text,1=image,2=file,3=system',
    content         TEXT NOT NULL,
    delivered_at    DATETIME NULL,
    read_at         DATETIME NULL,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_messages_conversation FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
    CONSTRAINT fk_messages_sender       FOREIGN KEY (sender_id)      REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_messages_receiver     FOREIGN KEY (receiver_id)    REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT chk_messages_type CHECK (message_type IN (0,1,2,3)),
    KEY idx_messages_conversation (conversation_id),
    KEY idx_messages_sender (sender_id),
    KEY idx_messages_receiver (receiver_id),
    KEY idx_messages_created (created_at),
    KEY idx_messages_unread (receiver_id, read_at),
    KEY idx_messages_conv_created (conversation_id, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天消息';

-- =====================
-- read_receipts: 群聊消息阅读回执
-- =====================
CREATE TABLE IF NOT EXISTS read_receipts (
    id          BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    message_id  BIGINT UNSIGNED NOT NULL,
    user_id     BIGINT UNSIGNED NOT NULL,
    read_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_read_receipts_message FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE,
    CONSTRAINT fk_read_receipts_user    FOREIGN KEY (user_id)    REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uk_read_receipts (message_id, user_id),
    KEY idx_read_receipts_user (user_id),
    KEY idx_read_receipts_message (message_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='群聊消息阅读回执';

-- =====================
-- 后续扩展建议
-- 1. message_attachments(消息附件表): id, message_id, file_type, url, size, metadata
-- 2. device_sessions(设备在线会话): id, user_id, device_id, platform, token, last_active
-- 3. 消息撤回: 增加 messages.recalled_at 或单独表 message_events
-- 4. 增加审计: 操作日志、封禁记录
-- 5. 增加全文索引: 需配置 InnoDB FULLTEXT (MySQL 5.6+ 对 utf8mb4 支持改善)
-- =====================
