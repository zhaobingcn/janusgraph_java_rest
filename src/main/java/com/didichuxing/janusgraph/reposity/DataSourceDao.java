package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.domain.DataSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface DataSourceDao {
    public void addNode(DataSource dataSource);

    public DataSource findById(Long id);

    public DataSource findByNodeId(String nodeId);

    public DataSource transferToDataSource(Vertex vertex);
}
