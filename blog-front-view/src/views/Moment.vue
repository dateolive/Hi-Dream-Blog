<template>
  <div id>
    <div class="momentList count" style="text-align: center">
      <h2>动态说说</h2>
      <p>
        目前共计
        <strong>{{ momentCnt }}</strong>篇公开动态！
      </p>
    </div>
    <el-row id="momentList" type="flex" justify="space-around">
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
      <el-col :span="screenWidth>750?16:24" id="diary">
        <div v-for="moment in momentList" :key="moment.id">
          <div class="diary-card">
            <img
           
              alt
              :src="introduction.avatar"
              class="user-icon"
            />
            <div class="arrow" ></div>
            <div class="diary-content">
              <div class="diary-head">
                <div class="diary-head-name">{{introduction.name}}</div>
                <div class="diary-head-time">{{moment.createTime}}</div>
              </div>
              <div id="post" class="moment-content typo m-padded-tb-small line-numbers match-braces rainbow-braces" v-html="moment.content">
              </div>
              <div class="diary-footer">
                <div class="diary-like" @click="LikeMoment(moment.id)">
                  <svg
                    aria-hidden="true"
                    focusable="false"
                    data-prefix="far"
                    data-icon="thumbs-up"
                    role="img"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 512 512"
                    class="svg-inline--fa fa-thumbs-up fa-w-16"
                  >
                    <path
                      fill="currentColor"
                      d="M466.27 286.69C475.04 271.84 480 256 480 236.85c0-44.015-37.218-85.58-85.82-85.58H357.7c4.92-12.81 8.85-28.13 8.85-46.54C366.55 31.936 328.86 0 271.28 0c-61.607 0-58.093 94.933-71.76 108.6-22.747 22.747-49.615 66.447-68.76 83.4H32c-17.673 0-32 14.327-32 32v240c0 17.673 14.327 32 32 32h64c14.893 0 27.408-10.174 30.978-23.95 44.509 1.001 75.06 39.94 177.802 39.94 7.22 0 15.22.01 22.22.01 77.117 0 111.986-39.423 112.94-95.33 13.319-18.425 20.299-43.122 17.34-66.99 9.854-18.452 13.664-40.343 8.99-62.99zm-61.75 53.83c12.56 21.13 1.26 49.41-13.94 57.57 7.7 48.78-17.608 65.9-53.12 65.9h-37.82c-71.639 0-118.029-37.82-171.64-37.82V240h10.92c28.36 0 67.98-70.89 94.54-97.46 28.36-28.36 18.91-75.63 37.82-94.54 47.27 0 47.27 32.98 47.27 56.73 0 39.17-28.36 56.72-28.36 94.54h103.99c21.11 0 37.73 18.91 37.82 37.82.09 18.9-12.82 37.81-22.27 37.81 13.489 14.555 16.371 45.236-5.21 65.62zM88 432c0 13.255-10.745 24-24 24s-24-10.745-24-24 10.745-24 24-24 24 10.745 24 24z"
                      class
                    />
                  </svg>
                  {{moment.likes}}人点赞
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="block pagination">
          <el-pagination
            @current-change="getMomentList"
            :current-page="currentPage"
            :page-count="total"
            layout="prev, pager, next"
            background
            hide-on-single-page
          ></el-pagination>
        </div>
      </el-col>
   
    </el-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "moment",
  data() {
    return {
      momentList: [],
      screenWidth: document.body.clientWidth,
      momentCnt: 0,
      currentPage: 1,
      total: 0
    };
  },
    computed: {
    ...mapState(["introduction"])
  },
  methods: {
    getMomentList(currentPage) {
      const _this = this;
      this.$axios
        .get("/momentList", {
          params: {
            currentPage: currentPage
          }
        })
        .then(res => {
         // console.log(res.data.data);
          _this.momentList = res.data.data;
          _this.currentPage = res.data.currentPage;
          _this.total = res.data.totalPage;
            _this.$nextTick(() => {
          Prism.highlightAll();
        });
        });
      this.scrollToTop();
    },
    getMomentCnt() {
      const _this = this;
      this.$axios.get("/momentCount").then(res => {
        //  console.log(res.data.data);
        _this.momentCnt = res.data.data;
      });
    },
    LikeMoment(id) {
      const _this = this;
      if (_this.getCookie("likeMoment" + id) == null) {
        this.$axios.post("/likeMoment/" + id).then(res => {
          _this.$message.success(res.data.msg);
          _this.setCookie("likeMoment" + id, id, 365);
        });
      } else {
        _this.$message.warning("你已经点赞了");
      }
      _this.getMomentCnt();
    },
    setCookie(key, value, expiredays) {
      var exdate = new Date();
      exdate.setDate(exdate.getDate() + expiredays);
      document.cookie =
        key +
        "=" +
        escape(value) +
        (expiredays == null ? "" : ";expires=" + exdate.toGMTString());
    },
    getCookie(key) {
      if (document.cookie.length) {
        var cookies = " " + document.cookie;
        var start = cookies.indexOf(" " + key + "=");
        if (start == -1) {
          return null;
        }
        var end = cookies.indexOf(";", start);
        if (end == -1) {
          end = cookies.length;
        }
        end -= start;
        var cookie = cookies.substr(start, end);
        return unescape(
          cookie.substr(
            cookie.indexOf("=") + 1,
            cookie.length - cookie.indexOf("=") + 1
          )
        );
      } else {
        return null;
      }
    }
  },
  mounted() {
    const that = this;
    that.getMomentList();
    that.getMomentCnt();
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth;
        that.screenWidth = window.screenWidth;
      })();
    };
  },
  watch: {
    screenWidth(val) {
      // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
      if (!this.timer) {
        // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
        this.screenWidth = val;
        this.timer = true;
        let that = this;
        setTimeout(function() {
          // 打印screenWidth变化的值
          // console.log(that.screenWidth)
          that.timer = false;
        }, 400);
      }
    }
  }
};
</script>

<style>
#diary {
  position: relative;
  border-left: 1px solid #00a1d6;
  margin: 0 25px 20px 15px;
}
#diary .diary-card {
  margin-bottom: 50px;
  position: relative;
}
#diary .user-icon {
  position: absolute;
  width: 40px;
  border-radius: 50%;
  left: -20px;
  transition: all 0.3s;
  cursor: pointer;
}
#diary .user-icon:hover {
  transform: scale(1.1);
}
#diary .arrow {
  position: absolute;
  top: 10px;
  left: 10px;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: #fff;
}
#diary .diary-content {
  background: #fff;
  border-radius: 5px;
  margin-left: 30px;
  box-shadow: 0 3px 8px 6px rgba(7, 17, 27, 0.06);
  
}
#diary .diary-head {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 7px;
  border-bottom: 1px solid #e1e8ed;
  height: 21px;
}
#diary .diary-head .diary-head-name {
  font-size: 14px;
  flex: 1;
}
#diary .diary-head .diary-head-time {
  color: #98a6ad;
  font-size: 12px;
}
#diary .diary-body {
  padding: 10px;
  position: relative;
}
#diary .diary-footer {
  color: #98a6ad;
  border-top: 1px solid #e1e8ed;
  flex-direction: row;
  width: 100%;
  display: flex;
  cursor: pointer;
}
#diary .diary-footer div {
  padding: 12px;
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
}
.moment-content {
    -webkit-box-flex: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    border: none;
    border-top: 1px solid rgba(34,36,38,.1);
    background: 0 0;
    margin: 0;
    padding: 1em 1em;
    -webkit-box-shadow: none;
    box-shadow: none;
    font-size: 1em;
    border-radius: 0;
    box-sizing: border-box;
}
.svg-inline--fa.fa-w-16 {
  width: 1em;
}
svg:not(:root).svg-inline--fa {
  overflow: visible;
}
.svg-inline--fa {
  display: inline-block;
  font-size: inherit;
  height: 1em;
  overflow: visible;
  vertical-align: -0.125em;
}
svg:not(:root) {
  overflow: hidden;
}
.pagination {
  background-color: #f9f9f9;
}
@media screen and (min-width: 320px) and (max-width: 750px) {
  .diary-head-name {
    font-size: 8px;
    flex: 1;
  }
}
</style>