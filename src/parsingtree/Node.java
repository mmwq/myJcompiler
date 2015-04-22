package parsingtree;

import jlex.Tokens;

public class Node {
	private Tokens token= null;
	private int inh;
	private int nvalue=0;
	private float fvalue=0;
	private Node father = null;
	private String nodeName = null;
	private int width;
	private boolean forn = false; //false: int  true : float
	
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isForn() {
		return forn;
	}

	public void setForn(boolean forn) {
		this.forn = forn;
	}
	
}
