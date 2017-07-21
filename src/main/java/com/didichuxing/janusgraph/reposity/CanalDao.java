package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.domain.Canal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface CanalDao {
    public void addNode(Canal canal);

    public Canal findById(Long id);

    public Canal findByNodeId(String nodeId);

    public Canal transferToCanal(Vertex vertex);
}
