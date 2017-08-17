package com.didichuxing.janusgraph.reposity.impl;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhzy on 2017/7/19.
 */
public class JanusgraphClient {

    private static final JanusgraphClient janusgraph = new JanusgraphClient();

    public JanusGraph graph = null;
    public GraphTraversalSource g = null;

    private JanusgraphClient(){
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("/janusgraph-hbase.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(graph == null){
            graph = JanusGraphFactory.build().set("storage.backend", prop.getProperty("storage.backend"))
                    .set("storage.hostname", prop.getProperty("storage.hostname"))
                    .set("storage.hbase.table", prop.getProperty("storage.hbase.table"))
                    .set("storage.hbase.compression-algorithm", prop.getProperty("storage.hbase.compression-algorithm"))
//                    .set("tx.max-commit-time", "1000ms")
//                    .set("storage.hbase.table", "WOATER:JANUSGRAPH")
                    .open();
//                graph = JanusGraphFactory.open();
                g = graph.traversal();
            }


    }

    public static JanusgraphClient getJanusgraph(){
        return janusgraph;
    }
}
