package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.ApiDao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.otherV;


/**
 * Created by zhzy on 2017/7/18.
 */
@Repository
public class ApiDaoImpl implements ApiDao {


    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();


    //添加Api节点
    @Override
    public void addNode(Api api) {
        Vertex node = janusgraph.graph.addVertex(Label.API);
        node.property("nodeId", api.getNodeId());
        node.property("nodeTitle", api.getNodeTitle());
        node.property("nodeName", api.getNodeName());

        for (String edgeId : api.getInComingEdge()) {
            findVertexByNodeId(edgeId).addEdge(RelationType.Link, node);
        }
        for (String edgeId : api.getOutGoingEdge()) {
            node.addEdge(RelationType.Link, findVertexByNodeId(edgeId));
        }
        janusgraph.graph.tx().commit();

    }

    //根据两个节点的nodeid添加边
    @Override
    public void addEdge(String startNodeId, String endNodeId) {
        Vertex startNode = findVertexByNodeId(startNodeId);
        Vertex endNode = findVertexByNodeId(endNodeId);
        if (!isEdgeExist(startNode, endNode)) {
            startNode.addEdge(RelationType.Link, endNode);
            janusgraph.graph.tx().commit();
        }
    }


    //根据id查找节点
    @Override
    public Api findById(Long id) {
        Vertex api = janusgraph.g.V(id).next();
        return transferToApi(api);
    }

    //根据node查找节点
    @Override
    public Api findByNodeId(String nodeId) {
        Vertex api = janusgraph.g.V().has(Label.API, "nodeId", nodeId).next();
        return transferToApi(api);
    }

    //根据nodeId查找节点，返回vertex类型
    @Override
    public Vertex findVertexByNodeId(String nodeId) {
        return janusgraph.g.V().has(Label.API, "nodeId", nodeId).next();
    }

    //将Vertex类型的节点转换为Api
    @Override
    public Api transferToApi(Vertex vertex) {
        Api api = new Api();
        api.setNodeId(vertex.property("nodeId").value().toString());
        api.setNodeName(vertex.property("nodeName").value().toString());
        api.setNodeTitle(vertex.property("nodeTitle").value().toString());
        return api;
    }

    //判断两个节点之间的边是否存在
    @Override
    public boolean isEdgeExist(Vertex startNode, Vertex endNode) {
        boolean exist = false;
        exist = janusgraph.g.V(startNode).outE().as("edge").inV().is(endNode).select("edge").hasNext();
        return exist;
    }

    //判断节点是否存在
    @Override
    public boolean isNodeExist(String nodeId) {
        boolean exist = false;
        exist = janusgraph.g.V().has(Label.API, "nodeId", nodeId).hasNext();
        return exist;
    }

    //判断节点是否存在边
    @Override
    public boolean isNodeEdgeExist(String nodeId) {
        boolean exist = false;
        exist = janusgraph.g.V().has(Label.API, "nodeId", nodeId).bothE().hasNext();
        return exist;
    }

    //删除节点
    @Override
    public boolean deleteNode(String nodeId) {
        if (isNodeExist(nodeId)) {
            janusgraph.g.V().has("nodeId", nodeId).next().remove();
            janusgraph.g.tx().commit();
            return true;
        }
        return false;
    }

    //删除边
    @Override
    public boolean deleteEdge(String startNodeId, String endNodeId) {
        Vertex startNode = janusgraph.g.V().has(Label.API, "nodeId", startNodeId).next();
        Vertex endNode = janusgraph.g.V().has(Label.API, "nodeId", endNodeId).next();
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
    public boolean updateNode(String nodeId, Map<String, Object> properties) {
        Vertex node = janusgraph.g.V().has(Label.API, "nodeId", nodeId).next();
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
