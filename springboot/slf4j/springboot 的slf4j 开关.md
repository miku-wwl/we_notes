``` java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FuuuApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(FuuuApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FuuuApplication.class, args);
        if (log.isDebugEnabled()) {
            log.debug("Hello World");
        }

        log.debug("debug");
        log.info("info");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
```

``` properties
spring.application.name=fuuu
logging.level.com.weilai.fuuu=debug
```