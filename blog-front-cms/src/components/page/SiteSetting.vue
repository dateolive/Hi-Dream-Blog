<template>
    <div>
        <el-row :gutter="20">
              <el-col :span="12">
                <el-card>
                    <div slot="header">
                        <span>资料卡</span>
                    </div>
                    <el-form label-position="right" label-width="100px">
                        <el-form-item
                            :label="item.name_zh"
                            v-for="item in typeMap.type3"
                            :key="item.id"
                        >
                            <div v-if="item.name_en=='favorite'">
                                <el-col :span="20">
                                    <el-input v-model="item.value"></el-input>
                                </el-col>
                                <el-col :span="4">
                                    <el-button
                                        type="danger"
                                        icon="el-icon-delete"
                                        @click="deleteFavorite(item)"
                                    >删除</el-button>
                                </el-col>
                            </div>
                            <div v-else>
                                <el-input v-model="item.value"></el-input>
                            </div>
                        </el-form-item>
                        <el-button
                            type="primary"
                            size="mini"
                            icon="el-icon-plus"
                            @click="addFavorite"
                        >添加自定义</el-button>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card>
                    <div slot="header">
                        <span>基础设置</span>
                    </div>

                    <el-form label-position="right" label-width="100px">
                        <el-form-item
                            :label="item.name_zh"
                            v-for="item in typeMap.type1"
                            :key="item.id"
                        >
                            <el-input v-model="item.value"></el-input>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
          
        </el-row>

        <el-row style="margin-top: 20px">
            <el-card>
                <div slot="header">
                    <span>页脚徽标</span>
                </div>
                <el-form :inline="true" v-for="badge in typeMap.type2" :key="badge.id">
                    <el-form-item label="title">
                        <el-input v-model="badge.value.title"></el-input>
                    </el-form-item>
                    <el-form-item label="url">
                        <el-input v-model="badge.value.url"></el-input>
                    </el-form-item>
                    <el-form-item label="subject">
                        <el-input v-model="badge.value.subject"></el-input>
                    </el-form-item>
                    <el-form-item label="value">
                        <el-input v-model="badge.value.value"></el-input>
                    </el-form-item>
                    <el-form-item label="color">
                        <el-input v-model="badge.value.color"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button
                            type="danger"
                            icon="el-icon-delete"
                            @click="deleteBadge(badge)"
                        >删除</el-button>
                    </el-form-item>
                </el-form>
                <el-button type="primary" size="mini" icon="el-icon-plus" @click="addBadge">添加 badge</el-button>
            </el-card>
        </el-row>

        <div style="text-align: right;margin-top: 30px">
            <el-button type="primary" icon="el-icon-check" @click="submit">保存</el-button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'SiteSetting',
    data() {
        return {
            deleteIds: [],
            typeMap: {}
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            this.$axios
                .get('/admin/siteSettings', {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    // console.log(res)
                    if (res.data.code === 200) {
                        this.typeMap = res.data.data;
                        res.data.data.type2.forEach(item => {
                            item.value = JSON.parse(item.value);
                        });
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
                .catch(err => {
                    this.$message.error('请求失败');
                });
        },
        addFavorite() {
            this.typeMap.type3.push({
                key: Date.now(),
                name_en: 'favorite',
                name_zh: '自定义',
                type: 3,
                value: '{"title":"","content":""}'
            });
        },
        addBadge() {
            this.typeMap.type2.push({
                key: Date.now(),
                name_en: 'badge',
                name_zh: '徽标',
                type: 2,
                value: {
                    color: '',
                    subject: '',
                    title: '',
                    url: '',
                    value: ''
                }
            });
        },
        deleteFavorite(favorite) {
            let arr = this.typeMap.type3;
            if (favorite.id) {
                this.deleteIds.push(favorite.id);
                arr.forEach((item, index) => {
                    if (item.id === favorite.id) {
                        arr.splice(index, 1);
                        return;
                    }
                });
            } else {
                arr.forEach((item, index) => {
                    if (item.key === favorite.key) {
                        arr.splice(index, 1);
                        return;
                    }
                });
            }
        },
        deleteBadge(badge) {
            let arr = this.typeMap.type2;
            if (badge.id) {
                this.deleteIds.push(badge.id);
                arr.forEach((item, index) => {
                    if (item.id === badge.id) {
                        arr.splice(index, 1);
                        return;
                    }
                });
            } else {
                arr.forEach((item, index) => {
                    if (item.key === badge.key) {
                        arr.splice(index, 1);
                        return;
                    }
                });
            }
        },
        submit() {
            const result = this.typeMap;
            result.type2.forEach(item => {
                item.value = JSON.stringify(item.value);
            });
            let updateArr = [];
            updateArr.push(...result.type1);
            updateArr.push(...result.type2);
            updateArr.push(...result.type3);
            const deleteIDS = this.deleteIds;
            const data = {
                settings:updateArr,
                deleteIds:deleteIDS
            };
            this.$axios
                .post('/admin/saveSiteSettings', data, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                  //  console.log(res)
                    if (res.data.code === 200) {
                        this.deleteIds = [];
                        this.getData();
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
                .catch(err => {
                    this.$message.error('没权限哦，请不要再试了');
                });
        }
    }
};
</script>

<style scoped>
</style>