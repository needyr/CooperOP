package cn.crtech.cooperop.bus.mvc.view.input;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class Selecttree extends BaseView {

	protected String label;
	protected boolean islabel = false;
	protected boolean isherf = false;
	protected String value;
	protected int cols=1;
	protected String action;
	protected String nodeid;
	protected String pid;
	protected String nodename;
	

	public String getNodeid() {
		return nodeid;
	}

	public String getPid() {
		return pid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getLabel() {
		return label;
	}

	public boolean isIslabel() {
		return islabel;
	}

	public boolean isIsherf() {
		return isherf;
	}

	public String getValue() {
		return value;
	}


	public int getCols() {
		return cols;
	}


	public String getAction() {
		return action;
	}


	public void setLabel(String label) {
		this.label = label;
	}

	public void setIslabel(boolean islabel) {
		this.islabel = islabel;
	}

	public void setIsherf(boolean isherf) {
		this.isherf = isherf;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}


	public void setAction(String action) {
		this.action = action;
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
		if(cols == 0){
			cols = 1;
		}
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
		label = null;
		islabel = false;
		isherf = false;
		value = null;
		cols = 1;
		action = null;
		nodeid = null;
		nodename = null;
		pid = null;
		super.release();
	}

}