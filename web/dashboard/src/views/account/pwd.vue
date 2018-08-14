<template>
    <div id="account-basic">
        <Row>
            <Col span="12">
                <Card title="修改密码" class="card-custom">
                    <p class="tips">定期更换密码可以让您的账户更加安全；密码必须包含数字、字母，区分大小写，并且不短于6位</p>
                    <Form :model="settingForm" label-position="left" :label-width="100" ref="settingForm">
                        <FormItem label="旧密码:" prop="username">
                            <Input v-model="settingForm.oldPwd" placeholder="请输入旧密码"></Input>
                        </FormItem>
                        <FormItem label="新密码:" prop="nickName">
                            <Input v-model="settingForm.newPwd" placeholder="请输入新密码"></Input>
                        </FormItem>
                        <FormItem label="确认密码:" prop="nickName">
                            <Input v-model="settingForm.newPwd" placeholder="请输入确认密码"></Input>
                        </FormItem>
                        <FormItem align="right" style="margin-bottom: 0">
                            <Button type="primary" @click="handleSubmit('settingForm')">修改密码</Button>
                        </FormItem>
                    </Form>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    data () {
        return {
            settingForm: {
                oldPwd: '',
                newPwd: '',
                newPwdRe: ''
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
    .tips{
        padding: 0 0 20px 0;
        color: gray;
    }
</style>
