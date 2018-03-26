package com.flightprediction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {

		CSVReader reader = new CSVReader();

		ArrayList<Dataset> TrainigSet = reader.readCSV();

		Iterator<Dataset> iterator = TrainigSet.iterator();
		while (iterator.hasNext()) {
			Dataset elementOfSet = iterator.next();
			//System.out.println(elementOfSet);
		}

		// Graph.createAndShowGui(TrainigSet);
		System.out.println("Centroids");
		Kmeans model = new Kmeans(TrainigSet);

		Set centroids = model.centroids.entrySet();
		Iterator centroidsIterator = centroids.iterator();

		while (centroidsIterator.hasNext()) {
			Map.Entry centroid = (Map.Entry) centroidsIterator.next();
			//System.out.print("Key is: " + centroid.getKey() + " & Value is: ");
			//System.out.println(centroid.getValue());
		}

		JFrame graph = new Graph(TrainigSet, model.centroids);
		boolean centroidChnage = true;
		while (centroidChnage) {

			TrainigSet = model.findDistance(TrainigSet);
			centroidChnage = model.updateCentroids(TrainigSet);
		}

		double inputDepartureTime = 0;
		boolean q = true;
		Scanner sc = new Scanner(System.in);
		while (q) {

			System.out.println("Enter departure Time :");
			inputDepartureTime = sc.nextDouble();

			int closestCluster = 1;

			double minDistance = Math.abs(model.centroids.get(1).x - inputDepartureTime);

			for (int i = 2; i <= 3; i++) {

				if (Math.abs(model.centroids.get(i).x - inputDepartureTime) < minDistance) {
					closestCluster = i;
				}
			}

			LinearRegression regressionModel = new LinearRegression(
					model.cluster.get(closestCluster).clusterPoints.size());
			double output = regressionModel.gradientDescent(model.cluster.get(closestCluster), inputDepartureTime);
			System.out.println("Output : " + output);

			System.out.println("press 1 to quit..any other number to continue");
			if (sc.nextDouble() != 1)
				q = true;
			else
				q = false;
		}
		sc.close();
	}
}
