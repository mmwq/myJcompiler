package jgencode;

import java.util.ArrayList;
import java.util.List;

public class Sys {
	int maxSize = 0;
	// private int curSize = 0;
	public int Stringnum = 0;
	// static int maxNum = 0;
	int temp = 0;
	String[] express = new String[4];
	public List<String[]> sysList = new ArrayList<String[]>();
	public List<Integer> tempList = new ArrayList<Integer>();

	public void genSys(String oprator, String op1, String op2, int dest) {
		String[] sys = { oprator, op1, op2, Integer.toString(dest) };
		sysList.add(sys);
	}

	public int malloc() {
		if (tempList.size() == 0) {
			tempList.add(1);
			return -1;
		} else if (this.findMax() > tempList.size()) {
			int t = this.findMin();
			tempList.add(t);
			return t * -1;
		} else {
			int t = tempList.size() + 1;
			tempList.add(t);
			if (maxSize < tempList.size())
				maxSize = tempList.size() + 1;
			return t * -1;
		}
	}

	public void free(int index) {
		index = index * -1;
		for (int i = 0; i < tempList.size(); i++) {
			if (index == tempList.get(i)) {
				tempList.remove(i);
				// curSize--;
				return;
			}
		}
	}

	private int findMax() {
		int max = 0;
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(max) < tempList.get(i))
				max = i;
		}
		return tempList.get(max);
	}

	private int findMin() {
		boolean flag = true;
		for (int i = 1; i <= this.findMax(); i++) {
			for (int j = 0; j < tempList.size(); j++) {
				if (i == tempList.get(j))
					flag = false;
			}
			if (flag)
				return i;
		}
		return -1;
	}
}
