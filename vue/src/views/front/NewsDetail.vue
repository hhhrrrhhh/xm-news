<template>
  <div style="width: 50%; margin: 10px auto">
    <div class="card">
      <div style="font-size: 24px; font-weight: bold; text-align: center; margin-bottom: 10px">{{ news.title }}</div>
      <el-button type="primary" @click="copyContent">一键复制</el-button>
      <div style="text-align: center; color: #888; margin-bottom: 20px">
        <span>发布时间：{{ news.time }}</span>
      </div>
      <div class="w-e-text">
        <!-- 使用 ref 来引用富文本内容 -->
        <div ref="editor" v-html="news.content" class="content-style"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "NewsDetail",
  data() {
    return {
      id: this.$route.query.id,
      news: {}
    }
  },
  created() {
      this.load()
  },
  methods: {
    load() {
      this.$request.get('/news/selectById/' + this.id).then(res => {
        this.news = res.data || {}
      })
    },
    copyContent() {
      // 创建一个不可见的、可编辑的div元素
      const tempDiv = document.createElement('div');
      tempDiv.style.position = 'absolute';
      tempDiv.style.left = '-9999px';
      tempDiv.style.whiteSpace = 'pre-wrap'; // 保持空白符和换行符
      tempDiv.setAttribute('contenteditable', 'true');
      document.body.appendChild(tempDiv);

      // 将富文本编辑器的内容复制到这个div中
      tempDiv.innerHTML = this.$refs.editor.innerHTML;

      // 选中div中的内容
      const range = document.createRange();
      range.selectNodeContents(tempDiv);
      const selection = window.getSelection();
      selection.removeAllRanges();
      selection.addRange(range);

      // 执行复制操作
      document.execCommand('copy');

      // 清除选中和移除临时div
      selection.removeAllRanges();
      document.body.removeChild(tempDiv);

      // 通知用户复制成功
      alert('内容已复制到剪贴板！');
    }
  }
}
</script>

<style scoped>
/* 指定富文本内容的样式 */
.content-style {
  font-family: 'Microsoft YaHei', sans-serif; /* 字体：微软雅黑 */
  font-size: 18px; /* 字号 */
  line-height: 50px; /* 行间距 */
  color: #333; /* 字体颜色 */
  padding: 10px; /* 内边距 */
}
</style>