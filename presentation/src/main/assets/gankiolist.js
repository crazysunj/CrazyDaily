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
__vue_options__.__file = "/Users/sunjian/Desktop/github/CrazyDaily/presentation/src/main/assets/gankiolist.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-7cfac2b8"
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
    "boxShadow": "0px 5px 20px 6px #80888888",
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
    "color": "#333333"
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
  "text4": {
    "lines": 1,
    "marginTop": "20",
    "marginLeft": "30",
    "fontSize": "40",
    "color": "#333333"
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

const modal = weex.requireModule('modal');
const stream = weex.requireModule('stream');
const GANK_IO_BASE_URL = "http://gank.io/api/random/data/";

/* harmony default export */ __webpack_exports__["default"] = ({
  data() {
    return {
      lists: []
    };
  },
  methods: {
    getLocalTime: function (time) {
      var date = new Date(time);
      return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
    },
    isWhoEmpty: function (who) {
      if (who) {
        return who;
      }
      return "神秘大佬 ";
    },
    itemclick: function (item, index) {
      modal.toast({ message: '你点击了' + item.desc + ",该项是第" + (index + 1), duration: 1 });
    },
    loadmore(event) {
      modal.toast({ message: 'loadmore', duration: 1 });
      const self = this;
      setTimeout(() => {
        stream.fetch({
          method: 'GET',
          url: GANK_IO_BASE_URL,
          type: 'json'
        }, function (ret) {
          if (!ret.ok) {
            modal.toast({ message: 'request failed', duration: 1 });
          } else {
            const length = ret.data.results.length;
            for (let i = 0; i < length; i++) {
              self.lists.push(ret.data.results[i]);
            }
          }
        });
      }, 600);
    }
  },
  created: function () {
    const self = this;
    const gank_io_url = GANK_IO_BASE_URL + weex.config.type + "/10";
    modal.toast({ message: gank_io_url, duration: 1 });
    stream.fetch({
      method: 'GET',
      url: gank_io_url,
      type: 'json'
    }, function (ret) {
      if (!ret.ok) {
        modal.toast({ message: 'request failed', duration: 1 });
      } else {
        self.lists = ret.data.results;
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
  }, _vm._l((_vm.lists), function(item, index) {
    return _c('cell', {
      staticClass: ["cell"],
      appendAsTree: true,
      attrs: {
        "append": "tree"
      },
      on: {
        "click": function($event) {
          _vm.itemclick(item, index)
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
    }, [_vm._v(_vm._s("作者：" + _vm.isWhoEmpty(item.who)))]), _c('text', {
      staticClass: ["text3"],
      attrs: {
        "lines": "1"
      }
    }, [_vm._v(_vm._s("发布时间：" + _vm.getLocalTime(item.publishedAt)))])]), _c('text', {
      staticClass: ["text4"]
    }, [_vm._v(">")])])])
  }))
},staticRenderFns: []}
module.exports.render._withStripped = true

/***/ })
/******/ ]);