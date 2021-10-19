package cn.crtech.cooperop.bus.mvc.view.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.view.BaseView;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class Table extends BaseView {
	protected int cols =4;
	protected int height;
	protected boolean fitwidth = true;
	protected boolean autoload=false;
	protected boolean sort=false;
	protected String action;
	protected String total;
	protected String treeaction;
	protected String key;
	protected String label;
	protected String icon;
	protected String color;
	protected int limit = Integer.parseInt(SystemConfig.getSystemConfigValue("global", "perpage", "25"));
	protected int border=1;
	protected String recordSubmitAction;
	protected String recordDeleteAction;
	protected String recordAddtAction;
	protected String recordCheckAction;
	protected String recordMoveAction;
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

	public int getCols() {
		return cols;
	}

	public int getHeight() {
		return height;
	}

	public boolean isFitwidth() {
		return fitwidth;
	}

	public boolean isAutoload() {
		return autoload;
	}

	public boolean isSort() {
		return sort;
	}

	public String getAction() {
		return action;
	}

	public String getTotal() {
		return total;
	}

	public String getTreeaction() {
		return treeaction;
	}

	public String getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public String getIcon() {
		return icon;
	}

	public String getColor() {
		return color;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getBorder() {
		return border;
	}

	public String getRecordSubmitAction() {
		return recordSubmitAction;
	}

	public String getRecordDeleteAction() {
		return recordDeleteAction;
	}

	public String getRecordAddtAction() {
		return recordAddtAction;
	}

	public String getRecordCheckAction() {
		return recordCheckAction;
	}

	public String getRecordMoveAction() {
		return recordMoveAction;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setFitwidth(boolean fitwidth) {
		this.fitwidth = fitwidth;
	}

	public void setAutoload(boolean autoload) {
		this.autoload = autoload;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setTreeaction(String treeaction) {
		this.treeaction = treeaction;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public void setRecordSubmitAction(String recordSubmitAction) {
		this.recordSubmitAction = recordSubmitAction;
	}

	public void setRecordDeleteAction(String recordDeleteAction) {
		this.recordDeleteAction = recordDeleteAction;
	}

	public void setRecordAddtAction(String recordAddtAction) {
		this.recordAddtAction = recordAddtAction;
	}

	public void setRecordCheckAction(String recordCheckAction) {
		this.recordCheckAction = recordCheckAction;
	}

	public void setRecordMoveAction(String recordMoveAction) {
		this.recordMoveAction = recordMoveAction;
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
			cols = 4;
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
		cols = 4;
		height = 1;
		fitwidth = false;
		autoload = false;
		sort = false;
		action = null;
		total = null;
		treeaction = null;
		key = null;
		label = null;
		icon = null;
		color = null;
		border = 1;
		recordSubmitAction = null;
		recordDeleteAction = null;
		recordAddtAction = null;
		recordCheckAction = null;
		recordMoveAction = null;
		extendable = false;
		collapsed = false;
		super.release();
	}

}