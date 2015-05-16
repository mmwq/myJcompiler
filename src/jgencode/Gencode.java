package jgencode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Gencode {
	public int genCode(Sys sysTable, int stablen) {
		System.out.println("After--------");
		for (int i = 0; i < sysTable.sysList.size(); i++) {
			String s1 = sysTable.sysList.get(i)[0];
			String s2 = sysTable.sysList.get(i)[1];
			if (s2.substring(0, 1).equals("$")) {
				int temp = Integer.parseInt(s2.substring(1));
				if (temp < 0) {
					temp = (temp * -1 - 1) * 4 + stablen;
					s2 = "$" + temp;
				}
			}
			String s3 = sysTable.sysList.get(i)[2];
			if (s3.charAt(0) == '$') {
				int temp = Integer.parseInt(s3.substring(1));
				if (temp < 0) {
					temp = (temp * -1 - 1) * 4 + stablen;
					s3 = "$" + temp;
				}
			}
			String s4 = sysTable.sysList.get(i)[3];
			int temp = Integer.parseInt(s4);
			if (temp < 0) {
				temp = (temp * -1 - 1) * 4 + stablen;
				s4 = Integer.toString(temp);
			}
			String[] s = { s1, s2, s3, s4 };
			sysTable.sysList.set(i, s);
		}
		for (int i = 0; i < sysTable.sysList.size(); i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(sysTable.sysList.get(i)[j] + " ");
			}
			System.out.println();
		}

		int dataSize = stablen + sysTable.maxSize * 4;
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter("1.s"));
			output.write("DATAS SEGMENT");
			output.newLine();
			output.write("dataseg       BYTE   " + dataSize + " DUP (?)");
			output.newLine();
			output.write("X DW 10000,1000,100,10,1");
			output.newLine();
			output.write("DATAS ENDS");
			output.newLine();
			output.write("CODES SEGMENT");
			output.newLine();
			output.write("	ASSUME CS:CODES,DS:DATAS");
			output.newLine();
			output.write("START:");
			output.newLine();
			output.write("	MOV AX,DATAS");
			output.newLine();
			output.write("	MOV DS,AX");
			output.newLine();
			// output.write("label1   BYTE    cr, Lf, \"The result is \"");
			// output.write("result     BYTE    11 DUP (?)");
			// output.write("        BYTE    cr, Lf, 0");
			for (int i = 0; i < sysTable.sysList.size(); i++) {
				List<String> s = this.genDots(sysTable.sysList.get(i));
				if (s != null) {
					for (int j = 0; j < s.size(); j++) {
						output.write("	" + s.get(j));
						output.newLine();
					}
				}
			}
			output.write("	MOV AH,4CH");
			output.newLine();
			output.write("	INT 21H");
			output.newLine();
			output.write("CODES ENDS");
			output.newLine();
			output.write("END START");
			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private List<String> genDots(String[] sys) {
		if (sys[0].equals("+")) {
			List<String> dotlist = new ArrayList<String>();
			String s0, s1, s2;
			if (sys[1].contains("$")) {
				s0 = "	mov bx," + sys[1].substring(1);
				dotlist.add(s0);
				s1 = "	mov ax,[bx]";
				dotlist.add(s1);
			} else {
				s1 = "	mov ax," + sys[1].substring(1);
				dotlist.add(s1);
			}
			if ((sys[2].contains("$"))) {
				s0 = "	mov bx," + sys[2].substring(1);
				dotlist.add(s0);
				s2 = "	add ax,[bx]";
				dotlist.add(s2);
			} else {
				s2 = "	add ax," + sys[2].substring(1);
				dotlist.add(s2);
			}
			String s3 = "	mov bx," + sys[3];
			String s4 = "	mov [bx],ax";
			dotlist.add(s3);
			dotlist.add(s4);
			return dotlist;
		} else if (sys[0].equals("-")) {
			List<String> dotlist = new ArrayList<String>();
			String s0, s1, s2;
			if (sys[1].contains("$")) {
				s0 = "	mov bx," + sys[1].substring(1);
				dotlist.add(s0);
				s1 = "	mov ax,[bx]";
				dotlist.add(s1);
			} else {
				s1 = "	mov ax," + sys[1].substring(1);
				dotlist.add(s1);
			}
			if ((sys[2].contains("$"))) {
				s0 = "	mov bx," + sys[2].substring(1);
				dotlist.add(s0);
				s2 = "	sub ax,[bx]";
				dotlist.add(s2);
			} else {
				s2 = "	sub ax," + sys[2].substring(1);
				dotlist.add(s2);
			}
			String s3 = "	mov bx," + sys[3];
			String s4 = "	mov [bx],ax";
			dotlist.add(s3);
			dotlist.add(s4);
			return dotlist;
		} else if (sys[0].equals("*")) {/* orders */
			List<String> dotlist = new ArrayList<String>();
			String s0, s1, s2;
			if (sys[1].contains("$")) {
				s0 = "	mov bx," + sys[1].substring(1);
				dotlist.add(s0);
				s1 = "	mov ax,[bx]";
				dotlist.add(s1);
			} else {
				s1 = "	mov ax," + sys[1].substring(1);
				dotlist.add(s1);
			}
			if ((sys[2].contains("$"))) {
				s0 = "	mov bx," + sys[2].substring(1);
				dotlist.add(s0);
				s2 = "	mov bx,[bx]";
				dotlist.add(s2);
			} else {
				s2 = "	mov bx," + sys[2].substring(1);
				dotlist.add(s2);
			}
			String s3 = "	imul bx";
			String s4 = "	mov bx," + sys[3];
			String s5 = "	mov [bx],ax";
			dotlist.add(s3);
			dotlist.add(s4);
			dotlist.add(s5);
			return dotlist;
		} else if (sys[0].equals("/")) {/* orders */
			List<String> dotlist = new ArrayList<String>();
			String s0, s1, s2;
			if (sys[1].contains("$")) {
				s0 = "	mov bx," + sys[1].substring(1);
				dotlist.add(s0);
				s1 = "	mov ax,[bx]";
				dotlist.add(s1);
			} else {
				s1 = "	mov ax," + sys[1].substring(1);
				dotlist.add(s1);
			}
			if ((sys[2].contains("$"))) {
				s0 = "	mov bx," + sys[2].substring(1);
				dotlist.add(s0);
				s2 = "	div  byte ptr DS:[bx]";
				dotlist.add(s2);
			} else {
				s2 = "	mov bx," + sys[2].substring(1);
				dotlist.add(s2);
				dotlist.add("div bl");
			}
			String s4 = "	mov bx," + sys[3];
			String s5 = "	mov [bx],ax";
			// dotlist.add(s3);
			dotlist.add(s4);
			dotlist.add(s5);
			return dotlist;
		} else if (sys[0].equals("outvar")) {
			List<String> dotlist = new ArrayList<String>();
			String s1, s0;
			if (sys[1].contains("$")) {
				s0 = "	mov bx," + sys[1].substring(1);
				dotlist.add(s0);
				s1 = "	mov ax,[bx]";
				dotlist.add(s1);
			} else {
				s1 = "	mov ax," + sys[1].substring(1);
				dotlist.add(s1);
			}
			dotlist.add("	MOV SI,offset X");
			dotlist.add("	xor dx,dx");
			dotlist.add("	mov bx,ax");
			dotlist.add("	MOV CX,5");
			dotlist.add("l1:div word ptr [SI]");
			dotlist.add("	push dx");
			dotlist.add("	CMP CX,1");
			dotlist.add("JZ l2");
			dotlist.add("	cmp dx,bx");
			dotlist.add("	jz skip");
			dotlist.add("	l2:    mov dl,al");
			dotlist.add("	OR DL,30H");
			dotlist.add("	mov ah,02h");
			dotlist.add("	int 21h");
			dotlist.add("skip:  pop ax");
			dotlist.add("	xor dx,dx");
			dotlist.add("	add SI,2");
			dotlist.add("	LOOP l1 ");
			return dotlist;
		}
		return null;

	}
}
