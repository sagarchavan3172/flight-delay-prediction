package com.flightprediction;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import com.flightprediction.Kmeans.Point;

public class Graph extends JFrame {

	Graph() {
		
		

	}

	public Graph(ArrayList<Dataset> trainigSet) {
		super("Clusters");
		setBounds(0,0,1500,1000);
		Points points = new Points(trainigSet);
		add(points);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Graph(ArrayList<Dataset> trainigSet, HashMap<Integer, Point> centroids) {
		super("Clusters");
		setBounds(0,0,1500,1000);
		Points points = new Points(trainigSet,centroids);
		add(points);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
