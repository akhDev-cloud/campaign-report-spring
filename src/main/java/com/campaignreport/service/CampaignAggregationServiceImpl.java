package com.campaignreport.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.campaignreport.dto.response.AggregatedRecordDto;
import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;
import com.campaignreport.repo.CampaignDataRepository;
import com.campaignreport.utils.DtoMapper;

@Service
public class CampaignAggregationServiceImpl implements CampaignAggregationService{

	@Override
	public void enrichAggregatedData(Map<CampaignDateKey, AggregatedRecord> map) {
		for (AggregatedRecord record : map.values()) {
            int clicks = record.getTotalClicks();
            double revenue = record.getTotalRevenue();
            double cost = record.getTotalCost();

            double uv = clicks != 0 ? revenue / clicks : 0;
            double cpc = clicks != 0 ? cost / clicks : 0;
            double roi = cpc != 0 ? uv / cpc : 0;
            double profit = revenue - cost;

            record.setUv(uv);
            record.setCpc(cpc);
            record.setRoi(roi);
            record.setProfit(profit);
        }
		
	}

	@Override
	public Map<LocalDate, List<AggregatedRecord>> groupByDate(Map<CampaignDateKey, AggregatedRecord> campaignMap) {
		TreeMap<LocalDate, List<AggregatedRecord>> result = new TreeMap<>();
	    for (AggregatedRecord record : campaignMap.values()) {
	        result.computeIfAbsent(record.getDate(), d -> new ArrayList<>()).add(record);
	    }
	    return result;
	}

	@Override
	public List<AggregatedRecordDto> recordsByDate(LocalDate dateFrom, LocalDate dateTo) {
		
		return CampaignDataRepository.dateMap.subMap(dateFrom, dateTo)
				.values()
				.stream()
				.flatMap(List::stream)
				.map(DtoMapper::AggregatedRecordsEntityToDtoMapper)
				.collect(Collectors.toList());
	}

}
