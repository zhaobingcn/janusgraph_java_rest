package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.DataSource;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.reposity.DataSourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping("/datasource")
public class DataSourceController {

    @Autowired
    private DataSourceDao dataSourceDao;

    @Autowired
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody
    boolean insertDataSource(@RequestBody(required = false)DataSource dataSource
    ){
        System.out.println(dataSource.toString());
        dataSourceDao.addNode(dataSource);
        return true;
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteDataSource(@PathVariable(value = "nodeId")String nodeId){
        return dao.deleteNode(Label.DATASOURCE, nodeId);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody DataSource findDataSource(@PathVariable(value = "nodeId") String nodeId){
        System.out.println(nodeId);
        return dataSourceDao.findByNodeId(nodeId);
    }

    @RequestMapping(value = "/isDataSourceEdgeExist/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody boolean isNodeEdgeExist(@PathVariable(value = "nodeId") String nodeId){
        return dao.isNodeEdgeExist(Label.DATASOURCE, nodeId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean updateNode(@RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        return dao.updateNode(Label.DATASOURCE, nodeId, properties);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteAll(){
        return dao.deleteAll(Label.DATASOURCE);

    }
}
