# IM-Demo：DDD 架构实践项目

> 🚀 一个基于领域驱动设计 (DDD) 架构的即时通讯 (IM) 系统 Demo，适合学习和理解 DDD 分层架构的初次实践者。

## 📖 项目简介

本项目是一个即时通讯系统的后端服务，采用 **DDD（领域驱动设计）** 架构风格实现。项目涵盖了用户管理、好友关系、群组管理、消息收发等核心 IM 功能，是学习 DDD 架构设计的良好实践案例。

### 技术栈

| 技术        | 版本      | 说明                    |
| ----------- | --------- | ----------------------- |
| Spring Boot | 3.5.7     | 应用框架                |
| Java        | 25        | 编程语言                |
| MyBatis-Plus | 3.5.7    | ORM 框架                |
| MySQL       | 8.0+      | 数据库                  |
| Spring Security | -     | 安全认证框架            |
| JWT         | 0.13.0    | 令牌认证                |
| WebSocket   | -         | 实时通信                |
| SpringDoc   | 2.5.0     | API 文档 (OpenAPI 3)    |

---

## 🏗️ DDD 架构设计

本项目严格遵循 DDD 四层架构，实现了清晰的职责分离：

```
┌─────────────────────────────────────────────────────────────┐
│                    Interfaces Layer                         │
│  (接口层：HTTP Controllers、WebSocket Handlers)              │
├─────────────────────────────────────────────────────────────┤
│                    Application Layer                         │
│  (应用层：Application Services、Commands、Queries)           │
├─────────────────────────────────────────────────────────────┤
│                      Domain Layer                            │
│  (领域层：Entities、Value Objects、Domain Services、Repos)   │
├─────────────────────────────────────────────────────────────┤
│                   Infrastructure Layer                       │
│  (基础设施层：Repository实现、数据库映射、外部服务集成)        │
└─────────────────────────────────────────────────────────────┘
```

### 项目结构

```
src/main/java/com/it/imdemo/
├── ImDemoApplication.java          # 应用入口
├── interfaces/                     # 接口层
│   ├── http/                       # HTTP 接口 (REST API)
│   │   ├── AuthController.java     # 认证控制器
│   │   └── HelloController.java    # 测试控制器
│   └── websocket/                  # WebSocket 接口
│       ├── MyWebSocketHandler.java # WebSocket 处理器
│       └── WebSocketConfig.java    # WebSocket 配置
│
├── application/                    # 应用层
│   ├── user/                       # 用户应用服务
│   │   ├── UserApplicationService.java
│   │   ├── UserAssembler.java      # DTO 转换器
│   │   ├── UserRegisterCmd.java    # 注册命令 (CQRS)
│   │   ├── UserLoginQry.java       # 登录查询 (CQRS)
│   │   └── UserLoginResp.java      # 登录响应
│   ├── message/                    # 消息应用服务
│   │   ├── MessageApplicationService.java
│   │   ├── SendMessageCmd.java     # 发送消息命令
│   │   └── GroupMessageSentEventListener.java
│   ├── group/                      # 群组应用服务
│   └── friendship/                 # 好友关系应用服务
│
├── domain/                         # 领域层 ⭐核心层
│   ├── user/                       # 用户领域
│   │   ├── model/
│   │   │   ├── User.java           # 用户聚合根
│   │   │   ├── ContactInformation.java  # 值对象
│   │   │   └── OnlineStatus.java   # 值对象/枚举
│   │   ├── UserRepository.java     # 仓储接口
│   │   └── UserContext.java        # 用户上下文
│   ├── message/                    # 消息领域
│   │   ├── model/
│   │   │   ├── Message.java        # 消息实体
│   │   │   ├── Conversation.java   # 会话聚合根
│   │   │   └── ReadReceipt.java    # 已读回执
│   │   ├── event/
│   │   │   └── GroupMessageSentEvent.java  # 领域事件
│   │   ├── MessageRepository.java
│   │   ├── ConversationRepository.java
│   │   ├── ReadReceiptRepository.java
│   │   └── WebSocketGateway.java   # 网关接口
│   ├── group/                      # 群组领域
│   │   ├── model/
│   │   │   ├── ChatGroup.java      # 群组聚合根
│   │   │   └── GroupMember.java    # 群成员实体
│   │   ├── GroupDomainService.java # 领域服务
│   │   ├── GroupRepository.java
│   │   └── GroupMemberRepository.java
│   └── friendship/                 # 好友关系领域
│       ├── model/
│       │   └── Friendship.java     # 好友关系实体
│       └── FriendshipRepository.java
│
├── infrastructure/                 # 基础设施层
│   ├── persistence/                # 持久化实现
│   │   ├── entity/                 # 数据库实体 (PO)
│   │   │   ├── UserEntity.java
│   │   │   ├── MessageEntity.java
│   │   │   └── ...
│   │   ├── mapper/                 # MyBatis Mapper
│   │   │   ├── UserMapper.java
│   │   │   ├── MessageMapper.java
│   │   │   └── ...
│   │   ├── repository/             # 仓储实现
│   │   │   ├── UserRepositoryImpl.java
│   │   │   ├── MessageRepositoryImpl.java
│   │   │   └── ...
│   │   └── convertor/              # 领域对象与PO转换器
│   │       ├── UserConvertor.java
│   │       └── ...
│   ├── security/                   # 安全相关
│   │   └── TokenService.java
│   ├── gateway/                    # 外部网关实现
│   ├── config/                     # 配置类
│   └── util/                       # 工具类
│       └── JwtUtil.java
│
└── commons/                        # 通用模块
    └── exception/                  # 异常定义
        ├── BizException.java
        ├── BizErrorCode.java
        └── AuthenticationException.java
```

---

## 🎯 DDD 核心概念实践

### 1. 领域层 (Domain Layer) - 业务核心

领域层是整个系统的核心，包含了所有业务逻辑，**与任何技术框架无关**。

#### 聚合根 (Aggregate Root)

`User` 是用户领域的聚合根，封装了用户相关的所有业务规则：

```java
@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String passwordHash;
    private String nickname;
    private ContactInformation contactInformation;  // 值对象
    private OnlineStatus onlineStatus;              // 值对象
    
    // ✅ 工厂方法：封装创建逻辑
    public static User create(String username, String password, ...) {
        return User.builder()
                .username(username)
                .passwordHash(password)
                .status(1)
                .onlineStatus(OnlineStatus.OFFLINE)
                .build();
    }
    
    // ✅ 领域行为：业务规则内聚在实体中
    public boolean validateLogin(String password) {
        return status.equals(1) && this.passwordHash.equals(password);
    }
    
    public void changePassword(String newPassword) {
        this.assertPasswordNotEmpty(newPassword);
        this.assertPasswordNotSame(passwordHash, newPassword);
        this.setPasswordHash(this.encryptedValue(newPassword));
    }
    
    public void makeOnline() {
        this.onlineStatus = preferredOnlineStatus;
        this.lastOnlineAt = new Date();
    }
}
```

#### 值对象 (Value Object)

`ContactInformation` 和 `OnlineStatus` 是典型的值对象，无唯一标识，通过属性值判等：

```java
@Data
@Builder
public class ContactInformation {
    private String phone;
    private String email;
}
```

#### 领域服务 (Domain Service)

当业务逻辑涉及**多个聚合**时，使用领域服务：

```java
@Service
public class GroupDomainService {
    
    @Resource
    private GroupMemberRepository groupMemberRepository;
    
    // 跨聚合的业务规则校验
    public void assertCanAddMember(ChatGroup chatGroup, Long operatorId, Long userToAddId) {
        if (operatorId.equals(userToAddId)) {
            throw new IllegalArgumentException("不能添加自己到群组");
        }
        if (groupMemberRepository.exists(chatGroup.getId(), operatorId)) {
            throw new IllegalArgumentException("操作用户不是群成员");
        }
        chatGroup.assertNotFull();
    }
}
```

#### 仓储接口 (Repository Interface)

仓储接口定义在领域层，**不依赖任何具体实现**：

```java
public interface UserRepository {
    void save(User user);
    Optional<User> getByUsername(String username);
    Optional<User> getById(Long userId);
}
```

#### 领域事件 (Domain Event)

用于解耦领域间的通信：

```java
public class GroupMessageSentEvent extends ApplicationEvent {
    private final Long senderId;
    private final Long groupId;
    private final Long messageId;
}
```

---

### 2. 应用层 (Application Layer) - 用例编排

应用层负责**编排领域对象**，实现具体的用例，不包含业务规则。

```java
@Service
public class UserApplicationService {
    
    @Resource
    private UserRepository userRepository;
    
    // 用例：用户注册
    public void register(UserRegisterCmd cmd) {
        User user = User.create(cmd.getUsername(), cmd.getPassword(), ...);
        userRepository.save(user);
    }
    
    // 用例：修改密码
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new BizException(BizErrorCode.USER_NOT_FOUND));
        user.changePassword(newPassword);  // 调用领域行为
        userRepository.save(user);
    }
}
```

#### CQRS 模式

项目采用了简化版的 CQRS (命令查询职责分离)：

- **Command (命令)**：`UserRegisterCmd`、`SendMessageCmd` - 用于写操作
- **Query (查询)**：`UserLoginQry` - 用于读操作
- **Response (响应)**：`UserLoginResp` - 返回数据

---

### 3. 基础设施层 (Infrastructure Layer) - 技术实现

基础设施层实现领域层定义的接口，处理所有技术细节。

#### 仓储实现

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @Resource
    private UserMapper userMapper;
    
    @Override
    public void save(User user) {
        UserEntity entity = UserConvertor.toEntity(user);  // 领域模型 -> PO
        userMapper.insertOrUpdate(entity);
    }
    
    @Override
    public Optional<User> getById(Long userId) {
        UserEntity entity = new LambdaQueryChainWrapper<>(userMapper)
                .eq(UserEntity::getId, userId)
                .one();
        return Optional.ofNullable(entity)
                .map(UserConvertor::toDomain);  // PO -> 领域模型
    }
}
```

#### 模型转换器 (Convertor)

在领域模型和持久化模型之间转换：

```
Domain Model (User)  <---->  Convertor  <---->  Entity (UserEntity)
     领域层                                          基础设施层
```

---

### 4. 接口层 (Interfaces Layer) - 外部交互

接口层处理外部请求，将其转化为应用层可以理解的调用。

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @PostMapping("/login")
    public LoginResp login(@RequestBody LoginReq req) {
        Authentication authentication = authenticationManager.authenticate(...);
        String token = tokenService.generateToken(authentication);
        return new LoginResp(token, 3600L);
    }
}
```

---

## 🔗 限界上下文 (Bounded Context)

项目划分了四个限界上下文，每个上下文有自己独立的领域模型：

```
┌─────────────────┐    ┌─────────────────┐
│   User Context  │    │ Message Context │
│  ─────────────  │    │  ─────────────  │
│  · User         │    │  · Message      │
│  · ContactInfo  │    │  · Conversation │
│  · OnlineStatus │    │  · ReadReceipt  │
└─────────────────┘    └─────────────────┘
         │                      │
         └──────────┬───────────┘
                    │
┌─────────────────┐ │ ┌─────────────────┐
│  Group Context  │ │ │Friendship Context│
│  ─────────────  │ │ │  ─────────────  │
│  · ChatGroup    │ │ │  · Friendship   │
│  · GroupMember  │ │ │                 │
└─────────────────┘   └─────────────────┘
```

---

## 📊 数据库设计

项目使用 MySQL 数据库，表结构设计体现了领域模型的映射：

| 表名             | 对应领域模型      | 说明         |
| ---------------- | ----------------- | ------------ |
| `users`          | User              | 用户信息     |
| `friendships`    | Friendship        | 好友关系     |
| `chat_groups`    | ChatGroup         | 群组信息     |
| `group_members`  | GroupMember       | 群成员       |
| `conversations`  | Conversation      | 会话         |
| `messages`       | Message           | 消息         |
| `read_receipts`  | ReadReceipt       | 已读回执     |

详细的建表语句请参考 [`docs/sql/init.sql`](docs/sql/init.sql)。

---

## 🚀 快速开始

### 环境要求

- JDK 25+
- Maven 3.9+
- MySQL 8.0+

### 运行步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd im-demo
   ```

2. **初始化数据库**
   ```bash
   mysql -u root -p < docs/sql/init.sql
   ```

3. **配置数据库连接**
   
   编辑 `src/main/resources/application.yml`，配置数据库连接信息。

4. **启动应用**
   ```bash
   mvn spring-boot:run
   ```

5. **访问 API 文档**
   
   打开浏览器访问：http://localhost:8080/swagger-ui.html

---

## 💡 学习要点

通过本项目，你可以学习到：

1. **DDD 四层架构**的标准划分与职责
2. **聚合根**与**实体**的区别和设计原则
3. **值对象**的正确使用场景
4. **领域服务**与**应用服务**的边界
5. **仓储模式**的接口与实现分离
6. **CQRS 模式**的简化应用
7. **领域事件**的发布与监听
8. **防腐层 (ACL)** 通过 Convertor 隔离领域模型与持久化模型

---

## 📚 推荐阅读

- [《领域驱动设计》- Eric Evans](https://book.douban.com/subject/1418618/)
- [《实现领域驱动设计》- Vaughn Vernon](https://book.douban.com/subject/25844633/)
- [《领域驱动设计精粹》- Vaughn Vernon](https://book.douban.com/subject/30333944/)

---

## 📝 License

MIT License
