package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.reposity.ApiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/20.
 */
@Controller
@RequestMapping("/edge")
public class EdgeController {

    @Autowired
    private ApiDao apiDao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody boolean insertEdge(@RequestBody Map ids){

        System.out.println(ids);
        apiDao.addEdge(ids.get("startNodeId").toString(), ids.get("endNodeId").toString());
        return true;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteEdge(@RequestBody Map ids){

        System.out.println(ids);
        return apiDao.deleteEdge(ids.get("startNodeId").toString(), ids.get("endNodeId").toString());
    }

}
