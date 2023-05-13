package helpers;

import com.github.javafaker.Faker;
import net.minidev.json.JSONObject;

import java.util.Date;
import java.util.*;

public class DataGenerator
{
    private static Faker faker = new Faker();

    public static String getRandomEmail(){
        String email = faker.name().firstName().toLowerCase() + faker.random().nextInt(0,100) + "@test.com";
        return email;
    }

    public static String getRandomUsername(){
        String username = faker.name().username();
        return username;
    }

    public static String getRandomTitle(){
        String title = faker.name().title();
        Date today = Calendar.getInstance().getTime();
        title += today;
        return title;
    }

    public static String getRandomDescription(){
        String title = faker.name().nameWithMiddle();
        return title;
    }


    public static JSONObject getRandomArticleValues(){
        String title = getRandomTitle();
        String description = faker.gameOfThrones().character();
        String body = faker.gameOfThrones().city();
        List tagList = new ArrayList<>();

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        json.put("body", body);
        json.put("tagList", tagList);

        return json;
    }
}
