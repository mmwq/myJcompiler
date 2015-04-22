package jparser;

import java.util.List;
import java.util.Stack;

import jlex.Tokens;
import parsingtree.*;

public class Parsing {
	static String[] keyword = { "main", "semi", ":=", "vid", "numconst", ",",
			"char", "float", "int", "function", "pid", "{", "}", "begin",
			"end", "[", "]", "charconst", "floatconst", "true", "false", "or",
			"and", "not", "<", ">", "==", "!=", "=", "+", "-", "*", "/", "(",
			")", "for", "rof", "if", "then", "else", "fi", "read", "write",
			"writeln", "string", "switch", "of", "ends", "case", ":", "esac",
			"while", "do", "elihw", "#" };
	static String[] symbolTable = { "P", "program", "declarations",
			"vardecblock", "vardecllist", "vardecl", "arraydecl", "type",
			"ctype", "vidlist", "procblock", "procdecllist", "procdecl",
			"procheader", "arguments", "arglist", "arg", "proccode", "code",
			"statements", "statement", "assignment", "asgmtvar", "expression",
			"bexpression", "cexpression", "t", "f", "const", "cconst",
			"booleanconst", "forstatement", "ifstatement", "istatement",
			"iestatement", "iostatement", "output", "out", "procedurecall",
			"parameters", "paralist", "switchstatement", "casestatement",
			"caselist", "case1", "whilestatement", "bexpression1",
			"bexpression2" };
	static String[][] expressions = {
			{ "S", "P" },
			{ "P", "main program" },
			{ "program", "declarations code" },
			{ "declarations", "vardecblock procblock" },
			{ "vardecblock", "" },
			{ "vardecblock", "{ vardecllist }" },
			{ "vardecllist", "vardecl semi vardecllist" },
			{ "vardecllist", "vardecl" },
			{ "vardecl", "arraydecl" },
			{ "vardecl", "type := const" },
			{ "vardecl", "type vidlist" },
			{ "arraydecl", "type vid [ numconst ]" },
			{ "type", "ctype" },
			{ "type", "char" },
			{ "ctype", "int" },
			{ "ctype", "float" },
			{ "vidlist", "vid" },
			{ "vidlist", "vid , vidlist" },
			{ "procblock", "" },
			{ "procblock", "procdecllist" },
			{ "procdecllist", "procdecl" },
			{ "procdecllist", "procdecl procdecllist" },
			{ "procdecl", "procheader proccode" },
			{ "procheader", "function pid ( arguments )" },
			{ "arguments", "" },
			{ "arguments", "arglist" },
			{ "arglist", "arg" },
			{ "arglist", "arg semi arglist" },
			{ "arg", "type vid" },
			{ "proccode", "{ statements semi }" },
			{ "code", "begin statements semi end" },
			{ "statements", "" },
			{ "statements", "statement semi statements" },
			{ "statement", "assignment" },
			{ "statement", "forstatement" },
			{ "statement", "ifstatement" },
			{ "statement", "iostatement" },
			{ "statement", "procedurecall" },
			{ "statement", "switchstatement" },
			{ "statement", "whilestatement" },
			{ "assignment", "asgmtvar := expression" },
			{ "asgmtvar", "vid" },
			{ "asgmtvar", "vid [ numconst ]" },
			{ "expression", "bexpression" },
			{ "expression", "cexpression" },
			{ "expression", "charconst" },
			{ "bexpression", "( bexpression ) or ( bexpression )" },
			{ "bexpression", "( bexpression ) and ( bexpression )" },
			{ "bexpression", "not ( bexpression )" },
			{ "bexpression", "booleanconst" },
			{ "bexpression", "cexpression < cexpression" },
			{ "bexpression", "cexpression > cexpression" },
			{ "bexpression", "cexpression == cexpression" },
			{ "bexpression", "cexpression != cexpression" },
			{ "cexpression", "cexpression + t" },
			{ "cexpression", "cexpression - t" },
			{ "cexpression", "t" },
			{ "t", "t * f" },
			{ "t", "t / f" },
			{ "t", "f" },
			{ "f", "asgmtvar" },
			{ "f", "cconst" },
			{ "f", "( cexpression )" },
			{ "const", "charconst" },
			{ "const", "cconst" },
			{ "const", "booleanconst" },
			{ "cconst", "floatconst" },
			{ "cconst", "numconst" },
			{ "cconst", "- cconst" },
			{ "booleanconst", "true" },
			{ "booleanconst", "false" },
			{ "forstatement",
					"for ( vid := expression semi bexpression semi statement ) statements rof" },
			{ "ifstatement", "istatement" },
			{ "ifstatement", "iestatement" },
			{ "istatement", "if bexpression then statements fi" },
			{ "iestatement",
					"if bexpression then statements else statements fi" },
			{ "iostatement", "read asgmtvar" },
			{ "iostatement", "write output" },
			{ "iostatement", "writeln output" }, { "output", "out" },
			{ "output", "out + output" }, { "out", "string" },
			{ "out", "asgmtvar" }, { "procedurecall", "pid ( parameters )" },
			{ "parameters", "" }, { "parameters", "paralist" },
			{ "paralist", "expression" },
			{ "paralist", "expression , paralist" },
			{ "switchstatement", "switch asgmtvar of caselist ends" },
			{ "caselist", "case1" },
			{ "caselist", "case case1 semi caselist" }, { "case1", "const" },
			{ "case1", "const : statements esac" },
			{ "whilestatement", "while bexpression do statements elihw" } };
	static int[][] actiongoto = new int[183][101];

	public String parsing(List<Tokens> tokenlist) {
		GetTable gett = new GetTable();
		actiongoto = gett.gett(expressions); // 从html获得分析表
		// String[] input = { "main", "{", "int", "vid", "}", "begin",
		// "semi","end", "#" };//a test input
		Stack<Node> analysisStack = new Stack<Node>();
		Stack<String> symbolStack = new Stack<String>();
		Stack<Integer> stateStack = new Stack<Integer>();
		stateStack.push(0);// start with state 0
		Ptree parsingtree = new Ptree();
		int i = 0;
		while (true) {
			String token = tokenlist.get(i).token;
			int n = findString(token);
			int opcode = actiongoto[stateStack.peek()][n];
			// System.out.println();
			if ((opcode > 0) && (opcode != 233)) {

				Node leaf = parsingtree.makeLeaf(tokenlist.get(i));
				analysisStack.push(leaf);

				symbolStack.push(token); // original
				stateStack.push(opcode);
				if (!token.equals("#")) {
					i++;
				}
			} else if (opcode == 0) {
				System.out.println("Syntax error near " + token);
				break;
			} else if (opcode < 0) {
				opcode = (opcode * -1); // 找到对应的规约表达式
				boolean forn1 = false, forn2 = false;
				int nvalue1, nvalue2; // 传递的参数
				float fvalue1, fvalue2;
				switch (opcode) {
				case 54: { // E => E + T
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					forn1 = analysisStack.peek().isForn();// forn = float(1) or
															// number(0)
					fvalue1 = analysisStack.peek().getFvalue();
					nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					forn2 = analysisStack.peek().isForn();
					fvalue2 = analysisStack.peek().getFvalue();
					nvalue2 = analysisStack.peek().getNvalue();
					if (forn1 || forn2) {// 如果是整数加浮点数
						node.setForn(true);
						node.setFvalue(fvalue1 + nvalue1 + fvalue2 + nvalue2);
					} else {// 如果是整数加整数
						node.setForn(false);
						node.setNvalue(nvalue1 + nvalue2);
					}
					int leftcode = findLeft(expressions[54][0]);
					analysisStack.pop();
					stateStack.pop();
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 55: {// E => E - T
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					forn1 = analysisStack.peek().isForn();// forn = float(1) or
															// number(0)
					fvalue1 = analysisStack.peek().getFvalue();
					nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					forn2 = analysisStack.peek().isForn();
					fvalue2 = analysisStack.peek().getFvalue();
					nvalue2 = analysisStack.peek().getNvalue();
					if (forn1 || forn2) {// 如果是整数减浮点数
						node.setForn(true);
						node.setFvalue(fvalue1 + nvalue1 - fvalue2 - nvalue2);
					} else {// 如果是整数减整数
						node.setForn(false);
						node.setNvalue(nvalue1 - nvalue2);
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[55][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 56: { // E=>T
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					node.setForn(analysisStack.peek().isForn());
					if (analysisStack.peek().isForn()) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 57: { // t => t * f;
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					forn1 = analysisStack.peek().isForn();
					// forn stands for float(1) or number(0)
					fvalue1 = analysisStack.peek().getFvalue();
					nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					forn2 = analysisStack.peek().isForn();
					fvalue2 = analysisStack.peek().getFvalue();
					nvalue2 = analysisStack.peek().getNvalue();
					if (forn1 || forn2) {// 如果是整数乘浮点数
						node.setForn(true);
						if (forn1) {
							node.setFvalue(fvalue1 * nvalue2);
						} else {
							node.setFvalue(fvalue2 * nvalue1);
						}
					} else {// 如果是整数乘整数
						node.setForn(false);
						node.setNvalue(nvalue1 * nvalue2);
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 58: { // t => t / f;
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					forn1 = analysisStack.peek().isForn();
					// forn stands for float(1) or number(0)
					fvalue1 = analysisStack.peek().getFvalue();
					nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					forn2 = analysisStack.peek().isForn();
					fvalue2 = analysisStack.peek().getFvalue();
					nvalue2 = analysisStack.peek().getNvalue();
					if (forn1 || forn2) {// 如果是整数除浮点数
						node.setForn(true);
						if (forn1) {
							if (nvalue2 != 0)
								node.setFvalue(fvalue1 / nvalue2);
							else
								return "error on line"
										+ (node.getToken()).linenum
										+ ":/ by zero";
						} else {
							if (fvalue2 != 0)
								node.setFvalue(nvalue1 / fvalue2);
							else
								return "error on line"
										+ (node.getToken()).linenum
										+ ": / by zero";
						}
					} else {// 如果是整数除整数
						node.setForn(false);
						if (nvalue2 != 0)
							node.setNvalue(nvalue1 / nvalue2);
						else
							return "error on line" + (node.getToken()).linenum
									+ ":/ by zero";
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 59: { // t=>f
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					node.setForn(analysisStack.peek().isForn());
					if (analysisStack.peek().isForn()) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 60: { // f=>asgmtvar
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					node.setForn(analysisStack.peek().isForn());
					if (analysisStack.peek().isForn()) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 61: {// f=>cconst
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					node.setForn(analysisStack.peek().isForn());
					if (analysisStack.peek().isForn()) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 62: {
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					node.setForn(analysisStack.peek().isForn());
					if (analysisStack.peek().isForn()) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 63: { //f => ( cexpression );
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					forn1 = analysisStack.peek().isForn();
					// forn stands for float(1) or number(0)
					fvalue1 = analysisStack.peek().getFvalue();
					nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					analysisStack.peek().setFather(node);
					if (forn1) {
						node.setForn(true);
						node.setFvalue(fvalue1);
					} else {
						node.setForn(false);
						node.setNvalue(nvalue1);
					}
					analysisStack.pop();
					stateStack.pop();
					int leftcode = findLeft(expressions[56][0]);
					stateStack
							.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
					analysisStack.push(node);
				}
				case 64:{//const => charconst;
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
				}
				case 65: { //f => ( cexpression );
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
				}
				case 66: { //f => ( cexpression );
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();}
				case 67: { //f => ( cexpression );
					Node node = parsingtree.makeNode(token);
					analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();}
				
				}
				if (expressions[opcode][1].length() != 0) {
					// System.out.println(expressions[opcode][1].split(" ").length);
					for (int count = 0; count < (expressions[opcode][1]
							.split(" ").length); count++) {
						symbolStack.pop();
						stateStack.pop();
					}
				}
				symbolStack.push(expressions[opcode][0]);
				System.out.println("reduce:" + symbolStack.peek() + " -> "
						+ expressions[opcode][1]);
				int leftcode = findLeft(expressions[opcode][0]);
				stateStack.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
			} else if (opcode == 233) {// 233 means accept
				System.out.println("Accepted!");
				break;
			}
		}
		return "success";
	}

	private static int findString(String c) { // 返回该token在关键字表的位置
		int index = -1;
		for (int i = 0; i < keyword.length; i++) {
			if (c.equals(keyword[i])) {
				return i;
			}
		}
		return index;
	}

	private static int findLeft(String c) { // 返回该token在符号表的位置
		int index = -1;
		for (int i = 0; i < symbolTable.length; i++) {
			if (c.equals(symbolTable[i])) {
				return i;
			}
		}
		return index;
	}

}
