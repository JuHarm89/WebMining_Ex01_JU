package service;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Julian
 *
 */
@Component
public interface StopWordsService {
	
	
	/** 
	 * Gets a List of Stop Words
	 * @return
	 */
	List<String> getStopWords();
	
	
	/**
	 * Checks whether the provided word is a Stop Word or not
	 * @param word
	 * @return
	 */
	boolean isStopWord(String word);
	
}
