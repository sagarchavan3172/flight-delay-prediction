package com.flightprediction;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

import com.flightprediction.Kmeans.Point;

public class Points extends JPanel {
	ArrayList<Dataset> data;
	HashMap<Integer, Point> centroids;
	final int PAD = 50;

	Points(ArrayList<Dataset> dataset) {
		data = dataset;
	}

	public Points(ArrayList<Dataset> trainigSet, HashMap<Integer, Point> centroids) {
		data = trainigSet;
		this.centroids = centroids;

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = getWidth();
		int h = getHeight();
		// y axix.

		// (0,0) = (PAD, h - PAD)
		g.drawLine(PAD, PAD, PAD, h - PAD);

		// x axis
		g.drawLine(PAD, h - PAD, w - PAD, h - PAD);

		int x = PAD - 30, y = h - PAD - 50;
		int linex1 = PAD - 5, liney1 = h - PAD - 50, linex2 = PAD + 5, liney2 = h - PAD - 50;

		// labels for y axis
		for (int i = 1; i <= 17; i++) {

			g.drawString(String.valueOf(i * 10), x, y);
			g.drawLine(linex1, liney1, linex2, liney2);
			y = y - 50;
			liney1 = liney1 - 50;
			liney2 = liney2 - 50;
		}

		// labels for x axis

		x = PAD + 50;
		y = h - 15;

		linex1 = PAD + 50;
		liney1 = h - 45;
		linex2 = PAD + 50;
		liney2 = h - 55;
		for (int i = 1; i <= 24; i++) {

			g.drawString(String.valueOf(i * 100), x, y);
			g.drawLine(linex1, liney1, linex2, liney2);
			x = x + 50;
			linex1 = linex1 + 50;
			linex2 = linex2 + 50;
		}

		Iterator<Dataset> iterator = data.iterator();
		while (iterator.hasNext()) {
			Dataset elementOfSet = iterator.next();
			double t = ((double) elementOfSet.flightDelayInMinutes) / 10.0;

			if (elementOfSet.closestCluster == -1)
				g.setColor(Color.BLUE);
			if (elementOfSet.closestCluster == 1)
				g.setColor(Color.BLUE);
			if (elementOfSet.closestCluster == 2)
				g.setColor(Color.GREEN);
			if (elementOfSet.closestCluster == 3)
				g.setColor(Color.YELLOW);

			g.fillOval(PAD + (elementOfSet.DepartureTime / 2), h - PAD - (int) (t * 50), 8, 8);

		}

		// centroid color is RED
		g.setColor(Color.RED);
		// plot centroid points
		Set clusterCentroids = centroids.entrySet();
		Iterator centroidsIterator = clusterCentroids.iterator();
		while (centroidsIterator.hasNext()) {
			Map.Entry centroid = (Map.Entry) centroidsIterator.next();

			String[] row = centroid.getValue().toString().split(",");
			double t = (Double.parseDouble(row[1])) / 10.0;

			// Double.parseDouble(arg0)

			g.fillOval(PAD + ((int) Double.parseDouble(row[0]) / 2), h - PAD - (int) (t * 50), 8, 8);
		}

	}

	public static void createAndShowGui(ArrayList<Dataset> TrainigSet) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new Points(TrainigSet));
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.setVisible(true);
	}
}