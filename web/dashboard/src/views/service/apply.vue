<template>
    <div id="account-basic">
        <Row>
            <Col span="24">

                <Card>
                    <div class="form">
                        <Form :model="queryParams" :label-width="80">
                            <Row>
                                <Col span="8">
                                    <FormItem label="申请编号">
                                        <Input v-model="queryParams.appVersion" placeholder=""/>
                                    </FormItem>
                                </Col>
                                <Col span="8">
                                    <FormItem label="服务类型">
                                        <Select v-model="queryParams.updateType" filterable clearable>
                                            <Option v-for="item in updateTypes" :value="item.value" :key="item.value" v-text="item.label" />
                                        </Select>
                                    </FormItem>
                                </Col>
                                <Col span="8">
                                    <FormItem label="申请状态">
                                        <Select v-model="queryParams.versionStatus" filterable clearable>
                                            <Option v-for="item in versionStatus" :value="item.value" :key="item.value" v-text="item.label" />
                                        </Select>
                                    </FormItem>
                                </Col>
                            </Row>
                            <Row>
                                <Col span="24" style="text-align: right">
                                    <!--<Button type="ghost" icon="plus" style="margin-right: 14px">添加 Android 版本</Button>-->
                                    <Button type="primary" icon="search">搜索</Button>
                                </Col>
                            </Row>
                        </Form>
                    </div>
                    <Table border :columns="columns" :data="tableList"/>
                    <div style="margin: 10px 0 0 0; text-align: right">
                        <Page show-total show-elevator show-sizer :total="total" :page-size="pageSize"
                              :current.sync="currentPage" @on-page-size-change=""/>
                    </div>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
export default {
    data () {
        return {
            queryParams: {},
            tableList: [
                {
                    id: "abcbdbe91132b4ff5e0",
                    serviceName: "消息服务",
                    payType: "付费",
                    applyStatus: "审核中",
                    applyTime: "2018-01-01 00:00:00",
                    serviceStartTime: ""
                },
                {
                    id: "abc12dxaa32b4ff51sa",
                    serviceName: "消息服务",
                    payType: "免费",
                    applyStatus: "通过",
                    applyTime: "2018-01-01 00:00:00",
                    serviceStartTime: "2018-05-01 00:00:00"
                },
                {
                    id: "wqac12dxxbtyx14ff5bs",
                    serviceName: "消息服务",
                    payType: "面议",
                    applyStatus: "拒绝",
                    applyTime: "2018-01-01 00:00:00",
                    serviceStartTime: ""
                }
            ],
            columns:[
                {
                    title: '申请编号',
                    key: 'id',
                    minWidth: 100,
                },
                {
                    title: '服务名称',
                    key: 'serviceName',
                },
                {
                    title: '付费类型',
                    minWidth: 140,
                    key: 'payType'
                },
                {
                    title: '申请状态',
                    minWidth: 140,
                    key: 'applyStatus'
                },
                {
                    title: '申请提交时间',
                    minWidth: 140,
                    key: 'applyTime'
                },
                {
                    title: '服务开始时间',
                    minWidth: 140,
                    key: 'serviceStartTime'
                },
                {
                    title: '操作',
                    render: (h, params) => {
                        console.log(params.row);
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '12px'
                                },
                                on: {
                                    click: () => {
                                        this.$router.push({
                                            name: 'version-android_edit',
                                            params: {
                                                androidId: params.row.id
                                            }
                                        });
                                    }
                                }
                            }, '查看')
                        ]);
                    }
                }
            ],
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
    .form{
        margin-bottom: 20px;
    }
</style>
