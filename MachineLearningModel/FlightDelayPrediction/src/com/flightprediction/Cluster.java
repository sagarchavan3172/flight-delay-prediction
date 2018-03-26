package com.flightprediction;

import java.util.ArrayList;

public class Cluster {

	ArrayList<Dataset> clusterPoints;
	int centroidX;
	int centroidY;

	class ClusterPoint {
		int departureTimeHHMM;

		int delayInMinutes;
		int closestCentroid;

		ClusterPoint() {
			departureTimeHHMM = 0;
			delayInMinutes = 0;
			closestCentroid = 0;

		}
	}

	Cluster() {

		clusterPoints = new ArrayList<Dataset>();
		centroidX = 0;
		centroidY = 0;

	}

	Cluster(int x, int y) {

		clusterPoints = new ArrayList<Dataset>();
		centroidX = x;
		centroidY = y;

	}

}
