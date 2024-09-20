import { Loading } from "element-ui";

export const servicesLoading = (node,str,lock) => {
  console.log(document.querySelector(node))
  return Loading.service({
    target: document.querySelector(node),//loading需要覆盖的DOM节点，默认为body
    text: str,//加载文案
    lock,//同v-loading的修饰符
    // backgroundColor: 'rgba(55,55,55,0.4)',//背景色
    // spinner: 'el-icon-loading',//加载图标
  })
}

export const dialogLoading = () => {
  console.log(document.querySelector("#CloudDialog").querySelector("#el-form"))
  return Loading.service({
    target: document.querySelector("#CloudDialog").querySelector("#el-form"),
    text: '正在加载',
    lock:true,
  })
}
