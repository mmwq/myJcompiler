package parsingtree;

import java.util.ArrayList;
import java.util.List;

public class Stable {
	public List<Stablee> list = new ArrayList<Stablee>();
//	public List<Stable> subTablelist = new ArrayList<Stable>();
//	public String pid;
//	private Stable father;
	private int offset = 0;

	// public void setFather
	public void addlement(Stablee element) {
		this.offset= this.offset+element.getSize();
		list.add(element);
	}


	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int check(String vn){
		for(int i = 0; i< this.list.size();i++){
			if(this.list.get(i).getVarname().equals(vn))
				return i;
		}
		return -1;
	}

}
