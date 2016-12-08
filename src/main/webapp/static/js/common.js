//加载top页面
function init() {
    loadMain("main");
}

function loadMain(path) {
    $("#main").load(path,function (data) {
        window.scrollTo(0,0);
    });
}
