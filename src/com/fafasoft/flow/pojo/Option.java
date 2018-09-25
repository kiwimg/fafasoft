package com.fafasoft.flow.pojo;

/**
 * Created by IntelliJ IDEA.
 * User: wangwei
 * Date: 2010-1-2
 * Time: 11:00:01
 * To change this template use File | Settings | File Templates.
 */
public class Option {
    private String id;
    private String text;
    private String type;
    private String nodeId;
    private String parentId;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getParentId() {
		if("null".equals(parentId) || "".equals(parentId)) {
			parentId="0";
		}
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String toString() {
		return this.text;
	}
}
