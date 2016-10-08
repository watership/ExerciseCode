var Skeleton = (function(W,D){
	return function(){
		var instance = this;
		var _x = 0;
		var _y = 0;
		var _w = 0;
		var _h = 0;
		var _color = 'transparent';
		var _dom = null;
		var _bones = {};
		var _root = null;
		var _skin = null;
		var buildBone = function(bone){
			if(bone.getParent()){
				var p = _bones[bone.getParent()];
				bone.setX(p.getX()+p.getFootX()-bone.getHeadX());
				bone.setY(p.getY()+p.getFootY()-bone.getHeadY());
			}
			var b = D.createElement('div');
			b.style.position = 'absolute';
			b.style.left = bone.getX() + 'px';
			b.style.top = bone.getY() + 'px';
			b.style.width = bone.getWidth() + 'px';
			b.style.height = bone.getHeight() + 'px';
			b.style.overflow = 'hidden';
			b.style.fontSize = '0';
			b.style.backgroundColor = bone.getColor();
			if(bone.getSkin()){
				b.style.backgroundImage = 'url(\''+bone.getSkin()+'\')';
			}
			bone.setDom(b);
			_dom.appendChild(b);
			for(var i in _bones){
				if(bone.getName()==_bones[i].getParent()){
					buildBone(_bones[i]);
				}
			}
		};
		var moveBone = function(bone,p){
			bone.setX(p.getX()+p.getFootX()-bone.getHeadX());
			bone.setY(p.getY()+p.getFootY()-bone.getHeadY());
			bone.getDom().style.left = bone.getX() + 'px';
			bone.getDom().style.top = bone.getY() + 'px';
			moveChilds(bone);
		};
		var moveChilds = function(p){
			for(var i in _bones){
				if(p.getName()==_bones[i].getParent()){
					moveBone(_bones[i],p);
				}
			}
		};
		var FNS = {
      		'filter':function(a){
        	return [
        		'progid:DXImageTransform.Microsoft.Matrix(M11="',
        		a[0],
        		'", M12="',
        		a[1],
        		'", M21="',
        		a[2],
        		'", M22="',
        		a[3],
        		'", sizingMethod="auto expand")'
        		].join('');
      		},
      		'MozTransform':function(a){
        		return 'rotate('+a[0]+'deg)';
      		},
      		'WebkitTransform':function(a){
        		return 'rotate('+a[0]+'deg)';
      		},
      		'OTransform':function(a){
        		return 'rotate('+a[0]+'deg)';
      		},
      		'transform':function(a){
        		return 'rotate('+a[0]+'deg)';
      		}
    	};
    	var FNSO = {
    		'MozTransformOrigin':function(x,y){
        		return x+'px '+y+'px';
      		},
      		'WebkitTransformOrigin':function(x,y){
        		return x+'px '+y+'px';
      		},
      		'OTransformOrigin':function(x,y){
        		return x+'px '+y+'px';
      		},
      		'transformOrigin':function(x,y){
        		return x+'px '+y+'px';
      		}
    	};
    	var matrix = function(degree){
    		var deg = Math.PI*degree/180;
			var sin = Math.sin(deg);
        	var cos = Math.cos(deg);
        	return [
        		[cos,-sin],
        		[sin,cos]
        	];
    	};
    	var multiple=function(m1, m2) {
	        var m = [];
	        var set = function(x, y, v) {
    	        if (!m[x]) {
        	        m[x] = [];
            	}
            	m[x][y] = v;
        	};
        	for (var i = 0; i < m1.length; i++) {
	            for (var k = 0; k < m2[0].length; k++) {
    	            var sum = 0;
        	        for (var j = 0; j < m2.length; j++) {
            	        sum += m1[i][j] * m2[j][k];
                	}
                	set(i, k, sum);
	            }
    	    }
	        return m;
    	};
    	var fixIE=function(o,m) {
        	var centerX = o.getWidth()/2;
        	var centerY = o.getHeight()/2;
        	var originX = o.getHeadX();
        	var originY = o.getHeadY();
        	var diffX = centerX-originX;
        	var diffY = centerY-originY;
        	var tr = multiple(m, [
            	[diffX],
            	[diffY]
        	]);
        	var ttx = tr[0][0] + originX;
        	var tty = tr[1][0] + originY;
        	var diff = [ttx - parseInt(o.getDom().offsetWidth,10) / 2, tty - parseInt(o.getDom().offsetHeight,10) / 2];
        	o.getDom().style.left = (o.getX()+diff[0])+'px';
        	o.getDom().style.top = (o.getY()+diff[1])+'px';
    	};
		var rotate = function(o,degree){
			o.setDegree(o.getDegree()+degree);
			var args = [];
      		if(D.all){
      			var deg = Math.PI*o.getDegree()/180;
				var sin = Math.sin(deg);
        		var cos = Math.cos(deg);
        		args.push(cos);
        		args.push(-sin);
        		args.push(sin);
        		args.push(cos);
      		}else{
        		args.push(o.getDegree());
      		}
      		for(var i in FNS){
        		try{
          			o.getDom().style[i] = FNS[i](args);
        		}catch(e){}
      		}
      		for(var i in FNSO){
        		try{
          			o.getDom().style[i] = FNSO[i](o.getHeadX(),o.getHeadY());
        		}catch(e){}
      		}
      		var dego = Math.PI*degree/180;
      		var sino = Math.sin(dego);
        	var coso = Math.cos(dego);
     		var x = o.getFootX()*coso-o.getFootY()*sino;
   	  		var y = o.getFootX()*sino+o.getFootY()*coso;
   	  		if(D.all){//IE修正
   	  			fixIE(o,matrix(o.getDegree()));
        	}
   			o.setFootX(x);
   			o.setFootY(y);
      		moveChilds(o);
		};
		this.setX = function(v){_x = v;return instance;};
		this.setY = function(v){_y = v;return instance;};
		this.setWidth = function(v){_w = v;return instance;};
		this.setHeight = function(v){_h = v;return instance;};
		this.setColor = function(v){_color = v;return instance;};

		this.getX = function(v){return _x;};
		this.getY = function(v){return _y;};
		this.getWidth = function(v){return _w;};
		this.getHeight = function(v){return _h;};
		this.getColor = function(){return _color;};
		this.getSkin = function(){return _skin;};
		this.addBone = function(v){
			_bones[v.getName()] = v;
			if(!v.getParent()){
				if(_root){
					throw('too many roots.');
				}
				_root = v;
			}
		};
		this.build = function(){
			if(_dom){
				_dom.parentNode.removeChild(_dom);
				_dom = null;
			}
			var o = D.createElement('div');
			o.id='qqClock';
			o.style.position = 'absolute';
			o.style.left = _x + 'px';
			o.style.top = _y + 'px';
			o.style.width = _w + 'px';
			o.style.height = _h + 'px';
			o.style.overflow = 'hidden';
			o.style.fontSize = '0';
			o.style.backgroundColor = _color;
			if(_skin){
				o.style.backgroundImage = 'url(\''+_skin+'\')';
			}
			_dom = o;
			buildBone(_root);
			D.body.appendChild(o);
		};
		this.rotate = function(boneName,degree){
			rotate(_bones[boneName],degree);
		};
		this.getBone = function(boneName){
			return _bones[boneName];
		};
		this.getDom = function(){
			return _dom;
		};
		this.setSkin = function(url){
			_skin = url;
		};
	};
})(window,document);