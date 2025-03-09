package pl.edu.pja.tpo03.s26822springdata;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@Service
public class FileService
{
    private final String s;
    public FileService(String s) {
        this.s=s;
    }
    public List<Entry> read()
    {
        Long l=1l;
        List<Entry> db=new ArrayList<>();
        try (FileInputStream stream = new FileInputStream(s)) {
            byte[]  bytes = stream.readAllBytes();
            String string=new String(bytes);
            String[] strings=string.split((char)13+"\n");
            for (String value : strings) {
                db.add(new Entry(l,value.split(";")));
                l++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return db;
    }

}
