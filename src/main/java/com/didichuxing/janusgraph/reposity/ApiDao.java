package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import org.apache.tinkerpop.gremlin.structure.Vertex;

/**
 * Created by zhzy on 2017/7/18.
 */
public interface ApiDao {

    public void addNode(Api api);

    public void addEdge(String startNodeId, String endNodeId);

    public Api findById(Long id);

    public Api findByNodeId(String nodeId);

    public Vertex findVertexByNodeId(String nodeId);

    public Api transferToApi(Vertex vertex);

    public boolean isEdgeExist(Vertex startNode, Vertex endNode);

    public boolean isNodeExist(String nodeId);

    public boolean isNodeEdgeExist(String nodeId);

    public boolean deleteNode(String nodeId);

    public boolean deleteEdge(String startNode, String endNode);

}
