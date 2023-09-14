<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb class=ArrowRight>
      <el-breadcrumb-item :to="{ path: '/admin' }">管理システム</el-breadcrumb-item>
      <el-breadcrumb-item>ページ管理</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <!-- 柱状图容器 -->
  <div class="chart-container">
    <div class="chart-options">
      <h2 class="chart-title">网站Top点击量</h2>
      <el-select v-model="value" class="m-2" placeholder="选择查看网站数" @change="updateChart">
        <el-option value="5" label="前5个" />
        <el-option value="10" label="前10个" />
        <el-option value="15" label="前15个" />
        <el-option value="20" label="前20个" />
      </el-select>
    </div>
    <div ref="chart" class="chart" style="height:350px;"></div>
  </div>
</template>

<script>
import * as echarts from "echarts"
import axios, { } from 'axios'
import { ElBreadcrumb, ElBreadcrumbItem } from 'element-plus'
// import { Edit, Setting, Delete, Search } from '@element-plus/icons-vue'  
export default {
  data() {
    return {
      value: "",
      webId: [],
      click_count: [],
      data: [10, 20, 30, 40, 50],
      option: {
        xAxis: {
          type: 'category',
          data: [10, 20, 30, 40, 50],
          axisLabel: {
            interval: 0,
            rotate: 40
          }
        },
        yAxis: {
          type: 'value'
        },
        series:
        {
          data: [10, 20, 30, 40, 50],
          type: 'bar',
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)'
          }
        },


      }
    }
  },
  mounted() {
    this.renderChart(5)
    console.log("!!!mounted")

    // const chart = echarts.init(this.$refs.chart);
    // chart.on("click", this.handleChartClick);
  },


  methods: {
    renderChart() {
      const ChartDom = this.$refs.chart
      const myChart = echarts.init(ChartDom)
      // const chart = echarts.init(this.$refs.chart);
      myChart.setOption(this.option)
      // 设置图表选项并渲染
      // chart.setOption(option);
    },


    updateChart() {
      this.renderChart(this.value);
      axios({
        method: 'post',
        url: this.$http + "/getWebInfo",
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        data: JSON.stringify(this.value)
      })
        .then((response) => {
          var data3 = response.data;
          console.log(data3)
          this.option.xAxis.data = data3.maps.web_id.map(String);
          this.option.series.data = data3.maps.click_count.map(Number);
          this.renderChart(this.value);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // handleChartClick(event) {
    //   const dataIndex = event.dataIndex;
    //   const clickedWebId = this.chartData[dataIndex].webId;
    //   const routePath = {
    //     path: "/webPage",
    //     query: {
    //       webId: clickedWebId
    //     }
    //   };
    //   router.push(routePath)
    // },
  },

  created() {
    axios({
      method: 'post',
      url: this.$http + "/getWebInfo",
      headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      data: JSON.stringify(5)
    })
      .then((response) => {
        var data3 = response.data;
        console.log(data3)
        this.option.xAxis.data = data3.maps.web_id.map(String);
        this.option.series.data = data3.maps.click_count.map(Number);
        this.renderChart(5);
      })
      .catch(function (error) {
        console.log(error);
      });
  },
}
</script>

<style  scoped>
/* 全局样式表 */
html,
body,
#app {
  height: 100%;
  margin: 0;
  padding: 0;
}

.chart-container {
  width: 100%;
  height: 500px;
}

.chart {
  width: 100%;
  height: 100%;
}

.chart-options {
  display: flex;
}

.m-2 {
  padding-top: 25px;
  margin-left: 10px;
}

.ArrowRight {
  padding-top: 15px;
  padding-left: 8px;
  text-align: center;
}
</style>