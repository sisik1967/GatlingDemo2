package helpers.createTokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.intuit.karate.Runner;
public class CreateTokens
{

    private static final ArrayList<String> tokens = new ArrayList<>();

    //iterator variable
    private static final AtomicInteger counter = new AtomicInteger();

    private static String[] emails = {
            "kardemo1@test.com",
            "kardemo2@test.com",
            "kardemo3@test.com"
    };

    public static String getNextToken() {
        return tokens.get(counter.getAndIncrement() % tokens.size());
    }


    public static void createAccessTokens() {

        for (String email : emails) {
            Map<String, Object> account = new HashMap<>();
            account.put("userEmail", email);
            account.put("userPassword", "Welcome1");
            Map<String, Object> result = Runner.runFeature("classpath:callers/conduit/token.feature@login", account, true);
            // With this line, You can call any karate feature from java file. This is very powerful technique
            // true demek karate configdeki parametreleri kullanicam demek,
            // false demek karate config kullanmayip kendi parametremi kendim vericem demek


            //token.feature fileinda def token'i burda kullaniyoruz.
            tokens.add(result.get("token").toString());

        }

    }

}