layui.use(['upload','form',['table']], function() {
    var $ = layui.jquery;
    var upload = layui.upload;
    var table=layui.table;
    var form=layui.form;
    var index=parent.layer.getFrameIndex(window.name);
    upload.render({
        elem: '#selectFile'
        ,url: 'uploadFile' //改成您自己的上传接口
        ,auto: false
        //,multiple: true //开启多文件上传
        ,accept:'file'
        ,exts: 'txt|doc|docx|xls|xlsx|ppt|pptx|jpg|png|mp4|pdf|zip' //允许上传的文件类型
        ,bindAction: '#uploadFile'
        ,data:{
            categoryId:function(){return $('#fileCategory').val();},
            fileIntroduce:function(){return $('#fileIntroduce').val();}
        }
        ,done: function(res){
            layer.msg('上传成功');
            parent.layer.close(index);
        }
        ,error: function(index, upload){
           layer.msg('所选文件格式不匹配！支持上传的文件格式有：txt|doc|docx|xls|xlsx|ppt|pptx|jpg|png|mp4|pdf')
        }
    });
});