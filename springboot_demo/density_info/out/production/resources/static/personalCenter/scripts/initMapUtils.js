

//-------------------------------------地图脚本------------------------------------------
// 创建地图实例
// var map = new BMap.Map("mapcontainer",{enableMapClick:false});          
var map = new BMap.Map("mapcontainer");

// 首都的点给个备份
// var point = new BMap.Point(116.418261, 39.921984);
var point = new BMap.Point(121.22063,31.29188);

// 初始化地图，设置中心点坐标和地图级别
map.centerAndZoom(point, 14);

// 允许滚轮缩放             
map.enableScrollWheelZoom();


// 添加地图类型控件
map.addControl(new BMap.MapTypeControl({
    mapTypes:[
        BMAP_NORMAL_MAP,
        BMAP_HYBRID_MAP
    ]
    ,
    anchor:BMAP_ANCHOR_BOTTOM_LEFT
}));

// 添加默认缩略地图控件
var overView = new BMap.OverviewMapControl();

//右下角，打开
var overViewOpen = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});

map.addControl(overView);          
map.addControl(overViewOpen); 

// 城市控件
var size = new BMap.Size(10, 20);
map.addControl(new BMap.CityListControl({
anchor: BMAP_ANCHOR_TOP_LEFT,
offset: size,
// 切换城市之间事件
// onChangeBefore: function(){
//    alert('before');
// },
// 切换城市之后事件
// onChangeAfter:function(){
//   alert('after');
// }
}));     


// //创建矩形
// var pStart = new BMap.Point(116.392214,39.918985);
// var pEnd = new BMap.Point(116.41478,39.911901);
// var rectangle = new BMap.Polygon([
//     new BMap.Point(pStart.lng,pStart.lat),
//     new BMap.Point(pEnd.lng,pStart.lat),
//     new BMap.Point(pEnd.lng,pEnd.lat),
//     new BMap.Point(pStart.lng,pEnd.lat)
// ], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5}); 



// 根据已知矩形边界画多边形到地图上
// var str1 = "121.211755,121.213336,121.225373,121.229146,121.229433,121.227816,121.224511,121.222894,121.214018,121.213695,121.214953";
// var str2 = "31.2962,31.297712,31.297095,31.296447,31.291078,31.288146,31.284227,31.284937,31.291294,31.291972,31.293114";

// var arr1 = str1.split(",");
// var arr2 = str2.split(",");

// var pTemp = [];

// for(var i = 0; i < arr1.length; i++){
//     pTemp.push(new BMap.Point(arr1[i], arr2[i]));
// }

// var polygonT = new BMap.Polygon(pTemp,{strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5,fillOpacity:0.1});
// map.addOverlay(polygonT);

// // 获取面积
// // alert(BMapLib.GeoUtils.getPolygonArea(rectangle));
// // alert(rectangle.getPath()[0].lng);

// rectangle.addEventListener("click",getAttr);
// function getAttr(){
//     var parr = rectangle.getPath();       
//     for(var i = 0; i < parr.length;i++){
//         alert("marker的位置"+i+"是" + parr[i].lng + "," + parr[i].lat);
//     }
       
// }
// map.addOverlay(rectangle);


// // 动态添加地图空间的监控
// var json_data = [[116.404,39.915],[116.383752,39.91334]];
// json_data.push([116.384502,39.932241]);
// var pointArray = new Array();
// for(var i=0;i<json_data.length;i++){
//     var marker = new BMap.Marker(new BMap.Point(json_data[i][0], json_data[i][1])); // 创建点
//     map.addOverlay(marker);    //增加点
//     pointArray[i] = new BMap.Point(json_data[i][0], json_data[i][1]);
//     marker.addEventListener("click",attribute1);
// }
// //让所有点在视野范围内
// map.setViewport(pointArray);
// //获取覆盖物位置
// function attribute1(e){
//     var p = e.target;
// alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat); 
// }



// 地图绘制
var overlays = [];
// 获取临时单个多边形的边界点集合 getPath() 具体操作函数查GeoUtils
var pArr = [];
var sArea;

// 地图绘制对象后的回调函数
var overlaycomplete = function(e){

    pArr = e.overlay.getPath();
    sArea = BMapLib.GeoUtils.getPolygonArea(e.overlay);

    overlays.push(e.overlay);
    alert("空间量算-所选定区域面积:" + BMapLib.GeoUtils.getPolygonArea(e.overlay) + "平方米");
    alert(overlays.length);
};

// 绘制对象的配置设定
var styleOptions = {
    strokeColor:"red",    //边线颜色。
    fillColor:"blue",      //填充颜色。当参数为空时，圆形将没有填充效果。
    strokeWeight: 2,       //边线的宽度，以像素为单位。
    strokeOpacity: 0.9,    //边线透明度，取值范围0 - 1。
    fillOpacity: 0.1,      //填充的透明度，取值范围0 - 1。
    strokeStyle: 'solid' //边线的样式，solid或dashed。
}

// 实例化鼠标绘制工具
var drawingManager = new BMapLib.DrawingManager(map, {
    isOpen: false, //是否开启绘制模式
    enableDrawingTool: true, //是否显示工具栏
    drawingToolOptions: {
        anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
        offset: new BMap.Size(5, 5), //偏离值
    },
    circleOptions: styleOptions, //圆的样式
    polylineOptions: styleOptions, //线的样式
    polygonOptions: styleOptions, //多边形的样式
    rectangleOptions: styleOptions //矩形的样式
});  


 // 添加鼠标绘制工具监听事件，用于获取绘制结果
drawingManager.addEventListener('overlaycomplete', overlaycomplete);


function clearAll() {
    for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    overlays.length = 0   
}




// ajax请求初期加载过程

// 获取请求接口数据的临时变量
var pointData;
// 请求过程
$.ajax({
type: "get",
url: "http://" + config.mAddress + ":8088/John/api/monitorpoints",
dataType: "json",
data: {},
async: false,
success: function(msg){
    console.log(msg);
    pointData = msg;
},
error: function(){
    alert('服务端异常');
    $('#loading').addClass('hidden');
}
});

//alert(tmp);
var points = pointData;


if(!isSupportCanvas()){
    alert('热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~')
}
//详细的参数,可以查看heatmap.js的文档 https://github.com/pa7/heatmap.js/blob/master/README.md
//参数说明如下:
/* visible 热力图是否显示,默认为true
 * opacity 热力的透明度,1-100
 * radius 势力图的每个点的半径大小   
 * gradient  {JSON} 热力图的渐变区间 . gradient如下所示
 *  {
        .2:'rgb(0, 255, 255)',
        .5:'rgb(0, 110, 255)',
        .8:'rgb(100, 0, 255)'
    }
    其中 key 表示插值的位置, 0~1. 
        value 为颜色值. 
 */
heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
map.addOverlay(heatmapOverlay);
heatmapOverlay.setDataSet({data:points,max:100});

//是否显示热力图
heatmapOverlay.show();
map.panTo(new BMap.Point(121.22063,31.29188));





function openHeatmap(){
    heatmapOverlay.show();
}
function closeHeatmap(){
    heatmapOverlay.hide();
}
function centermap(){
    map.panTo(new BMap.Point(121.22063,31.29188));
}
// closeHeatmap();
function setGradient(){
    /*格式如下所示:
    {
        0:'rgb(102, 255, 0)',
        .5:'rgb(255, 170, 0)',
        1:'rgb(255, 0, 0)'
    }*/
    var gradient = {};
    var colors = document.querySelectorAll("input[type='color']");
    colors = [].slice.call(colors,0);
    colors.forEach(function(ele){
        gradient[ele.getAttribute("data-key")] = ele.value; 
    });
    heatmapOverlay.setOptions({"gradient":gradient});
}
//判断浏览区是否支持canvas
function isSupportCanvas(){
    var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
}
//-------------------------------------地图脚本------------------------------------------








    initPage();

    // ajax请求初期加载过程
    // 获取请求接口数据的临时变量
    var polygonData;
    // 请求过程
    $.ajax({
    type: "get",
    url: "http://" + config.mAddress + ":8088/John/api/polygon/selectAll",
    dataType: "json",
    data: {},
    async: false,
    success: function(msg){
        console.log(msg);
        polygonData = msg;
    },
    error: function(){
        alert('服务端异常,获取Polygon信息失败');
        $('#loading').addClass('hidden');
    }
    });

    tableDraw(polygonData);



// initPage()
function initPage(){
    // 处理session脚本内容
    if(sessionStorage.session_id!='null' && sessionStorage.session_id!=null){
        document.getElementById("username").text=' '+ sessionStorage.username + ' ';
    }else{
        document.getElementById("username").text=' '+ "login" + ' ';
        sessionStorage.clear();
        window.location.href="../login/login.html";        
    }    
}

// 登出操作
function logout(){
    sessionStorage.clear();
    window.location.href="../login/login.html";
}

// onclick="pendding()"
function pendding(){
    alert("系统开发中...敬请期待");
}

// 绘制表格填充数据 data 承接 ajax获取的内容
function tableDraw(data){

    var oTable = document.getElementById("polygon_density");  
    var oTbody = oTable.getElementsByTagName("tbody"); 
          
    for (var i = 0; i < data.length; i++) {
        var mtr = document.createElement("tr"); 
        oTbody[0].appendChild(mtr);

        var mtd1 = document.createElement("td");
        mtd1.appendChild(document.createTextNode(data[i].pname));
        mtr.appendChild(mtd1);

        var mtd2 = document.createElement("td");
        mtd2.appendChild(document.createTextNode(data[i].s));
        mtr.appendChild(mtd2);

        var mtd3 = document.createElement("td");
        mtd3.appendChild(document.createTextNode(data[i].n));
        mtr.appendChild(mtd3); 

        var mtd4 = document.createElement("td");
        mtd4.appendChild(document.createTextNode(data[i].e));
        mtr.appendChild(mtd4); 

        var mtd5 = document.createElement("td");
        mtd5.appendChild(document.createTextNode(data[i].grade));
        mtr.appendChild(mtd5); 

        var mtd6 = document.createElement("td");

        // mtr.setAttribute("onclick","tableItemClick("+i+")");

        var opt1 = document.createElement("input");
        opt1.setAttribute("type","image");
        opt1.setAttribute("src","images/add.png");
        // opt1.setAttribute("onclick","addbutton()");

        var opt2 = document.createElement("input");
        opt2.setAttribute("type","image");
        opt2.setAttribute("src","images/delete.png");
        // opt2.setAttribute("onclick","deletebutton()");

        var opt3 = document.createElement("input");
        opt3.setAttribute("type","image");
        opt3.setAttribute("src","images/edit.png");
        // opt3.setAttribute("onclick","editbutton()");

        mtd6.appendChild(opt1);
        mtd6.appendChild(opt2);
        mtd6.appendChild(opt3);
                
        mtr.appendChild(mtd6); 
                
    };    
}


function tableItemClick(i){
    // alert("id为"+ id + "号的message等待编辑");
    alert(polygonData[i].pname);
}

function addbutton(){
    alert("增加");
}

function deletebutton(){
    alert("删除");
}

function editbutton(){
    alert("编辑");
}




// 请求api保存多边形区域对象
function savePolygon(){

    var xArr = pArr[0].lng;
    var yArr = pArr[0].lat;

    for (var i = 1; i < pArr.length; i++) {
        xArr = xArr + "," + pArr[i].lng;
        yArr = yArr + "," + pArr[i].lat;
    };

    $.ajax({
    type: "post",
    url: "http://" + config.mAddress + ":8088/John/api/polygon/padd",
    dataType: "json",
    data: {
    "token":sessionStorage.session_id,
    "sArea":sArea,
    "lngArr":xArr,
    "latArr":yArr,
    "pname": $("#pname").val(),
    "safe_info": $("#safe_info").val(),
    "initN": $("#initN").val()
    },
    async: false,
    success: function(data){
        alert(data);
        return 0;
    },
    error: function(){
        alert('服务端异常');
        $('#loading').addClass('hidden');
    }
    })
}






