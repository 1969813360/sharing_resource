layui.use(['rate'], function() {
    var rate = layui.rate;
    rate.render({
        elem: '#x1'
        ,value: 2.5
        ,half: true
        ,theme: '#FF8000' //自定义主题色
        ,choose: function(value){
            document.getElementById("h1").value=value;
        }
    });
    rate.render({
        elem: '#x2'
        ,value: 2.5
        ,half: true
        ,theme: '#009688'
        ,choose: function(value){
            document.getElementById("h2").value=value;
        }
    });

    rate.render({
        elem: '#x3'
        ,value: 2.5
        ,half: true
        ,theme: '#1E9FFF'
        ,choose: function(value){
            document.getElementById("h3").value=value;
        }
    });
    rate.render({
        elem: '#x4'
        ,value: 2.5
        ,half: true
        ,theme: '#2F4056'
        ,choose: function(value){
            document.getElementById("h4").value=value;
        }
    });
});
function getFileId(fileid) {
    layui.use('form',function () {
        var form=layui.form;
        document.getElementById("fileid").value=fileid;
    });
}
function submitComment()
{
    var $=layui.jquery;
    var index = parent.layer.getFrameIndex(window.name);
    $.ajax({
        url:"commentFile",
        method:"post",
        data:{
            x1:function(){return $('#h1').val();},
            x2:function(){return $('#h2').val();},
            x3:function(){return $('#h3').val();},
            x4:function(){return $('#h4').val();},
            fileid:function(){return $('#fileid').val();}
        },
        success:function (msg) {
            if (msg.code===1){
                layer.msg("评分成功！感谢您的参与！");
                parent.layer.close(index)}
            else layer.msg("评分失败！请稍后再试");
        }
    });
}