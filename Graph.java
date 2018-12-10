import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Graph extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11, 12, 13, 14));
		pane.setHgap(5);
		pane.setVgap(5);
        pane.setStyle("-fx-background-color: transparent;");  
		
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(1.5*maxVal(Simplex.MATRIX));
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1.5*maxVal(Simplex.MATRIX));
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
       
        lineChart.setTitle("Graphical Representation of LP Constraints");
        
        for (int j=1; j<=Simplex.NUMCONS; j++) {
    		String[] conSplit = Simplex.CONLIST[j-1].split("(?<=[[<=]+*/])|(?=[[<=]+*/])");
        	String[] conTrim = new String[3];
    		int l=0;
    		for (int k=0; k < conSplit.length; k++) {
    			String temp = conSplit[k].replaceAll("[^\\d.-]",  "");
    			if(!temp.equals("")) {
    				conTrim[l] = temp;
    				l++;
    			}
    		}
    		double[] con = new double[conTrim.length];
    		for (int i=0; i<conTrim.length; i++) {
    			con[i] = Double.parseDouble(conTrim[i]);
    		}
    		
    		XYChart.Series s = new XYChart.Series();
    	    s.setName("constraint "+j);

    		
    		if (conTrim[0].equals("0")) {
    	        s.getData().add(new XYChart.Data(0, con[2]));
    		    s.getData().add(new XYChart.Data(20, con[2]));
    		    s.getData().add(new XYChart.Data(40, con[2]));
    		    s.getData().add(new XYChart.Data(60, con[2]));
    		    s.getData().add(new XYChart.Data(80, con[2]));
    		    s.getData().add(new XYChart.Data(100, con[2]));
    		    s.getData().add(new XYChart.Data(120, con[2]));
    		    s.getData().add(new XYChart.Data(140, con[2]));
    		    s.getData().add(new XYChart.Data(160, con[2]));
    		    s.getData().add(new XYChart.Data(180, con[2]));
    		    s.getData().add(new XYChart.Data(200, con[2]));
    		    s.getData().add(new XYChart.Data(220, con[2]));
    		}
    		else if (conTrim[1].equals("0")) {
    		    s.getData().add(new XYChart.Data(con[2], 0));
    		    s.getData().add(new XYChart.Data(con[2], 20));
    		    s.getData().add(new XYChart.Data(con[2], 40));
    		    s.getData().add(new XYChart.Data(con[2], 60));
    		    s.getData().add(new XYChart.Data(con[2], 80));
    		    s.getData().add(new XYChart.Data(con[2], 100));
    		    s.getData().add(new XYChart.Data(con[2], 120));
    		    s.getData().add(new XYChart.Data(con[2], 140));
    		    s.getData().add(new XYChart.Data(con[2], 160));
    		    s.getData().add(new XYChart.Data(con[2], 180));
    		    s.getData().add(new XYChart.Data(con[2], 200));
    		    s.getData().add(new XYChart.Data(con[2], 220));
    		    
    		}
    		else {
    			for (int i=0; i<240; i = i+20) {
    				s.getData().add(new XYChart.Data(i, (con[2]-i)/con[1] ));
    			}
    		}
    		lineChart.getData().add(s);
        }
        pane.add(lineChart, 0, 0);

        
		Button back = new Button("Back to Solution");
        back.setOnAction(e -> backAction());
		pane.add(back, 0, 1);

        Scene scene  = new Scene(pane, 600, 600, Color.FLORALWHITE);       
        
        stage.setTitle("Graph");
        stage.setScene(scene);
        stage.show();
    }

	private void backAction() {
		try {
			new Optimal().start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static double maxVal(double[][] m) {
        double max = m[0][0];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] > max) {
                    max = m[i][j];
                }
            }
        }
        return max;
    }

}
