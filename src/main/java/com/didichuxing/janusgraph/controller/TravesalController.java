package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.ClientInfo;
import com.didichuxing.janusgraph.domain.Status;
import com.didichuxing.janusgraph.service.TraversalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/19.
 */
@Controller
@RequestMapping(value = "/traversal")
public class TravesalController {

    @Autowired
    private TraversalService traversalService;

    @RequestMapping(value = "/findAllNodes", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> findAllNodes(){
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setStatus(new Status("200", "查找成功"));
        clientInfo.setData(traversalService.generateNodesGraph());
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/findSubgraph/{id}/{depth}", method = RequestMethod.GET)
    public ResponseEntity<ClientInfo> findSubgraph(@PathVariable(value = "id")long id,
                                                   @PathVariable(value = "depth")int depth){
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setStatus(new Status("200", "查找成功"));
        clientInfo.setData(traversalService.generateGraph(id, depth));
        return ResponseEntity.ok(clientInfo);
    }

    @RequestMapping(value = "/findSubgraphByNodeId", method = RequestMethod.POST)
    public ResponseEntity<ClientInfo> findSubgraphByNodeId(@RequestBody Map<String, Object> map){

        String label = map.get("label").toString();
        Integer depth = Integer.valueOf(map.get("depth").toString());
        String nodeId = map.get("nodeId").toString();

        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setStatus(new Status("200", "查找成功"));
        clientInfo.setData(traversalService.generateGraph(label, nodeId, depth));
        return ResponseEntity.ok(clientInfo);
    }


}
