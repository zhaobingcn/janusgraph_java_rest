package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.generic.Label;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/18.
 */
public interface ApiDao {

    public void addNode(Api api);

    public Api findById(Long id);

    public Api findByNodeId(String nodeId);

    public Api transferToApi(Vertex vertex);



}
