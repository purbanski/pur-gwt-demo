package fr.pacifica.ua.mp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "results")
public class SearchDicoResult {

	String qWord;
	
	SearchDicoResult(){
	}
	
	
	SearchDicoResult(String texte){
		qWord = texte;
	}
	
	@XmlElement(name = "qword")
	public String getqWord() {
		return qWord;
	}

	public void setqWord(String qWord) {
		this.qWord = qWord;
	}

	String[] propWords;

	@XmlElement(name = "result")
	public String[] getPropWords() {
		return propWords;
	}

	public void setPropWords(String[] propWords) {
		this.propWords = propWords;
	}
	
	public String toString(){
		StringBuffer message = new StringBuffer("SearchResult");
		message.append(super.toString());
		message.append(" qWord:'").append(qWord).append("'");
		message.append("result : [");
		for (int i = 0; i < getPropWords().length; i++) {
			message.append(getPropWords()[i]);
			message.append(",");
		}
		
		message.append("]");
		return message.toString();
	}

}
