var form;


// $(document).ready(function(){
//     form = $("#form");
//     // var list =
//    //  $(window).on("load",function () {
//    //
//    // });
//     $("#addInput").click(function () {
//         addI();
//     });
//     $("#addRadio").click(function(){
//         addR();
//     });
//     $("#addoption").click(function () {
//        addO();
//     });
// });




function addI(st,id) {
    var td = $("#"+st);
    var input = $("<input>");
    var value = getValue(1,id);
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
    var val = getValue(1,id);
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
    var val = getValue(1,id);
    var option = $("<option></option>").text(val);
    select.append(option);
    $.each(list,function (i,value) {
        if(val!=value.value){
            option = $("<option></option>").text(value.value);
        }
        select.append(option);
    });
    td.append(select);
}

function getValue(uid,cid) {
    var val;
    $.ajax({
        url : "getValue",
        data : {"uid":uid,"cid":cid},
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
   $("input.cii").each(function () {
       $(this).keyup(function () {
           var value = $(this).val();
           var cid = $(this).attr("cid");
           var td = $(this).parent("td");
           $.ajax({
               url : "updateCv",
               data : {"uid":1,"cid":cid,"value":value},
               type : "POST",
               success : function (data) {
                   if(data=="success"){
                       td.css("border","1px solid green");
                   }else{
                       td.css("border","1px solid red");
                   }
               }
           })
       })
   });

   $("input.cir").each(function () {
       $(this).change(function () {
           var value = $(this).val();
           var cid = $(this).attr("cid");
           var td = $(this).parent("td");
           $.ajax({
               url : "updateCv",
               data : {"cid":cid,"uid":1,"value":value},
               type : "POST",
               success : function () {
                   if(data=="success"){
                       td.css("border","1px solid green");
                   }else{
                       td.css("border","1px solid red");
                   }
               }
           })
       })
   });

   $("select.cis").each(function () {
       $(this).change(function () {
           var value = $(this).val();
           var cid = $(this).attr("cid");
           var td = $(this).parent("td");
           $.ajax({
               url : "updateCv",
               data : {"cid" : cid,"uid" : 1,"value" : value},
               type : "POST",
               success : function () {
                   if(data=="success"){
                       td.css("border","1px solid green");
                   }else{
                       td.css("border","1px solid red");
                   }
               }
           })
       })
   })
});