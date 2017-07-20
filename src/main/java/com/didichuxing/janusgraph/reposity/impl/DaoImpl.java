package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.otherV;

/**
 * Created by zhzy on 2017/7/21.
 */
@Repository
public class DaoImpl implements Dao {

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();

    @Override
    public void addEdge(String startNodeId, String endNodeId) {
        Vertex startNode = findVertexByNodeId(startNodeId);
        Vertex endNode = findVertexByNodeId(endNodeId);
        if (!isEdgeExist(startNode, endNode)) {
            startNode.addEdge(RelationType.Link, endNode);
            janusgraph.graph.tx().commit();
        }
    }


    @Override
    public Vertex findVertexByNodeId(String label, String nodeId) {
        return janusgraph.g.V().has(label, "nodeId", nodeId).next();
    }

    @Override
    public Vertex findVertexByNodeId(String nodeId) {
        return janusgraph.g.V().has("nodeId", nodeId).next();
    }

    @Override
    public boolean isEdgeExist(Vertex startNode, Vertex endNode) {
        boolean exist = false;
        exist = janusgraph.g.V(startNode).outE().as("edge").inV().is(endNode).select("edge").hasNext();
        return exist;
    }

    @Override
    public boolean isNodeExist(String label, String nodeId) {
        boolean exist = false;
        exist = janusgraph.g.V().has(label, "nodeId", nodeId).hasNext();
        return exist;
    }

    @Override
    public boolean isNodeEdgeExist(String label, String nodeId) {
        boolean exist = false;
        exist = janusgraph.g.V().has(label, "nodeId", nodeId).bothE().hasNext();
        return exist;
    }

    @Override
    public boolean deleteNode(String label, String nodeId) {
        if (isNodeExist(label, nodeId)) {
            janusgraph.g.V().has(label, "nodeId", nodeId).next().remove();
            janusgraph.g.tx().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEdge(String label, String startNodeId, String endNodeId) {
        Vertex startNode = janusgraph.g.V().has(label, "nodeId", startNodeId).next();
        Vertex endNode = janusgraph.g.V().has(label, "nodeId", endNodeId).next();
        if (isEdgeExist(startNode, endNode)) {
//            写法1
//            List<Edge> edges = janusgraph.g.V(startNode).outE().toList();
//            for (Edge edge : edges) {
//                if (edge.inVertex().equals(endNode)) {
//                    edge.remove();
//                    janusgraph.g.tx().commit();
//                    return true;
//                }
//            }
//            写法2
            janusgraph.g.V(startNode).outE().where(otherV().is(endNode)).drop().iterate();
            janusgraph.g.tx().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNode(String label, String nodeId, Map<String, Object> properties) {
        Vertex node = janusgraph.g.V().has(label, "nodeId", nodeId).next();
        for(Map.Entry<String, Object> toBeUpdatedProperty: properties.entrySet()){
            node.property(toBeUpdatedProperty.getKey(), toBeUpdatedProperty.getValue());
        }
        janusgraph.g.tx().commit();
        return true;
    }

    @Override
    public boolean deleteAll(String label) {
        janusgraph.g.V().hasLabel(label).drop().iterate();
        janusgraph.g.tx().commit();
        return false;
    }

    @Override
    public boolean updateEdge() {
        return false;
    }
}
