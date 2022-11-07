package com.terratrue.app;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = "com.terratrue")
@ServletComponentScan({"com.terratrue.logging"})
public class App {
  private Logger logger = Logger.getLogger(App.class.getName());

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(App.class);
    app.run(args);
  }

  @GetMapping("/")
  public String hello() {
    logger.info("Hello world!");
    return "Hello world!";
  }
}
