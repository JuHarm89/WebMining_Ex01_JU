package service;

import java.util.ArrayList;
import java.util.List;

import model.Word;

public class WordExtractorServiceMockImpl implements WordExtractorService{

	public List<Word> extractWords(boolean excludeStopWords){
		return getMockList();
	}
	
	private List<Word> getMockList(){
		List<Word> words = new ArrayList<Word>();
		
		words.add(new Word("eins"));
		words.add(new Word("zwei"));
		words.add(new Word("Auto"));
		words.add(new Word("Drei"));
		words.add(new Word("Bus"));
		words.add(new Word("Baum"));
		
		return words;
	}
	
	
}
