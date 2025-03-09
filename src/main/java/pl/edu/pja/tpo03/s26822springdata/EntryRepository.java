//package pl.edu.pja.tpo03.s26822springdata;
//
//import jakarta.persistence.EntityManager;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.File;
//import java.util.*;
//
//@Repository
//public class EntryRepository {
//    private Long eid= 1L;
//    private final EntityManager entityManager;
//    private List<Entry> db;
//    private FileService fileService;
//    private final Scanner scanner;
//    public EntryRepository(FileService fileService, EntityManager entityManager, Scanner scanner){
//        this.entityManager = entityManager;
//        this.fileService=fileService;
//        this.scanner = scanner;
//    }
//    @Transactional
//    public void addEntry(Entry entry)
//    {
//        entityManager.persist(entry);
//        this.db.add(entry);
//        this.eid++;
//    }
//    public Entry search(String key) throws EntryNotFoundException {
//        return Optional.ofNullable(entityManager.createQuery("SELECT e FROM Entry e WHERE e.polish = :key OR e.english=:key OR e.german=:key", Entry.class)
//                .setParameter("key",key)
//                .getSingleResult()).orElseThrow(EntryNotFoundException::new);
//
//    }
//    public Optional<Entry> findById(Long id){
//        return Optional.ofNullable(entityManager.find(Entry.class, id));
//    }
//    public void sort(String string1)
//    {
//        Comparator<Entry> comparator=switch (string1)
//        {
//            case "ea"->(e1,e2)-> e1.getEnglish().compareToIgnoreCase(e2.getEnglish());
//            case "ed"->(e1,e2)-> e2.getEnglish().compareToIgnoreCase(e1.getEnglish());
//            case "pa"->(e1,e2)-> e1.getPolish().compareToIgnoreCase(e2.getPolish());
//            case "pd"->(e1,e2)-> e2.getPolish().compareToIgnoreCase(e1.getPolish());
//            case "ga"->(e1,e2)-> e1.getGerman().compareToIgnoreCase(e2.getGerman());
//            case "gd"->(e1,e2)-> e2.getGerman().compareToIgnoreCase(e1.getGerman());
//            default -> throw new IllegalStateException("Unexpected value: " + string1);
//        };
//        this.db.sort(comparator);
//    }
//    @Transactional
//    public void upload()
//    {
//        for (String[] strings:fileService.read())
//        {
//            this.addEntry(new Entry(this.eid,strings));
//        }
//    }
//    @Transactional
//    public void deleteById(Long id){
//        findById(id).ifPresent(entityManager::remove);
//        this.db.removeIf(e -> e.getId() == id);
//    }
//
//    @Transactional
//    public Entry update(Entry entry) throws EntryNotFoundException {
//
//        Entry dbentry = findById(entry.getId())
//                .orElseThrow(EntryNotFoundException::new);
//        dbentry.setEnglish(entry.getEnglish());
//        dbentry.setGerman(entry.getGerman());
//        dbentry.setPolish(entry.getPolish());
//        this.db.removeIf(e->e.getId()==entry.getId());
//        this.db.add(dbentry);
//        return dbentry;
//    }
//    public List<Entry> getAllEntries()
//    {
//        List<Entry> list=this.entityManager.createQuery("SELECT e FROM Entry e",Entry.class).getResultList();
//        this.db=list;
//        long max = Long.MIN_VALUE;
//        for (Entry obj : this.db) {
//            max = Math.max(max, obj.getId());
//        }
//        this.eid=max+1;
//        return list;
//    }
//
//    public List<Entry> getDb() {
//        return db;
//    }
//    @Transactional
//    public void prepnsaveEntry()
//    {
//        String string;
//        System.out.println("Enter a translation that you wish to add(Format: eng_ger_pol):");
//        string=this.scanner.next();
//        while ( string.chars().filter(ch -> ch == '_').count()!=2||string.length()<5)
//        {
//            System.out.println("Please make sure that the input is in the correct format.");
//            string=this.scanner.next();
//        }
//        while(this.containsStringInLists(this.db,string.split("_")))
//        {
//            System.out.println("The dictionary already contains this word.\nTry once more:");
//            string=this.scanner.next();
//        }
//        String[] strings=string.split("_");
//        this.addEntry(new Entry(this.eid,strings));
//    }
//    public  boolean containsStringInLists(List<Entry> listOfLists, String[] target) {
//        for (Entry e:listOfLists)
//        {
//            for (String string : target)
//                if (e.getEnglish().equalsIgnoreCase(string)||e.getGerman().equalsIgnoreCase(string)||e.getPolish().equalsIgnoreCase(string)) {
//                    return true;
//                }
//        }
//        return false;
//    }
//
//}
