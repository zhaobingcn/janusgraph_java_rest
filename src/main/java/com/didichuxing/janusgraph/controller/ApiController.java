package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.reposity.ApiDao;
import com.didichuxing.janusgraph.reposity.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiDao apiDao;

    @Autowired
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody boolean insertApi(@RequestBody(required = false)Api api
    ){
        System.out.println(api.toString());
        apiDao.addNode(api);
        return true;
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteApi(@PathVariable(value = "nodeId")String nodeId){
        return dao.deleteNode(Label.API, nodeId);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody Api findApi(@PathVariable(value = "nodeId") String nodeId){
        System.out.println(nodeId);
        return apiDao.findByNodeId(nodeId);
    }

    @RequestMapping(value = "/isApiEdgeExist/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody boolean isNodeEdgeExist(@PathVariable(value = "nodeId") String nodeId){
        return dao.isNodeEdgeExist(Label.API, nodeId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean updateNode(@RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        return dao.updateNode(Label.API, nodeId, properties);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteAll(){
        return dao.deleteAll(Label.API);

    }


}
