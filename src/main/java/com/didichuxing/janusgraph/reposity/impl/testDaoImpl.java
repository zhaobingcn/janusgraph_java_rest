package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.reposity.testDao;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by zhzy on 2017/7/18.
 */
@Repository
public class testDaoImpl implements testDao {

    JanusgraphClient janusgraph = JanusgraphClient.getJanusgraph();


    @Override
    public List<String> insertName(String name) {

        janusgraph.graph.addVertex("name", name);
        janusgraph.graph.tx().commit();

        Map<String, Object> a = janusgraph.g.V().has("name", name).valueMap().next();
        List<String> fin = new ArrayList<>();
        fin.add(a.get("name").toString());
        System.out.println(fin);
        return fin;

    }

    @Override
    public List<String> queryName(String name) {

        Map<String, Object> a = janusgraph.g.V().has("name", name).valueMap().next();
        List<String> fin = new ArrayList<>();
        fin.add(a.get("name").toString());
        System.out.println(fin);
        return fin;
    }

    @Override
    public Map<String, Object> graph() {
        List<Edge> edges = janusgraph.g.E().toList();
        List<Vertex> vertexs = janusgraph.g.V().toList();

        List<Map<String, Object>> nodes = new ArrayList<>();
        Map<String, Integer> nodesId = new LinkedHashMap<>();
        List<Map<String, Object>> links = new ArrayList<>();
        int i=0;
        for(Vertex vertex:vertexs){
            Map<String, Object> node = new HashMap<>();
            node.put("name", vertex.property("nodeId").value().toString());
            node.put("value", vertex.property("nodeId").value().toString());
            nodes.add(node);
            nodesId.put(vertex.property("nodeId").value().toString(), i);
            i++;
        }
        for(Edge edge: edges){
            Map<String, Object> link = new HashMap<>();
            link.put("source", nodesId.get(edge.outVertex().property("nodeId").value().toString()));
            link.put("target", nodesId.get(edge.inVertex().property("nodeId").value().toString()));
            links.add(link);
        }

        Map<String, Object> fin = new HashMap<>();
        fin.put("nodes", nodes);
        fin.put("links", links);

        return fin;
    }


}
