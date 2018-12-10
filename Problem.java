import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Problem extends Application {

	TextField obj = new TextField();
	TextArea con = new TextArea();
	ComboBox<String> m = new ComboBox();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11, 12, 13, 14));
		pane.setHgap(5);
		pane.setVgap(5);
        pane.setStyle("-fx-background-color: transparent;");  
		
		Text t1 = new Text("What type of problem is this?"); 
		t1.setFill(Color.STEELBLUE);
	    t1.setStyle("-fx-font: 16 consolas");
		pane.add(t1, 0, 0);
		
		m.getItems().addAll("Maximum");
		pane.add(m, 0,  1);
		
		Text t2 = new Text("Enter the objective function:");
		t2.setFill(Color.STEELBLUE);
	    t2.setStyle("-fx-font: 16 consolas");
		pane.add(t2, 0, 2);

	    Text t3 = new Text("Please use variable names without numbers.");
		t3.setFill(Color.SLATEGRAY);
	    t3.setStyle("-fx-font: 10 consolas");
		pane.add(t3, 0, 3);

		obj.setPrefWidth(400);
		pane.add(obj, 0, 4);
		
		Text t4 = new Text("Enter the constraints: ");
		t4.setFill(Color.STEELBLUE);
	    t4.setStyle("-fx-font: 16 consolas");
		pane.add(t4, 0, 5);
		
		Text t5 = new Text("Please separate the constraints with new lines and include all coefficients (even 0 and 1)."
				+ "\nPlease enter all constraints with variables in the same order as in the objective function."
				+ "\nAll variables are assumed to be positive as it is required for the Simplex method.");
		t5.setFill(Color.SLATEGRAY);
	    t5.setStyle("-fx-font: 10 consolas");
		pane.add(t5, 0, 6);
		
		con.setPrefSize(400, 200);
		pane.add(con, 0, 7);
		
		Button solve = new Button("Solve Problem");
		solve.setOnAction(e -> solution());
		pane.add(solve,  0,  8);

		Scene scene = new Scene(pane, 600, 400, Color.FLORALWHITE);
		stage.setTitle("Enter Problem");
		stage.setScene(scene);
		stage.show();
	}

	public void solution() {
		while (obj.getText().equals("")) {
			obj.setText("Please enter an objective function");
		}
		while (con.getText().equals("")) {
			con.setText("Please enter the constraints");
		}
		String o = obj.getText();
		String c = con.getText();
		try {
			Simplex.calculateSolution(o, c);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Solution();
	}

}
