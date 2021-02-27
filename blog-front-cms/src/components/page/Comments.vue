<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> 评论管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-input v-model="query.nick_name" placeholder="评论者名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">重置</el-button>
            </div>
            <el-table
                :data="comments"
                border
                :row-class-name="tableRowClassName"
                class="table"
                header-cell-class-name="table-header"
            >
                <el-table-column prop="comment_id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="nick_name" label="评论者"></el-table-column>
                <el-table-column label="评论内容" width="200">
                    <template slot-scope="scope">{{scope.row.content}}</template>
                </el-table-column>
                <el-table-column label="评论者头像" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.avatar"
                            :preview-src-list="[scope.row.avatar]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="reply_nick_name" label="回复人"></el-table-column>
                <el-table-column prop="blog_id" label="文章ID"></el-table-column>
                <el-table-column prop="pid" label="回复ID"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
                <el-table-column prop="site_url" label="站点"></el-table-column>
                <el-table-column prop="page" label="评论页面"></el-table-column>
                <el-table-column prop="create_time" label="创建时间"></el-table-column>
                <el-table-column property="is_check" align="center" label="审核">
                    <template slot-scope="scope">
                        <el-switch
                            :active-value="1"
                            :inactive-value="0"
                            v-model="scope.row.is_check"
                            @change="changeStatus(scope.$index,scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>

                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    @current-change="getData"
                    :current-page="currentPage"
                    :page-count="total"
                    layout="prev, pager, next"
                    background
                    hide-on-single-page
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'comments',
    inject: ['reload'],
    data() {
        return {
            query: {
                nick_name: ''
            },
            comments: [],
            currentPage: 1,
            pageSize: 5,
            total: 0
        };
    },
    mounted() {
        this.getData(1);
    },
    methods: {
        getData(currentPage) {
            const _this = this;
            this.$axios
                .get('/admin/comment', {
                    params: {
                        currentPage: currentPage
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.comments = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },
        // 触发搜索按钮
        handleSearch() {
            const _this = this;
            this.$axios
                .get('/admin/comment/' + _this.query.nick_name, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        this.$message.success(res.data.msg);
                        _this.comments = res.data.data;
                    } else {
                        this.$message.error(res.data.msg);
                    }
                });
        },
        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$axios
                        .delete('/admin/comment/delete', {
                            params: {
                                comment_id: row.comment_id
                            },
                            headers: {
                                Authorization: localStorage.getItem('token')
                            }
                        })
                        .then(res => {
                            if (res.data.code == 200) {
                                this.$message.success(res.data.msg);
                                this.reload();
                            } else {
                                this.$message.error(res.data.msg);
                            }
                        })
                        .catch(err => {
                            this.$message.error('不要再试了哦，没有权限');
                        });
                })
                .catch(() => {});
        },
        changeStatus(index, row) {
            const _this = this;
            const comment_id = row.comment_id;
            const is_check = row.is_check;
            const params = {
                comment_id,
                is_check
            };
            this.$axios
                .put('/admin/comment/isCheck', params, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        this.$message.success(res.data.msg);
                        this.reload();
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
                .catch(err => {
                    this.$message.error('不要再试了哦，没有权限');
                });
        },
        refreshData() {
            this.getData(1);
        },
        tableRowClassName({ row, rowIndex }) {
            //console.log(row.is_check)
            if (row.is_check === 0) {
                return 'warning-row';
            }
            return '';
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
/*上传图片*/
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
.el-table >>> .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background: #f0f9eb;
}
</style>
