package passwordGenerator;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class PasswordGenerator extends Application{
	
	public void start(Stage window) {
		VBox layout = new VBox();
		layout.setPrefSize(400, 400);
		GridPane grid = new GridPane();
		
		Label lengthText = new Label("Length: ");
		ComboBox length = new ComboBox();
		for (int i = 6; i <= 50; i++) {
			length.getItems().add(i);
		}
		Label symbolsText = new Label("Symbols: ");
		CheckBox symbols = new CheckBox("(!@#$%^&*;:,.<>-_+=)");
		Label numbersText = new Label("Numbers: ");
		CheckBox numbers = new CheckBox("(e.g. 0123456789)");
		Label lowercaseText = new Label("Lowercase Characters: ");
		CheckBox lowercase = new CheckBox("(e.g. a-z)");
		Label uppercaseText = new Label("Uppercase Characters: ");
		CheckBox uppercase = new CheckBox("(e.g. A-Z)");
		Label message = new Label();
		
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
		grid.add(message, 1, 5);
		
		BorderPane pane = new BorderPane();
		
		Button generatePassword = new Button("Generate Password");
			
		HBox hBox = new HBox();
		Label passwordText = new Label("Password: ");
		TextField password = new TextField();
		Button copyPassword = new Button("Copy");
		password.setEditable(false);
		
		hBox.getChildren().addAll(passwordText, password, copyPassword);
		
		pane.setCenter(generatePassword);
		pane.setBottom(hBox);
		
		layout.getChildren().add(grid);
		layout.getChildren().add(pane);
		
		generatePassword.setOnAction(event -> {
			message.setText("");
			password.setText("");
			if (length.getValue() == null) {
				message.setText("Please select a password length");
				return;
			}
			int passwordLength = (int) length.getValue();

			boolean[] passwordQualities = new boolean[4];
			passwordQualities[0] = symbols.isSelected();
			passwordQualities[1] = numbers.isSelected();
			passwordQualities[2] = lowercase.isSelected();
			passwordQualities[3] = uppercase.isSelected();
			if (!atLeastOneTrue(passwordQualities)) {
				message.setText("At least one checkbox must be ticked");
				return;
			}
			
			String characters = buildCharactersToMakePasswordWith(passwordQualities);
			String generatedPassword = newPassword(passwordLength, characters);
			while (!passwordContainsCharactersRequested(generatedPassword, passwordQualities)) {
				generatedPassword = newPassword(passwordLength, characters);
			}
			password.setText(generatedPassword);
		});
		
		copyPassword.setOnAction(event -> {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putString(password.getText());
			clipboard.setContent(content);
		});
		
		Scene view = new Scene(layout, 600, 300);
		window.setScene(view);
		window.setTitle("Password Generator");
		window.show();
	}

	public static void main(String[] args) {
		launch(PasswordGenerator.class);
	}
	
	public static String buildCharactersToMakePasswordWith(boolean[] qualities) {
		StringBuilder charactersToMakePasswordWith = new StringBuilder();
		
		if (qualities[0] == true) {
			charactersToMakePasswordWith.append("!@#$%^&*;:,.<>-_+=");
		}
		if (qualities[1] == true) {
			charactersToMakePasswordWith.append("0123456789");
		}
		if (qualities[2] == true) {
			charactersToMakePasswordWith.append("abcdefghijklmnopqrstuvwxyz");
		}
		if (qualities[3] == true) {
			charactersToMakePasswordWith.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		}
		
		return charactersToMakePasswordWith.toString();
	}
	
	public static String newPassword(int length, String charactersToBuildWith) {
		StringBuilder password = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			password.append(charactersToBuildWith.charAt(random.nextInt(charactersToBuildWith.length())));
		}
		
		return password.toString();
	}
	
	public static boolean atLeastOneTrue(boolean[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == true) return true;
		}
		return false;
	}
	
	public static boolean passwordContainsCharactersRequested(String password, boolean[] qualities) {
		if (qualities[0] == true) {
			Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
			Matcher matcher = pattern.matcher(password);
			if (!matcher.find()) {
				return false;
			}
		}
		if (qualities[1] == true) {
			Pattern pattern = Pattern.compile("[0-9]");
			Matcher matcher = pattern.matcher(password);
			if (!matcher.find()) {
				return false;
			}			
		}
		if (qualities[2] == true) {
			Pattern pattern = Pattern.compile("[a-z]");
			Matcher matcher = pattern.matcher(password);
			if (!matcher.find()) {
				return false;
			}			
		}
		if (qualities[3] == true) {
			Pattern pattern = Pattern.compile("[A-Z]");
			Matcher matcher = pattern.matcher(password);
			if (!matcher.find()) {
				return false;
			}			
		}
		return true;
	}

}
