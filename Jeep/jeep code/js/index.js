/*!
 * Name: Zhou Tao
 * Sate: http://www.saternet.org/
 * Date: 2013-12
 */

jQuery(document).ready(function($) {

//the app function
    var app = {
        init: function(){
            app.gridInit();
            app.gridName();
            TextEffect.init();
        },
        gridInit: function(){
            for (var i=0; i<36; i++){
                $('#dataRegion').append('<li></li>');
            };
            for (var i=0; i<106; i++){
                $('#dataRegion2').append('<li></li>');
            };
        },
        gridName: function(){
            //  data map init
            $('#dataRegion li').addClass(function(){
                $(this).addClass('region'+$(this).index());
            })
            $('#dataRegion2 li').addClass(function(){
                $(this).addClass('region'+$(this).index());
                $('#dataRegion2 .region32').attr('regionable','able');
                $('#dataRegion2 .region17').attr('regionable','able');
                $('#dataRegion2 .region19').attr('regionable','able');
                $('#dataRegion2 .region20').attr('regionable','able');
                $('#dataRegion2 .region35').attr('regionable','able');
                $('#dataRegion2 .region23').attr('regionable','able');
                $('#dataRegion2 .region41').attr('regionable','able');
            })
        }

    };

//  all text flash effect function
    var TextEffect = {
        init: function(){
            $(".page1 .title-wrap").hide();
            $('.line1 .text-wrap').hide();
            $('.data-line .line1-img').hide();
            $('.line2 .text-wrap').hide();
            $('.data-line .line2-img').hide();
            $('#dataRegion li').css('opacity','0');
            setTimeout(function(){
                $(".page1 .title-wrap").show(100,TextEffect.page1Text());
            },800);
        },
        page1Text: function (){
            $('.page1 .title1').textillate({ in: { effect: 'bounceInDown' } });
            $('.page1 .title12').textillate({ in: { effect: 'bounceInDown' } });
            $('.page1 .title2').textillate({ in: { effect: 'fadeInRight'},callback:function(){
                TextEffect.page1Line1();
            }});
        },
        page2Text: function (){
            $('.page2 .title1').textillate({ in: { effect: 'fadeInLeftBig' } });
            $('.page2 .title2').textillate({ in: { effect: 'fadeInRight'},callback:function(){
                $('.page2 .title3').fadeIn().textillate({ in: { effect: 'bounceInLeft' ,sync: true}});
                $('.page2 .title32').fadeIn().textillate({ in: { effect: 'bounceInLeft' ,sync: true,delay: 100}});
            }});
        },
        page1Line1: function(){
            $('.data-line .line1-img').show();
            $('.data-line .line1-img').addClass('animated bounceIn');
            $('.data-line .line1-img').one('webkitAnimationEnd mozAnimationEnd oAnimationEnd animationEnd',function(){
                $('.line1 .text-wrap').fadeIn();
                $('.data-line .line1-text1').textillate({ in: { effect: 'animated bounceIn'}});
                $('.data-line .line1-text2').textillate({ in: { effect: 'animated bounceIn',sync: true},callback:function(){
                    TextEffect.page1Line2();
                }});
            });
        },
        page1Line2: function(){
            $('.data-line .line2-img').show();
            $('.data-line .line2-img').addClass('animated bounceIn');
            $('.data-line .line2-img').one('webkitAnimationEnd mozAnimationEnd oAnimationEnd animationEnd',function(){
                $('.line2 .text-wrap').fadeIn();
                $('.data-line .line2-text1').textillate({ in: { effect: 'animated bounceIn'}});
                $('.data-line .line2-text2').textillate({ in: { effect: 'animated bounceIn',sync: true}});
            });
        },
        page1Line1Over: function(){
            $('.line1 .text-wrap').addClass('animated pulse');
            //$('.data-line .line1-text2').addClass('animated flash');
        },
        page1Line2Over: function(){
            $('.line2 .text-wrap').addClass('animated pulse');
            //$('.data-line .line1-text2').addClass('animated flash');
        },
        page4text: function(){
            $('.page4-text').removeClass('animated bounceInDown rotateInUpRight').addClass('animated bounceInDown').css('');
            $('.page4-text').one('webkitAnimationEnd mozAnimationEnd oAnimationEnd animationEnd',function(){
                $(this).css({'-webkit-transform':'scale(1.8)','-webkit-transition-duration':'1s'} ).textillate({ in: { effect: 'animated rotateIn'}});
            });
        }
    };


///////all event bind

//  all page switch
    $('.navigation li').click(function(){
        var that = $(this);
        $('.navigation').find('li').removeClass('page-on');
        $(this).addClass('page-on');

        $('.stage .page').hide(100,function(){
            $('.stage .page').eq(that.index()).show().addClass('animated fadeInLeft');
            TextEffect.page2Text();
            TextEffect.page4text();
            $('#slider').addClass('animated rollIn');
            $('#slider').one('webkitAnimationEnd mozAnimationEnd oAnimationEnd animationEnd',function(){
                $('#slider').fadeIn();
            });

        });
    });

//    $(window).mousewheel(function(event, delta, deltaX, deltaY) {
//            if (deltaY < 0) {
//                //motion(1);
//                p++;
//                $('.navigation li').removeClass('page-on');
//                $('.navigation li:nth-of-type(' + p + ')').addClass('page-on');
//            } else {
//                //motion(-1);
//                $('.navigation li').removeClass('page-on');
//                $('.navigation li:nth-of-type(' + p + ')').addClass('page-on');
//            };
//    });


//page1 event bind
    $('#dataRegion').bind('mouseover',function(event){
        event.preventDefault();
        var eventTargetClassName = $(event.target).attr('class');
        switch(eventTargetClassName) {
            case "region9" :
                TextEffect.page1Line1Over();
                 break;
            case "region10" :
                TextEffect.page1Line1Over();
                break;
            case "region18" :
                TextEffect.page1Line1Over();
                break;
            case "region8" :
                TextEffect.page1Line2Over();
                break;
            case "region17" :
                TextEffect.page1Line2Over();
                break;
            case "region26" :
                TextEffect.page1Line2Over();
                break;
        }
    })

//page2 event bind
    $('#dataRegion2').bind('click',function(event){
        event.preventDefault();

        var eventTargetClassName = $(event.target).attr('class');

        function switchImage(num){
            var imageUrl = 'url(image/bigimage/'+(num+1)+'.jpg)';
            if( ($('#bigImage').attr('imageID')) && (num == $('#bigImage').attr('imageID')) && ($('#bigImage').is(":visible")) ){
                $('#bigImage').animate({
                    top: '+=150',
                    left: '+=150',
                    width: 'toggle',
                    height: 'toggle'
                }, 800, function() {});
            }else{
                $('#bigImage').fadeOut(100,function(){
                    $(this).attr('imageID',num);
                    $(this).css({'background-image':imageUrl,'top':'110px','left':'100px'}).fadeIn();
                });
            }
        };

        function eventTargetClassNameColor(){
            $('#dataRegion2').find('.'+eventTargetClassName).css('background-color','rgba(0,0,0,0.3)');
        }

        switch(eventTargetClassName) {
            case "region32" :
                switchImage(0);
                eventTargetClassNameColor();
                break;
            case "region17" :
                switchImage(1);
                eventTargetClassNameColor();
                break;
            case "region19" :
                switchImage(2);
                eventTargetClassNameColor();
                break;
            case "region20" :
                switchImage(3);
                eventTargetClassNameColor();
                break;
            case "region35" :
                switchImage(4);
                eventTargetClassNameColor();
                break;
            case "region23" :
                switchImage(5);
                eventTargetClassNameColor();
                break;
            case "region41" :
                switchImage(6);
                eventTargetClassNameColor();
                break;
        }
    })

//  page3 event bind

    $('#next').click(function(){
        var thisLi = $("#slider li:visible");
        thisLi.css({'-webkit-transform':'rotateY(-90deg)','-webkit-transition-duration':'1s'} );
        setTimeout(visibleLi,1000);
        function visibleLi(){
            thisLi.hide();
            $("#slider li").eq(thisLi.index()+1).show().css('-webkit-transform','rotateY(90deg)');
            //thisLi = $("#slider li").eq(thisLi.index()+1);
            $("#slider li:visible").css({'-webkit-transform':'rotateY(0deg)','-webkit-transition-duration':'1s'} );
        }
    });
    $('#prev').click(function(){
        var thisLi = $("#slider li:visible");
        thisLi.css({'-webkit-transform':'rotateY(-90deg)','-webkit-transition-duration':'1s'} );
        setTimeout(visibleLi,1000);
        function visibleLi(){
            thisLi.hide();
            $("#slider li").eq(thisLi.index()-1).show().css('-webkit-transform','rotateY(90deg)');
            //thisLi = $("#slider li").eq(thisLi.index()+1);
            $("#slider li:visible").css({'-webkit-transform':'rotateY(0deg)','-webkit-transition-duration':'1s'} );
        }
    });

    $('#car img').hover(function(){
        $('this').addClass('car1-hover');
    });

    $('#bigImage').bind('click',function(){
        $(this).animate({
            top: '+=150',
            left: '+=150',
            width: 'toggle',
            height: 'toggle'
        }, 800, function() {
            // Animation complete has no function
        });
    });


//  init this app
    app.init();


});


