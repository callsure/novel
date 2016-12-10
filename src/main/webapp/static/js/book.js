/**
 * Created by runshu.lin on 16/12/10.
 */
$(function () {
    loadMyBook();
});
/**
 * 加载自己的书籍
 */
function loadMyBook() {
    var novels = storeWithExpiration.getAll();
    var content = $(".content").empty();
    if ($.isEmptyObject(novels)){
        $("#page").removeClass('hide');
    }else{
        var html = '';
        for (var i in novels) {
            if (novels[i]['type'] == 1){
                //一本书
                html += "<li><div class='bcp'><a href='javascript:deletebook("+novels[i]['id']+");'>[删除]</a></div>";
                html += "<div class='bcont'><a href='"+novels[i]['url']+"' class='title'>"+novels[i]['name']+"</a>";
                html += "<a href='"+novels[i]['chapterUrl']+"' class='lastread'>最后阅读: "+novels[i]['chapter']+"</a>";
                html += "</div></li>";
            }
        }
        content.html(html);
    }
}

//自动记录最后阅读章节
function autoWriteRead(bookId, bookName, url, chapter, chapterUrl) {
    //bookReadLast(bookId, bookName, url, chapter, chapterUrl);
}