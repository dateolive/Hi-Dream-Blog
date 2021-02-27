<template>
  <div class="tags">
    <el-card class="box-card">
      <div slot="header" class="d-flex align-items-center">
        <i class="card-icon el-icon-s-grid"></i>
        <span>分类</span>
      </div>
      <div class="text item">
        <el-tag
          class="tag-item"
          effect="plain"
          v-for="category in categorys"
          :color="category.color"
          v-bind:key="category.category_id"
        >
          <keep-alive>
            <router-link
              :to="{name: 'list', params: {type:'category',id: category.category_id}}"
              tag="span"
            >{{category.category_name}}</router-link>
          </keep-alive>
        </el-tag>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "categorys",
  data() {
    return {
      categorys: {}
    };
  },
  methods: {
    getTag() {
      const _this = this;
      this.$axios.get("/getAllCategory").then(res => {
        // console.log(res.data.data)
        const data = res.data.data;
        data.forEach(item => {
          item["color"] =
            "#" +
            Math.random()
              .toString(16)
              .substr(2, 6)
              .toUpperCase();
        });
        _this.categorys = data;
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
  margin-right: 8px;
  margin-top: 5px;
}

.tag-item {
  margin-right: 10px;
  color: #fff;
  margin-top: 10px;
}
</style>