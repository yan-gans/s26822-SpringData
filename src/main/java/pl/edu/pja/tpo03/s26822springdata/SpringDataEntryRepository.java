package pl.edu.pja.tpo03.s26822springdata;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataEntryRepository extends CrudRepository<Entry,Long> {
    Entry findByEnglishEqualsIgnoreCaseOrGermanEqualsIgnoreCaseOrPolishEqualsIgnoreCase(String value1,String value2,String value3);
    List<Entry> findByOrderByEnglishAsc();
    List<Entry> findByOrderByGermanAsc();
    List<Entry> findByOrderByPolishAsc();
}
