<template>
  <div class="friend">
    <el-card class="box-card">
      <div slot="header" class="d-flex align-items-center">
        <img class="card-icon" src="../assets/lianjie.png" />
        <span>友链</span>
        <el-button
          class="ml-auto"
          style="padding: 3px 0"
          type="text"
          @click="dialogFormVisible = true"
        >交换友链</el-button>
      </div>
      <el-row :gutter="20">
        <el-col :span="12" class="friend"  v-for="friend in friends" v-bind:key="friend.link_id">
          <div class="logo">
            <el-avatar :src="friend.avatar" alt />
          </div>
          <div class="link">
            <a :href="friend.link_url">{{friend.link_name}}</a>
          </div>
        </el-col>
  
      </el-row>
 

    </el-card>
    <!-- 编辑弹出框 -->
    <el-dialog title="交换友链" :visible.sync="dialogFormVisible" width="30%">
      <el-form ref="friendform" :model="friendform" label-width="70px">
        <el-form-item label="站点名称">
          <el-input v-model="friendform.link_name"></el-input>
        </el-form-item>
        <el-form-item label="站点网址">
          <el-input v-model="friendform.link_url"></el-input>
        </el-form-item>
        <el-form-item label="站点描述">
          <el-input v-model="friendform.link_info"></el-input>
        </el-form-item>
        <el-form-item label="站点图标">
          <el-input v-model="friendform.avatar"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveEdit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "FriendSider",
  data() {
    return {
      dialogFormVisible: false,
      friendform: {
        link_id: "",
        link_name: "",
        link_url: "",
        link_info: "",
        avatar: "",
        status: 0
      },
      formLabelWidth: "120px",
      friends: {}
    };
  },
  methods: {
    getFriends() {
      const _this = this;
      this.$axios.get("/allFriends").then(res => {
        // console.log(res.data.data);
        _this.friends = res.data.data;
      });
    },
    saveEdit() {
      //申请友链
      const _this = this;
      this.$axios
        .post("/saveFriend", _this.friendform)
        .then(res => {
          if (res.data.code === 200) {
            this.$message.success(res.data.msg);
          } else {
            this.$message.error("申请友链失败，请联系博主");
          }
        });
      _this.editVisible = false;
    }
  },
  created() {
    this.getFriends();
  }
};
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
  }
  .el-col {
    border-radius: 4px;
  }
.box-card:hover {
  transform: translate(0, -2px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}
.box-card .item {
  margin-bottom: 18px;
}
.box-card .item a {
  text-decoration: none;
  /*color: #F56C6C;*/
}
.box-card span {
  font-weight: bold;
}
.card-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}
.el-col{
  padding-right: 0px ! important ;
}
.friend .logo{
  float: left;
}
.friend .link{
  margin-top: 10px;
  margin-left: 20px;
}
.friend .link a{
  color: #606266;
}
.friend .link a:hover{
  color: #409EFF;
}
</style>