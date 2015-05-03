package service;

import java.util.List;

import model.Word;

import org.springframework.stereotype.Component;

@Component
public interface WordExtractorService {

	/**
	 * Generates a List of Words containing a String and their frequenzy in the provided Documents
	 * @param excludeStopWords
	 * @return
	 */
	List<Word> extractWords(boolean excludeStopWords);
}
