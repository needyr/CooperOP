package cn.crtech.cooperop.bus.mvc.view.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class Option extends BaseView {

	protected String value;
	protected boolean disabled = false;
	protected String label;
	

	public String getValue() {
		return value;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getLabel() {
		return label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setLabel(String label) {
		this.label = label;
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
		value = null;
		disabled = false;
		label = null;
		super.release();
	}

}