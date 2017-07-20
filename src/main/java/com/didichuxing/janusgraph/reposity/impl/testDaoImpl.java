package com.didichuxing.janusgraph.reposity.impl;

import com.didichuxing.janusgraph.reposity.testDao;
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


}
