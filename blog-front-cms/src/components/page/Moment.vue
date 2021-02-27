<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> 动态目录
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
                >添加动态</el-button>
                <el-input v-model="query.id" placeholder="动态ID" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">重置</el-button>
            </div>
            <el-table :data="momentList" border class="table" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column
                    prop="content"
                    min-width="280"
                    :show-overflow-tooltip="true"
                    label="动态"
                ></el-table-column>
                <el-table-column prop="likes" label="点赞人数"></el-table-column>
                <el-table-column property="published" align="center" label="是否发布">
                    <template slot-scope="scope">
                        <el-switch
                            :active-value="1"
                            :inactive-value="0"
                            v-model="scope.row.published"
                            @change="changeStatus(scope.$index,scope.row)"
                        ></el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column>
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="60%" :close-on-click-modal="false">
            <mavon-editor
                v-model="momentform.content"
                ref="md"
                @change="change"
                style="min-height: 600px"
                :ishljs="true"
                codeStyle="atom-one-dark"
            />
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';
export default {
    name: 'moment',
    inject: ['reload'],
    data() {
        return {
            query: {
                id: ''
            },
            editVisible: false,
            momentform: {
                id: null,
                content: '',
                published: 0
            },
            momentList: [],
            currentPage: 1,
            total: 0,
            fileList: '',
            isAdd: true
        };
    },
    components: {
        mavonEditor
    },
    mounted() {
        this.getData(1);
    },
    methods: {
        getData(currentPage) {
            const _this = this;
            this.$axios
                .get('/admin/AllmomentList', {
                    params: {
                        currentPage: currentPage
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.momentList = res.data.data;
                    _this.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                });
        },
        // 触发搜索按钮
        handleSearch() {
            const _this = this;
            _this.momentList = [];
            this.$axios
                .get('/admin/queryMomentById/' + _this.query.id, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        _this.momentList.push(res.data.data);
                        this.$message.success(res.data.msg);
                        //console.log(res.data.data)
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
                        .delete('/admin/moment/delete', {
                            params: {
                                id: row.id
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
            this.momentform = row;
            this.isAdd = false;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            const _this = this;
            //修改动态
            if (!_this.isAdd) {
                this.$axios
                    .put('/admin/updateMoment', _this.momentform, {
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
                this.momentform.published = 1;
                //添加动态
                this.$axios
                    .post('/admin/saveMoment', _this.momentform, {
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
            this.momentform = {
                id: ''
            };
            this.isAdd = true;
            this.editVisible = true;
        },
        refreshData() {
            this.getData(1);
        },
        change(value, render) {
            // render 为 markdown 解析后的结果
            //  this.blogDetail.blog_content=render;
        },
        changeStatus(index, row) {
            const _this = this;
            const id = row.id;
            const isPublish = row.published;
            const params = {
                id,
                isPublish
            };
            this.$axios
                .put('/admin/moment/isPublish', params, {
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
