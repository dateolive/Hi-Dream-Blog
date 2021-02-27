import axios from 'axios'
import Element from "element-ui";
axios.defaults.baseURL = 'https://api.datealive.top'
import {Message} from "element-ui"
let CancelToken = axios.CancelToken
axios.interceptors.request.use(config => {
    //console.log("前置拦截")
    // 可以统一设置请求头
    const userJson = window.localStorage.getItem('userInfo') || '{}'
    const user = JSON.parse(userJson)
    if (userJson !== '{}' && user.roles !== 'admin' && config.method !== 'get') {
        config.cancelToken = new CancelToken(function executor(cancel) {
            cancel('演示模式，不允许操作')
        })
        return config
    }
    return config
})
axios.interceptors.response.use(response => {
    const res = response.data;
    //console.log("后置拦截")
    // 当结果的code是否为200的情况
    if (res.code === 200 || res.code === 404) {
        return response
    } else {
        // 弹窗异常信息
        Element.Message({
            message: response.data.msg,
            type: 'error',
            duration: 2 * 1000
        })
        // 直接拒绝往下面返回结果信息
        return Promise.reject(response.data.msg)
    }
},
    error => {
      //  console.log('err==>' + error)// for debug
            // if (error.response.status === 401) {
            //     store.commit('REMOVE_INFO');
            //     router.push({
            //         path: '/login'
            //     });
            //     error.message = '请重新登录';
            // }
            // if (error.response.status === 403) {
            //     error.message = '权限不足，无法访问';
            // }
              // 弹窗异常信息
              Message.error(error)
        return Promise.reject(error)
    })
export default axios