package fr.pacifica.ua.mp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

public class MpReaderCsv {

	public static String A_LIB = "SECTION NAF26"; // 0
	public static String B_LIB = "LIBELLE NAF26";

	public static String C_LIB = "DIVISION NAF 88";
	public static String D_LIB = "LIBELLE NAF 88";

	public static String E_LIB = "GROUPE NAF 261";
	public static String F_LIB = "LIBELLE NAF 261";

	public static String G_LIB = "CLASSE NAF575";
	public static String H_LIB = "LIBELLE NAF575";

	public static String I_LIB = "CATEGORIE  NAF1342";
	public static String J_LIB = "LIBELLE NAF1342";
	public static String K_LIB = "SOUS-CATEGORIE NAF3142";
	public static String L_LIB = "LIBELLE NAF3142";
	public static String M_LIB = "comprend";

	public static String N_LIB = "CODE PACIFICA 1"; // 13
	public static String O_LIB = "CODE PACIFICA 2";
	public static String P_LIB = "CODE PACIFICA 3";

	public static String Q_LIB = "LIBELLE PACIFICA 1";
	public static String R_LIB = "LIBELLE PACIFICA 2";
	public static String S_LIB = "LIBELLE PACIFICA 3";

	public static String T_LIB = "ACTIVITE ELIGIBLE";

	public static String U_LIB = "CODE ASSIMILATION";
	public static String V_LIB = "LIBELLE ASSIMILATION";

	public static String W_LIB = "RDV"; // 22
	public static String X_LIB = "AVIS ASSURPRO";
	public static String Y_LIB = "MOTS CLES ACTIVITE";
	public static String Z_LIB = "COMMENTAIRES";
	public static String AA_LIB = "RATTACHEMENT NAFA (O / N)";
	public static String AB_LIB = "Ne comprend pas";

	public static String[] libellesCsv = new String[] { A_LIB, B_LIB, C_LIB, D_LIB, E_LIB, F_LIB, G_LIB,
			H_LIB, I_LIB, J_LIB, K_LIB, L_LIB, M_LIB, N_LIB, O_LIB, P_LIB, Q_LIB, R_LIB, S_LIB, T_LIB, U_LIB,
			V_LIB, W_LIB, X_LIB, Y_LIB, Z_LIB, AA_LIB, AB_LIB };

	List<String> linesCsv;
	
	
	/**
	 * 
	 * @param data
	 * @throws IOException
	 */
	public MpReaderCsv(File data) throws IOException {
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
		if (datas.length < libellesCsv.length - 1) {
			System.out.println(numLine + " longueur des libelles : " + libellesCsv.length);
			System.out.println(numLine + " longueur par ligne : " + datas.length);
			System.out.println(numLine + " : " + lineCsv);
		}
		for (int i = 0; i < libellesCsv.length - 1; i++) {
			
			Index index = Index.NOT_ANALYZED;
			if (i < 20) {
				if (libellesCsv[i].startsWith("LIBELLE")) {
					index = Index.ANALYZED;
				}
			} else {
				if(Y_LIB.equals(libellesCsv[i]) || Z_LIB.equals(libellesCsv[i])){
					index = Index.ANALYZED;
				}else {
					index = Index.NO;
				}
			}
		
			docLuc.add(new Field(libellesCsv[i], datas[i], Store.YES, index));
		}
		return docLuc;
	}
}
