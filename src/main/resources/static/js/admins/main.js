/**
 * Bolg main JS.
 * Created by waylau.com on 2017/3/9.
 */
// DOM 加载完再执行
$(function() {
	//菜单事件

	$(".blog-menu .list-group-item").click(function(){
		var url=$(this).attr("url");
	    window.location.href="http://localhost:8081"+url;
	});
	//选中菜单第一项
	//$(".blog-menu .list-group-item:first").trigger("click");
})