<template>
  <div>
    <div class="headBack">
      <el-menu
        :default-active="activeIndex"
        class="d-flex nav-menu"
        mode="horizontal"
        menu-trigger="click"
        :router="true"
        type="flex"
      >
        <el-menu-item index="/home" class="mr-auto hidden-sm-and-down" style="color:#409EFF;font-size:25px;">{{siteInfo.blogName}}</el-menu-item>
        <el-menu-item index="/home">首页</el-menu-item>
        <el-submenu index class="hidden-sm-and-down">
          <template slot="title">分类</template>
          <el-menu-item
            v-for="category in categorys"
            :key="category.category_id"
          >
            <router-link
              :to="{name: 'list', params: {type:'category',id: category.category_id}}"
              tag="span"
            >{{category.category_name}}</router-link>
          </el-menu-item>
        </el-submenu>
        <el-menu-item index="/archive">归档</el-menu-item>
          <el-menu-item index="/moment">动态</el-menu-item>
        <el-menu-item index="/friends">友情链接</el-menu-item>
        <el-menu-item index="/about">关于我</el-menu-item>
      </el-menu>
    </div>
    <div id="banner" class="home-banner">
      <div
        class="banner-img"
        v-bind:style="{backgroundImage:'url(' +siteInfo.backgroundImage + ')'}"
      >
        <template>
          <!--博主信息-->
          <div class="focusinfo">
            <!-- 头像 -->
            <div class="header-tou">
              <a target="_blank" :href="siteInfo.loginUrl">
                <img :src="siteInfo.headAvatar" />
              </a>
            </div>
            <!-- 简介 -->
            <div class="header-info">
              <p>{{siteInfo.headInfo}}</p>
            </div>
    
          </div>
          <!--左右倾斜-->
          <div class="slant-left"></div>
          <div class="slant-right"></div>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Head",
		props: {
			siteInfo: {
				type: Object
			},
			
		},
  data() {
    return {
      activeIndex: "/home",
      categorys: [],
      restaurants: [],
      state: "",
      timeout: null
    };
  },
  methods: {
    getCategoryIdAndTitle() {
      const _this = this;
      this.$axios
        .get("/getAllCategory")
        .then(res => {
          //  console.log(res.data.data);
          _this.categorys = res.data.data;
          //console.log(res.data.data);
        });
    }
  },
  mounted() {
    this.getCategoryIdAndTitle();
  }
};
</script>

<style scoped>
.headBack {
  width: 100%;
  background: rgba(40, 42, 44, 0.6);
  /*margin-bottom:30px;*/
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  z-index: 100;
}

#banner {
  position: relative;
  width: 100%;
  height: 500px;
}
#banner .banner-img {
  width: inherit;
  height: inherit;
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
  transition: all 0.2s linear;
  overflow: hidden;
}
#banner .banner-img:hover {
  transform: scale(1.1, 1.1);
  filter: contrast(130%);
}
#banner.home-banner {
  height: 550px;
}
#banner.home-banner .banner-img {
  background-position: center center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
  z-index: -1;
  transition: unset;
}
#banner.home-banner .banner-img:hover {
  transform: unset;
  filter: unset;
}
#banner.home-banner .slant-left {
  content: "";
  position: absolute;
  width: 0;
  height: 0;
  border-bottom: 100px solid #f9f9f9;
  border-right: 800px solid transparent;
  left: 0;
  bottom: 0;
}
#banner.home-banner .slant-right {
  content: "";
  position: absolute;
  width: 0;
  height: 0;
  border-bottom: 100px solid #f9f9f9;
  border-left: 800px solid transparent;
  right: 0;
  bottom: 0;
}
.focusinfo {
  position: relative;
  max-width: 800px;
  padding: 0 10px;
  top: 55%;
  left: 50%;
  transform: translate(-50%, -50%);
  -webkit-transform: translate(-50%, -50%);
  text-align: center;
}
.focusinfo img {
  width: 80px;
  height: auto;
  border-radius: 50%;
  border: 3px solid rgba(255, 255, 255, 0.3);
}
.focusinfo .header-info {
  width: 60%;
  font-size: 14px;
  color: #eaeadf;
  background: rgba(0, 0, 0, 0.4);
  padding: 20px 30px;
  margin: 30px auto 0 auto;
  font-family: miranafont, "Hiragino Sans GB", STXihei, "Microsoft YaHei",
    SimSun, sans-serif;
  letter-spacing: 1px;
  line-height: 30px;
}
.focusinfo .top-social {
  height: 32px;
  margin-top: 30px;
  margin-left: 10px;
  list-style: none;
  display: inline-block;
  font-family: miranafont, "Hiragino Sans GB", STXihei, "Microsoft YaHei",
    SimSun, sans-serif;
}
.focusinfo .top-social div {
  float: left;
  margin-right: 10px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  width: 32px;
  
}
.focusinfo .top-social div img{
  width: 32px;
  height: 32px;
}
.header-tou:hover {
  transition: all 2s;
  transform: rotate(360deg);
}
@media (max-width: 960px) {
  #banner {
    height: 400px;
  }
}
@media (max-width: 800px) {
  #banner {
    display: none;
  }
}
</style>