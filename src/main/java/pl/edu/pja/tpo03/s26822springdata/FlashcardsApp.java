package pl.edu.pja.tpo03.s26822springdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.edu.pja.tpo03.s26822springdata.SpringDataEntryRepository;

import java.util.Scanner;


@SpringBootApplication
public class FlashcardsApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        SpringDataEntryRepository entryRepository= context.getBean(SpringDataEntryRepository.class);
        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.fire(entryRepository);
}

}
