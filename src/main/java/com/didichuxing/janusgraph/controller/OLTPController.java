package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhzy on 2017/7/28.
 */
@Controller
@RequestMapping("/{label}")
public class OLTPController {

    @Autowired
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody boolean insert(@PathVariable(value = "label")String label,
                                        @RequestBody(required = false)Map<String, Object> map
    ){
        dao.addNode(label, map);
        return true;
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean delete(@PathVariable(value = "label")String label,
                                        @PathVariable(value = "nodeId")String nodeId){
        return dao.deleteNode(label, nodeId);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> find(@PathVariable(value = "label") String label,
                                                  @PathVariable(value = "nodeId") String nodeId){
        System.out.println(nodeId);
        Map<String, Object> nodeMap = dao.findValueMapByNodeId(label, nodeId);
        return nodeMap;
    }

    @RequestMapping(value = "/isNodeEdgeExist/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody boolean isNodeEdgeExist(@PathVariable(value = "label")String label,
                                                 @PathVariable(value = "nodeId") String nodeId){
        return dao.isNodeEdgeExist(label, nodeId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean updateNode(@PathVariable(value = "label")String label,
                                            @RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        return dao.updateNode(label, nodeId, properties);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteAll(@PathVariable(value = "label")String label){
        return dao.deleteAll(label);
    }

    @RequestMapping(value = "/findByProperty/{propertyKey}/{propertyValue}", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> findByProperty(@PathVariable(value = "label")String label,
                                                     @PathVariable(value = "propertyKey")String propertyKey,
                                                     @PathVariable(value = "propertyValue")String propertyValue){
        List<Vertex> nodes = dao.findNodesByLabelAndProperty(label, propertyKey, propertyValue);
        List<Map<String, Object>> displayNodes = new ArrayList<>();
        for(Vertex displayNode:nodes){
            displayNodes.add(dao.transferVertexToMap(displayNode));
        }
        return displayNodes;
    }

    @RequestMapping(value = "/findByProperties", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, Object>> findByProperties(@PathVariable(value = "label")String label,
                                                    @RequestBody Map<String, Object> map){
        List<Vertex> nodes = dao.findNodesByTypeAndVersion(label, map);
        List<Map<String, Object>> displayNodes = new ArrayList<>();
        for(Vertex displayNode:nodes){
            displayNodes.add(dao.transferVertexToMap(displayNode));
        }
        return displayNodes;
    }

}
