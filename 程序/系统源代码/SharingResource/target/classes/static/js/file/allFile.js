layui.use(['laypage', 'layer', 'table', 'element'], function () {
    var laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , element = layui.element
        , $ = layui.jquery;
    //第一个实例
    var tableIns = table.render({
        elem: '#AllFileTable'//前台页面table的名字
        , height: 415
        , url: 'getAllFile' //数据接口
        , page: true//开启分页
        , toolbar: '#toolbarDemo' //头工具栏，前台定义的script语句
        , id: 'AllFileTable'
        , method: "get"
        , cols: [[ //表头  fiield要与实体类名一致
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width: 50, sort: true, fixed: 'left',align:'center'},
            {field: 'userid', title: '上传人ID', width: 70,align:'center'},
            {field: 'filename', title: '文件名', width: 150,align:'center'},
            {field: 'filetype', title: '文件类型', width: 70,align:'center'},
            {field: 'category', title: '所属科类ID', width: 80,align:'center'},
            {field: 'filelocation', title: '文件存储位置', width: 150 ,hide:true,align:'center'},
            {field: 'filescore', title: '文件评分', width: 86,align:'center'},
            {field: 'uploaddate', title: '上传日期', width: 140,hide:true,align:'center'},
            {field: 'changedate', title: '最后修改日期', width: 140,hide:true,align:'center'},
            {field: 'fileintroduce', title: '文件简介', width: 180,edit:'text',align:'center'},
            {field: 'filestate', title: '文件状态', width: 100,align:'center'},
            {fixed: 'right', width: 250, align: 'center', toolbar: '#barDemo'}//barDemo对应前台页面table下的script语句
        ]]
    });
    //监听单元格点击事件
    table.on('edit(test)', function(obj){
        var data = obj.data
            ,dataname=obj.field
            ,value=obj.value;
        $.ajax({
            url:'changeData',
            method:'post',
            data:{
                fileid:data.id,
                dataname:dataname,
                value:value
            },
            success:function (msg) {
                tableIns.reload();
            }
        })
    });
    //监听表头栏事件：新增、批量删除等
    table.on('toolbar(test)', function (obj) {//test为前台table的lay-filter对应的值
        var checkStatus = table.checkStatus(obj.config.id)
            , data = checkStatus.data;//获取选中的数据
        switch (obj.event) {
            case 'rejectfiles':
                if (data.length === 0) {layer.msg('请至少选择一行');}
                else {
                    var datas = "";
                    for (var i = 0; i < data.length; i++) {datas += data[i].id + ",";}
                    $.ajax({
                        url: 'rejectfiles',
                        type: "post",
                        data: {
                            datas: datas
                        },
                        success: function (msg) {
                            if (msg.code === 1) {
                                layer.msg("操作成功!", {icon: 6});
                                tableIns.reload();
                            } else {layer.msg("操作失败!未知错误", {icon: 5});}
                        }
                    });
                }
                break;
            case 'agreefiles':
                if (data.length === 0) {layer.msg('请至少选择一行');}
                else {
                    var datas = "";
                    for (var i = 0; i < data.length; i++) {datas += data[i].id + ",";}
                    $.ajax({
                        url: 'agreefiles',
                        type: "post",
                        data: {
                            datas: datas
                        },
                        success: function (msg) {
                            if (msg.code === 1) {
                                layer.msg("操作成功!", {icon: 6});
                                tableIns.reload();
                            } else {layer.msg("操作失败!未知错误", {icon: 5});}
                        }
                    });
                }
                break;
            case 'allpassingfile':
                tableIns.reload({
                    url:'getAllPassingFile',
                    page:{curr:1}//从第一页开始渲染
                });
                break;
            case 'allfile':
                tableIns.reload({
                url:'getAllFile',
                page:{curr:1}//从第一页开始渲染
            });break;
            case 'allpassedfile':
                tableIns.reload({
                    url:'getAllPassedFile',
                    page:{curr:1}//从第一页开始渲染
                });break;
            case 'allnotpassedfile':
                tableIns.reload({
                    url:'getAllNotPassedFile',
                    page:{curr:1}//从第一页开始渲染
                });break;
        }
    });

    //查询按钮事件
    $("#search").click(function () {//查询事件，在table中写的方法调用 tableIns.reload();
        var fileCategory = document.getElementById("fileCategory").value;
        var filename = document.getElementById("filename").value;
        var filetype = document.getElementById("filetype").value;
        var filestate =document.getElementById("filestate").value;
        table.reload('AllFileTable', {
            url: 'searchFile',
            page: {
                curr: 1
            }
            , where: {
                fileCategory: fileCategory,
                filename:filename,
                filetype:filetype,
                filestate:filestate
            }
        });

    });

    //监听单元格工具事件
    table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'view') {
            $.ajax({
                url: 'setSourcePath',
                type: "post",
                data: {
                    filename: data.filename,
                    filetype:data.filetype,
                    filepath:data.filelocation
                },
                success: function (msg) {
                    if (msg.code === 1) {//视频文件
                        parent.layer.open({
                            type: 2,//iframe框架
                            title: '播放视频',
                            area: ['300px','600px'],
                            content: ['gotoViewVideo','no'],
                            resize:false,
                            scrollbar:false,
                            move:false,
                            end: function () {tableIns.reload();}
                        });
                    } else if(msg.code === 2){
                        parent.layer.open({
                            type: 2,//iframe框架
                            title: '预览Office文件',
                            area: ['500px','600px'],
                            content: ['toPdfFile','no'],
                            resize:false,
                            scrollbar:true,
                            move:false,
                            end: function () {tableIns.reload();}
                        });
                    }
                    else if(msg.code===3)
                    {
                        parent.layer.open({
                            type: 2,//iframe框架
                            title: '预览pdf文件',
                            area: ['500px','600px'],
                            content: ['viewPdfFile','no'],
                            resize:false,
                            scrollbar:true,
                            move:false,
                            end: function () {tableIns.reload();}
                        });
                    }
                    else if (msg.code===4)
                    {
                        parent.layer.open({
                            type: 2,//iframe框架
                            title: '预览图片',
                            area: ['300px','600px'],
                            content: ['gotoViewImg','no'],
                            resize:false,
                            scrollbar:false,
                            move:false,
                            end: function () {tableIns.reload();}
                        });
                    }
                }
            });
        }
        else if(layEvent==='download')
        {
            $.ajax({
                url:"downloadFile",
                method:"post",
                data:{
                    filelocation:data.filelocation,
                    filename:data.filename+data.filetype,
                    fileid:data.id
                },
                success: function (msg) {
                    if(msg.code===1) {layer.msg("下载成功！文件保存路径为："+msg.location)}
                    else if (msg.code===2) {layer.msg("未知错误，下载失败！")}
                }
            })
        }
        else if (layEvent==='passapply')
        {
            $.ajax({
                url:"changeFileState",
                method:"post",
                data:{
                    fileid:data.id,
                    filestate:"已过审"
                },
                success:function (msg) {
                    tableIns.reload();
                }
            })
        }
        else if (layEvent==='rejectfile')
        {
            $.ajax({
                url:"changeFileState",
                method:"post",
                data:{
                    fileid:data.id,
                    filestate:"未过审"
                },
                success:function (msg) {
                    tableIns.reload();
                }
            })
        }
    });
});

