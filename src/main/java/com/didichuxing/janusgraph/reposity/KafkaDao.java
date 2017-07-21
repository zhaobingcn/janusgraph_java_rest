package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.domain.Job;
import com.didichuxing.janusgraph.domain.Kafka;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface KafkaDao {
    public void addNode(Kafka kafka);

    public Kafka findById(Long id);

    public Kafka findByNodeId(String nodeId);

    public Kafka transferToKafka(Vertex vertex);
}
