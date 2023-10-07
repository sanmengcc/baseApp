<template>
  <div id="loginLayout" :style="{ 'backgroundImage': `url(${hostConfig.backgroundImage})` }">
    <div class="login_page">
      <div class="login_page_right" :style="{'backgroundImage': `url(${hostConfig.loginLeftImage})` }"/>
      <div class="login_page_form">
        <div class="login_page_form_title">
          <img class="login_page_form_title_logo" :src="hostConfig.loginLogo" alt="">
          <p class="login_page_form_title_p"> {{ hostConfig.loginTitle }}</p>
        </div>
        <el-form ref="loginForm" :model="loginForm" :rules="rules" class="login-form" autocomplete="off"
                 label-position="left">
          <span v-if="login.type === 'up'">
            <el-form-item prop="account">
              <el-input
                  ref="account"
                  v-model="loginForm.account"
                  :placeholder="$t('login.account')"
                  prefix-icon="el-icon-user"
                  name="account"
                  type="text"
                  autocomplete="off"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                  ref="password"
                  v-model="loginForm.password"
                  prefix-icon="el-icon-key"
                  type="password"
                  :placeholder="$t('login.password')"
                  name="password"
                  autocomplete="off"
                  :show-password="true"
              />
            </el-form-item>
            <el-form-item prop="captchaCode" class="code-input">
              <el-input
                  ref="captchaCode"
                  v-model="loginForm.captchaCode"
                  prefix-icon="el-icon-lock"
                  :placeholder="$t('login.code')"
                  name="captchaCode"
                  type="text"
                  autocomplete="off"
                  style="width: 60%"
                  class="left"
                  @keyup.enter.native="handleLogin"
              />
              <img
                  v-if="imageCode"
                  :src="imageCode"
                  width="38%"
                  height="100%"
                  alt="请输入验证码"
                  class="right"
                  @click="getCodeImage()"
              >
            </el-form-item>
            <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:14px;"
                       @click.native.prevent="handleLogin">
              {{ $t('login.logIn') }}
            </el-button>
          </span>
        </el-form>
      </div>
      <div class="login_page_foot">
        <div class="copyright">&copy; {{ curYear }} <a target="_blank">{{ hostConfig.copyright }}</a></div>
      </div>
    </div>
  </div>
</template>

<script>
import db from '@/utils/localstorage'
import {randomNum} from '@/utils'
import md5 from "js-md5";

export default {
  name: 'Login',
  data() {
    return {
      hostConfig: {},
      curYear: 0,
      login: {
        type: 'up'
      },
      loginForm: {
        loginType: '1',
        account: '',
        password: '',
        captchaId: '',
        captchaCode: ''
      },
      rules: {
        account: {required: true, message: this.$t('rules.require'), trigger: 'blur'},
        password: {required: true, message: this.$t('rules.require'), trigger: 'blur'},
        captchaCode: {required: true, message: this.$t('rules.require'), trigger: 'blur'}
      },
      loading: false,
      randomId: randomNum(24, 16),
      imageCode: '',
      page: {
        width: window.screen.width * 0.5,
        height: window.screen.height * 0.5
      }
    }
  },
  created() {

  },
  mounted() {
    db.clear()

    this.handleAsync()


  },
  destroyed() {
  },
  methods: {
    async setIndex() {
      let res = await this.$api.cloud.sys.getHostConfig();
      this.hostConfig = res.data
      document.title = this.hostConfig.sysName
      var link = document.querySelector("link\[rel\*='icon'\]") || document.createElement('link');
      link.type = 'image/x-icon';
      link.rel = 'shortcut icon';
      link.href = this.hostConfig.favicon;
      document.getElementsByTagName('head')[0].appendChild(link);
      this.$store.commit('host/setHostConfig', this.hostConfig)
      this.curYear = new Date().getFullYear()
    },
    async handleAsync() {
      await this.setIndex();
      await this.getCodeImage()
    },
    getCodeImage() {
      const randomKey = randomNum(24, 16)
      const params = {key: randomKey}
      this.$api.cloud.login.captchaImage(params)
          .then(res => {
            this.imageCode = 'data:image/png;base64,' + res.data.img
            this.loginForm.captchaId = res.data.captchaId
          }).catch((e) => {
        if (e.toString().indexOf('429') !== -1) {
          this.$message({
            message: this.$t('tips.tooManyRequest'),
            type: 'error'
          })
        } else {
          this.$message({
            message: this.$t('tips.getCodeImageFailed'),
            type: 'error'
          })
        }
      })
    },
    handleLogin() {
      let account_c = false
      let password_c = false
      let code_c = false
      this.$refs.loginForm.validateField('account', e => {
        if (!e) {
          account_c = true
        }
      })
      this.$refs.loginForm.validateField('password', e => {
        if (!e) {
          password_c = true
        }
      })
      this.$refs.loginForm.validateField('captchaCode', e => {
        if (!e) {
          code_c = true
        }
      })
      if (account_c && password_c && code_c) {
        this.loading = true
        const that = this
        var userLoginJson = JSON.parse(JSON.stringify(this.loginForm))
        userLoginJson.password = md5(userLoginJson.password)

        this.$api.cloud.login.userLogin(userLoginJson).then((r) => {
          const data = r.data
          this.saveLoginData(data)
          this.getUserDetailInfo()
        }).catch((error) => {
          that.loading = false
          that.getCodeImage()
        })
      }
    },
    saveLoginData(data) {
      this.$store.commit('account/setAccessToken', data.access_token)
      this.$store.commit('account/setRefreshToken', data.refresh_token)
      const current = new Date()
      const expireTime = current.setTime(current.getTime() + 1000 * data.expires_in)
      this.$store.commit('account/setExpireTime', expireTime)
    },
    getUserDetailInfo() {
      this.$api.cloud.login.getUserInfo().then((r) => {
        this.$store.commit('account/setUser', r.data)
        this.$message({
          message: this.$t('tips.loginSuccess'),
          type: 'success'
        })
        this.loading = false
        this.$router.push('/')
      }).catch((error) => {
        this.$message({
          message: this.$t('tips.loginFail'),
          type: 'error'
        })
        console.error(error)
        this.loading = false
      })
    }
  }
}
</script>
<style lang="scss">
#loginLayout {
  .el-input--suffix .el-input__inner {
    padding-right: 30px;
    height: 42px !important;
    font-size: 14px;
  }

  .el-input__inner {
    height: 42px !important;
    line-height: 42px;
  }
}
</style>
<style lang="scss" scoped>
@import "login";
</style>
