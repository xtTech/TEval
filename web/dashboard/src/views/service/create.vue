<template>
    <div id="account-basic">
        <Row>
            <Col span="24">

                <Card>
                    <Steps :current="step" :status="stepStatus">
                        <Step title="基本信息" content="这里是该步骤的描述信息"></Step>
                        <Step title="API文档" content="这里是该步骤的描述信息"></Step>
                        <Step title="错误码" content="这里是该步骤的描述信息"></Step>
                        <Step title="示例代码" content="这里是该步骤的描述信息"></Step>
                        <Step title="提交审核" content="这里是该步骤的描述信息"></Step>
                    </Steps>
                </Card>
            </Col>
        </Row>
        <Row class="form-row">
            <Col span="24">
                <Card>
                    <Row>
                        <Col span="12">
                            <Button type="default" :disabled="step===0" @click="prveStep">上一步</Button>
                        </Col>
                        <Col span="12" align="right">
                           <Button type="primary" :disabled="step===4" @click="nextStep">下一步</Button>
                        </Col>
                    </Row>
                </Card>
            </Col>
        </Row>
        <Row class="form-row">
            <Col span="24">
                <Card v-show="step === 0">
                    <BasicForm :form-item="formItem"></BasicForm>
                </Card>
                <Card v-show="step === 1">
                    <ApiForm :form-item="formItem"></ApiForm>
                </Card>

                <Card v-show="step === 2">
                    <p slot="title">服务级错误码</p>
                    <ErrorCodeForm :error-codes="formItem.serviceErrorCodes"></ErrorCodeForm>
                </Card>
                <Card v-show="step === 2" class="form-row">
                    <p slot="title">系统级错误码</p>
                    <ErrorCodeForm :error-codes="formItem.systemErrorCodes"></ErrorCodeForm>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
import BasicForm from './components/BasicForm'
import ApiForm from './components/ApiForm'
import ErrorCodeForm from './components/ErrorCodeForm'

export default {
    components: {
        BasicForm,
        ApiForm,
        ErrorCodeForm
    },
    data () {
        return {
            queryParams: {},
            formItem: {
                input: 'asdsad',
                select: '',
                radio: 'male',
                checkbox: [],
                switch: true,
                date: '',
                time: '',
                slider: [20, 50],
                textarea: '',
                img: {
                    url: '',
                    name: '',
                    status: 'finished'
                },
                serviceErrorCodes: [
                ],
                systemErrorCodes: [
                ]
            },
            step: 2,
            stepStatus: 'process'
        };
    },
    created () {
    },
    methods: {
        nextStep(){
            if(this.step<4){
                this.step++;
            }
        },
        prveStep(){
            if(this.step>0){
                this.step--;
            }
        }
    }
};
</script>

<style>
    .form-row{
        margin-top: 15px;
    }

    .demo-upload-list{
        display: inline-block;
        width: 60px;
        height: 60px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
    .demo-upload-list img{
        width: 100%;
        height: 100%;
    }
    .demo-upload-list-cover{
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }
    .demo-upload-list:hover .demo-upload-list-cover{
        display: block;
    }
    .demo-upload-list-cover i{
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 0 2px;
    }
</style>
