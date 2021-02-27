<template>
  <div class="archive">
    <div class="atrtitle count" style="text-align: center">
      <h2>文章归档</h2>
      <p>目前共计<strong> {{ blogcount }} </strong>篇公开文章！ 继续努力！</p>
    </div>
    <el-row id="artList" type="flex" justify="space-around">
      <el-col :span="16">
        <div class="block">
          <el-timeline>
            <el-timeline-item
              placement="top"
              v-for="archive in archives"
              v-bind:key="archive.blog_id"
              v-bind:timestamp="archive.create_time"
              size="large"
              icon="el-icon-more"
              type="primary"
              class="timeItem"
            >
                <router-link
                class="line-item"
                  :to="{name: 'article', params: {blogId: archive.blog_id}}"
                >{{archive.blog_title}}</router-link>
          
            </el-timeline-item>
          </el-timeline>
        </div>
        <el-pagination
          @current-change="getArchive"
          :current-page="currentPage"
          :page-count="total"
          layout="prev, pager, next"
          background
          hide-on-single-page
        ></el-pagination>
      </el-col>
       <el-col :span="6" class="hidden-sm-and-down" id="side">
        <div class="item">
          <Introduction />
        </div>
        <div class="item">
          <categorys />
        </div>
        <div class="item">
          <tags />
        </div>
        <div class="item">
          <FriendSider />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "archive",
  components: {},
  data() {
    return {
      archives: {},
      currentPage: 1,
      pageSize: 5,
      total: 0,
      blogcount: 0
    };
  },
  methods: {
    getArchive(currentPage) {
      const _this = this;
      this.$axios
        .get("/blogArchive", {
          params: {
            currentPage: currentPage
          }
        })
        .then(res => {
          // console.log(res.data.data)
          _this.archives = res.data.data;
          _this.currentPage = res.data.currentPage;
          _this.total = res.data.totalPage;
        });
    },
    getBlogCnt() {
      const _this = this;
      this.$axios.get("/blogsCount").then(res => {
        // console.log(res.data.data)
        _this.blogcount = res.data.data;
      });
    }
  },
  mounted() {
    this.getArchive(1);
    this.getBlogCnt();
  }
};
</script>

<style>
#side .item {
  margin-bottom: 30px;
}
.line-item {
  display: inline-block;
  color: #000;
  font-size: 15px;
  font-weight: 400;
  padding-bottom: 20px;
  padding-top: 20px;
  text-decoration: none;
}

.line-item:hover {
  cursor: pointer;
  color: #409eff;
  transform: translateX(10px);
  transition:all 1s;
  -webkit-line-clamp: 2;
}

.count {
  margin-bottom: 20px;
  font-size: 20px;
  color: #e6a23c;
}
@media screen and (min-width: 320px) and (max-width: 750px) {
  .atrtitle {
    margin-top: 50px;
  }
}

</style>