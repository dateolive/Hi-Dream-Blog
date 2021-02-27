import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            redirect: '/sysindex'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件', requireAuth: true },
            children: [
                {
                    path: '/sysindex',
                    name:'sysindex',
                    component: () => import(/* webpackChunkName: "SysIndex" */ '../components/page/SysIndex.vue'),
                    meta: { title: '系统首页', requireAuth: true }
                },
                {
                    path: '/friendlist',
                    name:'friendlist',
                    component: () => import(/* webpackChunkName: "friendlist" */ '../components/page/FriendList.vue'),
                    meta: { title: '友情链接', requireAuth: true  }
                },
                {
                    path: '/articles',
                    name:'articles',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/Articles.vue'),
                    meta: { title: '文章列表', requireAuth: true  }
                },
                {
                    // BlogEdit
                    path: '/BlogEdit/:blog_id',
                    name:'BlogEdit',
                    component: () => import(/* webpackChunkName: "BlogEdit" */ '../components/page/BlogEdit.vue'),
                    meta: { title: '文章编辑', requireAuth: true  }
                },
                {
                    // BlogEdit
                    path: '/BlogEdit',
                    name:'BlogRealse',
                    component: () => import(/* webpackChunkName: "BlogEdit" */ '../components/page/BlogEdit.vue'),
                    meta: { title: '文章编写', requireAuth: true  }
                },
                {
                    path: '/blogtag',
                    name:'blogtag',
                    component: () => import(/* webpackChunkName: "blogtag" */ '../components/page/BlogTag.vue'),
                    meta: { title: '标签目录', requireAuth: true  }
                },
                {
                    path: '/category',
                    name:'category',
                    component: () => import(/* webpackChunkName: "category" */ '../components/page/CateGory.vue'),
                    meta: { title: '分类目录', requireAuth: true  }
                },
                {
                    path: '/comments',
                    name:'comments',
                    component: () => import(/* webpackChunkName: "category" */ '../components/page/Comments.vue'),
                    meta: { title: '评论管理', requireAuth: true  }
                },
                {
                    path: '/moment',
                    name:'moment',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/Moment.vue'),
                    meta: { title: '动态列表', requireAuth: true  }
                },
                {
                    path:'/joblogs',
                    name:'joblogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Joblogs.vue'),
                    meta: { title: '任务日志', requireAuth: true  }
                },
                {
                    path:'/loginlogs',
                    name:'loginlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Loginlogs.vue'),
                    meta: { title: '登录日志', requireAuth: true  }
                },
                {
                    path:'/operationlogs',
                    name:'operationlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Operationlogs.vue'),
                    meta: { title: '操作日志', requireAuth: true  }
                },
                {
                    path:'/errorlogs',
                    name:'errorlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Errorlogs.vue'),
                    meta: { title: '异常日志', requireAuth: true  }
                },
                {
                    path:'/vistorlogs',
                    name:'vistorlogs',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/log/Vistorlogs.vue'),
                    meta: { title: '访问日志', requireAuth: true  }
                },
                {
                    path:'/SiteSetting',
                    name:'SiteSetting',
                    component: () => import(/* webpackChunkName: "articles" */ '../components/page/SiteSetting.vue'),
                    meta: { title: '站点设置', requireAuth: true  }
                },
                {
                    path: '/404',
                    name:'404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' , requireAuth: true }
                }
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
