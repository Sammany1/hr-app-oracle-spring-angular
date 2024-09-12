package com.example.oraclebridge;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.oraclebridge")
public class OracleBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OracleBridgeApplication.class, args);
    }

}
