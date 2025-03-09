package pl.edu.pja.tpo03.s26822springdata;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.util.Scanner;


@Configuration
public class Config
{
    @Bean
    @Profile("CAP")
    public TranslationHandler translationHandler()
    {
        return new TranslationCap();
    }
    @Bean
    @Profile("LOW")
    public TranslationHandler translationLow()
    {
        return new TransltaionLow();
    }
    @Bean
    @Profile("CSV")
    public TranslationHandler translationCsv()
    {
        return new TranslationCSV();
    }
    @Bean
    public FileService fileService(String s)
    {
        return new FileService(s);
    }
    @Bean
    public String path(@Value("${pl.edu.pja.tpo02.filename}") String s)
    {
        return s;
    }
    @Bean
    public Scanner scanner()
    {
        return new Scanner(System.in);
    }
}
