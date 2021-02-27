<template>
  <div class="app">
    <div class="rg-table-box">
      <Head :class="{'navBarWrap':navBarFixed}" :siteInfo="siteInfo"></Head>
      <el-row type="flex" justify="center" id="content">
        <el-col :xs="20" :md="20" :style="{'minHeight':minHeight+'px'}">
          <router-view></router-view>
        </el-col>
      </el-row>
      <!--回到顶部-->
		<el-backtop style="box-shadow: none;background: none;">
			<img src="./assets/totop.png" style="width: 40px;height: 40px;">
		</el-backtop>
      <meting-js server="netease" type="playlist" :id="musicId"  fixed="true" theme="#25CCF7"></meting-js>
      <Footer :siteInfo="siteInfo" :badges="badges" :newBlogs="newBlogs"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {
      minHeight: 0,
      navBarFixed: false,
      badges: [],
      siteInfo: {
          blogName: '',
          backgroundImage:'',
          loginUrl:'',
          headAvatar:'',
          headInfo:''
        },
        musicId:60198,
        newBlogs:[],
        hitokoto:''
    };
  },
  components: {},
  methods: {
    watchScroll() {
      var scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      //  当滚动超过 50 时，实现吸顶效果
      if (scrollTop > 50) {
        this.navBarFixed = true;
      } else {
        this.navBarFixed = false;
      }
    },
    getSiteSetting(){
      const _this = this;
      this.$axios
        .get("/siteInfo")
        .then(res => {
         // console.log(res.data.data.siteInfo)
          this.siteInfo = res.data.data.siteInfo
          this.badges = res.data.data.badges
          this.musicId=res.data.data.introduction.musicId
          this.$store.dispatch('saveSiteInfo', this.siteInfo)
					this.$store.dispatch('saveIntroduction', res.data.data.introduction)
          document.title = this.$route.meta.title + this.siteInfo.webTitleSuffix
          //document.title = this.siteInfo.webTitleSuffix
            // console.log(this.siteInfo)
            //console.log(this.musicId)
        });

    },
    getnewBlog(){
      const _this = this;
      this.$axios
        .get("/blogList", {
          params: {
            currentPage: 1
          }
        })
        .then(res => {
          _this.newBlogs = res.data.data;
        });
    }
  },
  created(){
    this.getSiteSetting();
    this.getnewBlog();
  },
  mounted() {
    let that = this;
    that.minHeight = document.documentElement.clientHeight;
    window.addEventListener("scroll", that.watchScroll);
    window.onresize = function() {
      that.minHeight = document.documentElement.clientHeight;
    };
  },
  watch:{
      '$route':function(to,from){
          document.body.scrollTop = 0;
          document.documentElement.scrollTop = 0; 
      }
  }
};
</script>

<style scoped>
.app {
  font-family: "microsoft yahei";
  background-color: #f9f9f9;
}
#content {
  background-color: #f9f9f9;
  padding: 100px 0;

}
@media screen and (min-width: 320px) and (max-width: 750px) {
  #content {
  background-color: #f9f9f9;
  padding: 100px 0;
}
}

</style>

