import axios from 'axios'
import Element from "element-ui";
axios.defaults.baseURL='https://api.datealive.top'
axios.interceptors.request.use(config => {
  //console.log("前置拦截")
  // 可以统一设置请求头
  return config
})
axios.interceptors.response.use(response => {
    const res = response.data;
    //console.log("后置拦截")
    // 当结果的code是否为200的情况
    if (res.code === 200||res.code===404) {
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
    return Promise.reject(error)
  })
export default axios