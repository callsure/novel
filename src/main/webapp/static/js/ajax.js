/**
 * Created by runshu.lin on 16/12/16.
 */
function bookdown(url) {
    $.ajax({
        type: "GET",
        url : url,
        dataType:"json",
        success: function (data) {
            if (data.success){
                window.location = "../download?token=" + data.msg;
            }else{
                alert(data.msg);
            }
        }
    });
    
}
