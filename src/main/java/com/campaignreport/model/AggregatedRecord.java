package com.campaignreport.model;

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
public class AggregatedRecord {
	private LocalDate date;
    private String campaignId;
    private String campaignName;
    private int totalClicks;
    private double totalCost;
    private double totalRevenue;
	
    public AggregatedRecord(LocalDate date, String campaignId, double totalRevenue) {
		super();
		this.date = date;
		this.campaignId = campaignId;
		this.totalRevenue = totalRevenue;
	}

	public AggregatedRecord(LocalDate date, String campaignId, String campaignName, int totalClicks, double totalCost) {
		super();
		this.date = date;
		this.campaignId = campaignId;
		this.campaignName = campaignName;
		this.totalClicks = totalClicks;
		this.totalCost = totalCost;
	}
    
    
    
    
}
