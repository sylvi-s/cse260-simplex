import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

import javafx.stage.Stage;

public class Tableaus extends Application {

	public static void main(String[] args) throws NumberFormatException, IOException {
		launch(args);
	}

	public void start(Stage stage) throws IOException {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(11, 12, 13, 14));
		pane.setHgap(5);
		pane.setVgap(5);
        pane.setStyle("-fx-background-color: transparent;");  

        Text t1 = new Text("Here are the intermediate tableaus: ");
		pane.add(t1, 0, 0);
		
		Text t = new Text(displayTableaus());
		t1.setFill(Color.STEELBLUE);
	    t1.setStyle("-fx-font: 12 consolas");
		pane.add(t, 0, 1);
		
		Button back = new Button("Back to Solution");
		back.setOnAction(e -> backAction());
		pane.add(back,  0,  2);
		
		Scene scene = new Scene(pane, 600, 600, Color.FLORALWHITE);
		stage.setTitle("Tableaus");
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

	private static String displayTableaus() throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader("tableaus.txt")); 
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
		    sb.append(System.lineSeparator());
		    line = br.readLine();
		}
				
		return sb.toString();
	}
}
