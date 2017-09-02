package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.*;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.janusgraph.core.SchemaViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.janusgraph.core.attribute.Text.*;


/**
 * Created by zhzy on 2017/7/21.
 */
@Repository
public class DaoImpl implements Dao {

    private final static Logger logger = LoggerFactory.getLogger(DaoImpl.class);

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();

    @Override
    public boolean addNode(String label, Map<String, Object> nodeDetail) {
        try{
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
            return true;
        }catch (SchemaViolationException e){
            logger.warn("添加数据nodeId出现重复，本次添加失败");
            janusgraph.graph.tx().rollback();
        }
        catch (Exception e){
            e.printStackTrace();
            janusgraph.graph.tx().rollback();
        }
        return false;
    }

    @Override
    public boolean addEdge(String startNodeId, String endNodeId) {
        if(!isEdgeExist(startNodeId, endNodeId)){
            Vertex startNode = findVertexByNodeId(startNodeId);
            Vertex endNode = findVertexByNodeId(endNodeId);
            String edgeId = startNodeId + endNodeId;
            startNode.addEdge(RelationType.Link, endNode).property("edgeId", edgeId);
            janusgraph.g.tx().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(String startLabel, String startNodeId, String endLabel, String endNodeId) {
        return false;
    }


    @Override
    public Map<String, Object> findValueMapByNodeId(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        if(janusgraph.g.V().has(label, "nodeId", nodeId).hasNext()){
            return transferVertexToMap(janusgraph.g.V().has(label, "nodeId", nodeId).next());
        }
        return new HashMap<>();
    }

    @Override
    public Vertex findVertexByNodeId(String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        if(janusgraph.g.V().has("nodeId", nodeId).hasNext()){
            System.out.println("+++++++++++++++++++");
            return janusgraph.g.V().has("nodeId", nodeId).next();

        }
        return null;
    }

    @Override
    public List<Vertex> fuzzyFindVertexByName(String label, String fuzzyName) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> vertices = janusgraph.g.V().hasLabel(label).has("nodeName", textRegex(".*?"+fuzzyName+".*?")).toList();
        return vertices;
    }


    @Override
    public boolean isEdgeExist(Vertex startNode, Vertex endNode) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        boolean exist = false;
        exist = janusgraph.g.V(startNode).outE().as("edge").inV().is(endNode).select("edge").hasNext();
        return exist;
    }

    @Override
    public boolean isEdgeExist(String startNodeId, String endNodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        boolean exist = false;
        String edgeId = startNodeId + endNodeId;
//        janusgraph.g.tx().rollback();
        exist = janusgraph.g.E().has(RelationType.Link, "edgeId", edgeId).hasNext();
//        Edge a = janusgraph.g.E().has(RelationType.Link, "edgeId", edgeId).next();
//        System.out.println("asddddddddddddddddddddddddddd" + exist + a.id());
        return exist;
    }

    @Override
    public boolean isNodeExist(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        boolean exist = false;
        exist = janusgraph.g.V().has(label, "nodeId", nodeId).hasNext();
        System.out.println("-----------------------------");
        System.out.println(exist);
        return exist;
    }

    @Override
    public boolean isNodeEdgeExist(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        boolean exist = false;
        exist = janusgraph.g.V().has(label, "nodeId", nodeId).bothE(RelationType.Link).hasNext();
        System.out.println("节点周围是否存在边" + exist);
        return exist;
    }

    @Override
    public boolean isOutGoingEdgeExist(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        boolean exist = false;
        exist = janusgraph.g.V().has(label, "nodeId", nodeId).outE().hasNext();
        System.out.println("节点周围是否存在出边" + exist);
        return exist;
    }

    @Override
    public boolean isInComingEdgeEsist(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        boolean exist = false;
        exist = janusgraph.g.V().has(label, "nodeId", nodeId).inE().hasNext();
        System.out.println("节点周围是否存在入边" + exist);
        return exist;
    }

    @Override
    public boolean deleteNode(String label, String nodeId) {
        try{
            if (isNodeExist(label, nodeId)) {
                janusgraph.g.V().has(label, "nodeId", nodeId).next().remove();
                janusgraph.g.tx().commit();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            janusgraph.g.tx().rollback();
        }
        return false;
    }

    @Override
    public boolean deleteNodeEdge(String label, String nodeId) {
        try{
            Iterator<Edge> edges = janusgraph.g.V().has(label, "nodeId", nodeId).bothE();
            while (edges.hasNext()){
                edges.next().remove();
            }
            janusgraph.g.tx().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            janusgraph.g.tx().rollback();
        }
        return false;
    }

    @Override
    public boolean deleteOutGoingEdge(String label, String nodeId) {
        try{
            Iterator<Edge> edges = janusgraph.g.V().has(label, "nodeId", nodeId).outE();
            while (edges.hasNext()){
                edges.next().remove();
            }
            janusgraph.g.tx().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            janusgraph.g.tx().rollback();
        }
        return false;
    }

    @Override
    public boolean deleteInComingEdge(String label, String nodeId) {
        try {
            Iterator<Edge> edges = janusgraph.g.V().has(label, "nodeId", nodeId).inE();
            while (edges.hasNext()){
                edges.next().remove();
            }
            janusgraph.g.tx().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            janusgraph.g.tx().rollback();
        }
        return false;
    }

    @Override
    public boolean deleteEdge(String startNodeId, String endNodeId) {
        /**
        Vertex startNode = janusgraph.g.V().has("nodeId", startNodeId).next();
        Vertex endNode = janusgraph.g.V().has("nodeId", endNodeId).next();
        if (isEdgeExist(startNode, endNode)) {
            写法1
            List<Edge> edges = janusgraph.g.V(startNode).outE().toList();
            for (Edge edge : edges) {
                if (edge.inVertex().equals(endNode)) {
                    edge.remove();
                    janusgraph.g.tx().commit();
                    return true;
                }
            }
            写法2
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
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> nodes = janusgraph.g.V().has(label, propertyKey, propertyValue).toList();
        return nodes;
    }

    @Override
    public List<Vertex> findNodesByTypeAndVersion(String label, Map<String, Object> typeAndVersion) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> nodes = janusgraph.g.V().has(label, "type", typeAndVersion.get("type"))
                .has("version", typeAndVersion.get("version")).toList();
        return nodes;
    }

    @Override
    public List<Vertex> findNeighborsNodesById(long id) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> nodes = new ArrayList<>();
        if(janusgraph.g.V().hasId(id).hasNext()){
            Vertex node = janusgraph.g.V().hasId(id).next();
            nodes = janusgraph.g.V().hasId(id).both().toList();
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<Vertex> findNeighborsNodesByNodeId(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> nodes = new ArrayList<>();
        if(janusgraph.g.V().has(label, "nodeId", nodeId).hasNext()){
            Vertex node = janusgraph.g.V().has(label, "nodeId", nodeId).next();
            nodes = janusgraph.g.V().has(label, "nodeId", nodeId).both().toList();
            nodes.add(node);
        }
        return nodes;
    }

    @Override
    public List<Edge> findNeighborsEdgesById(long id) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Edge> edges = new ArrayList<>();
        if(janusgraph.g.V().hasId(id).hasNext()){
            edges = janusgraph.g.V().hasId(id).bothE().toList();
        }
        return edges;
    }

    @Override
    public List<Edge> findNeighborsEdgesByNodeId(String label, String nodeId) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Edge> edges = new ArrayList<>();
        if(janusgraph.g.V().has(label, "nodeId", nodeId).hasNext()){
            edges = janusgraph.g.V().has(label, "nodeId", nodeId).bothE().toList();
        }
        return edges;
    }



    @Override
    public List<Vertex> findAllNodes() {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> nodes = janusgraph.g.V().toList();
        return nodes;
    }

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
    public List<Vertex> findNodesByLabel(String label) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        List<Vertex> nodes = janusgraph.g.V().hasLabel(label).toList();
        return nodes;
    }

    @Override
    public GraphTraversalSource findSubGraph(long id, int depth) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        TinkerGraph subGraph = (TinkerGraph) janusgraph.g.V(id).repeat(__.bothE().subgraph("subGraph").bothV()).times(depth).cap("subGraph").next();
        GraphTraversalSource sg = subGraph.traversal();
        return sg;
    }

    @Override
    public GraphTraversalSource findSubGraph(String label, String nodeId, int depth) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        TinkerGraph subGraph = (TinkerGraph) janusgraph.g.V().has(label, "nodeId", nodeId).repeat(__.bothE().subgraph("subGraph").bothV()).times(depth).cap("subGraph").next();
        GraphTraversalSource sg = subGraph.traversal();
        return sg;
    }

    @Override
    public GraphTraversalSource findInComingEdge(long id, int depth) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        TinkerGraph subGraph = (TinkerGraph) janusgraph.g.V(id).repeat(__.inE().subgraph("subGraph").outV()).times(depth).cap("subGraph").next();
        GraphTraversalSource sg = subGraph.traversal();
        return sg;
    }

    @Override
    public GraphTraversalSource findInComingEdge(String label, String nodeId, int depth) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        TinkerGraph subGraph = (TinkerGraph) janusgraph.g.V().has(label, "nodeId", nodeId).repeat(__.inE().subgraph("subGraph").outV()).times(depth).cap("subGraph").next();
        GraphTraversalSource sg = subGraph.traversal();
        return sg;
    }

    @Override
    public GraphTraversalSource findOutGoingEdge(long id, int depth) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        TinkerGraph subGraph = (TinkerGraph) janusgraph.g.V(id).repeat(__.outE().subgraph("subGraph").inV()).times(depth).cap("subGraph").next();
        GraphTraversalSource sg = subGraph.traversal();
        return sg;
    }

    @Override
    public GraphTraversalSource findOutGoingEdge(String label, String nodeId, int depth) {
        //添加事务提交，捕获数据库新的修改
        janusgraph.g.tx().commit();
        TinkerGraph subGraph = (TinkerGraph) janusgraph.g.V().has(label, "nodeId", nodeId).repeat(__.outE().subgraph("subGraph").inV()).times(depth).cap("subGraph").next();
        GraphTraversalSource sg = subGraph.traversal();
        return sg;
    }

    @Override
    public boolean updateEdge() {
        return false;
    }
}
