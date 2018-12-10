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

public class Start extends Application {

	public static int NUMVARS;
	public static boolean MAX;
	public static boolean MIN;
	static TextField numVars = new TextField();
	ComboBox<String> type = new ComboBox();
	
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

		Text t1 = new Text("Which problem do you want to solve?");
		t1.setFill(Color.STEELBLUE);
	    t1.setStyle("-fx-font: 16 consolas");
		pane.add(t1, 0, 0);
		
		/** To potentially implement later: LP with Big M method, Transportation problem with Northwest Corner method, 
	    	Transportation problem with Minimum Cost method **/
		type.getItems().addAll("LP with Simplex Method");
		pane.add(type, 0, 1);
		
		Text t2 = new Text("How many decision variables do you have?");
		t2.setFill(Color.STEELBLUE);
	    t2.setStyle("-fx-font: 16 consolas");
		pane.add(t2, 0, 2);
		
		numVars.setPrefWidth(100);
		pane.add(numVars, 0, 3);
		
		Button start = new Button("Start");
		start.setOnAction(e -> startActions());
		pane.add(start,  0,  6);

		Scene scene = new Scene(pane, 600, 400, Color.FLORALWHITE);
		stage.setTitle("Start");
		stage.setScene(scene);
		stage.show();
	}
	
	public void startActions() {
		while (numVars.getText().equals("")) {
			numVars.setText("Please enter the number of decision variables");
		}
		
		NUMVARS = Integer.parseInt(numVars.getText());
		try {
				new Problem().start(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

}

