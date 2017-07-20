package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.reposity.ApiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhzy on 2017/7/20.
 */
@Controller
@RequestMapping("/edge")
public class EdgeController {

    @Autowired
    private ApiDao apiDao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody boolean insertEdge(@RequestBody String startNodeId,
                                            @RequestBody String endNodeId){
        apiDao.addEdge(startNodeId, endNodeId);
        return true;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteEdge(@RequestBody String startNodeId,
                                            @RequestBody String endNodeId){
        apiDao.deleteEdge(startNodeId, endNodeId);
        return true;
    }

}
