package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.reposity.ApiDao;
import com.didichuxing.janusgraph.reposity.Dao;
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
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody boolean insertEdge(@RequestBody Map ids){

        System.out.println(ids);
        dao.addEdge(ids.get("startNodeId").toString(), ids.get("endNodeId").toString());
        return true;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteEdge(@RequestBody Map ids){

        System.out.println(ids);
        return dao.deleteEdge(ids.get("startNodeId").toString(), ids.get("endNodeId").toString());
    }

}
