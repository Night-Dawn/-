<template>
    <el-table :data="tableData"  v-loading="loading2"
    element-loading-text="加载中"  style="width: 100%">
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
            <el-form-item label="借阅时间">
              <p class="table_p">{{ props.row.borrows[0].borrow_date }}</p>
            </el-form-item>
            <el-form-item label="归还时间" >
              <p class="table_p">{{ props.row.borrows[0].return_date }}</p>
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
      <el-table-column label="出版社" prop="book_company"></el-table-column>
      <el-table-column label="出版时间" prop="book_date"></el-table-column>
    </el-table>
</template>

<script>
import { getborrowBook } from '../../api';
export default {
  data() {
    return {
        loading2:false,
        tableData:[],
    };
  },
  mounted() {
    this.$store.dispatch("showHeader");
    this.loadData();
  },
  methods: {
    loadData() {
      var User_id = sessionStorage.getItem("login_username");
      var that = this;
      var params = {
        User_username: User_id
      };
      getborrowBook(params).then(function(result) {
          that.tableData = result.data.list;
        console.log(result.data.list);
      });
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