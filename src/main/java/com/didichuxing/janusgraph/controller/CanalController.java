package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.Canal;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.reposity.CanalDao;
import com.didichuxing.janusgraph.reposity.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping("/canal")
public class CanalController {

    @Autowired
    private CanalDao canalDao;

    @Autowired
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody
    boolean insertCanal(@RequestBody(required = false)Canal canal
    ){
        System.out.println(canal.toString());
        canalDao.addNode(canal);
        return true;
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteCanal(@PathVariable(value = "nodeId")String nodeId){
        return dao.deleteNode(Label.CANAL, nodeId);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody Canal findCanal(@PathVariable(value = "nodeId") String nodeId){
        System.out.println(nodeId);
        return canalDao.findByNodeId(nodeId);
    }

    @RequestMapping(value = "/isCanalEdgeExist/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody boolean isNodeEdgeExist(@PathVariable(value = "nodeId") String nodeId){
        return dao.isNodeEdgeExist(Label.CANAL, nodeId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean updateNode(@RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        return dao.updateNode(Label.CANAL, nodeId, properties);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteAll(){
        return dao.deleteAll(Label.CANAL);
    }
}
