package pur.gwtplatform.samples.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "key")
public class Data {

	private String key;
	private String value;
	public Data(String id, String value) {
		this.key = id;
		this.value = value;
	}

	public Data() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
