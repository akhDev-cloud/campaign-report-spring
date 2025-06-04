package com.campaignreport.utils;



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class S3Downloader {
	
	private final RestTemplate restTemplate = new RestTemplate();

    public void download(String url, String localFileName) throws IOException {
    	
    	Path path = Paths.get(localFileName);
        Files.createDirectories(path.getParent());
        File file = new File(localFileName);
        
        if(!file.exists()) {
        	Resource resource = restTemplate.getForObject(url, Resource.class);
            try (InputStream in = resource.getInputStream()) {
                Files.copy(in, Paths.get(localFileName), StandardCopyOption.REPLACE_EXISTING);
            }

           log.info("Downloaded: {}", localFileName);
        } else {
        	log.info("File already exists localy, skipping download {}", localFileName);
        }
    }
}
