package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import models.request.GeneratorInputRequest;
import models.response.ErrorResponse;
import models.response.GeneratorInputResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import service.GeneratorService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static constants.RequestSpec.requestSpecification;
import static constants.RequestSpec.requestSpecificationFileDownload;
import static constants.ResponseSpec.*;
import static helper.HelperMethods.*;

public class GeneratorTest {

    GeneratorService generatorService = new GeneratorService();

    @Test
    public void getClients() {

        generatorService.getClients(requestSpecification(), checkStatusCodeOk());

    }

    @Test
    public void postClientLibraryShouldBeSuccess() throws JsonProcessingException {

        String expectedLanguage = getRandomLanguage();

        GeneratorInputRequest generatorInputRequest = GeneratorInputRequest.builder().build();

        generatorService.generateClientLibrary(generatorInputRequest, expectedLanguage, requestSpecification(), checkStatusCodeOk())
                .as(GeneratorInputResponse.class);

    }

    @Test
    public void postClientLibraryShouldBeFailThenSwaggerUrlNotValid() throws JsonProcessingException {

        String expectedLanguage = getRandomLanguage();

        String expectedErrorMessage = "The swagger specification supplied was not valid";

        String notValidSwaggerUrl = getRandomSwaggerUrl();

        GeneratorInputRequest generatorInputRequest = GeneratorInputRequest.builder()
                .swaggerUrl(notValidSwaggerUrl)
                .build();

        ErrorResponse errorResponse = generatorService
                .generateClientLibrary(generatorInputRequest, expectedLanguage, requestSpecification(), checkStatusCodeBadRequest())
                .as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getMessage(), expectedErrorMessage);

    }

    @Test
    public void postClientLibraryShouldBeFailThenLanguageNotValid() {

        String notValidLanguage = getRandomName();

        String expectedErrorMessage = "Unsupported target " +notValidLanguage+" supplied";

        GeneratorInputRequest generatorInputRequest = GeneratorInputRequest.builder().build();

        ErrorResponse errorResponse = generatorService
                .generateClientLibrary(generatorInputRequest, notValidLanguage, requestSpecification(), checkStatusCodeBadRequest())
                .as(ErrorResponse.class);

        Assert.assertEquals(errorResponse.getMessage(), expectedErrorMessage);

    }

    @Test
    public void getDownloadFileShouldBeSuccess() throws JsonProcessingException {

        String expectedLanguage = getRandomLanguage();

        GeneratorInputRequest generatorInputRequest = GeneratorInputRequest.builder().build();

        GeneratorInputResponse generatorInputResponse = generatorService.generateClientLibrary(generatorInputRequest, expectedLanguage, requestSpecification(), checkStatusCodeOk())
                .as(GeneratorInputResponse.class);

        String fileId = generatorInputResponse.getCode();

        Response downloadFileResponse = generatorService.getDownloadFile(fileId, requestSpecificationFileDownload(), checkStatusCodeContentTypeBinary());

        byte[] fileContent = downloadFileResponse.getBody().asByteArray();

        String pathname = "target/downloaded-"+expectedLanguage+".zip";

        try (FileOutputStream fos = new FileOutputStream((pathname))) {
            fos.write(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRandomLanguage() throws JsonProcessingException {

        String responseLanguages = generatorService.getClients(requestSpecification(), checkStatusCodeOk()).asPrettyString();

        ObjectMapper mapper = new ObjectMapper();

        List<String> languages = mapper.readValue(responseLanguages, new TypeReference<>(){});

        Random random = new Random();

        int index = random.nextInt(languages.size());

        return languages.get(index);

    }
}
