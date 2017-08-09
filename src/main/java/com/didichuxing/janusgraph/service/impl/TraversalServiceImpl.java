package com.didichuxing.janusgraph.service.impl;

import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.service.TraversalService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhzy on 2017/8/3.
 */
@Service
public class TraversalServiceImpl implements TraversalService {

    @Autowired
    private Dao dao;

    @Override
    public Map<String, Object> generateNodesGraph() {
        List<Vertex> nodes = dao.findAllNodes();
        Map<String, Object> displayNodes = new HashMap<>();
        List<Map<String, Object>> displayNodesList = new ArrayList<>();
        for(Vertex displayNode: nodes){
            displayNodesList.add(dao.transferVertexToMap(displayNode));
        }
        displayNodes.put("nodes", displayNodesList);
        return displayNodes;
    }

    @Override
    public Map<String, Object> generateGraph(long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("nodes", new String[]{});
        result.put("links", new String[]{});
        List<Vertex> vertices = dao.findNeighborsNodesById(id);
        if(vertices.isEmpty()){
            return result;
        }
        List<Edge> edges = dao.findNeighborsEdgesById(id);

        List<Map<String, Object>> nodes = new ArrayList<>();
        Map<String, Integer> nodesId = new LinkedHashMap<>();
        List<Map<String, Object>> links = new ArrayList<>();

        int i = 0;
        for(Vertex vertex: vertices){
            nodes.add(dao.transferVertexToMap(vertex));
            System.out.println("++++++++++++" + vertex.property("nodeId").value().toString());
            nodesId.put(vertex.property("nodeId").value().toString(), i);
            i++;
        }
        for(Edge edge: edges){
            Map<String, Object> link = new HashMap<>();
            link.put("source", nodesId.get(edge.outVertex().property("nodeId").value().toString()));
            link.put("target", nodesId.get(edge.inVertex().property("nodeId").value().toString()));
            links.add(link);
        }

        result.put("nodes", nodes);
        result.put("links", links);

        return result;
    }
}
