<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'sysindex',
                    title: '系统首页'
                },
                {
                    icon: 'el-icon-edit',
                    index: '1',
                    title: '文章管理',
                    subs: [
                        {
                            index: 'articles',
                            title: '文章列表'
                        },
                        {
                            index: 'BlogEdit',
                            title: '发布文章'
                        }
                    ]
                },
                {
                    icon: 'el-icon-s-help',
                    index: 'moment',
                    title: '动态管理'
                },
                {
                    icon: 'el-icon-lx-addressbook',
                    index: 'friendlist',
                    title: '友情链接'
                },
                {
                    icon: 'el-icon-lx-tag',
                    index: 'blogtag',
                    title: '标签目录'
                },
                {
                    icon: 'el-icon-notebook-2',
                    index: 'category',
                    title: '分类目录'
                },
                {
                    icon: 'el-icon-chat-dot-round',
                    index: 'comments',
                    title: '评论管理'
                },
                 {
                    icon: 'el-icon-tickets',
                    index: '7',
                    title: '站点管理',
                    subs: [
                        {
                            index: 'SiteSetting',
                            title: '站点设置'
                        }
                    ]
                },
                {
                    icon: 'el-icon-tickets',
                    index: '8',
                    title: '日志管理',
                    subs: [
                        {
                            index: 'joblogs',
                            title: '任务日志'
                        },
                        {
                            index: 'loginlogs',
                            title: '登录日志'
                        },
                        {
                            index: 'operationlogs',
                            title: '操作日志'
                        },
                        {
                            index: 'errorlogs',
                            title: '异常日志'
                        },
                        {
                            index: 'vistorlogs',
                            title: '访问日志'
                        }
                    ]
                }
            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
