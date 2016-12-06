//加载top页面
function init() {
    loadMain();
    window.scrollTo(0,0);
}

function loadMain() {
    $("#main").load("main");
}
