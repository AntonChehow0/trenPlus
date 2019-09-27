package com.com.sport.spnig.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class Server {

    public static void main(String[] argv) {
        SpringApplication.run(Server.class, argv);
    }

}
