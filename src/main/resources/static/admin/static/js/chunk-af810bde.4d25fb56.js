(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-af810bde"],{3585:function(t,i,s){"use strict";var a=s("6c2b"),e=s.n(a);e.a},"35a5":function(t,i,s){"use strict";s.r(i);var a=function(){var t=this,i=t.$createElement,s=t._self._c||i;return s("div",{staticClass:"openImg"},[s("div",{staticClass:"upload"},[s("el-upload",{staticClass:"avatar-uploader",attrs:{action:"https://jsonplaceholder.typicode.com/posts/","show-file-list":!1,"on-success":t.handleAvatarSuccess,"before-upload":t.beforeAvatarUpload}},[t.imageUrl?s("img",{staticClass:"avatar",attrs:{src:t.imageUrl}}):s("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1),t._v(" "),s("div",{staticClass:"items"},[s("div",{staticClass:"left"},t._l(t.imgArr,(function(i,a){return s("div",{key:a,class:{item:!0,check:a==t.indexNum},on:{click:function(i){return t.setItem(a)}}},[t._v(t._s(a+1))])})),0),t._v(" "),s("div",{staticClass:"right"},[s("div",{staticClass:"item del",on:{click:t.del}},[t._v("\n                -\n            ")]),t._v(" "),s("div",{staticClass:"item add",on:{click:t.add}},[t._v("\n                +\n            ")])])])])},e=[],n={name:"",data:function(){return{imageUrl:"",imgArr:[{img:""}],indexNum:0}},methods:{handleAvatarSuccess:function(t,i){this.imageUrl=URL.createObjectURL(i.raw)},beforeAvatarUpload:function(){},init:function(){this.imageUrl=this.imgArr[this.indexNum].img},setItem:function(t){this.indexNum=t,this.imageUrl=this.imgArr[t].img},del:function(){this.imgArr.splice(this.indexNum,1),this.indexNum=0,this.init()},add:function(){this.imgArr.push({img:""})}}},c=n,r=(s("3585"),s("2877")),l=Object(r["a"])(c,a,e,!1,null,"369604d8",null);i["default"]=l.exports},"6c2b":function(t,i,s){}}]);