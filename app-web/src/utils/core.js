// 表单重置
export function resetForm(refName) {
  if (this.$refs[refName]) {
    this.$refs[refName].resetFields();
  }
}

// 绑定操作栏指令
export function handleCommand(row, command, param) {
  return {
    row: row,
    command: command,
    param: param
  }
}

// 操作栏注册
export function executeOperate(c) {
  console.log(c)
  console.log(this)
  this[c.command](c.row)
}
// 操作栏注册
export function executeParentOperate(c) {
  this.$parent.$parent.$parent.$parent[c.command](c.row)
}
