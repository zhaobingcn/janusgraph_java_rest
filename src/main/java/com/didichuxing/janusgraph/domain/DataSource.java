package com.didichuxing.janusgraph.domain;

/**
 * Created by zhzy on 2017/7/18.
 */
public class DataSource {
    private String nodeId;
    private String nodeName;
    private String nodeTitle;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String[] inComingEdge;

    public String[] getInComingEdge() {
        return inComingEdge;
    }

    public void setInComingEdge(String[] inComingEdge) {
        this.inComingEdge = inComingEdge;
    }

    private String[] outGoingEdge;

    public String[] getOutGoingEdge() {
        return outGoingEdge;
    }

    public void setOutGoingEdge(String[] outGoingEdge) {
        this.outGoingEdge = outGoingEdge;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
}
