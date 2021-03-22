layui.use(['laypage', 'layer', 'table', 'element'], function () {
    var laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , element = layui.element
        , $ = layui.jquery;
    //第一个实例
    var tableIns = table.render({
        elem: '#ScoreRecordTable'//前台页面table的id
        , height: 415
        , url: 'getAllScoreRecord' //数据接口
        , page: true//开启分页
        , toolbar: '#toolbarDemo' //头工具栏，前台定义的script语句
        , id: 'ScoreRecordTable'
        , method: "get"
        , cols: [[ //表头  fiield要与实体类名一致
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left',align:'center'},
            {field: 'userid', title: '用户ID', width: 100,align:'center'},
            {field: 'fileid', title: '文件ID', width: 100,align:'center'},
            {field: 'score', title: '评分', width: 100,align:'center'},
            {field: 'date', title: '日期', width: 150,align:'center'}
        ]]
    });
    //监听表头栏事件：新增、批量删除等
    table.on('toolbar(test)', function (obj) {//test为前台table的lay-filter对应的值
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data;//获取选中的数据
        switch (obj.event) {
            case 'all':tableIns.reload({
                url:'getAllScoreRecord',
                page:{curr:1}//从第一页开始渲染
            });break;
        }
    });

    //查询按钮事件
    $("#search").click(function () {//查询事件，在table中写的方法调用 tableIns.reload();
        var userid = document.getElementById("userid").value;
        var fileid = document.getElementById("fileid").value;
        table.reload('ScoreRecordTable', {
            url: 'searchScoreRecord',
            page: {
                curr: 1
            }
            , where: {fileid: fileid,
                      userid:userid}
        });
    });
});

