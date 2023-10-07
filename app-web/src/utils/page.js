
// 初始化Tab页的大小（新增、修改、详情）
export function initTabWidth() {
  this.screenWidth = document.body.clientWidth
  if (this.screenWidth < 550) {
    return '95%'
  } else if (this.screenWidth < 990) {
    return '580px'
  } else if (this.screenWidth < 1400) {
    return '600px'
  } else {
    return '650px'
  }
}

export default {
  initTabWidth
}
