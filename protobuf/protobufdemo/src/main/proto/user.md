生成的 `User` 类包含 Protobuf 核心操作 API，按功能分类后，以下是最常用且重要的 API，涵盖对象构建、字段操作、序列化/反序列化等核心场景：

### 一、构建器（Builder）核心 API（创建/修改对象）
Protobuf 对象不可变，必须通过 Builder 构建或修改，这是最基础的 API 组。
- `User.newBuilder()`：创建空的 Builder 实例（核心入口）。
- `User.newBuilder(User prototype)`：基于已有 User 对象复制创建 Builder（用于修改已有对象）。
- `builder.setXxx(值)`：设置字段值（如 `setId(1)`、`setName("张三")`，支持所有定义的字段）。
- `builder.mergeXxx(值)`：合并字段值（如 `mergeAddress(addressBuilder.build())`，适用于嵌套消息）。
- `builder.clearXxx()`：清空字段值（如 `clearEmail()`，恢复字段默认值）。
- `builder.build()`：构建不可变的 User 实例（会校验字段初始化状态）。
- `builder.buildPartial()`：构建 User 实例（不校验初始化状态，性能略高）。

### 二、字段访问 API（读取对象数据）
- 基本字段 getter：`getId()`、`getName()`、`getEmail()`、`getAge()`、`getActive()`，直接获取对应字段值。
- 字符串字段字节形式：`getNameBytes()`、`getEmailBytes()`，返回 `ByteString` 类型（避免字符串拷贝，适合网络传输）。
- 可选字段判断：`hasAddress()`，判断可选字段（如 address）是否已设置（proto3 可选字段必备）。
- 嵌套消息访问：`getAddress()`，获取嵌套的 Address 对象；`getAddressOrBuilder()`，获取 Address 的 Builder 或实例（支持嵌套消息修改）。

### 三、序列化/反序列化 API（Protobuf 核心功能）
- 序列化（对象 → 二进制）：
    - `toByteArray()`：将 User 对象序列化为字节数组（最常用，适合网络传输、存储）。
    - `writeTo(CodedOutputStream output)`：将对象写入编码输出流（适合大对象或流式传输）。
    - `getSerializedSize()`：计算序列化后的字节大小（提前预估传输/存储容量）。
- 反序列化（二进制 → 对象）：
    - `User.parseFrom(byte[] data)`：从字节数组解析 User 对象（最常用）。
    - `User.parseFrom(InputStream input)`：从输入流解析（支持网络流、文件流直接解析）。
    - `User.parseDelimitedFrom(InputStream input)`：从分隔符分隔的流解析（处理多个连续的 Protobuf 消息）。
    - 重载形式：支持 `ByteBuffer`、`ByteString`、`ExtensionRegistryLite`（扩展字段支持）等参数，适配不同场景。

### 四、对象操作 API（比较、校验、默认实例）
- `equals(Object obj)`：深度比较两个 User 对象的所有字段是否相等（Protobuf 自动重写，比手动比较高效）。
- `hashCode()`：基于所有字段计算哈希值（支持 User 对象作为 Map 键值）。
- `isInitialized()`：校验对象是否完全初始化（确保必填字段已设置，避免序列化失败）。
- `getDefaultInstance()`：获取 User 的默认实例（所有字段为默认值：int 为 0、string 为空串、bool 为 false）。
- `parser()`：获取 User 的解析器实例（`Parser<User>`），支持自定义反序列化逻辑。

### 五、嵌套消息 Address 相关 API
Address 作为 User 的嵌套消息，自身也包含完整的 Protobuf API，核心如下：
- 构建：`User.Address.newBuilder()`、`builder.setStreet("xxx")`、`build()`。
- 访问：`getStreet()`、`getCity()`、`getCountry()`、`getStreetBytes()`。
- 序列化/反序列化：`Address.parseFrom(byte[] data)`、`toByteArray()`。

---