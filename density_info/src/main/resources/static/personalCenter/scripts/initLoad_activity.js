    // ajax请求初期加载过程

    // 切换线上测试地址
    // var address = "127.0.0.1";

    // 获取请求接口数据的临时变量
    var tmp;
    // 请求过程
    $.ajax({
    type: "get",
    url: "http://" + config.mAddress + ":8088/John/api/activity/selectAll",
    dataType: "json",
    data: {},
    async: false,
    success: function(msg){
        console.log(msg);
        tmp = msg;
    },
    error: function(){
        alert('服务端异常,获取Polygon信息失败');
        $('#loading').addClass('hidden');
    }
    });

for (var i = tmp.length - 1; i >= 0; i--) {

			// 总体模块
			var media_stream = document.createElement("div");
			media_stream.setAttribute("class", "media stream");
			// var txt1 = document.createTextNode("fuck " + i);
			// media_stream.appendChild(txt1);

			// <a/> 左边头像
			var labela = document.createElement("a");
			labela.setAttribute("class","media-avatar medium pull-left");

				// <img/>
				var labelimg = document.createElement("img");
				labelimg.setAttribute("src","images/user.png");
				labela.appendChild(labelimg);

			media_stream.appendChild(labela);

			// <div/> media-body 右侧信息体
			var stream_body = document.createElement("div");
			stream_body.setAttribute("class","media-body");

				// <div/> media_headline 头信息 用户名 + 日期
				var media_headline = document.createElement("div");
				media_headline.setAttribute("class","stream-headline");

				  // <h5/> username 
				  var labelh5 = document.createElement("h5");
				  labelh5.setAttribute("class", "stream-author");

					  var uname1 = document.createTextNode(tmp[i].uname);
					  labelh5.appendChild(uname1);

					  // <small/> date
					  var labelsmall = document.createElement("small");
						  // var date = document.createTextNode("08 July, 2017 " + i);
						  
    					  var date = document.createTextNode(new Date(tmp[i].create_time).toLocaleString());
						  labelsmall.appendChild(date);
					  labelh5.appendChild(labelsmall);

				  media_headline.appendChild(labelh5);

				// <div/> stream-text
				var labelcontent = document.createElement("div");
				labelcontent.setAttribute("class","stream-text");

					// content
					// var textcontext = document.createTextNode("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type." + i);
					var textcontext = document.createTextNode(tmp[i].content);
					labelcontent.appendChild(textcontext);

				media_headline.appendChild(labelcontent);


				// <div/> 	stream-options
				var stream_options = document.createElement("div");
				stream_options.setAttribute("class","stream-options");
					// <a/>
					var labelopta1 = document.createElement("a");
					labelopta1.setAttribute("class","btn btn-small");
						var labelopti0 = document.createElement("i");
							labelopti0.setAttribute("class","icon-thumbs-up shaded");
						var ac = document.createTextNode("赞同");

						labelopta1.appendChild(labelopti0);
						labelopta1.appendChild(ac);

					// <a/>
					var labelopta2 = document.createElement("a");
					labelopta2.setAttribute("class","btn btn-small");
						var labelopti1 = document.createElement("i");
							labelopti1.setAttribute("class","icon-reply shaded");
						var rep = document.createTextNode("回复");

						labelopta2.appendChild(labelopti1);
						labelopta2.appendChild(rep);

					// <a/>
					var labelopta3 = document.createElement("a");
					labelopta3.setAttribute("class","btn btn-small");
						var labelopti2 = document.createElement("i");
							labelopti2.setAttribute("class","icon-retweet shaded");
						var trans = document.createTextNode("转发");

						labelopta3.appendChild(labelopti2);
						labelopta3.appendChild(trans);							
						

				stream_options.appendChild(labelopta1);
				stream_options.appendChild(labelopta2);
				stream_options.appendChild(labelopta3);

				stream_body.appendChild(media_headline);
				stream_body.appendChild(stream_options);
				

			media_stream.appendChild(stream_body);

			


			var testdiv1 = document.getElementById("divTemplate");
			testdiv1.appendChild(media_stream);

		};

		var mediafoot = document.createElement("div");
		mediafoot.setAttribute("class","media stream load-more");
		mediafoot.setAttribute("onclick","refresh()");
				var lafoot = document.createElement("a");
					var footi = document.createElement("i");
					footi.setAttribute("class","icon-refresh shaded");
					var tmore = document.createTextNode("更多");
				lafoot.appendChild(footi);
				lafoot.appendChild(tmore);

		mediafoot.appendChild(lafoot);	

 		var testdiv2 = document.getElementById("divTemplate");
		testdiv2.appendChild(mediafoot);


    // 处理session脚本内容
    if(sessionStorage.session_id!='null' && sessionStorage.session_id!=null){
        document.getElementById("username").text=' '+ sessionStorage.username + ' ';
    }else{
        document.getElementById("username").text=' '+ "login" + ' ';
        sessionStorage.clear();
        window.location.href="../login/login.html";        
    }

    // 登出操作
    function logout(){
        sessionStorage.clear();
        window.location.href="../login/login.html";
    }
