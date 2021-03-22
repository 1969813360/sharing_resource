layui.use(['laypage', 'layer', 'table', 'element'], function () {
    var laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , element = layui.element
        , $ = layui.jquery;
    //第一个实例
    var tableIns = table.render({
        elem: '#FileCategoryTable'//前台页面table的id
        , height: 415
        , url: 'getAllFileCategory' //数据接口
        , page: true//开启分页
        , toolbar: '#toolbarDemo' //头工具栏，前台定义的script语句
        , id: 'FileCategoryTable'
        , method: "get"
        , cols: [[ //表头  fiield要与实体类名一致
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width: 80, sort: true, fixed: 'left',align:'center'},
            {field: 'name', title: '科目名', width: 100,align:'center',edit:true},
            {fixed: 'right', width: 150, align: 'center', toolbar: '#barDemo'}//barDemo对应前台页面table下的script语句
        ]]
    });
    //监听单元格点击事件
    table.on('edit(test)', function(obj){
        var data = obj.data
            ,dataname=obj.field
            ,value=obj.value;
        $.ajax({
            url:'updateFileCategory',
            method:'post',
            data:{
                fileCategoryId:data.id,
                value:value
            },
            success:function (msg) {
                if (msg.code===1){tableIns.reload();}
                else if (msg.code===2){
                    parent.layer.open({
                        type: 1,//iframe框架
                        shadeClose: true,
                        title: '警告',
                        area: ['300px', '80px'],
                        content:'您输入的科目名已存在！',
                        end: function () {tableIns.reload();}
                    })
                }
            }
        })
    });
    //监听表头栏事件：新增、批量删除等
    table.on('toolbar(test)', function (obj) {//test为前台table的lay-filter对应的值
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data;//获取选中的数据
        switch (obj.event) {
            case 'add':window.location.href="gotoAddFileCategory";break;
            case 'del':
                if (data.length === 0) {layer.msg('请至少选择一行');}
                else {
                    var datas = "";
                    for (var i = 0; i < data.length; i++) {datas += data[i].id + ",";}
                    $.ajax({
                        url: 'delFileCategorys',
                        type: "post",
                        data: {
                            datas: datas
                        },
                        success: function (msg) {
                            if (msg.code === 1) {
                                layer.msg("删除成功!", {icon: 6});
                                tableIns.reload();
                            } else {layer.msg("删除失败!未知错误", {icon: 5});}
                        }
                    });
                }
                break;
            case 'all':tableIns.reload({
                url:'getAllFileCategory',
                page:{curr:1}//从第一页开始渲染
            });break;
        }
    });

    //查询按钮事件
    $("#search").click(function () {//查询事件，在table中写的方法调用 tableIns.reload();
        var name = document.getElementById("name").value;
        table.reload('FileCategoryTable', {
            url: 'searchFileCategory',
            page: {
                curr: 1
            }
            , where: {name: name}
        });
    });

    //监听单元格工具事件
    table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'del') {
            layer.confirm('确定删除？', function (index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);//关闭当前弹窗，即：“确定删除”
                //向服务端发送删除指令
                $.ajax({
                    url: 'delFileCategory',
                    type: "post",
                    data: {
                        id: data.id
                    },
                    success: function (msg) {
                        if (msg.code === 1) {
                            console.log(msg);
                            //关闭弹窗
                            layer.close(index);
                            layer.msg("删除成功!", {icon: 6});
                            tableIns.reload();
                        } else if(msg.code === 2){
                            layer.msg("未知错误，删除失败!", {icon: 5});
                        }
                    }
                });
            });
        }
    });
});

