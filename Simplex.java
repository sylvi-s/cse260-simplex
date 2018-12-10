import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;

import javafx.stage.Stage;

public class Simplex {
	
	public static int NUMCONS;
	public static int ROWS;
	public static int COLS;
	public static String[] CONLIST = new String[NUMCONS];
	public static double[][] MATRIX = new double[ROWS][COLS];
	public static int ITERATIONS = 0;
	public static double OBJ;

	public static void main(String[] args) {

	}
	
	public static void calculateSolution(String o, String c) throws IOException {
		
		fillMatrix(o, c);
		File file = new File("tableaus.txt");
		if (file.exists()) {
			file.delete();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		//displayMatrix(MATRIX);
		bw.append(saveMatrix());
		
		while (!checkOptimal()) {
			int pivotVar = enteringVar();
			int pivotRow = enteringRow(pivotVar);
			//System.out.println(pivotVar+ " " + pivotRow);
			
			MATRIX = pivotMatrix(pivotVar, pivotRow);
			String pivotInfo = "\nThe pivot variable is x" + pivotVar + " and the pivot row is row " + pivotRow + "\n"; 
			bw.append(pivotInfo);
			//displayMatrix(MATRIX);
			bw.append(saveMatrix());
			
			ITERATIONS++;
		}
		bw.close();
		OBJ = MATRIX[0][COLS-1];
		try {
			new Optimal().start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static double[][] pivotMatrix(int enteringVarIndex, int pivotRowIndex) {
		double[] column = getColumn(enteringVarIndex);
		double pivotTerm = MATRIX[pivotRowIndex][enteringVarIndex];
		if (pivotTerm != 1) {
			MATRIX[pivotRowIndex] = divide(MATRIX[pivotRowIndex],pivotTerm);
		}
		
		for (int i=0; i<column.length; i++) {
			if (i != pivotRowIndex) {
				double coef = column[i];
				for (int j=0; j<MATRIX[0].length; j++) {
					MATRIX[i][j] = MATRIX[i][j] - coef*MATRIX[pivotRowIndex][j];
				}
			}	
		}
		return MATRIX;	
	}
	
	private static double[] divide(double[] a, double d) {
		double[] newA = new double[a.length];
		for (int i=0; i<a.length; i++) {
			newA[i] = a[i]/d;
		}
		return newA;
	}

	private static int enteringVar() {
	    int index = 0;
	    double min = MATRIX[0][0];
		for (int i=0; i<MATRIX[0].length-1; i++) {
			if (MATRIX[0][i] < min) {
				min = MATRIX[0][i];	
				index = i;
			}
		}
		return index;
	}
	
	private static int enteringRow(int enteringVarIndex) {
		int row = 1;
		double minRatio = 1E10;
		for (int i=0; i<MATRIX.length; i++) {
			if (MATRIX[i][enteringVarIndex] > 0) {
				double ratio = MATRIX[i][MATRIX[0].length-1]/MATRIX[i][enteringVarIndex];
				if (ratio < minRatio) {
					minRatio = ratio;
					row = i;
				}
			}
		}
		return row;
	}
	
	private static boolean checkOptimal() {
	    boolean optimal = false;
		int vars = 0;

		for (int i = 0; i < COLS - 1; i++) {
			double val = MATRIX[0][i];
			if (val >= 0) {
				vars++;
			}
		}

		if (vars == COLS - 1) {
			optimal = true;
		}

		return optimal;
	}
	
	public static double[] getColumn(int index) {
		double[] column = new double[MATRIX.length];
		for (int i=0; i < column.length; i++) {
			column[i] = MATRIX[i][index];
		}
		return column;
	}

	private static void fillMatrix(String o, String c) {
		o = o.replace("-", "+-");
		c = c.replace("-", "+-");
		
		CONLIST = c.split("\\r?\\n");
		NUMCONS = CONLIST.length;
		
		COLS = 1 + Start.NUMVARS + NUMCONS + 1;
		ROWS = 1 + NUMCONS;
		MATRIX = new double[ROWS][COLS];
		
		String[] objSplit = o.split("(?<=[+*/])|(?=[+*/])");
		
		String[] objTrim = new String[Start.NUMVARS];
		
		int j=0;
		for (int i=0; i<objSplit.length; i++) {
			String temp = objSplit[i].replaceAll("[^\\d.-]",  "");
			if(!temp.equals("")) {
				objTrim[j] = temp;
				j++;
			}
		}
		
		MATRIX[0][0] = 1.0;
		for (int i=0; i<objTrim.length; i++) {
			MATRIX[0][i+1] = -Double.parseDouble(objTrim[i]);
		}
		MATRIX[0][COLS-1] = 0;		

		for (int i=0; i<CONLIST.length; i++) {
			String[] conSplit = CONLIST[i].split("(?<=[[<=]+*/])|(?=[[<=]+*/])");

			String[] conTrim = new String[Start.NUMVARS+1];
			int l=0;
			for (int k=0; k < conSplit.length; k++) {
				String temp = conSplit[k].replaceAll("[^\\d.-]",  "");
				if(!temp.equals("")) {
					conTrim[l] = temp;
					l++;
				}
			}
			
			for (int n=0; n<conTrim.length-1; n++) {
				MATRIX[i+1][n+1] = Double.parseDouble(conTrim[n]);
			}
			MATRIX[i+1][COLS-1] = Double.parseDouble(conTrim[conTrim.length-1]);
		}
		
		for (int i=0; i<NUMCONS; i++) {
			MATRIX[i+1][1+Start.NUMVARS+i] = 1;
		}
		
	}
	
	private static void displayMatrix(double[][] matrix) {
		System.out.print("z");
		for (int k=1; k<=Start.NUMVARS; k++) {
			System.out.print("	x"+k);
		}
		for (int k=1; k<=NUMCONS; k++) {
			System.out.print("	s"+k);
		}
		System.out.print("	RHS");
		System.out.println();
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				System.out.print(MATRIX[i][j] + " 	");
			}
			System.out.println();
		}
	}
	
	private static String saveMatrix() throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("z");
		for (int k=1; k<=Start.NUMVARS; k++) {
			builder.append("		x"+k);
		}
		for (int k=1; k<=NUMCONS; k++) {
			builder.append("		s"+k);
		}
		builder.append("		RHS \n");
		
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLS; j++) {
				String temp = String.valueOf(Math.round(MATRIX[i][j]*100.0)/100.0);
				//String temp = String.format("%.2f", MATRIX[i][j]);
				builder.append(temp + "     	");
				//builder.append(MATRIX[i][j] + " 	");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

}
