package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import model.Word;

public class WordExtractorServiceImpl implements WordExtractorService{
	
	@Autowired
	private StopWordsService stopWordService;
	
	private String classPathSubFolder;

	@Override
	public List<Word> extractWords(boolean excludeStopWords) {
		
		List<Word> extractedWords = new ArrayList<Word>();
		try{
			List<String> textFiles = getClasspathFiles();
			for(String fileName: textFiles){
				String fileContent = parseTextFileFromClasspath(fileName);
				String[] words = parseWords(fileContent);
				for (String word: words){
					boolean stopWord = stopWordService.isStopWord(word);
					if (!(excludeStopWords && stopWord)){
						Optional<Word> existing = extractedWords.stream().filter(w -> w.getContent().equals(word)).findFirst();
						if (existing.isPresent()) existing.get().incrementAmount();
						else extractedWords.add(new Word(word, stopWord));
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return extractedWords;	
	}
	
	private String[] parseWords(String fileContent){
		return fileContent.split("(^['-]|['-]$|['-]\\W+|[^\\w'-]\\W*)");
		
		
	}
	
	private List<String> getClasspathFiles() throws IOException{
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		List<String> textFiles = new ArrayList<String>();
		Resource[] resources;
		try{
			resources = patternResolver.getResources("classpath:" + classPathSubFolder + "*.txt");
			for (Resource res: resources){
				textFiles.add(res.getFilename());
			}
		}catch(IOException e){
			e.printStackTrace();
			throw e;
		}
		return textFiles;
	}
	
	private String parseTextFileFromClasspath (String fileName) throws IOException{
		Resource resource = null;
		InputStream inputStream = null;
		StringBuilder stringBuilder = new StringBuilder();
		try{
			resource = new ClassPathResource(classPathSubFolder +fileName);
			inputStream = resource.getInputStream();
			try(Reader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))){
				int c=0;
				while((c=reader.read())!=-1){
					stringBuilder.append((char)c);
				}
			}
		}
		catch(IOException e){
			throw e;
		}

		return stringBuilder.toString();
		
	}

	@Value("${books.classpath.extension}")
	public void setClassPathSubFolder(String subFolder){
		classPathSubFolder = subFolder;
	}
}
