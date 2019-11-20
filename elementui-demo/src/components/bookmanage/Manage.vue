<template>
  <div id="manage">
    <!-- 删除Book对话框 -->
    <el-dialog title="删除对话框" :visible.sync="deletedialogVisible" size="tiny">
      <span>确认要删除此条信息吗?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deletedialogVisible = false">取 消</el-button>
        <el-button type="danger" @click="deleteClick()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 批量删除Book对话框 -->
    <el-dialog title="删除对话框" :visible.sync="muldeldialogVisible" size="tiny">
      <span>确认要删除这些信息吗?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="muldeldialogVisible = false">取 消</el-button>
        <el-button type="danger" @click="deletesClick()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改Book对话框 -->
    <el-dialog title="图书更新" :visible.sync="dialogVisible" size="small">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="ruleForm.isbn" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="图书名称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="writer">
          <el-input v-model="ruleForm.writer"></el-input>
        </el-form-item>
        <el-form-item label="出版时间" prop="date">
          <el-input v-model="ruleForm.date"></el-input>
        </el-form-item>
        <el-form-item
          label="图书价格"
          prop="price"
          :rules="[
                { required: true, message: '价格不能为空'},
                { type: 'number', message: '价格必须为数字值'}
                ]"
        >
          <el-input v-model.number="ruleForm.price" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="图书库存"
          prop="number"
          :rules="[
                { required: true, message: '库存不能为空'},
                { type: 'number', message: '库存必须为数字值'}
                ]"
        >
          <el-input v-model.number="ruleForm.number" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="brief">
          <el-input type="textarea" :rows="6" v-model="ruleForm.brief"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateForm('ruleForm')">更新</el-button>
          <el-button @click="dialogVisible =false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="ISBN">
        <el-input v-model="formInline.isbn" placeholder="ISBN" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item label="书名">
        <el-input v-model="formInline.name" placeholder="书名" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" v-if="isSelectMul" v-on:click="openmulDel">批量删除</el-button>
      </el-form-item>
      <el-form-item style="margin-left:100px">
        <el-pagination
          @current-change="handleCurrentChange"
          :page-size="pageSize"
          layout="prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </el-form-item>
    </el-form>
    <el-table :data="tableData"  v-loading="loading2"
    element-loading-text="加载中" @selection-change="handleSelectionChange" style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="ISBN">
              <p class="table_p">{{ props.row.book_id }}</p>
            </el-form-item>
            <el-form-item label="图书名称">
              <p class="table_p">{{ props.row.book_name }}</p>
            </el-form-item>
            <el-form-item label="作者">
              <p class="table_p">{{ props.row.book_writer }}</p>
            </el-form-item>
            <el-form-item label="出版社">
              <p class="table_p">{{ props.row.book_company }}</p>
            </el-form-item>
            <el-form-item label="出版时间">
              <p class="table_p">{{ props.row.book_date }}</p>
            </el-form-item>
            <el-form-item label="价格">
             
              <p class="table_p"> <i class="fa fa-jpy" aria-hidden="true"></i>{{ props.row.book_price }}</p>
            </el-form-item>
            <el-form-item label="库存">
              <p class="table_p">{{ props.row.book_number }}</p>
            </el-form-item>
            <el-row>
              <el-form-item label="简介" style="width:90%">
                <span>{{ props.row.book_brief}}</span>
              </el-form-item>
            </el-row>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column label="ISBN" prop="book_id"></el-table-column>
      <el-table-column label="图书名称" prop="book_name"></el-table-column>
      <el-table-column label="作者" prop="book_writer"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">更新</el-button>
          <el-button @click="deleteBook(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getBookList, updateBook, deleteBook } from "../../api";
export default {
  data() {
    return {
      deleteMsg: "",
      tableData: [],
      page: 0,
      pageSize: 10,
      total: 0,
      formInline: {
        isbn: "",
        name: ""
      },
      loading2:false,
      ruleForm: {
        isbn: "",
        name: "",
        writer: "",
        company: "",
        date: "",
        price: "",
        number: "",
        brief: ""
      },
      rules: {
        isbn: [
          { required: true, message: "请输入ISBN", trigger: "blur" },
          { min: 13, max: 13, message: "长度在13个字符", trigger: "change" }
        ],
        name: [
          { required: true, message: "请输入图书名称", trigger: "blur" },
          { min: 2, message: "长度至少2字符", trigger: "change" }
        ],
        writer: [{ required: true, message: "请输入作者", trigger: "blur" }],
        company: [{ required: true, message: "请输入出版社", trigger: "blur" }],
        date: [{ required: true, message: "请输入出版时间", trigger: "blur" }],
        brief: [{ required: true, message: "请输入简介", trigger: "blur" }]
      },
      dialogVisible: false,
      deletedialogVisible: false,
      muldeldialogVisible: false,
      multipleSelection: [],
      isSelectMul: false
    };
  },
  computed: {
    uploadUrl() {
      return uploadFile;
    }
  },
  mounted() {
    this.$store.dispatch("showHeader");
    this.loadData();
  },
  methods: {
    handleClick(row) {
      this.ruleForm.isbn = row.book_id;
      this.ruleForm.name = row.book_name;
      this.ruleForm.writer = row.book_writer;
      this.ruleForm.company = row.book_company;
      this.ruleForm.date = row.book_date;
      this.ruleForm.price = row.book_price;
      this.ruleForm.number = row.book_number;
      this.ruleForm.brief = row.book_brief;
      this.dialogVisible = true;
    },
    loadData() {
      this.loading2 = true;
      var that = this;
      var params = {
        Book_id: that.formInline.isbn,
        Book_name: that.formInline.name,
        page: that.page,
        pageSize: that.pageSize
      };
      getBookList(params).then(function(result) {
        that.loading2 = false;
        that.tableData = result.data.list;
        that.total = result.data.total;
      });
    },
    onSubmit() {
      this.loadData();
    },
    //跳转页数
    handleCurrentChange(val) {
      this.page = val;
      this.loadData();
    },
    updateForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          var qs = require("qs");
          var that = this;
          var params = qs.stringify({
            Book_id: this.ruleForm.isbn,
            Book_name: this.ruleForm.name,
            Book_writer: this.ruleForm.writer,
            Book_company: this.ruleForm.company,
            Book_date: this.ruleForm.date,
            Book_price: this.ruleForm.price,
            Book_number: this.ruleForm.number,
            Book_brief: this.ruleForm.brief
          });
          updateBook(params).then(function(result) {
            if (result.data.msg == "ok") {
              that.dialogVisible = false;
              that.loadData();
              const h = that.$createElement;
              that.$notify({
                title: "提示",
                message: "修改信息成功",
                type: "success"
              });
            } else {
              const h = that.$createElement;

              that.$notify({
                title: "提示",
                message: "修改信息出错",
                type: "error"
              });
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    deleteBook(row) {
      var that = this;
      that.deleteMsg = row.book_id;
      this.deletedialogVisible = true;
    },
    openmulDel() {
      this.muldeldialogVisible = true;
    },
    deleteClick() {
      var that = this;
      var params = {
        Book_id: that.deleteMsg
      };
      deleteBook(params).then(function(result) {
        if (result.data.msg == "ok") {
          that.deletedialogVisible = false;
          that.loadData();
          const h = that.$createElement;
          that.$notify({
            title: "提示",
            message: "删除信息成功",
            type: "success"
          });
        } else {
          const h = that.$createElement;

          that.$notify({
            title: "提示",
            message: "删除信息出错",
            type: "error"
          });
        }
      });
    },
    handleSelectionChange(val) {
      var that = this;
      that.multipleSelection = val;
      if (that.multipleSelection.length == 0) {
        that.isSelectMul = false;
      } else {
        that.isSelectMul = true;
      }
    },
    deletesClick() {
      var that = this;
      that.multipleSelection.forEach(row => {
        var params = {
          Book_id: row.book_id
        };
        deleteBook(params).then(function(result) {
          if (result.data.msg == "ok") {
            that.loadData();
            const h = that.$createElement;
            that.$notify({
              title: "提示",
              message: "删除信息成功",
              type: "success"
            });
          } else {
            const h = that.$createElement;
            that.$notify({
              title: "提示",
              message: "删除信息出错",
              type: "error"
            });
          }
        });
      });
      that.muldeldialogVisible = false;
    }
  }
};
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 40%;
}
.table_p{
  margin-top: 20px;
}
</style>