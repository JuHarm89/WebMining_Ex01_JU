package controller;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import service.WordExtractorService;
import application.App;
import model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Component
public class WebMiningViewController extends FXMLController{
	
	@Autowired
	WordExtractorService wordExtractorService;
	
	@FXML
	TableView<Word> wordsTable;

	@FXML
	TableColumn<Word, String> wordContentColumn;
	
	@FXML
	TableColumn<Word, Integer> amountColumn;
	
	@FXML
	TableColumn<Word, String> stopWordColumn;

	@FXML
	Button ectractBtn;
	
	@FXML
	CheckBox excludeCheckBox;
	
	@FXML
	public void clickExtractBtn(){
		wordsTable.setItems(getExtractedWords(excludeCheckBox.isSelected()));
	}
	
	public WebMiningViewController(){
	}
	
	private ObservableList<Word> getExtractedWords(boolean excludeStopWords){
		ObservableList<Word> words = FXCollections.observableArrayList();
		words.addAll(wordExtractorService.extractWords(excludeStopWords));
		Collections.sort(words, new Comparator<Object>(){
			@Override
			public int compare(Object word0, Object word1) {
				
				if (((Word)word0).getAmount() > ((Word)word1).getAmount())
					return -1;
				else return 1;
			}	
		});
		return words;
	}

	public WordExtractorService getWordExtractorService() {
		return wordExtractorService;
	}

	public void setWordExtractorService(WordExtractorService wordExtractorService) {
		this.wordExtractorService = wordExtractorService;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wordContentColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
		amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
		stopWordColumn.setCellValueFactory(cellData -> cellData.getValue().stopWordProperty().asString());
		
	}

	@Override
	@Value("${fxml.webmining.view}")
	public void setFxmlFilePath(String filepath) {
		this.fxmlFilePath = filepath;
	}

		
	
}
