package com.didichuxing.janusgraph.service.impl;

import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.service.TraversalService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> generateGraph() {
        return null;
    }
}
