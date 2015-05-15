package jcompiler;

import java.util.List;

import jgencode.Gencode;
import jgencode.Sys;
import jlex.Jlex;
import jlex.Tokens;
import jparser.Parsing;

public class Main {
	public static void main(String[] args) {
		Jlex lexModule = new Jlex();
		List<Tokens> tokenlist = lexModule.lexicalAnalysis("1.txt");
		Parsing parseModule = new Parsing();
		Sys sysTable = parseModule.parsingAndGenSys(tokenlist);
		if (sysTable.sysList.get(sysTable.sysList.size() - 1).length == 1)
			System.out
					.println(sysTable.sysList.get(sysTable.sysList.size() - 1));
//		for (int i = 0; i < sysTable.sysList.size() - 1; i++) {
//			for (int j = 0; j < 4; j++) {
//				System.out.print(sysTable.sysList.get(i)[j] + " ");
//			}
//			System.out.println();
//
//		}
		Gencode gencodeModule = new Gencode();
		gencodeModule.genCode(sysTable, parseModule.stab.getOffset());
	}
}
