package fr.pacifica.ua.co;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="fr.pacifica.ua.co")
public class ElementResult {

	private String cdInse;
	
	private String cdPost;
	
	private String ville;

	@XmlElement(name = "cdinse")
	public String getCdInse() {
		return cdInse;
	}

	public void setCdInse(String cdInse) {
		this.cdInse = cdInse;
	}

	@XmlElement(name = "cdpost")
	public String getCdPost() {
		return cdPost;
	}

	public void setCdPost(String cdPost) {
		this.cdPost = cdPost;
	}

	@XmlElement(name = "ville")
	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
