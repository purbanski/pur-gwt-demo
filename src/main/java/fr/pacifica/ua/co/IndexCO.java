package fr.pacifica.ua.co;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlElement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.util.Assert;

public class IndexCO {

	File indexDirectory;

	Version vLucene = Version.LUCENE_35;

	ReaderCOCsv readerCO;

	public ReaderCOCsv getReaderCO() {
		return readerCO;
	}

	public void setReaderCO(ReaderCOCsv readerCO) {
		this.readerCO = readerCO;
	}

	private static Logger log = Logger.getLogger(IndexCO.class.toString());

	public IndexCO(File _indexDirectory) {
		this.indexDirectory = _indexDirectory;
	}

	@XmlElement(name = "indexDirectory")
	public File getIndexDirectory() {
		return indexDirectory;
	}

	public void setIndexDirectory(File indexDirectory) {
		this.indexDirectory = indexDirectory;
	}

	
	public Analyzer getAnalyzer() {
		return new FrenchAnalyzer(vLucene);
	}

	protected FSDirectory getFSDirectory() throws IOException {
		log.info("indexDirectory : " + indexDirectory);
		return new SimpleFSDirectory(indexDirectory);
	}


	public File getIndexDirectoryDico() {
		return new File(indexDirectory.getAbsolutePath() + "_dico");
	}

	public SearchResult search(String texte) {
		SearchResult result = new SearchResult(texte);
		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(getFSDirectory(), true));
			QueryParser qParser = new MultiFieldQueryParser(vLucene, ReaderCOCsv.libellesCsv, getAnalyzer());
			Query q = qParser.parse(texte);
			TopDocs topDoc = searcher.search(q, 5);
			result.setNbResult(topDoc.totalHits);
			for (int i = 0; i < topDoc.scoreDocs.length; i++) {
				int numDoc = topDoc.scoreDocs[i].doc;
				result.add(searcher.doc(numDoc), topDoc.scoreDocs[i]);
			}
		} catch (ParseException e) {
			result.setMessage(e.getMessage());
		} catch (IOException e) {
			result.setMessage(e.getMessage());
		}
		log.info(result.toString());
		return result;
	}


	public void indexData() throws IOException {
		Assert.notNull(readerCO);
		List<Document> docs = readerCO.convertInDocuments();
		FSDirectory indexMp = getFSDirectory();
		IndexWriter index = new IndexWriter(indexMp, new IndexWriterConfig(vLucene, getAnalyzer()));
		try {
			log.info("Start - indexDocument");
			for (Document document : docs) {
				index.addDocument(document);
			}
		} finally {
			index.close();
			log.info("Close - index");
		}
	}

	
}
