package com.campaignreport.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;

public interface CampaignParser {
	 void parseCostFileAndAggregateByCompanyAndDate(
			 	String fileName,
	    		Map<CampaignDateKey, AggregatedRecord> map) throws IOException;
	 void parseRevenueFileAndAggregateByCompanyAndDate(
			 	String fileName,
	    		Map<CampaignDateKey, AggregatedRecord> map) throws IOException;
	 
}
