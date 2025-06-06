package com.campaignreport.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AggregatedRecordDto {
	
	private LocalDate date;
    private String campaignId;
    private String campaignName;
    private int totalClicks;
    private double totalCost;
    private double totalRevenue;
    
    private double uv;     
    private double cpc;    
    private double roi;    
    private double profit;
}
