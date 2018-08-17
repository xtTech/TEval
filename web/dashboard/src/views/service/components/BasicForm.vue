<template>
    <Form :model="formItem" :label-width="80">
        <FormItem label="服务名称">
            <Input v-model="formItem.input" placeholder="请输入服务名称"></Input>
        </FormItem>
        <FormItem label="服务类型">
            <Select v-model="formItem.select">
                <Option value="beijing">即时通讯</Option>
                <Option value="shanghai">用户管理</Option>
                <Option value="shenzhen">地址服务</Option>
                <Option value="shenzhen">其他</Option>
            </Select>
        </FormItem>
        <FormItem label="服务图标">
            <template v-if="formItem.img.status === 'finished'">
                <img :src="formItem.img.url">
                <div class="demo-upload-list-cover">
                    <Icon type="ios-eye-outline" @click.native="handleView(item.name)"></Icon>
                    <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                </div>
            </template>
            <template v-else>
                <Progress v-if="showProgress" :percent="percentage" hide-info></Progress>
            </template>
            <Upload
                    ref="upload"
                    :show-upload-list="false"
                    :on-success="handleSuccess"
                    :format="['jpg','jpeg','png']"
                    :max-size="2048"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleMaxSize"
                    :before-upload="handleBeforeUpload"
                    type="drag"
                    action="//jsonplaceholder.typicode.com/posts/"
                    style="display: inline-block;width:100px;">
                <div style="width: 100px;height:100px;line-height: 100px;">
                    <Icon type="ios-camera" size="30"></Icon>
                </div>
            </Upload>
        </FormItem>
        <FormItem label="服务简介">
            <Input v-model="formItem.info" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="服务简介"></Input>
        </FormItem>
        <FormItem label="付费类型">
            <RadioGroup v-model="formItem.radio">
                <Radio label="male">付费</Radio>
                <Radio label="female">免费</Radio>
                <Radio label="female1">面议</Radio>
            </RadioGroup>
        </FormItem>
        <FormItem label="功能介绍">
            <Input v-model="formItem.textarea" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="功能介绍"></Input>
            <span class="i">sadwad</span>
        </FormItem>
    </Form>
</template>

<script>
export default {
    props: {
        formItem: {
            type: Object,
        },
        showProgress: {
            type: Boolean
        },
        percentage: {
            type: Number
        }
    },
    data () {
        return {
            queryParams: {},
            step: 1,
            currentPage:1,
            pageSize: 10,
            total: 0,
            versionStatus:[],
            updateTypes:[]
        };
    },
    created () {
    },
    methods: {
        handleView (name) {
            this.imgName = name;
            this.visible = true;
        },
        handleRemove (file) {
            const fileList = this.$refs.upload.fileList;
            this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
        },
        handleSuccess (res, file) {
            file.url = 'https://o5wwk8baw.qnssl.com/7eb99afb9d5f317c912f08b5212fd69a/avatar';
            file.name = '7eb99afb9d5f317c912f08b5212fd69a';
        },
        handleFormatError (file) {
            this.$Notice.warning({
                title: 'The file format is incorrect',
                desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
            });
        },
        handleMaxSize (file) {
            this.$Notice.warning({
                title: 'Exceeding file size limit',
                desc: 'File  ' + file.name + ' is too large, no more than 2M.'
            });
        },
        handleBeforeUpload () {
            const check = this.uploadList.length < 5;
            if (!check) {
                this.$Notice.warning({
                    title: 'Up to five pictures can be uploaded.'
                });
            }
            return check;
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
