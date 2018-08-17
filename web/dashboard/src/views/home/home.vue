<template>
    <div id="home">
        <Card style="min-height: 400px;">
            <div class="logo">
                <img src="@/images/logo.png">
                <h2 style="margin-bottom: 50px">
                    <p>泰易服</p>
                </h2>
            </div>
        </Card>
    </div>
</template>
<script>
import {isLogin, getApp, getAppId, getApps, switchApp} from '@/libs/account';

export default {
    data () {
        return {
            isLogin: isLogin(),
            app: getApp(),
            appId: getAppId() ? getAppId() : '',
            appList: []
        };
    },
    async created () {
        console.log(this.apps);
    },
    mounted () {

    },
    methods: {
    }
};
</script>
<style scoped lang="scss">
    #home {
        .logo {
            text-align: center;
            img {
                display: inline-block;
                height: 120px;
                width: auto;
            }
        }
        .nameplate{
            width: 400px;
            position: relative;
            margin: 36px auto;
            border-radius: 4px;
            border: 1px solid #eee;
            padding: 12px;
            border-top: 2px solid #2d8cf0;
            overflow: hidden;
            h3{
                font-size: 20px;
                margin: 12px 0;
            }
            p{
                color: #777777;
                margin: 6px 0;
                font-size: 12px;
            }
            h3,p{
                z-index: 1;
                position: relative;
                word-break: break-all;
            }
            span{
                position: absolute;
                font-size: 80px;
                font-weight: bold;
                color: rgba(249, 249, 249, 0.78);
                right: 12px;
                line-height: .7;
                z-index: 0;
                user-select: none;
            }
        }
    }
</style>
