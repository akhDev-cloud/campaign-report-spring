package com.campaignreport.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CampaignParserImpl implements CampaignParser{
	
	
	private static final DateTimeFormatter INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("M/d/yy H:mm");

    public void parseCostFileAndAggregateByCompanyAndDate(
    		String fileName,
    		Map<CampaignDateKey, AggregatedRecord> map) throws IOException {
    	
    	Path filePath = Paths.get(fileName);
    	
        try (BufferedReader reader = Files.newBufferedReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String dataDateStr = record.get("data_date");   
                String campaignId = record.get("campaign_id");
                String campaignName = record.get("campaign_name");
                int clicks = Integer.parseInt(record.get("clicks"));
                double cost = Double.parseDouble(record.get("cost"));

                // Parse and convert date from EST to UTC
                LocalDate utcDate = parseDateToUTC(dataDateStr);

               
                CampaignDateKey key = new CampaignDateKey(utcDate, campaignId);
                AggregatedRecord aggreagatedRecord = new AggregatedRecord(utcDate, campaignId, campaignName, clicks, cost);
                
                map.compute(key, (k,v) -> {
                	if (v == null) {
                        return aggreagatedRecord; 
                    }
                	v.setTotalClicks(v.getTotalClicks() + aggreagatedRecord.getTotalClicks());
                	v.setTotalCost(v.getTotalCost() + aggreagatedRecord.getTotalCost());
                	return v;
                });
                

               log.info("processed key {} and record {}", key, aggreagatedRecord);
            }
        }
        
    }
    
    
    public void parseRevenueFileAndAggregateByCompanyAndDate(
    		String fileName,
    		Map<CampaignDateKey, AggregatedRecord> map) throws IOException {
    	
    	Path filePath = Paths.get(fileName);
        
    	try (BufferedReader reader = Files.newBufferedReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                String dataDateStr = record.get("data_date");   
                String campaignId = record.get("campaign_id");
                double revenue = Double.parseDouble(record.get("revenue"));

                // Parse and convert date from EST to UTC
                LocalDate utcDate = parseDateToUTC(dataDateStr);

               
                CampaignDateKey key = new CampaignDateKey(utcDate, campaignId);
                AggregatedRecord aggreagatedRecord = new AggregatedRecord(utcDate, campaignId, revenue);
                
                map.compute(key, (k,v) -> {
                	if (v == null) {
                        return aggreagatedRecord; 
                    }
                	v.setTotalRevenue(v.getTotalRevenue() + aggreagatedRecord.getTotalRevenue());
                	return v;
                });
                

               log.info("processed key {} and record {}", key, aggreagatedRecord);
            }
        }
       
    }

    private LocalDate parseDateToUTC(String dateStr) {
        // Parse local datetime (EST)
        LocalDateTime estDateTime = LocalDateTime.parse(dateStr, INPUT_DATE_FORMAT);

        // EST timezone
        ZoneId estZone = ZoneId.of("America/New_York");

        // Convert to ZonedDateTime in EST
        ZonedDateTime estZonedDateTime = estDateTime.atZone(estZone);

        // Convert to UTC
        ZonedDateTime utcZonedDateTime = estZonedDateTime.withZoneSameInstant(ZoneOffset.UTC);

        // Return UTC date only (no time)
        return utcZonedDateTime.toLocalDate();
    }
}
