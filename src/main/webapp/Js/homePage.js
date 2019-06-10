var intro;
var h4;
var p;

$(document).ready(function(){
    intro = document.getElementsByClassName("introduce");
    h4 = document.getElementsByClassName("h4");
    p = document.getElementsByClassName("p");
    $("#0").button();
    $("#1").button();
    $("#2").button();
    // console.log(intro[0]);
    $(window).on("load",function(){
        var page="getCharacterization";
        $.ajax({
            url:page,
            type:"POST",
            dataType:"json",
            success:function (list) {
                for(var i = 0;i < 3;++i){
                    if(i < list.length){
                        add(i,list[i].name,list[i].characterization);
                        $("#"+i).attr("cid",list[i].id);
                    }
                    else{
                        add(i,"敬请期待","");
                        $("#"+i).hide();
                    }

                }
            }
        });


        // add(0,"蓝桥杯","为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过1200余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。");
        // add(1,"蓝桥杯","为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过1200余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。");
        // add(2,"蓝桥杯","为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过1200余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。");
    });
});

function add(i,name,str){
    h4[i].innerHTML = name;
    p[i].append(str);
};

$(function () {
    $(".disp").click(function () {
        var cid = $(this).attr("cid");
        if(cid.length>0)
        window.location.href = "editMyContest?cid="+cid+"&status=-1";
    });
    $("#myCompe").click(function () {
        window.location.href = "listMyContest?start=0";
    });

    $("#signUp").click(function () {
        window.location.href = "listContest?start=0";
    })

});


