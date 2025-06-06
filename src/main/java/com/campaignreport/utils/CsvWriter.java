package com.campaignreport.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.campaignreport.model.AggregatedRecord;
import com.campaignreport.model.CampaignDateKey;

public class CsvWriter {
	
	public static void writeMapToCsv(Map<CampaignDateKey, AggregatedRecord> data, String filePath) throws IOException {
		
		Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
		
        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("Key", "Value"))
        ) {
            for (Map.Entry<CampaignDateKey, AggregatedRecord> entry : data.entrySet()) {
                csvPrinter.printRecord(entry.getKey(), entry.getValue());
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
