package com.colak.springtutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class HelloWorldControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void showHelloWorldInParallel() {
        int numberOfThreads = 6;
        try (ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            List<Runnable> tasks = new ArrayList<>();

            for (int i = 0; i < numberOfThreads; i++) {
                tasks.add(() -> {
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        String url = "http://localhost:" + port + "/hello";
                        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                        log.info(response.getBody());
                    } catch (Exception exception) {
                        log.error("Exception ", exception);
                    }
                });
            }

            // Submit all tasks to the executor service for parallel execution
            tasks.forEach(executorService::submit);

        }
    }
}
