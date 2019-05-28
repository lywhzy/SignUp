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
        "cid" : id,
        "type" : "text",
        "width" : "300px",
        "margin" : "1px auto"
    });
    input.attr("class","ci");
    input.addClass("form-control");
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
            p.innerHTML = p.innerHTML + '<input type="radio" name="exception"' + id + ' checked="checked" cid=' + id + ' value=' + value.value + ' class="ci">';
        } else{
            p.innerHTML = p.innerHTML + '<input type="radio" name="exception"' + id + ' cid=' + id + ' value=' + value.value + ' class="ci">';
        }
    });
    td.append(p);
}
function addO(st,id){
    var td = $("#"+st);
    var select = $("<select></select>");
    select.css({
        "cid" : id,
        "width" : "300px",
        "margin" : "1px auto"
    });
    select.addClass("form-control");
    select.attr("class","ci");
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
   var list = $(".ci");
   $.each(list,function (i,value) {
       console.log(value.nodeName);
   })
});