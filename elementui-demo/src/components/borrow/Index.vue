<template>
  <div id="index">
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
    <el-dialog
      title="日志更新"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      size="small"
    >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="流水号" prop="borrow_id">
          <el-input v-model="ruleForm.borrow_id" disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="ruleForm.isbn"></el-input>
        </el-form-item>
        <el-form-item label="用户编号" prop="user_id">
          <el-input v-model="ruleForm.user_id"></el-input>
        </el-form-item>
        <el-form-item label="借出日期" prop="borrow_date">
          <el-input
            type="date"
            placeholder="选择日期"
            v-model="ruleForm.borrow_date"
            style="width: 100%;"
          ></el-input>
        </el-form-item>
        <el-form-item label="归还日期" prop="return_date">
          <el-input
            type="date"
            placeholder="选择日期"
            v-model="ruleForm.return_date"
            style="width: 100%;"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateForm('ruleForm')">更新</el-button>
          <el-button @click="dialogVisible =false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="流水号">
        <el-input v-model="formInline.borrow_id" placeholder="流水号" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item label="ISBN">
        <el-input v-model="formInline.isbn" placeholder="ISBN" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item label="用户编号">
        <el-input v-model="formInline.user_id" placeholder="用户编号" v-on:change="onSubmit"></el-input>
      </el-form-item>
       <el-form-item>
        <el-button type="danger" v-if="isSelectMul" v-on:click="openmulDel">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" @selection-change="handleSelectionChange"  style="width: 100%">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="borrow_id" label="流水号"></el-table-column>
      <el-table-column prop="book_id" label="ISBN"></el-table-column>
      <el-table-column prop="user_id" label="用户编号"></el-table-column>
      <el-table-column prop="borrow_date" label="借出日期"></el-table-column>
      <el-table-column prop="return_date" label="归还日期"></el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">更新</el-button>
          <el-button @click="deleteBorrow(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top:50px">
      <el-pagination
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import { getBorrowList, updateBorrow, deleteBorrow } from "../../api";
export default {
  data() {
    return {
      deleteMsg: "",
      deletedialogVisible: false,
      page: 0,
      pageSize: 10,
      total: 0,
      formInline: {
        borrow_id: "",
        isbn: "",
        user_id: ""
      },
      tableData: [],
      dialogVisible: false,
      ruleForm: {
        borrow_id: "",
        isbn: "",
        user_id: "",
        borrow_date: "",
        return_date: ""
      },
      rules: {
        isbn: [
          { required: true, message: "请输入ISBN" },
          {
            pattern: /^\d{13,13}$/,
            message: "长度在 13个字符",
            trigger: "change"
          }
        ],
        user_id: [{ required: true, message: "请输入用户编号" }],
        borrow_date: [
          {
            required: true,
            message: "请选择日期",
            trigger: "blur"
          }
        ],
        return_date: [
          {
            required: true,
            message: "请选择日期",
            trigger: "blur"
          }
        ]
      },
      multipleSelection: [],
      isSelectMul: false,
      muldeldialogVisible:false
    };
  },
  mounted() {
    this.$store.dispatch("showHeader");
    this.loadData();
  },
  methods: {
    handleClick(row) {
      this.ruleForm.borrow_id = row.borrow_id;
      this.ruleForm.isbn = row.book_id * 1;

      this.ruleForm.user_id = row.user_id;
      this.ruleForm.borrow_date = row.borrow_date;
      this.ruleForm.return_date = row.return_date;
      this.dialogVisible = true;
    },
    deleteBorrow(row) {
      var that = this;
      that.deleteMsg = row.borrow_id;
      this.deletedialogVisible = true;
    },
    deleteClick() {
      var that = this;
      var params = {
        Borrow_id: that.deleteMsg
      };
      deleteBorrow(params).then(function(result) {
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
    updateForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          var qs = require("qs");
          var that = this;
          var params = qs.stringify({
            Borrow_id: that.ruleForm.borrow_id,
            Book_id: that.ruleForm.isbn,
            User_id: that.ruleForm.user_id,
            Borrow_date: that.ruleForm.borrow_date,
            Return_date: that.ruleForm.return_date
          });
          updateBorrow(params).then(function(result) {
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
    loadData() {
      var that = this;
      var params = {
        Borrow_id: that.formInline.borrow_id,
        Book_id: that.formInline.isbn,
        User_id: that.formInline.user_id,
        page: that.page,
        pageSize: that.pageSize
      };
      getBorrowList(params).then(function(result) {
        that.tableData = result.data.list;
        that.total = result.data.total;
      });
    },
    onSubmit() {
      this.loadData();
    },
    handleCurrentChange(val) {
      this.page = val;
      this.loadData();
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
    openmulDel() {
      this.muldeldialogVisible = true;
    },
    deletesClick() {
      var that = this;
      that.multipleSelection.forEach(row => {
        var params = {
          Borrow_id: row.borrow_id
        };
        deleteBorrow(params).then(function(result) {
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
</style>