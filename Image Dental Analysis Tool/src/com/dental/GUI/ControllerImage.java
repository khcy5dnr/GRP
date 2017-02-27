package com.dental.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.HBox;

public class ControllerImage extends Main implements Initializable {

	@FXML
	private Slider sharpSlider; 
	
	@FXML
	private Slider brightSlider;
	
	@FXML
	Button btnEnhance = new Button();
		
	@FXML
	HBox Preview = new HBox();
	
	@FXML
	Button btnNext = new Button();
	
	@FXML
	MenuBar menuBar = new MenuBar();
	
	@FXML
	Menu file = new Menu("File");
	
	@FXML
	Menu help = new Menu("Help");
	
	@FXML
	MenuItem itmOpen = new MenuItem();
	
	@FXML
	MenuItem itmClose = new MenuItem();
	
	@FXML
	MenuItem itmExit = new MenuItem();
	
	@FXML
	MenuItem itmAbout = new MenuItem();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Listen for Sharpness Slider Value Changes
		sharpSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				System.out.println("Sharpness Slider Changed (newValue: " + newValue.intValue() + ")\n");
			}
		});
		
		//Listen for Brightness Slider Value Changes
		brightSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				System.out.println("Brightness Slider Changed (newValue: " + newValue.intValue() + ")\n");
			}
		});
	}	
	
		
	@FXML
	private void onClickOpen() throws IOException {		
		
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Choose image");
	    
	    // File format restrictions - .png, .jpg, .jpeg
	    chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg") );             		
	    File Selectedfile = chooser.showOpenDialog(new Stage());
	    
	    // Taking file and storing it as image
	    BufferedImage bimg = ImageIO.read(Selectedfile);
	    
	    // Getting dimensions of the image
	    int width          = bimg.getWidth();				
	    int height         = bimg.getHeight();
	    
	    // For developer reference
	    System.out.println("The width of the image is:" + width + "The height of the image is:" + height);
	    
	    // Check if the image size is acceptable
	    if(width>1366&&height>768||width>1366||height>768) {
	    	
	    	// Alert box appears when if statement is true 
	    	Alert alert = new Alert(AlertType.ERROR);   		
	    	alert.setTitle("Error");
	    	alert.setHeaderText("Uploaded Failed");
	    	alert.setContentText("Upload image less than 1.1 mbs");
	    	alert.showAndWait();
	    }
	    	
	    // If file is of appropriate dimensions run the following code
	   	else {
	    		
	   		// Converting buffered image into an Image so JavaFX methods work
	   		Image image = SwingFXUtils.toFXImage(bimg, null);
	   		ImageView imageView = new ImageView();
	   		imageView.setImage(image);
	   		
	   		// Only ImageView is resized to fit not the original "image"
	   		imageView.setFitWidth(300);
	   		
	   		// Making sure previewed image is not too big for screen
	   		imageView.setPreserveRatio(true);
	   		imageView.setSmooth(true);
	   		imageView.setCache(true);
	   		imageView.setX(120);
	   		
	   		// Prints out image inside HBox "Preview"
	   		Preview.getChildren().add(imageView);
	   } 
	}

	@FXML
	private void onClickEnchance() {		
		// will be updated
	}
	
	@FXML
	private void onClickNext() throws IOException {	
	//	if (onClickUpload().isEmpty()){
	//	Alert alert = new Alert(AlertType.ERROR);   		
    //	alert.setTitle("Unable to proceed");
    //	alert.setHeaderText("No Image detected");
    //	alert.setContentText("Please upload an image to proceed");
    //	alert.showAndWait();
	//	btnNext.setDisable(true);
		
	//	}
	//	else {
		Stage stage; 
	    Parent root;
	    stage=(Stage) btnNext.getScene().getWindow();	    
	    root = FXMLLoader.load(getClass().getResource("GUI_Result.fxml"));
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	    }
	//}

	//To display all MenuItems under File when it is clicked
	@FXML
	private void onClickFile() {
		MenuItem itmOpen = new MenuItem("Open");
		MenuItem itmClose = new MenuItem("Close");
		MenuItem itmExit = new MenuItem("Exit");
		file.getItems().addAll(itmOpen, itmClose, itmExit);
		menuBar.getMenus().addAll(file);
	}
	
	//To display MenuItems in Help option ('About' in this case)
	@FXML 
	private void onClickHelp() {
		MenuItem itmAbout = new MenuItem("About");
		help.getItems().addAll(itmAbout);
		menuBar.getMenus().addAll(help);
		//Display copyright information when 'Help' is clicked
		Alert alert = new Alert(AlertType.WARNING);   		
    	alert.setTitle("Help");
    	alert.setHeaderText("Automated Image Dental Analysis");
    	alert.setContentText("Version 1.0 - Last Updated March 2017\n\n"
    			+ "Copyright 2017 Group 2 UNMC.\n "
    			+ "All rights reserved.\n\n"
    			+ "This software is made possible by OpenCV and Scene Builder.\n");
    	alert.showAndWait();
		

	}
	
	//When EXIT in File is clicked, the entire application is closed
	@FXML 
	private void onClickExit() {
		Platform.exit();
	}
}


