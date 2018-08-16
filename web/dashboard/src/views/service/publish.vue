<template>
    <div id="account-basic">
        <Row>
            <Col span="24">

                <Card>
                    <div class="form">
                        <Form :model="queryParams" :label-width="80">
                            <Row>
                                <Col span="24" style="text-align: right">
                                    <Button type="ghost" icon="plus" style="margin-right: 14px">创建服务</Button>
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
    data () {
        return {
            queryParams: {},
            tableList: [
                {
                    id: "abcbdbe91132b4ff5e0",
                    serviceName: "用户中心",
                    payType: "付费",
                    serviceStatus: "审核中",
                    createdTime: "2018-01-01 00:00:00",
                    serviceType: "用户管理"
                },
                {
                    id: "abc12dxaa32b4ff51sa",
                    serviceName: "消息服务",
                    payType: "免费",
                    serviceStatus: "未上架",
                    createdTime: "2018-01-01 00:00:00",
                    serviceType: "消息服务"
                },
                {
                    id: "wqac12dxxbtyx14ff5bs",
                    serviceName: "敏感词服务",
                    payType: "免费",
                    serviceStatus: "已上架",
                    createdTime: "2018-01-01 00:00:00",
                    serviceType: "基础服务"
                },
                {
                    id: "wqac12dxxbtyx14ff5bs",
                    serviceName: "IP寻址",
                    payType: "面议",
                    serviceStatus: "拒绝",
                    createdTime: "2018-01-01 00:00:00",
                    serviceType: "基础服务"
                }
            ],
            columns:[
                {
                    title: '服务编号',
                    key: 'id',
                    minWidth: 100,
                },
                {
                    title: '服务名称',
                    key: 'serviceName',
                },
                {
                    title: '分类',
                    minWidth: 140,
                    key: 'serviceType'
                },
                {
                    title: '付费类型',
                    minWidth: 140,
                    key: 'payType'
                },
                {
                    title: '创建时间',
                    minWidth: 140,
                    key: 'createdTime'
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
                            this.makeButton(h,'查看','primary','small')
                        ];

                        if(params.row.serviceStatus==='未上架'){
                            hs.push(this.makeButton(h,'上架','primary','small'));
                            hs.push(this.makeButton(h,'使用列表','default','small'));
                        }else if(params.row.serviceStatus==='已上架'){
                            hs.push(this.makeButton(h,'下架','error','small'));
                            hs.push(this.makeButton(h,'使用列表','default','small'));
                        }else if(params.row.serviceStatus==='拒绝'){
                            hs.push(this.makeButton(h,'修改','primary','small'));
                        }

                        return h('div', hs);
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
                        console.log("clicked!")
                    }
                }
            }, text);
        }
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
