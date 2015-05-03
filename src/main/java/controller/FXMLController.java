package controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.InitializingBean;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public abstract class FXMLController implements InitializingBean, Initializable{
	
	public FXMLController(){
		super();
	}
	
	protected Node view;
	
	protected String fxmlFilePath;
	
	public abstract void setFxmlFilePath(String filepath);
	
	@Override
	public void afterPropertiesSet () throws Exception{
		loadFxml();
	}
	
	protected final void loadFxml() throws IOException{
		
		try(InputStream fxmlStream = getClass().getResourceAsStream(fxmlFilePath)){
			FXMLLoader loader = new FXMLLoader();
			loader.setController(this);
			this.view = loader.load(fxmlStream);
		}
	}
	
	public Node getView (){
		return this.view;
	}

	public String getFxmlFilePath() {
		return fxmlFilePath;
	}
	
	
	
}
