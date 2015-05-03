package parsingtree;

import jlex.Tokens;

public class Node {
	private Tokens token = null;
	private int inh;
	private int nvalue = 0;
	private float fvalue = 0;
	private Node father = null;
	private String nodeName = null;
	private String svalue = null;
	private boolean bvalue;
	private int width;
	private int dataType = 0;
	private int linenum = 0;
	private String idName = null;
	// dataType : 1 float, 2 int 3 char 4 boolean
	public Tokens getToken() {
		return token;
	}

	public void setToken(Tokens token) {
		this.token = token;
	}

	public int getInh() {
		return inh;
	}

	public void setInh(int inh) {
		this.inh = inh;
	}

	public int getNvalue() {
		return nvalue;
	}

	public void setNvalue(int nvalue) {
		this.nvalue = nvalue;
	}

	public float getFvalue() {
		return fvalue;
	}

	public void setFvalue(float fvalue) {
		this.fvalue = fvalue;
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}

	public boolean isBvalue() {
		return bvalue;
	}

	public void setBvalue(boolean bvalue) {
		this.bvalue = bvalue;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getLinenum() {
		return linenum;
	}

	public void setLinenum(int linenum) {
		this.linenum = linenum;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}
	
}
