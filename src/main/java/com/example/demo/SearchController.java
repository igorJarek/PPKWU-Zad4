package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class SearchController
{
    @GetMapping("/search")
    public String greeting(@RequestParam(name="name", required=true) String name, Model model) throws IOException
    {
        name = name.replaceAll(" ", "+");

        Document document = Jsoup.connect("https://adm.edu.p.lodz.pl/user/users.php?search=" + name).get();
        Elements users = document.getElementsByClass("user-profile");
        if(users.size() > 0)
        {
            StringBuilder response = new StringBuilder();
            for(Element user : users)
            {
                Elements imageDiv = user.getElementsByClass("user-image-container");
                Element imageElement = imageDiv.get(0).getElementsByTag("a").first().getElementsByTag("img").first();
                String imageAdress = imageElement.attr("src");
                String imageTitle = imageElement.attr("alt");

                Elements dataDiv = user.getElementsByClass("user-info");
                String fullName = dataDiv.first().getElementsByTag("a").first().attr("title");
                String degree = dataDiv.first().getElementsByTag("h4").text();
                String fullNameRequest = fullName.replaceAll(" ", "+");

                response.append("<div>\n");
                response.append("<img src=\"" + imageAdress + "\" alt=\"" + imageTitle + "\">" + "\n");
                response.append("<h4> <a href=\"/generate?name=" + fullNameRequest + "\">" + degree + " " + fullName + "</a> </h4>\n");
                response.append("</div>\n");
            }

            model.addAttribute("bodyContent", response.toString());
        }
        else
            model.addAttribute("bodyContent", "Nie znaleziono nikogo o podanych danych.");

        return "search";
    }
}

