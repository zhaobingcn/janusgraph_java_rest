package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.Canal;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.CanalDao;
import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhzy on 2017/7/21.
 */
@Repository
public class CanalDaoImpl implements CanalDao{

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();

    @Autowired
    private Dao dao;


    @Override
    public void addNode(Canal canal) {
        Vertex node = janusgraph.graph.addVertex(Label.CANAL);
        node.property("nodeId", canal.getNodeId());
        node.property("nodeTitle", canal.getNodeTitle());
        node.property("nodeName", canal.getNodeName());

        for (String edgeId : canal.getInComingEdge()) {
            if(dao.findVertexByNodeId(edgeId) != null) {
                dao.findVertexByNodeId(edgeId).addEdge(RelationType.Link, node);
            }
        }
        for (String edgeId : canal.getOutGoingEdge()) {
            if(dao.findVertexByNodeId(edgeId) != null) {
                node.addEdge(RelationType.Link, dao.findVertexByNodeId(edgeId));
            }
        }
        janusgraph.graph.tx().commit();
    }

    @Override
    public Canal findById(Long id) {
        Vertex canal = janusgraph.g.V(id).next();
        return transferToCanal(canal);
    }

    @Override
    public Canal findByNodeId(String nodeId) {
        Vertex canal = dao.findVertexByNodeId(Label.CANAL, nodeId);
        return transferToCanal(canal);
    }

    @Override
    public Canal transferToCanal(Vertex vertex) {
        Canal canal = new Canal();
        canal.setNodeId(vertex.property("nodeId").value().toString());
        canal.setNodeName(vertex.property("nodeName").value().toString());
        canal.setNodeTitle(vertex.property("nodeTitle").value().toString());
        return canal;
    }
}
