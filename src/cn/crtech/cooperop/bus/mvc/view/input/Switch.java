package cn.crtech.cooperop.bus.mvc.view.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class Switch extends BaseView {

	protected String label;
	protected boolean islabel = false;
	protected boolean isherf = false;
	protected String value;
	protected String help;
	protected int cols=1;
	protected String column_type;
	protected boolean encryption = false;
	protected boolean readonly = false;
	protected String create_action;
	protected String modify_action;
	protected String enter_action;
	protected String out_action;
	protected String dbl_action;
	protected String onvalue;
	protected String ontext;
	protected String offvalue;
	protected String offtext;
	
	
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

	public String getValue() {
		return value;
	}

	public String getHelp() {
		return help;
	}

	public int getCols() {
		return cols;
	}

	public String getColumn_type() {
		return column_type;
	}

	public boolean isEncryption() {
		return encryption;
	}

	public String getCreate_action() {
		return create_action;
	}

	public String getModify_action() {
		return modify_action;
	}

	public String getEnter_action() {
		return enter_action;
	}

	public String getOut_action() {
		return out_action;
	}

	public String getDbl_action() {
		return dbl_action;
	}

	public String getOnvalue() {
		return onvalue;
	}

	public String getOntext() {
		return ontext;
	}

	public String getOffvalue() {
		return offvalue;
	}

	public String getOfftext() {
		return offtext;
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

	public void setHelp(String help) {
		this.help = help;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}

	public void setEncryption(boolean encryption) {
		this.encryption = encryption;
	}

	public void setCreate_action(String create_action) {
		this.create_action = create_action;
	}

	public void setModify_action(String modify_action) {
		this.modify_action = modify_action;
	}

	public void setEnter_action(String enter_action) {
		this.enter_action = enter_action;
	}

	public void setOut_action(String out_action) {
		this.out_action = out_action;
	}

	public void setDbl_action(String dbl_action) {
		this.dbl_action = dbl_action;
	}

	public void setOnvalue(String onvalue) {
		this.onvalue = onvalue;
	}

	public void setOntext(String ontext) {
		this.ontext = ontext;
	}

	public void setOffvalue(String offvalue) {
		this.offvalue = offvalue;
	}

	public void setOfftext(String offtext) {
		this.offtext = offtext;
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
		value = null;
		help = null;
		cols = 1;
		column_type = null;
		encryption = false;
		readonly = false;
		create_action = null;
		modify_action = null;
		enter_action = null;
		out_action = null;
		dbl_action = null;
		onvalue = null;
		ontext = null;
		offvalue = null;
		offtext = null;
		super.release();
	}

}