package pl.edu.pja.tpo03.s26822springdata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
@Entity
public class Entry{
    @Id
    private  Long id;
    private  String english;
    private  String german;
    private  String polish;

    public Entry()
    {

    }
    public Entry(Long id, String[] strings) {
        this.id=id;
        this.english = strings[0];
        this.german = strings[1];
        this.polish = strings[2];
    }
    public Long getId() {
        return id;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public String getGerman() {
        return german;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPolish() {
        return polish;
    }

    public String getEnglish() {
        return english;
    }

    @Override
    public String toString() {
        return String.format("%-20s | %-20s | %-20s", this.english,this.german,this.polish);
    }



}
