package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.reposity.KafkaDao;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/21.
 */
public class KafkaDaoImpl implements KafkaDao{
    @Override
    public void addNode(Api api) {

    }

    @Override
    public Api findById(Long id) {
        return null;
    }

    @Override
    public Api findByNodeId(String nodeId) {
        return null;
    }

    @Override
    public Api transferToApi(Vertex vertex) {
        return null;
    }
}
