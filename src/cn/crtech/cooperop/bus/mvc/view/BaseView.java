package cn.crtech.cooperop.bus.mvc.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.Tag;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public abstract class BaseView extends BodyTagSupport implements DynamicAttributes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7456026601756283409L;

	private final Hashtable<String, Object> dynamicAttributes = new Hashtable<String, Object>();

	protected HashMap<String, Object> attributes = new HashMap<String, Object>();

	protected long lastModifyTime = 0;

	protected StringBuffer content;

	/**
	 * Prepare for evaluation of the body.
	 * 
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.BodyTag method
	 */
	@Override
	public void doInitBody() throws JspException {

	}

	/**
	 * Process the start tag for this instance.
	 * 
	 * @return EVAL_BODY_INCLUDE if the tag wants to process body, SKIP_BODY if it does not want to process it.
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.Tag method
	 */
	@Override
	public int doStartTag() throws JspException {
		loadAttributes();

		String sview = this.getClass().getName();
		sview = sview.substring(sview.lastIndexOf(".") + 1).toLowerCase();
		content = new StringBuffer();
		if (this.getClass().equals(Page.class)) {
			content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		} else {
			Tag parent = getParent();
			while(!(parent.getClass().equals(BaseView.class) || parent.getClass().getSuperclass().equals(BaseView.class))){
				parent = parent.getParent();
			}
			if (parent != null && !this.getClass().equals(Page.class)) {
				content.append("<cooperop-html ><![CDATA[" + ((BodyTagSupport) parent).getBodyContent().getString() + "]]></cooperop-html>\r\n");
				((BodyTagSupport) parent).getBodyContent().clearBody();
			}
		}
		content.append("<cooperop-" + sview);
		Iterator<String> it = attributes.keySet().iterator();

		if (this.getClass().equals(Page.class)) {
			HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
			content.append(" contextpath=\"" + req.getContextPath() + "\"");
			content.append(" xmlns:html=\"http://www.w3.org/1999/xhtml\"");
		}
		while (it.hasNext()) {
			String key = it.next();
			if (attributes.get(key) != null) {
				content.append(" " + key + "=\"" + attributes.get(key) + "\"");
			}
		}
		content.append(">");
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * Process the end tag for this instance.
	 * 
	 * @return indication of whether to continue evaluating the JSP page.
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.Tag method
	 */
	@Override
	public int doEndTag() throws JspException {
		String sview = this.getClass().getName();
		sview = sview.substring(sview.lastIndexOf(".") + 1).toLowerCase();

		if (bodyContent != null) {
			content.append("<cooperop-html ><![CDATA[" + bodyContent.getString() + "]]></cooperop-html>\r\n");
		}
		content.append("</cooperop-" + sview + ">\r\n");

		if (!this.getClass().equals(Page.class)) {
			Tag parent = getParent();
			while(!(parent.getClass().equals(BaseView.class) || parent.getClass().getSuperclass().equals(BaseView.class))){
				parent = parent.getParent();
			}
			((BaseView)parent).content.append(content);
			content = null;
		}

		dynamicAttributes.clear();
		return (EVAL_PAGE);
	}

	/**
	 * Release state.
	 * 
	 * @see Tag#release
	 */

	@Override
	public void release() {
		bodyContent = null;
		dynamicAttributes.clear();
		super.release();
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		if (value != null) {
			dynamicAttributes.put(localName, value);
		}
	}

	public Enumeration<String> getDynamicAttributeNames() {
		return dynamicAttributes.keys();
	}

	public Object getDynamicAttribute(String key) {
		return dynamicAttributes.get(key);
	}

	public Hashtable<String, Object> getDynamicAttributes() {
		return dynamicAttributes;
	}

	private void loadAttributes() {
		attributes.clear();

		for (Class<?> clazz = this.getClass(); clazz != BaseView.class.getSuperclass(); clazz = clazz.getSuperclass()) {

			Field[] f = clazz.getDeclaredFields();

			if (f != null) {
				Method m = null;
				for (int i = 0; i < f.length; i++) {
					if (!f[i].getType().isPrimitive() && f[i].getType() != String.class) {
						continue;
					}
					String name = f[i].getName();
					String get = "get" + CommonFun.capitalize(name);
					String set = "set" + CommonFun.capitalize(name);
					if (f[i].getType() == boolean.class || f[i].getType() == Boolean.TYPE) {
						get = "is" + CommonFun.capitalize(name);
					}
					boolean hasget = false;
					boolean hasset = false;
					try {
						m = clazz.getDeclaredMethod(set, f[i].getType());
						if (m != null) {
							hasset = true;
						}
					} catch (Exception e) {

					}
					try {
						m = clazz.getDeclaredMethod(get);
						if (m != null) {
							hasget = true;
						}
					} catch (Exception e) {
						get = "get" + CommonFun.capitalize(name);
						try {
							m = clazz.getDeclaredMethod(get);
						} catch (Exception e1) {
							;
						}
						if (m != null) {
							hasget = true;
						}
					}

					if (hasget && hasset) {
						try {
							Object val = m.invoke(this);
							attributes.put(name, val);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		attributes.putAll(dynamicAttributes);

		// Weblogic存在id不作为动态属性存在的问题
		if (!attributes.containsKey("id")) {
			attributes.put("id", getId());
		}
	}
	
	protected StringBuffer result2xml(String nodename, Result rs) {
		StringBuffer content = new StringBuffer();

		content.append("<cooperop-" + nodename + "s>\r\n");
		for (Record r : rs.getResultset()) {
			content.append("<cooperop-" + nodename);
			Iterator<String> it = r.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (r.get(key) != null) {
					content.append(" " + key + "=\"" + r.getString(key) + "\"");
				}
			}
			content.append(">\r\n");
			content.append("</cooperop-" + nodename + ">\r\n");
		}
		content.append("</cooperop-" + nodename + "s>\r\n");
		
		return content;
		
	}
}
