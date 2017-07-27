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
        node.property("type", api.getType());

        //因为添加点还未存在，所以不会出现重复边的问题
        for (String nodeId : api.getInComingEdge()) {
            if(dao.findVertexByNodeId(nodeId) != null){
                dao.findVertexByNodeId(nodeId).addEdge(RelationType.Link, node)
                        .property("edgeId", nodeId + api.getNodeId());
            }
        }
        for (String nodeId : api.getOutGoingEdge()) {
            if(dao.findVertexByNodeId(nodeId) != null){
                node.addEdge(RelationType.Link, dao.findVertexByNodeId(nodeId))
                        .property("edgeId", api.getNodeId() + nodeId);
            }
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
        Vertex api = dao.findVertexByNodeId(Label.API, nodeId);
        return transferToApi(api);
    }


    //将Vertex类型的节点转换为Api
    @Override
    public Api transferToApi(Vertex vertex) {
        Api api = new Api();
        api.setNodeId(vertex.property("nodeId").value().toString());
        api.setNodeName(vertex.property("nodeName").value().toString());
        api.setNodeTitle(vertex.property("nodeTitle").value().toString());
        api.setType(vertex.property("type").value().toString());
        return api;
    }












}
