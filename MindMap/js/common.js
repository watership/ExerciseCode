//Tree Class
function Tree(dom){
    this.dom = dom;
    this.childNodes = [];
    this.id = 'root';
}

Tree.prototype = {
    init:function(){
        this.getLine();
        this.textEdit();
    },
    getLine:function (){
        var that = this;
        var line = null;
        //if(!(this.dom.find('.node').attr('contenteditable'))){
            //console.log(this.dom.find('.node').attr('contenteditable'));
            this.dom.bind('mousedown',function(e){
                var l = 0;
                var t = 0;
                var ml = 'M'+parseInt(that.dom.css('left'))+' '+parseInt(that.dom.css('top'))+'L';
                line = r.path();
                line.attr('stroke-width','2px');
                var lineColor = $(this).css('background-color');
                line.attr('stroke',lineColor);
                var disX = e.pageX - that.dom.offset().left;
                var disY = e.pageY - that.dom.offset().top;
                if (that.dom.setCapture) {
                    that.dom.setCapture();
                }
                $(document).bind({
                    mousemove: fn1,
                    mouseup: fn2
                });
                //return false;
                function fn1(e){
                    l = e.pageX - disX;
                    t = e.pageY - disY;
                    if( l<0 ){
                        l = 0;
                    }else if( l<document.documentElement.clientWidth - that.dom.offsetWidth ){
                        l = document.documentElement.clientWidth - that.dom.offsetWidth;
                    }
                    if (t < 0) {
                        t = 0;
                    } else  if (t > document.documentElement.clientHeight - that.dom.offsetHeight) {
                        t = document.documentElement.clientHeight - that.dom.offsetHeight;
                    }

                    var rootX = parseInt(that.dom.css('left')) + (that.dom.width())/2;
                    var rootY = parseInt(that.dom.css('top')) + (that.dom.height())/2;

                    ml = 'M'+rootX+' '+rootY+'L'+l+' '+t;
                    line.attr('path',ml);
                    $('#mindMap').addClass('line');
                }
                function fn2(){
                    if( l>0 && t>0){
                        that.createNode(l,t,line);
                    }else{
                        line.remove();
                    }
                    $(document).unbind({
                        mousemove:fn1,
                        mouseup:fn2
                    });
                    $('#mindMap').removeClass('line');
                    if (that.dom.releaseCapture) {
                        that.dom.releaseCapture();
                    }
                }
            });
        //}
        return line;
    },
    createNode:function(l,t,line){
        var htmlNode = '<div class="node-container"><div class="node" contenteditable="true">新建节点</div>' +
            '<div class="controls"><div class="node-close"></div><div class="node-move"></div></div></div>';
        $('#mindMap').append(htmlNode);
        var lastNode = $('.node-container:last-of-type');
        var x = l - parseInt(lastNode.width()/2);
        var y = t - parseInt(lastNode.height()/2);
        var color = 0;
        //if( this.parenetNode || this.parenetNode.id == 'root'){

        lastNode.css({'left':x,'top':y});
        lastNode.find('.node').focus().select();
        var nodeId = mathGuid();
        var node = new Node(this,lastNode,line,nodeId);
        nodeList.push(node);
        this.childNodes.push(node);
        if( node.parentNode.id == 'root' ){
            color = nodeColor[(node.parentNode.childNodes.length-1)];
        }else{
            color = node.parentNode.dom.css('background-color');
        }
        node.dom.css('background-color',color);
        //node.dom.show();
        node.init();
        dataSave();
    },
    textEdit:function(){
        var that = this;
        var oldText ;
        this.dom.find('.node').bind({
            click :function(){
                oldText = $(this).text();
                $(this).attr('contenteditable','true');
                that.dom.addClass('editing');
            },
            keydown :function(e){
                if( e.which == 13 ){
                    if( $(this).text() == ''){
                        //console.log(that);
                        that.removeEvent();
                    }else{
                        $(this).attr('contenteditable','false');
                        that.dom.removeClass('editing');
                    }
                }else{
                    that.drawLine(parseInt(that.dom.css('left')),parseInt(that.dom.css('top')));
                }
            },
            focusout : function(){
                if(!$(this).text()){
                    //alert('aaa');
                    that.removeEvent();
                }else{
                    $(this).attr('contenteditable','false');
                    that.dom.removeClass('editing');
                    if( ($(this).text() != oldText)&&($(this).text() != "新建节点") ){
                        dataSave();
                    }
                }
            }
        });
    }
};


//Node Class
function Node(parentNode,dom,line,id){
    Tree.call(this,dom);
    this.parentNode = parentNode;
    this.childNodes = [];
    this.line = line;
    this.id = id;
    }

extend(Node,Tree);

Node.prototype.init = function(){
    this.freeDrag();
    this.hoverEvent();
    this.removeEvent();
    this.getLine();
    this.textEdit();
};
Node.prototype.drawLine = function(l,t){
    var ml = this.line.attr('path');
    var parentX = parseInt(ml[0][1]);
    var parentY = parseInt(ml[0][2]);
    l = l +  (this.dom.width())/2;
    t = t +  (this.dom.height())/2;
    ml = pathML(parentX,parentY,l,t);
    this.line.attr('path',ml);

    for(var i=0;i<this.childNodes.length;i++){
        var ml2 = this.childNodes[i].line.attr('path');
        var parentX2 = parseInt(ml2[1][1]);
        var parentY2 = parseInt(ml2[1][2]);
        ml = pathML(l,t,parentX2,parentY2);
        this.childNodes[i].line.attr('path',ml);
    }
};
Node.prototype.freeDrag = function(){
    var obj = this.dom.find('.node-move');
    var that = this;
    obj.bind('mousedown',function(e){
        that.dom.addClass('node-moving');
        var disX = e.pageX - that.dom.offset().left;
        var disY = e.pageY - that.dom.offset().top;
        if (that.dom.setCapture) {
            that.dom.setCapture();
        }
        $(document).bind({
            mousemove: fn1,
            mouseup: fn2
        });
        return false;
        function fn1(e){
            var l = e.pageX - disX;
            var t = e.pageY - disY;
            if( l<0 ){
                l = 0;
            }else if( l<document.documentElement.clientWidth - that.dom.offsetWidth ){
                l = document.documentElement.clientWidth - that.dom.offsetWidth;
            }
            if (t < 0) {
                t = 0;
            } else  if (t > document.documentElement.clientHeight - that.dom.offsetHeight) {
                t = document.documentElement.clientHeight - that.dom.offsetHeight;
            }

            //var rootX = parseInt(that.dom.css('left')) + (that.dom.width())/2;
            //var rootY = parseInt(that.dom.css('top')) + (that.dom.height())/2;

            that.dom.css({left:l,top:t});
            //console.log(that.line.attr('path'));
            that.drawLine(l,t);
            //$('#direction').html(that.line.attr('path'));

            $('#mindMap').addClass('following');
        }
        function fn2(){
            $(document).unbind({
                mousemove:fn1,
                mouseup:fn2
            });
            $('#mindMap').removeClass('following');
            if (that.dom.releaseCapture) {
                that.dom.releaseCapture();
            }
            that.dom.removeClass('node-moving');
            dataSave();
        }
    })
};
Node.prototype.hoverEvent = function(){
    this.dom.bind({
        mouseenter: function(){
            $(this).css('padding-right','24px');
            $(this).find('.controls').show();
        },
        mouseleave: function(){
            if(!($('#mindMap').attr('class') == 'following')){
                $(this).find('.controls').hide();
                $(this).css('padding-right','0px');
            }
        }
    });
};
Node.prototype.removeEvent = function(){
    var that = this;
    this.dom.find('.node-close').bind('click',function(){
        //删除DOM
        that.dom.fadeOut(100,function(){
            that.dom.remove();
        })
        that.line.remove();
        //that.parentNode.childNodes.;
        for(var i=0;i<that.parentNode.childNodes.length;i++){
            if(that.parentNode.childNodes[i].id == that.id){
                that.parentNode.childNodes.splice(i,1);
            }
        }
        (function removeNode(that){
            if(that.childNodes.length > 0){
                for(var i=0;i<that.childNodes.length;i++){
                    that.childNodes[i].dom.detach();
                    that.childNodes[i].line.remove();
                    for(var k=0;k<nodeList.length;k++){
                        if( nodeList[k].id == that.childNodes[i].id ){
                            nodeList.splice(k,1);
                        }
                    }
                    removeNode(that.childNodes[i]);
                    //that.childNodes[i].removeEvent();
                }
            }
        })(that);
        //删除对象和维护nodeList
        //_.without(nodeList,that);
        for(var i=0;i<nodeList.length;i++){
            if( nodeList[i].id == that.id ){

               nodeList.splice(i,1);
            }
        }
        dataSave();
    });
    //that = null;
};


