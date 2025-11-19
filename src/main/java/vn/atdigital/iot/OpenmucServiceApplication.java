package vn.atdigital.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OpenmucServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenmucServiceApplication.class, args);
    }

}
