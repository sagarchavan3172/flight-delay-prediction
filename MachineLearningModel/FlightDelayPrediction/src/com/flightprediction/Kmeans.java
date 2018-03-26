package com.flightprediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Kmeans {

	HashMap<Integer, Point> centroids;
	int k; // number of clusters
	HashMap<Integer, Cluster> cluster;

	static class Point {
		double x;
		double y;

		Point() {
			x = y = 0;
		}

		Point(int x, int y) {
			this.x = x;
			this.y = y;

		}

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return x + "," + y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
	}

	Kmeans() {

		centroids = new HashMap<Integer, Point>();
		cluster = new HashMap<Integer, Cluster>();

		k = 3;
		for (int i = 1; i <= k; i++) {
			int x, y;
			x = new Random().nextInt(2400);
			y = new Random().nextInt(30);
			centroids.put(i, new Point(x, y));
			cluster.put(i, new Cluster(x, y));
		}

	}

	public Kmeans(ArrayList<Dataset> trainigSet) {
		centroids = new HashMap<Integer, Point>();
		cluster = new HashMap<Integer, Cluster>();

		k = 3;

		for (int i = 1; i <= k; i++) {
			int x, y, index;
			index = new Random().nextInt(trainigSet.size());
			x = trainigSet.get(index).DepartureTime;
			y = trainigSet.get(index).flightDelayInMinutes;
			centroids.put(i, new Point(x, y));
			cluster.put(i, new Cluster(x, y));
		}
	}

	ArrayList<Dataset> findDistance(ArrayList<Dataset> trainingset) {

		double centroidX, centroidY;
		int elementX, elementY, index = 0;
		double distance;

		double minDistance;
		int closestClusterIndex = -1;
		Iterator<Dataset> iterator = trainingset.iterator();
		while (iterator.hasNext()) {

			Dataset element = iterator.next();

			minDistance = 99999999;

			elementX = element.DepartureTime;
			elementY = element.flightDelayInMinutes;

			for (int i = 1; i <= k; i++) {

				centroidX = centroids.get(i).x;
				centroidY = centroids.get(i).y;

				distance = Math.sqrt(Math.pow(centroidX - elementX, 2) + Math.pow(centroidY - elementY, 2));

				if (distance <= minDistance) {
					minDistance = distance;
					closestClusterIndex = i;
				}
			}

			Dataset elementWithClosestCluster = new Dataset(elementX, elementY, closestClusterIndex);
			if (trainingset.get(index).closestCluster != closestClusterIndex) {
				trainingset.set(index, elementWithClosestCluster);
				// element.closestCluster = closestClusterIndex;
				Cluster closestCluster = cluster.get(closestClusterIndex);
				closestCluster.clusterPoints.add(elementWithClosestCluster);
			}
			index++;

		}
		return trainingset;

	}

	public static Point mean(Cluster cluster) {
		double sumOfDepartureTime = 0, sumOfDepartureDelay = 0;
		for (int i = 0; i < cluster.clusterPoints.size(); i++) {
			sumOfDepartureTime += cluster.clusterPoints.get(i).DepartureTime;
			sumOfDepartureDelay += cluster.clusterPoints.get(i).flightDelayInMinutes;
		}
		return new Point(sumOfDepartureTime / cluster.clusterPoints.size(),
				sumOfDepartureDelay / cluster.clusterPoints.size());
	}

	boolean updateCentroids(ArrayList<Dataset> trainigSet) {
		boolean centroidChange = false;
		HashMap<Integer, Point> oldCentroids = (HashMap) centroids.clone();

		for (int i = 1; i <= k; i++) {

			centroids.put(i, Kmeans.mean(cluster.get(i)));
		}

		for (int i = 1; i <= k; i++) {

			if (oldCentroids.get(i).x != centroids.get(i).x) {
				centroidChange = true;
				break;
			}
		}
		return centroidChange;
	}

}
