package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.service.TraversalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping(value = "/traversal")
public class TravesalController {

    @Autowired
    private TraversalService traversalService;

//    @RequestMapping(value = "/findNeighbors/{id}")
//    public Map<String, Object> findNeighbors(@PathVariable(value = "id")long id){
//
//    }

    @RequestMapping(value = "/findAllNodes", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> findAllNodes(){
        return traversalService.generateNodesGraph();
    }

    @RequestMapping(value = "/findSubgraph/{id}/{depth}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> findSubgraph(@PathVariable(value = "id")long id,
                                                          @PathVariable(value = "depth")int depth){
        return traversalService.generateGraph(id);
    }
}
