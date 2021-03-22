layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
function closeBtn() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}
function changePasswordButton()
{
    var $=layui.jquery;
    var layer=layui.layer;
    var index1 = parent.layer.getFrameIndex(window.name);
    var oddpassword=document.getElementById("oddpassword").value;
    var newpassword=document.getElementById("newpassword").value;
    var repassword=document.getElementById("repassword").value;
    $.ajax({
        url: 'updatePassword',
        type: "post",
        data: {
            oddpassword:oddpassword,
            newpassword:newpassword,
            repassword:repassword
        },
        success: function (msg) {
            console.log(msg);
            switch (msg.code) {
                case 1:layer.msg("您输入的原密码错误!", {icon: 5});break;
                case 2:layer.msg("您输入新密码与确认密码不一致!", {icon: 5});break;
                case 3:layer.confirm('修改成功！点击确定返回登陆页面', {btn:'确定',closeBtn:0,move:false},function (index) {
                    parent.layer.close(index);
                    parent.layer.close(index1);
                    parent.window.location.href="gotoLogin";
                });break;
                default:layer.msg("未知错误！请稍后重试！",{icon: 5});
            }
        }
    });

}