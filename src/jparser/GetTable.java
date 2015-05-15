package jparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class GetTable {
	static int[][] actiongoto = new int[185][103];
	static String[][] expressions;

	public int[][] gett(String[][] expressions1) {
		expressions=expressions1;
		File file = new File("LALR·ÖÎö±í.htm");
		BufferedReader reader = null;
		try {
			String line = null;
			int count = -1, row = -3;
			reader = new BufferedReader(new FileReader(file));
			while (!(line = reader.readLine()).equals("</table>")) {
				// System.out.println(line);
				if (line.equals("<tr>")) {
					row++;
					count = -1;
					continue;
				} else if (line.equals("</tr>")) {
					continue;
				} else if (row >= 0) {
					if (count != -1) {
						line = line.replace("<td nowrap>", "");
						line = line.replace("</td>", "");
						line = line.replaceFirst("&nbsp;", " ");//separate operation name from 
						if (!line.equals(" ")) {				//the string
							String[] rors = line.split(" ");
							if (rors.length != 1) {
								if (rors[0].equals("shift")) {
									actiongoto[row][count] = Integer
											.parseInt(rors[1]);
									// System.out.println("shift "
									// + actiongoto[row][count] + " "
									// + row + " " + count);
								} else if (rors[0].equals("reduce")) {
									rors[1] = rors[1].replace("&nbsp;", " ");
									rors[1] = rors[1].replace("¦Å", "");
									String[] expression = rors[1].split(" -> ");//get the expression
									int expressionNumber = findexpnum(expression);
									actiongoto[row][count] = expressionNumber
											* -1;
								}

							} else if (rors[0].equals("accept")) {
								actiongoto[row][count] = 233;
//								 System.out.println("WTF"+row+" "+count);
							} else {
								actiongoto[row][count] = Integer
										.parseInt(rors[0]);
							}

						}
						count++;
					} else
						count++;
				}
			}
			reader.close();
//			for (int m = 0; m < 182; m++) { // Check if it works
//				for (int n = 0; n < 102; n++) {
//					if (actiongoto[m][n] != 0) {
//						System.out
//								.println(m + " " + n + " " + actiongoto[m][n]);
//					}
//				}
//			}
			return actiongoto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//find index of a given expression
	private static int findexpnum(String[] expression) {
		if (expression.length == 1) {
			for (int i = 0; i < expressions.length; i++) {
				if ((expression[0].equals(expressions[i][0]))
						&& (expressions[i][1].length() == 0)) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < expressions.length; i++) {
				if ((expression[0].equals(expressions[i][0]))
						&&expression[1].equals(expressions[i][1])) {
					return i;
				}
			}
		}
		return -1;
	}
}
