package com.campaignreport.repo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;
import com.campaignreport.service.CampaignAggregationService;




public class CampaignDataRepository {
	
	public static final Map<CampaignDateKey, AggregatedRecord> campaignMap = new HashMap<>();
    public static final TreeMap<LocalDate, List<AggregatedRecord>> dateMap = new TreeMap<>();
   
    
    public static void GroupDataByDate(CampaignAggregationService service) { 
        dateMap.putAll(service.groupByDate(campaignMap));
    }
}
