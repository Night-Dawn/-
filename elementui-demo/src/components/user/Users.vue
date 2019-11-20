<template>
  <div id="users">
    <!-- 借阅详情 对话框-->
    <el-dialog title="借阅书籍" :visible.sync="dialog_borrow" size="large">
      <el-table v-loading="loading"
      element-loading-text="加载中" :data="tableData_borrow" style="width: 100%">
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
                <p class="table_p">
                  <i class="fa fa-jpy" aria-hidden="true"></i>
                  {{ props.row.book_price }}
                </p>
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
      </el-table>
    </el-dialog>
    <!-- 删除User对话框 -->
    <el-dialog title="删除对话框" :visible.sync="deletedialogVisible" size="tiny">
      <span>确认要删除此条信息吗?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deletedialogVisible = false">取 消</el-button>
        <el-button type="danger" @click="deleteClick()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 批量删除User对话框 -->
    <el-dialog title="删除对话框" :visible.sync="muldeldialogVisible" size="tiny">
      <span>确认要删除这些信息吗?</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="muldeldialogVisible = false">取 消</el-button>
        <el-button type="danger" @click="deletesClick()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户对话框 -->
    <el-dialog
      title="修改用户"
      :visible.sync="dialog_update"
      :close-on-click-modal="false"
      size="large"
    >
      <el-form :model="updateForm" :rules="updaterules" ref="updateForm" label-width="100px">
        <el-form-item label="用户编号" prop="User_id">
          <el-input readonly="readonly" v-model="updateForm.User_id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="User_name">
          <el-input v-model="updateForm.User_name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="User_sex">
          <el-select v-model="updateForm.User_sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="User_address">
          <el-input v-model="updateForm.User_address"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="User_phone">
          <el-input v-model="updateForm.User_phone"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="User_username">
          <el-input v-model="updateForm.User_username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="User_password">
          <el-input v-model="updateForm.User_password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="UpdateForm('updateForm')">更新</el-button>
          <el-button @click="dialog_update =false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- 新增用户对话框 -->
    <el-dialog title="新增用户" :visible.sync="dialog_add" :close-on-click-modal="false" size="large">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="姓名" prop="User_name">
          <el-input v-model="ruleForm.User_name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="User_sex">
          <el-select v-model="ruleForm.User_sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址" prop="User_address">
          <el-input v-model="ruleForm.User_address"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="User_phone">
          <el-input v-model="ruleForm.User_phone"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="User_username">
          <el-input v-model="ruleForm.User_username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="User_password">
          <el-input v-model="ruleForm.User_password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="可借图书数量" prop="User_borrow_max">
          <el-input readonly="readonly" v-model="ruleForm.User_borrow_max"></el-input>
        </el-form-item>
        <el-form-item label="已借图书数量" prop="User_borrow_number">
          <el-input readonly="readonly" v-model="ruleForm.User_borrow_number"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="用户编号">
        <el-input v-model="formInline.User_id" placeholder="用户编号" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="formInline.User_name" placeholder="姓名" v-on:change="onSubmit"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" v-on:click="addUser">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="danger" v-if="isSelectMul" v-on:click="openmulDel">批量删除</el-button>
      </el-form-item>
      <el-form-item>
        <el-pagination
          @current-change="handleCurrentChange"
          :page-size="pageSize"
          layout="prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </el-form-item>
    </el-form>

    <el-table
      :data="tableData"
      style="width: 100%"
      v-loading="loading2"
      element-loading-text="加载中"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="用户编号">
              <p class="table_p">{{ props.row.user_id }}</p>
            </el-form-item>
            <el-form-item label="姓名">
              <p class="table_p">{{ props.row.user_name }}</p>
            </el-form-item>
            <el-form-item label="性别">
              <p class="table_p">{{ props.row.user_sex }}</p>
            </el-form-item>
            <el-form-item label="地址">
              <p class="table_p">{{ props.row.user_address }}</p>
            </el-form-item>
            <el-form-item label="联系方式">
              <p class="table_p">{{ props.row.user_phone }}</p>
            </el-form-item>
            <el-form-item label="用户名">
              <p class="table_p">{{ props.row.user_username }}</p>
            </el-form-item>
            <el-form-item label="密码">
              <p class="table_p">{{ props.row.user_password }}</p>
            </el-form-item>
            <el-form-item label="可借图书数量">
              <p class="table_p">{{ props.row.user_borrow_max }}</p>
            </el-form-item>
            <el-form-item label="已借图书数量">
              <p class="table_p">{{ props.row.user_borrow_number }}</p>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column label="用户编号" prop="user_id"></el-table-column>
      <el-table-column label="姓名" prop="user_name"></el-table-column>
      <el-table-column label="性别" prop="user_sex"></el-table-column>
      <el-table-column label="地址" prop="user_address"></el-table-column>
      <el-table-column label="联系方式" prop="user_phone" width="130px"></el-table-column>
      <el-table-column fixed="right" label="操作" width="140px">
        <template slot-scope="scope">
          <!-- @click="handleClick(scope.row)" -->
          <el-button type="text" @click="handleClick(scope.row)" size="small">借阅</el-button>
          <el-button @click="updateClick(scope.row)" type="text" size="small">更新</el-button>
          <el-button @click="deleteUser(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<style type="text/css" scoped>
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
  width: 30%;
}
.table_p {
  color: #1d8ce0;
  margin-top: 20px;
}
</style>
<script type="text/javascript">
import {
  getUserList,
  checkUser,
  addUser,
  updateUser,
  deleteUser,
  getUserbook
} from "../../api";

export default {
  data() {
    var validateUser = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入用户名"));
      } else {
        var that = this;
        var params = {
          User_id: value
        };
        checkUser(params).then(function(result) {
          if (result.data.msg == "ok") {
            that.$message({
              message: "用户名可用",
              type: "success"
            });
          } else if (result.data.msg == "exist") {
            that.$message.error("用户名已存在");
          }
        });
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.User_password) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      deleteMsg: "",
      tableData: [],
      tableData_borrow: [],
      formInline: {
        User_id: "",
        User_name: ""
      },
      page: 0,
      total: 0,
      pageSize: 10,
      loading2: false,
      loading:false,
      selectedOptions: [],
      multipleSelection: [],
      isSelectMul: false,
      dialog_add: false,
      dialog_update: false,
      dialog_borrow: false,
      deletedialogVisible: false,
      muldeldialogVisible: false,
      ruleForm: {
        User_name: "",
        User_sex: "",
        User_address: "",
        User_phone: "",
        User_username: "",
        User_password: "",
        checkPass: "",
        User_borrow_max: "6",
        User_borrow_number: "0"
      },
      updateForm: {
        User_id: "",
        User_name: "",
        User_sex: "",
        User_address: "",
        User_phone: "",
        User_username: "",
        User_password: ""
      },
      rules: {
        User_name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        User_sex: [
          { required: true, message: "请选择性别", trigger: "change" }
        ],
        User_address: [
          { required: true, message: "请输入地址", trigger: "blur" }
        ],
        User_phone: [
          { required: true, message: "请输入联系方式", trigger: "blur" }
        ],
        User_username: [
          { required: true, validator: validateUser, trigger: "blur" },
          { min: 10, max: 10, message: "长度为10个字符", trigger: "change" }
        ],
        User_password: [
          { required: true, validator: validatePass, trigger: "blur" },
          { min: 6, message: "至少6个字符", trigger: "change" }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: "blur" }
        ]
      },
      updaterules: {
        User_name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        User_sex: [
          { required: true, message: "请选择性别", trigger: "change" }
        ],
        User_address: [
          { required: true, message: "请输入地址", trigger: "blur" }
        ],
        User_phone: [
          { required: true, message: "请输入联系方式", trigger: "blur" }
        ],
        User_username: [
          { required: true, validator: validateUser, trigger: "blur" },
          { min: 10, max: 10, message: "长度为10个字符", trigger: "change" }
        ],
        User_password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "至少6个字符", trigger: "change" }
        ]
      }
    };
  },
  mounted() {
    this.$store.dispatch("showHeader");
    this.loadData();
  },
  methods: {
    //加载数据
    loadData() {
      this.loading2 = true;
      var params = {
        User_id: this.formInline.User_id,
        User_name: this.formInline.User_name,
        page: this.page,
        pageSize: this.pageSize
      };
      getUserList(params)
        .then(
          function(result) {
            this.loading2 = false;
            this.tableData = result.data.list;
            this.total = result.data.total;
          }.bind(this)
        )
        .catch(
          function(error) {
            this.loading2 = false;
            console.log(error);
          }.bind(this)
        );
    },
    //改变分页大小
    handleCurrentChange(val) {
      this.page = val;
      this.loadData();
    },
    handleClick(row){
      
      var that = this;
      that.dialog_borrow=true;
      that.loading=true;
      var params = {
        User_id: row.user_id
      };
      getUserbook(params).then(function(result) {
        that.loading=false;
        that.tableData_borrow = result.data.list;
      });
      
    },
    //打开新增页面
    addUser() {
      this.dialog_add = true;
    },
    onSubmit() {
      this.loadData();
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    openmulDel() {
      this.muldeldialogVisible = true;
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
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          var qs = require("qs");
          var that = this;
          var params = qs.stringify({
            User_name: that.ruleForm.User_name,
            User_sex: that.ruleForm.User_sex,
            User_address: that.ruleForm.User_address,
            User_phone: that.ruleForm.User_phone,
            User_username: that.ruleForm.User_username,
            User_password: that.ruleForm.User_password,
            User_borrow_max: that.ruleForm.User_borrow_max,
            User_borrow_number: that.ruleForm.User_borrow_number
          });
          addUser(params).then(function(result) {
            if (result.data.msg == "ok") {
              that.dialog_add = false;
              that.$refs[formName].resetFields();
              that.loadData();

              const h = that.$createElement;
              that.$notify({
                title: "提示",
                message: "添加用户成功",
                type: "success"
              });
            } else {
            }
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    UpdateForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          var qs = require("qs");
          var that = this;
          var params = qs.stringify({
            User_id: this.updateForm.User_id,
            User_name: this.updateForm.User_name,
            User_sex: this.updateForm.User_sex,
            User_address: this.updateForm.User_address,
            User_phone: this.updateForm.User_phone,
            User_username: this.updateForm.User_username,
            User_password: this.updateForm.User_password
          });
          updateUser(params).then(function(result) {
            if (result.data.msg == "ok") {
              that.dialog_update = false;
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
    updateClick(row) {
      this.updateForm.User_id = row.user_id;
      this.updateForm.User_name = row.user_name;
      this.updateForm.User_sex = row.user_sex;
      this.updateForm.User_address = row.user_address;
      this.updateForm.User_phone = row.user_phone;
      this.updateForm.User_username = row.user_username;
      this.updateForm.User_password = row.user_password;
      this.dialog_update = true;
    },
    deleteUser(row) {
      var that = this;
      that.deleteMsg = row.user_id;
      this.deletedialogVisible = true;
    },
    deleteClick() {
      var that = this;
      var params = {
        User_id: that.deleteMsg
      };
      deleteUser(params).then(function(result) {
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
    deletesClick() {
      var that = this;
      that.multipleSelection.forEach(row => {
        var params = {
          User_id: row.user_id
        };
        deleteUser(params).then(function(result) {
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