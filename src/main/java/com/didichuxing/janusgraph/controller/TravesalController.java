package com.didichuxing.janusgraph.controller;

import com.didichuxing.janusgraph.domain.ClientInfo;
import com.didichuxing.janusgraph.domain.Status;
import com.didichuxing.janusgraph.service.TraversalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        clientInfo.setData(traversalService.generateGraph(id));
        return ResponseEntity.ok(clientInfo);
    }
}
