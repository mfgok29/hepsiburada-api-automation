package helper;

import com.github.javafaker.Faker;


public class HelperMethods {

    public static String getRandomName() {
        Faker faker = new Faker();
        return faker.animal().name();
    }

    public static String getRandomSwaggerUrl(){
        Faker faker = new Faker();

        String domainName = faker.internet().domainName();

        String urlPath = "/api/" + faker.lorem().word();

        return "https://" + domainName + urlPath;
    }


}
