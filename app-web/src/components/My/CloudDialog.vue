<template>
  <el-dialog
    ref="CloudDialog"
    top="15vh"
    :title="title"
    :view="view"
    :width="width"
    custom-class="cloud-loading"
    :append-to-body="true"
    :modal-append-to-body="false"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    style="height:90vh;overflow: auto; margin: 5vh auto"
    :visible="visible"
    :before-close="handleClose"
    @open="openSrcoll()"
    @close="handleClose()"
    :formLoading="formLoading"
  >
    <!-- Form表单 -->
    <div
      contenteditable="false"
      :class="view ? 'dialog-form-view' : 'dialog-form'"
      :style="formStyle">
      <slot name="contentarea"/>
    </div>
    <!-- 底部操作按钮   -->
    <div slot="footer" class="dialog-footer content_center">
      <el-button class="footer-button" type="warning" plain @click="handleClose">
        关闭
      </el-button>
      <el-button v-show="!view" :loading="submitLoading" class="footer-button" type="primary" plain @click="submitForm">
        提交
      </el-button>
    </div>
  </el-dialog>
</template>
<script>

import {dialogLoading} from '@/utils/loading'

export default {
  name: 'CloudDialog',
  props: {
    title: {
      type: String,
      default: ''
    },
    width: String,
    visible: {
      type: Boolean,
      default: false
    },
    formHeight: {
      type: Number,
      default: 50
    },
    view: {
      type: Boolean,
      default: false
    },
    fullscreen: {
      type: Boolean,
      default: false
    },
    formLoading: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    formLoading(loading) {
      this.$nextTick(() => {
        this.submitLoading = loading
        if (loading) {
          this.buttonLoading = dialogLoading()
        } else {
          if (this.buttonLoading) {
            this.buttonLoading.close();
          }
        }
      });

    }
  },
  data() {
    return {
      buttonLoading: false,
      submitLoading: false,
      formStyle: {
        height: this.formHeight + 'vh',
        'overflow-y': 'auto',
        'border-top': '2px solid #f5f7fa'
      }
    }
  },
  methods: {
    openSrcoll() {
      this.$nextTick(() => {
        let form = document.getElementsByClassName("dialog-form")[0];
        if (form) {
          form.scrollTop = 0;
          form.height = '90vh';
        }
      });
      this.$emit('open')
    },
    handleClose() {
      this.$emit('close')
    },
    submitForm() {
      this.$emit('submitForm')
    }
  }
};
</script>
<style lang="scss" scoped>
.el-dialog__wrapper {
  overflow-y: hidden !important;
}

::v-deep.cloud-dialog .el-dialog__body {
  max-height: calc(100vh - 150px);
  overflow: auto;
  border-top: 1px solid #dfdfdf;
  border-bottom: 1px solid #dfdfdf;
  border-radius: 15px !important;
}

.dialog-form::-webkit-scrollbar {
  width: 2px;
}

.dialog-form::-webkit-scrollbar-track {
  background: #20a0ff;
}

.dialog-form::-webkit-scrollbar-thumb {
  background: #00ff00;
  border-radius: 10px;
}


::v-deep .el-dialog {
  border-radius: 15px !important;
}

::v-deep .el-dialog {
  position: fixed;
  height: fit-content;
  left: 0 !important;
  right: 0 !important;
  top: 0 !important;
  bottom: 0 !important;
  margin: auto !important;
}

.content_center {
  position: sticky !important;
  border-top: 2px solid #f5f7fa;
  bottom: 20px;
  width: 100%;
  z-index: 1002;
}

::v-deep .el-dialog__footer {
  padding: 10px !important;
}

::v-deep .el-dialog__body {
  padding: 0px 20px !important;
}

::v-deep .el-form-item {
  margin-right: 10px;
}

.footer-button {
  margin-top: 5px;
  border-radius: 5px !important;
}

// input
.dialog-form-view {
  ::v-deep .el-input__inner {
    border: none !important;
    readonly: read-only;
  }

  ::v-deep .el-textarea__inner {
    border: none !important;
  }

  ::v-deep .el-input__inner::placeholder {
    opacity: 0;
  }

  ::v-deep .el-textarea__inner::placeholder {
    opacity: 0;
  }

  ::v-deep .el-input.is-disabled .el-input__inner {
    background-color: transparent !important;
  }

  ::v-deep .el-textarea.is-disabled .el-textarea__inner {
    background-color: transparent !important;
  }

  ::v-deep .el-input-number__decrease {
    display: none !important;
  }

  ::v-deep .el-input-number__increase {
    display: none !important;
  }

  .el-icon-view table-operation {
    font-size: 5px;
  }
}
</style>
