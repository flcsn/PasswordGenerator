package passwordGenerator;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class PasswordGenerator extends Application{
	
	public void start(Stage window) {
		VBox layout = new VBox();
		GridPane grid = new GridPane();
		
		Label lengthText = new Label("Length: ");
		ComboBox length = new ComboBox();
		for (int i = 6; i <= 50; i++) {
			length.getItems().add(i);
		}
		Label symbolsText = new Label("Symbols: ");
		CheckBox symbols = new CheckBox("(e.g. !\";#$%&'()*+,-./:;<=>?@[]^_`{|}~)");
		Label numbersText = new Label("Numbers: ");
		CheckBox numbers = new CheckBox("(e.g. 0123456789)");
		Label lowercaseText = new Label("Lowercase Characters: ");
		CheckBox lowercase = new CheckBox("(e.g. a-z)");
		Label uppercaseText = new Label("Uppercase Characters: ");
		CheckBox uppercase = new CheckBox("e.g. A-Z");
		
		grid.add(lengthText, 0, 0);
		grid.add(length, 1, 0);
		grid.add(symbolsText, 0, 1);
		grid.add(symbols, 1, 1);
		grid.add(numbersText, 0, 2);
		grid.add(numbers, 1, 2);
		grid.add(lowercaseText, 0, 3);
		grid.add(lowercase, 1, 3);
		grid.add(uppercaseText, 0, 4);
		grid.add(uppercase, 1, 4);
		
		BorderPane pane = new BorderPane();
		
		Button generatePassword = new Button("Generate Password");
		
		HBox hBox = new HBox();
		Label passwordText = new Label("Password: ");
		TextField password = new TextField("sample password");
		password.setEditable(false);
		
		hBox.getChildren().addAll(passwordText, password);
		hBox.setSpacing(50);
		
		pane.setCenter(generatePassword);
		pane.setBottom(hBox);
		
		layout.getChildren().add(grid);
		layout.getChildren().add(pane);
		
		Scene view = new Scene(layout);
		window.setScene(view);
		window.setTitle("Password Generator");
		window.show();
	}

	public static void main(String[] args) {
		launch(PasswordGenerator.class);
	}

}
