package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface DataSourceDao {
    public void addNode(Api api);

    public Api findById(Long id);

    public Api findByNodeId(String nodeId);

    public Api transferToApi(Vertex vertex);
}
