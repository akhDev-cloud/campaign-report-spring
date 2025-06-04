package com.campaignreport.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;

public interface CampaignParser {
	 public void parseCostFileAndAggregateByCompanyAndDate(
			 	String fileName,
	    		Map<CampaignDateKey, AggregatedRecord> map) throws IOException;
	 public void parseRevenueFileAndAggregateByCompanyAndDate(
			 	String fileName,
	    		Map<CampaignDateKey, AggregatedRecord> map) throws IOException;
	 
}
