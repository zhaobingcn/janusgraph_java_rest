package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface Dao {
//    public void addNode(Api api);

    public void addEdge(String startNodeId, String endNodeId);

//    public Api findById(Long id);
//
//    public Api findByNodeId(String nodeId);

    public Vertex findVertexByNodeId(String label, String nodeId);

    public Vertex findVertexByNodeId(String nodeId);

//    public Api transferToApi(Vertex vertex);

    public boolean isEdgeExist(Vertex startNode, Vertex endNode);

    public boolean isNodeExist(String label, String nodeId);

    public boolean isNodeEdgeExist(String label, String nodeId);

    public boolean deleteNode(String label, String nodeId);

    public boolean deleteEdge(String label, String startNodeId, String endNodeId);

    public boolean updateNode(String label, String nodeId, Map<String, Object> properties);

    public boolean deleteAll(String label);

    //TODO
    public boolean updateEdge();
}
