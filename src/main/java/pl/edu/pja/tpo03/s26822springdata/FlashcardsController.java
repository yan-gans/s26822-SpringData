package pl.edu.pja.tpo03.s26822springdata;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Controller
public class FlashcardsController
{
    private Long eid;
    private  SpringDataEntryRepository entryRepository;
    private final TranslationHandler translationHandler;
    private FileService fileService;
    private Scanner scanner;

    public FlashcardsController(FileService fileService, TranslationHandler translationHandler, Scanner scanner)
    {
        this.eid=1l;
        this.scanner=scanner;
        this.translationHandler=translationHandler;
        this.fileService=fileService;
    }
    public void fire(SpringDataEntryRepository entryRepository)
    {
        this.entryRepository=entryRepository;
        if(this.entryRepository.count()==0l)
            this.entryRepository.saveAll(fileService.read());
        this.eid=this.entryRepository.count()+1;
        System.out.println("""
                To perform an operation please enter the corresponding letter:
                a->add a new translation
                d->delete a translation
                m->modify a translation
                j->sort
                h->search
                s->show all translations
                t->take a test
                q->quit""");
        String string=this.scanner.next();
        char c=string.charAt(0);
        while (string.length()!=1||(c!='a'&&c!='s'&&c!='t'&&c!='d'&&c!='j'&&c!='h'&&c!='q'&&c!='m'))
        {
            System.out.println("Please make sure that the input is in the correct format.");
            string=this.scanner.next();c=string.charAt(0);
        }
        while (c!='q')
        {
            switch (c)
            {
                case 'm':
                {
                    System.out.println("Enter any word from a translation to search for:");
                    String input=this.scanner.next();
                    Entry  entry = this.entryRepository.findByEnglishEqualsIgnoreCaseOrGermanEqualsIgnoreCaseOrPolishEqualsIgnoreCase(input,input,input);
                    System.out.println("Enter the language of the word you want to translate and after submitting the change:");
                    switch (this.scanner.next().charAt(0))
                    {
                        case 'e':
                        {
                            entry.setEnglish(this.scanner.next());
                            break;
                        }
                        case 'g':
                        {
                            entry.setGerman(this.scanner.next());
                            break;
                        }
                        case 'p':
                        {
                            entry.setPolish(this.scanner.next());
                            break;
                        }
                    }
                    Entry dbentry = null;
                    try {
                        dbentry = this.entryRepository.findById(entry.getId()).orElseThrow(EntryNotFoundException::new);
                    } catch (EntryNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    dbentry.setEnglish(entry.getEnglish());
                    dbentry.setGerman(entry.getGerman());
                    dbentry.setPolish(entry.getPolish());
                    this.entryRepository.save(dbentry);
                    break;
                }
                case 'j':
                {
                    System.out.println("Enter the language:");
                    String string1=this.scanner.next();
                    while (!string1.equals("ea") && !string1.equals("ed") && !string1.equals("pa") && !string1.equals("pd") && !string1.equals("gd") && !string1.equals("ga"))
                    {
                        System.out.println("Please make sure that the input is in the correct format.");
                        string1=this.scanner.next();
                    }
                    List<Entry> list=switch (string1)
                    {
                        case "ea"->this.entryRepository.findByOrderByEnglishAsc();
                        case "ed"->this.entryRepository.findByOrderByEnglishAsc().reversed();
                        case "pa"->this.entryRepository.findByOrderByGermanAsc();
                        case "pd"->this.entryRepository.findByOrderByGermanAsc().reversed();
                        case "ga"->this.entryRepository.findByOrderByPolishAsc();
                        case "gd"->this.entryRepository.findByOrderByPolishAsc().reversed();
                        default -> throw new IllegalStateException("Unexpected value: " + string1);
                    };
                    this.translationHandler.show(list);
                    break;
                }
                case 'h':
                {
                    System.out.println("Enter any word from a translation to search for:");
                    String input=this.scanner.next();
                    Entry  entry = this.entryRepository.findByEnglishEqualsIgnoreCaseOrGermanEqualsIgnoreCaseOrPolishEqualsIgnoreCase(input,input,input);
                    System.out.println(entry);

                    break;
                }
                case 'd':
                {
                    System.out.println("Enter any word from a translation to delete:");
                    String input=this.scanner.next();
                    Entry  entry = this.entryRepository.findByEnglishEqualsIgnoreCaseOrGermanEqualsIgnoreCaseOrPolishEqualsIgnoreCase(input,input,input);
                    this.entryRepository.deleteById(entry.getId());
                        System.out.println("Successfully deleted.");
                    break;
                }
                case 'a':
                {
                    String string1;
                    System.out.println("Enter a translation that you wish to add(Format: eng_ger_pol):");
                    string1=this.scanner.next();
                    while ( string1.chars().filter(ch -> ch == '_').count()!=2||string1.length()<5)
                    {
                        System.out.println("Please make sure that the input is in the correct format.");
                        string1=this.scanner.next();
                    }
                    while(this.containsStringInLists(this.entryRepository.findAll(),string1.split("_")))
                    {
                        System.out.println("The dictionary already contains this word.\nTry once more:");
                        string1=this.scanner.next();
                    }
                    String[] strings=string1.split("_");
                    this.entryRepository.save(new Entry(this.eid,strings));
                    this.eid++;
                    System.out.println("Received the translation");
                    break;
                }
                case 's':
                {
                    this.translationHandler.show(this.entryRepository.findAll());
                    break;
                }
                case 't':
                {
                    this.test();
                    break;
                }
            }
            c=this.scanner.next().charAt(0);
            while ((c!='a'&&c!='s'&&c!='t'&&c!='d'&&c!='j'&&c!='h'&&c!='q'&&c!='m'))
            {
                System.out.println("There's no such an option. Please enter the appropriate input.");
                c=this.scanner.next().charAt(0);
            }
        }
    }
    public  boolean containsStringInLists(Iterable<Entry> listOfLists, String[] target) {
        for (Entry e:listOfLists)
        {
            for (String string : target)
                if (e.getEnglish().equalsIgnoreCase(string)||e.getGerman().equalsIgnoreCase(string)||e.getPolish().equalsIgnoreCase(string)) {
                    return true;
                }
        }
        return false;
    }
    public void test()
    {
        int a=(int)(Math.random()*3);
        long b=(long) (Math.random()*(this.entryRepository.count()+1));
        String lang,word, f,sec;
        Entry e=this.entryRepository.findById(b).orElseThrow();
        switch (a)
        {
            case 1:
            {
                f=e.getEnglish();sec=e.getPolish();
                lang="German";
                word=e.getGerman();
                break;
            }
            case 2:
            {
                f=e.getEnglish();sec=e.getGerman();
                lang="Polish";
                word=e.getPolish();
                break;
            }
            default:
            {
                f=e.getGerman();sec=e.getPolish();
                lang="English";
                word=e.getEnglish();
            }
        }
        System.out.println("The test has started." +
                           "\nThe word you receive is in "+lang+" and is " + word+"." +
                           " \nPlease provide the translations(order doesnt matter, ex.: eng_fr)");
        String s=this.scanner.next();
        while ( s.chars().filter(ch -> ch == '_').count()!=1||s.length()<3)
        {
            System.out.println("Please make sure that the input is in the correct format.");
            s=this.scanner.next();
        }
        String[] strings=s.split("_");
        boolean pass=true;
        for(int i=0;i<strings.length;i++)
        {
            if(!strings[i].equalsIgnoreCase(f))
            {
                if(!strings[i].equalsIgnoreCase(sec))
                {
                    pass=false;
                    if(i==0)
                        System.out.println("The first word is incorrect.");
                    else
                        System.out.println("The second word is incorrect.");
                }
            }
        }
        if(pass) System.out.println("Congrets!");
    }
}
