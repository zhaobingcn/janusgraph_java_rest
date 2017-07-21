package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.DataSource;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.reposity.DataSourceDao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhzy on 2017/7/21.
 */
@Repository
public class DataSourceDaoImpl implements DataSourceDao {

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();

    @Autowired
    private Dao dao;

    @Override
    public void addNode(DataSource dataSource) {
        Vertex node = janusgraph.graph.addVertex(Label.DATASOURCE);
        node.property("nodeId", dataSource.getNodeId());
        node.property("nodeTitle", dataSource.getNodeTitle());
        node.property("nodeName", dataSource.getNodeName());

        for (String edgeId : dataSource.getInComingEdge()) {
            dao.findVertexByNodeId(edgeId).addEdge(RelationType.Link, node);
        }
        for (String edgeId : dataSource.getOutGoingEdge()) {
            node.addEdge(RelationType.Link, dao.findVertexByNodeId(edgeId));
        }
        janusgraph.graph.tx().commit();
    }

    @Override
    public DataSource findById(Long id) {
        Vertex dataSource = janusgraph.g.V(id).next();
        return transferToDataSource(dataSource);
    }

    @Override
    public DataSource findByNodeId(String nodeId) {
        Vertex dataSource = janusgraph.g.V().has(Label.DATASOURCE, "nodeId", nodeId).next();
        return transferToDataSource(dataSource);
    }

    @Override
    public DataSource transferToDataSource(Vertex vertex) {
        DataSource dataSource = new DataSource();
        dataSource.setNodeId(vertex.property("nodeId").value().toString());
        dataSource.setNodeName(vertex.property("nodeName").value().toString());
        dataSource.setNodeTitle(vertex.property("nodeTitle").value().toString());
        return dataSource;
    }


}
