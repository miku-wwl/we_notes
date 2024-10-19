``` java
import org.springframework.core.codec.CodecException;

import java.io.*;

public class ArraySerializer {

    public <T> byte[] serialize(T obj) throws CodecException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CodecException("Serialization failed", e);
        }
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws CodecException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            Object obj = ois.readObject();
            if (!clazz.isInstance(obj)) {
                throw new CodecException("Deserialized object is not of type " + clazz.getName());
            }
            return clazz.cast(obj);
        } catch (IOException | ClassNotFoundException e) {
            throw new CodecException("Deserialization failed", e);
        }
    }
}
```

``` java
import org.springframework.core.codec.CodecException;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArraySerializer serializer = new ArraySerializer();

        // 序列化 int 数组
        int[] intArray = {1, 2, 3, 4, 5};
        byte[] serializedIntArray = null;
        try {
            serializedIntArray = serializer.serialize(intArray);
            System.out.println("Serialized int array length: " + serializedIntArray.length);
        } catch (CodecException e) {
            e.printStackTrace();
        }

        // 反序列化 int 数组
        int[] deserializedIntArray = null;
        try {
            deserializedIntArray = serializer.deserialize(serializedIntArray, int[].class);
            System.out.println("Deserialized int array: " + Arrays.toString(deserializedIntArray));
        } catch (CodecException e) {
            e.printStackTrace();
        }

        // 序列化 String 数组
        String[] stringArray = {"Hello", "World"};
        byte[] serializedStringArray = null;
        try {
            serializedStringArray = serializer.serialize(stringArray);
            System.out.println("Serialized String array length: " + serializedStringArray.length);
        } catch (CodecException e) {
            e.printStackTrace();
        }

        // 反序列化 String 数组
        String[] deserializedStringArray = null;
        try {
            deserializedStringArray = serializer.deserialize(serializedStringArray, String[].class);
            System.out.println("Deserialized String array: " + Arrays.toString(deserializedStringArray));
        } catch (CodecException e) {
            e.printStackTrace();
        }
    }
}
```

```
C:\jdk-17.0.11\bin\java.exe "-javaagent:C:\JetBrains\IntelliJ IDEA 2024.1.1\lib\idea_rt.jar=55617:C:\JetBrains\IntelliJ IDEA 2024.1.1\bin" -Dfile.encoding=UTF-8 -classpath C:\workspace\we_aeron\target\test-classes;C:\workspace\we_aeron\target\classes;C:\apache-maven-3.9.6\org\projectlombok\lombok\1.18.24\lombok-1.18.24.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-starter-web\3.0.0\spring-boot-starter-web-3.0.0.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-starter\3.0.0\spring-boot-starter-3.0.0.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot\3.0.0\spring-boot-3.0.0.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-autoconfigure\3.0.0\spring-boot-autoconfigure-3.0.0.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-starter-logging\3.0.0\spring-boot-starter-logging-3.0.0.jar;C:\apache-maven-3.9.6\ch\qos\logback\logback-classic\1.4.5\logback-classic-1.4.5.jar;C:\apache-maven-3.9.6\ch\qos\logback\logback-core\1.4.5\logback-core-1.4.5.jar;C:\apache-maven-3.9.6\org\apache\logging\log4j\log4j-to-slf4j\2.19.0\log4j-to-slf4j-2.19.0.jar;C:\apache-maven-3.9.6\org\apache\logging\log4j\log4j-api\2.19.0\log4j-api-2.19.0.jar;C:\apache-maven-3.9.6\org\slf4j\jul-to-slf4j\2.0.4\jul-to-slf4j-2.0.4.jar;C:\apache-maven-3.9.6\jakarta\annotation\jakarta.annotation-api\2.1.1\jakarta.annotation-api-2.1.1.jar;C:\apache-maven-3.9.6\org\yaml\snakeyaml\1.33\snakeyaml-1.33.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-starter-json\3.0.0\spring-boot-starter-json-3.0.0.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\core\jackson-databind\2.14.1\jackson-databind-2.14.1.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\core\jackson-core\2.14.1\jackson-core-2.14.1.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.14.1\jackson-datatype-jdk8-2.14.1.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.14.1\jackson-datatype-jsr310-2.14.1.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\module\jackson-module-parameter-names\2.14.1\jackson-module-parameter-names-2.14.1.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-starter-tomcat\3.0.0\spring-boot-starter-tomcat-3.0.0.jar;C:\apache-maven-3.9.6\org\apache\tomcat\embed\tomcat-embed-core\10.1.1\tomcat-embed-core-10.1.1.jar;C:\apache-maven-3.9.6\org\apache\tomcat\embed\tomcat-embed-el\10.1.1\tomcat-embed-el-10.1.1.jar;C:\apache-maven-3.9.6\org\apache\tomcat\embed\tomcat-embed-websocket\10.1.1\tomcat-embed-websocket-10.1.1.jar;C:\apache-maven-3.9.6\org\springframework\spring-web\6.0.2\spring-web-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-beans\6.0.2\spring-beans-6.0.2.jar;C:\apache-maven-3.9.6\io\micrometer\micrometer-observation\1.10.2\micrometer-observation-1.10.2.jar;C:\apache-maven-3.9.6\io\micrometer\micrometer-commons\1.10.2\micrometer-commons-1.10.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-webmvc\6.0.2\spring-webmvc-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-aop\6.0.2\spring-aop-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-context\6.0.2\spring-context-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-expression\6.0.2\spring-expression-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-starter-test\3.0.0\spring-boot-starter-test-3.0.0.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-test\3.0.0\spring-boot-test-3.0.0.jar;C:\apache-maven-3.9.6\org\springframework\boot\spring-boot-test-autoconfigure\3.0.0\spring-boot-test-autoconfigure-3.0.0.jar;C:\apache-maven-3.9.6\com\jayway\jsonpath\json-path\2.7.0\json-path-2.7.0.jar;C:\apache-maven-3.9.6\net\minidev\json-smart\2.4.8\json-smart-2.4.8.jar;C:\apache-maven-3.9.6\net\minidev\accessors-smart\2.4.8\accessors-smart-2.4.8.jar;C:\apache-maven-3.9.6\org\ow2\asm\asm\9.1\asm-9.1.jar;C:\apache-maven-3.9.6\org\slf4j\slf4j-api\2.0.4\slf4j-api-2.0.4.jar;C:\apache-maven-3.9.6\jakarta\xml\bind\jakarta.xml.bind-api\4.0.0\jakarta.xml.bind-api-4.0.0.jar;C:\apache-maven-3.9.6\jakarta\activation\jakarta.activation-api\2.1.0\jakarta.activation-api-2.1.0.jar;C:\apache-maven-3.9.6\org\assertj\assertj-core\3.23.1\assertj-core-3.23.1.jar;C:\apache-maven-3.9.6\net\bytebuddy\byte-buddy\1.12.19\byte-buddy-1.12.19.jar;C:\apache-maven-3.9.6\org\hamcrest\hamcrest\2.2\hamcrest-2.2.jar;C:\apache-maven-3.9.6\org\junit\jupiter\junit-jupiter\5.9.1\junit-jupiter-5.9.1.jar;C:\apache-maven-3.9.6\org\junit\jupiter\junit-jupiter-params\5.9.1\junit-jupiter-params-5.9.1.jar;C:\apache-maven-3.9.6\org\junit\jupiter\junit-jupiter-engine\5.9.1\junit-jupiter-engine-5.9.1.jar;C:\apache-maven-3.9.6\org\junit\platform\junit-platform-engine\1.9.1\junit-platform-engine-1.9.1.jar;C:\apache-maven-3.9.6\org\mockito\mockito-core\4.8.1\mockito-core-4.8.1.jar;C:\apache-maven-3.9.6\net\bytebuddy\byte-buddy-agent\1.12.19\byte-buddy-agent-1.12.19.jar;C:\apache-maven-3.9.6\org\objenesis\objenesis\3.2\objenesis-3.2.jar;C:\apache-maven-3.9.6\org\mockito\mockito-junit-jupiter\4.8.1\mockito-junit-jupiter-4.8.1.jar;C:\apache-maven-3.9.6\org\skyscreamer\jsonassert\1.5.1\jsonassert-1.5.1.jar;C:\apache-maven-3.9.6\com\vaadin\external\google\android-json\0.0.20131108.vaadin1\android-json-0.0.20131108.vaadin1.jar;C:\apache-maven-3.9.6\org\springframework\spring-core\6.0.2\spring-core-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-jcl\6.0.2\spring-jcl-6.0.2.jar;C:\apache-maven-3.9.6\org\springframework\spring-test\6.0.2\spring-test-6.0.2.jar;C:\apache-maven-3.9.6\org\xmlunit\xmlunit-core\2.9.0\xmlunit-core-2.9.0.jar;C:\apache-maven-3.9.6\org\junit\jupiter\junit-jupiter-api\5.11.2\junit-jupiter-api-5.11.2.jar;C:\apache-maven-3.9.6\org\opentest4j\opentest4j\1.3.0\opentest4j-1.3.0.jar;C:\apache-maven-3.9.6\org\junit\platform\junit-platform-commons\1.9.1\junit-platform-commons-1.9.1.jar;C:\apache-maven-3.9.6\org\apiguardian\apiguardian-api\1.1.2\apiguardian-api-1.1.2.jar;C:\apache-maven-3.9.6\io\aeron\aeron-all\1.46.5\aeron-all-1.46.5.jar;C:\apache-maven-3.9.6\uk\co\real-logic\sbe-all\1.33.0\sbe-all-1.33.0.jar;C:\apache-maven-3.9.6\com\lmax\disruptor\4.0.0\disruptor-4.0.0.jar;C:\apache-maven-3.9.6\cn\hutool\hutool-all\5.8.32\hutool-all-5.8.32.jar;C:\apache-maven-3.9.6\org\eclipse\collections\eclipse-collections-api\9.2.0\eclipse-collections-api-9.2.0.jar;C:\apache-maven-3.9.6\org\eclipse\collections\eclipse-collections\9.2.0\eclipse-collections-9.2.0.jar;C:\apache-maven-3.9.6\com\github\xiaoymin\knife4j-openapi3-jakarta-spring-boot-starter\4.1.0\knife4j-openapi3-jakarta-spring-boot-starter-4.1.0.jar;C:\apache-maven-3.9.6\com\github\xiaoymin\knife4j-core\4.1.0\knife4j-core-4.1.0.jar;C:\apache-maven-3.9.6\com\github\xiaoymin\knife4j-openapi3-ui\4.1.0\knife4j-openapi3-ui-4.1.0.jar;C:\apache-maven-3.9.6\org\springdoc\springdoc-openapi-starter-common\2.0.4\springdoc-openapi-starter-common-2.0.4.jar;C:\apache-maven-3.9.6\io\swagger\core\v3\swagger-core-jakarta\2.2.8\swagger-core-jakarta-2.2.8.jar;C:\apache-maven-3.9.6\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;C:\apache-maven-3.9.6\jakarta\validation\jakarta.validation-api\3.0.2\jakarta.validation-api-3.0.2.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\dataformat\jackson-dataformat-yaml\2.14.1\jackson-dataformat-yaml-2.14.1.jar;C:\apache-maven-3.9.6\org\springdoc\springdoc-openapi-starter-webflux-ui\2.0.4\springdoc-openapi-starter-webflux-ui-2.0.4.jar;C:\apache-maven-3.9.6\org\springdoc\springdoc-openapi-starter-webflux-api\2.0.4\springdoc-openapi-starter-webflux-api-2.0.4.jar;C:\apache-maven-3.9.6\org\springframework\spring-webflux\6.0.2\spring-webflux-6.0.2.jar;C:\apache-maven-3.9.6\io\projectreactor\reactor-core\3.5.0\reactor-core-3.5.0.jar;C:\apache-maven-3.9.6\org\reactivestreams\reactive-streams\1.0.4\reactive-streams-1.0.4.jar;C:\apache-maven-3.9.6\org\webjars\swagger-ui\4.18.1\swagger-ui-4.18.1.jar;C:\apache-maven-3.9.6\org\webjars\webjars-locator-core\0.52\webjars-locator-core-0.52.jar;C:\apache-maven-3.9.6\io\github\classgraph\classgraph\4.8.149\classgraph-4.8.149.jar;C:\apache-maven-3.9.6\org\springdoc\springdoc-openapi-starter-webmvc-ui\2.0.4\springdoc-openapi-starter-webmvc-ui-2.0.4.jar;C:\apache-maven-3.9.6\org\springdoc\springdoc-openapi-starter-webmvc-api\2.0.4\springdoc-openapi-starter-webmvc-api-2.0.4.jar;C:\apache-maven-3.9.6\io\swagger\core\v3\swagger-annotations-jakarta\2.2.8\swagger-annotations-jakarta-2.2.8.jar;C:\apache-maven-3.9.6\io\swagger\core\v3\swagger-models-jakarta\2.2.8\swagger-models-jakarta-2.2.8.jar;C:\apache-maven-3.9.6\com\fasterxml\jackson\core\jackson-annotations\2.14.1\jackson-annotations-2.14.1.jar com.weilai.aeron.Main
Serialized int array length: 47
Deserialized int array: [1, 2, 3, 4, 5]
Serialized String array length: 60
Deserialized String array: [Hello, World]

Process finished with exit code 0

```