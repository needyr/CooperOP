package cn.crtech.cooperop.bus.mvc.view.layout;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.mvc.view.BaseView;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TableField extends BaseView {
	protected String name;
	protected String label;
	protected String datatype;
	protected String dictionary;
	protected String format;
	protected boolean sort = false;
	protected String defaultsort;
	protected boolean hidden = false;
	protected boolean editable = false;
	protected String maxlength;
	protected String tsize;
	protected String maxdigits;
	protected String enter_action;
	protected String dbl_action;
	protected String modify_action;
	protected String width;
	protected String app_field;
	protected boolean is_merge = false;

	public boolean getIs_merge() {
		return is_merge;
	}

	public void setIs_merge(boolean is_merge) {
		this.is_merge = is_merge;
	}

	public String getApp_field() {
		return app_field;
	}

	public void setApp_field(String app_field) {
		this.app_field = app_field;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getEnter_action() {
		return enter_action;
	}

	public String getDbl_action() {
		return dbl_action;
	}

	public String getModify_action() {
		return modify_action;
	}

	public void setEnter_action(String enter_action) {
		this.enter_action = enter_action;
	}

	public void setDbl_action(String dbl_action) {
		this.dbl_action = dbl_action;
	}

	public void setModify_action(String modify_action) {
		this.modify_action = modify_action;
	}

	public String getName() {
		return name;
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

	public boolean isHidden() {
		return hidden;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public String getTsize() {
		return tsize;
	}

	public String getMaxdigits() {
		return maxdigits;
	}

	public String getEnteraction() {
		return enter_action;
	}

	public String getDblaction() {
		return dbl_action;
	}

	public String getModifyaction() {
		return modify_action;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public void setTsize(String tsize) {
		this.tsize = tsize;
	}

	public void setMaxdigits(String maxdigits) {
		this.maxdigits = maxdigits;
	}

	public void setEnteraction(String enteraction) {
		this.enter_action = enteraction;
	}

	public void setDblaction(String dblaction) {
		this.dbl_action = dblaction;
	}

	public void setModifyaction(String modifyaction) {
		this.modify_action = modifyaction;
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
	 * @return EVAL_BODY_INCLUDE if the tag wants to process body, SKIP_BODY if
	 *         it does not want to process it.
	 * @throws JspException
	 *             if an error occurred while processing this tag
	 * @todo Implement this javax.servlet.jsp.tagext.Tag method
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		if (!CommonFun.isNe(dictionary)) {
			try {
				Record dics = new Record();
				List<Record> options = Dictionary.listOptions(dictionary);
				for (Record option : options) {
					dics.put(option.getString("dictlist"), option.getString("dictlist"));
				}
				getDynamicAttributes().put("dictionary", CommonFun.object2Json(dics).replaceAll("\"", "&quot;"));
			} catch (Exception e) {
				throw new JspException(e);
			}
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
		name = null;
		label = null;
		datatype = null;
		dictionary = null;
		sort = false;
		defaultsort = null;
		hidden = false;
		editable = false;
		maxlength = null;
		tsize = null;
		maxdigits = null;
		enter_action = null;
		dbl_action = null;
		app_field = null;
		modify_action = null;
		super.release();
	}

}