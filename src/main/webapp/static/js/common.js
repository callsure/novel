$(function () {
    init();
});

//初始化页面
function init() {
    //初始化store.js
    if (!store.enabled) {
        console.info('Local storage is not supported by your browser. Please disable "Private Mode", or upgrade to a modern browser.');
        return;
    }
    autoLoadLastRead();
}

function autoLoadLastRead() {
    //读取最后阅读章节
    var last = $(".last a").empty();
    var datas = storeWithExpiration.getAll();
    if (!$.isEmptyObject(datas)){
        for (var i in datas){
            if(datas[i].lastTime!=""){
                var novelName = datas[0].name;
                var chapter = datas[0].chapter;
                var chapterUrl = datas[0].chapterUrl;
                last.html("<a href='" + chapterUrl + "'>最后阅读:[" + novelName + "]" + chapter + "</a>")
                return;
            }
        }
    }else{
        $(".last").empty()
    }
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

//扩展store.js
var storeWithExpiration = {
    set: function(key, name, url, chapter, chapterUrl, lastTime, type) {
        store.set(key, { id:key, name:name, url:url, chapter:chapter, chapterUrl:chapterUrl, lastTime:lastTime, type:type });
    },
    get: function(key) {
        var info = store.get(key);
        if (typeof info === "undefined") { return {} }
        return info;
    },
    remove: function (key) {
        store.remove(key)
    },
    getAll: function() {
        var ret = [];
        store.forEach(function(key, val) {
            if (typeof val['url'] == "undefined") {
                val['url'] = "javascript:void(0);";
            }
            if (typeof val['chapter'] == "undefined") {
                val['chapter'] = "这本书你还没翻开过呢!~~~";
            }
            if (typeof val['chapterUrl'] == "undefined") {
                val['chapterUrl'] = "javascript:void(0);";
            }
            if (typeof val['lastTime'] == "undefined") {
                val['lastTime'] = "";
            }
            if (typeof val['type'] == "undefined") {
                val['type'] = "";
            }
            ret.push(val);
        });

        var soreTime =  function (a, b){
            if(a.lastTime > b.lastTime){
                return -1;
            } else if(a.lastTime < b.lastTime){
                return 1;
            }else {
                return 0;
            }
        };

        return ret.sort(soreTime);
    }
};

//存放书籍
function bookShelf(bookId, bookName, url){
    var book = storeWithExpiration.get(bookId);
    //类型type为1是加入书架的书籍
    storeWithExpiration.set(bookId, bookName, url, book.chapter, book.chapterUrl, book.lastTime, 1);
    alert("加入成功!");
}

//记录最后的阅读章节
function bookReadLast(bookId, bookName, url, chapter, chapterUrl) {
    var book = storeWithExpiration.get(bookId);
    storeWithExpiration.set(bookId, bookName, url, chapter, chapterUrl, new Date(), book.type);
}

//删除书籍
function deletebook(bookId) {
    storeWithExpiration.remove(bookId);
    loadMyBook();
}
