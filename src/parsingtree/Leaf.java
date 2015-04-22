package parsingtree;

import jlex.Tokens;

public class Leaf {
	private Tokens token;
	private Node father;
	private int width;
	public Tokens getToken() {
		return token;
	}

	public void setToken(Tokens token) {
		this.token = token;
	}

	public Node getFather() {
		return father;
	}

	public void setFather(Node father) {
		this.father = father;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
