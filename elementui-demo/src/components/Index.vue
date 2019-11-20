<template>
  <div id="index">
    <el-tabs type="border-card">
      <el-tab-pane label="图书管理">
        <p>• &nbsp;根据图书编号、图书名称查询图书信息</p>
        <p>• &nbsp;可查询图书的编号、名称、作者、价格、在馆数量等</p>
      </el-tab-pane>
    </el-tabs>
    
  </div>
</template>
<script type="text/javascript">
export default {
  mounted() {
    this.open();
  },
  methods: {
    open() {
      const h = this.$createElement;

      this.$notify({
        title: "标题名称",
        message: h("i", { style: "color: teal" }, "欢迎进入图书管理系统"),
        offset: 200
      });
    }
  }
};
</script>