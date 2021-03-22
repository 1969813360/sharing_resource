layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
function getAuthorMessage(username,sort,email,score) {
    layui.use('form',function () {
        var form=layui.form;
        document.getElementById("username").value=username;
        document.getElementById("sort").value=sort;
        document.getElementById("email").value=email;
        document.getElementById("score").value=score;
    });
}