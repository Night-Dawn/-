<template>
  <div id="index">
    <el-dialog title="图书详情" :visible.sync="dialogVisible" size="large">
      <div style="position:absolute;top:25px;left:700px;">
        <img v-bind:src="book.book_img" class="image" />
      </div>
      <el-row :gutter="24" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">ISBN</el-tag>:
        </el-col>
        <el-col :span="12">
          <span>{{book.book_id}}</span>
        </el-col>
      </el-row>
      <el-row :gutter="14" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">图书名称</el-tag>:
        </el-col>
        <el-col :span="12">
          <span>{{book.book_name}}</span>
        </el-col>
      </el-row>
      <el-row :gutter="14" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">作者</el-tag>:
        </el-col>
        <el-col :span="12">
          <span>{{book.book_writer}}</span>
        </el-col>
      </el-row>
      <el-row :gutter="14" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">出版社</el-tag>:
        </el-col>
        <el-col :span="12">
          <span>{{book.book_company}}</span>
        </el-col>
      </el-row>
      <el-row :gutter="14" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">出版日期</el-tag>:
        </el-col>
        <el-col :span="12">
          <span>{{book.book_date}}</span>
        </el-col>
      </el-row>
      <el-row :gutter="23" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">简介</el-tag>:
        </el-col>
        <el-col :span="21">
          <span>{{book.book_brief}}</span>
        </el-col>
      </el-row>

      <el-row :gutter="14" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">价格</el-tag>:
        </el-col>
        <el-col :span="12">
          <i class="fa fa-jpy" aria-hidden="true"></i>
          <span>{{book.book_price}}</span>
        </el-col>
      </el-row>
      <el-row :gutter="14" style="margin-bottom:20px;color:#58B7FF">
        <el-col :span="2">
          <el-tag type="success">库存</el-tag>:
        </el-col>
        <el-col :span="12">
          <span>{{book.book_number}}</span>
        </el-col>
      </el-row>
    </el-dialog>

    <el-dialog title="新增图书" :visible.sync="dialog_add" :close-on-click-modal="false" size="large">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="ruleForm.isbn"></el-input>
        </el-form-item>
        <el-form-item label="图书名称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="writer">
          <el-input v-model="ruleForm.writer"></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="company">
          <el-input v-model="ruleForm.company"></el-input>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="出版时间" prop="date">
              <el-input v-model="ruleForm.date"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-upload
              class="avatar-uploader"
              style="width:300px;"
              ref="upload"
              :action="uploadUrl"
              :on-change="handlePreview"
              :on-remove="handleRemove"
              :auto-upload="false"
              list-type="picture"
            >
              <p>请尽量上传430x430像素图片</p>
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            </el-upload>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item label="图书价格" prop="price">
              <el-input v-model="ruleForm.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="图书库存" prop="number">
              <el-input v-model="ruleForm.number"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="简介" prop="brief">
          <el-input type="textarea" v-model="ruleForm.brief"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
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
        <el-button type="danger" v-on:click="addBook">新增</el-button>
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
    <el-row>
      <el-col
        :span="4"
        v-for="book in booklist"
        :key="book.book_id"
        :offset="1"
        style="margin-bottom:40px"
      >
        <el-card
          :body-style="{ padding: '0px', height:'340px'}"
          shadow="hover"
          style="width: 230px;height: 320px;"
        >
          <el-button
            style="padding:0px;margin:0px;"
            v-on:click="searchbook(book.book_id)"
            :plain="true"
            type="info"
          >
            <div style="padding: 6px;height: 310px">
              <div style="position: flex;top: 30px;">
                <img v-bind:src="book.book_img" class="image" />
              </div>
              <div>
                <el-tag
                  type="primary"
                  style="position: relative;top: 15px;text-align: center;"
                >{{book.book_name}}</el-tag>
              </div>
              <div>
                <el-tag
                  color="#2edfa3"
                  style="position: relative;top: 15px;text-align: center;"
                >{{book.book_writer}}/{{book.book_date}}</el-tag>
              </div>
            </div>
          </el-button>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script type="text/javascript">
import { getBookList, getBookDetails, uploadFile, addBook } from "../../api";
export default {
  data() {
    return {
      img_name: "",
      booklist: [],
      page: 0,
      pageSize: 8,
      total: 0,
      formInline: {
        isbn: "",
        name: ""
      },
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
        price: [{ required: true, message: "请输入价格", trigger: "blur" }],
        number: [{ required: true, message: "请输入库存", trigger: "blur" }],
        brief: [{ required: true, message: "请输入简介", trigger: "blur" }]
      },
      book: {
        book_id: "",
        book_name: "",
        book_writer: "",
        book_company: "",
        book_date: "",
        book_brief: "",
        book_img: "",
        book_price: "",
        book_number: ""
      },
      dialogVisible: false,
      dialog_add: false
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
    loadData() {
      var that = this;
      // this.load = true;

      var params = {
        Book_id: that.formInline.isbn,
        Book_name: that.formInline.name,
        page: that.page,
        pageSize: that.pageSize
      };
      getBookList(params).then(function(result) {
        that.booklist = result.data.list;
        that.total = result.data.total;
      });
    },
    searchbook(val) {
      var that = this;
      var params = {
        Book_id: val
      };
      getBookDetails(params).then(function(result) {
        that.book.book_id = result.data.detail.book_id;
        that.book.book_name = result.data.detail.book_name;
        that.book.book_writer = result.data.detail.book_writer;
        that.book.book_company = result.data.detail.book_company;
        that.book.book_date = result.data.detail.book_date;
        that.book.book_brief = result.data.detail.book_brief;
        that.book.book_img = result.data.detail.book_img;
        that.book.book_price = result.data.detail.book_price;
        that.book.book_number = result.data.detail.book_number;
        that.dialogVisible = true;
      });
    },
    addBook() {
      this.dialog_add = true;
    },
    onSubmit() {
      this.loadData();
    },
    //跳转页数
    handleCurrentChange(val) {
      this.page = val;
      this.loadData();
    },
    submitForm(formName) {
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
            Book_brief: this.ruleForm.brief,
            Book_img: this.img_name
          });
          addBook(params).then(function(result) {
            if (result.data.msg == "ok") {
              that.$refs.upload.submit();
              that.$refs.upload.clearFiles();
              that.dialog_add = false;
              that.loadData();

              const h = that.$createElement;
              that.$notify({
                title: "提示",
                message: "添加书籍成功",
                type: "success"
              });
            } else {
              if (result.data.msg == "error") {
                const h = that.$createElement;

                that.$notify({
                  title: "提示",
                  message: "添加书籍信息已存在",
                  type: "error"
                });
              } else {
                const h = that.$createElement;

                that.$notify({
                  title: "提示",
                  message: "添加书籍错误",
                  type: "error"
                });
              }
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.$refs.upload.clearFiles();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      this.img_name = file.name;
    },
    removeForm() {
      this.ruleForm.isbn = "";
      this.ruleForm.name = "";
      this.ruleForm.writer = "";
      this.ruleForm.company = "";
      this.ruleForm.date = "";
      this.ruleForm.price = "";
      this.ruleForm.number = "";
      this.ruleForm.brief = "";
      this.img_name = "";
    }
  }
};
</script>
<style>
.image {
  height: 200px;
  width: auto;
  margin: 20px;
}
</style>