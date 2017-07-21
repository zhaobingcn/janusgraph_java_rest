package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.Job;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.reposity.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobDao jobDao;

    @Autowired
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody
    boolean insertJob(@RequestBody(required = false)Job job
    ){
        System.out.println(job.toString());
        jobDao.addNode(job);
        return true;
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteJob(@PathVariable(value = "nodeId")String nodeId){
        return dao.deleteNode(Label.JOB, nodeId);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody Job findJob(@PathVariable(value = "nodeId") String nodeId){
        System.out.println(nodeId);
        return jobDao.findByNodeId(nodeId);
    }

    @RequestMapping(value = "/isJobEdgeExist/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody boolean isNodeEdgeExist(@PathVariable(value = "nodeId") String nodeId){
        return dao.isNodeEdgeExist(Label.JOB, nodeId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean updateNode(@RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        return dao.updateNode(Label.JOB, nodeId, properties);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteAll(){
        return dao.deleteAll(Label.JOB);

    }
}
