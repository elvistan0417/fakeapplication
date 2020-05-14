package com.experian.eda.dev.hazelcast.server.fake.fake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class Controller {

    @Value("${health.file}")
    private File healthFile;


    @GetMapping("/")
    @ResponseBody
    public String getHealt1h() throws IOException {
        return "testing";
    }

    @GetMapping(value = "/hazelcast/health", produces = MediaType.TEXT_PLAIN_VALUE)
    public byte[] getHealth() throws IOException {
        if (!healthFile.exists()) {
            throw new HealthFileNotFoundException();
        }

        return Files.readAllBytes(Paths.get(healthFile.toURI()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private static final class HealthFileNotFoundException extends RuntimeException {
    }
}
