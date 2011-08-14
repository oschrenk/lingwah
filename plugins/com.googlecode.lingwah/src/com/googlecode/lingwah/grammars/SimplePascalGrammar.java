package com.googlecode.lingwah.grammars;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.common.WhitespaceParser;

/**
 * A simple grammar similar to Pascal
 * @author Ted Stockwell
 */
public class SimplePascalGrammar extends Grammar {
	public final Parser all= anyChar();
	
	public final Parser digit = cho(str("0"), str("1"), str("2"), str("3"), str("4"), str("5"), str("6"), str("7"), str("8"), str("9"));
	
	public final Parser character = cho(str("A"), str("B"), str("C"), str("D"), str("E"), str("F"), str("G"),
			str("H"), str("I"), str("J"), str("K"), str("L"), str("M"), str("N"), 
			str("O"), str("P"), str("Q"), str("R"), str("S"), str("T"), str("U"), 
			str("V"), str("W"), str("X"), str("Y"), str("Z"));
	
	public final Parser identifier = seq(character, zeroOrMore(cho(character,digit)));
	
	public final Parser number = seq(opt(str("-")), digit , zeroOrMore(digit));
	
	public final Parser string = seq(str("\"") , zeroOrMore(all.excluding(str("\""))), str("\""));
	
	public final Parser assignment = seq(identifier, str(":=") , cho( number, identifier, string ));
	
	public final Parser whitespace = new WhitespaceParser();
	
	public final Parser program = seq(str("PROGRAM") , whitespace , identifier , whitespace ,
	           str("BEGIN") , whitespace ,
	           zeroOrMore(seq(assignment, str(";"), whitespace )),
	           str("END."));
	
	public SimplePascalGrammar() {
		init();
	}
}
