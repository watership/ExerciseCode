var iWidth = 20, 
	iHeight = 20, 
	SAY = ["玩的不错！","可以啊！","太强了！","别把浏览器玩爆了！"];
var len = 3, 
	speed, 
	gridElems = multiArray(iWidth,iHeight), 
	carrier, 
	snake, 
	info, 
	btnStart, 
	topScore = len,
	snakeTimer, 
	brakeTimers = [], 
	skateTimers = [], 
	directkey, 
	allowPress = true;

window.onload = function(){
	document.oncontextmenu=function(){return false;}
	document.onselectstart=function(){return false;}
	info = document.getElementById("say");
	btnStart = document.getElementById("btnStart");
	initGrid(); 
	document.onkeydown = attachEvents; 
	btnStart.onclick = function (e) {
 		btnStart.blur(); 
		start(); 
		btnStart.setAttribute("disabled",true);
		btnStart.style.color = "#aaa";
	}
}

function start() {
	len = 3;
	speed = 10;
	directkey = 39;
	carrier = multiArray(iWidth,iHeight);
	snake = new Array();
	clear();
	initSnake(); 
	addObject("food");
	walk();
	addRandomBrake();
}

function initGrid() {
	var body = document.getElementsByTagName("body")[0];
	var table = document.createElement("table"),
		tbody = document.createElement("tbody")
	for(var j = 0; j < iHeight; j++) {  
		var col = document.createElement("tr");  
		for(var i = 0; i < iWidth; i++) {  
			var row = document.createElement("td");
			gridElems[i][j] = col.appendChild(row);  
		}
		tbody.appendChild(col);  
	}
	table.appendChild(tbody);
	document.getElementById("snakeWrap").appendChild(table);
}


function initSnake() {
	var pointer = randomPointer(len-1, len-1, iWidth/2);
	for(var i = 0; i < len; i++) {
		var x = pointer[0] - i,
			y = pointer[1];
		snake.push([x,y]);
		carrier[x][y] = "cover";
	}
}

function attachEvents(ev) {
	var ev = ev || event;
	if( allowPress )
	directkey = Math.abs(ev.keyCode - directkey) != 2 && ev.keyCode > 36 && ev.keyCode < 41 ? ev.keyCode : directkey; 
	allowPress = false;
	return false;
}


function walk() {
	if(snakeTimer) window.clearInterval(snakeTimer);
	snakeTimer = window.setInterval(step, Math.floor(3000/speed));
}

function step() {
	var headX = snake[0][0],
		headY = snake[0][1];
	switch(directkey) {
		case 37: headX -= 1; break;
		case 38: headY -= 1; break;
		case 39: headX += 1; break
		case 40: headY += 1; break;
	}

	if(headX >= iWidth || headX < 0 || headY >= iHeight || headY < 0 || carrier[headX][headY] == "block" || carrier[headX][headY] == "cover" ) {
		trace("游戏结束!");
		if(getText(document.getElementById("score"))*1 < len) trace(len,document.getElementById("score"));
		btnStart.removeAttribute("disabled");
		btnStart.style.color = "#333";
		window.clearInterval(snakeTimer);
		for(var i = 0; i < brakeTimers.length; i++) window.clearTimeout(brakeTimers[i]);
		for(var i = 0; i < skateTimers.length; i++) window.clearTimeout(skateTimers[i]);
		return;
	}
	
	if(len % 4 == 0 && speed < 60 && carrier[headX][headY] == "food") {
		speed += 5;
		walk(); 
		trace("加速！");
	}
	
	if(carrier[headX][headY] == "brake") {
		speed = 5;
		walk();
		trace("恭喜！获得减速机会一次！");
	}
	
	if(carrier[headX][headY] == "skate") {
		speed += 20;
		walk();
		trace("遭遇突然加速！");
	}       
	
	if(len % 6 == 0 && len < 60 && carrier[headX][headY] == "food") {
		addObject("block");
	}       
	
	if(len <= 40 && len % 10 == 0) {
		var cheer = SAY[len/10-1];
		trace(cheer);
	}       
	
	if(carrier[headX][headY] != "food") {
		var lastX = snake[snake.length-1][0],
			lastY = snake[snake.length-1][1];
		carrier[lastX][lastY] = false;
		gridElems[lastX][lastY].className = "";
		snake.pop();
	} else {
		carrier[headX][headY] = false;
		trace("吃到食物,又长了！");
		addObject("food");
	}
	snake.unshift([headX,headY]);
	carrier[headX][headY] = "cover";
	gridElems[headX][headY].className = "cover";
	
	len = snake.length;
	allowPress = true;
}


function addObject(name) {
	var p = randomPointer();
	carrier[p[0]][p[1]] = name;
	gridElems[p[0]][p[1]].className = name;
}


function addRandomBrake() {
	var num = randowNum(1,5);
	for(var i = 0; i < num; i++) {
		brakeTimers.push( window.setTimeout(function(){addObject("brake")},randowNum(10000,100000)) );
		skateTimers.push( window.setTimeout(function(){addObject("skate")},randowNum(5000,100000)) );
	}                
}


function trace(sth,who) {
	who = who || info;
	if(document.all) who.innerText = sth;
	else who.textContent = sth;
}


function getText(target) {
	if(document.all) return target.innerText;
	else return target.textContent;
}


function multiArray(m,n) {
	var arr =  new Array(n);
	for(var i=0; i<m; i++) 
		arr[i] = new Array(m);
	return arr;
}


function clear() {
	for(var y = 0; y < gridElems.length; y++) {
		for(var x = 0; x < gridElems[y].length; x++) {
			gridElems[x][y].className = "";
		}
	}
}


function randomPointer(startX,startY,endX,endY) {
	startX = startX || 0;
	startY = startY || 0;
	endX = endX || iWidth;
	endY = endY || iHeight;
	var p = [],
		x = Math.floor(Math.random()*(endX - startX)) + startX,
		y = Math.floor(Math.random()*(endY - startY)) + startY;
	if(carrier[x][y]) return randomPointer(startX,startY,endX,endY);
	p[0] = x;
	p[1] = y;
	return p;
}


function randowNum(start,end) {
	return Math.floor(Math.random()*(end - start)) + start;
}
