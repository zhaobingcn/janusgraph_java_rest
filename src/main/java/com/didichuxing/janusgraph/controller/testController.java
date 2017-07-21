package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.reposity.impl.testDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zhzy on 2017/7/18.
 */

@Controller
public class testController {

    @Autowired
    private testDaoImpl janusgraphDao;

    @RequestMapping(value = "/queryname/{name}", method = RequestMethod.GET)
    public @ResponseBody List<String> queryname(@PathVariable("name")String name){
        System.out.println("++++++++" + name);
        return janusgraphDao.queryName(name);
    }

    @RequestMapping(value = "/querynamep", method = RequestMethod.POST)
    public @ResponseBody List<String> insertName(@RequestBody(required = false) Api api

    ){
        System.out.println(api.toString());
        return janusgraphDao.insertName(api.getNodeName());
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> graph(){
        return  janusgraphDao.graph();
    }

}
