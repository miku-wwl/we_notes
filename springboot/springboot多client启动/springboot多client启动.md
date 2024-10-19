``` java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModelingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModelingApplication.class, args);
    }
}
```

``` java
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class PClient {
    @PostConstruct
    private void init() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    log.info("PClient");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
```

``` java
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HClient {
    @PostConstruct
    private void init() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    log.info("HClient");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

```