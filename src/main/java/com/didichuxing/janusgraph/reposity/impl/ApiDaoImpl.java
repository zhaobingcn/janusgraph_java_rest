package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.ApiDao;
import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Dao dao;


    //添加Api节点
    @Override
    public void addNode(Api api) {
        Vertex node = janusgraph.graph.addVertex(Label.API);
        node.property("nodeId", api.getNodeId());
        node.property("nodeTitle", api.getNodeTitle());
        node.property("nodeName", api.getNodeName());

        for (String edgeId : api.getInComingEdge()) {
            dao.findVertexByNodeId(edgeId).addEdge(RelationType.Link, node);
        }
        for (String edgeId : api.getOutGoingEdge()) {
            node.addEdge(RelationType.Link, dao.findVertexByNodeId(edgeId));
        }
        janusgraph.graph.tx().commit();

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


    //将Vertex类型的节点转换为Api
    @Override
    public Api transferToApi(Vertex vertex) {
        Api api = new Api();
        api.setNodeId(vertex.property("nodeId").value().toString());
        api.setNodeName(vertex.property("nodeName").value().toString());
        api.setNodeTitle(vertex.property("nodeTitle").value().toString());
        return api;
    }












}
