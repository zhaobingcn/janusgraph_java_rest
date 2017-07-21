package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.Kafka;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.reposity.KafkaDao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhzy on 2017/7/21.
 */

@Repository
public class KafkaDaoImpl implements KafkaDao{

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();

    @Autowired
    private Dao dao;

    @Override
    public void addNode(Kafka kafka) {
        Vertex node = janusgraph.graph.addVertex(Label.KAFKA);
        node.property("nodeId", kafka.getNodeId());
        node.property("nodeTitle", kafka.getNodeTitle());
        node.property("nodeName", kafka.getNodeName());

        for (String edgeId : kafka.getInComingEdge()) {
            dao.findVertexByNodeId(edgeId).addEdge(RelationType.Link, node);
        }
        for (String edgeId : kafka.getOutGoingEdge()) {
            node.addEdge(RelationType.Link, dao.findVertexByNodeId(edgeId));
        }
        janusgraph.graph.tx().commit();
    }

    @Override
    public Kafka findById(Long id) {
        Vertex kafka = janusgraph.g.V(id).next();
        return transferToKafka(kafka);
    }

    @Override
    public Kafka findByNodeId(String nodeId) {
        Vertex kafka = janusgraph.g.V().has(Label.KAFKA, "nodeId", nodeId).next();
        return transferToKafka(kafka);
    }

    @Override
    public Kafka transferToKafka(Vertex vertex) {
        Kafka kafka = new Kafka();
        kafka.setNodeId(vertex.property("nodeId").value().toString());
        kafka.setNodeName(vertex.property("nodeName").value().toString());
        kafka.setNodeTitle(vertex.property("nodeTitle").value().toString());
        return kafka;
    }

}
