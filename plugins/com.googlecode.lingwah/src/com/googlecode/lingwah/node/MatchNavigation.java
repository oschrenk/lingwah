package com.googlecode.lingwah.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;

public class MatchNavigation
{

	@SuppressWarnings("unchecked")
	public static <T extends Match> List<T> findAllByType(Match root, Parser type) {
		List<T> matches = new ArrayList<T>();
		List<Match> todo = new ArrayList<Match>();
		HashSet<Match> done = new HashSet<Match>();
		todo.addAll(root.getChildren());

		while (!todo.isEmpty()) 
		{
			Match node= todo.remove(0);
			if (done.contains(node))
				continue;
			
			if (node.getParser() == type)
				matches.add((T)node);
			
			todo.addAll(node.getChildren());
		}

		return matches;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Match> T findFirstByType(Match root, Parser type) {
		List<Match> todo = new ArrayList<Match>();
		HashSet<Match> done = new HashSet<Match>();
		todo.addAll(root.getChildren());

		while (!todo.isEmpty()) 
		{
			Match node= todo.remove(0);
			if (done.contains(node))
				continue;
			
			if (node.getParser() == type)
				return (T)node;
		}

		return null;
	}

	public static Match findChildByType(Match match, Parser parser) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Match> findChildrenByType(Match match,
			ParserReference parser) {
		// TODO Auto-generated method stub
		return null;
	}	

}
