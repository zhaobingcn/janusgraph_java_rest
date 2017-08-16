package com.didichuxing.janusgraph.reposity.impl;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;

/**
 * Created by zhzy on 2017/7/19.
 */
public class JanusgraphClient {

    private static final JanusgraphClient janusgraph = new JanusgraphClient();

    public JanusGraph graph = null;
    public GraphTraversalSource g = null;

    private JanusgraphClient(){
        if(graph == null){
            graph = JanusGraphFactory.build().set("storage.backend", "hbase")
                    .set("storage.hostname", "127.0.0.1")
                    .set("tx.max-commit-time", "1000ms")
                    .set("storage.hbase.table", "testgraph4")
                    .open();
//            graph = JanusGraphFactory.open("src/main/resources/janusgraph-hbase.properties");
            g = graph.traversal();
        }
    }

    public static JanusgraphClient getJanusgraph(){
        return janusgraph;
    }
}
