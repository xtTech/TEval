<template>
    <div id="sidebar">
        <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}">
            <Menu :active-name="activeNavName" theme="light" :open-names="openNames" width="200" ref="menus"
                  @on-select="goToPage">
                <Submenu name="account">
                    <template slot="title">
                        <Icon type="ios-person"/>
                        <span>账号管理</span>
                    </template>
                    <MenuItem name="account-basic">基本资料</MenuItem>
                    <MenuItem name="account-pwd">修改密码</MenuItem>
                </Submenu>
                <Submenu name="service">
                    <template slot="title">
                        <Icon type="ios-calendar" />
                        <span>我的服务</span>
                    </template>
                    <MenuItem name="service-apply">申请列表</MenuItem>
                    <MenuItem name="service-publish">发布服务</MenuItem>
                </Submenu>
            </Menu>
            <Button type="dashed" size="large" class="btn-custom-add-fn" align="center">添加功能插件</Button>
        </Sider>
    </div>
</template>
<script>
import {hasObject} from '@/libs/util';
import {isAdmin} from '@/libs/account';
export default {
    data () {
        return {
            activeNavName: '',
            openNames: [],
            admin: isAdmin()
        };
    },
    created () {
        this.routeWatch();
    },
    mounted () {
        this.$nextTick(() => {
            this.routeWatch();
        });
    },
    filters: {},
    methods: {
        routeWatch () {
            this.activeNavName = this.$route.name;
            this.openNames = [this.$route.matched[0].name];
        },
        goToPage (name) {
            this.$router.push({name});
        }
    },
    watch: {
        '$route': 'routeWatch'
    }
};
</script>
<style lang="scss">
    .btn-custom-add-fn{
        margin: 10px;
        align-self: center;
        align-items: center;
        width: 90%;
        padding: 0;
    }
    #page-admin {
        .page-body{
            // z-index: -1;
            // width: calc(100% - 50px)!important;
        }
    }
</style>
