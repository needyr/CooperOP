## 德阳二院 > 山东众阳his

his要求callback函数必须同步调用（不能异步调用callback），否则无法拦截开医嘱

使用IE showModalDiarylog 阻塞式弹窗，弹出窗口访问访问HIS服务器上的ycbstransit.html，
该页面内嵌iframe访问审查结果界面detail.html，以此解决showModalDiarylog 父子窗口不能进行跨域传值的问题

需要注意一下问题：
1. showModalDiarylog 只能在ie上运行，或旧的google浏览器，37+版本google不支持该弹窗
2. 需要放置中转界面ycbstransit.html 到his服务器上并提供访问链接
3. 医院医策服务器上的ycfun.js位于bsintf下，为svn项目中的bsintf/mzyy下的ycfun.js
