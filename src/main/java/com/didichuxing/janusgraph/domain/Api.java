package com.didichuxing.janusgraph.domain;

/**
 * Created by zhzy on 2017/7/18.
 */
public class Api {

    private String nodeId;
    private String nodeName;
    private String nodeTitle;

    private String[] inComingEdge;
    private String[] outGoingEdge;

    public String[] getOutGoingEdge() {
        return outGoingEdge;
    }

    public void setOutGoingEdge(String[] outGoingEdge) {
        this.outGoingEdge = outGoingEdge;
    }

    public String[] getInComingEdge() {
        return inComingEdge;
    }

    public void setInComingEdge(String[] inComingEdge) {
        this.inComingEdge = inComingEdge;
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



    public String toString(){
        return nodeId + nodeName + nodeTitle;
    }
}
