<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img :src="user.avatar" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{user.username}}</div>
                            <div>{{user.roles}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">{{user.introduce}}</div>
                </el-card>
                <el-card shadow="hover" style="height:252px;">
                    <div slot="header" class="clearfix">
                        <span>语言详情</span>
                    </div>Vue
                    <el-progress :percentage="71.3" color="#42b983"></el-progress>JavaScript
                    <el-progress :percentage="24.1" color="#f1e05a"></el-progress>CSS
                    <el-progress :percentage="13.7"></el-progress>HTML
                    <el-progress :percentage="5.9" color="#f56c6c"></el-progress>
                </el-card>
            </el-col>
            <el-col :span="16">
                <el-row :gutter="20" class="mgb20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-lx-edit grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{blogCount}}</div>
                                    <div>文章发布数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-2">
                                <i class="el-icon-lx-comment grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{commentCnt}}</div>
                                    <div>评论总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-lx-like grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">{{momentCnt}}</div>
                                    <div>动态总数</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-card shadow="hover" style="height:403px;">
                    <div slot="header" class="clearfix">
                        <span>最新文章</span>
                        <el-button
                            style="float: right; padding: 3px 0"
                            type="text"
                            icon="el-icon-s-data"
                        ></el-button>
                    </div>
                    <el-table :show-header="false" :data="blogArticles" style="width:100%;">
                        <el-table-column>
                            <template slot-scope="scope">
                                <div
                                    class="todo-item"
                                    :class="{'todo-item-del': scope.row.blog_status}"
                                >{{scope.row.blog_title}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column width="60">
                            <template slot-scope="scope">
                                <button
                                    class="el-icon-edit"
                                    @click="gotoEdit(scope.$index,scope.row.blog_id)"
                                ></button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
export default {
    name: 'sysindex',
    data() {
        return {
            user: {
                userid: '',
                username: '',
                avatar: '',
                roles: '',
                introduce: ''
            },
            blogCount: 0,
            commentCnt: 0,
            momentCnt: 0,
            blogArticles: []
        };
    },
    components: {},
    computed: {
        role() {
            return this.user.roles === 'admin' ? '超级管理员' : '普通用户';
        }
    },
    created() {
        if (this.$store.getters.getUser.username) {
            this.user.userid = this.$store.getters.getUser.userid;
            this.user.username = this.$store.getters.getUser.username;
            this.user.avatar = this.$store.getters.getUser.avatar;
            this.user.roles = this.$store.getters.getUser.roles;
            this.user.introduce = this.$store.getters.getUser.introduce;
            this.getBlogCnt();
            this.getMomentCnt();
            this.getNewBlog();
            this.getCommentCnt();
        }
    },
    // activated() {
    //     this.handleListener();
    // },
    // deactivated() {
    //     window.removeEventListener('resize', this.renderChart);
    //     bus.$off('collapse', this.handleBus);
    // },
    methods: {
        getBlogCnt() {
            const _this = this;
            this.$axios.get('/blogsCount').then(res => {
                if (res.data.code == 200) {
                    //this.$message.success(res.data.msg);

                    _this.blogCount = res.data.data;
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },
        getCommentCnt() {
            const _this = this;
            this.$axios
                .get('/admin/comment/allCnt', {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        //this.$message.success(res.data.msg);
                        // console.log(res.data)
                        _this.commentCnt = res.data.data;
                    } else {
                        this.$message.error(res.data.msg);
                    }
                });
        },
        getMomentCnt() {
            const _this = this;
            this.$axios.get('/momentCount').then(res => {
                if (res.data.code == 200) {
                    //this.$message.success(res.data.msg);

                    _this.momentCnt = res.data.data;
                } else {
                    this.$message.error(res.data.msg);
                }
            });
        },
        getNewBlog() {
            const _this = this;
            this.$axios
                .get('/admin/blogList', {
                    params: {
                        currentPage: 1
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.blogArticles = res.data.data;
                });
        },
        gotoEdit(index, blog_id) {
            this.$router.push({
                path: '/BlogEdit',
                query: {
                    blog_id: blog_id
                }
            });
        }
    }
};
</script>


<style scoped>
.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 20px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>
