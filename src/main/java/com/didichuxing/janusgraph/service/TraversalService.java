package com.didichuxing.janusgraph.service;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhzy on 2017/8/3.
 */
public interface TraversalService {


    public Map<String, Object> generateNodesGraph();

    public Map<String, Object> generateGraph(long id);

    public Map<String, Object> generateGraph(String label, String nodeId);

    public Map<String, Object> generateGraph(String label, String nodeId, int depth);

    public Map<String, Object> generateGraph(long id, int depth);

    public Map<String, Object> generateUpStreamGraph(long id, int depth);

    public Map<String, Object> generateUpStreamGraph(String label, String nodeId, int depth);

    public Map<String, Object> generateDownStreamGraph(long id, int depth);

    public Map<String, Object> generateDownStreamGraph(String label, String nodeId, int depth);

}
