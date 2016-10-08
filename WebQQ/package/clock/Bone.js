var Bone = (function(){
	return function(){
		var instance = this;
		var _name = null;
		var _x = 0;
		var _y = 0;
		var _z = 0;
		var _w = 0;
		var _h = 0;
		var _length = 0;
		var _degree = 0;
		var _skin = null;
		var _head = {
			'x':0,
			'y':0
		};
		var _foot = {
			'x':0,
			'y':0
		};
		var _color = 'transparent';
		var _parent = null;
		var _dom = null;

		var freshLength = function(){
			_length = Math.sqrt(Math.pow(_foot.x-_head.x,2)+Math.pow(_foot.y+_head.y,2));
		};

		this.setName = function(v){_name = v;return instance;};
		this.setX = function(v){_x = v;return instance;};
		this.setY = function(v){_y = v;return instance;};
		this.setZ = function(v){_z = v;return instance;};
		this.setDegree = function(v){_degree = v;return instance;};
		this.setWidth = function(v){_w = v;return instance;};
		this.setHeight = function(v){_h = v;return instance;};
		this.setHeadX = function(v){
			_head.x = v;
			freshLength();
			return instance;
		};
		this.setHeadY = function(v){
			_head.y = v;
			freshLength();
			return instance;
		};
		this.setFootX = function(v){
			_foot.x = v;
			freshLength();
			return instance;
		};
		this.setFootY = function(v){
			_foot.y = v;
			freshLength();
			return instance;
		};
		this.setParent = function(v){_parent = v;return instance;};
		this.setColor = function(v){_color = v;return instance;};
		this.setSkin = function(url){
			_skin = url;
		};

		this.getName = function(){return _name;};
		this.getX = function(v){return _x;};
		this.getY = function(v){return _y;};
		this.getZ = function(v){return _z;};
		this.getWidth = function(v){return _w;};
		this.getHeight = function(v){return _h;};
		this.getHeadX = function(){return _head.x;};
		this.getHeadY = function(){return _head.y;};
		this.getFootX = function(){return _foot.x;};
		this.getFootY = function(){return _foot.y;};
		this.getParent = function(){return _parent;};
		this.getColor = function(){return _color;};
		this.getLength = function(){return _length};
		this.getDegree = function(){return _degree};
		this.getSkin = function(){return _skin;};

		this.setDom = function(v){_dom = v;};
		this.getDom = function(){return _dom;};
	};
})();