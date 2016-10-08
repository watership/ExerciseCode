//class extend function
function extend(Child, Parent) {
    var F = function(){};
    F.prototype = Parent.prototype;
    Child.prototype = new F();
    Child.prototype.constructor = Child;
    Child.uber = Parent.prototype;
}

//r.path function
function pathML(a,b,c,d){
    c = c || '';
    d = d || '';
    var ml = 'M'+a+' '+b+'L'+c+' '+d;
    return ml;
}


//node color
var nodeColor = ['#b98fef','#dc2f58','#b2b2c0','#ff5528','#ffaa1e','#78c882','#50cbca','#05b9f0'];

//生成GUID
function mathGuid (){
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
        return v.toString(16);
    }).toUpperCase();
}

function uiNode(x,y,text,color,ml,id,parentId){
    var htmlNode = '<div class="node-container"><div class="node" contenteditable="true"></div>' +
        '<div class="controls"><div class="node-close"></div><div class="node-move"></div></div></div>';
    $('#mindMap').append(htmlNode);
    var lastNode = $('.node-container:last-of-type');
    lastNode.find('.node').text(text);
    lastNode.css({'left':x,'top':y,'background-color':color});
    var line = r.path();
    line.attr('stroke-width','2px');
    line.attr('path',ml);
    var node = new Node(root,lastNode,line,id);
    node.parentId = parentId;
    nodeList.push(node);
}

//匹配父级和子集
function resetUiNode(){
        for(var j=0;j<nodeList.length;j++){
            for(var k=0;k<nodeList.length;k++){
                if( nodeList[j].parentId == nodeList[k].id ){
                    nodeList[j].parentNode = nodeList[k];
                    nodeList[k].childNodes.push(nodeList[j]);
                    nodeList[j].line.attr('stroke',nodeList[k].dom.css('background-color'));
                }else if(nodeList[j].parentId == 'root'){
                    nodeList[j].parentNode = root;
                    nodeList[j].line.attr('stroke',root.dom.css('background-color'));
                }else{
                    //throw new Error('No parent node');
                }
            }
            nodeList[j].init();
        }
}

//把json数据渲染成成思维导图
function jsonToObj(data){
    deleteAllNode();
    for(var i=0;i<data.node.length;i++){
        var x = data.node[i].x;
        var y = data.node[i].y;
        var text = data.node[i].text;
        //var text = '新建节点';
        var color = data.node[i].color;
        var ml = data.node[i].ml;
        var id = data.node[i].id;
        var parentId = data.node[i].parentId;
        uiNode(x,y,text,color,ml,id,parentId);
    }
    $('#root').find('.node').text(data.tree[0].text);
    resetUiNode();
    //dataSave();
}


//把当前状态保存成json数据
function objToJson(array){
    var jsonNode = '{"node":[';
    for(var i=0;i<array.length;i++){
        var x = parseInt(array[i].dom.css('left'));
        var y = parseInt(array[i].dom.css('top'));
        //var text = array[i].dom.find('node').text();
        var text = array[i].dom.find('.node').text();
        var nodecolor = array[i].dom.css('background-color');
        var ml = array[i].line.attr('path');
        var id = array[i].id;
        var parentId = array[i].parentNode.id;
        var n ='{'+'"x":'+ x +',"y":'+ y +',"text":"'+ text +'","color":"'+
            nodecolor +'","ml":"'+ ml +'","id":"'+ id +'","parentId":"'+ parentId +'"}';
        if( i==(array.length-1) ){
            jsonNode += n;
        }else{
            jsonNode += n;
            jsonNode +=',';
        }
    }
    var treeText = $('#root').find('.node').text();
    jsonNode +='],"tree":[{"text":"'+ treeText +'"}]}';
    console.log(jsonNode);
    return $.parseJSON(jsonNode);

}

//把当前的状态保存在列表中
function dataSave(){
    dataJson = objToJson(nodeList);
    dataArray.push(dataJson);
    dataIndex = dataArray.length;
    console.log(dataIndex);
    localStorage.setItem('localStorageData',JSON.stringify(dataJson));
}

//删除所有节点
function deleteAllNode(){
    for(var i=0;i<nodeList.length;i++){
        nodeList[i].dom.remove();
        nodeList[i].line.remove();
    }
    nodeList = [];
    $('#root').find('.node').text('思维导图');
}


