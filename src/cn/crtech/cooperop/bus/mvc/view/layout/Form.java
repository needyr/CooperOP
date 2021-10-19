package cn.crtech.cooperop.bus.mvc.view.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class Form extends BaseView {
	
	protected String label;
	protected int cols = 4;
	protected String color = "";
	protected int border = 1;
	protected String icon;
	protected boolean extendable = false;
	protected boolean collapsed = false;
	
	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

	public boolean isExtendable() {
		return extendable;
	}

	public void setExtendable(boolean extendable) {
		this.extendable = extendable;
	}

	public String getLabel() {
		return label;
	}

	public int getCols() {
		return cols;
	}

	public String getColor() {
		return color;
	}

	public int getBorder() {
		return border;
	}

	public String getIcon() {
		return icon;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
		super.release();
		label = null;
		cols = 4;
		color = null;
		border = 1;
		icon = null;
		extendable = false;
		collapsed = false;
	}

}