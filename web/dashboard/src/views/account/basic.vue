<template>
    <div id="account-basic">
        <Card title="账户信息" class="card-custom">
            <Row>
                <Col span="2" class="left-span">登录账号</Col>
                <Col span="22" class="right-span">hzchenl@tairanchina.com</Col>
            </Row>
            <Row>
                <Col span="2" class="left-span">账户名称</Col>
                <Col span="22" class="right-span">泰然金融</Col>
            </Row>
            <Row>
                <Col span="2" class="left-span">账户ID</Col>
                <Col span="22" class="right-span">123456789</Col>
            </Row>
            <Row>
                <Col span="2" class="left-span">创建时间</Col>
                <Col span="22" class="right-span">2018-01-01 00:00:00</Col>
            </Row>
        </Card>
        <Card title="开发信息">
            <Row>
                <Col span="3" class="left-span">开发者ID (AppID)</Col>
                <Col span="21" class="right-span">
                    wx7fd4290faf76c0f8
                    <br>
                    <span class="info-span">开发者ID是服务调用识别码，配合开发者密码可调用服务的接口能力。</span>
                </Col>
            </Row>
            <Row>
                <Col span="3" class="left-span">开发者密码 (AppSecret)</Col>
                <Col span="21" class="right-span">
                    wx7***0f8 <Button size="small">展开</Button>
                    <br>
                    <span class="info-span">开发者密码是校验开发者身份的密码，具有极高的安全性。切记勿把密码直接交给第三方开发者或直接存储在代码中。</span>
                </Col>
            </Row>
        </Card>
    </div>
</template>

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
