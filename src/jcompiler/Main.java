package jcompiler;

import java.util.List;

import jlex.Jlex;
import jlex.Tokens;
import jparser.Parsing;

public class Main {
	public static void main(String []args) {
		Jlex lexModule = new Jlex();
		List<Tokens> tokenlist = lexModule.lexicalAnalysis("1.txt");
		Parsing parseModule = new Parsing();
		String result=parseModule.parsing(tokenlist);
		System.out.println(result);
	}
}
