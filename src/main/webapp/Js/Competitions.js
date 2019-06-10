
var content;
$(document).ready(function(){
    $(window).on("load",function(){
        content = $("#content");
        // add("第十届蓝桥杯省赛","applyCompetition.html","报名中","为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过1200余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。");
    });

    $(".a1").click(function () {
        var value = $(this).attr("value");
        if(value=="报名截止"){
            alert("报名已截止，无法报名");
            return false;
        }else if (value=="暂未开放") {
            alert("比赛暂未开放，请稍后再试");
            return false;
        }else if(value=="已报名"){
            var url = $(this).attr("href");
            url = url.substring(0,url.length-1) + "1";
           $(this).attr("href",url);
            return true;
        }else{
            return true;
        }
    })
});


function add(name,href,zhuangtai,introduce){
    var a = $("<a style='cursor: pointer;'></a>");
    a.attr("href",href);                 //给a  标签添加 地址
    var div = $("<div></div>");
    div.addClass("item");
    var p1 = $("<p class='name'></p>");
    var span1 = $("<span></span>").text(zhuangtai);
    p1.append(name,span1);                             //添加大赛名字 和状态
    var p2 = $("<p class='intro'></p>");            //大赛介绍
    var span2 = $("<span>&emsp;&emsp;</span>");
    p2.append(span2,introduce);

    div.append(p1,p2);
    a.append(div);
    content.append(a);
}