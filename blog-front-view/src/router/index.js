import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'
import Home from '../views/Home.vue'
import Archive from '../views/Archive.vue'
import Article from '../views/Article.vue'
import BlogsByParams from '../components/BlogsByTagCate.vue'
import friends from '../views/Friends.vue'
import about from '../views/About.vue'
import Moment from '../views/Moment.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    meta: {
      title: '首页'
    }
  },
  {
    path: '/Home',
    name: 'Home',
    component: Home,
    meta: {
      title: '首页'
    }
  },
  {
    path: '/archive',
    name: 'archive',
    component: Archive,
    meta: {
      title: '归档'
    }
  },
  {
    path: '/moment',
    name: 'moment',
    component: Moment,
    meta: {
      title: '动态说说'
    }
  },
  {
    path: '/article/:blogId',
    name: 'article',
    component: Article,
    meta: {
      title: '文章'
    }
  },
  {
    path: '/list/:type/:id',
    name: 'list',
    component: BlogsByParams,
    meta: {
      title: '文章'
    }
  },
  {
    path: '/friends',
    name: 'friends',
    component: friends,
    meta: {
      title: '友情链接'
    }
  },
  {
    path: '/about',
    name: 'about',
    component: about,
    meta: {
      title: '关于我'
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//挂载路由守卫
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    if (store.state.siteInfo.webTitleSuffix) {
      document.title = to.meta.title + store.state.siteInfo.webTitleSuffix
    } else {
      document.title = to.meta.title
    }
  }
  next()
})
export default router
