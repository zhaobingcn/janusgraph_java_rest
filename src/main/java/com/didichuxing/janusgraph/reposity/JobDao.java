package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.domain.Job;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface JobDao {
    public void addNode(Job job);

    public Job findById(Long id);

    public Job findByNodeId(String nodeId);

    public Job transferToJob(Vertex vertex);
}
