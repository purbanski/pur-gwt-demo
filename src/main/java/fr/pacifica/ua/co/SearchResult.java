package fr.pacifica.ua.co;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

@XmlType (namespace="fr.pacifica.ua.co")
@XmlRootElement(name = "SearchResult")
public class SearchResult {

	String query;

	int nbResult;

	String message;

	List<ElementResult> _listSearchResult = new ArrayList<ElementResult>();

	public SearchResult(String _query) {
		query = _query;
	}

	public SearchResult() {
	}

	@XmlElement(name = "nbResult")
	public int getNbResult() {
		return nbResult;
	}

	public void setNbResult(int nbResult) {
		this.nbResult = nbResult;
	}

	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}

	@XmlElement(name = "query")
	public String getQuery() {
		return query;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void add(Document document, ScoreDoc score) {
		ElementResult eleR = new ElementResult();
		eleR.setCdInse(document.get(ReaderCOCsv.A_LIB));
		eleR.setCdPost(document.get(ReaderCOCsv.B_LIB));
		eleR.setVille(document.get(ReaderCOCsv.C_LIB));
		_listSearchResult.add(eleR);
	}

	@XmlElement(name = "Result")
	public List<ElementResult> getListSearchResult() {
		return _listSearchResult;
	}

	public String toString() {
		StringBuffer message = new StringBuffer("SearchResult");
		message.append(super.toString());
		message.append(" q:'").append(query).append("'");
		message.append(" nb:'").append(nbResult).append("'");
		return message.toString();
	}

}
