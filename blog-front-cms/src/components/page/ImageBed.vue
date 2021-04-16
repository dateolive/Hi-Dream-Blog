<template>
    <div class="main-container">
        <div class="main-content">
            <el-card class>
                <el-upload
                    class="upload"
                    drag
                    ref="upload"
                    action="string"
                    accept="image/jpeg, image/png, image/jpg"
                    :before-upload="onBeforeUploadImage"
                    :http-request="UploadImage"
                    :on-change="fileChange"
                    :file-list="fileList"
                    multiple
                >
                    <i class="el-icon-upload" style="color:#1e90ff;"></i>
                    <div class="el-upload__text">
                        将文件拖到此处，或
                        <em>点击上传</em>
                    </div>
                    <div
                        class="el-upload__tip"
                        style=" text-align: center;"
                        slot="tip"
                    >只能上传jpg/png文件，且不超过1MB</div>
                </el-upload>
                <el-progress :stroke-width="16" :percentage="progressPercent"></el-progress>
            </el-card>
            <!-- 滚动加载 -->
            <div v-infinite-scroll="load" infinite-scroll-disabled="disabled">
                <Waterfall
                    ref="waterfall"
                    :list="list"
                    :gutter="10"
                    :width="240"
                    :breakpoints="{
            1200: { //当屏幕宽度小于等于1200
              rowPerView: 4,
            },
            800: { //当屏幕宽度小于等于800
              rowPerView: 3,
            },
            500: { //当屏幕宽度小于等于500
              rowPerView: 2,
            }
          }"
                    backgroundColor="#E4E7ED"
                >
                    <template slot="item" slot-scope="props">
                        <div class="card">
                            <div
                                class="cover"
                                :style="initCardStyle(props)"
                                @click="handleClick(props.data)"
                            >
                                <img
                                    :src="props.data.download_url"
                                    alt
                                    @load="$refs.waterfall.refresh"
                                />
                            </div>

                            <div class="name">
                                <p>{{props.data.name }}</p>
                            </div>
                            <div class="menus">
                                <p
                                    data-title="复制"
                                    v-clipboard:copy="props.data.download_url"
                                    v-clipboard:success="onCopy"
                                    v-clipboard:error="onError"
                                />
                                <p data-title="删除" @click="handleDelete(props.data)" />
                            </div>
                        </div>
                    </template>
                </Waterfall>
            </div>
        </div>
    </div>
</template>

<script>
import Waterfall from 'vue-waterfall-plugin';

export default {
    name: 'ImageBed',
    components: {
        Waterfall
    },
    data() {
        return {
            list: [],
            loading: false,
            fileList: [],
            progressPercent: 0
        };
    },
    computed: {
        noMore() {
            return this.list.length >= 200;
        },
        disabled() {
            return this.loading || this.noMore;
        }
    },
    mounted() {
    },
    methods: {
        async load() {
            this.loading = true;
            await this.addNewList();
            this.loading = false;
        },
        addNewList() {
            const _this = this;
            this.$axios
                .get('/admin/allImageByGiteeFromGitee', {
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    _this.list = res.data.data;
                });
        },
        // 初始化卡片样式
        initCardStyle(props) {
            if (this.isSetInitStyle) {
                return {
                    width: `${props.data.itemWidth - 20}px`,
                    height: `${((props.data.itemWidth - 20) / props.data.width) * props.data.height}px`,
                    backgroundColor: props.data.blankColor
                };
            }
        },
        /**
         * 图片点击
         */
        handleClick(item) {
            this.$message.info("码云图床");
        },
        /**
         * 删除
         */
        handleDelete(item) {
            this.delImage(item.download_url);
            // this.$message.error('删除');
        },
        onCopy(e) {
            this.$message.success('内容已复制到剪切板！');
        },
        // 复制失败时的回调函数
        onError(e) {
            this.$message.error('抱歉，复制失败！');
        },
        delImage(name) {
            const _this = this;
             // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            }).then(() => {
            this.$axios
                .delete('/admin/delImgFromGitee', {
                    params: {
                        imgPath: name
                    },
                    headers: {
                        Authorization: localStorage.getItem('token')
                    }
                })
                .then(res => {
                    if (res.data.code === 200) {
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error('删除失败');
                    }
                    _this.load()
                });
                }).catch(() => {});
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
            const _this=this;
            formData.append('file', param.file);
            _this.$axios
                .post(
                    '/admin/uploadImgtoGitee',
                    formData,
                    {
                        headers: {
                            Authorization: localStorage.getItem('token')
                        },
                         'Content-Type': 'multipart/form-data',
                        onUploadProgress: progressEvent => {
                            if(progressEvent.lengthComputable){
                                  _this.progressPercent = Number((progressEvent.loaded / progressEvent.total * 100).toFixed(2))
                            }
                          
                        }
                    },
                
                )
                .then(res => {
                    this.$message.success(res.data.msg);
                    _this.load()
                    // 但是我们上传成功了图片， fileList 里面的值却没有改变，还好有on-change指令可以使用
                })
                .catch(err => {
                    this.$message.error('没有权限啦');
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
.main-container {
    display: flex;
}
.main-container .main-content {
    flex: 1;
    height: 100vh;
    overflow: auto;
    z-index: 0;
}
.main-container .main-content .upload {
    border-radius: 5px;
    border: 1px solid #ebeef5;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    text-align: center;
    padding: 50px;
    margin-bottom: 50px;
    z-index: -1;
    background: url(../../assets/img/comment-1.gif) right bottom no-repeat;
   
}
.main-container .slide-menu {
    flex: 0 0 200px;
    width: 200px;
}
.main-container .slide-menu .operations {
    position: fixed;
    right: 0;
    width: 200px;
    top: 0;
    bottom: 0;
    box-sizing: border-box;
    padding: 20px;
}
.main-container .slide-menu .operations .item {
    margin-bottom: 20px;
}
.main-container .slide-menu .operations .item .title {
    padding-bottom: 10px;
    text-align: left;
    color: #fff;
}
.card {
    background: #fff;
    border-radius: 5px;
    overflow: hidden;
    cursor: pointer;
    position: relative;
    transition: 0.2s;
    top: 0;
}
.card:hover {
    top: -3px;
}
.card .cover {
    margin: 10px 10px 0 10px;
}
.card .cover img {
    display: block;
    width: 100%;
}
.card .name {
    background: #fff;
    color: #666;
    font-weight: 600;
    padding: 10px 20px;
    font-size: 14px;
}
.card .menus {
    padding: 10px;
    border-top: 1px solid #e7e7e7;
    text-align: right;
}
.card .menus p {
    position: relative;
    display: inline-block;
    padding: 4px 10px;
    text-decoration: none;
    text-align: center;
    cursor: pointer;
    user-select: none;
    color: white;
    font-size: 12px;
    margin-left: 10px;
}
.card .menus p::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: linear-gradient(135deg, #6e8efb, #a777e3);
    border-radius: 4px;
    transition: box-shadow 0.5s ease, transform 0.2s ease;
    will-change: transform;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0)) rotateY(var(--ry, 0)) translateZ(var(--tz, -12px));
}
.card .menus p:hover::before {
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
.card .menus p::after {
    position: relative;
    display: inline-block;
    content: attr(data-title);
    transition: transform 0.2s ease;
    font-weight: bold;
    letter-spacing: 0.01em;
    will-change: transform;
    transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0)) rotateY(var(--ry, 0));
}
</style>