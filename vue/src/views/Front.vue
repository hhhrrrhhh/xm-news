<template>
  <div>
    <div class="front-notice"><i class="el-icon-bell" style="margin-right: 2px"></i>公告：{{ top }}</div>
    <!--头部-->
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">项目前台</div>
      </div>
      <div class="front-header-center">
        <div class="front-header-nav">
          <el-menu :default-active="$route.path" mode="horizontal" router>
            <el-menu-item index="/front/home">首页</el-menu-item>
            <el-menu-item index="/front/person">个人中心</el-menu-item>
          </el-menu>
        </div>
      </div>
    </div>
    <!--主体-->
    <div class="main-body">
      <router-view ref="child" />
    </div>
  </div>
</template>

<script>
export default {
  name: "FrontLayout",

  data() {
    return {
      top: '',
      notice: [],
    };
  },

  mounted() {
    this.loadNotice();
  },

  methods: {
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data;
        let i = 0;
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content;
          setInterval(() => {
            this.top = this.notice[i].content;
            i++;
            if (i === this.notice.length) {
              i = 0;
            }
          }, 2500);
        }
      });
    },
  },
};
</script>

<style scoped>
@import "@/assets/css/front.css";
</style>
