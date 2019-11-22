            mui.plusReady(function(){
                var pages = ["html/index/index.html","html/message/message.html","html/class/class.html"];
                var arr = document.getElementsByClassName("mui-tab-item")
                var styles = {
                    top:"45px",
                    bottom:"51px"
                }
                var pageArr = [];
                var title=document.getElementById('title');
                var slef = plus.webview.currentWebview();
                for(var i=0; i<arr.length; i++){
                    // 有几个选项卡，需要创建几个子页面
                    var page = plus.webview.create(pages[i],pages[i],styles);
                    pageArr.push(page);
                    !function(i){
                        arr[i].addEventListener("tap",function(){
                            // 让当前页面(i)显示，不是当前页面隐藏
                            for(var j=0; j<pageArr.length; j++){
                                if(j!=i) pageArr[j].hide();
                                else pageArr[j].show();
                            }
                            // 设置标题
                            //title.innerHTML = this.querySelector('.mui-tab-label').innerHTML;
                            /* 让新创建的webview，追加合并到当前的窗口上。合并成一个窗口。
                             * 目的：将父子窗口合并成一个页面，实现同开同关的效果。 避免点击返回安监室，子页面先关闭，而父页面的头部和尾部没有关闭的BUG。
                             */
                            slef.append(pageArr[i]);
                            
                            
                        })
                    }(i);
                }
                // 默认触发第0个选项卡的tap事件。
                mui.trigger(arr[0],"tap");
            });

		var main,menu;
		var showMenu = false;

		mui.init({
			swipeBack: false,
			beforeback: back
		});

		function back() {
			if (showMenu) {
				//菜单处于显示状态，返回键应该先关闭菜单,阻止主窗口执行mui.back逻辑；
				closeMenu();
				return false;
			} else {
				//菜单处于隐藏状态，执行返回时，要先close菜单页面，然后继续执行mui.back逻辑关闭主窗口；
				menu.close('none');
				return true;
			}
		}
		//plusReady事件后，自动创建menu窗口；
		mui.plusReady(function() {
			main = plus.webview.currentWebview();
			main.addEventListener('maskClick', closeMenu);
			//setTimeout的目的是等待窗体动画结束后，再执行create webview操作，避免资源竞争，导致窗口动画不流畅；
			setTimeout(function () {
				//侧滑菜单默认隐藏，这样可以节省内存；
				menu = mui.preload({
					id: 'main-menu',
					url: 'main-menu.html',
					styles: {
						left: 0,
						width: '70%',
						zindex: 9997
					}
				});
			},300);
			
		});
		/**
		 * 显示菜单菜单
		 */
		function openMenu() {
			if (!showMenu) {
				//侧滑菜单处于隐藏状态，则立即显示出来；
				
				//显示完毕后，根据不同动画效果移动窗体；			
				menu.show('none', 0, function() {
					menu.setStyle({
						left: '0%',
						transition: {
							duration: 150
						},
					});	
				});
				//显示遮罩
				main.setStyle({mask:"rgba(0,0,0,0.5)"});
				showMenu = true;
			}
		}
		

		/**
		 * 关闭侧滑菜单
		 */
		function closeMenu () {
			_closeMenu();
			//关闭遮罩
			main.setStyle({mask:"none"});
		}
		
		/**
		 * 关闭侧滑菜单（业务部分）
		 */
		function _closeMenu() {
			if (showMenu) {
				//关闭遮罩；
				
				//主窗体开始侧滑；
				menu.setStyle({
					left: '-70%',
					transition: {
						duration: 150
					}
				});
					
				
				//等窗体动画结束后，隐藏菜单webview，节省资源；
				setTimeout(function() {
					menu.hide();
				}, 200);
				//改变标志位
				showMenu = false;
			}
		}	

		 //点击左上角图标，打开侧滑菜单；
		document.querySelector('.mui-icon-bars').addEventListener('tap', openMenu);
		 //在android4.4中的swipe事件，需要preventDefault一下，否则触发不正常
		 //故，在dragleft，dragright中preventDefault
		window.addEventListener('dragright', function(e) {
			e.detail.gesture.preventDefault();
		});
		window.addEventListener('dragleft', function(e) {
			e.detail.gesture.preventDefault();
		});
		 //主界面向左滑动，若菜单已显示，则关闭菜单；否则，不做任何操作；
		window.addEventListener("swipeleft", closeMenu);
		
		 //menu页面向左滑动，关闭菜单；
		window.addEventListener("menu:swipeleft", closeMenu);

		//重写mui.menu方法，Android版本menu按键按下可自动打开、关闭侧滑菜单；
		mui.menu = function() {
			if (showMenu) {
				closeMenu();
			} else {
				openMenu();
			}
		}