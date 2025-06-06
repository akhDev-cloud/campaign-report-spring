package com.campaignreport.boot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.campaignreport.constants.FileConstants;
import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;
import com.campaignreport.repo.CampaignDataRepository;
import com.campaignreport.service.CampaignAggregationService;
import com.campaignreport.service.CampaignParser;
import com.campaignreport.utils.CsvWriter;
import com.campaignreport.utils.S3Downloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class Initializer implements ApplicationRunner{
	
	private final String COST_FILENAME = "cost_1.csv";
	private final String REVENUE_FILENAME = "revenue_1.csv";
	
	private final S3Downloader s3Downloader;
	private final CampaignParser parser;
	private final CampaignAggregationService dataService;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		String costFileName = Strings.concat(FileConstants.LOCAL_PATH, COST_FILENAME);
		String revenueFileName =  Strings.concat(FileConstants.LOCAL_PATH, REVENUE_FILENAME);
		
		log.info("running initializer");
	
		downloadRemoteFile(s3Downloader, FileConstants.COSTS_DOWNLOAD_PATH, costFileName);
		downloadRemoteFile(s3Downloader, FileConstants.REVENUE_DOWNLOAD_PATH, revenueFileName);
		
		parser.parseCostFileAndAggregateByCompanyAndDate(costFileName, CampaignDataRepository.campaignMap);
		log.info("file cost processed");
		parser.parseRevenueFileAndAggregateByCompanyAndDate(revenueFileName, CampaignDataRepository.campaignMap);
		log.info("file revenue processed");
		
		dataService.enrichAggregatedData(CampaignDataRepository.campaignMap);
		log.info("map enriched");
		
		CampaignDataRepository.GroupDataByDate(dataService);
		log.info("sorting by date finished");
		
		CsvWriter.writeMapToCsv(CampaignDataRepository.campaignMap, Strings.concat(FileConstants.LOCAL_PATH, "output.csv"));
		log.info("output file created");
	}


	private void downloadRemoteFile(S3Downloader s3Downloader, String fileDownloadPath, String localPathToSave) throws IOException {
		
		s3Downloader.download(
				fileDownloadPath,
				localPathToSave
			);
		log.info("file {} persists locally", localPathToSave);
		
	}

}
