package pur.gwtplatform.samples.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="fr.pacifica.ua.mp")
public class ElementResult {

	private String highs;

	private String score;

	private String k = "SOUS-CATEGORIE NAF3142";
	private String l = "LIBELLE NAF3142";
	private String m = "comprend";

	private String n = "CODE PACIFICA 1";
	private String o = "CODE PACIFICA 2";
	private String p = "CODE PACIFICA 3";

	private String t = "ACTIVITE ELIGIBLE";

	private String u = "CODE ASSIMILATION";

	private String z = "COMMENTAIRES";

	@XmlElement(name = "k")
	public String getK() {
		return k;
	}

	@XmlElement(name = "l")
	public String getL() {
		return l;
	}

	@XmlElement(name = "m")
	public String getM() {
		return m;
	}

	@XmlElement(name = "n")
	public String getN() {
		return n;
	}

	@XmlElement(name = "o")
	public String getO() {
		return o;
	}

	@XmlElement(name = "p")
	public String getP() {
		return p;
	}

	@XmlElement(name = "t")
	public String getT() {
		return t;
	}

	@XmlElement(name = "u")
	public String getU() {
		return u;
	}

	@XmlElement(name = "z")
	public String getZ() {
		return z;
	}

	@XmlElement(name = "highs")
	public String getHighs() {
		return highs;
	}

	@XmlElement(name = "score")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void setL(String l) {
		this.l = l;
	}

	public void setM(String m) {
		this.m = m;
	}

	public void setN(String n) {
		this.n = n;
	}

	public void setO(String o) {
		this.o = o;
	}

	public void setP(String p) {
		this.p = p;
	}

	public void setT(String t) {
		this.t = t;
	}

	public void setU(String u) {
		this.u = u;
	}

	public void setZ(String z) {
		this.z = z;
	}

	public void setTokensHigh(String highs) {
		this.highs = highs;
	}

}
