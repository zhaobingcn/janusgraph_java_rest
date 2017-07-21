package com.didichuxing.janusgraph.reposity;

import java.util.List;
import java.util.Map;

/**
 * Created by zhzy on 2017/7/18.
 */
public interface testDao {

    public List<String> insertName(String name);

    public List<String> queryName(String name);

    public Map<String, Object> graph();
}
