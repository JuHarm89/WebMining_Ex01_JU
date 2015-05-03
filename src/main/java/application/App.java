package application;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppConfig;
import controller.RootController;
import controller.WebMiningViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.*;
import javafx.fxml.*;
public class App extends Application{
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
    public void start(Stage primaryStage) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        WebMiningViewController controller = context.getBean(WebMiningViewController.class);
        	
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Web Mining Ex01");
        this.primaryStage.setScene(new Scene ((Parent) controller.getView()));
        this.primaryStage.show();
        
        //initRootLayout(loader);

        //showWebMiningView(loader);
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout(SpringFxmlLoader loader) {
        try {
            // Load root layout from fxml file.
            
            rootLayout = (BorderPane) loader.load("/view/rootLayout.fxml", RootController.class);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showWebMiningView(SpringFxmlLoader loader){
    	try{
    		
    		AnchorPane webMiningView = (AnchorPane)loader.load("/view/webMiningView.fxml", WebMiningViewController.class);
    		rootLayout.setCenter(webMiningView);
    		
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }

	
	public static void main(String[] args) {
		launch(args);

	}
}
