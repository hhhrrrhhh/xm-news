<template>
  <div class="main-content">
    <div class="header">
      <h1>内蒙古今日招聘信息汇总</h1>
      <button @click="goToLogin" class="login-button">登录</button> <!-- 登录按钮 -->
    </div>

    <div class="content-area">
      <!-- 动态生成新闻框 -->
      <div v-for="province in provinces" :key="province.provincesId" class="column">
        <h2>{{ province.provincesName }}<a :href="`/front/provinceNews?provinceId=${province.provincesId}`" class="more-link">更多...</a>
        </h2>
        <ul>
          <li v-for="(item, index) in province.newsList.slice(0, 10)" :key="item.id">
            <span class="number">{{ index + 1 }}.</span>
            <a @click="$router.push('/front/newsDetail?id=' + encodeURIComponent(item.id))">{{ item.title }}</a>
          </li>
        </ul>
      </div>
    </div>

    <footer class="footer">
      &copy; 2024 新闻. 保留所有权利。
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      provinces: [],  // 存储从服务器加载的省份及其新闻列表
      pageSize: 10,  // 每页显示的新闻数量
    }
  },
  mounted() {
    this.loadProvincesAndNews();
  },
  methods: {
    loadProvincesAndNews() {
      this.$request.get('/provinces/selectAll').then(res => {
        console.log('Provinces loaded:', res.data); // 调试输出
        this.provinces = res.data.map(province => ({
          ...province,
          newsList: [] // 初始化每个省份的新闻列表为空
        }));

        this.provinces.forEach(province => {
          this.loadProvinceNews(province, 1);
        });
      }).catch(error => {
        console.error("Error loading provinces:", error);
      });
    },
    loadProvinceNews(province, pageNum) {
      this.$request.get('/news/selectPage', {
        params: {
          pageNum: pageNum,
          pageSize: this.pageSize,
          provincesId: province.provincesId,  // 按provincesId加载新闻
        }
      }).then(res => {
        if (res.code === '200') {
          console.log(`News for ${province.provincesName} loaded:`, res.data?.list); // 调试输出
          province.newsList = res.data?.list || [];
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error(`Error loading news for province ${province.provincesName}:`, error);
      });
    },
    goToLogin() {
      console.log("Login button clicked");
      this.$router.push('/login');  // 跳转到登录页面
    }
  }
}
</script>

<style scoped>
body {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  margin: 0;
  padding: 0;
  background-color: #f7f7f7;
}

.main-content {
  width: 95%;
  margin: 20px auto;
}

.header {
  text-align: center;
  padding: 20px 0;
  background-color: #fff;
  border-bottom: 1px solid #ddd;
}

.login-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  position: absolute;
  right: 20px;
  top: 20px;
}

.login-button:hover {
  background-color: #0056b3;
}




.content-area {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start; /* 确保元素从左到右对齐 */
  margin-top: 20px;
}

.content-area .column {
  width: 23%;
  background-color: #fff;
  margin-bottom: 30px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  box-sizing: border-box;
  margin-right: 2%; /* 为列添加右边距以保持间距 */
}

/* 每行的最后一个列元素右边距设置为0 */
.content-area .column:nth-child(4n) {
  margin-right: 0;
}

.content-area .column h2 {
  font-size: 18px;
  border-bottom: 2px solid #333;
  padding-bottom: 10px;
  margin-bottom: 15px;
  position: relative;
}

.content-area .column .more-link {
  position: absolute;
  top: 15px;
  right: 15px;
  font-size: 14px;
  color: #007bff;
  text-decoration: none;
}

.content-area .column .more-link:hover {
  text-decoration: underline;
}

.content-area ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.content-area ul li {
  border-bottom: 1px solid #eee;
  padding: 10px 0;
  font-size: 14px;
  display: flex;
  align-items: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.content-area ul li:last-child {
  border-bottom: none;
}

.content-area ul li a {
  color: #333;
  text-decoration: none;
  flex-grow: 1;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.content-area ul li a:hover {
  text-decoration: underline;
}

.content-area ul li .number {
  font-weight: bold;
  margin-right: 10px;
  color: #555;
  flex-shrink: 0;
}

.footer {
  text-align: center;
  padding: 20px;
  background-color: #fff;
  border-top: 1px solid #ddd;
  margin-top: 40px;
}
</style>
