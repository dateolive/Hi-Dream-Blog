<template>
  <div class="f-footer">
    <el-main class="main">
      <el-row :gutter="20" type="flex" justify="center">
        <el-col :span="6" class="hidden-md-and-down">
          <h4>{{ siteInfo.footerImgTitle }}</h4>
          <el-avatar shape="square" :size="100" fit="fill" :src="siteInfo.footerImgUrl"></el-avatar>
        </el-col>
        <el-col :span="10" class="center">
          <h4>最新博客文章</h4>
          <div class="bloglist" v-for="blog in newBlogs" :key="blog.blog_id">
            <router-link
              class="blogitem"
              :to="{name: 'article', params: {blogId: blog.blog_id}}"
            >{{blog.blog_title}}</router-link>
          </div>
        </el-col>
        <el-col :span="8" class="hidden-md-and-down">
          <h4>搜索博客文章</h4>
          <el-autocomplete
            v-model="queryString"
            :fetch-suggestions="debounceQuery"
            placeholder="Search..."
            class="right item m-search"
            popper-class="m-search-item"
            @select="handleSelect"
          >
            <template slot-scope="{ item }">
              <div class="title">{{ item.blog_title }}</div>
              <span class="content">{{ item.blog_summary }}</span>
            </template>
          </el-autocomplete>
        </el-col>
      </el-row>
      <div class="divider"></div>
      <el-row type="flex" class="row-bg" justify="center">
        <el-col :span="24">
          <p class="copyright">
            <span
              style="margin-right: 10px"
              v-if="siteInfo.copyright"
            >{{ siteInfo.copyright.title }}</span>
            <router-link
              to="/"
              style="color:#ffe500"
              v-if="siteInfo.copyright"
            >{{ siteInfo.copyright.siteName }}</router-link>
            <span style="margin: 0 15px" v-if="siteInfo.copyright && siteInfo.beian">|</span>
            <img src="../assets/beian.png" alt class="beian" v-if="siteInfo.beian" />
            <a
              rel="external nofollow noopener"
              href="http://www.beian.miit.gov.cn/"
              target="_blank"
              style="color:#ffe500"
            >{{ siteInfo.beian }}</a>
          </p>
          <div class="github-badge" v-for="(item,index) in badges" :key="index">
            <a
              rel="external nofollow noopener"
              :href="item.url"
              target="_blank"
              :title="item.title"
            >
              <span class="badge-subject">{{ item.subject }}</span>
              <span class="badge-value" :class="`bg-${item.color}`">{{ item.value }}</span>
            </a>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script>
export default {
  name: "Footer",
  props: {
    siteInfo: {
      type: Object,
      required: true
    },
    badges: {
      type: Array,
      required: true
    },
    newBlogs: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      queryString: "",
      queryResult: [],
      timer: null
    };
  },
  methods: {
    debounceQuery(queryString, callback) {
      this.timer && clearTimeout(this.timer);
      this.timer = setTimeout(
        () => this.querySearchAsync(queryString, callback),
        1000
      );
    },
    querySearchAsync(queryString, callback) {
      if (
        queryString == null ||
        queryString.trim() === "" ||
        queryString.indexOf("%") !== -1 ||
        queryString.indexOf("_") !== -1 ||
        queryString.indexOf("[") !== -1 ||
        queryString.indexOf("#") !== -1 ||
        queryString.indexOf("*") !== -1 ||
        queryString.trim().length > 20
      ) {
        return;
      }
      this.$axios
        .get("/searchBlog/" + queryString)
        .then(res => {
          if (res.data.code === 200) {
            console.log(res.data)
            this.queryResult = res.data.data;
            if (this.queryResult.length === 0) {
              this.queryResult.push({ title: "无相关搜索结果" });
            }
            callback(this.queryResult);
          }
        })
        .catch(() => {
          this.msgError("请求失败");
        });
    },
    handleSelect(item) {
      if (item.blog_id) {
        this.$router.push(`/article/${item.blog_id}`);
      }
    }
  }
};
</script>

<style scoped>
.f-footer {
  padding: 20px;
  text-align: center;
  background: #1b1c1d;
  color: rgba(255, 255, 255, 0.9);
}
.main {
  width: 70%;
  margin: 0 auto;
  color: #ccc;
}
.el-row {
  margin-bottom: 20px;
}
.el-row :last-child {
  margin-bottom: 0;
}
.el-col:first-child {
  box-shadow: none;
}
.el-col {
  box-shadow: -1px 0 0 0 rgb(255 255 255 / 10%);
  border-radius: 4px;
}
.bloglist {
  list-style-type: none;
  margin: 1em 0;
  padding: 0 0;
  font-size: 1em;
}
.blogitem {
  display: block;
  cursor: pointer;
  color: rgba(255, 255, 255, 0.5);
}
.blogitem:hover {
  color: #fff;
}
.divider {
  border-top: 1px solid rgba(34, 36, 38, 0.15);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  margin-top: 2rem;
  margin-bottom: 2rem;
}
.beian {
  width: 17px;
  height: 17px;
  margin-bottom: -4px;
  margin-right: 5px;
}
.copyright {
  opacity: 0.6 !important;
  letter-spacing: 1px !important;
  font-weight: 300 !important;
}

.m-search {
  min-width: 220px;
  padding: 0 !important;
}
.m-search input {
  color: rgba(255, 255, 255, 0.9);
  border: 0px !important;
  background-color: inherit;
  padding: 0.67857143em 2.1em 0.67857143em 1em;
}
.m-search i {
  color: rgba(255, 255, 255, 0.9) !important;
}
.m-search-item {
  min-width: 350px !important;
}
.m-search-item li {
  line-height: normal !important;
  padding: 8px 10px !important;
}
.m-search-item li .title {
  text-overflow: ellipsis;
  overflow: hidden;
  color: rgba(0, 0, 0, 0.87);
}
.m-search-item li .content {
  text-overflow: ellipsis;
  font-size: 12px;
  color: rgba(0, 0, 0, 0.7);
}
.github-badge {
  display: inline-block;
  border-radius: 4px;
  text-shadow: none;
  font-size: 12px;
  color: #fff;
  line-height: 15px;
  background-color: #000;
  margin: 5px 8px;
}

.github-badge .badge-subject {
  display: inline-block;
  background-color: #505050;
  padding: 4px 6px 4px 6px;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}

.github-badge .badge-value {
  display: inline-block;
  padding: 4px 6px 4px 6px;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
}

.github-badge .bg-brightgreen {
  background-color: #4dc820 !important;
}

.github-badge .bg-orange {
  background-color: orange !important;
}

.github-badge .bg-yellow {
  background-color: #d8b024 !important;
}

.github-badge .bg-blueviolet {
  background-color: #8833d7 !important;
}

.github-badge .bg-pink {
  background-color: #f26bae !important;
}

.github-badge .bg-red {
  background-color: #e05d44 !important;
}

.github-badge .bg-blue {
  background-color: #007ec6 !important;
}

.github-badge .bg-semantic-ui {
  background-color: #35bdb2 !important;
}

.github-badge .bg-github {
  background-color: #747d8c !important;
}

.github-badge .bg-lightgray {
  background-color: #9f9f9f !important;
}

.github-badge .bg-gray {
  background-color: #555555 !important;
}

.github-badge .bg-lightgray {
  background-color: #9f9f9f !important;
}
.github-badge a {
  color: #fff;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
@media (max-width: 800px) {
  .el-col {
    box-shadow: none;
    border-radius: 4px;
  }
}
</style>