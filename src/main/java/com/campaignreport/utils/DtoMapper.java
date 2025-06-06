package com.campaignreport.utils;

import com.campaignreport.dto.response.AggregatedRecordDto;
import com.campaignreport.model.AggregatedRecord;

public class DtoMapper {
	
	public static AggregatedRecordDto AggregatedRecordsEntityToDtoMapper(AggregatedRecord entity) {
		return new AggregatedRecordDto(
				entity.getDate(),
				entity.getCampaignId(),
				entity.getCampaignName(),
				entity.getTotalClicks(),
				entity.getTotalCost(),
				entity.getTotalRevenue(),
				entity.getUv(),
				entity.getCpc(),
				entity.getRoi(),
				entity.getProfit());
	}
}
