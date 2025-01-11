package co.simplon.sockwork_business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class SockworkBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SockworkBusinessApplication.class, args);
    }

}
