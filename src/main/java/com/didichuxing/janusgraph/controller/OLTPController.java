package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.ClientInfo;
import com.didichuxing.janusgraph.domain.Status;
import com.didichuxing.janusgraph.reposity.Dao;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ClientInfo> insert(@PathVariable(value = "label")String label,
                                        @RequestBody(required = false)Map<String, Object> map
    ){
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.addNode(label, map));
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setStatus(new Status("200", "添加成功"));
        clientInfo.setData(result);
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/delete/{nodeId}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientInfo> delete(@PathVariable(value = "label")String label,
                                        @PathVariable(value = "nodeId")String nodeId){
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.deleteNode(label, nodeId));
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setStatus(new Status("200", "删除成功"));
        clientInfo.setData(result);
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/find/{nodeId}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> find(@PathVariable(value = "label") String label,
                                                  @PathVariable(value = "nodeId") String nodeId){

        Map<String, Object> nodeMap = dao.findValueMapByNodeId(label, nodeId);
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setStatus(new Status("200", "查找成功"));
        clientInfo.setData(nodeMap);
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/isNodeEdgeExist/{nodeId}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo>  isNodeEdgeExist(@PathVariable(value = "label")String label,
                                                       @PathVariable(value = "nodeId") String nodeId){
        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.isNodeEdgeExist(label, nodeId));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "执行成功"));
        return ResponseEntity.status(HttpStatus.OK).body(clientInfo);
    }

    @RequestMapping(value = "/isInComingEdgeExist/{nodeId}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> isInComingEdgeExist(@PathVariable(value = "label")String label,
                                                          @PathVariable(value = "nodeId")String nodeId){
        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.isInComingEdgeEsist(label, nodeId));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "执行成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/isOutGoingEdgeExist/{nodeId}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> isOutGoingEdgeExist(@PathVariable(value = "label")String label,
                                                          @PathVariable(value = "nodeId")String nodeId){
        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.isOutGoingEdgeExist(label, nodeId));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "执行成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/deleteOutGoingEdge/{nodeId}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientInfo> deleteOutGoingEdge(@PathVariable(value = "label")String label,
                                                          @PathVariable(value = "nodeId")String nodeId){
        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.deleteOutGoingEdge(label, nodeId));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "执行成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/deleteInComingEdge/{nodeId}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientInfo> deleteInComingEdge(@PathVariable(value = "label")String label,
                                                         @PathVariable(value = "nodeId")String nodeId){
        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.deleteInComingEdge(label, nodeId));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "执行成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/deleteNodeEdge/{nodeId}", method = RequestMethod.DELETE)
    public ResponseEntity<ClientInfo> deleteNodeEdge(@PathVariable(value = "label")String label,
                                                         @PathVariable(value = "nodeId")String nodeId){
        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.deleteNodeEdge(label, nodeId));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "执行成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ClientInfo> updateNode(@PathVariable(value = "label")String label,
                                            @RequestBody Map map){
        String nodeId = map.get("nodeId").toString();
        Map<String, Object> properties = (Map<String, Object>) map.get("properties");

        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.updateNode(label, nodeId, properties));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "更新成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity<ClientInfo> deleteAll(@PathVariable(value = "label")String label){

        ClientInfo clientInfo = new ClientInfo();
        Map<String, Object> result = new HashMap<>();
        result.put("state", dao.deleteAll(label));
        clientInfo.setData(result);
        clientInfo.setStatus(new Status("200", "删除成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/findByProperty/{propertyKey}/{propertyValue}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> findByProperty(@PathVariable(value = "label")String label,
                                                     @PathVariable(value = "propertyKey")String propertyKey,
                                                     @PathVariable(value = "propertyValue")String propertyValue){
        List<Vertex> nodes = dao.findNodesByLabelAndProperty(label, propertyKey, propertyValue);
        List<Map<String, Object>> displayNodes = new ArrayList<>();
        for(Vertex displayNode:nodes){
            displayNodes.add(dao.transferVertexToMap(displayNode));
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setData(displayNodes);
        clientInfo.setStatus(new Status("200", "查找成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/findByProperties", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> findByProperties(@PathVariable(value = "label")String label,
                                                    @RequestBody Map<String, Object> map){
        List<Vertex> nodes = dao.findNodesByTypeAndVersion(label, map);
        List<Map<String, Object>> displayNodes = new ArrayList<>();
        for(Vertex displayNode:nodes){
            displayNodes.add(dao.transferVertexToMap(displayNode));
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setData(displayNodes);
        clientInfo.setStatus(new Status("200", "查找成功"));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/fuzzyFindByName/{fuzzyString}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> fuzzyFindByName(@PathVariable(value = "label")String label,
                                                      @PathVariable(value = "fuzzyString")String fuzzyString){
        if(fuzzyString.equals("empty")){
            List<Vertex> nodes = dao.findNodesByLabel(label);
            List<Map<String, Object>> displayNodes = new ArrayList<>();
            for(Vertex displayNode:nodes){
                displayNodes.add(dao.transferVertexToMap(displayNode));
            }
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setData(displayNodes);
            clientInfo.setStatus(new Status("200", "查找成功"));
            return ResponseEntity.ok(clientInfo);
        }
        List<Vertex> nodes = dao.fuzzyFindVertexByName(label, fuzzyString);
        List<Map<String, Object>> displayNodes = new ArrayList<>();
        for(Vertex displayNode:nodes){
            displayNodes.add(dao.transferVertexToMap(displayNode));
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setData(displayNodes);
        clientInfo.setStatus(new Status("200", "查找成功"));
        return ResponseEntity.ok(clientInfo);
    }

}
