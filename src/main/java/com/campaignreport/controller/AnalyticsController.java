package com.campaignreport.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campaignreport.dto.response.AggregatedRecordDto;
import com.campaignreport.service.CampaignAggregationService;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnalyticsController {
	
	private final CampaignAggregationService aggregationService;
	
	@GetMapping("/records")
	ResponseEntity<List<AggregatedRecordDto>> recordsByDate(
			@RequestParam LocalDate dateFrom,
			@RequestParam LocalDate dateTo){
		
		List<AggregatedRecordDto> data = aggregationService.recordsByDate(dateFrom, dateTo);
		
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
}
