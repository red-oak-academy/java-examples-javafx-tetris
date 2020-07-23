package me.redoak;

import me.redoak.tetrisfx.protocol.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class TetrisfxServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TetrisfxServerApplication.class, args);
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            System.out.println("You entered: " + input);
        }
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter();
    }

}

