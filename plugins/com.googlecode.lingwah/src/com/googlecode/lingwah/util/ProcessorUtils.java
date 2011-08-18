package com.googlecode.lingwah.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.MatchProcessor;

public class ProcessorUtils {
	private static final Map<Class<?>, Map<String, Method>> __visitMethodCache= new HashMap<Class<?>, Map<String,Method>>();
	private static final Map<Class<?>, Map<String, Method>> __leaveMethodCache= new HashMap<Class<?>, Map<String,Method>>();
	private static final Method __defaultVisitMethod;
	private static final Method __defaultLeaveMethod;
	static {
		try {
			__defaultVisitMethod= MatchProcessor.class.getMethod("visit", new Class[] { Match.class });
			__defaultLeaveMethod= MatchProcessor.class.getMethod("leave", new Class[] { Match.class });
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
	public static Method findVisitMethod(MatchProcessor visitor, Match node) {
		

		Method visitMethod= null;

		Class<?> visitorClass= visitor.getClass();
		Map<String, Method> visitMethods= __visitMethodCache.get(visitorClass);
		if (visitMethods == null) 
			__visitMethodCache.put(visitorClass, visitMethods= new HashMap<String, Method>());
		String methodName= node.getParser().getLabel();
		methodName= "visit"+methodName.substring(0, 1).toUpperCase()+methodName.substring(1);
		visitMethod= visitMethods.get(methodName);

		if (visitMethod == null) 
			return __defaultVisitMethod;

		return visitMethod;
	}

	
	/**
	 * Find the appropriate leave method for the given node and visitor.
	 */
	public static Method findLeaveMethod(MatchProcessor visitor, Match node) {

		Method visitMethod= null;

		Class<?> visitorClass= visitor.getClass();
		Map<String, Method> visitMethods= __leaveMethodCache.get(visitorClass);
		if (visitMethods == null) 
			__leaveMethodCache.put(visitorClass, visitMethods= new HashMap<String, Method>());
		String methodName= node.getParser().getLabel();
		methodName= "leave"+methodName.substring(0, 1).toUpperCase()+methodName.substring(1);
		visitMethod= visitMethods.get(methodName);

		if (visitMethod == null) 
			return __defaultLeaveMethod;

		return visitMethod;
	}

}
