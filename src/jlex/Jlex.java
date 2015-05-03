package jlex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mwq
 *
 */
public class Jlex {
	static List<Tokens> tokenlist = new ArrayList<Tokens>();

	public List<Tokens> lexicalAnalysis(String filename) {
		int lines = 1;
		filename = "1.txt";
		String line = null;
		File file = new File(filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				System.out.print("line " + lines + ": ");
				line = line.replace('\t', ' ');
				line = line.replace("=", " = ");
				line = line.replace(">", " > ");
				line = line.replace("<", " < ");
				line = line.replace(":", " : ");
				line = line.replace(",", " , ");
				line = line.replace(";", " ; ");
				line = line.replace("(", " ( ");
				line = line.replace(")", " ) ");
				line = line.replace("+", " + ");
				line = line.replace("-", " - ");
				line = line.replace("*", " * ");
				line = line.replace("/", " / ");
				line = line.replace("]", " ] ");
				line = line.replace("[", " [ ");
				line = line.replace("{", " { ");
				line = line.replace("}", " } ");
				line = line.replace("@@", " @@ ");
				String[] tokens = line.split(" ");
//				System.out.println(line);
				for (int i = 0; i < tokens.length; i++) {
					if (tokens[i].length() != 0) {
						// to be continued 0414
						if (tokens[i].equals("begin")) {
							System.out.print(" start ");
							Tokens token = new Tokens();
							token.token = "begin";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("end")) {
							System.out.print(" end");
							Tokens token = new Tokens();
							token.token = "end";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("main")) {
							System.out.print(" main");
							Tokens token = new Tokens();
							token.token = "main";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("function")) {
							System.out.print(" function");
							Tokens token = new Tokens();
							token.token = "function";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("int")) {
							System.out.print(" int");
							Tokens token = new Tokens();
							token.token = "int";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("float")) {
							System.out.print(" float ");
							Tokens token = new Tokens();
							token.token = "float";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("char")) {
							System.out.print(" char");
							Tokens token = new Tokens();
							token.token = "char";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals(",")) {
							System.out.print(" ,");
							Tokens token = new Tokens();
							token.token = ",";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("if")) {
							System.out.print(" if");
							Tokens token = new Tokens();
							token.token = "if";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("else")) {
							System.out.print(" else ");
							Tokens token = new Tokens();
							token.token = "else";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("then")) {
							System.out.print(" then");
							Tokens token = new Tokens();
							token.token = "then";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("fi")) {
							System.out.print(" fi");
							Tokens token = new Tokens();
							token.token = "fi";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("for")) {
							System.out.print(" for");
							Tokens token = new Tokens();
							token.token = "for";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("rof")) {
							System.out.print(" rof");
							Tokens token = new Tokens();
							token.token = "rof";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("switch")) {
							System.out.print(" switch");
							Tokens token = new Tokens();
							token.token = "switch";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("case")) {
							System.out.print(" esac");
							Tokens token = new Tokens();
							token.token = "esac";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("ends")) {
							System.out.print(" ends");
							Tokens token = new Tokens();
							token.token = "ends";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("while")) {
							System.out.print(" while");
							Tokens token = new Tokens();
							token.token = "while";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("do")) {
							System.out.print(" do");
							Tokens token = new Tokens();
							token.token = "do";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("elihw")) {
							System.out.print(" elihw");
							Tokens token = new Tokens();
							token.token = "elihw";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("and")) {
							System.out.print(" and");
							Tokens token = new Tokens();
							token.token = "and";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("or")) {
							System.out.print(" or");
							Tokens token = new Tokens();
							token.token = "or";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("not")) {
							System.out.print(" not");
							Tokens token = new Tokens();
							token.token = "not";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("(")) {
							System.out.print(" (");
							Tokens token = new Tokens();
							token.token = "(";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals(")")) {
							System.out.print(" )");
							Tokens token = new Tokens();
							token.token = ")";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("[")) {
							System.out.print(" [");
							Tokens token = new Tokens();
							token.token = "[";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("]")) {
							System.out.print(" ]");
							Tokens token = new Tokens();
							token.token = "]";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("{")) {
							System.out.print(" {");
							Tokens token = new Tokens();
							token.token = "{";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("}")) {
							System.out.print(" }");
							Tokens token = new Tokens();
							token.token = "}";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("+")) {
							System.out.print(" +");
							Tokens token = new Tokens();
							token.token = "+";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("-")) {
							System.out.print(" -");
							Tokens token = new Tokens();
							token.token = "-";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("*")) {
							System.out.print(" *");
							Tokens token = new Tokens();
							token.token = "*";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("/")) {
							System.out.print(" /");
							Tokens token = new Tokens();
							token.token = "/";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals(";")) {
							System.out.print(" semi");
							Tokens token = new Tokens();
							token.token = "semi";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals(":")) {
							if (tokens[i + 2].equals("=")) {
								System.out.print(" :=");
								Tokens token = new Tokens();
								token.token = ":=";
								token.linenum = lines;
								tokenlist.add(token);
								i = i + 2;
								continue;
							} else {
								System.out.print(" :");
								Tokens token = new Tokens();
								token.token = ":";
								token.linenum = lines;
								tokenlist.add(token);
								continue;
							}
						} else if (tokens[i].equals(">")) {
							System.out.print(" >");
							Tokens token = new Tokens();
							token.token = ">";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("<")) {
							System.out.print("< ");
							Tokens token = new Tokens();
							token.token = "<";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("=")) {
							if (tokens[i + 2].equals("=")) {
								System.out.print(" ==");
								i = i + 2;
								continue;
							} else {
								System.out.print(" '=' is unrecognized");
								continue;
							}
						} else if (tokens[i].equals("!")) {
							if (tokens[i + 2].equals("=")) {
								System.out.print(" !=");
								Tokens token = new Tokens();
								token.token = "!=";
								token.linenum = lines;
								tokenlist.add(token);
								i = i + 2;
								continue;
							} else {
								System.out.print(" '!' is unrecognized");
								continue;
							}
						} else if (tokens[i].equals("@@")) {
							break;
						} else if (tokens[i].equals("read")) {
							System.out.print(" read");
							Tokens token = new Tokens();
							token.token = "read";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("write")) {
							System.out.print(" write");
							Tokens token = new Tokens();
							token.token = "write";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("writeln")) {
							System.out.print(" writeln");
							Tokens token = new Tokens();
							token.token = "writeln";
							token.linenum = lines;
							tokenlist.add(token);
							continue;
						} 	else if (tokens[i].equals("true")) {
							System.out.print(" true");
							Tokens token = new Tokens();
							token.token = "true";
							token.bvalue=true;
							token.linenum = lines;
							token.dataType =4;
							tokenlist.add(token);
							continue;
						} else if (tokens[i].equals("false")) {
							System.out.print(" false");
							Tokens token = new Tokens();
							token.token = "false";
							token.bvalue= false;
							token.linenum = lines;
							token.dataType =4;
							tokenlist.add(token);
							continue;
						} else if (isVid(tokens[i])) {
							System.out.print(" vid=" + tokens[i]);
							Tokens token = new Tokens();
							token.token = "vid";
							token.linenum = lines;
							token.vid = tokens[i];
							tokenlist.add(token);
							continue;
						} else if (isPid(tokens[i])) {
							System.out.print(" pid=" + tokens[i]);
							Tokens token = new Tokens();
							token.token = "pid";
							token.linenum = lines;
							token.pid = tokens[i];
							tokenlist.add(token);
							continue;
						} else if (isNumeric(tokens[i])) {
							System.out.print(" numconst="
									+ Integer.parseInt(tokens[i]));
							Tokens token = new Tokens();
							token.token = "numconst";
							token.dataType = 2;
							token.linenum = lines;
							token.nvalue = Integer.parseInt(tokens[i]);
							tokenlist.add(token);
							continue;
						} else if (isFloat(tokens[i])) {
							System.out.print(" floatconst="
									+ Float.parseFloat(tokens[i]));
							Tokens token = new Tokens();
							token.token = "floatconst";
							token.linenum = lines;
							token.dataType= 1;
							token.fvalue = Float.parseFloat(tokens[i]);
							tokenlist.add(token);
							continue;
						} else if (isString(tokens[i])) {
							System.out.print(" string=" + tokens[i]);
							Tokens token = new Tokens();
							token.token = "string";
							token.linenum = lines;
							token.string = tokens[i];
							tokenlist.add(token);
							continue;
						} else if (isChar(tokens[i])) {
							System.out.print(" charconst=" + tokens[i]);
							Tokens token = new Tokens();
							token.token = "charconst";
							token.linenum = lines;
							token.dataType = 3;
							token.string = tokens[i];
							tokenlist.add(token);
							continue;
						} else
							System.out.print(" wrong formate in this token!");
					}
				}
				System.out.print("\n");
				lines++;
			}
			reader.close();
			Tokens token = new Tokens();
			token.token = "#";
			tokenlist.add(token);
			return tokenlist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isFloat(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isVid(String str) {
		Pattern pattern = Pattern.compile("[a-z][0-9a-zA-Z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isPid(String str) {
		Pattern pattern = Pattern.compile("[A-Z][0-9a-zA-Z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isString(String str) {
		Pattern pattern = Pattern.compile("[\"][0-9a-zA-Z]*[\"]");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static boolean isChar(String str) {
		Pattern pattern = Pattern.compile("['][0-9a-zA-Z][']");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
