<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> 友情链接
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-plus"
                    class="handle-plus mr10"
                    @click="handleAdd"
                >添加友链</el-button>
                <el-input v-model="query.link_name" placeholder="站点名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">重置</el-button>
            </div>
            <el-table :data="friendList" border class="table" header-cell-class-name="table-header">
                <el-table-column prop="link_id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="link_name" label="站点名称"></el-table-column>
                <el-table-column label="站点描述">
                    <template slot-scope="scope">{{scope.row.link_info}}</template>
                </el-table-column>
                <el-table-column label="站点图标" align="center">
                    <template slot-scope="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.avatar"
                            :preview-src-list="[scope.row.avatar]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="link_url" label="站点链接"></el-table-column>
                <el-table-column property="status" align="center" label="友链状态">
                    <template slot-scope="scope">
                        <el-switch
                            :active-value="1"
                            :inactive-value="0"
                            v-model="scope.row.status"
                            @change="changeStatus(scope.$index,scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
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

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="friendform" :model="friendform" label-width="70px">
                <el-form-item label="站点名称">
                    <el-input v-model="friendform.link_name"></el-input>
                </el-form-item>
                <el-form-item label="站点网址">
                    <el-input v-model="friendform.link_url"></el-input>
                </el-form-item>
                <el-form-item label="站点描述">
                    <el-input v-model="friendform.link_info"></el-input>
                </el-form-item>
                <el-form-item label="站点图标">
                    <el-input v-model="friendform.avatar"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: 'friendlist',
    inject: ['reload'],
    data() {
        return {
            query: {
                link_name: ''
            },
            editVisible: false,
            friendform: {
                link_id: '',
                link_name: '',
                link_url: '',
                link_info: '',
                avatar: '',
                status: ''
            },

            friendList: [],
            currentPage: 1,
            pageSize: 5,
            total: 0,
            fileList: '',
            isAdd: true
        };
    },
    mounted() {
        this.getData(1);
    },
    methods: {
        getData(currentPage) {
            const _this = this;
            this.$axios
                .get('/admin/friendsList', {
                    params: {
                        currentPage: currentPage
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.friendList = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },
        // 触发搜索按钮
        handleSearch() {
            const _this = this;
            this.$axios.get('/queryFriend/' + _this.query.link_name).then(res => {
                if (res.data.code == 200) {
                    this.$message.success(res.data.msg);
                    _this.friendList = res.data.data;
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
                        .delete('/admin/deleteFriend', {
                            params: {
                                link_id: row.link_id
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
        // 编辑操作
        handleEdit(index, row) {
            this.friendform = row;
            this.isAdd = false;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            const _this = this;
            //修改友链
            if (!_this.isAdd) {
                this.$axios
                    .put('/admin/updateFriend', _this.friendform, {
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
            } else {
                //添加友链
                this.$axios
                    .post('/admin/saveFriend', _this.friendform, {
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
            }
            _this.editVisible = false;
        },
        //添加操作
        handleAdd() {
            this.friendform = {
                link_name: '',
                link_url: '',
                link_info: '',
                avatar: '',
                status: ''
            };
            this.isAdd = true;
            this.editVisible = true;
        },
        changeStatus(index, row) {
            const _this = this;
            const link_id = row.link_id;
            const status = row.status;
            const params = {
                link_id,
                status
            };
            this.$axios
                .put('/admin/updateFriStatus', params, {
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
</style>
