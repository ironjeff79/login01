<template>
  222222
    <!-- 柱状图容器 -->
    <div class="chart-container">
      <div ref="chart" class="chart"></div>
    </div>
  </template>
  
  <script>
//   import * as echarts from 'echarts';
  import axios from "axios";
  
  export default {
    mounted() {
      this.renderChart();
    },
    methods: {
      renderChart() {
        // 发送异步请求获取数据
        axios.get("/webinfo/getInfo")
          .then(res => {
            const data = res.data;
  
            // 将数据转换为柱状图所需的格式
            const chartData = data.map(item => ({
              foreignId: item.webId,
              clickCount: item.clickCount,
              webname: item.webName
            }));
  
            // 提取网页名称列表
            const weblist = chartData.map(item => item.webname);
  
            // 初始化 echarts 实例
            const chart = echarts.init(this.$refs.chart);
  
            // 配置图表选项
            const option = {
              title: {
                text: '网页点击量统计'
              },
              tooltip: {
                trigger: 'axis',
                axisPointer: {
                  type: 'shadow'
                }
              },
              xAxis: {
                type: 'category',
                data: weblist,
                axisLabel: {
                  formatter: '{value}'
                }
              },
              yAxis: {
                type: 'value',
                axisLabel: {
                  formatter: '{value} 次'
                }
              },
              series: [{
                type: 'bar',
                data: chartData.map(item => item.clickCount)
              }]
            };
  
            // 设置图表选项并渲染
            chart.setOption(option);
          })
          .catch(err => {
            console.error(err);
          });
      },
    }
  };
  </script>
  
  <style>
  .chart-container {
    width: 100%;
    height: 400px;
  }
  
  .chart {
    width: 100%;
    height: 100%;
  }
  </style>