layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
function goback()
{
    history.back(-1);
}
function addStudentButton()
{
    var $=layui.jquery;
    var layer=layui.layer;
    var email=document.getElementById("email").value;
    var username=document.getElementById("username").value;
    var password=document.getElementById("password").value;
    var repassword=document.getElementById("repassword").value;
    $.ajax({
        url: 'addStudent',
        type: "post",
        data: {
            email:email,
            username:username,
            password:password,
            repassword:repassword
        },
        success: function (msg) {
            console.log(msg);
            switch (msg.code) {
                case 1:layer.msg("您输入的密码不一致!", {icon: 5});break;
                case 2:layer.msg("您输入的邮箱已被注册!", {icon: 5});break;
                case 3: {
                    layer.msg("注册成功！", {icon: 5});
                    var type=document.getElementById("type").value;
                    console.log(type);
                    if (type==="1")
                        window.location.href = "gotoHome";
                    else if (type==="2")
                        window.location.href = "AllUser";
                    break;
                }
                default:layer.msg("未知错误！请稍后重试！",{icon: 5});
            }
        }
    });

}