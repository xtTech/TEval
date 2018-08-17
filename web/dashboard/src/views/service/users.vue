<template>
    <div id="account-basic">
        <Row>
            <Col span="24">

                <Card>
                    <div class="form">
                        <Form :model="queryParams" :label-width="80">
                            <Row>
                                <Col span="24" style="text-align: right">
                                    <!--<Button type="ghost" icon="plus" style="margin-right: 14px">创建服务</Button>-->
                                    <Button type="primary" icon="refresh">刷新</Button>
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
    data() {
        return {
            queryParams: {},
            tableList: [
                {
                    id: 'abcbdbe91132b4ff5e0',
                    serviceName: '用户中心',
                    username: 'lizhengxian2005@vip.qq.com',
                    appId: 'b279de...',
                    applyTime: '2018-01-01 00:00:00',
                    serviceStatus: '审核中'
                }
            ],
            columns: [
                {
                    title: '申请编号',
                    key: 'id',
                    minWidth: 120,
                },
                {
                    title: '服务名称',
                    key: 'serviceName',
                    minWidth: 100,
                },
                {
                    title: '申请账号',
                    minWidth: 140,
                    key: 'username'
                },
                {
                    title: '开发者ID(AppID)',
                    minWidth: 140,
                    key: 'appId'
                },
                {
                    title: '申请时间',
                    minWidth: 140,
                    key: 'applyTime'
                },
                {
                    title: '状态',
                    minWidth: 140,
                    key: 'serviceStatus'
                },
                {
                    title: '操作',
                    minWidth: 220,
                    render: (h, params) => {
                        console.log(params.row.serviceStatus);

                        let hs = [
                            this.makeButton(h, '查看', 'primary', 'small')
                        ];

                        if (params.row.serviceStatus === '未上架') {
                            hs.push(this.makeButton(h, '上架', 'primary', 'small'));
                            hs.push(this.makeButton(h, '使用列表', 'default', 'small'));
                        } else if (params.row.serviceStatus === '已上架') {
                            hs.push(this.makeButton(h, '下架', 'error', 'small'));
                            hs.push(this.makeButton(h, '使用列表', 'default', 'small'));
                        } else if (params.row.serviceStatus === '拒绝') {
                            hs.push(this.makeButton(h, '修改', 'primary', 'small'));
                        }

                        return h('div', hs);
                    }
                }
            ],
            currentPage: 1,
            pageSize: 10,
            total: 0,
            versionStatus: [],
            updateTypes: []
        };
    },
    created() {
    },
    methods: {
        makeButton: function (h, text, type, size) {
            return h('Button', {
                props: {
                    type: type,
                    size: size
                },
                style: {
                    marginRight: '12px'
                },
                on: {
                    click: () => {
                        console.log('clicked!')
                    }
                }
            }, text);
        }
    }
};
</script>

<style>
    .card-custom {
        margin-bottom: 20px;
    }

    .tips {
        padding: 0 0 20px 0;
        color: gray;
    }

    .form {
        margin-bottom: 20px;
    }
</style>
