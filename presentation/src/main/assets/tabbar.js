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
/******/ 	return __webpack_require__(__webpack_require__.s = 11);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
var global = module.exports = typeof window != 'undefined' && window.Math == Math ? window : typeof self != 'undefined' && self.Math == Math ? self
// eslint-disable-next-line no-new-func
: Function('return this')();
if (typeof __g == 'number') __g = global; // eslint-disable-line no-undef

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

module.exports = function (it) {
  return (typeof it === 'undefined' ? 'undefined' : _typeof(it)) === 'object' ? it !== null : typeof it === 'function';
};

/***/ }),
/* 2 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// Thank's IE8 for his funny defineProperty
module.exports = !__webpack_require__(3)(function () {
  return Object.defineProperty({}, 'a', { get: function get() {
      return 7;
    } }).a != 7;
});

/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = function (exec) {
  try {
    return !!exec();
  } catch (e) {
    return true;
  }
};

/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var core = module.exports = { version: '2.5.1' };
if (typeof __e == 'number') __e = core; // eslint-disable-line no-undef

/***/ }),
/* 5 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// to indexed object, toObject with fallback for non-array-like ES3 strings
var IObject = __webpack_require__(6);
var defined = __webpack_require__(7);
module.exports = function (it) {
  return IObject(defined(it));
};

/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// fallback for non-array-like ES3 and non-enumerable old V8 strings
var cof = __webpack_require__(61);
// eslint-disable-next-line no-prototype-builtins
module.exports = Object('z').propertyIsEnumerable(0) ? Object : function (it) {
  return cof(it) == 'String' ? it.split('') : Object(it);
};

/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 7.2.1 RequireObjectCoercible(argument)
module.exports = function (it) {
  if (it == undefined) throw TypeError("Can't call method on  " + it);
  return it;
};

/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 7.1.4 ToInteger
var ceil = Math.ceil;
var floor = Math.floor;
module.exports = function (it) {
  return isNaN(it = +it) ? 0 : (it > 0 ? floor : ceil)(it);
};

/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(76)
var __weex_style__ = __webpack_require__(77)
var __weex_script__ = __webpack_require__(78)

__weex_define__('@weex-component/wxc-navbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(84)
var __weex_style__ = __webpack_require__(85)
var __weex_script__ = __webpack_require__(86)

__weex_define__('@weex-component/wxc-tabitem', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

var __vue_exports__, __vue_options__
var __vue_styles__ = []

/* styles */
__vue_styles__.push(__webpack_require__(12)
)

/* script */
__vue_exports__ = __webpack_require__(13)

/* template */
var __vue_template__ = __webpack_require__(90)
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
__vue_options__.__file = "/Users/sunjian/Desktop/github/CrazyDaily/presentation/src/main/assets/tabbar.vue"
__vue_options__.render = __vue_template__.render
__vue_options__.staticRenderFns = __vue_template__.staticRenderFns
__vue_options__._scopeId = "data-v-73103732"
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
/* 12 */
/***/ (function(module, exports) {

module.exports = {
  "tabbar": {
    "width": 750,
    "zIndex": 10
  }
}

/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

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

__webpack_require__(14);
module.exports = {
  data: {
    page: 0, //默认选择的是第一个页面
    tabItems: [//配置每个tabbar
    {
      title: "首页",
      defaultTitleColor: "#000",
      image: "http://gtms01.alicdn.com/tps/i1/TB1qw.hMpXXXXagXXXX9t7RGVXX-46-46.png",
      selectedImage: "http://gtms04.alicdn.com/tps/i4/TB16jjPMpXXXXazXVXX9t7RGVXX-46-46.png",
      activeTitleColor: "#db3652"
    }, {
      title: "最新",
      defaultTitleColor: "#000",
      image: "http://gtms03.alicdn.com/tps/i3/TB1LEn9MpXXXXaUXpXX9t7RGVXX-46-46.png",
      selectedImage: "http://gtms02.alicdn.com/tps/i2/TB1qysbMpXXXXcnXXXX9t7RGVXX-46-46.png",
      activeTitleColor: "#db3652"
    }, {
      title: "购物车",
      defaultTitleColor: "#000",
      image: "http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png",
      selectedImage: "http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png",
      activeTitleColor: "#db3652",
      haslabel: true,
      labelName: "cart",
      label: 5
    }, {
      title: "个人中心",
      defaultTitleColor: "#000",
      image: "http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png",
      selectedImage: "http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png",
      activeTitleColor: "#db3652"
    }]
  }
};

/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(15);

/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(16)
var __weex_style__ = __webpack_require__(17)
var __weex_script__ = __webpack_require__(18)

__weex_define__('@weex-component/tabbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 16 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "tabbar"
  ],
  "id": "tabbar",
  "style": {
    "transformOrigin": function () {return this.transformOrigin},
    "height": function () {return this.tabbarHeight},
    "backgroundColor": function () {return 'rgba(255, 255, 255,' + (this.bgopacity) + ')'}
  },
  "children": [
    {
      "type": "div",
      "classList": [
        "tabs"
      ],
      "attr": {
        "ref": function () {return this.$index}
      },
      "events": {
        "click": "switchPage"
      },
      "repeat": function () {return this.tabs},
      "children": [
        {
          "type": "image",
          "classList": [
            "img"
          ],
          "shown": function () {return this.selected!==this.$index},
          "style": {
            "left": function () {return this.imgleft}
          },
          "attr": {
            "src": function () {return this.image}
          }
        },
        {
          "type": "image",
          "classList": [
            "img"
          ],
          "shown": function () {return this.selected===this.$index},
          "style": {
            "left": function () {return this.imgleft}
          },
          "attr": {
            "src": function () {return this.selectedImage}
          }
        },
        {
          "type": "text",
          "classList": [
            "text"
          ],
          "style": {
            "color": function () {return this.selected===this.$index?this.activeTitleColor:this.defaultTitleColor}
          },
          "attr": {
            "value": function () {return this.title}
          }
        },
        {
          "type": "text",
          "classList": [
            "number"
          ],
          "style": {
            "backgroundColor": function () {return this.labelColor}
          },
          "id": function () {return this.labelName},
          "shown": function () {return this.haslabel},
          "attr": {
            "value": function () {return this.label}
          }
        }
      ]
    }
  ]
}

/***/ }),
/* 17 */
/***/ (function(module, exports) {

module.exports = {
  "tabbar": {
    "flexDirection": "row",
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "textAlign": "center",
    "borderTop": "1px solid rgb(197, 197, 197)",
    "zIndex": 10,
    "width": 750
  },
  "tabs": {
    "position": "relative",
    "flex": 1,
    "textAlign": "center",
    "flexDirection": "column",
    "bottom": 0
  },
  "img": {
    "width": 50,
    "height": 50,
    "left": 70,
    "marginTop": 10,
    "position": "relative",
    "textAlign": "center"
  },
  "text": {
    "fontSize": 26,
    "textAlign": "center",
    "marginTop": 5
  },
  "number": {
    "position": "absolute",
    "bottom": 55,
    "right": 30,
    "color": "#ffffff",
    "borderRadius": 20,
    "width": 40,
    "height": 40,
    "lineHeight": 40,
    "fontSize": 28,
    "textAlign": "center"
  }
}

/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

module.exports = function(module, exports, __weex_require__){'use strict';

var animation = __weex_require__('@weex-module/animation');
__webpack_require__(19);

module.exports = {
	data: function () {return {
		tabs: [],
		selected: 0,
		tabbarHeight: "100",
		imgleft: 70,
		bgopacity: "1",
		labelColor: "#db3652",
		transformOrigin: "center center",
		current_translate: "show",
		controlAnim: 0 }},
	created: function created() {
		var _this = this;

		if (this.tabs.length > 4) {
			this.imgleft = 50 + (5 - this.tabs.length) * 30;
		} else {
			this.imgleft = 40 + (5 - this.tabs.length) * 30;
		}

		this.$on("tabbarAccept", function (e) {
			_this.executeMessage({
				target: e.detail.target,
				action: e.detail.action,
				value: e.detail.value
			});
		});

		this.$on("tabbarState", function (e) {
			if (e.detail.state === "show") {
				_this.tabbarShow();
			} else {
				_this.tabbarHide();
			}
		});
	},
	ready: function ready() {},

	methods: {
		switchPage: function switchPage(event) {
			var index = event.target.attr.ref;
			this.$dispatch("switchPage", {
				page: index
			});
			this.onselected = index;
		},
		anim: function anim(styles, timingFunction, duration, callback) {
			animation.transition(this._ids.tabbar.el.ref, {
				styles: styles,
				timingFunction: timingFunction,
				duration: duration
			}, callback);
		},
		tabbarShow: function tabbarShow() {
			var _this2 = this;

			if (this.current_translate === "show" || this.controlAnim === 1) {
				return;
			}

			this.controlAnim = 1;
			this.current_translate = 'translate(0, 0)';

			this.anim({
				transform: this.current_translate
			}, "ease-in", 300, function () {
				_this2.current_translate = "show";
				_this2.controlAnim = 0;
			});
		},
		tabbarHide: function tabbarHide() {
			var _this3 = this;

			if (this.current_translate === "hide" || this.controlAnim === 1) {
				return;
			}

			this.controlAnim = 1;
			this.current_translate = 'translate(0, 100%)';

			this.anim({
				transform: this.current_translate
			}, "ease-out", 300, function () {
				_this3.current_translate = "hide";
				_this3.controlAnim = 0;
			});
		},
		executeMessage: function executeMessage(message) {
			switch (message.action) {
				case "add":
					this.$vm(message.target).label++;
					break;
				case "minus":
					this.$vm(message.target).label--;
					break;
				case "set":
					this.$vm(message.target).label = message.value;
			}
		}
	}
};}
/* generated by weex-loader */


/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(20);
__webpack_require__(24);
__webpack_require__(28);
__webpack_require__(32);
__webpack_require__(36);
__webpack_require__(40);
__webpack_require__(72);
__webpack_require__(9);
__webpack_require__(79);
__webpack_require__(83);
__webpack_require__(10);

/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(21)
var __weex_style__ = __webpack_require__(22)
var __weex_script__ = __webpack_require__(23)

__weex_define__('@weex-component/wxc-button', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 21 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": function () {return ['btn', 'btn-' + (this.type), 'btn-sz-' + (this.size)]},
  "children": [
    {
      "type": "text",
      "classList": function () {return ['btn-txt', 'btn-txt-' + (this.type), 'btn-txt-sz-' + (this.size)]},
      "attr": {
        "value": function () {return this.value}
      }
    }
  ]
}

/***/ }),
/* 22 */
/***/ (function(module, exports) {

module.exports = {
  "btn": {
    "marginBottom": 0,
    "alignItems": "center",
    "justifyContent": "center",
    "borderWidth": "1px",
    "borderStyle": "solid",
    "borderColor": "#333333"
  },
  "btn-default": {
    "color": "rgb(51,51,51)"
  },
  "btn-primary": {
    "backgroundColor": "rgb(40,96,144)",
    "borderColor": "rgb(40,96,144)"
  },
  "btn-success": {
    "backgroundColor": "rgb(92,184,92)",
    "borderColor": "rgb(76,174,76)"
  },
  "btn-info": {
    "backgroundColor": "rgb(91,192,222)",
    "borderColor": "rgb(70,184,218)"
  },
  "btn-warning": {
    "backgroundColor": "rgb(240,173,78)",
    "borderColor": "rgb(238,162,54)"
  },
  "btn-danger": {
    "backgroundColor": "rgb(217,83,79)",
    "borderColor": "rgb(212,63,58)"
  },
  "btn-link": {
    "borderColor": "rgba(0,0,0,0)",
    "borderRadius": 0
  },
  "btn-txt-default": {
    "color": "rgb(51,51,51)"
  },
  "btn-txt-primary": {
    "color": "rgb(255,255,255)"
  },
  "btn-txt-success": {
    "color": "rgb(255,255,255)"
  },
  "btn-txt-info": {
    "color": "rgb(255,255,255)"
  },
  "btn-txt-warning": {
    "color": "rgb(255,255,255)"
  },
  "btn-txt-danger": {
    "color": "rgb(255,255,255)"
  },
  "btn-txt-link": {
    "color": "rgb(51,122,183)"
  },
  "btn-sz-large": {
    "width": "300px",
    "height": "100px",
    "paddingTop": "25px",
    "paddingBottom": "25px",
    "paddingLeft": "40px",
    "paddingRight": "40px",
    "borderRadius": "15px"
  },
  "btn-sz-middle": {
    "width": "240px",
    "height": "80px",
    "paddingTop": "15px",
    "paddingBottom": "15px",
    "paddingLeft": "30px",
    "paddingRight": "30px",
    "borderRadius": "10px"
  },
  "btn-sz-small": {
    "width": "170px",
    "height": "60px",
    "paddingTop": "12px",
    "paddingBottom": "12px",
    "paddingLeft": "25px",
    "paddingRight": "25px",
    "borderRadius": "7px"
  },
  "btn-txt-sz-large": {
    "fontSize": "45px"
  },
  "btn-txt-sz-middle": {
    "fontSize": "35px"
  },
  "btn-txt-sz-small": {
    "fontSize": "30px"
  }
}

/***/ }),
/* 23 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    type: 'default',
    size: 'large',
    value: ''
  }},
  methods: {}
};}
/* generated by weex-loader */


/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(25)
var __weex_style__ = __webpack_require__(26)
var __weex_script__ = __webpack_require__(27)

__weex_define__('@weex-component/wxc-hn', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 25 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": function () {return ['h' + (this.level)]},
  "style": {
    "justifyContent": "center"
  },
  "children": [
    {
      "type": "text",
      "classList": function () {return ['txt-h' + (this.level)]},
      "attr": {
        "value": function () {return this.value}
      }
    }
  ]
}

/***/ }),
/* 26 */
/***/ (function(module, exports) {

module.exports = {
  "h1": {
    "height": "110px",
    "paddingTop": "20px",
    "paddingBottom": "20px"
  },
  "h2": {
    "height": "110px",
    "paddingTop": "20px",
    "paddingBottom": "20px"
  },
  "h3": {
    "height": "110px",
    "paddingTop": "20px",
    "paddingBottom": "20px"
  },
  "txt-h1": {
    "fontSize": "70px"
  },
  "txt-h2": {
    "fontSize": "52px"
  },
  "txt-h3": {
    "fontSize": "42px"
  }
}

/***/ }),
/* 27 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    level: 1,
    value: ''
  }},
  methods: {}
};}
/* generated by weex-loader */


/***/ }),
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(29)
var __weex_style__ = __webpack_require__(30)
var __weex_script__ = __webpack_require__(31)

__weex_define__('@weex-component/wxc-list-item', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 29 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "item"
  ],
  "events": {
    "touchstart": "touchstart",
    "touchend": "touchend"
  },
  "style": {
    "backgroundColor": function () {return this.bgColor}
  },
  "children": [
    {
      "type": "content"
    }
  ]
}

/***/ }),
/* 30 */
/***/ (function(module, exports) {

module.exports = {
  "item": {
    "paddingTop": "25px",
    "paddingBottom": "25px",
    "paddingLeft": "35px",
    "paddingRight": "35px",
    "height": "160px",
    "justifyContent": "center",
    "borderBottomWidth": "1px",
    "borderColor": "#dddddd"
  }
}

/***/ }),
/* 31 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    bgColor: '#ffffff'
  }},
  methods: {
    touchstart: function touchstart() {},
    touchend: function touchend() {}
  }
};}
/* generated by weex-loader */


/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(33)
var __weex_style__ = __webpack_require__(34)
var __weex_script__ = __webpack_require__(35)

__weex_define__('@weex-component/wxc-panel', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 33 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": function () {return ['panel', 'panel-' + (this.type)]},
  "style": {
    "borderWidth": function () {return this.border}
  },
  "children": [
    {
      "type": "text",
      "classList": function () {return ['panel-header', 'panel-header-' + (this.type)]},
      "style": {
        "paddingTop": function () {return this.paddingHead},
        "paddingBottom": function () {return this.paddingHead},
        "paddingLeft": function () {return this.paddingHead*1.5},
        "paddingRight": function () {return this.paddingHead*1.5}
      },
      "attr": {
        "value": function () {return this.title}
      }
    },
    {
      "type": "div",
      "classList": function () {return ['panel-body', 'panel-body-' + (this.type)]},
      "style": {
        "paddingTop": function () {return this.paddingBody},
        "paddingBottom": function () {return this.paddingBody},
        "paddingLeft": function () {return this.paddingBody*1.5},
        "paddingRight": function () {return this.paddingBody*1.5}
      },
      "children": [
        {
          "type": "content"
        }
      ]
    }
  ]
}

/***/ }),
/* 34 */
/***/ (function(module, exports) {

module.exports = {
  "panel": {
    "marginBottom": "20px",
    "backgroundColor": "#ffffff",
    "borderColor": "#dddddd",
    "borderWidth": "1px"
  },
  "panel-primary": {
    "borderColor": "rgb(40,96,144)"
  },
  "panel-success": {
    "borderColor": "rgb(76,174,76)"
  },
  "panel-info": {
    "borderColor": "rgb(70,184,218)"
  },
  "panel-warning": {
    "borderColor": "rgb(238,162,54)"
  },
  "panel-danger": {
    "borderColor": "rgb(212,63,58)"
  },
  "panel-header": {
    "backgroundColor": "#f5f5f5",
    "fontSize": "40px",
    "color": "#333333"
  },
  "panel-header-primary": {
    "backgroundColor": "rgb(40,96,144)",
    "color": "#ffffff"
  },
  "panel-header-success": {
    "backgroundColor": "rgb(92,184,92)",
    "color": "#ffffff"
  },
  "panel-header-info": {
    "backgroundColor": "rgb(91,192,222)",
    "color": "#ffffff"
  },
  "panel-header-warning": {
    "backgroundColor": "rgb(240,173,78)",
    "color": "#ffffff"
  },
  "panel-header-danger": {
    "backgroundColor": "rgb(217,83,79)",
    "color": "#ffffff"
  }
}

/***/ }),
/* 35 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    type: 'default',
    title: '',
    paddingBody: 20,
    paddingHead: 20,
    dataClass: '',
    border: 0
  }},
  ready: function ready() {}
};}
/* generated by weex-loader */


/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(37)
var __weex_style__ = __webpack_require__(38)
var __weex_script__ = __webpack_require__(39)

__weex_define__('@weex-component/wxc-tip', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 37 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": function () {return ['tip', 'tip-' + (this.type)]},
  "children": [
    {
      "type": "text",
      "classList": function () {return ['tip-txt', 'tip-txt-' + (this.type)]},
      "attr": {
        "value": function () {return this.value}
      }
    }
  ]
}

/***/ }),
/* 38 */
/***/ (function(module, exports) {

module.exports = {
  "tip": {
    "paddingLeft": "36px",
    "paddingRight": "36px",
    "paddingTop": "36px",
    "paddingBottom": "36px",
    "borderRadius": "10px"
  },
  "tip-txt": {
    "fontSize": "28px"
  },
  "tip-success": {
    "backgroundColor": "#dff0d8",
    "borderColor": "#d6e9c6"
  },
  "tip-txt-success": {
    "color": "#3c763d"
  },
  "tip-info": {
    "backgroundColor": "#d9edf7",
    "borderColor": "#bce8f1"
  },
  "tip-txt-info": {
    "color": "#31708f"
  },
  "tip-warning": {
    "backgroundColor": "#fcf8e3",
    "borderColor": "#faebcc"
  },
  "tip-txt-warning": {
    "color": "#8a6d3b"
  },
  "tip-danger": {
    "backgroundColor": "#f2dede",
    "borderColor": "#ebccd1"
  },
  "tip-txt-danger": {
    "color": "#a94442"
  }
}

/***/ }),
/* 39 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    type: 'success',
    value: ''
  }}
};}
/* generated by weex-loader */


/***/ }),
/* 40 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(41)
var __weex_style__ = __webpack_require__(42)
var __weex_script__ = __webpack_require__(43)

__weex_define__('@weex-component/wxc-countdown', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 41 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "style": {
    "overflow": "hidden",
    "flexDirection": "row"
  },
  "events": {
    "appear": "appeared",
    "disappear": "disappeared"
  },
  "children": [
    {
      "type": "content"
    }
  ]
}

/***/ }),
/* 42 */
/***/ (function(module, exports) {

module.exports = {
  "wrap": {
    "overflow": "hidden"
  }
}

/***/ }),
/* 43 */
/***/ (function(module, exports, __webpack_require__) {

module.exports = function(module, exports, __weex_require__){'use strict';

var _assign = __webpack_require__(44);

var _assign2 = _interopRequireDefault(_assign);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

module.exports = {
    data: function () {return {
        now: 0,
        remain: 0,
        time: {
            elapse: 0,
            D: '0',
            DD: '0',
            h: '0',
            hh: '00',
            H: '0',
            HH: '0',
            m: '0',
            mm: '00',
            M: '0',
            MM: '0',
            s: '0',
            ss: '00',
            S: '0',
            SS: '0'
        },
        outofview: false
    }},
    ready: function ready() {
        if (this.remain <= 0) {
            return;
        }

        this.now = Date.now();
        this.nextTick();
    },
    methods: {
        nextTick: function nextTick() {
            if (this.outofview) {
                setTimeout(this.nextTick.bind(this), 1000);
            } else {
                this.time.elapse = parseInt((Date.now() - this.now) / 1000);

                if (this.calc()) {
                    this.$emit('tick', (0, _assign2.default)({}, this.time));
                    setTimeout(this.nextTick.bind(this), 1000);
                } else {
                    this.$emit('alarm', (0, _assign2.default)({}, this.time));
                }
                this._app.updateActions();
            }
        },
        format: function format(str) {
            if (str.length >= 2) {
                return str;
            } else {
                return '0' + str;
            }
        },
        calc: function calc() {
            var remain = this.remain - this.time.elapse;
            if (remain < 0) {
                remain = 0;
            }
            this.time.D = String(parseInt(remain / 86400));
            this.time.DD = this.format(this.time.D);
            this.time.h = String(parseInt((remain - parseInt(this.time.D) * 86400) / 3600));
            this.time.hh = this.format(this.time.h);
            this.time.H = String(parseInt(remain / 3600));
            this.time.HH = this.format(this.time.H);
            this.time.m = String(parseInt((remain - parseInt(this.time.H) * 3600) / 60));
            this.time.mm = this.format(this.time.m);
            this.time.M = String(parseInt(remain / 60));
            this.time.MM = this.format(this.time.M);
            this.time.s = String(remain - parseInt(this.time.M) * 60);
            this.time.ss = this.format(this.time.s);
            this.time.S = String(remain);
            this.time.SS = this.format(this.time.S);

            return remain > 0;
        },
        appeared: function appeared() {
            this.outofview = false;
        },
        disappeared: function disappeared() {
            this.outofview = true;
        }
    }
};}
/* generated by weex-loader */


/***/ }),
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = { "default": __webpack_require__(45), __esModule: true };

/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


__webpack_require__(46);
module.exports = __webpack_require__(4).Object.assign;

/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 19.1.3.1 Object.assign(target, source)
var $export = __webpack_require__(47);

$export($export.S + $export.F, 'Object', { assign: __webpack_require__(57) });

/***/ }),
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var global = __webpack_require__(0);
var core = __webpack_require__(4);
var ctx = __webpack_require__(48);
var hide = __webpack_require__(50);
var PROTOTYPE = 'prototype';

var $export = function $export(type, name, source) {
  var IS_FORCED = type & $export.F;
  var IS_GLOBAL = type & $export.G;
  var IS_STATIC = type & $export.S;
  var IS_PROTO = type & $export.P;
  var IS_BIND = type & $export.B;
  var IS_WRAP = type & $export.W;
  var exports = IS_GLOBAL ? core : core[name] || (core[name] = {});
  var expProto = exports[PROTOTYPE];
  var target = IS_GLOBAL ? global : IS_STATIC ? global[name] : (global[name] || {})[PROTOTYPE];
  var key, own, out;
  if (IS_GLOBAL) source = name;
  for (key in source) {
    // contains in native
    own = !IS_FORCED && target && target[key] !== undefined;
    if (own && key in exports) continue;
    // export native or passed
    out = own ? target[key] : source[key];
    // prevent global pollution for namespaces
    exports[key] = IS_GLOBAL && typeof target[key] != 'function' ? source[key]
    // bind timers to global for call from export context
    : IS_BIND && own ? ctx(out, global)
    // wrap global constructors for prevent change them in library
    : IS_WRAP && target[key] == out ? function (C) {
      var F = function F(a, b, c) {
        if (this instanceof C) {
          switch (arguments.length) {
            case 0:
              return new C();
            case 1:
              return new C(a);
            case 2:
              return new C(a, b);
          }return new C(a, b, c);
        }return C.apply(this, arguments);
      };
      F[PROTOTYPE] = C[PROTOTYPE];
      return F;
      // make static versions for prototype methods
    }(out) : IS_PROTO && typeof out == 'function' ? ctx(Function.call, out) : out;
    // export proto methods to core.%CONSTRUCTOR%.methods.%NAME%
    if (IS_PROTO) {
      (exports.virtual || (exports.virtual = {}))[key] = out;
      // export proto methods to core.%CONSTRUCTOR%.prototype.%NAME%
      if (type & $export.R && expProto && !expProto[key]) hide(expProto, key, out);
    }
  }
};
// type bitmap
$export.F = 1; // forced
$export.G = 2; // global
$export.S = 4; // static
$export.P = 8; // proto
$export.B = 16; // bind
$export.W = 32; // wrap
$export.U = 64; // safe
$export.R = 128; // real proto method for `library`
module.exports = $export;

/***/ }),
/* 48 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// optional / simple context binding
var aFunction = __webpack_require__(49);
module.exports = function (fn, that, length) {
  aFunction(fn);
  if (that === undefined) return fn;
  switch (length) {
    case 1:
      return function (a) {
        return fn.call(that, a);
      };
    case 2:
      return function (a, b) {
        return fn.call(that, a, b);
      };
    case 3:
      return function (a, b, c) {
        return fn.call(that, a, b, c);
      };
  }
  return function () /* ...args */{
    return fn.apply(that, arguments);
  };
};

/***/ }),
/* 49 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = function (it) {
  if (typeof it != 'function') throw TypeError(it + ' is not a function!');
  return it;
};

/***/ }),
/* 50 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var dP = __webpack_require__(51);
var createDesc = __webpack_require__(56);
module.exports = __webpack_require__(2) ? function (object, key, value) {
  return dP.f(object, key, createDesc(1, value));
} : function (object, key, value) {
  object[key] = value;
  return object;
};

/***/ }),
/* 51 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var anObject = __webpack_require__(52);
var IE8_DOM_DEFINE = __webpack_require__(53);
var toPrimitive = __webpack_require__(55);
var dP = Object.defineProperty;

exports.f = __webpack_require__(2) ? Object.defineProperty : function defineProperty(O, P, Attributes) {
  anObject(O);
  P = toPrimitive(P, true);
  anObject(Attributes);
  if (IE8_DOM_DEFINE) try {
    return dP(O, P, Attributes);
  } catch (e) {/* empty */}
  if ('get' in Attributes || 'set' in Attributes) throw TypeError('Accessors not supported!');
  if ('value' in Attributes) O[P] = Attributes.value;
  return O;
};

/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isObject = __webpack_require__(1);
module.exports = function (it) {
  if (!isObject(it)) throw TypeError(it + ' is not an object!');
  return it;
};

/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = !__webpack_require__(2) && !__webpack_require__(3)(function () {
  return Object.defineProperty(__webpack_require__(54)('div'), 'a', { get: function get() {
      return 7;
    } }).a != 7;
});

/***/ }),
/* 54 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var isObject = __webpack_require__(1);
var document = __webpack_require__(0).document;
// typeof document.createElement is 'object' in old IE
var is = isObject(document) && isObject(document.createElement);
module.exports = function (it) {
  return is ? document.createElement(it) : {};
};

/***/ }),
/* 55 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 7.1.1 ToPrimitive(input [, PreferredType])
var isObject = __webpack_require__(1);
// instead of the ES6 spec version, we didn't implement @@toPrimitive case
// and the second argument - flag - preferred type is a string
module.exports = function (it, S) {
  if (!isObject(it)) return it;
  var fn, val;
  if (S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it))) return val;
  if (typeof (fn = it.valueOf) == 'function' && !isObject(val = fn.call(it))) return val;
  if (!S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it))) return val;
  throw TypeError("Can't convert object to primitive value");
};

/***/ }),
/* 56 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


module.exports = function (bitmap, value) {
  return {
    enumerable: !(bitmap & 1),
    configurable: !(bitmap & 2),
    writable: !(bitmap & 4),
    value: value
  };
};

/***/ }),
/* 57 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// 19.1.2.1 Object.assign(target, source, ...)

var getKeys = __webpack_require__(58);
var gOPS = __webpack_require__(69);
var pIE = __webpack_require__(70);
var toObject = __webpack_require__(71);
var IObject = __webpack_require__(6);
var $assign = Object.assign;

// should work with symbols and should have deterministic property order (V8 bug)
module.exports = !$assign || __webpack_require__(3)(function () {
  var A = {};
  var B = {};
  // eslint-disable-next-line no-undef
  var S = Symbol();
  var K = 'abcdefghijklmnopqrst';
  A[S] = 7;
  K.split('').forEach(function (k) {
    B[k] = k;
  });
  return $assign({}, A)[S] != 7 || Object.keys($assign({}, B)).join('') != K;
}) ? function assign(target, source) {
  // eslint-disable-line no-unused-vars
  var T = toObject(target);
  var aLen = arguments.length;
  var index = 1;
  var getSymbols = gOPS.f;
  var isEnum = pIE.f;
  while (aLen > index) {
    var S = IObject(arguments[index++]);
    var keys = getSymbols ? getKeys(S).concat(getSymbols(S)) : getKeys(S);
    var length = keys.length;
    var j = 0;
    var key;
    while (length > j) {
      if (isEnum.call(S, key = keys[j++])) T[key] = S[key];
    }
  }return T;
} : $assign;

/***/ }),
/* 58 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 19.1.2.14 / 15.2.3.14 Object.keys(O)
var $keys = __webpack_require__(59);
var enumBugKeys = __webpack_require__(68);

module.exports = Object.keys || function keys(O) {
  return $keys(O, enumBugKeys);
};

/***/ }),
/* 59 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var has = __webpack_require__(60);
var toIObject = __webpack_require__(5);
var arrayIndexOf = __webpack_require__(62)(false);
var IE_PROTO = __webpack_require__(65)('IE_PROTO');

module.exports = function (object, names) {
  var O = toIObject(object);
  var i = 0;
  var result = [];
  var key;
  for (key in O) {
    if (key != IE_PROTO) has(O, key) && result.push(key);
  } // Don't enum bug & hidden keys
  while (names.length > i) {
    if (has(O, key = names[i++])) {
      ~arrayIndexOf(result, key) || result.push(key);
    }
  }return result;
};

/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var hasOwnProperty = {}.hasOwnProperty;
module.exports = function (it, key) {
  return hasOwnProperty.call(it, key);
};

/***/ }),
/* 61 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var toString = {}.toString;

module.exports = function (it) {
  return toString.call(it).slice(8, -1);
};

/***/ }),
/* 62 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// false -> Array#indexOf
// true  -> Array#includes
var toIObject = __webpack_require__(5);
var toLength = __webpack_require__(63);
var toAbsoluteIndex = __webpack_require__(64);
module.exports = function (IS_INCLUDES) {
  return function ($this, el, fromIndex) {
    var O = toIObject($this);
    var length = toLength(O.length);
    var index = toAbsoluteIndex(fromIndex, length);
    var value;
    // Array#includes uses SameValueZero equality algorithm
    // eslint-disable-next-line no-self-compare
    if (IS_INCLUDES && el != el) while (length > index) {
      value = O[index++];
      // eslint-disable-next-line no-self-compare
      if (value != value) return true;
      // Array#indexOf ignores holes, Array#includes - not
    } else for (; length > index; index++) {
      if (IS_INCLUDES || index in O) {
        if (O[index] === el) return IS_INCLUDES || index || 0;
      }
    }return !IS_INCLUDES && -1;
  };
};

/***/ }),
/* 63 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 7.1.15 ToLength
var toInteger = __webpack_require__(8);
var min = Math.min;
module.exports = function (it) {
  return it > 0 ? min(toInteger(it), 0x1fffffffffffff) : 0; // pow(2, 53) - 1 == 9007199254740991
};

/***/ }),
/* 64 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var toInteger = __webpack_require__(8);
var max = Math.max;
var min = Math.min;
module.exports = function (index, length) {
  index = toInteger(index);
  return index < 0 ? max(index + length, 0) : min(index, length);
};

/***/ }),
/* 65 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var shared = __webpack_require__(66)('keys');
var uid = __webpack_require__(67);
module.exports = function (key) {
  return shared[key] || (shared[key] = uid(key));
};

/***/ }),
/* 66 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var global = __webpack_require__(0);
var SHARED = '__core-js_shared__';
var store = global[SHARED] || (global[SHARED] = {});
module.exports = function (key) {
  return store[key] || (store[key] = {});
};

/***/ }),
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var id = 0;
var px = Math.random();
module.exports = function (key) {
  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + px).toString(36));
};

/***/ }),
/* 68 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// IE 8- don't enum bug keys
module.exports = 'constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf'.split(',');

/***/ }),
/* 69 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.f = Object.getOwnPropertySymbols;

/***/ }),
/* 70 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


exports.f = {}.propertyIsEnumerable;

/***/ }),
/* 71 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


// 7.1.13 ToObject(argument)
var defined = __webpack_require__(7);
module.exports = function (it) {
  return Object(defined(it));
};

/***/ }),
/* 72 */
/***/ (function(module, exports, __webpack_require__) {

var __weex_template__ = __webpack_require__(73)
var __weex_style__ = __webpack_require__(74)
var __weex_script__ = __webpack_require__(75)

__weex_define__('@weex-component/wxc-marquee', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 73 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "wrap"
  ],
  "events": {
    "appear": "appeared",
    "disappear": "disappeared"
  },
  "children": [
    {
      "type": "div",
      "id": "anim",
      "classList": [
        "anim"
      ],
      "children": [
        {
          "type": "content"
        }
      ]
    }
  ]
}

/***/ }),
/* 74 */
/***/ (function(module, exports) {

module.exports = {
  "wrap": {
    "overflow": "hidden",
    "position": "relative"
  },
  "anim": {
    "flexDirection": "column",
    "position": "absolute",
    "transform": "translateY(0) translateZ(0)"
  }
}

/***/ }),
/* 75 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
    data: function () {return {
        step: 0,
        count: 0,
        index: 1,
        duration: 0,
        interval: 0,
        outofview: false
    }},
    ready: function ready() {
        if (this.interval > 0 && this.step > 0 && this.duration > 0) {
            this.nextTick();
        }
    },
    methods: {
        nextTick: function nextTick() {
            var self = this;
            if (this.outofview) {
                setTimeout(self.nextTick.bind(self), self.interval);
            } else {
                setTimeout(function () {
                    self.animation(self.nextTick.bind(self));
                }, self.interval);
            }
        },
        animation: function animation(cb) {
            var self = this;
            var offset = -self.step * self.index;
            var $animation = __weex_require__('@weex-module/animation');
            $animation.transition(this.$el('anim'), {
                styles: {
                    transform: 'translateY(' + String(offset) + 'px) translateZ(0)'
                },
                timingFunction: 'ease',
                duration: self.duration
            }, function () {
                self.index = (self.index + 1) % self.count;
                self.$emit('change', {
                    index: self.index,
                    count: self.count
                });
                cb && cb();
            });
        },
        appeared: function appeared() {
            this.outofview = false;
        },
        disappeared: function disappeared() {
            this.outofview = true;
        }
    }
};}
/* generated by weex-loader */


/***/ }),
/* 76 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "container"
  ],
  "style": {
    "height": function () {return this.height},
    "backgroundColor": function () {return this.backgroundColor}
  },
  "attr": {
    "dataRole": function () {return this.dataRole}
  },
  "children": [
    {
      "type": "text",
      "classList": [
        "right-text"
      ],
      "style": {
        "color": function () {return this.rightItemColor}
      },
      "attr": {
        "naviItemPosition": "right",
        "value": function () {return this.rightItemTitle}
      },
      "shown": function () {return !this.rightItemSrc},
      "events": {
        "click": "onclickrightitem"
      }
    },
    {
      "type": "image",
      "classList": [
        "right-image"
      ],
      "attr": {
        "naviItemPosition": "right",
        "src": function () {return this.rightItemSrc}
      },
      "shown": function () {return this.rightItemSrc},
      "events": {
        "click": "onclickrightitem"
      }
    },
    {
      "type": "text",
      "classList": [
        "left-text"
      ],
      "style": {
        "color": function () {return this.leftItemColor}
      },
      "attr": {
        "naviItemPosition": "left",
        "value": function () {return this.leftItemTitle}
      },
      "shown": function () {return !this.leftItemSrc},
      "events": {
        "click": "onclickleftitem"
      }
    },
    {
      "type": "image",
      "classList": [
        "left-image"
      ],
      "attr": {
        "naviItemPosition": "left",
        "src": function () {return this.leftItemSrc}
      },
      "shown": function () {return this.leftItemSrc},
      "events": {
        "click": "onclickleftitem"
      }
    },
    {
      "type": "text",
      "classList": [
        "center-text"
      ],
      "style": {
        "color": function () {return this.titleColor}
      },
      "attr": {
        "naviItemPosition": "center",
        "value": function () {return this.title}
      }
    }
  ]
}

/***/ }),
/* 77 */
/***/ (function(module, exports) {

module.exports = {
  "container": {
    "flexDirection": "row",
    "position": "fixed",
    "top": 0,
    "left": 0,
    "right": 0,
    "width": 750
  },
  "right-text": {
    "position": "absolute",
    "bottom": 28,
    "right": 32,
    "textAlign": "right",
    "fontSize": 32,
    "fontFamily": "'Open Sans', sans-serif"
  },
  "left-text": {
    "position": "absolute",
    "bottom": 28,
    "left": 32,
    "textAlign": "left",
    "fontSize": 32,
    "fontFamily": "'Open Sans', sans-serif"
  },
  "center-text": {
    "position": "absolute",
    "bottom": 25,
    "left": 172,
    "right": 172,
    "textAlign": "center",
    "fontSize": 36,
    "fontWeight": "bold"
  },
  "left-image": {
    "position": "absolute",
    "bottom": 20,
    "left": 28,
    "width": 50,
    "height": 50
  },
  "right-image": {
    "position": "absolute",
    "bottom": 20,
    "right": 28,
    "width": 50,
    "height": 50
  }
}

/***/ }),
/* 78 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    dataRole: 'navbar',

    backgroundColor: 'black',

    height: 88,

    title: "",

    titleColor: 'black',

    rightItemSrc: '',

    rightItemTitle: '',

    rightItemColor: 'black',

    leftItemSrc: '',

    leftItemTitle: '',

    leftItemColor: 'black'
  }},
  methods: {
    onclickrightitem: function onclickrightitem(e) {
      this.$dispatch('naviBar.rightItem.click', {});
    },
    onclickleftitem: function onclickleftitem(e) {
      this.$dispatch('naviBar.leftItem.click', {});
    }
  }
};}
/* generated by weex-loader */


/***/ }),
/* 79 */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(9)
var __weex_template__ = __webpack_require__(80)
var __weex_style__ = __webpack_require__(81)
var __weex_script__ = __webpack_require__(82)

__weex_define__('@weex-component/wxc-navpage', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 80 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "wrapper"
  ],
  "children": [
    {
      "type": "wxc-navbar",
      "attr": {
        "dataRole": function () {return this.dataRole},
        "height": function () {return this.height},
        "backgroundColor": function () {return this.backgroundColor},
        "title": function () {return this.title},
        "titleColor": function () {return this.titleColor},
        "leftItemSrc": function () {return this.leftItemSrc},
        "leftItemTitle": function () {return this.leftItemTitle},
        "leftItemColor": function () {return this.leftItemColor},
        "rightItemSrc": function () {return this.rightItemSrc},
        "rightItemTitle": function () {return this.rightItemTitle},
        "rightItemColor": function () {return this.rightItemColor}
      }
    },
    {
      "type": "div",
      "classList": [
        "wrapper"
      ],
      "style": {
        "marginTop": function () {return this.height}
      },
      "children": [
        {
          "type": "content"
        }
      ]
    }
  ]
}

/***/ }),
/* 81 */
/***/ (function(module, exports) {

module.exports = {
  "wrapper": {
    "position": "absolute",
    "top": 0,
    "left": 0,
    "right": 0,
    "bottom": 0,
    "width": 750
  }
}

/***/ }),
/* 82 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    dataRole: 'navbar',
    backgroundColor: 'black',
    height: 88,
    title: "",
    titleColor: 'black',
    rightItemSrc: '',
    rightItemTitle: '',
    rightItemColor: 'black',
    leftItemSrc: '',
    leftItemTitle: '',
    leftItemColor: 'black'
  }}
};}
/* generated by weex-loader */


/***/ }),
/* 83 */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(10)
var __weex_template__ = __webpack_require__(87)
var __weex_style__ = __webpack_require__(88)
var __weex_script__ = __webpack_require__(89)

__weex_define__('@weex-component/wxc-tabbar', [], function(__weex_require__, __weex_exports__, __weex_module__) {

    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
    if (__weex_exports__.__esModule && __weex_exports__.default) {
      __weex_module__.exports = __weex_exports__.default
    }

    __weex_module__.exports.template = __weex_template__

    __weex_module__.exports.style = __weex_style__

})


/***/ }),
/* 84 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "container"
  ],
  "style": {
    "backgroundColor": function () {return this.backgroundColor}
  },
  "events": {
    "click": "onclickitem"
  },
  "children": [
    {
      "type": "image",
      "classList": [
        "top-line"
      ],
      "attr": {
        "src": "http://gtms03.alicdn.com/tps/i3/TB1mdsiMpXXXXXpXXXXNw4JIXXX-640-4.png"
      }
    },
    {
      "type": "image",
      "classList": [
        "tab-icon"
      ],
      "attr": {
        "src": function () {return this.icon}
      }
    },
    {
      "type": "text",
      "classList": [
        "tab-text"
      ],
      "style": {
        "color": function () {return this.titleColor}
      },
      "attr": {
        "value": function () {return this.title}
      }
    }
  ]
}

/***/ }),
/* 85 */
/***/ (function(module, exports) {

module.exports = {
  "container": {
    "flex": 1,
    "flexDirection": "column",
    "alignItems": "center",
    "justifyContent": "center",
    "height": 88
  },
  "top-line": {
    "position": "absolute",
    "top": 0,
    "left": 0,
    "right": 0,
    "height": 2
  },
  "tab-icon": {
    "marginTop": 5,
    "width": 40,
    "height": 40
  },
  "tab-text": {
    "marginTop": 5,
    "textAlign": "center",
    "fontSize": 20
  }
}

/***/ }),
/* 86 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    index: 0,
    title: '',
    titleColor: '#000000',
    icon: '',
    backgroundColor: '#ffffff'
  }},
  methods: {
    onclickitem: function onclickitem(e) {
      var vm = this;
      var params = {
        index: vm.index
      };
      vm.$dispatch('tabItem.onClick', params);
    }
  }
};}
/* generated by weex-loader */


/***/ }),
/* 87 */
/***/ (function(module, exports) {

module.exports = {
  "type": "div",
  "classList": [
    "wrapper"
  ],
  "children": [
    {
      "type": "embed",
      "classList": [
        "content"
      ],
      "style": {
        "visibility": function () {return this.visibility}
      },
      "repeat": function () {return this.tabItems},
      "attr": {
        "src": function () {return this.src},
        "type": "weex"
      }
    },
    {
      "type": "div",
      "classList": [
        "tabbar"
      ],
      "append": "tree",
      "children": [
        {
          "type": "wxc-tabitem",
          "repeat": function () {return this.tabItems},
          "attr": {
            "index": function () {return this.index},
            "icon": function () {return this.icon},
            "title": function () {return this.title},
            "titleColor": function () {return this.titleColor}
          }
        }
      ]
    }
  ]
}

/***/ }),
/* 88 */
/***/ (function(module, exports) {

module.exports = {
  "wrapper": {
    "width": 750,
    "position": "absolute",
    "top": 0,
    "left": 0,
    "right": 0,
    "bottom": 0
  },
  "content": {
    "position": "absolute",
    "top": 0,
    "left": 0,
    "right": 0,
    "bottom": 0,
    "marginTop": 0,
    "marginBottom": 88
  },
  "tabbar": {
    "flexDirection": "row",
    "position": "fixed",
    "bottom": 0,
    "left": 0,
    "right": 0,
    "height": 88
  }
}

/***/ }),
/* 89 */
/***/ (function(module, exports) {

module.exports = function(module, exports, __weex_require__){'use strict';

module.exports = {
  data: function () {return {
    tabItems: [],
    selectedIndex: 0,
    selectedColor: '#ff0000',
    unselectedColor: '#000000'
  }},
  created: function created() {
    this.selected(this.selectedIndex);

    this.$on('tabItem.onClick', function (e) {
      var detail = e.detail;
      this.selectedIndex = detail.index;
      this.selected(detail.index);

      var params = {
        index: detail.index
      };
      this.$dispatch('tabBar.onClick', params);
    });
  },
  methods: {
    selected: function selected(index) {
      for (var i = 0; i < this.tabItems.length; i++) {
        var tabItem = this.tabItems[i];
        if (i == index) {
          tabItem.icon = tabItem.selectedImage;
          tabItem.titleColor = this.selectedColor;
          tabItem.visibility = 'visible';
        } else {
          tabItem.icon = tabItem.image;
          tabItem.titleColor = this.unselectedColor;
          tabItem.visibility = 'hidden';
        }
      }
    }
  }
};}
/* generated by weex-loader */


/***/ }),
/* 90 */
/***/ (function(module, exports) {

module.exports={render:function(){},staticRenderFns:[]}

/***/ })
/******/ ]);