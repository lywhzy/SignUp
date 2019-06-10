var form;


$(document).ready(function(){
    var status = $("#container").attr("status");
    if(status==0){
        $("#ex").text("提交");
    }else{
        $("#ex").text("取消");
    }
});




function addI(st,id) {
    var td = $("#"+st);
    var status = $("#container").attr("status");
    var input = $("<input>");
    var value = getValue(id,status);
    input.css({
        "type" : "text",
        "width" : "300px",
        "margin" : "1px auto"
    });
    input.attr("class","cii");
    input.attr("cid",id);
    input.val(value);
    td.append(input);

}
function addR(st,id){
    var td = $("#"+st);
    var p=document.createElement("p");
    var val = getValue(id);
    var list = getAlternative(id);
    $.each(list,function (i,value) {
        if(i==0) p.innerHTML='<label>' + value.value + '</label>';
        else p.innerHTML = p.innerHTML + '<label>' + value.value + '</label>';
        if(val==value.value){
            p.innerHTML = p.innerHTML + '<input type="radio" name="exception"' + id + ' checked="checked" cid=' + id + ' value=' + value.value + ' class="cir">';
        } else{
            p.innerHTML = p.innerHTML + '<input type="radio" name="exception"' + id + ' cid=' + id + ' value=' + value.value + ' class="cir">';
        }
    });
    td.append(p);
}
function addO(st,id){
    var td = $("#"+st);
    var status = $("#container").attr("status");
    var select = $("<select></select>");
    var span = $("<span></span>");
    select.css({
        "width" : "300px",
        "margin" : "1px auto"
    });
    select.addClass("form-control");
    select.attr("class","cis");
    select.attr("cid",id);
    var list = getAlternative(id);
    var val = getValue(id,status);
    var option = $("<option></option>").text(val);
    select.append(option);
    $.each(list,function (i,value) {
        if(val!=value.value){
            option = $("<option></option>").text(value.value);
        }
        select.append(option);
    });
    option = $("<option></option>").text("自定义");
    select.append(option);
    td.append(select);
}

function getValue(cid,status) {
    var val;
    $.ajax({
        url : "getValue",
        data : {"cid":cid,"status":status},
        type : "POST",
        async : false,
        success : function (value) {
            val = value;
        }
    });
    return val;
}



function getAlternative(cid) {
    var list = new Array();
    $.ajax({
        url : "getAlternative",
        data: {"cid" : cid},
        dataType:"json",
        type : "POST",
        async: false,
        success : function (value) {
            for(var i = 0;i < value.length;++i){
                list.push(value[i]);
            }
        }
    });
    return list;
}

$(function () {
   // $("input.cii").each(function () {
   //     $(this).keyup(function () {
   //         var value = $(this).val();
   //         var cid = $(this).attr("cid");
   //         var td = $(this).parent("td");
   //         $.ajax({
   //             url : "updateCv",
   //             data : {"uid":1,"cid":cid,"value":value},
   //             type : "POST",
   //             success : function (data) {
   //                 if(data=="success"){
   //                     td.css("border","1px solid green");
   //                 }else{
   //                     td.css("border","1px solid red");
   //                 }
   //             }
   //         })
   //     })
   // });
   //
   // $("input.cir").each(function () {
   //     $(this).change(function () {
   //         var value = $(this).val();
   //         var cid = $(this).attr("cid");
   //         var td = $(this).parent("td");
   //         $.ajax({
   //             url : "updateCv",
   //             data : {"cid":cid,"uid":1,"value":value},
   //             type : "POST",
   //             success : function () {
   //                 if(data=="success"){
   //                     td.css("border","1px solid green");
   //                 }else{
   //                     td.css("border","1px solid red");
   //                 }
   //             }
   //         })
   //     })
   // });
   //


   $("select.cis").each(function () {
       $(this).change(function () {
           var value = $(this).val();

           if(value=="自定义"){
               var td = $(this).parent("td");
               var input = $("<input>");
               input.css({
                   "type" : "text",
                   "width" : "300px",
                   "margin" : "1px auto"
               });
               td.append(input);
           }else{
               var td = $(this).parent("td")[0];
               var input = $(this).parent("td").children().eq(2)[0];
               if(input!=null)
                td.removeChild(input);
           }
       })
   });

    $("#update").click(function () {
        var status = $("#container").attr("status");
        if(status==1){
            $(".cii").each(function () {
                // console.log($(this).attr("cid"));
                // console.log($(this).val());
                var cid = $(this).attr("cid");
                var value = $(this).val();
                $.ajax({
                    url : "updateCv",
                    data : {"cid" : cid,"value" : value,"custom" : 0},
                    type : "POST",
                    success : function (data) {

                    }
                });
            });
            $(".cis").each(function () {
                var cid = $(this).attr("cid");
                var value = $(this).val();
                var input = $(this).parent("td").find("input");
                var custom = 0;
                if(value=="自定义"){
                    value = input.val();
                    custom = 1;
                }
                $.ajax({
                    url : "updateCv",
                    data : {"cid" : cid,"value" : value,"custom" : custom},
                    type : "POST",
                    success : function (data) {

                    }
                });
            });
            alert("修改成功");
        }else{
            $(".cii").each(function () {
                // console.log($(this).attr("cid"));
                // console.log($(this).val());
                var cid = $(this).attr("cid");
                var value = $(this).val();
                $.ajax({
                    url : "keep",
                    data : {"cid" : cid,"value" : value,"custom" : 0},
                    type : "POST",
                    success : function (data) {

                    }
                });
            });
            $(".cis").each(function () {
                var cid = $(this).attr("cid");
                var value = $(this).val();
                var input = $(this).parent("td").find("input");
                var custom = 0;
                if(value=="自定义"){
                    value = input.val();
                    custom = 1;
                }
                $.ajax({
                    url : "keep",
                    data : {"cid" : cid,"value" : value,"custom" : custom},
                    type : "POST",
                    success : function (data) {

                    }
                });
            });
            alert("保存成功");
        }

    });



    $("#ex").click(function () {
        var status = $("#container").attr("status");
        var contest = $("#container").attr("contest");
        if(status==1){
            window.location.replace("listMyContest?start=0");
        }else{
            $(".cii").each(function () {
                // console.log($(this).attr("cid"));
                // console.log($(this).val());
                var cid = $(this).attr("cid");
                var value = $(this).val();
                $.ajax({
                    url : "signup",
                    data : {"cid" : cid,"value" : value,"custom" : 0,"contest" : contest},
                    type : "POST",
                    success : function (data) {

                    }
                });
            });
            $(".cis").each(function () {
                var cid = $(this).attr("cid");
                var value = $(this).val();
                var input = $(this).parent("td").find("input");
                var custom = 0;
                if(value=="自定义"){
                    value = input.val();
                    custom = 1;
                }
                $.ajax({
                    url : "signup",
                    data : {"cid" : cid,"value" : value,"custom" : custom,"contest" : contest},
                    type : "POST",
                    success : function (data) {

                    }
                });
            });
            window.location.replace("listMyContest?start=0");
        }
    })

});