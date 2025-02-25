package io.sparktype.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Starter {

  public static void main(String[] args) {
    SpringApplication.run(Starter.class, args);
  }

}
