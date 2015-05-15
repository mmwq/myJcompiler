package jgencode;

import java.io.BufferedWriter;
import java.io.FileWriter;

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
			output.write(".386");
			output.newLine();
			output.write(".MODEL FLAT");
			output.newLine();
			output.write("ExitProcess PROTO NEAR32 stdcall, dwExitCode:DWORD");
			output.newLine();
			output.write("INCLUDE io.h            ; header file for input/output");
			output.newLine();
			output.write("cr      EQU     0dh     ; carriage return character");
			output.newLine();
			output.write("Lf      EQU     0ah     ; line feed");
			output.newLine();
			output.write(".STACK  4096            ; reserve 4096-byte stack");
			output.newLine();
			output.write(".DATA                   ; reserve storage for data");
			output.newLine();
			output.write("dataseg       BYTE   " + dataSize + " DUP (?)");
			output.newLine();
			// output.write("label1   BYTE    cr, Lf, \"The result is \"");
			// output.write("result     BYTE    11 DUP (?)");
			// output.write("        BYTE    cr, Lf, 0");
			output.write(".CODE                           ; start of main program code");
			output.newLine();
			output.write("_start:");
			output.newLine();
			for (int i = 0; i < sysTable.sysList.size(); i++) {
				String[] s =this.genDots(sysTable.sysList.get(i));
				for(int j=0;j<s.length;j++)
				{
					output.write("	"+s[j]);
					output.newLine();
				}
			}
			output.write("	INVOKE  ExitProcess, 0  ; exit with return code 0");
			output.newLine();
			output.write("PUBLIC _start ");
			output.newLine();
			output.write("END ");
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	private String[] genDots(String[] sys) {
		if (sys[0].equals("+")) {
			String s1,s2;
			if (sys[1].contains("$")) {
				s1 = "mov eax,dataseg+"+sys[0].substring(1);
			} else{
				s1 = "mov eax,"+sys[0].substring(1);
			}
			if((sys[2].contains("$"))){
				s2 = "add eax,dataseg+"+sys[1].substring(1);
			} else{
				s2 = "add eax,"+sys[1].substring(1);
			}
				String s3 = "mov dataseg+"+sys[3]+",eax";
			String [] s = {s1,s2,s3};
			return s;
		} else if(sys[0].equals("-")){
			String s1,s2;
			if (sys[1].contains("$")) {
				s1 = "mov eax,dataseg+"+sys[0].substring(1);
			} else{
				s1 = "mov eax,"+sys[0].substring(1);
			}
			if((sys[2].contains("$"))){
				s2 = "sub eax,dataseg+"+sys[1].substring(1);
			} else{
				s2 = "sub eax,"+sys[1].substring(1);
			}
				String s3 = "mov dataseg+"+sys[3]+",eax";
			String [] s = {s1,s2,s3};
			return s;
		}else if(sys[0].equals("*")){
			String s1,s2;
			if (sys[1].contains("$")) {
				s1 = "mov eax,dataseg+"+sys[0].substring(1);
			} else{
				s1 = "mov eax,"+sys[0].substring(1);
			}
			if((sys[2].contains("$"))){
				s2 = "mov eax,dataseg+"+sys[1].substring(1);
			} else{
				s2 = "mov eax,"+sys[1].substring(1);
			}
				String s3 = "imul ebx";
				String s4 = "mov dataseg+"+sys[3]+",eax";
			String [] s = {s1,s2,s3,s4};
			return s;
		}else if(sys[0].equals("/")){
			String s1,s2;
			if (sys[1].contains("$")) {
				s1 = "mov eax,dataseg+"+sys[0].substring(1);
			} else{
				s1 = "mov eax,"+sys[0].substring(1);
			}
			if((sys[2].contains("$"))){
				s2 = "mov eax,dataseg+"+sys[1].substring(1);
			} else{
				s2 = "mov eax,"+sys[1].substring(1);
			}
				String s3 = "div ebx";
				String s4 = "mov dataseg+"+sys[3]+",eax";
			String [] s = {s1,s2,s3,s4};
			return s;
		} else
			return null;
		
		
	}
}
