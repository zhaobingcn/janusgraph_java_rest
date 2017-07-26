package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Map;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface Dao {

    //添加边
    public void addEdge(String startNodeId, String endNodeId);

    //根据label查找节点
    public Vertex findVertexByNodeId(String label, String nodeId);

    //根据id查找节点
    public Vertex findVertexByNodeId(String nodeId);

    //查看两个节点间的边是否存在,根据两个节点查询
    public boolean isEdgeExist(Vertex startNode, Vertex endNode);

    //查看两个节点之间的边是否存在，根据边id查询
    public boolean isEdgeExist(String startNodeId, String endNodeId);

    //查看一个节点是否存在
    public boolean isNodeExist(String label, String nodeId);

    //查看一个节点是否存在边
    public boolean isNodeEdgeExist(String label, String nodeId);

    //删除节点
    public boolean deleteNode(String label, String nodeId);

    //删除边
    public boolean deleteEdge(String startNodeId, String endNodeId);

    //修改节点数据
    public boolean updateNode(String label, String nodeId, Map<String, Object> properties);

    //删除某一个标签的所有数据
    public boolean deleteAll(String label);

    //TODO
    public boolean updateEdge();
}
