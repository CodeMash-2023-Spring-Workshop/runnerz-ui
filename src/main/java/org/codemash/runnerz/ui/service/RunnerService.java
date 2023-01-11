package org.codemash.runnerz.ui.service;

import org.codemash.runnerz.ui.model.Run;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RunnerService {
    private final RestTemplate restTemplate;

    public RunnerService(RestTemplateBuilder restTemplateBuilder, @Value("${apiBase}") String runnerServiceUri) {
        this.restTemplate = restTemplateBuilder.rootUri(runnerServiceUri).build();
    }

    public String hello(String name) {
        if (name.length() > 0) {
            return restTemplate.getForObject("/api/hello?name={name}", String.class, name);
        } else {
            return restTemplate.getForObject("/api/hello", String.class);
        }
    }

    public Integer createRun(Run run) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Run> request =
                new HttpEntity<Run>(run, headers);
        return restTemplate.postForObject("/api/runs", request, Integer.class);
    }

    public Run[] getAll() {
        return restTemplate.getForObject("/api/runs", Run[].class);
    }

    public Boolean isHealthy() {
        try {
            String response = restTemplate.getForObject("/api/hello", String.class);
            if (response != null && response.contains("Hello")) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
