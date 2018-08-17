<template>
    <Form :model="this" :label-width="100" label-position="left">
        <FormItem
                v-for="(item, index) in errorCodes"
                v-if="item.status"
                :key="index"
                label="错误码"
                :prop="'errorCodes.' + index + '.code'"
                :rules="{required: true, message: '错误码不能为空', trigger: 'blur'}">
            <Row>
                <Col span="4">
                    <Input type="text" v-model="item.code" placeholder="错误码"></Input>
                </Col>
                <Col span="16" offset="1">
                    <Input type="text" v-model="item.desc" placeholder="输入描述"></Input>
                </Col>
                <Col span="2" offset="1" align="right">
                    <Button @click="handleRemove(index)">删除此条</Button>
                </Col>
            </Row>
        </FormItem>
        <FormItem>
            <Row>
                <Col span="21">
                    <Button type="dashed" long @click="handleAdd" icon="md-add">添加一条错误码</Button>
                </Col>
            </Row>
        </FormItem>
    </Form>
</template>

<script>
export default {
    props: {
        errorCodes: {
            type: Array,
        }
    },
    data () {
        return {
            index: this.errorCodes.len
        };
    },
    created () {
    },
    methods: {
        handleSubmit (name) {
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$Message.success('Success!');
                } else {
                    this.$Message.error('Fail!');
                }
            })
        },
        handleReset (name) {
            this.$refs[name].resetFields();
        },
        handleAdd () {
            this.index++;
            this.errorCodes.push({
                code: null,
                desc: '',
                index: this.index,
                status: 1
            });
        },
        handleRemove (index) {
            this.errorCodes[index].status = 0;
        }
    }
};
</script>

<style>
    .demo-upload-list img{
        width: 100%;
        height: 100%;
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
