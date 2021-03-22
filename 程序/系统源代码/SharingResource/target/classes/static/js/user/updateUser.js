layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
function getUserMessage(username,password,email,score) {
    layui.use('form',function () {
        var form=layui.form;
        document.getElementById("bname").value=username;
        document.getElementById("bpassword").value=password;
        document.getElementById("bemail").value=email;
        document.getElementById("bscore").value=score;

        document.getElementById("username").value=username;
        document.getElementById("password").value=password;
        document.getElementById("email").value=email;
        document.getElementById("score").value=score;
    });
}
function closeButton() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function resetUser() {
    var username=document.getElementById("bname").value;
    var password=document.getElementById("bpassword").value;
    var email=document.getElementById("bemail").value;
    var score=document.getElementById("bscore").value;

    document.getElementById("username").value=username;
    document.getElementById("password").value=password;
    document.getElementById("email").value=email;
    document.getElementById("score").value=score;
}
function updateUserButton()
{
    var $=layui.jquery;
    var layer=layui.layer;
    var index1 = parent.layer.getFrameIndex(window.name);
    var email=document.getElementById("email").value;
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    $.ajax({
        url: 'updateMyself',
        type: "post",
        data: {
            email:email,
            username:username,
            password:password
        },
        success: function (msg) {
            console.log(msg);
            switch (msg.code) {
                case 1:layer.msg("您输入的邮箱已被注册!", {icon: 5});break;
                case 2:layer.confirm('修改成功！点击确定返回登陆页面', {btn:'确定',closeBtn:0,move:false},function (index) {
                        parent.layer.close(index);
                        parent.layer.close(index1);
                        parent.window.location.href="gotoLogin";
                    });break;
                default:layer.msg("未知错误！请稍后重试！",{icon: 5});
            }
        }
    });

}