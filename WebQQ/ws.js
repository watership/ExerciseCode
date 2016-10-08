
//获取Elements
function $(str) {
	return document.getElementById(str);
}

function $tag(str,oParent) {
	oParent = oParent || document;
	return oParent.getElementsByTagName(str);
}

function $class(sClass,oParent){ 
	var oParent = oParent || document;
	var aEles = oParent.getElementsByTagName('*');
	var arr = [];
	var re = new RegExp('\\b'+sClass+'\\b');
	for(var i=0; i<aEles.length; i++){
	if(re.test(aEles[i].className)){
		arr.push(aEles[i]);
	}
	}
		return arr;
}        
		
//获取Style
function getStyle(obj,attr){
		if(obj.currentStyle){
			return obj.currentStyle[attr];
		}else{
			return getComputedStyle(obj,false)[attr];
		}
	}

//绑定事件函数
function bindEvent(obj, sEv, sFn) {
	if (obj.addEventListener) {
		obj.addEventListener(sEv, sFn, false);
	} else {
		obj.attachEvent('on' + sEv, function() {
			sFn.call(obj);
		});
	}
}
//取消绑定事件的函数
function removeEvent(obj, sEv, sFn){
	if(obj.removeEventListener){
		obj.removeEventListener(sEv, sFn, false);
	}else{
		obj.detachEvent('on' + sEv,function() {
			sFn.call(obj);
		})
	}
}

//运动框架（缓冲运动）
function startMove(obj,json,speed,fn){
		clearInterval(obj.timer);
		obj.timer = setInterval(function(){
			var bBtn = true;
			for(var attr in json){
				var iCur = 0;
				if(attr == 'opacity'){
					iCur = Math.round(getStyle(obj,attr)*100);
				}
				else{
					iCur = parseInt(getStyle(obj,attr));
				}
				
				var iSpeed = (json[attr] - iCur)/speed;
				iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
				
				if(json[attr] != iCur){
					bBtn = false;
				}
				
				if(attr == 'opacity'){
					obj.style.filter = 'alpha(opacity='+(iCur+iSpeed)+')';
					obj.style.opacity = (iCur+iSpeed)/100;
				}
				else{
					obj.style[attr] = iCur + iSpeed + 'px';
				}
				
			}
			
			if(bBtn){
				clearInterval(obj.timer);
				if(fn){
					fn.call(obj);
				}
			}
			
		},30);
	}


//弹性运动
function startMove2(obj,target) {
	var iTimer = null;
	var iSpeed = 0;
		clearInterval(obj.iTimer);
		obj.iTimer = setInterval(function() {
			iSpeed += (target - obj.offsetTop) / 5;
			iSpeed *= 0.7;
			if (Math.abs(obj.offsetTop - target) < 1 && Math.abs(iSpeed) < 1) {
				clearInterval(obj.iTimer);
			} else {
				obj.style.top = obj.offsetTop + iSpeed + 'px';
			}
		}, 30);
	}


//自由拖拽（已限制范围）（注释：obj1为函数执行对象，obj2为移动的对象）
function freeDrag(obj1,obj2) {
	obj2=obj2||obj1;
	obj1.onmousedown = function(ev) {
		obj2.style.zIndex=qqindex++;
		var ev = ev || event;
		var disX = ev.clientX - obj2.offsetLeft;
		var disY = ev.clientY - obj2.offsetTop;
		if (obj2.setCapture) {
			obj2.setCapture();
		}
		bindEvent(document, 'mousemove',fn1 );
		bindEvent(document,'mouseup',fn2)
		return false;

		function fn1(ev) {
			var ev = ev || event;
			var L = ev.clientX - disX;
			var T = ev.clientY - disY;
			if (L < 0) {
					L = 0;
				} else  if (L > document.documentElement.clientWidth - obj2.offsetWidth) {
					L = document.documentElement.clientWidth - obj2.offsetWidth;
				}
				if (T < 0) {
					T = 0;
				} else  if (T > document.documentElement.clientHeight - obj2.offsetHeight) {
					T = document.documentElement.clientHeight - obj2.offsetHeight;
				}
			obj2.style.left = L + 'px';
			obj2.style.top = T + 'px';
		}
		function fn2() {
			removeEvent(document, 'mousemove',fn1);
			removeEvent(document,'mouseup',fn2);
			if (obj2.releaseCapture) {
				obj2.releaseCapture();
			}
		}
	}
}


//碰撞检测
function qqcrash(obj1, obj2){
		var L1=obj1.offsetLeft;
		var R1=obj1.offsetLeft+obj1.offsetWidth;
		var T1=obj1.offsetTop;
		var B1=obj1.offsetTop+obj1.offsetHeight;
		
		var L2=obj2.offsetLeft;
		var R2=obj2.offsetLeft+obj2.offsetWidth;
		var T2=obj2.offsetTop;
		var B2=obj2.offsetTop+obj2.offsetHeight;
		
		if(R1<L2 || L1>R2 || B1<T2 || T1>B2){
				return false;
			}
			else{
				return true;
			}
}


//计算勾股数
	function pynum(obj1, obj2)
	{
		var a=obj1.offsetLeft-obj2.offsetLeft;
		var b=obj1.offsetTop-obj2.offsetTop;
		return Math.sqrt(a*a+b*b);
	}


//DOM操作
function firstElementChild(obj) {
		return obj.firstElementChild || obj.firstChild;
	}
	function lastElementChild(obj) {
		return obj.lastElementChild || obj.lastChild;
	}
	function previousElementSibling(obj) {
		return obj.previousElementSibling || obj.previousSibling;
	}
	function nextElementSibling(obj) {
		return obj.nextElementSibling || obj.nextSibling;
	}

	//QQ拼音
	function qqPingyin(q){
		q?q.toggle():function(d,j){
		j=d.createElement('script');
		j.async=true;
		j.src='//ime.qq.com/fcgi-bin/getjs';
		j.setAttribute('ime-cfg','lt=2');
		d=d.getElementsByTagName('head')[0];
		d.insertBefore(j,d.firstChild)}
		(document)}
		//(window.QQWebIME)
