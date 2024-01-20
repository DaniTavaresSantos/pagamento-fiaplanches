package br.com.fiaplanchespayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@SpringBootApplication
public class FiapLanchesPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiapLanchesPaymentApplication.class, args);
    }

}
