webpackJsonp([1],{"CYx/":function(e,t){},Hwa4:function(e,t){},NHnr:function(e,t,o){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=o("//Fk"),n=o.n(i),r=o("7+uW"),s={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var a=o("VU/8")({name:"App"},s,!1,function(e){o("Hwa4")},null,null).exports,c=o("/ocq"),d=o("mvHQ"),u=o.n(d),l=o("NYxO");r.default.use(l.a);var m=new l.a.Store({state:{token:localStorage.getItem("token")||null,curStudent:localStorage.getItem("curStudent")||null},mutations:{login:function(e,t){localStorage.setItem("token",t),e.token=t},logout:function(e){localStorage.removeItem("token"),localStorage.removeItem("curStudent"),e.token=null,e.curStudent=null},setCurStudent:function(e,t){localStorage.setItem("curStudent",u()(t)),e.curStudent=u()(t)}},actions:{}}),h=o("woOf"),f=o.n(h),p={name:"PreferenceSelect",data:function(){return{student:{},firstChoice:"",secondChoice:"",choiceOverview:[],majorInfo:[],filesInfo:[],choiceInfo:[],researchMajor:[],profMajorOne:[],profMajorTwo:[],modifiedForm:{},adjustDegreeType:"",adjustMajor:""}},created:function(){this.student=JSON.parse(this.$store.state.curStudent),this.adjustDegreeType=this.student["adjust-degree-type"],this.adjustMajor=this.student["adjust-major"],this.modifiedForm["first-choice-major"]=this.student["first-choice-major"],this.modifiedForm["second-choice-major"]=this.student["second-choice-major"],this.modifiedForm["first-choice-direction"]=this.student["first-choice-direction"],this.modifiedForm["second-choice-direction"]=this.student["second-choice-direction"],""!==this.student["last-modify-time"]&&(""!==this.student["first-choice-direction"]&&null!==this.student["first-choice-direction"]?this.firstChoice=this.student["first-choice-major"]+"－"+this.student["first-choice-direction"]:this.firstChoice=this.student["first-choice-major"],""!==this.student["second-choice-direction"]&&null!==this.student["second-choice-direction"]?this.secondChoice=this.student["second-choice-major"]+"－"+this.student["second-choice-direction"]:this.secondChoice=this.student["second-choice-major"]),this.getMajorsInfo(),this.getFilesInfo(),this.getChoicesOverview()},methods:{getPersonInfo:function(){var e=this;this.$axios.get("/student/info").then(function(t){e.$store.commit("setCurStudent",t.data),e.student=f()({},e.student,JSON.parse(e.$store.state.curStudent)),e.adjustDegreeType=e.student["adjust-degree-type"],e.adjustMajor=e.student["adjust-major"]}).catch(function(e){console.log(e)})},getChoicesOverview:function(){var e=this;this.$axios.get("/student/choices-overview").then(function(t){e.choiceInfo=t.data}).catch(function(t){console.log(t),e.$message.alert("刷新失败！")})},getFilesInfo:function(){var e=this;this.$axios.get("/student/files").then(function(t){e.filesInfo=t.data}).catch(function(e){console.log(e)})},getFileById:function(e,t){var o=this;this.$axios.get("/student/files/"+t.id,{headers:{"Content-Type":"application/octet-stream"},responseType:"blob"}).then(function(e){o.downloadFile(e.data,t.name)}).catch(function(e){console.log(e)})},downloadFile:function(e,t){var o=new Blob([e]);if("download"in document.createElement("a")){var i=document.createElement("a");i.download=t,i.style.display="none",i.href=URL.createObjectURL(o),document.body.appendChild(i),i.click(),URL.revokeObjectURL(i.href),document.body.removeChild(i)}else navigator.msSaveBlob(o,t)},getMajorsInfo:function(){var e=this;this.$axios.get("/student/major").then(function(t){if(console.log(t),e.majorInfo=t.data,"学术学位"===e.student["degree-type"])for(var o=0;o<e.majorInfo.length;o++)e.majorInfo[o].major!==e.student.major&&e.researchMajor.push(e.majorInfo[o]);else for(var i=0;i<e.majorInfo.length;i++)e.profMajorOne.push(e.majorInfo[i]),e.profMajorTwo.push(e.majorInfo[i])}).catch(function(e){console.log(e)})},submitInfo:function(){var e=this;if("学术学位"===this.student["degree-type"]&&(this.modifiedForm["first-choice-major"]=this.student["first-choice-major"],this.modifiedForm["first-choice-direction"]=this.student["first-choice-direction"]),this.modifiedForm["first-choice-major"]===this.modifiedForm["second-choice-major"])return"学术学位"===this.student["dgree-type"]?void this.$message.error("第一志愿和第二志愿的专业应不同！请重新选择！"):void this.$message.error("第一志愿和第二志愿的学科方向应不同！请重新选择！");this.modifiedForm["adjust-major"]=this.student["adjust-major"],this.modifiedForm["adjust-degree-type"]=this.student["adjust-degree-type"],this.$axios.post("/student/choice",this.modifiedForm).then(function(t){e.$message.success("提交成功"),e.getPersonInfo(),e.getChoicesOverview()}).catch(function(t){console.log(t),e.$message.error("提交失败")})},changeFirstChoice:function(e){var t=e.split("－")[0].trim();this.profMajorTwo=[];for(var o=0;o<this.majorInfo.length;o++)this.majorInfo[o].major!==t&&this.profMajorTwo.push(this.majorInfo[o]);null!==this.secondChoice&&t===this.secondChoice.split("－")[0].trim()&&this.$message.warning("第一志愿和第二志愿的学科方向应不同！请重新选择！"),this.modifiedForm["first-choice-major"]=t,1===e.split("－").length?this.modifiedForm["first-choice-direction"]="":this.modifiedForm["first-choice-direction"]=e.split("－")[1].trim()},changeSecChoice:function(e){var t=e.split("－")[0].trim();-1!==this.student.department.indexOf("软件")&&"学术学位"===this.student["degree-type"]&&(this.student["adjust-degree-type"]="不调剂专硕"!==t),"专业学位"===this.student["degree-type"]?null!==this.firstChoice&&t===this.firstChoice.split("－")[0].trim()?this.$message.error("第一志愿和第二志愿的学科方向应不同！请重新选择！"):(this.modifiedForm["second-choice-major"]=t,1===e.split("－").length?this.modifiedForm["second-choice-direction"]="":this.modifiedForm["second-choice-direction"]=e.split("－")[1].trim()):t===this.student.major?this.$message.error("第一志愿和第二志愿的专业应不同！请重新选择"):(this.modifiedForm["second-choice-major"]=t,1===e.split("－").length?this.modifiedForm["second-choice-direction"]="":this.modifiedForm["second-choice-direction"]=e.split("－")[1].trim())}}},g={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-main",[o("el-row",[-1!==e.student.department.indexOf("计算机科学")?o("h2",[e._v("2020年复旦大学计算机科学技术学院研究生复试志愿填报")]):o("h2",[e._v("2020年复旦大学软件学院研究生复试志愿填报")])]),e._v(" "),o("el-row",{attrs:{gutter:20}},[o("el-col",{attrs:{span:12}},[o("div",{staticClass:"base_info"},[o("p",[e._v("准考证号："+e._s(e.student["ticket-number"]))]),e._v(" "),o("p",[e._v("考生姓名："+e._s(e.student.name))]),e._v(" "),o("p",[e._v("报考学位类型："+e._s(e.student["degree-type"]))]),e._v(" "),o("p",[e._v("报考专业："+e._s(e.student.major))])])]),e._v(" "),o("el-col",{attrs:{span:12}},[o("div",[o("span",{staticStyle:{padding:"5%","font-size":"1.17em","margin-block-start":"1em","margin-block-end":"1em","margin-inline-start":"0px","margin-inline-end":"0px","font-weight":"bold"}},[e._v("名额与填报志愿人数")]),e._v(" "),o("el-button",{staticStyle:{background:"#afb4db",border:"none"},attrs:{type:"primary",size:"mini"},on:{click:e.getChoicesOverview}},[e._v("刷新")]),e._v(" "),o("el-table",{attrs:{data:e.choiceInfo}},[o("el-table-column",{attrs:{prop:"major",label:"志愿方向"}}),e._v(" "),o("el-table-column",{attrs:{prop:"spots",label:"统考招生名额"}}),e._v(" "),o("el-table-column",{attrs:{prop:"first-choice-major-numbers",label:"第一志愿人数"}})],1)],1)])],1),e._v(" "),o("el-row",{attrs:{gutter:20}},[o("el-col",{attrs:{span:12}},[o("div",{staticStyle:{"padding-left":"10px"}},[o("h3",[e._v("相关文件列表")]),e._v(" "),o("el-table",{attrs:{data:e.filesInfo}},[o("el-table-column",{attrs:{prop:"name",label:"文件名"}}),e._v(" "),o("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-button",{staticStyle:{background:"#afb4db",border:"none"},attrs:{size:"mini"},on:{click:function(o){return e.getFileById(t.$index,t.row)}}},[e._v("下载")])]}}])})],1)],1)]),e._v(" "),o("el-col",{attrs:{span:12}},[o("div",[o("h3",[e._v("请选择第一志愿")]),e._v(" "),"学术学位"===e.student["degree-type"]?o("p",[e._v(e._s(e.student.major))]):o("el-select",{attrs:{placeholder:"第一志愿"},on:{change:e.changeFirstChoice},model:{value:e.firstChoice,callback:function(t){e.firstChoice=t},expression:"firstChoice"}},e._l(e.profMajorOne,function(e){return o("el-option",{key:e.display,attrs:{label:e.display,value:e.display}})}),1),e._v(" "),o("br"),e._v(" "),o("h3",[e._v("请选择第二志愿")]),e._v(" "),"学术学位"===e.student["degree-type"]?o("el-select",{attrs:{placeholder:"第二志愿"},on:{change:e.changeSecChoice},model:{value:e.secondChoice,callback:function(t){e.secondChoice=t},expression:"secondChoice"}},e._l(e.researchMajor,function(e){return o("el-option",{key:e.display,attrs:{label:e.display,value:e.display}})}),1):o("el-select",{attrs:{placeholder:"第二志愿"},on:{change:e.changeSecChoice},model:{value:e.secondChoice,callback:function(t){e.secondChoice=t},expression:"secondChoice"}},e._l(e.profMajorTwo,function(e){return o("el-option",{key:e.display,attrs:{label:e.display,value:e.display}})}),1),e._v(" "),o("br"),e._v(" "),-1!==e.student.department.indexOf("计算机科学")?o("div",[o("el-checkbox",{model:{value:e.student["adjust-major"],callback:function(t){e.$set(e.student,"adjust-major",t)},expression:"student['adjust-major']"}},[e._v("愿意调剂到其他学科方向")]),e._v(" "),"学术学位"===e.student["degree-type"]?o("el-checkbox",{model:{value:e.student["adjust-degree-type"],callback:function(t){e.$set(e.student,"adjust-degree-type",t)},expression:"student['adjust-degree-type']"}},[e._v("愿意调剂为专业学位硕士")]):e._e()],1):o("div",["学术学位"===e.student["degree-type"]?o("el-checkbox",{model:{value:e.student["adjust-major"],callback:function(t){e.$set(e.student,"adjust-major",t)},expression:"student['adjust-major']"}},[e._v("愿意调剂到其他学科方向")]):e._e()],1),e._v(" "),o("br"),e._v(" "),o("el-button",{staticStyle:{background:"#afb4db",border:"none"},attrs:{type:"primary"},on:{click:e.submitInfo}},[e._v("提交")])],1)])],1),e._v(" "),o("el-row",[e.student["last-modify-time"]?o("div",[null!==e.student["first-choice-direction"]&&""!==e.student["first-choice-direction"]?o("p",[e._v("当前提交第一志愿："+e._s(e.student["first-choice-major"])+" - "+e._s(e.student["first-choice-direction"]))]):o("p",[e._v("当前提交第一志愿："+e._s(e.student["first-choice-major"]))]),e._v(" "),null!==e.student["second-choice-direction"]&&""!==e.student["second-choice-direction"]?o("p",[e._v("当前提交第二志愿："+e._s(e.student["second-choice-major"])+" - "+e._s(e.student["second-choice-direction"]))]):o("p",[e._v("当前提交第二志愿："+e._s(e.student["second-choice-major"]))]),e._v(" "),-1===e.student.department.indexOf("软件")||"学术学位"===e.student["degree-type"]?o("div",[!0===e.adjustMajor?o("p",[e._v("愿意调剂到其他学科方向")]):o("p",{staticStyle:{color:"firebrick"}},[e._v("不愿意调剂到其他学科方向")]),e._v(" "),!0===e.adjustDegreeType?o("p",[e._v("愿意调剂为专业硕士")]):o("p",{staticStyle:{color:"firebrick"}},[e._v("不愿意调剂为专业硕士")])]):e._e(),e._v(" "),o("p",[e._v("当前志愿提交时间："+e._s(e.student["last-modify-time"]))])]):e._e()]),e._v(" "),o("br"),e._v(" "),o("br")],1)},staticRenderFns:[]};var v=o("VU/8")(p,g,!1,function(e){o("rs2S")},"data-v-263b5caf",null).exports,j={name:"Login",data:function(){return{loginForm:{ticketNumber:"",name:"",idNumber:""},rules:{ticketNumber:[{required:!0,message:"请输入准考证号！",trigger:"blur"}],name:[{required:!0,message:"请输入姓名！",trigger:"blur"}],idNumber:[{required:!0,message:"请输入考生报名号！",trigger:"blur"}]},loading:!1}},methods:{login:function(){var e=this;this.$axios.post("/student/login",{"ticket-number":this.loginForm.ticketNumber,name:this.loginForm.name,"id-number":this.loginForm.idNumber}).then(function(t){200===t.status?(e.$store.commit("login",t.data.token),e.$store.commit("setCurStudent",t.data.student),e.$router.replace({path:"/preference"})):e.$message.error("登录失败")}).catch(function(t){console.log(t),e.$message.error("登录失败")})}}},b={render:function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{attrs:{id:"base_login"}},[o("el-form",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"login_container",attrs:{model:e.loginForm,rules:e.rules,"label-position":"left","label-width":"0px"}},[o("h3",{staticClass:"login_title"},[e._v("报考计算机学院研究生志愿填报")]),e._v(" "),o("el-form-item",{attrs:{prop:"ticketNumber"}},[o("el-input",{attrs:{type:"text","auto-complete":"off",placeholder:"准考证号"},model:{value:e.loginForm.ticketNumber,callback:function(t){e.$set(e.loginForm,"ticketNumber",t)},expression:"loginForm.ticketNumber"}})],1),e._v(" "),o("el-form-item",{attrs:{prop:"name"}},[o("el-input",{attrs:{type:"text","auto-complete":"off",placeholder:"姓名"},model:{value:e.loginForm.name,callback:function(t){e.$set(e.loginForm,"name",t)},expression:"loginForm.name"}})],1),e._v(" "),o("el-form-item",{attrs:{prop:"idNumber"}},[o("el-input",{attrs:{type:"text","auto-complete":"off",placeholder:"考生报名号"},model:{value:e.loginForm.idNumber,callback:function(t){e.$set(e.loginForm,"idNumber",t)},expression:"loginForm.idNumber"}})],1),e._v(" "),o("el-form-item",{staticStyle:{width:"100%"}},[o("el-button",{staticStyle:{background:"#afb4db",border:"none","text-align":"center"},attrs:{type:"primary"},on:{click:e.login}},[e._v("登录")])],1)],1)],1)},staticRenderFns:[]};var _=o("VU/8")(j,b,!1,function(e){o("CYx/")},"data-v-65cd1b06",null).exports;r.default.use(c.a);var y=new c.a({base:"preference/cs",routes:[{path:"/",name:"Index",redirect:"/login",component:_},{path:"/preference",name:"Preference",component:v,meta:{requireAuth:!0}},{path:"/login",name:"Login",component:_}]});y.beforeEach(function(e,t,o){e.matched.some(function(e){return e.meta.requireAuth})?m.state.token?o():o({path:"/login",query:{redirect:e.fullPath}}):o()});var k=o("zL8q"),F=o.n(k),x=(o("tvR6"),o("mtWM"));r.default.prototype.$axios=x,x.defaults.baseURL="/preference/se/api",x.defaults.withCredentials=!0,x.defaults.headers.post["Content-Type"]="application/json;charset=UTF-8",r.default.config.productionTip=!1,r.default.use(F.a),x.interceptors.request.use(function(e){return m.state.token&&(e.headers.Authorization="Bearer "+m.state.token),e},function(e){return n.a.reject(e)}),x.interceptors.response.use(function(e){return e},function(e){return e&&(m.commit("logout"),"/login"!==y.currentRoute.path&&y.replace({path:"/login",query:{redirect:y.currentRoute.path}})),n.a.reject(e.response.data)}),new r.default({el:"#app",router:y,store:m,components:{App:a},template:"<App/>"})},rs2S:function(e,t){},tvR6:function(e,t){},txPn:function(e,t,o){var i=o("zNUS");i.mock("/api/student/login","post",function(){return{token:"test",student:{"ticket-number":"9876",name:"张三","dgree-type":"学术学位",department:"计算机科学技术学院",major:"计算机系统结构",direction:"分布式存储系统与网络","id-number":"12345","first-choice-major":"计算机系统结构","first-choice-direction":"分布式存储系统与网络","second-choice-major":"","second-choice-direction":"","adjust-major":!1,"adjust-degree-type":!1,"last-modify-time":""}}});i.mock("/api/student/choices-overview","get",function(){var e=[];return e.push({major:"计算机系统结构",spots:"40","first-choice-major-numbers":"0"}),e.push({major:"计算机软件与理论",spots:"50","first-choice-major-numbers":"0"}),{overview:e}});i.mock("/api/student/majors","get",function(){var e=[];return e.push({"degree-type":"学术学位",major:"计算机系统结构",direction:"下一代网络体系结构",display:"计算机系统结构－下一代网络体系结构",spots:"40"}),e.push({"degree-type":"专业学位",major:"计算机科学与技术",direction:"智能媒体计算",display:"计算机科学与技术－智能媒体计算",spots:"30"}),e.push({"degree-type":"专业学位",major:"人类表型组",direction:"",display:"人类表型组",spots:"30"}),{resp:e}});i.mock("/api/student/files","get",function(){var e=[];return e.push({id:"1",name:"test.txt"}),e.push({id:"2",name:"test2.txt"}),{resp:e}})}},["NHnr"]);
//# sourceMappingURL=app.4374b3ae6be92fe5eee4.js.map