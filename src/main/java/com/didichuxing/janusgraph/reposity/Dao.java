package com.didichuxing.janusgraph.reposity;

import com.didichuxing.janusgraph.domain.Api;
import com.didichuxing.janusgraph.service.TraversalService;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.stringtemplate.v4.ST;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhzy on 2017/7/21.
 */
public interface Dao {

    //添加节点
    public boolean addNode(String label, Map<String, Object> node);

    //添加边
    public boolean addEdge(String startNodeId, String endNodeId);

    //添加边，有标签
    public boolean addEdge(String startLabel, String startNodeId, String endLabel, String endNodeId);

    //根据label查找节点
    public Map<String, Object> findValueMapByNodeId(String label, String nodeId);

    //根据id查找节点
    public Vertex findVertexByNodeId(String nodeId);

    //根据节点类型模糊查询
    public List<Vertex> fuzzyFindVertexByName(String label, String fuzzyTitle);

    //查看两个节点间的边是否存在,根据两个节点查询
    public boolean isEdgeExist(Vertex startNode, Vertex endNode);

    //查看两个节点之间的边是否存在，根据边id查询
    public boolean isEdgeExist(String startNodeId, String endNodeId);

    //查看一个节点是否存在
    public boolean isNodeExist(String label, String nodeId);

    //查看一个节点是否存在边
    public boolean isNodeEdgeExist(String label, String nodeId);

    //查看有没有出边
    public boolean isOutGoingEdgeExist(String label, String nodeId);

    //查看有没有入边
    public boolean isInComingEdgeEsist(String label, String nodeId);

    //根据nodeId删除节点
    public boolean deleteNode(String label, String nodeId);

    //根据nodeId删除一个node旁边的所有边
    public boolean deleteNodeEdge(String label, String nodeId);

    //根据nodeId删除一个node旁边的所有出边
    public boolean deleteOutGoingEdge(String label, String nodeId);

    //根据nodeId删除一个node旁边的所有入边
    public boolean deleteInComingEdge(String label, String nodeId);

    //删除边
    public boolean deleteEdge(String startNodeId, String endNodeId);

    //修改节点数据
    public boolean updateNode(String label, String nodeId, Map<String, Object> properties);

    //删除某一个标签的所有数据
    public boolean deleteAll(String label);

    //根据属性查询
    public List<Vertex> findNodesByLabelAndProperty(String label, String propertyKey, String propertyValue);

    //根据联合属性查询
    public List<Vertex> findNodesByTypeAndVersion(String label, Map<String, Object> properties);

    //根据id查询一个节点的周围所有节点及自身
    public List<Vertex> findNeighborsNodesById(long id);

    //根据nodeId查询一个节点周围所有节点及自身
    public List<Vertex> findNeighborsNodesByNodeId(String label, String nodeId);

    //根据id查询一个节点周围的所有关系
    public List<Edge> findNeighborsEdgesById(long id);

    //根据nodeId查询一个节点周围的所有关系
    public List<Edge> findNeighborsEdgesByNodeId(String label, String nodeId);

    //返回所有的节点
    public List<Vertex> findAllNodes();

    //将Vertex转化成Map
    public Map<String, Object> transferVertexToMap(Vertex vertex);

    //根据label查找节点
    public List<Vertex> findNodesByLabel(String label);

    //根据某个节点为中心，找他周围几度的节点集合
    public GraphTraversalSource findSubGraph(long id, int depth);

    //根据某个节点为中心，找他周围几度节点的集合
    public GraphTraversalSource findSubGraph(String label, String nodeId, int depth);

    //找到上游的几层节点
    public GraphTraversalSource findInComingEdge(long id, int depth);

    //找到上游的几层节点
    public GraphTraversalSource findInComingEdge(String label, String nodeId, int depth);

    //找到下游的几层节点
    public GraphTraversalSource findOutGoingEdge(long id, int depth);

    //找到下游的几层节点
    public GraphTraversalSource findOutGoingEdge(String label, String nodeId, int depth);

    //TODO
    public boolean updateEdge();
}
