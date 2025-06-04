package com.campaignreport.boot;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.campaignreport.constants.FileConstants;
import com.campaignreport.utils.S3Downloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class Initializer implements ApplicationRunner{
	
	private final S3Downloader s3Downloader;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("running initializer");
		s3Downloader.download(
			    "https://s3.amazonaws.com/frontstory-test-data/server-side/cost_1.csv",
			    Strings.concat(FileConstants.DOWNLOAD_PATH, "cost_1.csv")
			);
		log.info("file cost persists locally");
		s3Downloader.download(
			    "https://s3.amazonaws.com/frontstory-test-data/server-side/revenue_1.csv",
			    Strings.concat(FileConstants.DOWNLOAD_PATH, "revenue_1.csv")
			);
		log.info("file revenue persists locally");
	}

}
