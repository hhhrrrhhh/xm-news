<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入新闻标题关键字查询" style="width: 200px" v-model="title"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>

        <!-- 隐藏 ID 列 -->
        <el-table-column prop="id" label="序号" width="70" v-if="false"></el-table-column>

        <!-- 显示自动生成的序号 -->
        <el-table-column label="序号" width="70">
          <template v-slot="scope">
            <span>{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="title" label="新闻标题"></el-table-column>
        <el-table-column prop="provincesName" label="省份名称"></el-table-column>
        <el-table-column label="操作" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog :visible.sync="fromVisible" :close-on-click-modal="false" destroy-on-close>
      <span slot="title">新闻信息</span>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item label="新闻标题" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="省份名称" prop="provincesId">
          <el-select v-model="form.provincesId">
            <el-option v-for="item in provincesList" :key="item.provincesId" :value="item.provincesId" :label="item.provincesName"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="新闻内容" prop="content">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :visible.sync="fromVisible1" :close-on-click-modal="false" destroy-on-close>
      <span slot="title">文章内容</span>
      <div class="w-e-text">
        <div v-html="content"></div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fromVisible1 = false">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import E from "wangeditor"

export default {
  data() {
    return {
      provincesList: [],
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      title: null,
      fromVisible: false,
      fromVisible1: false,
      form: {},
      content: '',
      ids: [],
      editor: null,  // 富文本编辑器实例
      rules: {  // 表单验证规则
        title: [
          { required: true, message: '请输入新闻标题', trigger: 'blur' }
        ],
        provincesId: [
          { required: true, message: '请选择省份', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入新闻内容', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.load(1)
    this.$request.get('/provinces/selectAll').then(res => {
      this.provincesList = res.data
    })
  },
  methods: {
    preview(content) {
      this.content = content
      this.fromVisible1 = true
    },
    handleAdd() {
      this.form = {}
      this.fromVisible = true
      this.setRichText('')
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.fromVisible = true
      this.setRichText(this.form.content)
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.form.content = this.editor.txt.html()  // 获取富文本内容
          this.$request({
            url: this.form.id ? '/news/update' : '/news/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"}).then(() => {
        this.$request.delete('/news/delete/' + id).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(() => {
        this.$request.delete('/news/delete/batch', {data: this.ids}).then(res => {
          if (res.code === '200') {
            this.$message.success('操作成功')
            this.load(1)
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum
      this.$request.get('/news/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          title: this.title,
        }
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data?.list
          this.total = res.data?.total
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    reset() {
      this.title = null
      this.load(1)
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },
    setRichText(html) {
      this.$nextTick(() => {
        this.editor = new E('#editor')
        this.editor.create()
        this.editor.txt.html(html)
      })
    },
  }
}
</script>

<style scoped>
</style>
