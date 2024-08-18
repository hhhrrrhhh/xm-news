<template>
  <div class="news-list">
    <h1>{{ provinceName }} 新闻列表</h1>
    <ul>
      <li v-for="(item, index) in newsList" :key="item.id">
        <span class="number">{{ (currentPage - 1) * pageSize + index + 1 }}.</span>
        <a @click="$router.push('/front/newsDetail?id=' + encodeURIComponent(item.id))">{{ item.title }}</a>
      </li>
    </ul>
    <div class="pagination">
      <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">上一页</button>
      <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">下一页</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      provinceId: null,
      provinceName: '',
      newsList: [],
      pageSize: 15,
      currentPage: 1,
      totalPages: 1,
    };
  },
  mounted() {
    this.provinceId = this.$route.query.provinceId;
    this.loadProvinceInfo();
    this.loadProvinceNews();
  },
  methods: {
    loadProvinceInfo() {
      this.$request.get('/provinces/selectAll').then(res => {
        const province = res.data.find(p => p.provincesId == this.provinceId);
        if (province) {
          this.provinceName = province.provincesName;
        } else {
          this.$message.error('省份信息加载失败');
        }
      }).catch(error => {
        console.error("Error loading provinces:", error);
      });
    },
    loadProvinceNews() {
      this.$request.get('/news/selectPage', {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          provincesId: this.provinceId,
        }
      }).then(res => {
        if (res.code === '200') {
          this.newsList = res.data.list || [];
          this.totalPages = Math.ceil(res.data.total / this.pageSize);
        } else {
          this.$message.error(res.msg);
        }
      }).catch(error => {
        console.error("Error loading news:", error);
      });
    },
    changePage(page) {
      if (page > 0 && page <= this.totalPages) {
        this.currentPage = page;
        this.loadProvinceNews();
      }
    }
  }
}
</script>

<style scoped>
.news-list {
  width: 80%;
  margin: 20px auto;
  font-family: Arial, sans-serif;
}

.news-list h1 {
  text-align: center;
  margin-bottom: 20px;
}

.news-list ul {
  list-style: none;
  padding: 0;
}

.news-list ul li {
  border-bottom: 1px solid #eee;
  padding: 10px 0;
  font-size: 16px;
  display: flex;
  align-items: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.news-list ul li a {
  color: #007bff;
  text-decoration: none;
  flex-grow: 1;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.news-list ul li a:hover {
  text-decoration: underline;
}

.news-list ul li .number {
  font-weight: bold;
  margin-right: 10px;
  color: #555;
  flex-shrink: 0;
}

.pagination {
  text-align: center;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  background-color: #007bff;
  color: #fff;
  cursor: pointer;
  border-radius: 5px;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
