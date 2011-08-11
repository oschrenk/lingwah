package com.googlecode.lingwah.node;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.googlecode.lingwah.annotations.ElementType;

public class VisitorUtils {
	private static final Map<Class<?>, Map<String, Method>> __visitMethodCache= new HashMap<Class<?>, Map<String,Method>>();
	private static final Map<Class<?>, Map<String, Method>> __leaveMethodCache= new HashMap<Class<?>, Map<String,Method>>();
	private static final Method __defaultVisitMethod;
	private static final Method __defaultLeaveMethod;
	static {
		try {
			__defaultVisitMethod= MatchVistor.class.getMethod("visit", new Class[] { Match.class });
			__defaultLeaveMethod= MatchVistor.class.getMethod("leave", new Class[] { Match.class });
		} catch (Exception e) {
			throw new RuntimeException("Internal Error", e);
		}
	}

	
	/**
	 * Find the appropriate visit method for the given node and visitor.
	 * Use annotations find visit method dynamically based on node id.
	 * visit method will be named "visit"+id
	 * 
	 */
	public static Method findVisitMethod(MatchVistor visitor, Match node) {
		

		Method visitMethod= null;

		Class<?> visitorClass= visitor.getClass();
		Map<String, Method> visitMethods= __visitMethodCache.get(visitorClass);
		if (visitMethods == null) 
			__visitMethodCache.put(visitorClass, visitMethods= new HashMap<String, Method>());
		visitMethod= visitMethods.get(node.getSyntaxElementType());

		if (visitMethod == null) {
			for (Method method:visitorClass.getMethods()) {
				ElementType nodeId= method.getAnnotation(ElementType.class);
				if (nodeId == null)
					continue;
				if (!Pattern.matches(nodeId.value(), node.getSyntaxElementType().getTypeID()))
					continue;
				String n= method.getName();
				if (!n.startsWith("visit"))
					continue;

				visitMethods.put(node.getSyntaxElementType().getTypeID(), method);
				visitMethod= method;
				break;
			}
		}

		if (visitMethod == null) 
			return __defaultVisitMethod;

		return visitMethod;
	}

	
	/**
	 * Find the appropriate leave method for the given node and visitor.
	 */
	public static Method findLeaveMethod(MatchVistor visitor, Match node) {

		Method visitMethod= null;

		Class<?> visitorClass= visitor.getClass();
		Map<String, Method> visitMethods= __leaveMethodCache.get(visitorClass);
		if (visitMethods == null) 
			__leaveMethodCache.put(visitorClass, visitMethods= new HashMap<String, Method>());
		visitMethod= visitMethods.get(node.getSyntaxElementType());

		if (visitMethod == null) {
			for (Method method:visitorClass.getMethods()) {
				ElementType nodeId= method.getAnnotation(ElementType.class);
				if (nodeId == null)
					continue;
				if (!Pattern.matches(nodeId.value(), node.getSyntaxElementType().getTypeID()))
					continue;
				String n= method.getName();
				if (!n.startsWith("leave"))
					continue;

				visitMethods.put(node.getSyntaxElementType().getTypeID(), method);
				visitMethod= method;
				break;
			}
		}

		if (visitMethod == null) 
			return __defaultLeaveMethod;

		return visitMethod;
	}

}
