package com.flightprediction;

import java.util.ArrayList;

public class LinearRegression {

	// Cluster cluster;
	double[][] normalized;

	LinearRegression(int size) {
		// this.cluster = cluster;
		normalized = new double[size][1];
	}

	void normalization(Cluster cluster) {

		int maxX = findHighestDepartureTime(cluster.clusterPoints);
		int minX = findLowestDepartureTime(cluster.clusterPoints);
		int index = 0;

		for (Dataset clusterPoint : cluster.clusterPoints) {

			normalized[index++][0] = (double) (clusterPoint.DepartureTime - maxX) / (maxX - minX);

		}

	}

	double costFunction(Matrix features, Matrix delayMatrix, Matrix parameterMatrix) {

		double two = 2.0;

		Matrix xMinusyTranspose = features.times(parameterMatrix).minus(delayMatrix).transpose();
		Matrix xMinusy = features.times(parameterMatrix).minus(delayMatrix);

		double value = (double) xMinusyTranspose.times(xMinusy).get(0, 0);

		return value / (two * (double) delayMatrix.getRowDimension());
	}

	int findHighestDepartureTime(ArrayList<Dataset> clusterPoints) {

		int max = clusterPoints.get(0).DepartureTime;
		for (int ktr = 0; ktr < clusterPoints.size(); ktr++) {
			if (clusterPoints.get(ktr).DepartureTime > max) {
				max = clusterPoints.get(ktr).DepartureTime;
			}
		}
		return max;

	}

	int findLowestDepartureTime(ArrayList<Dataset> clusterPoints) {

		int min = clusterPoints.get(0).DepartureTime;
		for (int ktr = 0; ktr < clusterPoints.size(); ktr++) {
			if (clusterPoints.get(ktr).DepartureTime < min) {
				min = clusterPoints.get(ktr).DepartureTime;
			}
		}
		return min;

	}

	double gradientDescent(Cluster cluster, double inputDepartureTime) {

		normalization(cluster);

		// create matrix of 1 ;

		Matrix matrixOfOnes = new Matrix(cluster.clusterPoints.size(), 1, 1.0);
		Matrix clusterPoints = new Matrix(normalized);

		Matrix features = clusterPoints.mergeMatrix(matrixOfOnes);
		int index = 0;
		double[][] delay = new double[cluster.clusterPoints.size()][1];

		for (Dataset clusterPoint : cluster.clusterPoints) {

			delay[index++][0] = (double) clusterPoint.flightDelayInMinutes;

		}
		Matrix delayMatrix = new Matrix(delay);

		double[][] parameter = new double[2][1];
		parameter[0][0] = 0.0;
		parameter[1][0] = 0.0;

		Matrix parameterMatrix = new Matrix(parameter);

		double learningRate = 0.1;
		int repetition = 2000;

		double m = (double) cluster.clusterPoints.size();
		ArrayList<Double> costHistory = new ArrayList<Double>();

		for (int i = 0; i < repetition; i++) {

			Matrix h = features.times(parameterMatrix).minus(delayMatrix).transpose();

			double onebym = (1 / m);

			// parameter[0][0] = parameter[0][0] - learningRate * (1 / m) *
			// (h.times(matrixOfOnes).get(0, 0));
			// parameter[1][0] = parameter[1][0] - learningRate * (1 / m) *
			// (h.times(clusterPoints).get(0, 0));

			double x1byh = h.times(matrixOfOnes).get(0, 0);
			parameter[0][0] = parameter[0][0] - (learningRate * onebym * x1byh);

			double x2byh = h.times(clusterPoints).get(0, 0);
			parameter[1][0] = parameter[1][0] - (learningRate * onebym * x2byh);

			parameterMatrix = new Matrix(parameter);

			costHistory.add(costFunction(features, delayMatrix, parameterMatrix));

		}

		int maxX = findHighestDepartureTime(cluster.clusterPoints);
		int minX = findLowestDepartureTime(cluster.clusterPoints);

		inputDepartureTime = (inputDepartureTime - (double)maxX) /(double) ((double)maxX - (double)minX);
		return parameter[0][0] + parameter[1][0] * inputDepartureTime;

	}

}
