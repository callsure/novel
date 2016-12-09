$(function () {
    init();
});

//初始化页面
function init() {
    // loadMain("main");
}

function loadMain(path) {
    $("#main").load(path,function (data) {
        window.scrollTo(0,0);
    });
}

function search(path) {
    var query = $("#search").val();
    query = encodeURIComponent(query);
    window.location.href = path + query;
}
