<template>
    <div class="header">
        <div class="header-logo">
            <img class="logo" src="@/images/logohere_black.jpg"/>
        </div>
        <div class="header-menu">
        </div>
        <div class="header-right">


            <Menu mode="horizontal" theme="dark" active-name="1" class="menu-custom">
                <MenuItem name="1">
                    <Icon type="ios-paper" />
                    首页
                </MenuItem>
                <MenuItem name="2">
                    <Icon type="ios-people" />
                    API服务
                </MenuItem>
                <Submenu name="3">
                    <template slot="title">
                        <Icon type="ios-albums-outline" />
                        产品&解决方案
                    </template>
                    <MenuGroup title="使用">
                        <MenuItem name="3-1">新增和启动</MenuItem>
                        <MenuItem name="3-2">活跃分析</MenuItem>
                        <MenuItem name="3-3">时段分析</MenuItem>
                    </MenuGroup>
                    <MenuGroup title="留存">
                        <MenuItem name="3-4">用户留存</MenuItem>
                        <MenuItem name="3-5">流失用户</MenuItem>
                    </MenuGroup>
                </Submenu>
            </Menu>

            <template v-if="loginData">
                <Dropdown @on-click="dropdownClicked" trigger="click" style="margin-left: 20px;text-align: left">
                    <a href="javascript:void(0)">
                        {{hasValue(loginData.nickName) ? loginData.nickName : '137****2123'}}
                        <Icon type="arrow-down-b"></Icon>
                    </a>
                    <DropdownMenu slot="list">
                        <DropdownItem name="setting">设置</DropdownItem>
                        <DropdownItem name="logout">退出登录</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            </template>
            <a v-else @click="goToLogin">
                登录
            </a>
        </div>
    </div>
</template>
<script>
import {setLogout, setUserNickName, getUser, switchApp, getApp, getAppId, getApps} from '@/libs/account';
import {http, hasValue} from '@/libs/util';

export default {
    data () {
        const validateInput = (rule, value, callback) => {
            if (value !== value.trim()) {
                callback(new Error('请移除前后空格'));
            } else {
                callback();
            }
        };
        return {
            loginData: {},
            app: {},
            appId: '',
            appList: [],
            inSettingUser: false,
            settingForm: {
                nickName: ''
            },
            settingFormRule: {
                nickName: [
                    {required: true, message: '请输入用户昵称', trigger: 'blur'},
                    {required: true, validator: validateInput, trigger: 'blur'},
                    {required: true, pattern: /^[A-Za-z0-9\u4E00-\u9FA5]*$/g, message: '用户昵称中只允许包含字母、数字和中文', trigger: 'blur'}
                ]
            }
        };
    },
    async created () {
        let apps = await getApps();

        if (apps) {
            this.appList = apps;
        }

        let app = getApp();
        if (app) {
            this.app = app;
            this.appId = app.appId;
        }

        this.loginData = getUser();
        this.settingForm.nickName = this.loginData.nickName;
    },
    methods: {
        handelChangeApp (appId) {
            if (appId === getAppId()) return false;
            let app = this.appList.find(e => {
                return e.appId === appId;
            });

            // 如果当前没有选择 app 就不需要询问是否切换
            if (getApp()) {
                this.$Modal.confirm({
                    title: '你确定要切换吗？',
                    content: '切换 App 将导致当前操作被中断!',
                    onOk: () => {
                        switchApp(app);
                    },
                    onCancel: () => {
                        let app = getApp();
                        this.appId = app.appId;
                    }
                });
            } else {
                switchApp(app);
            }
        },
        handelLogout () {
            this.loginData = null;
            setLogout();
            this.$router.push('/');
            location.reload();
        },
        dropdownClicked: function (name) {
            if (name === 'logout') {
                this.handelLogout();
            } else if (name === 'setting') {
                this.inSettingUser = true;
            }
        },
        toIndex: function () {
            this.$router.push('/');
        },
        goToLogin: function () {
            this.$router.push('/login');
        },
        handelCopyAppId: function () {
            let app = getApp();
            this.$copyText(app.tenantAppId).then(() => {
                this.$Message.success('已复制AppId[ ' + app.tenantAppId + ' ]到剪贴板');
            }, () => {
                this.$Message.error('复制失败');
            });
        },
        hasValue,
        handleSubmit (name) {
        	console.log(name);
            this.$refs[name].validate(async valid => {
                if (!valid) {
                    this.$Message.error('请先完成所有设置!');
                    return false;
                }

                let response = await http.put(`/user/update/${this.settingForm.nickName}`);
                if (response.data.code !== 200) {
                    this.$Notice.error({
                        title: '错误',
                        desc: response.data.message
                    });
                } else {
                    setUserNickName(this.settingForm.nickName);
                    this.inSettingUser = false;
                    location.reload();
                }
            });
        }
    }
};
</script>
<style scoped lang="scss">
    .header {
        height: 80px;
        padding: 0 20px;
        display: flex;
        justify-content: space-between;
        align-self: center;
        font-size: 12px;
        line-height: 64px;
        background-color: #495060;
        box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.05);
        .header-logo {
            height: 100%;
            .logo {
                display: block;
                height: 100%;
                width: auto;
                padding: 10px 0px;
            }
        }
        .header-right,.header-menu {
            display: flex;
            align-items: center;
            & > span {
                white-space: nowrap;
                text-align: right;
            }
            a{
                color: white;
            }
        }
    }
</style>
<style lang="scss">
    .header-switch-app {
        .ivu-select-selection {
            border: 0;
        }
        .ivu-select-visible .ivu-select-selection {
            box-shadow: none;
        }
        .ivu-select-dropdown {
            width: 240px !important;
        }
    }
    .user-modal{
        .ivu-modal {
            width: 80% !important;
            max-width: 600px;
        }
        .ivu-modal-footer {
            display: none;
        }
    }
    .ivu-layout-sider-children{
        background-color: white;
    }
    .ivu-menu-item{

    }
</style>
