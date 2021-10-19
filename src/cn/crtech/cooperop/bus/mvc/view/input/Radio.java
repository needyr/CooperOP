package cn.crtech.cooperop.bus.mvc.view.input;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.mvc.view.BaseView;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Radio extends BaseView {

	protected String label;
	protected boolean islabel = false;
	protected boolean isherf = false;
	protected String value;
	protected String help;
	protected int cols=1;
	protected String dictionary;
	protected String action;
	protected String height;
	protected String column_type;
	protected boolean encryption = false;
	protected boolean readonly = false;
	protected String create_action;
	protected String modify_action;
	protected String enter_action;
	protected String out_action;
	protected String dbl_action;
	
	
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

	public String getDictionary() {
		return dictionary;
	}

	public String getAction() {
		return action;
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

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public void setAction(String action) {
		this.action = action;
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
		if (!CommonFun.isNe(dictionary)) {
			try {
				List<Record> options = Dictionary.listOptions(dictionary);
				for (Record option : options) {
					content.append("<cooperop-option value=\"" + option.getString("dictlist") + "\" label=\"" + option.getString("dictlist") + "\" />\r\n");
				}
			} catch (Exception e) {
				throw new JspException(e);
			}
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
		dictionary = null;
		action = null;
		height = null;
		column_type = null;
		encryption = false;
		readonly = false;
		create_action = null;
		modify_action = null;
		enter_action = null;
		out_action = null;
		dbl_action = null;
		super.release();
	}

}