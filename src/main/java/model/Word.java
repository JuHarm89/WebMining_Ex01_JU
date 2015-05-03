package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Word {
	
	private StringProperty content;
	private IntegerProperty amount;
	private BooleanProperty stopWord;
	
	public Word (String content){
		this(content, false);
	}
	
	public Word (String content, boolean stopWord){
		this.content = new SimpleStringProperty(content);
		this.amount = new SimpleIntegerProperty(1);
		this.stopWord = new SimpleBooleanProperty(stopWord);
	}
	
	public void incrementAmount(){
		setAmount(getAmount() + 1);
	}
	
	public final StringProperty contentProperty() {
		return this.content;
	}
	public final java.lang.String getContent() {
		return this.contentProperty().get();
	}
	public final void setContent(final java.lang.String content) {
		this.contentProperty().set(content);
	}
	public final IntegerProperty amountProperty() {
		return this.amount;
	}
	public final int getAmount() {
		return this.amountProperty().get();
	}
	public final void setAmount(final int amount) {
		this.amountProperty().set(amount);
	}

	public final BooleanProperty stopWordProperty() {
		return this.stopWord;
	}

	public final boolean isStopWord() {
		return this.stopWordProperty().get();
	}

	public final void setStopWord(final boolean stopWord) {
		this.stopWordProperty().set(stopWord);
	}
	
	

}
