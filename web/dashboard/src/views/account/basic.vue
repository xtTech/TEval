<template>
    <div id="account-basic">
        <Row>
            <Col span="12">
                <Card title="账户信息" class="card-custom">
                    <a href="#" slot="extra" @click="inSettingUser=true" style="line-height: 20px">
                        <Icon type="ios-photos-outline" />
                        修改
                    </a>
                    <Row>
                        <Col span="4" class="left-span">登录账号</Col>
                        <Col span="20" class="right-span">{{userInfo.username}}</Col>
                    </Row>
                    <Row>
                        <Col span="4" class="left-span">账户名称</Col>
                        <Col span="20" class="right-span">{{userInfo.nickName}}</Col>
                    </Row>
                    <Row>
                        <Col span="4" class="left-span">账户ID</Col>
                        <Col span="20" class="right-span">{{userInfo.userId}}</Col>
                    </Row>
                    <Row>
                        <Col span="4" class="left-span">创建时间</Col>
                        <Col span="20" class="right-span">{{userInfo.createdTime}}</Col>
                    </Row>
                </Card>
            </Col>
        </Row>
        <Row>
            <Col span="24">
                <Card title="开发信息">
                    <Row>
                        <Col span="4" class="left-span">开发者ID (AppID)</Col>
                        <Col span="20" class="right-span">
                            {{devInfo.appId}}
                            <br>
                            <span class="info-span">开发者ID是服务调用识别码，配合开发者密码可调用服务的接口能力。</span>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="4" class="left-span">开发者密码 (AppSecret)</Col>
                        <Col span="20" class="right-span">
                            {{devInfo.appSecret}} <Button size="small">展开</Button>
                            <br>
                            <span class="info-span">开发者密码是校验开发者身份的密码，具有极高的安全性。切记勿把密码直接交给第三方开发者或直接存储在代码中。</span>
                        </Col>
                    </Row>
                </Card>
            </Col>
        </Row>
        <Modal v-model="inSettingUser" title="账户信息修改" class="user-modal">
            <Form :model="settingForm" label-position="left" :label-width="100" ref="settingForm">
                <FormItem label="登录账号:" prop="username">
                    <Input v-model="settingForm.username" placeholder="请输入用户登录账号"></Input>
                </FormItem>
                <FormItem label="账户名称:" prop="nickName">
                    <Input v-model="settingForm.nickName" placeholder="请输入账户名称"></Input>
                </FormItem>
                <FormItem align="right" style="margin-bottom: 0">
                    <Button type="primary" @click="handleSubmit('settingForm')">保存修改</Button>
                </FormItem>
            </Form>
            <div slot="footer"></div>
        </Modal>
    </div>
</template>

<script>
export default {
    data () {
        return {
            inSettingUser: false,
            userInfo: {
                username: 'hzchenl@tairanchina.com',
                nickName: '泰然金融',
                userId: '123456789',
                createdTime: '2018-01-01 12:00:00'
            },
            settingForm: {
                username: 'hzchenl@tairanchina.com',
                nickName: '泰然金融',
                userId: '123456789',
                createdTime: '2018-01-01 12:00:00'
            },
            devInfo: {
                appId: 'wx7fd4290faf76c0f8',
                appSecret: 'wx7***0f8'
            }
        };
    },
    created () {
    },
    methods: {
    }
};
</script>

<style>
    .card-custom{
        margin-bottom: 20px;
    }
    .left-span{
        padding: 0 10px;
        line-height: 2;
    }
    .right-span{
        padding: 0 10px;
        line-height: 2;
    }
    .info-span{
        margin-top: 10px;
        color: #b5b5b5;
    }
</style>
