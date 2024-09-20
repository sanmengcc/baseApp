
// 初始化Tab页的大小（新增、修改、详情）
export function initTabWidth() {
  this.screenWidth = document.body.clientWidth
  if (this.screenWidth < 550) {
    return '95%'
  } else if (this.screenWidth < 990) {
    return '680px'
  } else if (this.screenWidth < 1400) {
    return '700px'
  } else {
    return '750px'
  }
}

export function initTabHalfWidth() {
  this.screenWidth = document.body.clientWidth
  return '40%'
}


export default {
  initTabWidth,initTabHalfWidth
}
