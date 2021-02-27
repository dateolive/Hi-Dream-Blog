<template>
  <div class="container">
    <h3 class="header">Comments | 快来评论吧~</h3>
    <div class="comment" v-for="item in comments" :key="item.comment_id">
      <div class="info">
        <img class="avatar" :src="item.avatar" width="36" height="36" />
        <div class="right">
          <a class="name">{{item.nick_name}}</a>
          <div class="date">{{item.create_time}}</div>
        </div>
      </div>
      <div class="content">{{item.content}}</div>
      <div class="control">
        <span class="comment-reply" @click="showCommentInput(item)">
          <i class="iconfont el-icon-chat-dot-round"></i>
          <span>回复</span>
        </span>
      </div>
      <div class="reply">
        <div class="item" v-for="reply in item.replyComments" :key="reply.comment_id">
          <div class="reply-content info">
            <img class="avatar" :src="reply.avatar" width="36" height="36" />
            <span class="from-name">{{reply.nick_name}}</span>
            <span>:</span>
            <span class="to-name">@{{reply.reply_nick_name}}</span>
            <span>{{reply.content}}</span>
          </div>
          <div class="reply-bottom">
            <span>{{reply.create_time}}</span>
            <span class="reply-text" @click="showCommentInput(item,reply)">
              <i class="iconfont el-icon-chat-dot-round"></i>
              <span>回复</span>
            </span>
          </div>
        </div>
        <div
          class="write-reply"
          v-if="item.replyComments.length > 0"
          @click="showCommentInput(item)"
        >
          <i class="el-icon-edit"></i>
          <span class="add-comment">添加新评论</span>
        </div>
        <transition name="fade">
          <div class="input-wrapper" v-if="showItemId === item.comment_id">
            <el-input
              class="gray-bg-input"
              v-model="commentForm.content"
              type="textarea"
              :rows="3"
              autofocus
              placeholder="写下你的评论"
            ></el-input>
            <div class="input-control">
              <el-input
                class="input"
                placeholder="你的名称"
                prefix-icon="el-icon-user"
                v-model="commentForm.nick_name"
              ></el-input>
              <el-input
                class="input"
                placeholder="邮箱"
                prefix-icon="el-icon-message"
                v-model="commentForm.email"
              ></el-input>
              <el-input
                class="input"
                placeholder="站点链接"
                prefix-icon="el-icon-link"
                v-model="commentForm.site_url"
              ></el-input>
            </div>
            <div class="btn-control">
              <el-button class="cancel" type="info" plain round @click="cancel">取消</el-button>
              <el-button class="btn" type="primary" round @click="commitComment">确定</el-button>
            </div>
          </div>
        </transition>
      </div>
    </div>
    <el-pagination
      @current-change="getComment"
      :current-page="currentPage"
      :page-count="total"
      layout="prev, pager, next"
      background
      hide-on-single-page
    ></el-pagination>
    <div class="fatherInput">
      <div class="input-wrapper" v-if="showFatherInput">
        <el-input
          class="gray-bg-input"
          v-model="commentForm.content"
          type="textarea"
          :rows="3"
          autofocus
          placeholder="写下你的评论"
        ></el-input>
        <div class="input-control">
          <el-input
            class="input"
            placeholder="你的名称"
            prefix-icon="el-icon-user"
            v-model="commentForm.nick_name"
          ></el-input>
          <el-input
            class="input"
            placeholder="输入邮箱获取回复及头像"
            prefix-icon="el-icon-message"
            type="email"
            v-model="commentForm.email"
          ></el-input>
          <el-input
            class="input"
            placeholder="站点链接"
            prefix-icon="el-icon-link"
            v-model="commentForm.site_url"
          ></el-input>
        </div>
        <div class="btn-control">
          <el-button class="btn" type="primary" round @click="commitComment">确定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "comment",
  props: {
    objectData: {
      type: Object
    },
    required: true
  },
  data() {
    return {
      comments: {},
      Postdata: {
        blog_id: 0,
        currentPage: 1,
        page: 0
      },
      total: 0,
      currentPage: 1,
      showFatherInput: true,
      showItemId: null,
      commentForm: {
        blog_id: 0,
        content: "",
        nick_name: "",
        email: "",
        site_url: "",
        avatar: "",
        reply_nick_name: "",
        pid: null,
        create_time: "",
        page: 0,
        is_check: 0
      },
      replyForm: {}
    };
  },
  watch: {
    objectData: function(newVal, oldVal) {
      // console.log(newVal)
      this.Postdata.blog_id = newVal.blog_id;
      this.Postdata.page = newVal.page;
      this.commentForm.page = newVal.page;
      //console.log(this.Postdata.blog_id)
      newVal && this.getComment(this.currentPage);
    }
  },
  methods: {
    getComment(currentPage) {
      const _this = this;
      _this.Postdata.currentPage = currentPage;
      //console.log(_this.Postdata.blog_id)
      this.$axios.post("/comment/query", _this.Postdata).then(res => {
        //   console.log(_this.Postdata.currentPage);
        if (res.data.code == 200) {
          _this.comments = res.data.data;
          _this.total = res.data.totalPage;
          _this.currentPage = res.data.currentPage;
          //   console.log(res.data)
          //this.$message.success(res.data.msg);
        } else {
          //this.$message.error(res.data.msg);
          // console.log(res.data);
        }
      });
    },
    showCommentInput(item, reply) {
      if (reply) {
        this.commentForm.content = "@" + reply.nick_name + " ";
        this.showFatherInput = false;
        this.replyForm = reply;
        this.replyForm.pid=reply.comment_id;
     //  console.log(reply);
      } else {
        this.commentForm.content = "";
        this.showFatherInput = false;
        this.replyForm = item;
        this.replyForm.pid=item.comment_id;
        //console.log(this.replyForm);
      }
      this.replyForm.comment_id = item.comment_id;
      this.showItemId = item.comment_id;
    },
    cancel() {
      this.showItemId = null;
      this.commentForm.inputComment = "";
      this.showFatherInput = true;
      this.replyForm.pid=null;
    },
    commitComment() {
      const _this = this;
      this.commentForm.blog_id = this.Postdata.blog_id;
      this.commentForm.avatar = "https://imapi.datealive.top/tp/pic.php";
      if (this.commentForm.blog_id != null) {
        if(_this.commentForm.content!=''&&_this.commentForm.email!=''&&_this.commentForm.nick_name!=''){
        if (this.showItemId == null) {
         // console.log("直接评论");
          _this.commentForm.pid = 0;
        } else {
          this.commentForm.pid = this.replyForm.comment_id;
          //this.commentForm.pid = this.replyForm.pid;//这里传入的是当前要回复评论的id，后期后端要改下这个bug
          //console.log("这个评论的id"+ this.commentForm.pid);
          this.commentForm.reply_nick_name = this.replyForm.nick_name;
        }
        //console.log(_this.commentForm)
        this.$axios.post("/comment/save", _this.commentForm).then(res => {
          //  console.log(res.data);
          if (res.data.code === 200) {
            _this.$message.success(res.data.msg);
          } else {
            this.$message.warning("请刷新页面再评论");
          }
        });
        }else{
          this.$message.warning("请检查是否填写完整");
        }

      } else {
        this.$message.warning("请刷新页面再评论");
      }
    }
  },
  mounted() {}
};
</script>

<style scoped>
.container {
  padding: 100px 10px;
  box-sizing: border-box;
}
.container .header {
  padding-bottom: 0.21428571rem;
  border-bottom: 1px solid rgba(34, 36, 38, 0.15);
}
.container .comment {
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-bottom: 1px solid #f2f6fc;
}
.container .comment .info {
  display: flex;
  align-items: center;
}
.container .comment .info .avatar {
  border-radius: 50%;
}
.container .comment .info .right {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}
.container .comment .info .right .name {
  font-size: 16px;
  color: #303133;
  margin-bottom: 5px;
  font-weight: 500;
}
.container .comment .info .right .date {
  font-size: 12px;
  color: #909399;
}
.container .comment .content {
  font-size: 16px;
  color: #303133;
  line-height: 20px;
  padding: 10px 0;
}
.container .comment .control {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #909399;
}
.container .comment .control .like {
  display: flex;
  align-items: center;
  margin-right: 20px;
  cursor: pointer;
}
.container .comment .control .like.active,
.container .comment .control .like:hover {
  color: #409eff;
}
.container .comment .control .like .iconfont {
  font-size: 14px;
  margin-right: 5px;
}
.container .comment .control .comment-reply {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.container .comment .control .comment-reply:hover {
  color: #333;
}
.container .comment .control .comment-reply .iconfont {
  font-size: 16px;
  margin-right: 5px;
}
.container .comment .reply {
  margin: 10px 10px;
  border-left: 2px solid #dcdfe6;
}
.container .comment .reply .item {
  margin: 0 10px;
  padding: 10px 0;
  border-bottom: 1px dashed #ebeef5;
}
.container .comment .reply .item .reply-content {
  display: flex;
  align-items: center;
  padding: 0px 10px;
  font-size: 14px;
  color: #303133;
}
.container .comment .reply .item .reply-content .from-name {
  color: #409eff;
  padding: 0 10px;
}
.container .comment .reply .item .reply-content .to-name {
  color: #409eff;
  margin-left: 5px;
  margin-right: 5px;
}
.container .comment .reply .item .reply-bottom {
  display: flex;
  align-items: center;
  margin-top: 6px;
  font-size: 12px;
  color: #909399;
}
.container .comment .reply .item .reply-bottom .reply-text {
  display: flex;
  align-items: center;
  margin-left: 10px;
  cursor: pointer;
}
.container .comment .reply .item .reply-bottom .reply-text:hover {
  color: #333;
}
.container .comment .reply .item .reply-bottom .reply-text .icon-comment {
  margin-right: 5px;
}
.container .comment .reply .write-reply {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #909399;
  padding: 10px;
  cursor: pointer;
}
.container .comment .reply .write-reply:hover {
  color: #303133;
}
.container .comment .reply .write-reply .el-icon-edit {
  margin-right: 5px;
}
.container .comment .reply .fade-enter-active,
.container .comment .reply fade-leave-active {
  transition: opacity 0.5s;
}
.container .comment .reply .fade-enter,
.container .comment .reply .fade-leave-to {
  opacity: 0;
}
.container .comment .reply .input-wrapper {
  padding: 10px;
}

.container .comment .reply .input-wrapper .btn-control {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-top: 10px;
}
.container .comment .reply .input-wrapper .input-control {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-top: 10px;
}
.container .comment .reply .input-wrapper .input-control .input {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-left: 5px;
}
.container .comment .reply .input-wrapper .btn-control .cancel {
  font-size: 16px;
  color: #606266;
  margin-right: 20px;
  cursor: pointer;
}
.container .comment .reply .input-wrapper .btn-control .cancel:hover {
  color: #333;
}
.container .comment .reply .input-wrapper .btn-control .confirm {
  font-size: 16px;
}
.fatherInput {
  margin-top: 50px;
}
.fatherInput .input-wrapper {
  padding: 10px;
}

.fatherInput .input-wrapper .btn-control {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-top: 10px;
}
.fatherInput .input-wrapper .input-control {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-top: 10px;
}
.fatherInput .input-wrapper .input-control .input {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  margin-left: 5px;
}
.fatherInput .input-wrapper .btn-control .cancel {
  font-size: 16px;
  color: #606266;
  margin-right: 20px;
  cursor: pointer;
}
.fatherInput .input-wrapper .btn-control .cancel:hover {
  color: #333;
}
.fatherInput .input-wrapper .btn-control .confirm {
  font-size: 16px;
}
</style>