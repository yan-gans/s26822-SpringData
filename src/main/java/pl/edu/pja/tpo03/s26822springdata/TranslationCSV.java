package pl.edu.pja.tpo03.s26822springdata;


import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TranslationCSV implements TranslationHandler {
    @Override
    public void show(Iterable<Entry> entryRepository) {
        System.out.printf("%-20s   %-20s   %-20s%n", "English:","German:","Polish:");
        for (Entry e:entryRepository)
        {
            System.out.println(e);
        }
    }
}
