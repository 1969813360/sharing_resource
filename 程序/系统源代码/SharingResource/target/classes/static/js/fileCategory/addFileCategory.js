layui.use('form', function(){
    var form = layui.form;
    //监听提交
    form.on('submit(formDemo)', function(data){
        layer.msg(JSON.stringify(data.field));
        return false;
    });
});
function gotoAllFileCategory()
{
    window.location.href="AllFileCategory";
}
function addFileCategoryButton()
{
    var $=layui.jquery;
    var layer=layui.layer;
    var name=document.getElementById("name").value;
    $.ajax({
        url: 'addFileCategory',
        type: "post",
        data: {
            name:name
        },
        success: function (msg) {
            console.log(msg);
            switch (msg.code) {
                case 1:layer.msg("您输入的科目名已存在!", {icon: 5});break;
                case 2: {
                    layer.msg("注册成功！", {icon: 5});
                    window.location.href = "AllFileCategory";
                    break;
                }
                default:layer.msg("未知错误！请稍后重试！",{icon: 5});
            }
        }
    });

}