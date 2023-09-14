<!-- 页面右侧小组件 -->
<template>
    <div>
      <h4 class="text-gray">
        <i class="linecons-tag" :id="transName(item)"></i>{{transName(item)}}
      </h4>
      <div class="row">
        <div class="col-sm-3" v-for="(web, idx) in item.web" :key="idx">
          <div class="xe-widget xe-conversations box2 label-info" title=""
            @click="sendMsg(web)"
            data-toggle="tooltip" 
            data-placement="bottom" 
            :data-original-title="web.url">
  
            <div class="xe-comment-entry">
              <a class="xe-user-img">
                <img :src="web.logo" class="lozad img-circle" width="40">
              </a>
              <div class="xe-comment">
                <a href="#" class="xe-user-name overflowClip_1">
                  <strong>{{web.title}}</strong>
                </a>
                <p class="overflowClip_2">{{web.desc}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <br />
    </div>
  </template>
  
  <script>
  export default {
    name: 'WebItem',
    props: {
      item: Object,
      transName: Function
    },
    methods: {
      sendMsg(web) {
        this.targetPage = web
        this.$emit('func', this.targetPage)
        const params = {
          foreignId: web.foreignId,
          title: web.title
        };
      //   axios({
      //   method: 'post',
      //   url: this.$http + "/addCount/"+webId,
      //   headers: { 'Content-Type': 'application/json;charset=UTF-8' },
      //   data: JSON.stringify(this.value)
      // })
      //   .then((response) => {
      //     var data3 = response.data;
      //     console.log(data3)
      //     this.option.xAxis.data = data3.maps.web_id.map(String);
      //     this.option.series.data = data3.maps.click_count.map(Number);
      //     this.renderChart(this.value);
      //   })
      //   .catch(function (error) {
      //     console.log(error);
      //   });
      },
      openweb(url) {
        window.open(url, '_blank');
      }
    },
      
    }
  
  </script>
  
  <style scoped>
  i {
    margin-right: 7px;
  }
  </style> 
  
  