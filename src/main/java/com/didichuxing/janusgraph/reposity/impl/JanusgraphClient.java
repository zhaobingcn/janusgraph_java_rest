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
//                    .set("storage.directory", "db/berkeleyje")
                    .set("storage.tablename", "testgraph")
                    .open();
            g = graph.traversal();
        }
    }

    public static JanusgraphClient getJanusgraph(){
        return janusgraph;
    }
}
