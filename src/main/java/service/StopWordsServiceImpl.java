package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @author Julian
 *
 */
public class StopWordsServiceImpl implements StopWordsService{

	private List<String> stopWords;
	
	private String classPathSubFolder;
	
	@Override
	public List<String> getStopWords() {
		return this.stopWords;
	}

	@Override
	public boolean isStopWord(String word) {
		return stopWords.contains(word);
	}
	
	@PostConstruct
	private void parseStopWords(){
		stopWords = new ArrayList<String>();
		List<String> fileNames = null;
		try{
			fileNames = getClasspathFiles();
			for(String fileName: fileNames){
				String text =parseTextFileFromClasspath(fileName);
				stopWords.addAll(Arrays.asList(parseWords(text)));
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private String[] parseWords(String fileContent){
		return fileContent.split("\\r?\\n");
		
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
	
	private List<String> getClasspathFiles() throws IOException{
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		List<String> files = new ArrayList<String>();
		Resource[] resources;
		try{
			resources = patternResolver.getResources("classpath:" + classPathSubFolder + "**");
			for (Resource res: resources){
				files.add(res.getFilename());
			}
		}catch(IOException e){
			e.printStackTrace();
			throw e;
		}
		return files;
	}
	
	@Value("${stopwords.classpath.extension}")
	public void setClassPathSubFolder(String subFolder){
		classPathSubFolder = subFolder;
	}
}
