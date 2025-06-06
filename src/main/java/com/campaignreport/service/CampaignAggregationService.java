package com.campaignreport.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.campaignreport.dto.response.AggregatedRecordDto;
import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;

public interface CampaignAggregationService {
	void enrichAggregatedData(Map<CampaignDateKey, AggregatedRecord> map);

	Map<LocalDate, List<AggregatedRecord>> groupByDate(
			Map<CampaignDateKey, AggregatedRecord> campaignMap);

	List<AggregatedRecordDto> recordsByDate(LocalDate dateFrom, LocalDate dateTo);
}
