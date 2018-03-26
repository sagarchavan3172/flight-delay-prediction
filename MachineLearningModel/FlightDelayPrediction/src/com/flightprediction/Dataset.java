package com.flightprediction;

public class Dataset {

	int flightDelayInMinutes;
	int DepartureTime;
	int closestCluster;

	Dataset() {
		flightDelayInMinutes = -1;
		DepartureTime = -1;
		closestCluster = -1;
	}

	public Dataset(int TimeInHHMM, int DelayInMinutes, int closestClusterIndex) {

		this.DepartureTime = TimeInHHMM;
		this.flightDelayInMinutes = DelayInMinutes;
		this.closestCluster = closestClusterIndex;
	}

	public Dataset(int TimeInHHMM, int DelayInMinutes) {

		this.DepartureTime = TimeInHHMM;
		this.flightDelayInMinutes = DelayInMinutes;
		this.closestCluster = -1;
	}

	public String toString() {
		return "flightDelayInMinutes =" + flightDelayInMinutes + " , DepartureTime =" + DepartureTime;

	}
}
