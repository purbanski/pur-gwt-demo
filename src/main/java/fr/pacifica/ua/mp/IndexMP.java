package fr.pacifica.ua.mp;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlElement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
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
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.util.Assert;

public class IndexMP {

	File indexDirectory;

	Version vLucene = Version.LUCENE_35;

	MpReaderCsv readerMp;

	public MpReaderCsv getReaderMp() {
		return readerMp;
	}

	public void setReaderMp(MpReaderCsv readerMp) {
		this.readerMp = readerMp;
	}

	private static Logger log = Logger.getLogger(IndexMP.class.toString());

	public IndexMP(File _indexDirectory) {
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

	protected FSDirectory getFSDirectoryDico() throws IOException {
		File dicoPath = getIndexDirectoryDico();
		log.info("indexDico : " + indexDirectory);
		return new SimpleFSDirectory(dicoPath);
	}

	public File getIndexDirectoryDico() {
		return new File(indexDirectory.getAbsolutePath() + "_dico");
	}

	public SearchResult search(String texte) {
		SearchResult result = new SearchResult(texte);
		try {
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(getFSDirectory(), true));
			QueryParser qParser = new MultiFieldQueryParser(vLucene, MpReaderCsv.libellesCsv, getAnalyzer());
			Query q = qParser.parse(texte);
			TopDocs topDoc = searcher.search(q, 100);
			result.setNbResult(topDoc.totalHits);
			for (int i = 0; i < topDoc.scoreDocs.length; i++) {
				int numDoc = topDoc.scoreDocs[i].doc;
				result.add(searcher.doc(numDoc), getHighlight(searcher.doc(numDoc), q), topDoc.scoreDocs[i]);
			}
		} catch (ParseException e) {
			result.setMessage(e.getMessage());
		} catch (IOException e) {
			result.setMessage(e.getMessage());
		} catch (InvalidTokenOffsetsException e) {
			result.setMessage(e.getMessage());
		}
		log.info(result.toString());
		return result;
	}

	public SearchDicoResult searchDico(String texte) throws IOException {
		SpellChecker spell = new SpellChecker(getFSDirectoryDico());
		String[] results = spell.suggestSimilar(texte, 10);
		SearchDicoResult result = new SearchDicoResult(texte);
		result.setPropWords(results);
		log.info(result.toString());
		return result;
	}

	public void indexData() throws IOException {
		Assert.notNull(readerMp);
		List<Document> docs = readerMp.convertInDocuments();
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
		SpellChecker spell = new SpellChecker(getFSDirectoryDico());
		Dictionary dico = new LuceneDictionary(IndexReader.open(indexMp), MpReaderCsv.L_LIB);
		spell.indexDictionary(dico, new IndexWriterConfig(vLucene, getAnalyzer()), true);
	}

	/**
	 * 
	 * @param doc
	 * @param high
	 * @return highlights
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	private String getHighlight(Document doc, Query q) throws IOException, InvalidTokenOffsetsException {

		QueryScorer scorer = new QueryScorer(q);
		Highlighter high = new Highlighter(scorer);
		String[] fieldName = MpReaderCsv.libellesCsv;
		StringBuffer bufhighs = new StringBuffer();
		int maxBoucle = fieldName.length;
		for (int fi = 0; fi < maxBoucle; fi++) {
			String texTemp = doc.get(fieldName[fi]);
			if (texTemp != null) {
				TokenStream stream = getAnalyzer().tokenStream(fieldName[fi], new StringReader(texTemp));
				String anHigh = high.getBestFragments(stream, texTemp, 3, "[...]");
				if (anHigh != null && anHigh.length() > 1) {
					bufhighs.append("Ex le champ <b>").append(fieldName[fi]).append("</b> contient :<i> ...]").append(anHigh).append("[...]</i>");
				}
			}

		}

		return bufhighs.toString();
	}

}
