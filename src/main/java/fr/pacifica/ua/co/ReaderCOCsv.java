package fr.pacifica.ua.co;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.springframework.util.StringUtils;

public class ReaderCOCsv {

	public static String A_LIB = "cdinse"; // 0
	public static String B_LIB = "cdpost"; // 1
	public static String C_LIB = "ville"; // 2
	public static String D_LIB = "villel"; // 3
	public static String E_LIB = "cddepa"; // 4

	public static String F_LIB = "E"; // 5
	public static String G_LIB = "01"; // 6

	public static String H_LIB = "date"; // 7
	public static String I_LIB = "C"; // 8

	public static String[] libellesCsv = new String[] { A_LIB, B_LIB, C_LIB, D_LIB, E_LIB, F_LIB, G_LIB,
			H_LIB, I_LIB };

	List<String> linesCsv;

	/**
	 * 
	 * @param data
	 * @throws IOException
	 */
	public ReaderCOCsv(File data) throws IOException {
		this.linesCsv = FileUtils.readLines(data);
		// Isole les libelles
		String[] testlibelleCsv = linesCsv.remove(0).split(";");
		new AssertionError(testlibelleCsv.equals(libellesCsv));
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<Document> convertInDocuments() throws IOException {
		List<Document> docs = new ArrayList<Document>();
		int numLigne = 0;
		System.out.println("Start read CSV DATA : ");
		for (String line : linesCsv) {
			docs.add(convertInDocument(line, numLigne++));
		}
		System.out.println("End read " + numLigne + " ok;");
		return docs;
	}

	/**
	 * @param lineCsv
	 * @return
	 */
	protected Document convertInDocument(String lineCsv, int numLine) {
		Document docLuc = new Document();
		String[] datas = lineCsv.split(";");
			System.out.println(numLine + " longueur des libelles : " + libellesCsv.length);
			System.out.println(numLine + " longueur par ligne : " + datas.length);
			
		//si ce n'est pas un cedex
		if (datas.length < 9) {
			
			System.out.println(numLine + " : " + lineCsv);
			
			//on enregistre le code insee
			docLuc.add(new Field(libellesCsv[0], datas[0], Store.YES, Index.NO));
			//on indexe le code postale
			docLuc.add(new Field(libellesCsv[1], datas[1], Store.YES, Index.ANALYZED));
			//on analyse la ville
			docLuc.add(new Field(libellesCsv[2], datas[2], Store.YES, Index.ANALYZED));
			//on indexe le departement
			docLuc.add(new Field(libellesCsv[4], datas[4], Store.YES, Index.NOT_ANALYZED));	
			
			//Boost les communes les plus importantes en fonction du nombre de zero du code postale.
			int numZero = StringUtils.countOccurrencesOf(datas[1], "0");
			Float boost = Float.valueOf("1." + String.valueOf(numZero));
			System.out.println("Boost : " + String.valueOf(boost));
			docLuc.setBoost(boost);
		}

		return docLuc;
	}
}
