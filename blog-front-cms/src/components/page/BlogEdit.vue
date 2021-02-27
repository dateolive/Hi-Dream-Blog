<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 博客
                </el-breadcrumb-item>
                <el-breadcrumb-item>编写文章</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-form ref="form" :model="blogDetail" label-width="80px">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="文章标题">
                            <el-input v-model="blogDetail.blog_title" style="width:400px"></el-input>
                        </el-form-item>
                        <el-form-item label="文章配图">
                            <el-upload
                                class="upload-demo"
                                ref="upload"
                                action="string"
                                accept="image/jpeg, image/png, image/jpg"
                                list-type="picture-card"
                                :before-upload="onBeforeUploadImage"
                                :http-request="UploadImage"
                                :on-change="fileChange"
                                :file-list="fileList"
                                :limit="1"
                            >
                                <el-button size="small" type="primary">点击上传</el-button>
                                <div slot="tip" class="el-upload__tip">只能上传jpg/jpeg/png文件，且不超过500kb</div>
                            </el-upload>
                        </el-form-item>
                        <el-form-item label="内容摘要">
                            <el-input
                                v-model="blogDetail.blog_summary"
                                type="textarea"
                                :rows="2"
                                style="width:400px"
                            ></el-input>
                        </el-form-item>
                        <el-form-item label="发布时间" style="width:400px">
                            <el-col :span="11">
                                <el-date-picker
                                    type="datetime"
                                    value-format="yyyy-MM-dd HH:mm:ss"
                                    placeholder="选择日期"
                                    v-model="blogDetail.create_time"
                                    style="width: 100%;"
                                ></el-date-picker>
                            </el-col>
                        </el-form-item>
                        <el-form-item label="是否置顶">
                            <el-switch
                                v-model="blogDetail.is_top"
                                :active-value="1"
                                :inactive-value="0"
                                active-color="#13ce66"
                            ></el-switch>
                        </el-form-item>
                        <el-form-item label="文章标签">
                            <el-tag
                                :key="tag.tag_id"
                                v-for="tag in tagsList"
                                closable
                                :disable-transitions="false"
                                @close="handleCloseTag(tag)"
                            >{{tag.tag_name}}</el-tag>
                            <el-input
                                class="input-new-tag"
                                v-if="inputVisibleTag"
                                v-model="inputValueTag"
                                ref="saveTagInputTag"
                                size="small"
                                @keyup.enter.native="handleInputConfirmTag"
                                @blur="handleInputConfirmTag"
                            ></el-input>
                            <el-button
                                v-else
                                class="button-new-tag"
                                size="small"
                                @click="showInputTag"
                            >+ New Tag</el-button>
                        </el-form-item>
                        <el-form-item label="文章目录">
                            <el-tag
                                :key="category.category_id"
                                v-for="category in categorys"
                                closable
                                :disable-transitions="false"
                                @close="handleCloseCategory(category)"
                            >{{category.category_name}}</el-tag>
                            <el-input
                                class="input-new-tag"
                                v-if="inputVisibleCategory"
                                v-model="inputValueCategory"
                                ref="saveInputCategory"
                                size="small"
                                @keyup.enter.native="handleInputConfirmCategory"
                                @blur="handleInputConfirmCategory"
                            ></el-input>
                            <el-button
                                v-else
                                class="button-new-tag"
                                size="small"
                                @click="showInputCategory"
                            >+ New Category</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
                <mavon-editor
                    v-model="content"
                    ref="md"
                    @imgAdd="$imgAdd"
                    @change="change"
                    style="min-height: 600px"
                    :ishljs="true"
                    codeStyle="atom-one-dark"
                />
                <el-form-item size="large">
                    <el-button class="editor-btn" type="primary" @click="release">发布文章</el-button>
                    <el-button class="editor-btn" type="primary" @click="saveblog">保存草稿</el-button>
                    <el-button class="editor-btn" type="info" @click="$router.back(-1)">取消</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';
var token = localStorage.getItem('token'); // 要保证取到
export default {
    name: 'markdown',
    data: function() {
        return {
            configs: {},
            content: '',
            blogDetail: {
                blog_id: null,
                blog_title: '',
                blog_cover_image: '',
                blog_content: '',
                blog_summary: '',
                blog_status: 0,
                create_time: '',
                update_time: '',
                is_top: 0,
                cateGory: [],
                tags: []
            },
            inputVisibleTag: false,
            inputValueTag: '',
            inputVisibleCategory: false,
            inputValueCategory: '',
            categorys: [],
            tagsList: [],
            fileList: [],
            logo:''
        };
    },
    components: {
        mavonEditor
    },
    created: function() {
        if (this.$route.query.blog_id) {
            this.getData();
        }
    },
    methods: {
        // 将图片上传到服务器，返回地址替换到md中
        $imgAdd(pos, $file) {},
        change(value, render) {
            // render 为 markdown 解析后的结果
            //  this.blogDetail.blog_content=render;
        },
        release() {
            const _this = this;
            _this.blogDetail.tags = _this.tagsList;
            _this.blogDetail.blog_content = _this.content;
            _this.blogDetail.cateGory = _this.categorys;
            _this.blogDetail.blog_status = 0;
            this.$axios
                .post('/admin/saveBlog', _this.blogDetail, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
                .catch(err => {
                    this.$message.error('不要再试了哦，没有权限');
                });
        },
        saveblog() {
            const _this = this;
            _this.blogDetail.tags = _this.tagsList;
            _this.blogDetail.blog_content = _this.content;
            _this.blogDetail.cateGory = _this.categorys;
            _this.blogDetail.blog_status = 1;
            this.$axios
                .post('/admin/saveBlog', _this.blogDetail, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code == 200) {
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                })
                .catch(err => {
                    this.$message.error('不要再试了哦，没有权限');
                });
        },
        handleCloseTag(tag) {
            this.tagsList.splice(this.tagsList.indexOf(tag), 1);
        },
        showInputTag() {
            this.inputVisibleTag = true;
            this.$nextTick(_ => {
                this.$refs.saveTagInputTag.$refs.input.focus();
            });
        },
        handleInputConfirmTag() {
            const _this = this;
            let tag_name = this.inputValueTag;
            let data = {
                tag_name
            };
            if (tag_name) {
                //先判断是否有这个标签，如果有，则返回对应的id和name
                this.$axios.get('/getTagByName/' + tag_name).then(res => {
                    if (res.data.code === 200) {
                        _this.tagsList.push(res.data.data);
                        this.$message.success('添加标签成功');
                    } else {
                        _this.$axios
                            .post('/admin/saveTag', data, {
                                headers: {
                                    Authorization: localStorage.getItem('token')
                                }
                            })
                            .then(res => {
                                let newTag = {
                                    tag_id: res.data.data,
                                    tag_name: tag_name
                                };
                                _this.tagsList.push(newTag);
                                this.$message.success('添加标签成功');
                            })
                            .catch(err => {
                                this.$message.error('不要再试了哦，没有权限');
                            });
                    }
                });
            }
            this.inputVisibleTag = false;
            this.inputValueTag = '';
        },
        handleCloseCategory(category) {
            this.categorys.splice(this.categorys.indexOf(category), 1);
        },
        showInputCategory() {
            this.inputVisibleCategory = true;
            this.$nextTick(_ => {
                this.$refs.saveInputCategory.$refs.input.focus();
            });
        },
        handleInputConfirmCategory() {
            const _this = this;
            let category_name = this.inputValueCategory;
            let data = {
                category_name
            };
            if (category_name) {
                //先判断是否有这个分类，如果有，则返回对应的id和name
                this.$axios.get('/getCateGoryByName/' + category_name).then(res => {
                    if (res.data.code === 200) {
                        _this.categorys.push(res.data.data);
                        this.$message.success('添加分类成功');
                    } else {
                        _this.$axios
                            .post('/admin/saveCategory', data, {
                                headers: {
                                    Authorization: localStorage.getItem('token')
                                }
                            })
                            .then(res => {
                                let newCategory = {
                                    category_id: res.data.data,
                                    category_name: category_name
                                };
                                _this.categorys.push(newCategory);
                                this.$message.success('添加分类成功');
                            })
                            .catch(err => {
                                this.$message.error('不要再试了哦，没有权限');
                            });
                    }
                });
            }
            this.inputVisibleCategory = false;
            this.inputValueCategory = '';
        },
        getData() {
            const _this = this;
            const blog_id = this.$route.query.blog_id;
            this.$axios
                .get('/admin/blog/' + parseInt(blog_id), {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.blogDetail = res.data.data;
                    _this.content = res.data.data.blog_content;
                    _this.tagsList = res.data.data.tags;
                    _this.categorys = res.data.data.cateGory;
                });
        },
        onBeforeUploadImage(file) {
            const isIMAGE = file.type === 'image/jpeg' || 'image/jpg' || 'image/png';
            const isLt1M = file.size / 1024 / 1024 < 1;
            if (!isIMAGE) {
                this.$message.error('上传文件只能是图片格式!');
            }
            if (!isLt1M) {
                this.$message.error('上传文件大小不能超过 1MB!');
            }
            return isIMAGE && isLt1M;
        },
        UploadImage(param) {
            const formData = new FormData();
            formData.append('blog_img', param.file);
             this.$axios
                .post('/admin/uploadImg', formData, {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                      this.$message.success(res.data.msg);
                    this.blogDetail.blog_cover_image=res.data.data;
                    param.onSuccess(); // 上传成功的图片会显示绿色的对勾
                    // 但是我们上传成功了图片， fileList 里面的值却没有改变，还好有on-change指令可以使用
                })
                .catch(err => {
                      this.$message.error("没有权限啦");
                    param.onError();
                });
        },
        fileChange(file) {
            this.$refs.upload.clearFiles(); //清除文件对象
            this.logo = file.raw; // 取出上传文件的对象，在其它地方也可以使用
            this.fileList = [{ name: file.name, url: file.url }]; // 重新手动赋值filstList， 免得自定义上传成功了, 而fileList并没有动态改变， 这样每次都是上传一个对象
        }
    }
};
</script>
<style scoped>
.editor-btn {
    margin-top: 20px;
}
.el-tag + .el-tag {
    margin-left: 10px;
}
.button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
}
.input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
}
</style>