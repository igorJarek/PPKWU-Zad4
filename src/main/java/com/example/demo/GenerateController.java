package com.example.demo;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.StructuredName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class GenerateController {
    @GetMapping("/generate")
    public ResponseEntity<Resource>  searchEmployee(@RequestParam(name="name", required=true) String name) throws IOException
    {
        name = name.replaceAll(" ", "+");
        Document document = Jsoup.connect("https://adm.edu.p.lodz.pl/user/users.php?search=" + name).get();

        Element user = document.getElementsByClass("user-info").first();
        String fullName = user.getElementsByTag("a").first().attr("title");
        String[] splitterName = fullName.split(" ");
        String degree = user.getElementsByTag("h4").text();

        VCard vcard = new VCard();
        StructuredName data = new StructuredName();
        data.setFamily(splitterName[1]);
        data.setGiven(splitterName[0]);
        data.getPrefixes().add(degree);

        vcard.setStructuredName(data);
        vcard.setFormattedName(fullName);
        vcard.setOrganization("Politechnika Lodzka");

        File file = new File("card.vcf");
        Ezvcard.write(vcard).version(VCardVersion.V3_0).go(file);

        InputStreamResource resource = new InputStreamResource(new FileInputStream("card.vcf"));
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename=" + fullName + ".vcf")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("text/vcf")).body(resource);

    }
}

