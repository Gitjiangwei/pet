(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-652f4936"],{a39e:function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"lotteryLog"},[a("el-table",{directives:[{name:"show",rawName:"v-show",value:!e.isEditor,expression:"!isEditor"}],staticStyle:{width:"100%"},attrs:{data:e.tableData1,border:""}},[a("el-table-column",{attrs:{prop:"date",label:"日期"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"操作",width:"200px"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"success",size:"small"},on:{click:function(a){return e.handleClick(t.row)}}},[e._v("更多")]),e._v(" "),a("el-button",{attrs:{type:"danger",size:"small"}},[e._v("移除")])]}}])})],1),e._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:e.isEditor,expression:"isEditor"}]},[a("div",{staticClass:"tools"},[a("el-row",[a("el-col",{attrs:{span:12}},[a("div",{staticClass:"grid-content bg-purple"},[a("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入ID/电话"},model:{value:e.searchVal,callback:function(t){e.searchVal=t},expression:"searchVal"}},[a("el-button",{attrs:{slot:"append",icon:"el-icon-search"},slot:"append"})],1)],1)]),e._v(" "),a("el-col",{attrs:{span:5}},[a("div",{staticClass:"grid-content bg-purple-light"})]),e._v(" "),a("el-col",{attrs:{span:7}},[a("div",{staticClass:"grid-content bg-purple"},[a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.activeName,callback:function(t){e.activeName=t},expression:"activeName"}},e._l(e.options,(function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1)])],1),e._v(" "),a("div",{staticClass:"search"})],1),e._v(" "),e._m(0),e._v(" "),a("div",{staticClass:"table"},[a("el-table",{staticStyle:{width:"100%"},attrs:{data:e.tableData,border:""}},[a("el-table-column",{attrs:{prop:"date",label:"日期",width:"130"}}),e._v(" "),a("el-table-column",{attrs:{prop:"id",label:"ID",width:"180"}}),e._v(" "),a("el-table-column",{attrs:{prop:"name",label:"名称",width:"180"}}),e._v(" "),a("el-table-column",{attrs:{prop:"phone",label:"电话",width:"100"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址"}}),e._v(" "),a("el-table-column",{attrs:{prop:"noun",label:"几等奖",width:"80"}}),e._v(" "),"first"==e.activeName?a("el-table-column",{attrs:{prop:"code",label:"领奖码",width:"180"}}):e._e(),e._v(" "),a("el-table-column",{attrs:{prop:"courierNum",label:"快递单号"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-input",{attrs:{placeholder:"请输入快递单号"},model:{value:t.row.number,callback:function(a){e.$set(t.row,"number",a)},expression:"scope.row.number"}})]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"express",label:"快递公司"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-input",{attrs:{placeholder:"请输入快递公司"},model:{value:t.row.mail,callback:function(a){e.$set(t.row,"mail",a)},expression:"scope.row.mail"}})]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"courierNum",label:"备注"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return["first"==e.activeName?a("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["admin"],expression:"['admin']"}],attrs:{type:"success"}},[e._v("确定发货")]):e._e(),e._v(" "),"second"==e.activeName?a("el-button",{directives:[{name:"permission",rawName:"v-permission",value:["admin"],expression:"['admin']"}],attrs:{type:"success"}},[e._v("已发")]):e._e()]}}])})],1)],1),e._v(" "),a("div",{staticClass:"tools"},[a("el-button",{on:{click:function(t){e.isEditor=!1}}},[e._v("返回")])],1)])],1)},s=[function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"nums"},[a("span",[e._v("中奖人数：1000")]),e._v(" "),a("span",[e._v("参加人数：1000")]),e._v(" "),a("span",[e._v("比赛作品：1000")]),e._v(" "),a("span",[e._v("进出次数：1000")])])}],i={name:"",data:function(){return{options:[{value:"first",label:"奖品未发"},{value:"second",label:"奖品已发"},{value:"all",label:"全部"}],isEditor:!1,tableData1:[{date:"2020.3.20"},{date:"2020.4.1"}],searchVal:"",activeName:"first",tableData:[{noun:"1",id:"asndsa123",avatar:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",name:"张某某",works:"123",number:15635647896,address:"南京市******"},{noun:"1",id:"asndsa123",avatar:"https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",name:"张某某",works:"123",number:15635647896,address:"南京市******"}],imgArr:[{img:""},{img:""},{img:""},{img:""},{img:""},{img:""},{img:""},{img:""},{img:""}],indexNum:0,imageUrl:"",showImg:""}},mounted:function(){this.init()},methods:{handleClick:function(){this.isEditor=!0},init:function(){this.showImg=this.imgArr[this.indexNum].img},setItem:function(e){this.indexNum=e,this.showImg=this.imgArr[e].img},handleAvatarSuccess:function(e,t){this.imageUrl=URL.createObjectURL(t.raw)},beforeAvatarUpload:function(){}}},n=i,r=(a("fb19"),a("2877")),o=Object(r["a"])(n,l,s,!1,null,"2658b389",null);t["default"]=o.exports},b918:function(e,t,a){},fb19:function(e,t,a){"use strict";var l=a("b918"),s=a.n(l);s.a}}]);