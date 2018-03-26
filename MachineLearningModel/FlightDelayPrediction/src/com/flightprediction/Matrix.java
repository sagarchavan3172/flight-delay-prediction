package com.flightprediction;

public class Matrix {

	private double[][] A;
	private int m, n;

	public Matrix(int m, int n) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
	}

	public Matrix(int m, int n, double s) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = s;
			}
		}
	}

	public Matrix(double[][] A) {
		m = A.length;
		n = A[0].length;
		for (int i = 0; i < m; i++) {
			if (A[i].length != n) {
				throw new IllegalArgumentException("All rows must have the same length.");
			}
		}
		this.A = A;
	}

	public Matrix(double[][] A, int m, int n) {
		this.A = A;
		this.m = m;
		this.n = n;
	}

	public Matrix(double vals[], int m) {
		this.m = m;
		n = (m != 0 ? vals.length / m : 0);
		if (m * n != vals.length) {
			throw new IllegalArgumentException("Array length must be a multiple of m.");
		}
		A = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = vals[i + j * m];
			}
		}
	}

	public double[][] getArray() {
		return A;
	}

	public int getRowDimension() {
		return m;
	}

	public int getColumnDimension() {
		return n;
	}

	public double get(int i, int j) {
		return A[i][j];
	}

	public Matrix mergeMatrix(Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m, n + 1);
		// double[][] C = X.getArray();
		for (int i = 0; i < n + 1; i++) {
			for (int j = 0; j < m; j++) {
				if (i == 0)
					X.A[j][i] = B.A[j][0];
				else
					X.A[j][i] = this.A[j][0];
			}
		}
		return X;
	}

	public void set(int i, int j, double s) {
		A[i][j] = s;
	}

	public Matrix transpose() {
		Matrix X = new Matrix(n, m);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[j][i] = A[i][j];
			}
		}
		return X;
	}

	public Matrix plus(Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m, n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B.A[i][j];
			}
		}
		return X;
	}

	public Matrix minus(Matrix B) {
		checkMatrixDimensions(B);
		Matrix X = new Matrix(m, n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B.A[i][j];
			}
		}
		return X;
	}

	public Matrix arrayTimes(Matrix B) {

		Matrix X = new Matrix(m, B.n);
		double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < B.n; j++) {
				for (int k = 0; k < n; k++)
					C[i][j] += A[i][k] * B.A[k][j];
			}
		}
		return X;
	}



	public Matrix times(Matrix B) {
		if (B.m != n) {
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");
		}
		Matrix X = new Matrix(m, B.n);
		double[][] C = X.getArray();
		double[] Bcolj = new double[n];
		for (int j = 0; j < B.n; j++) {
			for (int k = 0; k < n; k++) {
				Bcolj[k] = B.A[k][j];
			}
			for (int i = 0; i < m; i++) {
				double[] Arowi = A[i];
				double s = 0;
				for (int k = 0; k < n; k++) {
					s += Arowi[k] * Bcolj[k];
				}
				C[i][j] = s;
			}
		}
		return X;
	}

	private void checkMatrixDimensions(Matrix B) {
		if (B.m != m || B.n != n) {
			throw new IllegalArgumentException("Matrix dimensions must agree.");
		}
	}

	private static final long serialVersionUID = 1;
}
