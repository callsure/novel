//加载top页面
function init() {
    loadTop();
    loadFoot();
    window.scrollTo(0,0);
}

function loadTop() {
    $("#top").load("top");
}

function loadFoot() {
    $("#foot").load("foot");
}
