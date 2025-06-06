package com.campaignreport.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class S3Properties {
	
	@Value("${COSTS_DOWNLOAD_PATH: }")
	private String costDownloadPath;
	
	@Value("${REVENUE_DOWNLOAD_PATH: }")
	private String revenueDownloadPath;
}
