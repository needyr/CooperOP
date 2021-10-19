package cn.crtech.cooperop.bus.rdms;

import java.util.ArrayList;
import java.util.List;

public class TreeResult {
	
	private String title;
	
	private String key;
	
	private List<TreeResult> children = new ArrayList<TreeResult>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<TreeResult> getChildren() {
		return children;
	}

	public void setChildren(List<TreeResult> children) {
		this.children = children;
	};
	

}
