package parsingtree;

public class Stablee {
	private String varname;
	private int vartype;
	private int offset;
	private int size = 0;
	private boolean isArray=false;
	private int arraySize;
	public String getVarname() {
		return varname;
	}
	public void setVarname(String varname) {
		this.varname = varname;
	}
	public int getVartype() {
		return vartype;
	}
	public void setVartype(int vartype) {
		this.vartype = vartype;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isArray() {
		return isArray;
	}
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	public int getArraySize() {
		return arraySize;
	}
	public void setArraySize(int arraySize) {
		this.arraySize = arraySize;
	}	
	
}
