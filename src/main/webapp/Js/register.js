var current_fs, next_fs, previous_fs; //fieldsets

var left, opacity, scale; //fieldset properties which we will animate

var animating; //flag to prevent quick multi-click glitches
// var flag = 0;
// var count = 59;

$(document).ready(function(){
    $(window).on("load",function(){
        // console.log("hello");
        $(".next").click(function(){
            // console.log("hello");

            if(animating) return false;

            animating = true;

            current_fs = $(this).parent();

            next_fs = $(this).parent().next();

            //activate next step on progressbar using the index of next_fs

            $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
            // $("#progressbar li").eq($("fieldset")).index(next_fs).eq(last+1).addClass("action-button");


            //show the next fieldset

            next_fs.show();

            //hide the current fieldset with style

            current_fs.animate({opacity: 0}, {

                step: function(now, mx) {

                    //as the opacity of current_fs reduces to 0 - stored in "now"

                    //1. scale current_fs down to 80%

                    scale = 1 - (1 - now) * 0.2;

                    //2. bring next_fs from the right(50%)

                    left = (now * 50)+"%";

                    //3. increase opacity of next_fs to 1 as it moves in

                    opacity = 1 - now;

                    current_fs.css({'transform': 'scale('+scale+')'});

                    next_fs.css({'left': left, 'opacity': opacity});

                },

                duration: 800,

                complete: function(){

                    current_fs.hide();

                    animating = false;

                },

                //this comes from the custom easing plugin

                easing: 'easeInOutBack'

            });

        });
        $(".previous").click(function(){

            if(animating) return false;

            animating = true;



            current_fs = $(this).parent();

            previous_fs = $(this).parent().prev();



            //de-activate current step on progressbar

            $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");



            //show the previous fieldset

            previous_fs.show();

            //hide the current fieldset with style

            current_fs.animate({opacity: 0}, {

                step: function(now, mx) {

                    //as the opacity of current_fs reduces to 0 - stored in "now"

                    //1. scale previous_fs from 80% to 100%

                    scale = 0.8 + (1 - now) * 0.2;

                    //2. take current_fs to the right(50%) - from 0%

                    left = ((1-now) * 50)+"%";

                    //3. increase opacity of previous_fs to 1 as it moves in

                    opacity = 1 - now;

                    current_fs.css({'left': left});

                    previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});

                },

                duration: 800,

                complete: function(){

                    current_fs.hide();

                    animating = false;

                },

                //this comes from the custom easing plugin

                easing: 'easeInOutBack'

            });

        });
        $("#getCode").click(function(){        //获取验证码的按钮 点击事件
            alert("验证码已发送到邮箱");
            // $("#getCode").attr("disabled",true);
            f();
        });
        $("#return").click(function(){
            //链接到首页
        });
    });

});

function f() {
    $("#getCode").attr("disabled",true);
    $("#getCode").css({
        "cursor" : "Default",
        "box-shadow" : "none",
        "background-color" : "#6fafa5"
    });
    var count = 60;
    // run(count);
    var xx = window.setInterval(tt, 1000);
    function tt(){
        count--;
        $("#getCode").text(count+"s后重新发送");

        if(count == 0){
            window.clearInterval(xx);
            // window.location.href = "https://123.sogou.com/";
            $("#getCode").text("发送验证码");
            $("#getCode").css({
                "disabled" : "false",
                "cursor" : "pointer",
                "box-shadow" : "0 0 0 2px white, 0 0 0 3px #4177cc"
            });

        }
    }
}
