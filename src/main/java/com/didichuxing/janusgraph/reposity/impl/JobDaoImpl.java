package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.Job;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.generic.RelationType;
import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.reposity.JobDao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by zhzy on 2017/7/21.
 */

@Repository
public class JobDaoImpl implements JobDao {

    private JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();

    @Autowired
    private Dao dao;

    @Override
    public void addNode(Job job) {
        Vertex node = janusgraph.graph.addVertex(Label.JOB);
        node.property("nodeId", job.getNodeId());
        node.property("nodeTitle", job.getNodeTitle());
        node.property("nodeName", job.getNodeName());

        for (String edgeId : job.getInComingEdge()) {
            dao.findVertexByNodeId(edgeId).addEdge(RelationType.Link, node);
        }
        for (String edgeId : job.getOutGoingEdge()) {
            node.addEdge(RelationType.Link, dao.findVertexByNodeId(edgeId));
        }
        janusgraph.graph.tx().commit();
    }

    @Override
    public Job findById(Long id) {
        Vertex job = janusgraph.g.V(id).next();
        return transferToJob(job);
    }

    @Override
    public Job findByNodeId(String nodeId) {
        Vertex job = janusgraph.g.V().has(Label.JOB, "nodeId", nodeId).next();
        return transferToJob(job);
    }

    @Override
    public Job transferToJob(Vertex vertex) {
        Job job = new Job();
        job.setNodeId(vertex.property("nodeId").value().toString());
        job.setNodeName(vertex.property("nodeName").value().toString());
        job.setNodeTitle(vertex.property("nodeTitle").value().toString());
        return job;
    }


}