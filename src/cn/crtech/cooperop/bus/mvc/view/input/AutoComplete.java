package cn.crtech.cooperop.bus.mvc.view.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class AutoComplete extends BaseView {

	protected String label;
	protected boolean islabel = false;
	protected boolean isherf = false;
	protected boolean htmlEscape = false;
	protected String value;
	protected String text;
	protected String tips;
	protected String help;
	protected int cols=1;
	protected String action;
	protected boolean editable;
	protected boolean readonly = false;
	protected String maxlength;
	protected String minlength;
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

	

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
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

	public boolean isHtmlEscape() {
		return htmlEscape;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public String getTips() {
		return tips;
	}

	public String getHelp() {
		return help;
	}

	public int getCols() {
		return cols;
	}

	public String getAction() {
		return action;
	}

	public boolean isEditable() {
		return editable;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public String getMinlength() {
		return minlength;
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

	public void setHtmlEscape(boolean htmlEscape) {
		this.htmlEscape = htmlEscape;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
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
		isherf = false;
		htmlEscape = false;
		value = null;
		text = null;
		tips = null;
		help = null;
		cols = 1;
		action = null;
		editable = false;
		maxlength = null;
		minlength = null;
		readonly = false;
		super.release();
	}

}