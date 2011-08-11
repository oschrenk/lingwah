package com.googlecode.lingwah.node;

/**
 * Denotes the type of syntax element that a node represents.
 * Each node has an associated syntax element type.
 * 
 * @author Ted Stockwell
 *
 */
public class SyntaxElementType {
	
	private String typeID;
	
	public SyntaxElementType(String typeID) {
		this.typeID= typeID;
	}
	public SyntaxElementType(SyntaxElementType superType, String typeID) {
		this.typeID= superType.getTypeID()+"|"+typeID;
	}
	public SyntaxElementType(SyntaxElementType superType, SyntaxElementType type) {
		this.typeID= superType.getTypeID()+"|"+type.getTypeID();
	}
	public SyntaxElementType(Class<?> nodeType) {
		typeID= nodeType.getName();
		Class<?> parent= nodeType.getSuperclass();
		while (Match.class.isAssignableFrom(parent)) {
			typeID= parent.getName()+"|"+typeID;
		}
	}
	
	public String getTypeID() {
		return typeID;
	}
	
	 /**
     * Determines if the node type represented by this <code>NodeType</code> object 
     * is either the same as, or is a superclass of, the node type 
     * represented by the specified <code>NodeType</code> parameter. 
     * It returns <code>true</code> if so;
     * otherwise it returns <code>false</code>. 
     */
  	public boolean isAssignableFrom(SyntaxElementType type) {
  		return type.typeID.startsWith(typeID);
  	}
}
