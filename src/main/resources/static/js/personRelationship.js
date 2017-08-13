/**
 * Created by hexu on 2016/12/7.
 */

function loadRelationshipPersonal() {
    var myChart = echarts.init(document.getElementById('relationship'));
    myChart.showLoading();
    $.ajax({
        url : "/query",
        type: "get",
        dataType : "json",
        success : function (graph) {
        myChart.hideLoading();
        graph.nodes.forEach(function (node) {
            node.itemStyle = null;
            node.value = node.value;
            node.symbolSize = Math.sqrt(node.value)*10;
            node.label = {
                normal: {
                    show: node.symbolSize > 1
                }
            };
        });

        option = {
            title: {
                text: '测试图形',
                top: 'bottom',
                left: 'right'
            },
            tooltip: {},
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            series : [
                {
                    name: '节点',
                    type: 'graph',
                    layout: 'force',
                   // circular: {
                   //     rotateLabel: true
                   // },
                    data: graph.nodes.map(function (node, idx) {
                        node.id = idx;
                        return node;
                    }),
                    links: graph.links,
                    roam: true,
                    label: {
                        normal: {
                            position: 'top',
                            formatter: '{b}'
                        }
                    },
                    lineStyle: {
                        normal: {
                            color: 'source',
                            curveness: 0.0
                        }
                    },
                    edgeSymbol: ['point', 'arrow'],
                    force: {
                        repulsion: 100,
                        edgeLength: 100
                    }
                }
            ]
        };
        myChart.setOption(option);
        // myChart.on("click", function(params) {
        //     window.open('/detailOfExpert/cooperateOfAuthor?name=' + encodeURIComponent(params.name)
        //         +"&&institution=" + encodeURIComponent(params.institution));
        // }
        // );
    }
    });
}

loadRelationshipPersonal();