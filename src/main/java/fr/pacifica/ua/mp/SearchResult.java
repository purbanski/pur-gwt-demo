package fr.pacifica.ua.mp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

@XmlType (namespace="fr.pacifica.ua.mp")
@XmlRootElement(name = "SearchResult")
public class SearchResult {
	
	String query;
	
	int nbResult;
	
	String message;

	List<ElementResult> _listSearchResult = new ArrayList<ElementResult>();
	
	public SearchResult(String _query){
		query = _query;
	}
	
	public SearchResult(){
	}
	
	@XmlElement(name="nbResult")
	public int getNbResult() {
		return nbResult;
	}

	public void setNbResult(int nbResult) {
		this.nbResult = nbResult;
	}

	@XmlElement(name="message")
	public String getMessage() {
		return message;
	}

	@XmlElement(name="query")
	public String getQuery() {
		return query;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void add(Document document, String highlight, ScoreDoc score) {
		ElementResult eleR = new ElementResult();
		eleR.setScore(String.valueOf(score.score));
		eleR.setTokensHigh(highlight);
		eleR.setK(document.get(MpReaderCsv.K_LIB));
		eleR.setL(document.get(MpReaderCsv.L_LIB));
		eleR.setM(document.get(MpReaderCsv.M_LIB));
		eleR.setN(document.get(MpReaderCsv.N_LIB));
		eleR.setO(document.get(MpReaderCsv.O_LIB));
		eleR.setP(document.get(MpReaderCsv.P_LIB));
		eleR.setT(document.get(MpReaderCsv.T_LIB));
		eleR.setU(document.get(MpReaderCsv.U_LIB));
		eleR.setZ(document.get(MpReaderCsv.U_LIB));
		_listSearchResult.add(eleR);
	}

	@XmlElement(name="Result")
	public List<ElementResult> getListSearchResult() {
		return _listSearchResult;
	}
	
	public String toString(){
		StringBuffer message = new StringBuffer("SearchResult");
		message.append(super.toString());
		message.append(" q:'").append(query).append("'");
		message.append(" nb:'").append(nbResult).append("'");
		return message.toString();
	}
	

}
