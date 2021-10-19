package cn.crtech.cooperop.bus.mvc.view.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import cn.crtech.cooperop.bus.mvc.view.BaseView;

public class Slider extends BaseView {

	protected String label;
	protected boolean islabel = false;
	protected String value;
	protected String help;
	protected int cols=1;
	protected String datatype;
	protected String format;
	protected double max;
	protected double step;
	protected double min;

	

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

	public String getDatatype() {
		return datatype;
	}

	public String getFormat() {
		return format;
	}

	public double getMax() {
		return max;
	}

	public double getStep() {
		return step;
	}

	public double getMin() {
		return min;
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

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public void setMin(double min) {
		this.min = min;
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
		label = null;
		islabel = false;
		value = null;
		help = null;
		cols = 0;
		datatype = null;
		format = null;
		max = 0;
		step = 0;
		min = 0;
		super.release();
	}

}