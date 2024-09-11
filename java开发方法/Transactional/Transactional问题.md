## 不必要

### 1. 无需事务的业务

在没有事务操作的业务方法上使用 @Transactional 注解，比如：用在仅有查询或者一些 HTTP 请求的方法，虽然加上影响不大，但从编码规范的角度来看还是不够严谨，建议去掉。

```java
@Transactionalpublic
String testQuery() {
    standardBak2Service.getById(1L);
    return "testB";
}
```

### 2. 事务范围过大

有些同学为了省事直接将 @Transactional 注解加在了类上或者抽象类上，这样做导致的问题就是**类内的方法或抽象类的实现类中所有方法全部都被事务管理**。增加了不必要的性能开销或复杂性，建议按需使用，只在有事务逻辑的方法上加@Transactional。

```java
@Transactionalpublic
abstract class BaseService {
}

@Slf4j
@Servicepublic
class TestMergeService extends BaseService {
    private final TestAService testAService;

    public String testMerge() {
        testAService.testA();
        return "ok";
    }
}
```

如果在类中的方法上添加 @Transactional 注解，它将覆盖类级别的事务配置。例如，类级别上配置了只读事务，方法级别上的 @Transactional 注解也会覆盖该配置，从而启用读写事务。

```java
@Transactional(readOnly = true)
public class TestMergeService {
    private final TestBService testBService;
    private final TestAService testAService;

    @Transactional
    public String testMerge() {
        testAService.testA();
        testBService.testB();
        return "ok";
    }
}     
```

## 不生效

### 3. 方法权限问题

**不要把 @Transactional注解加在 private 级别的方法上！**

我们知道 @Transactional 注解依赖于Spring AOP切面来增强事务行为，这个 AOP 是通过代理来实现的，而 private 方法恰恰不能被代理的，所以 AOP 对 private 方法的增强是无效的，@Transactional也就不会生效。

```java
@Transactionalprivate
String testMerge() {
    testAService.testA();
    testBService.testB();
    return "ok";
}
```

那如果我在 testMerge() 方法内调用 private 的方法事务会生效吗？

答案：事务会生效

```java
@Transactionalpublic
String testMerge() throws Exception {
    ccc();
    return "ok";
}

private void ccc() {
    testAService.testA();
    testBService.testB();
}
```

### 4. 被用 final 、static 修饰方法

和上边的原因类似，被用 `final` 、`static` 修饰的方法上加 @Transactional 也不会生效。

- static 静态方法属于类本身的而非实例，因此代理机制是无法对静态方法进行代理或拦截的
- final 修饰的方法不能被子类重写，事务相关的逻辑无法插入到 final 方法中，代理机制无法对 final 方法进行拦截或增强。

这些都是java基础概念了，使用时要注意。

```java
@Transactionalpublic
static void b() {
}

@Transactionalpublic
final void b() {
}
```