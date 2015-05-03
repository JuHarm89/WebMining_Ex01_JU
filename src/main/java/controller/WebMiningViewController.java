package controller;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.stereotype.Component;

import service.WordExtractorService;
import view.LogarithmicNumberAxis;
import application.App;
import model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Component
public class WebMiningViewController extends FXMLController{
	
	ObservableList<Word> currentWordList;
	
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
	LineChart<Number, Number> frequenzyLineChart;
	
	@FXML
	ScatterChart<Number, Number> frequenzyScatterChart;
	
	@FXML
	public void clickExtractBtn(){
		setCurrentWordList(getExtractedWords(excludeCheckBox.isSelected()));
		wordsTable.setItems(getCurrentWordList());
		fillCharts();
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
	
	private void fillCharts(){
		frequenzyLineChart.getData().clear();
		frequenzyScatterChart.getData().clear();
		HashMap<Number, Number> map = new HashMap<>();
		for (Word word: getCurrentWordList()){
			if (map.containsKey(word.getAmount()))
				map.put(word.getAmount(), map.get(word.getAmount()).intValue() + 1);
			else 
				map.put(word.getAmount(), 1);
		}
		XYChart.Series<Number,	Number> series = new XYChart.Series<Number, Number>();
		for (Entry<Number, Number> entry: map.entrySet()){
			series.getData().add(new XYChart.Data<Number, Number>(entry.getValue(), entry.getKey()));
		}
		
		frequenzyLineChart.getData().add(series);
		frequenzyScatterChart.getData().add(series);
		
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

	public ObservableList<Word> getCurrentWordList() {
		return currentWordList;
	}

	public void setCurrentWordList(ObservableList<Word> currentWordList) {
		this.currentWordList = currentWordList;
	}
}
