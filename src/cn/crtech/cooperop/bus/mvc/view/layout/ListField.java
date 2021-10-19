package cn.crtech.cooperop.bus.mvc.view.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class ListField extends BaseView {
	
	protected String code;
	protected String label;
	protected String datatype;
	protected String dictionary;
	protected String format;
	protected boolean sort = false;
	protected String defaultsort;

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public String getDatatype() {
		return datatype;
	}

	public String getDictionary() {
		return dictionary;
	}

	public String getFormat() {
		return format;
	}

	public boolean isSort() {
		return sort;
	}

	public String getDefaultsort() {
		return defaultsort;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public void setDefaultsort(String defaultsort) {
		this.defaultsort = defaultsort;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6971883718300369866L;

	/**
	 * Prepare for evaluation of the body.
	 * 
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.BodyTag method
	 */
	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	/**
	 * Process the start tag for this instance.
	 * 
	 * @return EVAL_BODY_INCLUDE if the tag wants to process body, SKIP_BODY if it does not want to process it.
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.Tag method
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
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
		int i = super.doEndTag();
		
		return i;
	}

	/**
	 * Release state.
	 * 
	 * @see Tag#release
	 */

	@Override
	public void release() {
		code = null;
		label = null;
		datatype = null;
		dictionary = null;
		format = null;
		sort = false;
		defaultsort = null;
		super.release();
	}

}