package fr.pacifica.ua.mp;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/applicationContext.xml" })
public class TestLunceMp {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void readerMp() throws IOException {
		MpReaderCsv readerMp = (MpReaderCsv) applicationContext.getBean("readerCsvMP");
		List<Document> docsData2 = readerMp.convertInDocuments();
		Assert.assertEquals(6140, docsData2.size());
	}

	@Test
	public void indexMp() throws IOException {	
		IndexMP indexMP = (IndexMP)applicationContext.getBean("indexMP");				
		File fIndex = indexMP.getIndexDirectory();			
		if( fIndex.exists()){
			File fMove = new File(fIndex.getAbsolutePath() + "_" + System.currentTimeMillis() );
			System.out.println("move " + fIndex.getPath() + " to "+ fMove.getPath());
			FileUtils.moveDirectory(fIndex,fMove);
		}		
		File fIndexDico = indexMP.getIndexDirectoryDico();
		if( fIndexDico.exists()){
			File fDicoMove = new File(fIndexDico.getAbsolutePath() + "_" + System.currentTimeMillis() );
			System.out.println("move " + fIndex.getPath() + " to "+ fDicoMove.getPath());
			FileUtils.moveDirectory(fIndexDico,fDicoMove);
		}
		
		indexMP.indexData();
		
		Assert.assertTrue(fIndex.isDirectory());
		Assert.assertTrue(fIndexDico.isDirectory());
	}


	@Test
	public void searchMpDot() {
		IndexMP searcher = (IndexMP) applicationContext.getBean("indexMP");
		SearchResult result = searcher.search("01.11");

		Assert.assertEquals(34, result.getNbResult());

	}

	@Test
	public void searchDico() throws IOException {
		IndexMP searcher = (IndexMP) applicationContext.getBean("indexMP");
		String[] result = searcher.searchDico("bla").getPropWords();
		System.out.println(result[0] + ", " + result[1] + ", " + result[2]);
		Assert.assertEquals(10, result.length);

	}

	@Test
	public void urlEncode() throws UnsupportedEncodingException {
		Assert.assertEquals("01.10", URLDecoder.decode("01.10", "UTF-8"));
		Assert.assertEquals("01.10", URLDecoder.decode("01%2e10", "UTF-8"));
	}
	


}