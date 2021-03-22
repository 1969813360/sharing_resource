layui.use(['laypage', 'layer', 'table', 'element'], function () {
    var laypage = layui.laypage //分页
        , layer = layui.layer //弹层
        , table = layui.table //表格
        , element = layui.element
        , $ = layui.jquery;
    //第一个实例
    var tableIns = table.render({
        elem: '#FileTable'//前台页面table的名字
        , height: 500
        , url: 'getAllPassedFile' //数据接口
        , page: true//开启分页
        , toolbar: '#toolbarDemo' //头工具栏，前台定义的script语句
        , id: 'FileTable'
        , method: "get"
        , cols: [[ //表头  fiield要与实体类名一致
            {field: 'id', title: 'ID', width: 50, sort: true, fixed: 'left',align:'center'},
            {field: 'userid', title: '上传人ID', width: 70,align:'center'},
            {field: 'filename', title: '文件名', width: 150,align:'center'},
            {field: 'filetype', title: '文件类型', width: 70,align:'center'},
            {field: 'category', title: '所属科类ID', width: 80,align:'center'},
            {field: 'filelocation', title: '文件存储位置', width: 150 ,hide: true,align:'center'},
            {field: 'filescore', title: '文件评分', width: 86,align:'center'},
            {field: 'uploaddate', title: '上传日期', width: 140,align:'center'},
            {field: 'changedate', title: '最后修改日期', width: 140,align:'center'},
            {field: 'fileintroduce', title: '文件简介', width: 180,edit:'text',align:'center'},
            {field: 'filestate', title: '文件状态', width: 100,align:'center'},
            {fixed: 'right', width: 285, align: 'center', toolbar: '#barDemo'}//barDemo对应前台页面table下的script语句
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
                if (msg.code===1){tableIns.reload();}
                else if (msg.code===2){
                    parent.layer.open({
                        type: 1,//iframe框架
                        shadeClose: true,
                        title: '警告',
                        area: ['300px', '80px'],
                        content:'您不是该文件的作者，禁止修改！',
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
            case 'add':
                parent.layer.open({
                    type: 2,//iframe框架
                    shadeClose: true,
                    title: '资源上传',
                    area: ['30%', '300px'],
                    content: ['gotoAddFile', 'no'],//跳到后台
                    success:function(layer,index){},
                    end: function () {tableIns.reload();}
                });break;
            case 'myfile':
                tableIns.reload({
                    url:'showMyFile',
                    page:{curr:1}//从第一页开始渲染
                });
                break;
            case 'allfile':tableIns.reload({
                url:'getAllPassedFile',
                page:{curr:1}//从第一页开始渲染
            });break;
        }
    });

    //查询按钮事件
    $("#search").click(function () {//查询事件，在table中写的方法调用 tableIns.reload();
        var fileCategory = document.getElementById("fileCategory").value;
        var filename = document.getElementById("filename").value;
        var filetype = document.getElementById("filetype").value;
        table.reload('FileTable', {
            url: 'searchFile',
            page: {
                curr: 1
            }
            , where: {
                fileCategory: fileCategory,
                filename:filename,
                filetype:filetype
            }
        });

    });

    //监听单元格工具事件
    table.on('tool(test)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值
        if (layEvent === 'author') {
            $.ajax({
                url:"getAuthorMsg",
                type:"post",
                data:{id:data.userid},
                success:function (msg) {
                    parent.layer.open({
                        type: 2,//iframe框架
                        shadeClose: true,
                        title: '作者信息',
                        area: ['30%', '270px'],
                        content: ['gotoAuthorMsg', 'no'],
                        success: function (layer, index) {
                            // 获取子页面的iframe
                            var iframe = parent.window['layui-layer-iframe' + index];
                            iframe.getAuthorMessage(msg.username, msg.sort, msg.email, msg.score);
                        },
                        end: function () {
                            table.reload("FileTable");
                        }
                    })
                }
            })
        }
        else if (layEvent === 'del') {
            layer.confirm('确定删除？', function (index) {
                // obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);//关闭当前弹窗，即：“确定删除”
                //向服务端发送删除指令
                $.ajax({
                    url: 'delFile',
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
                            layer.msg("这不是您上传的文件，删除失败!", {icon: 5});
                        }
                    }
                });
            });
        } else if (layEvent === 'view') {
            $.ajax({
                url: 'setSourcePath',
                type: "post",
                data: {
                    filename: data.filename,
                    filetype:data.filetype,
                    filepath:data.filelocation
                },
                success: function (msg) {
                    if (msg.code === 0) {//压缩文件
                        layer.msg("压缩文件，请下载查看！", {icon: 5})
                    }
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
                    else if (msg.code===2) {layer.msg("未知错误，下载失败！", {icon: 5})}
                }
            })
        }
        else if (layEvent==='comment')
        {
            $.ajax({
                url:"judgeCommentFile",
                method:"post",
                data:{
                    fileid:data.id
                },
                success:function (msg) {
                    if (msg.code===1)
                    {
                        parent.layer.open({
                            type: 2,
                            shadeClose: false,
                            title: '资源评分',
                            area: ['30%', '400px'],
                            content: ['gotoCommentFile', 'no'],//跳到后台
                            success: function (layer, index) {
                                // 获取子页面的iframe
                                var iframe = parent.window['layui-layer-iframe' + index];
                                iframe.getFileId(msg.fileid);
                            },
                            end: function () {
                                tableIns.reload();
                            }
                        });
                    }
                    else if (msg.code===2){layer.msg("您并未下载过该文件！请下载后再参与评分！", {icon: 5})}
                    else if (msg.code===3) {layer.msg("不能对自己上传的文件评分！", {icon: 5})}
                }
            })
        }
    });
});
function MyHouse()
{
    layui.use(['layer','table','form'],function () {
    var layer=layui.layer;
    var table=layui.table;
    var form=layui.form;
    var $=layui.jquery;
    $.ajax({
        url:"getUserMsg",
        type:"post",
        success:function (msg) {
            parent.layer.open({
                type: 2,//iframe框架
                shadeClose: false,//点击遮幕关闭弹窗？
                title: '个人中心',
                area: ['30%', '450px'],
                closeBtn:0,
                content: ['gotoMyHouse', 'no'],
                success: function (layer, index) {
                    // 获取子页面的iframe
                    var iframe = parent.window['layui-layer-iframe' + index];
                    iframe.getUserMessage(msg.username,msg.password,msg.email,msg.score);
                },
                end: function () {
                    table.reload("FileTable");
                }
            });
        }
    })
    })
}