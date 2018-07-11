
// 初始化页面设置
initPage();

// marker位置信息的POI点数组-全局变量
var PoiArr;

// 热力图点位置信息-全局变量
var points;

// 区域多边形面信息-全局变量
var polygonData;

// 手动添加 地图绘制多边形数组-全局变量
var overlays = [];

// 后端数据 地图绘制多边形数组-全局变量
var backlays = [];

// 后端数据 地图绘制marker点数组-全局变量
var backPoilays = [];

// 获取临时单个多边形的边界点集合-全局变量
// getPath() 具体操作函数查GeoUtils
var pArr = [];

// 所选多边形点-全局变量
var sArea;



//----------------------------------- 空间解析交互过程 -----------------------------------
// 创建地图实例
var map = new BMap.Map("mapcontainer");          

// 首都的点给个备份
// var point = new BMap.Point(116.418261, 39.921984);

// 我济的点给个备份
// var point = new BMap.Point(121.22063,31.29188);

var point = new BMap.Point(sessionStorage.config_lng,sessionStorage.config_lat);

// 初始化地图，设置中心点坐标和地图级别
map.centerAndZoom(point, 16);
// 允许滚轮缩放             
map.enableScrollWheelZoom();

// //添加地图类型控件
// map.addControl(new BMap.MapTypeControl({
//     mapTypes:[
//         BMAP_NORMAL_MAP,
//         BMAP_HYBRID_MAP
//     ]}));


//添加默认缩略地图控件
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


// 热力图点API请求-ajax请求初期加载过程

// 获取请求接口数据的临时变量
var tmp;
// 请求过程
$.ajax({
type: "get",
url: "http://" + config.mAddress + ":8088/John/api/monitorpointsByUid?token="+sessionStorage.session_id,
dataType: "json",
data: {},
async: false,
success: function(msg){
    console.log(msg);
    tmp = msg;
},
error: function(){
    alert('服务端异常');
    $('#loading').addClass('hidden');
}
});

//alert(tmp);
// 赋值给全局变量-显示热力图的点数据
points = tmp;

// 赋值给全局变量-显示位置信息的POI点数组
PoiArr = tmp;

// 热力图绘制
heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
map.addOverlay(heatmapOverlay);
heatmapOverlay.setDataSet({data:points,max:100});

// 是否显示热力图-默认打开
heatmapOverlay.show();

// 页面加载默认地图跳转至:

// map.panTo(new BMap.Point(121.22063,31.29188));
map.panTo(new BMap.Point(sessionStorage.config_lng,sessionStorage.config_lat));



// 根据已知矩形边界画多边形到地图上
// var str1 = "121.211755,121.213336,121.225373,121.229146,121.229433,121.227816,121.224511,121.222894,"+
// "121.214018,121.213695,121.214953";
// var str2 = "31.2962,31.297712,31.297095,31.296447,31.291078,31.288146,31.284227,31.284937,31.291294,"+
// "31.291972,31.293114";
// var arr1 = str1.split(",");
// var arr2 = str2.split(",");

// 逐一push解析
// var pTemp = [];
// for(var i = 0; i < arr1.length; i++){
//     pTemp.push(new BMap.Point(arr1[i], arr2[i]));
// }

// 构造polygon对象
// var polygonT = new BMap.Polygon(pTemp,{
// 	strokeColor:"blue", strokeWeight:2, 
// 	strokeOpacity:0.5,
// 	fillOpacity:0.1});
// map.addOverlay(polygonT);

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


// 地图绘制对象后的回调函数
var overlaycomplete = function(e){

    pArr = e.overlay.getPath();
    sArea = BMapLib.GeoUtils.getPolygonArea(e.overlay);

    overlays.push(e.overlay);
    // alert("空间量算-所选定区域面积:" + BMapLib.GeoUtils.getPolygonArea(e.overlay) + "平方米");
    // alert(overlays.length);

    // 触发弹出层准备编辑存储
    drawOver();
};

 // 添加鼠标绘制工具监听事件，用于获取绘制结果
drawingManager.addEventListener('overlaycomplete', overlaycomplete);



// 请求API接口-返回多边形区域数据-ajax请求初期加载过程 
$.ajax({
type: "post",
url: "http://" + config.mAddress + ":8088/John/api/polygon/selectByUid",
dataType: "json",
data: {"token":sessionStorage.session_id},
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

// 绘制表格
buildTable(polygonData);





//----------------------------------- 空间解析交互过程 -----------------------------------


// 初始化通用脚本
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

// 等待开发部署 onclick="pendding()"
function pendding(){
    alert("系统开发中...敬请期待");
}

// 打开热力图
function openHeatmap(){
    heatmapOverlay.show();
}

// 关闭热力图
function closeHeatmap(){
    heatmapOverlay.hide();
}

// 地图移动到中心位置定位
function centermap(){
    // map.panTo(new BMap.Point(121.22063,31.29188));
    map.panTo(new BMap.Point(sessionStorage.config_lng,sessionStorage.config_lat));
}

// 清除图层绘制
function clearAll() {
    for(var i = 0; i < overlays.length; i++){
        map.removeOverlay(overlays[i]);
    }
    overlays.length = 0;

    pArr.length = 0;
    sArea = 0;
}


// 绘制增删改表格
function buildTable (data) {

	// 获取管理器实例
    var oTable = document.getElementById("polygon_density");  
    // 找出修改的主体实例
    var oTbody = oTable.getElementsByTagName("tbody"); 
          
    // 遍历解析过程      
    for (var i = 0; i < data.length; i++) {
        var mtr = document.createElement("tr"); 
        oTbody[0].appendChild(mtr);

        var mtd1 = document.createElement("td");
        mtd1.appendChild(document.createTextNode(data[i].pname));
        mtr.appendChild(mtd1);

        var mtd2 = document.createElement("td");
        var s = parseFloat(data[i].s).toFixed(2);
        mtd2.appendChild(document.createTextNode(s));
        mtr.appendChild(mtd2);

        var mtd3 = document.createElement("td");
        mtd3.appendChild(document.createTextNode(data[i].n));
        mtr.appendChild(mtd3); 

        var mtd4 = document.createElement("td");
        var e = parseFloat(data[i].e).toFixed(2);
        mtd4.appendChild(document.createTextNode(e));
        mtr.appendChild(mtd4); 

        var mtd5 = document.createElement("td");
        mtd5.appendChild(document.createTextNode(data[i].grade));
        mtr.appendChild(mtd5); 

        var mtd6 = document.createElement("td");

        // mtr.setAttribute("onclick","tableItemClick("+i+")");

        // var opt1 = document.createElement("input");
        // opt1.setAttribute("type","image");
        // opt1.setAttribute("src","images/add.png");
        // opt1.setAttribute("onclick","addbutton()");

        var opt2 = document.createElement("input");
        opt2.setAttribute("type","image");
        opt2.setAttribute("src","images/delete.png");
        opt2.setAttribute("onclick","deletebutton("+i+")");

        var opt3 = document.createElement("input");
        opt3.setAttribute("type","image");
        opt3.setAttribute("src","images/edit.png");
        opt3.setAttribute("onclick","editbutton("+i+")");

        // mtd6.appendChild(opt1);
        mtd6.appendChild(opt2);
        mtd6.appendChild(opt3);
                
        mtr.appendChild(mtd6); 
                
    };    
}


// 全局操作多边形数组所先定的下标
var arrIndex;

//  管理器表格事件-增加操作
function addbutton(){
    alert("增加");
}

// 管理器表格事件-删除操作 
function deletebutton(i){
    // alert("删除");

    // 全局变量根据捕捉事件获取的元素下标
    arrIndex = i;
    deleteApi();
}



// 管理器表格事件-编辑操作
function editbutton(i){

	// 绑定保存健功能为更新操作 00000000000
	var savebnt = document.getElementById("savebnt");
	savebnt.setAttribute("onclick","savePolygon(0)");

	// 全局变量根据捕捉事件获取的元素下标
	arrIndex = i;

	// 弹出层控制-数据解析和传递
	$("#mymodal").modal("toggle");

	var ipt0 = document.getElementById("pname");
	ipt0.value = polygonData[i].pname;	

	var ipt1 = document.getElementById("pS");
	ipt1.value = polygonData[i].s;

	var ipt2 = document.getElementById("pbound");
	ipt2.value = "lng:" + polygonData[i].lng_s +"\n lat:" + polygonData[i].lat_s;

	var ipt3 = document.getElementById("lng_s");
	ipt3.value = polygonData[i].lng_s;

	var ipt4 = document.getElementById("lat_s");
	ipt4.value = polygonData[i].lat_s;

	var ipt5 = document.getElementById("initN");
	ipt5.value = polygonData[i].n;

	var ipt6 = document.getElementById("safer_info");
	ipt6.value = polygonData[i].safer_info;

    // alert("编辑");
}

function tableItemClick(i){
    // alert("id为"+ id + "号的message等待编辑");
    alert(polygonData[i].pname);
}

// 绘制结束事件触发解析数据过程
function drawOver() {

	// 绑定保存健功能为插入操作 11111111111
	var savebnt = document.getElementById("savebnt");
	savebnt.setAttribute("onclick","savePolygon(1)");

	// 解析边界点 生成数组
    var xArr = pArr[0].lng;
    var yArr = pArr[0].lat;
    for (var i = 1; i < pArr.length; i++) {
        xArr = xArr + "," + pArr[i].lng;
        yArr = yArr + "," + pArr[i].lat;
    };

    // 弹框控制传输解析数据
    $("#mymodal").modal("toggle");
    // 面积
    var ipt1 = document.getElementById("pS");
    ipt1.value = sArea;
    // 边界整合信息
    var ipt2 = document.getElementById("pbound");
    ipt2.value = "lng:"+xArr+"\n lat:" + yArr;
    // 经度边界
	var ipt3 = document.getElementById("lng_s");
	ipt3.value = xArr + "";
	// 纬度边界
	var ipt4 = document.getElementById("lat_s");
	ipt4.value = yArr + "";

}

// 	弹出控制层 提交控制函数 - 请求api保存多边形区域对象
function savePolygon(opt){
	if (opt == 0) {
		if (arrIndex >= 0 && arrIndex < polygonData.length) {
			// 更改操作
			// alert(polygonData[arrIndex]);
			updateApi();
		}else {
			alert("编辑参数有误，请刷新");
		}

	}else if (opt == 1){
		// 插入操作
		// alert("this is insert!");
		insertApi();
	}
}

// 对应服务端请求接口-API多边形插入操作函数
function insertApi(){

    var xArr = pArr[0].lng;
    var yArr = pArr[0].lat;

    for (var i = 1; i < pArr.length; i++) {
        xArr = xArr + "," + pArr[i].lng;
        yArr = yArr + "," + pArr[i].lat;
    };

    var res;
    $.ajax({
    type: "post",
    url: "http://" + config.mAddress + ":8088/John/api/polygon/padd",
    dataType: "json",
    data: {
    "token":sessionStorage.session_id,
    "sArea":parseFloat(sArea),
    "lngArr":xArr,
    "latArr":yArr,
    "pname": $("#pname").val(),
    "safer_info": $("#safer_info").val(),
    "initN": Number($("#initN").val())
    },
    async: false,
    success: function(data){
    	res = data;
        alert("保存成功!");
        console.log(res);

        $("#mymodal").modal("hide");
        clearAll();
         window.location.reload();
    },
    error: function(){
        alert('服务端异常,请检查网络并重新提交');
        // $('#loading').addClass('hidden');
    }
    });
}

// 对应服务端请求接口-API多边形更新操作函数
function updateApi(){

	polygonData[arrIndex].pname = $("#pname").val();
	polygonData[arrIndex].safer_info = $("#safer_info").val();


    $.ajax({
    type: "post",
    url: "http://" + config.mAddress + ":8088/John/api/polygon/update",
    dataType: "json",
    data: {
    	"id":polygonData[arrIndex].id,
    	"pname":polygonData[arrIndex].pname,
    	"uid":polygonData[arrIndex].uid,
    	"n":polygonData[arrIndex].n,
    	"s":polygonData[arrIndex].s,
    	"e":polygonData[arrIndex].e,
    	"grade":polygonData[arrIndex].grade,
    	"safer_info":polygonData[arrIndex].safer_info,
    	"creat_time":polygonData[arrIndex].creat_time,
    	"update_time":polygonData[arrIndex].update_time,
    	"lng_s":polygonData[arrIndex].lng_s,
    	"lat_s":polygonData[arrIndex].lat_s
    },
    async: false,
    success: function(data){
    	res = data;
        alert("变更成功!");
        // 前端控制打日志
        console.log(res);

        // 隐藏弹出层
        $("#mymodal").modal("hide");

        //   变更后刷新页面
        window.location.reload();
    },
    error: function(){
        alert('服务端异常,请检查网络并重新提交');
        // $('#loading').addClass('hidden');
    }
    });		
}

// 对应服务端请求接口-API多边形更新操作函数
function deleteApi(){

	// alert(Number(polygonData[arrIndex].id));
    $.ajax({
    type: "post",
    url: "http://" + config.mAddress + ":8088/John/api/polygon/deleteById",
    dataType: "json",
    data: {
    	"id":Number(polygonData[arrIndex].id)
    },
    async: false,
    success: function(data){
    	res = data;
    	if (res == 1) {
    		alert("删除成功!");
    	}else{
    		alert("删除异常!");
    	}
        
        // 前端控制打日志
        console.log(res);

        //   变更后刷新页面
        window.location.reload();
    },
    error: function(){
        alert('服务端异常,请检查网络并重新提交');
        // $('#loading').addClass('hidden');
    }
    });	
}

// 绘制服务端传来的polygons
function drawAllP(){
	for (var j = 0; j < polygonData.length; j++) {
		// 根据已知矩形边界画多边形到地图上
		var str1 = polygonData[j].lng_s;
		var str2 = polygonData[j].lat_s;

		var arr1 = str1.split(",");
		var arr2 = str2.split(",");

		// 逐一push解析
		var pTemp = [];
		for(var i = 0; i < arr1.length; i++){
		    pTemp.push(new BMap.Point(arr1[i], arr2[i]));
		}

		// 构造polygon对象
		var polygonT = new BMap.Polygon(pTemp,{
			strokeColor:"blue", strokeWeight:2, 
			strokeOpacity:0.5,
			fillOpacity:0.1});
		backlays.push(polygonT);
		map.addOverlay(polygonT);

        polygonT.addEventListener("click",pclick);

		// alert(i);
	}		
}
function pclick(e){
    var p = e.target;
    p.enableEditing();
    alert("开启编辑模式");
    // alert("所选定区域对应多边形实体-包含拓扑关系点的集合显示如下图");
}

// 清除服务端传来的polygons
function clearAllP(){
	for (var i = 0; i < backlays.length; i++) {
		map.removeOverlay(backlays[i]);
	}
}

// 弹出层取消按键
function cancelPolygon(){
	// 清理已绘制多边形
	clearAll();
}


// 绘制位置麻点
function drawPoint(){

    // 动态添加地图空间的监控
    // var json_data = [[116.404,39.915],[116.383752,39.91334]];
    // json_data.push([116.384502,39.932241]);

    // 生成临时数组控制视野范围
    // var pointArray = new Array();

    for(var i=0;i<PoiArr.length;i++){
        var marker = new BMap.Marker(new BMap.Point(PoiArr[i].lng, PoiArr[i].lat)); // 创建点
        //增加点
        map.addOverlay(marker);  

        //  填充视野控制数组
        // pointArray[i] = new BMap.Point(json_data[i][0], json_data[i][1]);

        backPoilays.push(marker);

        //跳动的动画
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); 

        // 设置麻点监听事件
        marker.addEventListener("click",poiClickListerner);
    }

    //让所有点在视野范围内
    // map.setViewport(pointArray);
  
}
// 设置marker点击事件
function poiClickListerner(e){
    var p = e.target;
    alert("该用户的位置是- 经度:" + p.getPosition().lng + ", 纬度:" + p.getPosition().lat); 
}
// 清除绘制的marker点
function clearPoint(){
    for (var i = 0; i < backPoilays.length; i++) {
        map.removeOverlay(backPoilays[i]);
    };
}  


function backUpFunction(){
    // 解析新增数据 添加至全局变量
    var ad ={creat_time:res.creat_time,
        e:res.e,
        grade:res.grade,
        id:res.id,
        lat_s:res.lat_s,
        lng_s:res.lng_s,
        n:res.n,
        pname:res.pname,
        s:res.s,
        safer_info:res.safer_info,
        uid:res.uid,
        update_time:res.update_time};
    alert(polygonData.length);  
    // push到全局变量
    polygonData.push(ad);
    alert(polygonData.length);  
    // 刷新管理表
    buildTable(polygonData);


    // 全部多边形的请求过程
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
}



