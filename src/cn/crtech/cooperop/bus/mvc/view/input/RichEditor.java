package cn.crtech.cooperop.bus.mvc.view.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class RichEditor extends BaseView {

	protected String label;
	protected boolean islabel;
	protected String value;
	protected String help;
	protected int cols=1;
	protected String maxlength;
	protected String minlength;
	protected String height;
	protected String toolbar;
//	protected String column_type;
//	protected Boolean encryption = false;
//	protected String create_action;
//	protected String modify_action;
//	protected String enter_action;
//	protected String out_action;
//	protected String dbl_action;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6971883718300369866L;

	

	public String getLabel() {
		return label;
	}

	public boolean isIslabel() {
		return islabel;
	}

	public String getValue() {
		return value;
	}

	public String getHelp() {
		return help;
	}

	public int getCols() {
		return cols;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public String getMinlength() {
		return minlength;
	}

	public String getHeight() {
		return height;
	}

	public String getToolbar() {
		return toolbar;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setIslabel(boolean islabel) {
		this.islabel = islabel;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setToolbar(String toolbar) {
		this.toolbar = toolbar;
	}

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
		if(cols == 0){
			cols = 1;
		}
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
		value = null;
		help = null;
		cols = 1;
		maxlength = null;
		minlength = null;
		height = null;
		toolbar = null;
		super.release();
	}

}