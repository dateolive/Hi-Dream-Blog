<template>
  <div class="tags">
    <el-card class="box-card">
      <div slot="header" class="d-flex align-items-center">
        <img class="card-icon" src="../assets/tag.png" />
        <span>标签</span>
      </div>
      <div class="text item">
        <el-tag class="tag-item" v-for="tag in tags" v-bind:key="tag.tag_id">
          <keep-alive>
            <router-link
              :to="{name: 'list', params: {type:'tag',id: tag.tag_id}}"
              tag="span"
            >{{tag.tag_name}}</router-link>
          </keep-alive>
        </el-tag>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "tags",
  data() {
    return {
      tags: {}
    };
  },
  methods: {
    getTag() {
      const _this = this;
      this.$axios.get("/getAllTag").then(res => {
        // console.log(res.data.data)
        _this.tags = res.data.data;
      });
    }
  },
  created() {
    this.getTag();
  }
};
</script>

<style scoped>
.box-card:hover {
  transform: translate(0, -2px);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}
.box-card .item:hover {
  color: #409eff;
  cursor: pointer;
}

.box-card span {
  font-weight: bold;
}

.card-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.tag-item {
  margin-right: 10px;
  margin-top: 10px;
}
</style>