``` java
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Hello, World!");
    }
}
```