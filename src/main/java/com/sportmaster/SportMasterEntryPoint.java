package com.sportmaster;

import com.sportmaster.formatter.ResultFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportMasterEntryPoint implements CommandLineRunner {

    @Autowired
    ResultFormatter resultFormatter;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SportMasterEntryPoint.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(resultFormatter.formatStr(args[0], args[1]));
    }
}
