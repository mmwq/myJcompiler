package parsingtree;

import jlex.Tokens;

public class Ptree {
	public Node makeNode(String nodename){
		Node node = new Node();
		node.setNodeName(nodename);
		return node;
	}
	public Node makeLeaf(Tokens token){
		Node node = new Node();;
		node.setToken(token);
		node.setNodeName(token.token);
		return node;
	}
}
