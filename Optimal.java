import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Optimal extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11, 12, 13, 14));
		pane.setHgap(5);
		pane.setVgap(5);
        pane.setStyle("-fx-background-color: transparent;");  

        Text t1 = new Text("The optimal solution was found! \nThe optimal solution was found after " + Simplex.ITERATIONS+ " iterations of the Simplex method.");
        t1.setFill(Color.STEELBLUE);
	    t1.setStyle("-fx-font: 12 consolas");
		pane.add(t1, 0, 0);
		
		String temp = String.valueOf(Math.round(Simplex.OBJ*100.0)/100.0);
		Text t2 = new Text("The optimal objective function value is " + temp);
		t2.setFill(Color.STEELBLUE);
	    t2.setStyle("-fx-font: 12 consolas");
		pane.add(t2, 0, 1);
		
		String vars = "The variables have the following values: \n";
				
		for (int i=1; i<=Start.NUMVARS; i++) {
			double[] col = Simplex.getColumn(i);
			for (int j=1; j<col.length; j++) {
				if (col[j] == 1.0) {
					String temp2 = String.valueOf(Math.round(Simplex.MATRIX[j][Simplex.COLS-1]*100.0)/100.0);
					vars += "x"+i+" = "+ temp2 + "\n";

				}
			}
		}
		
		Text t3 = new Text(vars);
		t3.setFill(Color.STEELBLUE);
	    t3.setStyle("-fx-font: 12 consolas");
		pane.add(t3, 0, 2);
		
		Button tableau = new Button("View Tableaus");
		tableau.setOnAction(e -> tableauAction());
		pane.add(tableau,  0,  3);

		if (Start.NUMVARS == 2) {
			Button start = new Button("View Graph");
			start.setOnAction(e -> graphAction());
			pane.add(start,  0,  4);
		}
		
		Scene scene = new Scene(pane, 600, 400, Color.FLORALWHITE);
		primaryStage.setTitle("Optimal Solution");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void tableauAction() {
		try {
			new Tableaus().start(new Stage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void graphAction() {
		// TODO Auto-generated method stub
		new Graph().start(new Stage());
	}
	

}
