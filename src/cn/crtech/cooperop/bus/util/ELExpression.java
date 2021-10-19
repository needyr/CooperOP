package cn.crtech.cooperop.bus.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.el.ExpressionEvaluatorImpl;

public class ELExpression {
	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", "120032016");
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("userid", "120032016");
		params2.put("age", "32");
		params2.put("sex", "鐢�");
		params.put("info", params2);
		try {
			System.out.println(ELExpression.excuteExpression("${info.age}", params));
		} catch (ELException e) {
			e.printStackTrace();
		}
	}
	
	public static Object excuteExpression(String elExpression, Map<String, Object> params) throws ELException {
		if (CommonFun.isNe(elExpression)) return elExpression;
		ExpressionEvaluatorImpl expressionEvaluatorImpl = new ExpressionEvaluatorImpl();
		ParameterResolver vr = new ParameterResolver();
		vr.setVariables(params);
		return expressionEvaluatorImpl.evaluate(elExpression, Object.class, vr, null);
	}
	
	public static String excuteExpressionD(String expression, Map<String, Object> map) {
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			expression = CommonFun.replaceOnlyStr(expression, "$[" + key + "]", CommonFun.isNe(map.get(key)) ? "" : map.get(key) + "");
		}
		while (expression.indexOf("$[") > -1) {
			String t1 = expression.substring(0, expression.indexOf("$[")); 
			String t2 = expression.substring(expression.indexOf("$[")); 
			t2 = t2.substring(t2.indexOf("]") + 1);
			expression = t1 + t2;
		}
		return expression;
	}

	public static String excuteExpressionA(String expression, Map<String, Object> map) {
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			expression = CommonFun.replaceOnlyStr(expression, "@[" + key + "]", CommonFun.isNe(map.get(key)) ? "" : map.get(key) + "");
		}
		while (expression.indexOf("@[") > -1) {
			String t1 = expression.substring(0, expression.indexOf("@[")); 
			String t2 = expression.substring(expression.indexOf("@[")); 
			t2 = t2.substring(t2.indexOf("]") + 1);
			expression = t1 + t2;
		}
		return expression;
	}


}

class ParameterResolver implements VariableResolver {
	Map<String, Object> params = new HashMap<String, Object>();
	public void setVariables(Map<String, Object> params) {
		this.params = params;
	}
	public Object resolveVariable(String arg0)
			throws ELException {
		return params.get(arg0);
	}
}

