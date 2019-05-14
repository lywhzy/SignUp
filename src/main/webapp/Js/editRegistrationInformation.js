var form;


$(document).ready(function(){
    form = $("#form");
    // var list =
   //  $(window).on("load",function () {
   //
   // });
    $("#addInput").click(function () {
        addI();
    });
    $("#addRadio").click(function(){
        addR();
    });
    $("#addoption").click(function () {
       addO();
    });
});


function addI() {
    var input = $("<input>");
    input.css({
        "type" : "text",
        "width" : "300px",
        "margin" : "1px auto"
    });
    input.addClass("form-control");
    form.append(input);
}
function addR(){
    var p=document.createElement("p");
    p.innerHTML='<label for="boy">男</label>';
    p.innerHTML = p.innerHTML + '<input type="radio" name="exception" id="boy"/>';
    p.innerHTML = p.innerHTML + '<label for="girl">女</label>';
    p.innerHTML = p.innerHTML + '<input type="radio" name="exception" id="girl"/>';
    form.append(p);
}
function addO(){
    var select = $("<select></select>");
    select.css({
        "width" : "300px",
        "margin" : "1px auto"
    });
    select.addClass("form-control");
    var option1 = $("<option></option>").text("hello1");
    var option2 = $("<option></option>").text("hello2");
    var option3 = $("<option></option>").text("hello3");
    select.append(option1,option2,option3);
    form.append(select);
}