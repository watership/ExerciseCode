var loadingDiv,
		iMinIndex,
		iMinZindex,
		index,
		qqapp,
		sound_btn=true,
		appPos=[],
		oNear,
		qqindex=3,
		qqClockTime;

window.onbeforeunload = function(){return "您确定要离开QQ Desktop吗?";}

window.onload=function(){
//删除Loading弹出层
	loadingDiv=document.getElementById('loadingDiv');
	startMove(loadingDiv,{opacity:0},8,function(){
		clearInterval(s1);
		clearInterval(s2);
		loadingDiv.style.display='none';
		loadingDiv.parentNode.removeChild(loadingDiv);
})
//屏蔽文本选择
	document.onselectstart=function(){return false;}
//右键菜单
	document.oncontextmenu=function(ev){
		var ev = ev || event;
		$('qqtextmenu').style.display = 'block';
		if(ev.clientX>(document.documentElement.clientWidth - $('qqtextmenu').offsetWidth)){
			$('qqtextmenu').style.left=(document.documentElement.clientWidth - $('qqtextmenu').offsetWidth)+'px';
		}else{
			$('qqtextmenu').style.left = ev.clientX + 'px';
		}
		if(ev.clientY>(document.documentElement.clientHeight - $('qqtextmenu').offsetHeight)){
			$('qqtextmenu').style.top=(document.documentElement.clientHeight - $('qqtextmenu').offsetHeight)+'px';
		}else{
			$('qqtextmenu').style.top = ev.clientY + 'px';
		}
		return false;
	}
	bindEvent(document, 'click', function(){
		$('qqtextmenu').style.display = 'none';
	});
	var textmenuLi=$('qqtextmenu').children;
	for(var i=0;i<textmenuLi.length;i++){
			textmenuLi[i].onclick=function(ev){
				var ev = ev || event;
				ev.cancelBubble = true;
		}
	}
	textmenuLi[0].onclick=qqstartBtn;
	textmenuLi[1].onclick=contactMe;
	//textmenuLi[2].onclick=

//布局Desktop和app
appRank();
//窗口大小改变时重新布局Desktop和app
window.onresize=function(){
	appRank();
}
//app拖拽互换位置
for(var i=0;i<qqapp.length;i++){
		transpos(qqapp[i]);
}

//QQ拼音输入法调用
	qqPingyin(window.QQWebIME);

//音乐播放器调用
	initAudio();
	loadAudio('music.mp3','肖邦《夜曲》');
	freeDrag($('audioPlay_container'));
	$('audioPlay_title').onmousemove=$('audioPlay_time').onmousemove=$('audioPlay_range').onmousemove=$('audioPlay_play').onmousemove=$('audioPlay_volume').onmousemove=function(ev){
		var ev = ev || event;
		ev.cancelBubble = true;
	}
	$('audioPlay_title').onmousedown=$('audioPlay_time').onmousedown=$('audioPlay_range').onmousedown=$('audioPlay_play').onmousedown=$('audioPlay_volume').onmousedown=function(ev){
		var ev = ev || event;
		ev.cancelBubble = true;
	}

//日历calendar
//freeDrag($('qqcalendar'));

//时钟clock
freeDrag($('qqClock'));

//左侧栏的app移入图标变大
	bindEvent(document, 'mousemove', appBig);

//声音的开关
	$("id_sound").onclick=soundBtn;

//QQ按钮
	$('qqmin').onclick=contactMe;
	$('qqmin_close').onclick=function(){
			$('qqmin_space').style.overflow='hidden';
			$('aboutMask').style.display="none";
			startMove($('qqmin_space'),{right:260,bottom:100,width:180,height:65},7,function(){
					startMove($('qqmin_space'),{right:15,bottom:15,width:30,height:30},1,function(){
						$('qqmin_space').style.display="none";
					})
			});
		}

//系统设置弹出层
	$class("qqset",$(ToolList))[0].onclick=qqsetBtn;
	$("qqset_close").onclick=qqsetClose;
	$('qqset').onclick=function(){
		$('qqset').style.zIndex=qqindex++;
	}
	freeDrag($("set_bar"),$('qqset'));
	$tag("ul",$('set_con'))[0].onclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p  class="apptxt">弹出窗的实现</p><p>1.自由拖拽</p><p>2.运动效果</p><p>3.CSS3圆角</p>';
	qqappOpenWindow();
}

//主题设置弹出层
	$class("qqtheme",$(ToolList))[0].onclick=qqthemeBtn;
	$("qqtheme_close").onclick=qqthemeClose;
	$('qqtheme').onclick=function(){
		$('qqtheme').style.zIndex=qqindex++;
	}
	freeDrag($("theme_bar"),$('qqtheme'));
//主题设置功能
	var theme_li=$tag("li",$("theme_con"));
	var theme_img=$tag("img",$("theme_con"));
	for(var i=0;i<theme_li.length;i++){
		theme_li[i].index=i;
		theme_li[i].onclick=function(){
			var This=this;
			startMove($("qbg"),{opacity:20},15,function(){
				$("qbg").src=theme_img[This.index].getAttribute('qbgSrc');
				startMove($("qbg"),{opacity:100},10)
			})
		}
	}

//关于作者 关于作品
	$class("qqstart",$(ToolList))[0].onclick=qqstartBtn;
	$('about_close').onclick=function(){
			$('aboutMask').style.display="none";
			startMove($('aboutme'),{top:-680},3,function(){
					$('aboutme').style.display="none";
			});
		}

//app弹出层
freeDrag($class('qqapp_bar')[0],$('qqapp_open'));
 //最大化/还原按钮
 var qqappmax=true;
 $class('qqapp_maximize',$('qqapp_open'))[0].onclick = function (){
 	if(qqappmax){
 		$('qqapp_open').style.zIndex=19995;
 		$class('qqapp_maximize',$('qqapp_open'))[0].style.backgroundPosition= "-98px -62px";
 		startMove($('qqapp_open'),{top:0,left:0,width:document.documentElement.clientWidth - 2},5)
  	startMove($class('qqapp_con')[0],{height:document.documentElement.clientHeight -30},5);
  	qqappmax=false;
 	}else{
 		$('qqapp_open').style.zIndex=3;
 		$class('qqapp_maximize',$('qqapp_open'))[0].style.backgroundPosition= "-39px -62px";
 		startMove($('qqapp_open'),{top:120,left:340,height:375,width:450},5)
 		startMove($class('qqapp_con')[0],{height:375},5);
  	qqappmax=true;
 	}
 };
 //关闭按钮
 $class('qqapp_close',$('qqapp_open'))[0].onclick =function(){
 	startMove($('qqapp_open'),{width:10,height:10,top:700},5,function(){
 		$('qqapp_open').style.display="none";
 	})
 }
 //最小化按钮
 $class('qqapp_minimize',$('qqapp_open'))[0].onclick =function(){
 	startMove($class('qqapp_con')[0],{opacity:0},3);
 	startMove($('qqapp_open'),{opacity:7,top:document.documentElement.clientHeight-64,left:document.documentElement.offsetWidth-224,height:64,width:112},6,function(){
	 	$('qqapp_open').style.display="none";
	 	$('qqtip').style.display="block";
 	})
 }
 //任务栏
$('qqtip').onclick =qqtipWindow;



//左侧app点击事件
$('ItemList').children[1].onclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p class="apptxt">我的联系方式</p><p>周涛</p><p>手机：18811108466</p><p>邮箱：silentzhou@gmail.com</p><p>QQ：448943353</p><p>新浪微博：@蓝色水池</p><p>网站：www.saternet.org</p>';
	qqappOpenWindow();
}
$('ItemList').children[0].onclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p  class="apptxt">QQ Desktop</p><p>作者耗时近两个星期完成</p><p>借鉴腾讯WebQQ（http://web.qq.com/）开发的一个Web Desktop.</p><p>其中采用了HTML5的一些新功能</p><p style="font-weight:bold">在Chrome浏览器下拥有更好的支持</p><p style="font-size:12px">(IE9,火狐12,Opera12等由于不支持播放器的HTML5新控件的标签，所以显示有问题，请使用Chrome打开，谢谢。)</p>';
	qqappOpenWindow();
}
$('ItemList').children[2].onclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p  class="apptxt">左下角可以换肤更换壁纸哦！~</p><p>（具有淡入淡出的效果，但是取决网速）</p>';
	qqappOpenWindow();
}
$('ItemList').children[3].onclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>不想听歌就静音吧~</p><p>左下角大大的Q+有相关的开发文档的~</p><p>（不过正在撰写之中~）</p>';
	qqappOpenWindow();
}
$('ItemList').children[4].onclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>试试桌面的app吧</p><p  class="apptxt">记得要双击！</p>';
	qqappOpenWindow();
}

//桌面app点击事件
$('desktop').children[0].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>试试拖拽这个app</p><p>使劲往外拽看看~</p>';
	qqappOpenWindow();
}
$('desktop').children[1].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>app之间是可以互相换位置的哦~</p><p>试试看吧！</p>';
	qqappOpenWindow();
}
$('desktop').children[2].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>你能看的出来吗？</p><p>app的名称使用了CSS3的文字阴影</p><p>这样在浅背景下白色的文字依然清晰可见的~</p>';
	qqappOpenWindow();
}
$('desktop').children[3].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>这个窗口是可以最小化，最大化的哦~</p><p>最小化到任务栏后可以重新打开的！</p><p>试试看吧！</p>';
}
$('desktop').children[4].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>看到右下角任务栏里面的QQ图标了吗？</p><p>那是我的联系方式，记住！哈哈</p>';
	qqappOpenWindow();
}
$('desktop').children[5].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>你是试过了QQ拼音了吗？</p><p>是可以使用的！</p>';
	qqappOpenWindow();
}
$('desktop').children[6].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>你试过更换背景主题了吗？</p><p>试试看吧~</p>';
	qqappOpenWindow();
}
$('desktop').children[7].ondblclick=function(){
	$class('qqapp_con',$('qqapp_open'))[0].innerHTML='<p>点击左下角的大Q+</p><p>获取最详细的开发文档吧！</p>';
	qqappOpenWindow();
}
}//这是Window.onload的，切莫删除！




//***************************函数***************************//
//**********************************************************//
//排列图标
function initIco(){
	var appNum=Math.floor(((document.documentElement.scrollHeight))/128);
		for(var i=0;i<qqapp.length;i++){
			qqapp[i].style.left=Math.floor(i/appNum)*110+"px";
			qqapp[i].style.top=i%appNum*110+"px";
		}
	}
//记录app布局以后的位置json
function appPosition(){
	for(var i=0;i<qqapp.length;i++){
		appPos[i]={left: qqapp[i].offsetLeft, top: qqapp[i].offsetTop};
	}
}
//布局desktop的上面的app
function appRank(){
		qqapp=$("desktop").children;
		for(var i=0;i<qqapp.length;i++){
				qqapp[i].index=i;
			}
		initIco();
		appPosition();
}

//左侧栏的app移入图标变大
function appBig(ev){
		var ev=ev||event;
		var oDiv=$('ItemList');
		var aImg=$tag('img',oDiv);
		var d=0;
		var iMax=100;
		var i=0;
		
		function getDistance(obj){
			return Math.sqrt
			(
				Math.pow(obj.offsetLeft+oDiv.offsetLeft-ev.clientX+obj.offsetWidth/2, 2)+
				Math.pow(obj.offsetTop+oDiv.offsetTop-ev.clientY+obj.offsetHeight/2, 2)
			);
		}
		for(var i=0;i<aImg.length;i++){
				d=getDistance(aImg[i]);
				d=Math.min(d, iMax);
				aImg[i].style.width=((iMax-d)/iMax)*35+48+'px';
		}
};

//Desktop app拖拽互换位置
function transpos(obj){
		obj.onmousedown=function (ev){
			var ev=ev||event;
			var disX=ev.clientX-obj.offsetLeft;
			var disY=ev.clientY-obj.offsetTop;
			obj.className='qqactive';
			document.onmousemove=function (ev){
				var ev=ev||event;
				obj.style.left=ev.clientX-disX+'px';
				obj.style.top=ev.clientY-disY+'px';

				for(var i=0;i<qqapp.length;i++){
					qqapp[i].className="";
				}
				obj.clsssName="qqactive"
				var oNear=findNearest(obj);
				if(oNear){
					oNear.className='qqactive';
				}
			};
			
			document.onmouseup=function (){
				obj.className='';
				document.onmousemove=document.onmouseup=null;
				var oNear=findNearest(obj);
				if(oNear){
					oNear.className='';
					startMove(oNear, appPos[obj.index],8);
					startMove(obj, appPos[oNear.index],8);
					var tmp=0;
					tmp=obj.index;
					obj.index=oNear.index;
					oNear.index=tmp;
				}
				else{
					startMove(obj, appPos[obj.index],8);
				}
			};
			clearInterval(obj.timer);
			return false;
		};
	}

//找到碰上的，并且最近的
function findNearest(obj)	{
		var iMin=999999999;
		var iMinIndex=-1;

		for(var i=0;i<qqapp.length;i++){
			if(obj==qqapp[i])continue;
			if(qqcrash(obj, qqapp[i])){
				var dis=pynum(obj, qqapp[i]);
				if(iMin>dis){
					iMin=dis;
					iMinIndex=i;
				}
			}
		}
		
		if(iMinIndex==-1){
			return null;
		}else{
			return qqapp[iMinIndex];
		}
	}

//系统设置和主题设置按钮以及他们的关闭按钮
	function qqsetBtn(){
		$('qqset').style.zIndex=qqindex++;
		$('qqset').style.display="block";
		startMove($('qqset'),{top:38},3);
	}

	function qqthemeBtn(){
		$('qqtheme').style.zIndex=qqindex++;
		$('qqtheme').style.display="block";
		startMove($('qqtheme'),{top:85},3);
	}

	function qqstartBtn(){
		$('aboutMask').style.display="block";
		$('aboutme').style.display="block";
		startMove($('aboutme'),{top:80},4);
	}

	function qqsetClose(){
		startMove($('qqset'),{top:700},3,function(){
					$('qqset').style.top=-620+"px";
					$('qqset').style.display="none";
				});
	}
	function qqthemeClose(){
		startMove($('qqtheme'),{top:700},3,function(){
					$('qqtheme').style.top=-620+"px";
					$('qqtheme').style.display="none";
				});
	}

	//声音的开关按钮
function soundBtn(){
		if(sound_btn){
			$("id_sound").className="a_sound";
			sound_btn=false;
			audioEl.volume=volumeEl.value=0;
	}else{
			$("id_sound").className="sound";
			sound_btn=true;
			audioEl.volume=volumeEl.value=0.5;
	}
	}

//联系方式
function contactMe(){
		$('aboutMask').style.display="block";
		$('qqmin_space').style.overflow='';
		$('qqmin_space').style.display='block';
		startMove($('qqmin_space'),{right:480,bottom:150,width:320,height:220},3)
	}

//tip调用函数
function qqtipWindow(){
 	$('qqapp_open').style.display="block";
 	$('qqtip').style.display="none";
 	startMove($class('qqapp_con')[0],{opacity:100},4);
 	startMove($('qqapp_open'),{opacity:100,top:120,left:340,height:375,width:450},6)
 }

	//app的调用
	function qqappOpenWindow(){
	$('qqapp_open').style.display="block";
	$('qqtip').style.display="none";
	$('qqapp_open').style.left="340px";
	$('qqapp_open').style.top="120px";
	$('qqapp_open').style.width="450px";
	$('qqapp_open').style.height="375px";
	$('qqapp_open').style.filter = 'alpha(opacity=0)';
	$('qqapp_open').style.opacity = 0;
	startMove($class('qqapp_con')[0],{opacity:100},4);
	startMove($('qqapp_open'),{opacity:100},10);
}