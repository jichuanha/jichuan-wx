//分页省略事件

function pageList(visiblePages,curPage) {
    var pageNum,pageCount,pageSize;
    pageCount = $('#pageCount').val();
    pageSize = $('#page_size').val();
    pageNum = Math.ceil(pageCount/pageSize);
    var li = '';
    li += '<li class="disabled prevli"><a href="javascript:"></a></li>';
    if(pageNum < 1){
        pageNum = 1;
    }
    if(pageNum <= visiblePages){
        for(var i = 1;i<= pageNum;i++){
            if(curPage == i) {
                li += '<li class="page-lis active"><a href="javascript:">'+i+'</a></li>';
            } else {
                li += '<li class="page-lis"><a href="javascript:">'+i+'</a></li>';
            }
        }
    }
    else{
        if(curPage < visiblePages -1){
            for(var i=1; i<=visiblePages-1; i++) {
                if(curPage == i) {
                    li += '<li class="page-lis active"><a href="javascript:">'+i+'</a></li>';
                } else {
                    li += '<li class="page-lis"><a href="javascript:">'+i+'</a></li>';
                }
            }
            li += '<li class="page-lis disabled"><a href="javascript:">...</a></li>';
            li += '<li class="page-lis"><a href="javascript:">'+pageNum+'</a></li>';
        }else if(curPage >= visiblePages -1){
            if(pageNum-curPage <= visiblePages-4) { // 能连到结束
                li += '<li class="page-lis"><a href="javascript:">1</a></li>';
                li += '<li class="page-lis disabled"><a href="javascript:">...</a></li>';
                for(var i=pageNum-(visiblePages-2); i<=pageNum; i++) {
                    if(curPage == i) {
                        li += '<li class="page-lis active"><a href="javascript:">'+i+'</a></li>';
                    } else {
                        li += '<li class="page-lis"><a href="javascript:">'+i+'</a></li>';
                    }
                }
            }else { // 不能连到结束
                li += '<li class="page-lis"><a href="javascript:">1</a></li>';
                li += '<li class="page-lis disabled"><a href="javascript:">...</a></li>';
                for(var i= curPage-(visiblePages-4); i<= parseInt(curPage)+1; i++) {
                    if(curPage == i) {
                        li += '<li class="page-lis active"><a href="javascript:">'+i+'</a></li>';
                    } else {
                        li += '<li class="page-lis"><a href="javascript:">'+i+'</a></li>';
                    }
                }
                li += '<li class="page-lis disabled"><a href="javascript:">...</a></li>';
                li += '<li class="page-lis"><a href="javascript:">'+pageNum+'</a></li>';
            }
        }
    }
    li += '<li class="nextli"><a href="javascript:" class="next"></a></li>' ;
    li += '<li class="disabled controls"><a href="javascript:"><input type="text" value="" class="curr-page" onkeyup=\"this.value=this.value.replace(/\\D/g,\'\');if(this.value>'+pageNum+'){this.value = '+pageNum+'; }\" onclick="this.select()">页 <span class="btn-link">跳转</span></li>';
    $('.pagination ul').html(li);
    if(pageNum <= 1){
        $('.nextli').addClass('disabled');
    }
}

//获取接口数据 如果是字符串转json
function strToJson(msg) {
    if(typeof msg == 'string'){
        var json = JSON.parse(msg);
        return json;
    }
    return msg;
}

function addDisable() {
    pageCount  = $('#pageCount').val();
    pageNum = $('#page_size').val();
    if(parseInt(pageCount) <= parseInt(pageNum)){
        $('.nextli').addClass('disabled');
    }
}
//输入框限制
function keyUpFuc(num) {

}