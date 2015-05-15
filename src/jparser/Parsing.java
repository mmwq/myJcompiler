package jparser;

import java.util.List;
import java.util.Stack;

import jgencode.Sys;
import jlex.Tokens;
import parsingtree.Node;
import parsingtree.Ptree;
import parsingtree.Stable;
import parsingtree.Stablee;

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
			"caselist", "case1", "whilestatement", "M", "N", "S" };
	static String[][] expressions = {
			{ "S", "P" },
			{ "P", "main program" },
			{ "program", "M declarations code" },
			{ "M", "" },
			{ "declarations", "vardecblock procblock" },
			{ "vardecblock", "" },
			{ "vardecblock", "{ vardecllist }" },
			{ "vardecllist", "vardecl semi vardecllist" },
			{ "vardecllist", "vardecl" },
			{ "vardecl", "arraydecl" },
			{ "vardecl", "type vid := const" },
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
			{ "procdecllist", "N procdecl" },
			{ "procdecllist", "N procdecl procdecllist" },
			{ "N", "" },
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
	static int[][] actiongoto = new int[185][103];
	public Stable stab = new Stable();

	public Sys parsingAndGenSys(List<Tokens> tokenlist) {
		GetTable gett = new GetTable();
		actiongoto = gett.gett(expressions); // 从html获得分析表
		// String[] input = { "main", "{", "int", "vid", "}", "begin",
		// "semi","end", "#" };//a test input
		Stack<Node> analysisStack = new Stack<Node>();
//		Stack<String> symbolStack = new Stack<String>();
		Stack<Integer> stateStack = new Stack<Integer>();
		stateStack.push(0);// start with state 0
		Ptree parsingtree = new Ptree();
		Sys sysTable = new Sys();
		int i = 0;
		while (true) {
			String token = tokenlist.get(i).token;
			int n = findString(token);
			int opcode = actiongoto[stateStack.peek()][n];
			// System.out.println();
			if ((opcode > 0) && (opcode != 233)) {

				Node leaf = parsingtree.makeLeaf(tokenlist.get(i));
				analysisStack.push(leaf);
				// System.out.println("shift " + opcode);
//				symbolStack.push(token); // original
				stateStack.push(opcode);
				if (!token.equals("#")) {
					i++;
				}
			} else if (opcode == 0) {
				System.out.println("Syntax error near " + token);
				break;
			} else if (opcode < 0) {
				opcode = (opcode * -1); // 找到对应的规约表达式
				// int dataType1 = 0, dataType2 = 0;
				// int nvalue1, nvalue2; // 传递的参数
				// float fvalue1, fvalue2;
				Node node = parsingtree.makeNode(expressions[opcode][0]);
				// analysisStack.peek().setFather(node);
				switch (opcode) {
				case 3: {// M => ;
					// do nothing
					break;
				}
				case 10: {// vardecl => type vid := const;
					Stablee element = new Stablee();
					element.setOffset(stab.getOffset());
					// Node node1 = analysisStack.peek();
					// fvalue1 = analysisStack.peek().getFvalue();
					int index = analysisStack.peek().getIndex();
					// char cvalue = analysisStack.peek().getSvalue().charAt(0);
					// boolean bvalue = analysisStack.peek().isBvalue();
					int typ = analysisStack.peek().getDataType();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.pop();
					stateStack.pop();
					element.setVarname(analysisStack.peek().getToken().vid);
					analysisStack.pop();
					stateStack.pop();
					if (analysisStack.peek().getDataType() != typ) {
						String[] a = { "error" };
						sysTable.sysList.add(a);
						return sysTable;
					}
					element.setVartype(typ);
					switch (typ) {// dataType : 1 float, 2 int 3 char 4 boolean
					case 1: {
						element.setSize(4);
						// gencode(element.setOffset,fvalue1)
						break;
					}
					case 2: {
						element.setSize(4);
						sysTable.genSys("+", "$" + index, "#0",
								element.getOffset());
						if (index < 0)
							sysTable.free(index);
						break;
					}
					case 3: {
						element.setSize(1);
						// gencode(element.setOffset,nvalue1)
						break;
					}
					case 4: {
						element.setSize(1);
						// gencode(element.setOffset,bvalue1)
						break;
					}
					}
					if (stab.check(element.getVarname()) != -1) {
						String[] a = { "dacal error" };
						sysTable.sysList.add(a);
						return sysTable;
					}

					stab.addlement(element);
					analysisStack.pop();
					stateStack.pop();
					break;

				}
				case 11: { // vardecl => type vidlist;
					int num = analysisStack.peek().getWidth();
					analysisStack.pop();
					stateStack.pop();
					int w = analysisStack.peek().getDataType() == 1
							|| analysisStack.peek().getDataType() == 2 ? 4 : 1;
					stab.setOffset(stab.getOffset() + w * num);
					for (int j =1; j<=num; j++) {
						stab.list.get(stab.list.size() - j).setVartype(
								analysisStack.peek().getDataType());
						if (analysisStack.peek().getDataType() == 1/**///if first
								|| analysisStack.peek().getDataType() == 2) {
							stab.list.get(stab.list.size() - j).setSize(4);
							stab.list.get(stab.list.size() - j).setOffset(
									(stab.getOffset()-4*j));

						} else {
							stab.list.get(stab.list.size() - j).setSize(1);
							stab.list.get(stab.list.size() - j).setOffset(
									stab.getOffset()-j);
						}
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 12: {// arraydecl => type vid [ numconst ];
					Stablee element = new Stablee();
					analysisStack.pop();
					stateStack.pop();
					element.setArraySize(analysisStack.peek().getToken().nvalue);
					analysisStack.pop();
					stateStack.pop();
					analysisStack.pop();
					stateStack.pop();
					element.setVarname(analysisStack.peek().getToken().vid);
					element.setArray(true);
					element.setOffset(stab.getOffset());
					analysisStack.pop();
					stateStack.pop();
					element.setVartype(analysisStack.peek().getDataType());
					int w = analysisStack.peek().getDataType() == 1
							|| analysisStack.peek().getDataType() == 2 ? 4 : 1;
					element.setSize(w * element.getArraySize());
					// stab.setOffset(stab.getOffset() + element.getSize());
					stab.list.add(element);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 13: {// type => ctype;
					node.setDataType(analysisStack.peek().getDataType());
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 14: {// type => char;
					node.setDataType(3);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 15: {// ctype => int;
					node.setDataType(2);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 16: {// ctype => float;
					node.setDataType(1);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 17: {// vidlist => vid;
					node.setWidth(analysisStack.peek().getWidth() + 1);
					Stablee element = new Stablee();
					element.setVarname(analysisStack.peek().getToken().vid);
					if (stab.check(element.getVarname()) != -1) {
						String[] a = { "decal error" };
						sysTable.sysList.add(a);
						return sysTable;
					}
					stab.addlement(element);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 18: {// vidlist => vid , vidlist;
					node.setWidth(analysisStack.peek().getWidth() + 1);
					Stablee element = new Stablee();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.pop();
					stateStack.pop();
					element.setVarname(analysisStack.peek().getToken().vid);
					if (stab.check(element.getVarname()) != -1) {
						String[] a = { "decal error" };
						sysTable.sysList.add(a);
						return sysTable;
					}
					stab.addlement(element);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
//				case 23:{
//					
//				}
				case 42: {// assignment => asgmtvar := expression;
					Node node1 = analysisStack.peek();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.pop();
					stateStack.pop();
					sysTable.genSys("+", "$" + node1.getIndex(),"#0", 
							analysisStack.peek().getIndex());
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 43: {// asgmtvar => vid;
//					if(analysisStack.peek().getToken().vid.equals("b"))
//					{
//						int aa=1;
//						aa ++;
//					}
					int index = stab.check(analysisStack.peek().getToken().vid);
					if (index == -1) {
						String[] a = { "varible "
								+ analysisStack.peek().getToken().vid
								+ "not declared" };
						sysTable.sysList.add(a);
						return sysTable;
					}
					node.setDataType(stab.list.get(index).getVartype());
					index = stab.list.get(index).getOffset();
					node.setIndex(index);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 44: {// asgmtvar => vid [ numconst ];
					analysisStack.pop();
					stateStack.pop();
					Node node1 = analysisStack.peek();
					analysisStack.pop();
					stateStack.pop();
					analysisStack.pop();
					stateStack.pop();
					int index = stab.check(analysisStack.peek().getToken().vid);
					node.setDataType(stab.list.get(index).getVartype());
					if (index != -1) {
						if (stab.list.get(index).getArraySize() - 1 <= node1
								.getToken().nvalue
								|| node1.getToken().nvalue < 0) {
							String[] a = { "array overflow" };
							sysTable.sysList.add(a);
							return sysTable;
						}
						int w = stab.list.get(index).getVartype() == 1
								| stab.list.get(index).getVartype() == 2 ? 4
								: 1;
						index = stab.list.get(index).getOffset() + w
								* node1.getToken().nvalue;
						node.setIndex(index);
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 46: {// expression => cexpression;
					node.setIndex(analysisStack.peek().getIndex());
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 55: { // bexpression => cexpression != cexpression;
					Node node1 = analysisStack.peek();
					// dataType1 = analysisStack.peek().getDataType();
					// fvalue1 = analysisStack.peek().getFvalue();
					// nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					Node node2 = analysisStack.peek();
					// dataType2 = analysisStack.peek().getDataType();
					// fvalue2 = analysisStack.peek().getFvalue();
					// nvalue2 = analysisStack.peek().getNvalue();
					if (node1.getDataType() == 2 && node2.getDataType() == 2) {
						if (node1.getNvalue() == node2.getNvalue())
							node.setBvalue(true);
						else
							node.setBvalue(false);
					} else if (node1.getDataType() == 1
							&& node2.getDataType() == 1) {
						if (node1.getFvalue() == node2.getFvalue())
							node.setBvalue(true);
						else
							node.setBvalue(false);
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 56: { // E => E + T/**/
					// dataType1 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					Node node1 = analysisStack.peek();
					// fvalue1 = analysisStack.peek().getFvalue();
					// nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					Node node2 = analysisStack.peek();
					// dataType2 = analysisStack.peek().getDataType();
					// fvalue2 = analysisStack.peek().getFvalue();
					// nvalue2 = analysisStack.peek().getNvalue();
					if (node1.getDataType() == 2 && node2.getDataType() == 2) {// 如果是整数加整数
						node.setDataType(2);
						// node.setNvalue(node1.getNvalue() +
						// node2.getNvalue());
						int index = sysTable.malloc();
						sysTable.genSys("+", "$" + node1.getIndex(), "$"
								+ node2.getIndex(), index);
						if (node1.getIndex() < 0)
							sysTable.free(node1.getIndex());
						if (node2.getIndex() < 0)
							sysTable.free(node2.getIndex());
						node.setIndex(index);
					} else {// 如果浮点数加浮点数
						node.setDataType(1);
						// node.setFvalue(node1.getNvalue() +
						// node2.getNvalue()+node1.getFvalue() +
						// node2.getFvalue());
					}

					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 57: {// E => E - T/**/
					Node node1 = analysisStack.peek();
					// dataType1 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					// fvalue1 = analysisStack.peek().getFvalue();
					// nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					Node node2 = analysisStack.peek();
					// dataType2 = analysisStack.peek().getDataType();
					// fvalue2 = analysisStack.peek().getFvalue();
					// nvalue2 = analysisStack.peek().getNvalue();
					if (node1.getDataType() == 2 && node2.getDataType() == 2) {// 如果是整数加浮点数
						node.setDataType(2);
						int index = sysTable.malloc();
						sysTable.genSys("-", "$" + node2.getIndex(), "$"
								+ node1.getIndex(), index);
						node.setIndex(index);
						if (node1.getIndex() < 0)
							sysTable.free(node1.getIndex());
						if (node2.getIndex() < 0)
							sysTable.free(node2.getIndex());
						// node.setNvalue(nvalue1 - nvalue2);
					} else {// 如果是整数加整数
						node.setDataType(1);
						// node.setFvalue(nvalue1 - nvalue2 + fvalue1 -
						// fvalue2);
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 58: { // E=>T/**/
					node.setDataType(analysisStack.peek().getDataType());
					node.setIndex(analysisStack.peek().getIndex());
					if (analysisStack.peek().getDataType() == 1) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 59: { // t => t * f;/**/
					Node node1 = analysisStack.peek();
					// dataType1 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					// fvalue1 = analysisStack.peek().getFvalue();
					// nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					Node node2 = analysisStack.peek();
					// dataType2 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					// fvalue2 = analysisStack.peek().getFvalue();
					// nvalue2 = analysisStack.peek().getNvalue();
					if (node1.getDataType() == 1 || node2.getDataType() == 1) {// 如果是整数乘浮点数
						node.setDataType(1);
						if (node1.getDataType() == 1) {
							// node.setFvalue(fvalue1 * nvalue2);
						} else {
							// node.setFvalue(fvalue2 * nvalue1);
						}
					} else {// 如果是整数乘整数
						node.setDataType(2);
						int index = sysTable.malloc();
						sysTable.genSys("*", "$" + node1.getIndex(), "$"
								+ node2.getIndex(), index);
						node.setIndex(index);
						if (node1.getIndex() < 0)
							sysTable.free(node1.getIndex());
						if (node2.getIndex() < 0)
							sysTable.free(node2.getIndex());
						// node.setNvalue(nvalue1 * nvalue2);
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 60: { // t => t / f;/**/
					Node node1 = analysisStack.peek();
					// dataType1 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					// fvalue1 = analysisStack.peek().getFvalue();
					// nvalue1 = analysisStack.peek().getNvalue();
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					Node node2 = analysisStack.peek();
					// dataType2 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					// fvalue2 = analysisStack.peek().getFvalue();
					// nvalue2 = analysisStack.peek().getNvalue();
					if (node1.getDataType() == 1 || node2.getDataType() == 1) {// 如果是整数除浮点数
						node.setDataType(1);
						if (node1.getDataType() == 1) {
							if (node2.getNvalue() != 0) {
							}
							// node.setFvalue(fvalue1 / nvalue2);
							else {
								String[] a = { "error on line"
										+ (node.getToken()).linenum
										+ ":/ by zero" };
								sysTable.sysList.add(a);
								return sysTable;
							}

						} else {
							if (node2.getFvalue() != 0) {
							}
							// node.setFvalue(nvalue1 / fvalue2);
							else {
								String[] a = { "error on line"
										+ (node.getToken()).linenum
										+ ":/ by zero" };
								sysTable.sysList.add(a);
								return sysTable;
							}
						}
					} else {// 如果是整数除整数
						node.setDataType(2);
						if (node2.getNvalue() != 0) {
							// node.setNvalue(nvalue1 / nvalue2);
							int index = sysTable.malloc();
							sysTable.genSys("/", "$" + node1.getIndex(), "$"
									+ node2.getIndex(), index);
							node.setIndex(index);
							if (node1.getIndex() < 0)
								sysTable.free(node1.getIndex());
							if (node2.getIndex() < 0)
								sysTable.free(node2.getIndex());
						} else {
							String[] a = { "error on line"
									+ (node.getToken()).linenum + ":/ by zero" };
							sysTable.sysList.add(a);
							return sysTable;
						}
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 61: { // t=>f/**/
					node.setDataType(analysisStack.peek().getDataType());
					node.setIndex(analysisStack.peek().getIndex());
					if (analysisStack.peek().getDataType() == 1) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 62: { // f=>asgmtvar
					node.setDataType(analysisStack.peek().getDataType());
					node.setIndex(analysisStack.peek().getIndex());
					if (analysisStack.peek().getDataType() == 1) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else if (analysisStack.peek().getDataType() == 2) {
						node.setNvalue(analysisStack.peek().getNvalue());
					} 
						/**/
						// return "type mismatch on "
						// + analysisStack.peek().getLinenum();
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 63: {// f=>cconst/**/
					node.setDataType(analysisStack.peek().getDataType());
					node.setIndex(analysisStack.peek().getIndex());
					if (analysisStack.peek().getDataType() == 1) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 64: { // f => ( cexpression );/**/
					analysisStack.pop();
					stateStack.pop();
					Node node1 = new Node();
					node.setIndex(node1.getIndex());
					// dataType1 = analysisStack.peek().getDataType();
					// dataType : 1 float, 2 int 3 char 4 boolean
					// fvalue1 = analysisStack.peek().getFvalue();
					// nvalue1 = analysisStack.peek().getNvalue();
					// analysisStack.peek().setFather(node);
					analysisStack.pop();
					stateStack.pop();
					// analysisStack.peek().setFather(node);
					if (node1.getDataType() == 1) {
						node.setDataType(1);
						// node.setFvalue(fvalue1);
					} else {
						node.setDataType(2);
						// node.setNvalue(nvalue1);
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 65: {// const => charconst;/**/
					// dataType : 1 float, 2 int 3 char 4 boolean
					node.setDataType(3);
					node.setSvalue(analysisStack.peek().getToken().string
							.replace("'", ""));
					node.setIndex(analysisStack.peek().getIndex());
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 66: { // const => cconst;/**/
					node.setDataType(analysisStack.peek().getDataType());
					node.setIndex(analysisStack.peek().getIndex());
					if (analysisStack.peek().getDataType() == 1) {
						node.setFvalue(analysisStack.peek().getFvalue());
					} else {
						node.setNvalue(analysisStack.peek().getNvalue());
					}
					analysisStack.pop();
					stateStack.pop();
					break;
				}

				case 67: {// const => booleanconst;/**/
					node.setDataType(4);
					node.setIndex(analysisStack.peek().getIndex());
					node.setBvalue(analysisStack.peek().getToken().bvalue);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 68: { // cconst => floatconst; /**/
					node.setDataType(1);
					node.setIndex(analysisStack.peek().getIndex());
					node.setFvalue(analysisStack.peek().getToken().fvalue);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 69: { // cconst => numconst;
					node.setDataType(2);
					// node.setNvalue(analysisStack.peek().getToken().nvalue);
					int index = sysTable.malloc();
					node.setIndex(index);
					sysTable.genSys("+",  "#"
							+ analysisStack.peek().getToken().nvalue,"#0", index);
					analysisStack.pop();
					stateStack.pop();
					// System.out.println("!!");
					break;
				}
				case 70: { // cconst => - cconst;
					node.setDataType(analysisStack.peek().getDataType());
					if (node.getDataType() == 1)
						node.setFvalue(analysisStack.peek().getToken().fvalue);
					else {
						// node.setNvalue(analysisStack.peek().getToken().nvalue);
						int index = sysTable.malloc();
						sysTable.genSys("*", "#-1", "$"
								+ analysisStack.peek().getIndex(), index);
						if (analysisStack.peek().getIndex() < 0)
							sysTable.free(analysisStack.peek().getIndex());
					}

					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 71: { // booleanconst => true;
					node.setDataType(4);
					// node.setBvalue(true);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				case 72: { // booleanconst => false;
					node.setDataType(4);
					// node.setBvalue(false);
					analysisStack.pop();
					stateStack.pop();
					break;
				}
				default: {
					if (expressions[opcode][1].length() != 0) {
						// System.out.println(expressions[opcode][1].split(" ").length);
						for (int count = 0; count < (expressions[opcode][1]
								.split(" ").length); count++) {
							// symbolStack.pop();
							stateStack.pop();
							analysisStack.pop();
						}
					}
					// symbolStack.push(expressions[opcode][0]);

					// int leftcode = findLeft(expressions[opcode][0]);
				}
				}
				int leftcode = findLeft(expressions[opcode][0]);
				System.out.println("reduce:" + expressions[opcode][0] + " -> "
						+ expressions[opcode][1]);
				stateStack.push(actiongoto[stateStack.peek()][leftcode + 55]);// 查goto表
				// if (stateStack.peek() == 113) {
				// System.out.println("!");
				// }
				analysisStack.push(node);

			} else if (opcode == 233) {// 233 means accept
				System.out.println("Accepted!");
				break;
			}
		}
		System.out.println(stab.getOffset());

		return sysTable;
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
