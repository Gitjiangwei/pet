(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3f8c8c86"],{1271:function(e,t,a){},"900d":function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"tipOff"},[a("div",{staticClass:"tools"},[a("div",{staticClass:"search"},[a("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入内容"},model:{value:e.searchVal,callback:function(t){e.searchVal=t},expression:"searchVal"}},[a("el-select",{attrs:{slot:"prepend",placeholder:"请选择"},slot:"prepend",model:{value:e.select,callback:function(t){e.select=t},expression:"select"}},e._l(e.optionConfig,(function(e){return a("el-option",{key:e.value,attrs:{label:e.name,value:e.value}})})),1),e._v(" "),a("el-button",{attrs:{slot:"append",icon:"el-icon-search"},slot:"append"})],1)],1),e._v(" "),a("el-select",{attrs:{placeholder:"请选择"},model:{value:e.type,callback:function(t){e.type=t},expression:"type"}},e._l(e.options,(function(e){return a("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),e._v(" "),a("el-table",{staticStyle:{width:"100%"},attrs:{border:"",data:e.tableData}},[a("el-table-column",{attrs:{prop:"id",label:"id",width:"80"}}),e._v(" "),a("el-table-column",{attrs:{prop:"name",label:"作者",width:"180"}}),e._v(" "),a("el-table-column",{attrs:{prop:"date",label:"时间",width:"180"}}),e._v(" "),a("el-table-column",{attrs:{prop:"content",label:"简介"}}),e._v(" "),a("el-table-column",{attrs:{width:"270",label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(a){return e.examine(t.$index,t.row)}}},[e._v("审核")]),e._v(" "),t.row.istuijian?a("el-button",{attrs:{type:"primary",size:"mini"}},[e._v("推荐到首页")]):e._e(),e._v(" "),t.row.istuijian?e._e():a("el-button",{attrs:{type:"primary",size:"mini"}},[e._v("取消推荐")]),e._v(" "),a("el-button",{attrs:{type:"danger",size:"mini"},on:{click:function(a){return e.unadopt(t.$index,t.row)}}},[e._v("删除")])]}}])})],1),e._v(" "),a("div",{staticClass:"pagination"},[a("el-pagination",{attrs:{background:"","page-sizes":[10,25,40,50],"page-size":25,"prev-text":"上一页","next-text":"下一页",layout:"sizes, prev, pager, next",total:e.total}})],1)],1)},l=[],i={name:"",data:function(){return{searchVal:"",select:1,tableData:[{id:"1",date:"2016-05-02",name:"王小虎",nickname:"昵称",content:"上海市普陀区金沙江路 1518 弄",from:"微信",phone:"19276876542",istuijian:!0,img:"https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"},{id:"2",date:"2016-05-04",name:"王小虎",nickname:"昵称",content:"上海市普陀区金沙江路 1517 弄",from:"手机",istuijian:!0,phone:"19276876542"},{id:"3",date:"2016-05-01",name:"王小虎",nickname:"昵称",content:"上海市普陀区金沙江路 1519 弄",from:"微信",istuijian:!0,phone:"19276876542"},{id:"4",date:"2016-05-03",name:"王小虎",nickname:"昵称",content:"上海市普陀区金沙江路 1516 弄",from:"微信",istuijian:!1,phone:"19276876542"}],optionConfig:[{name:"姓名",value:1},{name:"手机号",value:2}],total:1e3,options:[{label:"待确认",value:0},{label:"已通过",value:1},{label:"未通过",value:2},{label:"推荐用户",value:3}],type:0}},methods:{examine:function(e,t){this.$router.push({path:"/accusation/info/".concat(t.id)})},unadopt:function(){var e=this;this.$prompt("请输入审核失败原因","失败原因",{confirmButtonText:"确定",cancelButtonText:"取消"}).then((function(t){var a=t.value;e.$message({type:"success",message:"你的邮箱是: "+a})})).catch((function(){e.$message({type:"info",message:"取消输入"})}))}}},o=i,s=(a("ec86"),a("2877")),c=Object(s["a"])(o,n,l,!1,null,"16f8eb7d",null);t["default"]=c.exports},ec86:function(e,t,a){"use strict";var n=a("1271"),l=a.n(n);l.a}}]);