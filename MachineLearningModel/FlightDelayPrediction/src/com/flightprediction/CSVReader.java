package com.flightprediction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	String csvFile;

	ArrayList<Dataset> readCSV() {
		csvFile = "C:/Users/sagar/Desktop/sagar3.csv";
		String line = "";
		String cvsSplitBy = ",";

		ArrayList<Dataset> TrainigSet = new ArrayList<Dataset>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				// comma is a separator
				String[] row = line.split(cvsSplitBy);
				TrainigSet.add(new Dataset(Integer.parseInt(row[0]), Integer.parseInt(row[1])));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return TrainigSet;
	}

}