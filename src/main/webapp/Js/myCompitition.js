// <table class="table table-hover table-bordered">
//     <thead>
//     <th>比赛名称</th>
//     <th>比赛状态</th>
//     <th>比赛附件</th>
//     </thead>
//     <tbody id="tbody">
//     <tr>
//     <td><img width="20px" src="http://how2j.cn/study/bootstrap/lol/gareen.png"/></td>
//     <td>盖伦</td>
//     <td>616</td>
//     </tr>
//
//     </tbody>
 //   </table>
// <span class="glyphicon glyphicon-download-alt"></span>   下载图标

var con;
var table;
var thead;
var tbody;

$(document).ready(function () {
   $(window).on("load",function () {
       con = $("#content");
       judgeIsEmptyCompetiton(1);                   //初始设置有比赛
       $("#exit").click(function () {
           if (confirm("您确定要退出？")){
               window.location.replace("homePage.html");
               //跳转到登录界面
           }
       })
   }) ;
});

function judgeIsEmptyCompetiton(flag){            //判断是否有比赛
    if (flag == 1){                            //有比赛
        table = $("<table></table>");
        table.addClass("table table-hover table-bordered");
        thead = $("<thead></thead>");
        var tr = $("<tr></tr>");
        var td1 = $("<td></td>").text("比赛名称");
        var td2 = $("<td></td>").text("比赛状态");
        var td3 = $("<td></td>").text("附件");
        tr.append(td1,td2,td3);
        thead.append(tr);
        tbody = $("<tbody></tbody>");
        table.append(thead,tbody);
        con.append(table);
        addCompetition("蓝桥杯","报名中","#","附件名称");                        //此处举例添加一条比赛信息
    //    之后比赛同上面一样添加，参数：比赛名称，比赛状态，附件的下载地址，附件名称
    }
    else{
        var h4 = $("#h4");
        h4.text("暂无比赛");
    }

}

function addCompetition(name,state,aHref,aName){
    var tr = $("<tr></tr>");
    var td1 = $("<td></td>");
    var a1 = $("<a></a>");
    var span = $("<span></span>");
    span.addClass("glyphicon glyphicon-edit");
    a1.append(span);
    td1.append(name,"  ",a1);
    var td2 = $("<td></td>").text(state);
    var td3 = $("<td></td>");
    var a2 = $("<a></a>");
    a2.css("href",aHref);
    span = $("<span></span>");
    span.addClass("glyphicon glyphicon-download-alt");
    a2.append(span,aName);
    td3.append(a2);
    tr.append(td1,td2,td3);
    tbody.append(tr);

}