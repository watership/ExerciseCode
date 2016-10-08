var r;
var nodeList = [];
var root;
var dataJson;
var dataArray = [];
var localStorageData;
var dataIndex = -1;

$(function(){
    //Create SVG
    r = Raphael("mindMap", 4000, 3000);
    r.style="overflow: hidden; position: relative;";

    //Create Node Tree
    root = new Tree($('#root'));
    root.init();

    //Get local Storage
    localStorageData = JSON.parse(localStorage.getItem('localStorageData'));
    if(localStorageData){
        jsonToObj(localStorageData);
    }

    //menu button
    $('#mindUndo').bind('click',function(){
        if(dataIndex>1){
            dataIndex--;
            //deleteAllNode();
            if(dataArray[dataIndex].node){
                jsonToObj(dataArray[(dataIndex-1)]);
            }else{
                throw Error('no node');
            }
        }
    });
    $('#mindRedo').bind('click',function(){
        if(dataIndex<(dataArray.length-1)){
            dataIndex++;
            //deleteAllNode();
            jsonToObj(dataArray[(dataIndex-1)]);
        }
    });
    $('#mindClear').bind('click',function(){
        deleteAllNode();
        dataSave();
    });


});