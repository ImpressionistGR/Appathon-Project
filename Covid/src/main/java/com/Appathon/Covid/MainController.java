package com.Appathon.Covid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


@Controller
@RequestMapping(path = "/covid")
public class MainController {
    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private PublishYearCategoryRepository publishYearCategoryRepository;

    @Autowired
    private InstitutionRepository institutionRepository;


    @PatchMapping(path="/findArticleFromTitle")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody Collection<Paper> findArticleFromTitle (@RequestParam String word) {
        System.out.println(word);
        return paperRepository.findArticleFromTitle(word);
    }

    @GetMapping(path="/visualizeData")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody LinkedList<ChartData> visualizeData () {
        LinkedList<ChartData> list = new LinkedList<>();

        // articles_by_journal
        LinkedList<String> labels = new LinkedList<>();
        LinkedList<Integer> data = new LinkedList<>();
        Iterator<Journal> journals = journalRepository.findAll().iterator();
        while(journals.hasNext()) {
            Journal j = journals.next();
            labels.add(j.getJournal());
            data.add(j.getSum());
        }
        LinkedList<String> tmp = new LinkedList<>();
        DataSet d = new DataSet("Articles By Journal", data, tmp);
        LinkedList<DataSet> ld = new LinkedList<>();
        ld.add(d);
        ChartData chartData = new ChartData(labels, ld);
        list.add(chartData);

        // articles_by_publish_year_category
        labels = new LinkedList<>();
        data = new LinkedList<>();
        Iterator<PublishYearCategory> categories = publishYearCategoryRepository.findAll().iterator();
        while(categories.hasNext()) {
            PublishYearCategory c = categories.next();
            labels.add(c.getCategory());
            data.add(c.getSum());
        }
        tmp = new LinkedList<>();
        d = new DataSet("Articles By Publish Year Category", data, tmp);
        ld = new LinkedList<>();
        ld.add(d);
        chartData = new ChartData(labels, ld);
        list.add(chartData);

        // authors_by_institution
        labels = new LinkedList<>();
        data = new LinkedList<>();
        Iterator<Institution> institutions = institutionRepository.findAll().iterator();
        while(institutions.hasNext()) {
            Institution i = institutions.next();
            labels.add(i.getInstitution());
            data.add(i.getSum());
        }
        tmp = new LinkedList<>();
        d = new DataSet("Authors By Institution", data, tmp);
        ld = new LinkedList<>();
        ld.add(d);
        chartData = new ChartData(labels, ld);
        list.add(chartData);

        return list;
    }

    @PatchMapping(path="/update") // Map ONLY PATCH Requests
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody String updatePaper (@RequestParam String sha) {
        LinkedList<String> list = jsonToList(sha);

        //update paper - add bodytext
        String bodyText = list.removeFirst();
        Paper p = paperRepository.findById(sha).get();
        if (p.getStatus()==1) return "already exists";

        //else - add paper
        p.setBodyText(bodyText);
        p.setStatus(1);
        paperRepository.save(p);

        //update articles_by_journal
        Journal j;
        if (!(journalRepository.findById(p.getJournal()).isPresent())) {
            j = new Journal();
            j.setJournal(p.getJournal());
            j.setSum(1);
        }
        else {
            j = journalRepository.findById(p.getJournal()).get();
            j.setSum(j.getSum()+1);
        }
        journalRepository.save(j);

        //update_articles_by_publish_year_category
        PublishYearCategory c;
        String category = "";
        String pt = p.getPublish_time();
        Integer py = Integer.parseInt(pt.substring(pt.length()-4));
        if (py <= 1950) category = "year<=1950";
        else if (py > 1950 && py <= 1980) category ="1950<year<=1980";
        else if (py > 1980) category = "1980>year";
        if (!(publishYearCategoryRepository.findById(category).isPresent())) {
            c = new PublishYearCategory();
            c.setCategory(category);
            c.setSum(1);
        }
        else {
            c = publishYearCategoryRepository.findById(category).get();
            c.setSum(c.getSum()+1);
        }
        publishYearCategoryRepository.save(c);

        //add authors
        Author a;
        while((list.size())!=0) {
            a = new Author();
            a.setSha(sha);
            a.setFullName(list.removeFirst());
            String institution = list.removeFirst();
            if (institution == "") institution = "none";
            a.setInstitution(institution);
            a.setSettlement(list.removeFirst());
            a.setCountry(list.removeFirst());
            authorRepository.save(a);

            //update authors_by_institution
            Institution i;
            if (!(institutionRepository.findById(a.getInstitution()).isPresent())) {
                i = new Institution();
                i.setInstitution(a.getInstitution());
                i.setSum(1);
            }
            else {
                i = institutionRepository.findById(a.getInstitution()).get();
                i.setSum(i.getSum()+1);
            }
            institutionRepository.save(i);
        }
        return "updated";
    }

    public LinkedList<String> jsonToList(String sha) {
        LinkedList<String> list = new LinkedList<String>();
        try {
            File file = new File("C:\\Programming\\demo_projects\\json_files\\7_json\\" + sha + ".json");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line, body_text, prevSection = "none", section, lastText, tmp;

            int len = 0;
            boolean check = false;

            do {
                line = br.readLine();
                //do nothing until you reach authors
            } while (!(line.contains("\"authors\"")));

            if (!(line.contains("\"authors\": []"))) {
                for (; ; ) {
                    line = br.readLine();
                    if (line.contains("\"body_text\"")) break;

                    //add fullname
                    if (line.contains("\"first\"")) {
                        tmp = line.substring(line.indexOf("\"first\"") + 10, line.length() - 2);
                        line = br.readLine();
                        if (line.contains("],"))
                            tmp = tmp + " " + line.substring(line.indexOf("\"middle\"") + 11, line.length() - 2);
                        else {
                            line = br.readLine();
                            tmp = tmp + " " + line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\"")-1);
                            br.readLine();
                        }
                        line = br.readLine();
                        tmp = tmp + " " + line.substring(line.indexOf("\"last\"") + 9, line.length() - 2);

                        System.out.println(tmp);
                        list.add(tmp);
                    }

                    //add institution
                    else if (line.contains("\"institution\"")) {
                        System.out.println(line.substring(line.indexOf("\"institution\"") + 16, line.length() - 2));
                        list.add(line.substring(line.indexOf("\"institution\"") + 16, line.length() - 2));
                    }

                    //add settlement
                    else if (line.contains("\"settlement\"")) {
                        System.out.println(line.substring(line.indexOf("\"settlement\"") + 15, line.length() - 2));
                        list.add(line.substring(line.indexOf("\"settlement\"") + 15, line.length() - 2));
                    }

                    //add country
                    else if (line.contains("\"country\"")) {
                        System.out.println(line.substring(line.indexOf("\"country\"") + 12, line.length() - 1));
                        list.add(line.substring(line.indexOf("\"country\"") + 12, line.length() - 1));
                    }
                }
            }

            br.readLine();
            line=br.readLine(); //read first text line

            for(;;) {
                //until you reach bib_entries do this
                if (!(line.contains("text"))) break;
                line = line.substring(line.indexOf("\"text\"")+9, line.length()-2);
                lastText = line;
                len=line.length();
                sb.append(line);

                for(;;) { //until we surpass cite_spans, ref_spans
                    line=br.readLine();
                    check=line.contains("\"section\""); // read section line
                    if (check) break;
                }

                section = line.substring(line.indexOf("\"section\"")+12, line.length()-1);

                if ((prevSection.contentEquals("none"))||(!(section.contentEquals(prevSection)))) { //fix the section
                    sb.delete(sb.length()-len, sb.length());
                    sb.append("\n");
                    sb.append(section);
                    sb.append("\n");
                    sb.append(lastText);
                }
                prevSection=section;
                br.readLine();
                br.readLine();
                line=br.readLine();
                //next text line
            }

            fr.close();    //closes the stream and release the resources
            body_text = sb.toString();
            list.addFirst(body_text);
            //System.out.println(body_text);
            System.out.println(body_text.length());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
