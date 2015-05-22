package jgencode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Gencode {
	static 		int bnum = 1;
	public int genCode(Sys sysTable, int stablen,String defaultName,String customName) {

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
			String filename;
			if(!customName.equals("null"))
				{filename=customName;}
			else
				{filename=defaultName.substring(0, defaultName.length()-4).concat(".asm");}
			output = new BufferedWriter(new FileWriter("1.asm"));
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
			output.write("print: ");
			output.newLine();
			output.write("	MOV SI,offset X");
			output.newLine();
			output.write("	xor dx,dx");
			output.newLine();
			output.write("	mov bx,ax");
			output.newLine();
			output.write("	MOV CX,5");
			output.newLine();
			output.write("l1:div word ptr [SI]");
			output.newLine();
			output.write("	push dx");
			output.newLine();
			output.write("	CMP CX,1");
			output.newLine();
			output.write("JZ l2");
			output.newLine();
			output.write("	cmp dx,bx");
			output.newLine();
			output.write("	jz skip");
			output.newLine();
			output.write("	l2:    mov dl,al");
			output.newLine();
			output.write("	OR DL,30H");
			output.newLine();
			output.write("	mov ah,02h");
			output.newLine();
			output.write("	int 21h");
			output.newLine();
			output.write("skip:  pop ax");
			output.newLine();
			output.write("	xor dx,dx");
			output.newLine();
			output.write("	add SI,2");
			output.newLine();
			output.write("	LOOP l1 ");
			output.newLine();
			output.write("	ret");
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
			dotlist.add("call print");
			return dotlist;
		} else if(sys[0].equals(">")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("	mov bx," + sys[1].substring(1));
			dotlist.add("	mov ax,[bx]");
			dotlist.add("	mov bx," + sys[2].substring(1));
			dotlist.add("	cmp ax,[bx]");
			dotlist.add("	jle bn"+bnum);
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0FFFFH");
			dotlist.add("	mov [bx],ax");
			dotlist.add("	jmp bnext"+bnum);
			dotlist.add("bn"+bnum+":");
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0");
			dotlist.add("	mov [bx],ax");
			dotlist.add("bnext"+bnum+":");
			bnum++;
			return dotlist;
		}else if(sys[0].equals("<")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("	mov bx," + sys[1].substring(1));
			dotlist.add("	mov ax,[bx]");
			dotlist.add("	mov bx," + sys[2].substring(1));
			dotlist.add("	cmp ax,[bx]");
			dotlist.add("	jge bn"+bnum);
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0FFFFH");
			dotlist.add("	mov [bx],ax");
			dotlist.add("	jmp bnext"+bnum);
			dotlist.add("bn"+bnum+":");
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0");
			dotlist.add("	mov [bx],ax");
			dotlist.add("bnext"+bnum+":");
			bnum++;
			return dotlist;
		}else if(sys[0].equals("==")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("	mov bx," + sys[1].substring(1));
			dotlist.add("	mov ax,[bx]");
			dotlist.add("	mov bx," + sys[2].substring(1));
			dotlist.add("	cmp ax,[bx]");
			dotlist.add("	jne bn"+bnum);
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0FFFFH");
			dotlist.add("	mov [bx],ax");
			dotlist.add("	jmp bnext"+bnum);
			dotlist.add("bn"+bnum+":");
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0");
			dotlist.add("	mov [bx],ax");
			dotlist.add("bnext"+bnum+":");
			bnum++;
			return dotlist;
		}else if(sys[0].equals("<")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("	mov bx," + sys[1].substring(1));
			dotlist.add("	mov ax,[bx]");
			dotlist.add("	mov bx," + sys[2].substring(1));
			dotlist.add("	cmp ax,[bx]");
			dotlist.add("	je bn"+bnum);
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0FFFFH");
			dotlist.add("	mov [bx],ax");
			dotlist.add("	jmp bnext"+bnum);
			dotlist.add("bn"+bnum+":");
			dotlist.add("	mov bx,"+sys[3]);
			dotlist.add("	mov ax,0");
			dotlist.add("	mov [bx],ax");
			dotlist.add("bnext"+bnum+":");
			bnum++;
			return dotlist;
		}else if(sys[0].equals("no")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("no"+sys[1]);
			return dotlist;
		}else if(sys[0].equals("next")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("next"+sys[1]);
			return dotlist;
		}else if(sys[0].equals("if")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("	mov bx," + sys[1].substring(1));
			dotlist.add("	mov ax,[bx]");
			dotlist.add("	mov bx,0FFFFH");
			dotlist.add("	cmp ax,bx");
			dotlist.add("	jne no"+sys[3]);
			return dotlist;
		}else if(sys[0].equals("goto")){
			List<String> dotlist = new ArrayList<String>();
			dotlist.add("	jmp next"+sys[3]);
			return dotlist;
		}
		
		
		return null;

	}
}
