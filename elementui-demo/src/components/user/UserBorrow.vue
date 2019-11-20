<template>
  <div id="borrow">
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
      <el-row>
        <el-button type="primary" v-if="borrow_book =='true'" v-on:click="subscribe(book.book_id)">
          <i class="fa fa-star" aria-hidden="true"></i>借阅
        </el-button>
        <el-button
          type="primary"
          v-if="borrow_book =='false'"
          v-on:click="unsubscribe(book.book_id)"
        >
          <i class="fa fa-times" aria-hidden="true"></i> 退订
        </el-button>
        <el-button type="primary" v-if="borrow_book =='crossed'">
          <i class="fa fa-minus-circle" aria-hidden="true"></i>读者已超过最大借书限制
        </el-button>
      </el-row>
    </el-dialog>

    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="ISBN">
        <el-input v-model="formInline.isbn" placeholder="ISBN" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item label="书名">
        <el-input v-model="formInline.name" placeholder="书名" v-on:change="onSubmit"></el-input>
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
import {
  getBookList,
  getBookDetails,
  isBorrow,
  insertBorrow,
  deleteborrow
} from "../../api";
export default {
  data() {
    return {
      borrow_book: "true",
      iscount: true,
      booklist: [],
      page: 0,
      pageSize: 8,
      total: 0,
      formInline: {
        isbn: "",
        name: ""
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
      dialogVisible: false
    };
  },

  mounted() {
    this.$store.dispatch("showHeader");
    this.loadData();
  },
  methods: {
    loadData() {
      var that = this;
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
      var User_id = sessionStorage.getItem("login_username");
      var params = {
        User_username: User_id,
        Book_id: val
      };
      isBorrow(params).then(function(result) {
        //借阅
        if (result.data.msg == "true") {
          that.borrow_book = "false";
        } else if (result.data.msg == "crossed") {
          that.borrow_book = "crossed";
        } else {
          //退订
          that.borrow_book = "true";
        }
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
    subscribe(val) {
      //   console.log(val);
      var User_username = sessionStorage.getItem("login_username");
      //   console.log(User_username);
      this.$confirm("确定借阅本书?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          var qs = require("qs");
          var that = this;
          var params = qs.stringify({
            Book_id: val,
            User_username: User_username
          });
          insertBorrow(params).then(function(result) {
            if (result.data.msg == "ok") {
              that.$message({
                type: "success",
                message: "借阅成功!"
              });
              that.dialogVisible = false;
            } else {
            }
          });
        })
        .catch(() => {
          //   this.$message({
          //     type: 'info',
          //     message: '已取消删除'
          //   });
        });
      //   var qs = require("qs");
      //   var that = this;
      //   var params = qs.stringify({
      //     Book_id: val,
      //     User_username: User_username
      //   });
      //   insertBorrow(params).then(function(result) {
      //       if (result.data.msg == "ok") {

      //       }
      //       else{

      //       }
      //   });
    },
    unsubscribe(val) {
      var User_username = sessionStorage.getItem("login_username");
      this.$confirm("确定退订本书?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
            var that = this;
          var params = {
            Book_id: val,
            User_username: User_username
          };
          deleteborrow(params).then(function(result) {
            if (result.data.msg == "ok") {
              that.$message({
                type: "success",
                message: "退订成功!"
              });
              that.dialogVisible = false;
            } else {
            }
          });
        })
        .catch(() => {
          //   this.$message({
          //     type: 'info',
          //     message: '已取消删除'
          //   });
        });
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