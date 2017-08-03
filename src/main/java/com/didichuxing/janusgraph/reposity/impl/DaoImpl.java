package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.otherV;

/**
 * Created by zhzy on 2017/7/21.
 */
@Repository
public class DaoImpl implements Dao {

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();


    @Override
    public void addNode(String label, Map<String, Object> nodeDetail) {
        Vertex node = janusgraph.graph.addVertex(label);
        for(Map.Entry<String, Object> property: nodeDetail.entrySet()){
            if(property.getKey().toString() != "inComingEdge" &&
                    property.getKey().toString() != "outGoingEdge"){
                node.property(property.getKey(), property.getValue());
            }
        }
        //因为添加点还未存在，所以不会出现重复边的问题
        for (String nodeId : (ArrayList<String>)nodeDetail.get("inComingEdge")) {
            if(findVertexByNodeId(nodeId) != null){
                findVertexByNodeId(nodeId).addEdge(RelationType.Link, node)
                        .property("edgeId", nodeId + nodeDetail.get("nodeId"));
            }
        }
        for (String nodeId : (ArrayList<String>)nodeDetail.get("outGoingEdge")) {
            if(findVertexByNodeId(nodeId) != null){
                node.addEdge(RelationType.Link, findVertexByNodeId(nodeId))
                        .property("edgeId", nodeDetail.get("nodeId") + nodeId);
            }
        }
        janusgraph.graph.tx().commit();
    }

    @Override
    public void addEdge(String startNodeId, String endNodeId) {
        if (!isEdgeExist(startNodeId, endNodeId)) {
            Vertex startNode = findVertexByNodeId(startNodeId);
            Vertex endNode = findVertexByNodeId(endNodeId);
            startNode.addEdge(RelationType.Link, endNode).property("edgeId", startNodeId + endNodeId);
            janusgraph.graph.tx().commit();
        }
    }

    @Override
    public void addEdge(String startLabel, String startNodeId, String endLabel, String endNodeId) {
    }


    @Override
    public Map<String, Object> findValueMapByNodeId(String label, String nodeId) {
        return janusgraph.g.V().has(label, "nodeId", nodeId).valueMap().next();
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
    public boolean isEdgeExist(String startNodeId, String endNodeId) {
        boolean exist = false;
        String edgeId = startNodeId + endNodeId;
        exist = janusgraph.g.E().has(RelationType.Link, "edgeId", edgeId).hasNext();
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
    public boolean deleteEdge(String startNodeId, String endNodeId) {
        /**
        Vertex startNode = janusgraph.g.V().has("nodeId", startNodeId).next();
        Vertex endNode = janusgraph.g.V().has("nodeId", endNodeId).next();
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
         **/
        if(isEdgeExist(startNodeId, endNodeId)){
            String edgeId = startNodeId + endNodeId;
            janusgraph.g.E().has(RelationType.Link, "edgeId", edgeId).next().remove();
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
        return true;
    }

    @Override
    public List<Vertex> findNodesByLabelAndProperty(String label, String propertyKey, String propertyValue) {
        List<Vertex> nodes = janusgraph.g.V().has(label, propertyKey, propertyValue).toList();
        return nodes;
    }

    @Override
    public List<Vertex> findNodesByTypeAndVersion(String label, Map<String, Object> typeAndVersion) {
        List<Vertex> nodes = janusgraph.g.V().has(label, "type", typeAndVersion.get("type"))
                .has("version", typeAndVersion.get("version")).toList();
        return nodes;
    }

    @Override
    public List<Vertex> findAllNodes() {
        List<Vertex> nodes = janusgraph.g.V().toList();
        return nodes;
    }

//    @Override
//    public List<Vertex> findNeighborsNodesById(long id) {
//        List<Vertex> nodes = janusgraph.g.V().where(id().is(id)).next();
//        return nodes;
//    }

//    @Override
//    public List<Edge> findNeighborsEdgesById(long id) {
//        return null;
//    }

    @Override
    public Map<String, Object> transferVertexToMap(Vertex vertex) {
        Map<String, Object> displayNode = new HashMap<>();
        displayNode.put("id", vertex.id());
        displayNode.put("label", vertex.label());
        Iterator<Property> propertyIterator = (Iterator)vertex.properties();
        while(propertyIterator.hasNext()){
            Property property = propertyIterator.next();
            displayNode.put(property.key(), property.value());
        }
        return displayNode;
    }

    @Override
    public boolean updateEdge() {
        return false;
    }
}
