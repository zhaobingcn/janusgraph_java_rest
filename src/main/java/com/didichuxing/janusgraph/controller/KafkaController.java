package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.Kafka;
import com.didichuxing.janusgraph.generic.Label;
import com.didichuxing.janusgraph.reposity.Dao;
import com.didichuxing.janusgraph.reposity.KafkaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaDao kafkaDao;

    @Autowired
    private Dao dao;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public @ResponseBody
    boolean insertKafka(@RequestBody(required = false)Kafka kafka
    ){
        System.out.println(kafka.toString());
        kafkaDao.addNode(kafka);
        return true;
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteKafka(@PathVariable(value = "nodeId")String nodeId){
        return dao.deleteNode(Label.KAFKA, nodeId);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody Kafka findkafka(@PathVariable(value = "nodeId") String nodeId){
        System.out.println(nodeId);
        return kafkaDao.findByNodeId(nodeId);
    }

    @RequestMapping(value = "/isKafkaEdgeExist/{nodeId}", method = RequestMethod.GET)
    public @ResponseBody boolean isNodeEdgeExist(@PathVariable(value = "nodeId") String nodeId){
        return dao.isNodeEdgeExist(Label.KAFKA, nodeId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody boolean updateNode(@RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");
        return dao.updateNode(Label.KAFKA, nodeId, properties);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public @ResponseBody boolean deleteAll(){
        return dao.deleteAll(Label.KAFKA);

    }
}
