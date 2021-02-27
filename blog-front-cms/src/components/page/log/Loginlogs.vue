<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-addressbook"></i> 登录日志
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-refresh" @click="refreshData">重置</el-button>
            </div>
            <el-table :data="logList" border class="table" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="operateor" label="操作人"></el-table-column>
                <el-table-column prop="ip" label="IP"></el-table-column>
                <el-table-column prop="ipSource" label="IP来源" show-overflow-tooltip></el-table-column>
                <el-table-column prop="operatetype" label="操作类型"></el-table-column>
                <el-table-column prop="description" label="方法描述"></el-table-column>
                <el-table-column prop="os" label="操作系统"></el-table-column>
                <el-table-column prop="browser" label="浏览器" show-overflow-tooltip></el-table-column>
                <el-table-column label="操作耗时" width="100">
                    <template v-slot="scope">
                        <el-tag size="small">{{ scope.row.spendTime }}ms</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="url" label="请求链接" show-overflow-tooltip></el-table-column>
                <el-table-column label="操作结果" width="80">
                    <template v-slot="scope">
                        <el-tag v-if="scope.row.operateresult=='正常'" size="small" effect="dark">成功</el-tag>
                        <el-tag v-else size="small" effect="dark" type="danger">失败</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="operatedate" label="操作时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
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
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="query.currentPage"
                    :page-sizes="[10, 20, 30, 50]"
                    :page-size="query.pageSize"
                    :page-count="total"
                    layout="total, sizes, prev, pager, next, jumper"
                    background
                ></el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'loginlogs',
    inject: ['reload'],
    data() {
        return {
            query: {
                classification: 2,
                currentPage: 1,
                pageSize: 10
            },
            logList: [],
            total: 0
        };
    },
    mounted() {
        this.getData();
    },
    methods: {
        getData() {
            const _this = this;
            this.$axios
                .post('/admin/logList/query', _this.query, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.logList = res.data.data;
                    _this.query.currentPage = res.data.currentPage;
                    _this.total = res.data.totalPage;
                })
                .catch(err => {
                    this.$message.error('不要再试了哦，没有权限');
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
                        .delete('/admin/deleteLog', {
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
        refreshData() {
            this.getData();
        },
        handleSizeChange(newSize) {
            this.query.pageSize = newSize;
            this.getData();
        },
        handleCurrentChange(newPage) {
            this.query.currentPage = newPage;
            this.getData();
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
