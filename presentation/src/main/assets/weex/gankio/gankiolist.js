// { "framework": "Vue"} 

/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(1)
)

/* script */
__vue_exports__ = __webpack_require__(2)

/* template */
var __vue_template__ = __webpack_require__(3)
__vue_options__ = __vue_exports__ = __vue_exports__ || {}
if (
  typeof __vue_exports__.default === "object" ||
  typeof __vue_exports__.default === "function"
) {
if (Object.keys(__vue_exports__).some(function (key) { return key !== "default" && key !== "__esModule" })) {console.error("named exports are not supported in *.vue files.")}
__vue_options__ = __vue_exports__ = __vue_exports__.default
}
if (typeof __vue_options__ === "function") {
  __vue_options__ = __vue_options__.options
}
__vue_options__.__file = "/Users/sunjian/Desktop/github/CrazyDaily/presentation/src/main/assets/weex/gankio/gankiolist.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-fedffb30"
__vue_options__.style = __vue_options__.style || {}
__vue_styles__.forEach(function (module) {
  for (var name in module) {
    __vue_options__.style[name] = module[name]
  }
})
if (typeof __register_static_styles__ === "function") {
  __register_static_styles__(__vue_options__._scopeId, __vue_styles__)
}

module.exports = __vue_exports__
module.exports.el = 'true'
new Vue(module.exports)


/***/ }),
/* 1 */
/***/ (function(module, exports) {

module.exports = {
  "panel1": {
    "width": "690",
    "height": "220",
    "marginLeft": "30",
    "marginTop": "20",
    "marginBottom": "20",
    "alignItems": "center",
    "flexDirection": "row",
    "boxShadow": "0px 3px 12px 4px #80888888",
    "borderRadius": "20"
  },
  "panel2": {
    "width": "600",
    "height": "220",
    "flexDirection": "column"
  },
  "text1": {
    "marginTop": "20",
    "lines": 1,
    "marginLeft": "40",
    "fontSize": "46",
    "color": "#333333",
    "textOverflow": "ellipsis"
  },
  "text2": {
    "lines": 1,
    "marginTop": "20",
    "marginLeft": "40",
    "fontSize": "36",
    "color": "#666666"
  },
  "text3": {
    "lines": 1,
    "marginTop": "35",
    "marginLeft": "40",
    "fontSize": "26",
    "color": "#999999"
  },
  "icon": {
    "marginTop": "20",
    "marginLeft": "30",
    "width": "20",
    "height": "26"
  },
  "loading": {
    "width": 750,
    "display": "flex",
    "MsFlexAlign": "center",
    "WebkitAlignItems": "center",
    "WebkitBoxAlign": "center",
    "alignItems": "center"
  },
  "indicator-text": {
    "marginBottom": "16",
    "color": "#666666",
    "fontSize": "42",
    "textAlign": "center"
  },
  "indicator": {
    "marginBottom": "16",
    "height": "40",
    "width": "40",
    "color": "#FF4081"
  }
}

/***/ }),
/* 2 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

var modal = weex.requireModule('modal');
var stream = weex.requireModule('stream');
var GANK_IO_BASE_URL = 'http://gank.io/api/random/data/';
var gankIoUrl = '' + GANK_IO_BASE_URL + weex.config.type + '/10';

/* harmony default export */ __webpack_exports__["default"] = ({
  data: function data() {
    return {
      loadinging: false,
      lists: []
    };
  },

  methods: {
    getLocalTime: function getLocalTime(time) {
      var date = new Date(time);
      return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    },
    isWhoEmpty: function isWhoEmpty(who) {
      if (who) {
        return who;
      }
      return '神秘大佬';
    },
    itemclick: function itemclick(item) {
      weex.requireModule('crazyDaily').router(item.url);
    },
    loadmore: function loadmore() {
      var self = this;
      self.loadinging = true;
      setTimeout(function () {
        stream.fetch({
          method: 'GET',
          url: gankIoUrl,
          type: 'json'
        }, function (ret) {
          if (!ret.ok) {
            modal.toast({ message: 'request failed', duration: 1 });
          } else {
            ret.data.results.forEach(function (value) {
              return self.lists.push(value);
            });
          }
          self.loadinging = false;
        });
      }, 200);
    }
  },
  created: function created() {
    var self = this;
    stream.fetch({
      method: 'GET',
      url: gankIoUrl,
      type: 'json'
    }, function (ret) {
      if (!ret.ok) {
        modal.toast({ message: 'request failed', duration: 1 });
      } else {
        ret.data.results.forEach(function (value) {
          return self.lists.push(value);
        });
      }
    });
  }
});

/***/ }),
/* 3 */
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('list', {
    staticClass: ["list"],
    attrs: {
      "loadmoreoffset": "10"
    },
    on: {
      "loadmore": _vm.loadmore
    }
  }, [_vm._l((_vm.lists), function(item) {
    return _c('cell', {
      staticClass: ["cell"],
      appendAsTree: true,
      attrs: {
        "append": "tree"
      },
      on: {
        "click": function($event) {
          _vm.itemclick(item)
        }
      }
    }, [_c('div', {
      staticClass: ["panel1"]
    }, [_c('div', {
      staticClass: ["panel2"]
    }, [_c('text', {
      staticClass: ["text1"],
      attrs: {
        "lines": "1"
      }
    }, [_vm._v(_vm._s(item.desc))]), _c('text', {
      staticClass: ["text2"],
      attrs: {
        "lines": "1"
      }
    }, [_vm._v(_vm._s('作者：' + _vm.isWhoEmpty(item.who)))]), _c('text', {
      staticClass: ["text3"],
      attrs: {
        "lines": "1"
      }
    }, [_vm._v(_vm._s('发布时间：' + _vm.getLocalTime(item.publishedAt)))])]), _c('image', {
      staticClass: ["icon"],
      attrs: {
        "src": "mipmap://ic_go.png"
      }
    })])])
  }), _c('loading', {
    staticClass: ["loading"],
    attrs: {
      "display": _vm.loadinging ? 'show' : 'hide'
    }
  }, [_c('loading-indicator', {
    staticClass: ["indicator"]
  }), _c('text', {
    staticClass: ["indicator-text"]
  }, [_vm._v("正在加载中...")])])], 2)
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ })
/******/ ]);