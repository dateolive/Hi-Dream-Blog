import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueRouter from 'vue-router'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import 'element-ui/lib/theme-chalk/display.css'
import './assets/style.css'
import "./assets/typo.css";
import Head from './components/Head'
import Footer from './components/Footer'
import FriendSider from './components/FriendSider'
import tags from './components/tags'
import categorys from './components/categorys'
import commentList from './components/Comment'
import Introduction from './components/Introduction'
import store from './store'
Vue.use(Element)
import axios from '@/plugins/axios'
Vue.prototype.$axios = axios //
 // 全局注册
 // import with ES6
Vue.component("Head",Head)
Vue.component("Footer",Footer)
Vue.component("FriendSider",FriendSider)
Vue.component("tags",tags)
Vue.component("categorys",categorys)
Vue.component("commentList",commentList)
Vue.component("Introduction",Introduction)
Vue.use(VueRouter)
const cubic = value => Math.pow(value, 3);
const easeInOutCubic = value => value < 0.5 ? cubic(value * 2) / 2 : 1 - cubic((1 - value) * 2) / 2;
Vue.prototype.scrollToTop = function () {
	const el = document.documentElement
	const beginTime = Date.now()
	const beginValue = el.scrollTop
	const rAF = window.requestAnimationFrame || (func => setTimeout(func, 16))
	const frameFunc = () => {
		const progress = (Date.now() - beginTime) / 500;
		if (progress < 1) {
			el.scrollTop = beginValue * (1 - easeInOutCubic(progress))
			rAF(frameFunc)
		} else {
			el.scrollTop = 0
		}
	}
	rAF(frameFunc)
}
Vue.config.productionTip = false

//希望大家能留下版权信息，十分感谢
console.log("\n %c Hi-Dream’s BLOG 博客系统开源作者：datealive %c https://github.com/dateolive \n", "color: #48dbfb; background: #1b1c1d; padding:5px 0;", "background: #fadfa3; padding:5px 0;")

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
