package cn.crtech.cooperop.bus.mvc.view.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class File extends BaseView {

	protected String label;
	protected boolean islabel;
	protected String value;
	protected String help;
	protected int cols=1;
	protected boolean autoupload = false;
	protected String filetypes;
	protected String maxsize;
	protected String minsize;
	protected String maxlength;
	protected String minlength;
	protected String height;
	protected String column_type;
	protected boolean encryption = false;
	protected boolean deleteable = true;

	protected boolean addable = true;
	protected String create_action;
	protected String modify_action;
	protected String enter_action;
	protected String out_action;
	protected String dbl_action;
	
	public boolean isDeleteable() {
		return deleteable;
	}
	
	public boolean isAddable() {
		return addable;
	}
	
	public void setDeleteable(boolean deleteable) {
		this.deleteable = deleteable;
	}
	
	public void setAddable(boolean addable) {
		this.addable = addable;
	}
	
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

	public boolean isAutoupload() {
		return autoupload;
	}

	public String getFiletypes() {
		return filetypes;
	}

	public String getMaxsize() {
		return maxsize;
	}

	public String getMinsize() {
		return minsize;
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

	public void setAutoupload(boolean autoupload) {
		this.autoupload = autoupload;
	}

	public void setFiletypes(String filetypes) {
		this.filetypes = filetypes;
	}

	public void setMaxsize(String maxsize) {
		this.maxsize = maxsize;
	}

	public void setMinsize(String minsize) {
		this.minsize = minsize;
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
		value = null;
		help = null;
		cols = 1;
		autoupload = false;
		deleteable = true;
		addable = true;
		filetypes = null;
		maxsize = null;
		minsize = null;
		maxlength = null;
		minlength = null;
		height = null;
		column_type = null;
		encryption = false;
		create_action = null;
		modify_action = null;
		enter_action = null;
		out_action = null;
		dbl_action = null;
		super.release();
	}

}