var Utils = {};
// --------------------------------------------
// 1调试模式，js错误会弹出错误提示框
// --------------------------------------------
Utils.debug = 0;
Utils.event = {};
Date.prototype.type = 'date';
//
$$ = function(str) {
	var eleId = str;
	if (str.indexOf("#") == 0) {
		eleId = str.substr(1);
	}
	return document.getElementById(eleId);
};

// --------------------------------------------
// 大写
// --------------------------------------------
Number.prototype.toUpcase = function() {
	var num = this;
	var strOutput = "";
	var strUnit = "仟佰拾亿仟佰拾万仟佰拾元角分";
	num += "00";
	var intPos = num.indexOf(".");
	if (intPos >= 0) {
		num = num.substring(0, intPos) + num.substr(intPos + 1, 2);
	}
	strUnit = strUnit.substr(strUnit.length - num.length);
	for (var i = 0; i < num.length; i++) {
		strOutput += "零壹贰叁肆伍陆柒捌玖".substr(num.substr(i, 1), 1) + strUnit.substr(i, 1);
	}
	return strOutput.replace(/零角零分$/, "整").replace(/零[仟佰拾]/g, "零").replace(/零{2,}/g, "零").replace(/零([亿|万])/g, "$1")
			.replace(/零+元/, "元").replace(/亿零{0,3}万/, "亿").replace(/^元/, "零元");
};

// --------------------------------------------
// 按进制格式化
// --------------------------------------------
Number.prototype.format = function(decimal) {
	var number = this;
	return number / Math.pow(10, bidInfo.decimal);
}

// ---------------------------------
// 保留2位小数
// ----------------------------------
Number.prototype.round = function(how){
	var number = this;
	if(!how)
		how=2;
	var d = Math.round(number*Math.pow(10,how))/Math.pow(10,how);
	return d;
}

Number.prototype.formatStr = function(decimal) {
	var number = this;
	var s1 = number / Math.pow(10, bidInfo.decimal);
	return s1.addComma();
}
// ---------------------------------
// 保留2位小数
// decimals 保留位数 number 默认2
// comma 是否添加千分点 boolean 默认false
// ----------------------------------
Number.prototype.roundNumber = function(decimals, comma) {
	var number = this;
	var newString;
	if (typeof (decimals) == "undefined")
		decimals = 2;
	if (typeof (comma) == "undefined")
		comma = false;
	decimals = Number(decimals);
	if (decimals < 1) {
		newString = (Math.round(number)).toString();
	} else {
		var numString = number.toString();
		if (numString.lastIndexOf(".") == -1) {
			numString += ".";
		}
		var cutoff = numString.lastIndexOf(".") + decimals;
		var d1 = Number(numString.substring(cutoff, cutoff + 1));
		var d2 = Number(numString.substring(cutoff + 1, cutoff + 2));
		if (d2 >= 5) {
			if (d1 == 9 && cutoff > 0) {
				while (cutoff > 0 && (d1 == 9 || isNaN(d1))) {
					if (d1 != ".") {
						cutoff -= 1;
						d1 = Number(numString.substring(cutoff, cutoff + 1));
					} else {
						cutoff -= 1;
					}
				}
			}
			d1 += 1;
		}
		if (d1 == 10) {
			numString = numString.substring(0, numString.lastIndexOf("."));
			var roundedNum = Number(numString) + 1;
			newString = roundedNum.toString() + '.';
		} else {
			newString = numString.substring(0, cutoff) + d1.toString();
		}
	}
	if (comma)
		newString = new Number(newString).addComma();
	if (newString.lastIndexOf(".") == -1 && decimals > 0)
		newString += ".";
	var decs = (newString.substring(newString.lastIndexOf(".") + 1)).length;
	for ( var i = 0; i < decimals - decs; i++)
		newString += "0";
	return newString;
}
// --------------------------------------------
// 添加千分点
// --------------------------------------------
Number.prototype.addComma = function() {
	var number = this;
	if (number == 0) {
		return 0;
	}
	var num = number + "";
	num = num.replace(new RegExp(",", "g"), "");
	// 正负号处理
	var symble = "";
	if (/^([-+]).*$/.test(num)) {
		symble = num.replace(/^([-+]).*$/, "$1");
		num = num.replace(/^([-+])(.*)$/, "$2");
	}
	if (/^[0-9]+(\.[0-9]+)?$/.test(num)) {
		var num = num.replace(new RegExp("^[0]+", "g"), "");
		if (/^\./.test(num)) {
			num = "0" + num;
		}
		var decimal = num.replace(/^[0-9]+(\.[0-9]+)?$/, "$1");
		var integer = num.replace(/^([0-9]+)(\.[0-9]+)?$/, "$1");
		var re = /(\d+)(\d{3})/;
		while (re.test(integer)) {
			integer = integer.replace(re, "$1,$2");
		}
		return symble + integer + decimal;
	} else {
		return number;
	}
};
// --------------------------------------------
// 加法
// --------------------------------------------
Number.prototype.add = function(value) {
	var arg1 = this;
	var arg2 = value;
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1.mul(m) + arg2.mul(m)).div(m);
};

// --------------------------------------------
// 减法
// --------------------------------------------
Number.prototype.sub = function(value) {
	return this.add(-value);
};

// --------------------------------------------
// 除法
// --------------------------------------------
Number.prototype.div = function(value) {
	var arg1 = this;
	var arg2 = value;
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
};

// --------------------------------------------
// 乘法
// --------------------------------------------
Number.prototype.mul = function(value) {
	var arg1 = this;
	var arg2 = value;
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
};

String.prototype.toDate = function() {
	var arr1 = this.split(" ")[0].split("-");
	var arr2 = this.split(" ")[1].split(":");
	var year = parseFloat(arr1[0]);
	var month = parseFloat(arr1[1]);
	var date = parseFloat(arr1[2]);
	var hours = parseFloat(arr2[0]);
	var minutes = parseFloat(arr2[1]);
	var seconds = parseFloat(arr2[2]);
	var myDate = new Date(year, month - 1, date, hours, minutes, seconds);
	var index = this.indexOf(".");
	if(index > 0){
		var milliseconds = this.substr(index+1);
		myDate = new Date(year, month - 1, date, hours, minutes, seconds,milliseconds);
	}
	return myDate;
};


String.prototype.isNumber = function() {
	var s=this.trim();
	if(s.length==0)
		return false;
	if(isNaN(s))
		return false;
	return true;
};


String.prototype.toObject = function() {
	var obj = eval("(" + this + ")");
	return obj;
};

// --------------------------------------------
// 删除千分点
// --------------------------------------------
String.prototype.removeComma = function() {
	var num = this.replace(new RegExp(",", "g"), "");
	return num;
};

// --------------------------------------------
// 子字符串出现位置（可忽略大小写），如："abcdefg".indexOfIgnoreCase("cd", 1, true)
// --------------------------------------------
String.prototype.indexOfIgnoreCase = function() {
	if (typeof(arguments[arguments.length - 1]) != 'boolean')
		return this.indexOf.apply(this, arguments);
	else {
		var bi = arguments[arguments.length - 1];
		var thisObj = this;
		var idx = 0;
		if (typeof(arguments[arguments.length - 2]) == 'number') {
			idx = arguments[arguments.length - 2];
			thisObj = this.substr(idx);
		}
		var re = new RegExp(arguments[0], bi ? 'i' : '');
		var r = thisObj.match(re);
		return r == null ? -1 : r.index + idx;
	}
}

// --------------------------------------------
// 格式化字符串，如"abcd{0}efg{1}hijk{2}".format("111", "222",
// "333")-->"abcd111efg222hijk333"
// --------------------------------------------
String.prototype.format = function() {
	if (arguments.length == 0)
		return this;
	for (var s = this, i = 0; i < arguments.length; i++)
		s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
	return s;
};

// --------------------------------------------
// 字符串替换全部
// --------------------------------------------
String.prototype.replaceAll = function(s1, s2) {
	// return this.replace(new RegExp(s1, "gm"), s2); //特殊字符失败
	var r = new RegExp(s1.replace(/([\(\)\[\]\{\}\^\$\+\-\*\?\.\"\'\|\/\\])/g, "\\$1"), "ig");
	return this.replace(r, s2);
}

// --------------------------------------------
// 去除左右空格
// --------------------------------------------
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

// --------------------------------------------
// 去除左空格
// --------------------------------------------
String.prototype.ltrim = function() {
	return this.replace(/^\s*/g, "");
}

// --------------------------------------------
// 去除右空格
// --------------------------------------------
String.prototype.rtrim = function() {
	return this.replace(/\s*$/g, "");
}

// --------------------------------------------
// 判断是否由指定字符串开始
// --------------------------------------------
String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
}

// --------------------------------------------
// 判断是否由指定字符串结束
// --------------------------------------------
String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
}

// --------------------------------------------
// 获取ansi长度，双字节（汉字）返回长度为2
// --------------------------------------------
String.prototype.ansiLength = function () {
	var i = l = 0;
	for( ; i < this.length; i++) {
		if (this.charCodeAt(i) > 255) {
			l += 2;
		} else {
			l++;
		}
	}
	return l;
}

// --------------------------------------------
// 获取指定ansi长度的子字符
// 无safe参数或者不为true时，如果指定len处为汉字则减一个字
// --------------------------------------------
String.prototype.ansiSubstr = function (start, len, safe) {
	var l = 0;
	for(var i = start ; i < this.length; i++) {
		if (this.charCodeAt(i) > 255) {
			l += 2;
		} else {
			l++;
		}
		if (l >= len) {
			if (l > len && safe == false)
				return this.substr(start, i + 1);
			else
				return this.substr(start, i);
		}
	}
	return this.substr(start, this.length);
}

// --------------------------------------------
// 字符串左侧按指定长度补齐字符
// --------------------------------------------
String.prototype.padLeft = function(len, s) {
	if (s != null)
		return this.pad(len, s, false);
	else
		return this.pad(len, ' ', false);
}

// --------------------------------------------
// 字符串右侧按指定长度补齐字符
// --------------------------------------------
String.prototype.padRight = function(len, s) {
	if (s != null)
		return this.pad(len, s, true);
	else
		return this.pad(len, ' ', true);
}

String.prototype.pad = function(len, s, right) {
	if (this.length < len) {
		var paddingString = new String();
		for (var i = 1; i <= (len - this.length); i++)
			paddingString += s;
		if (right)
			return (this + paddingString);
		else
			return (paddingString + this);
	} else
		return this;
}

Date.prototype.toString = function() {
	var s = "";
	s += this.getFullYear();
	if (this.getMonth() < 9) {
		s += "-0" + (this.getMonth() + 1);
	} else {
		s += "-" + (this.getMonth() + 1);
	}
	if (this.getDate() < 10) {
		s += "-0" + this.getDate();
	} else {
		s += "-" + this.getDate();
	}
	if (this.getHours() < 10) {
		s += " 0" + this.getHours();
	} else {
		s += " " + this.getHours();
	}
	if (this.getMinutes() < 10) {
		s += ":0" + this.getMinutes();
	} else {
		s += ":" + this.getMinutes();
	}
	if (this.getSeconds() < 10) {
		s += ":0" + this.getSeconds();
	} else {
		s += ":" + this.getSeconds();
	}
	return s;
};

// --------------------------------------------
// 日期加，减请用负数
// add: 加减量
// addType:
// 加减量类型。0,y,yy,year：年;1,m,mm,month：月;3,h,hh,hour：小时;4,mi,nn,minute：分;5,s,ss,second：秒;6,w,ww,week：星期;其它值为天
// --------------------------------------------
Date.dateAdd = function(date, add, addType) {
	// var d = new Date(date);
	var d = $.type(date) == "date" ? date : date.toString().toDate();
	h = d.valueOf();
	if (add != 0) {
		if (addType) {
			var strAddType = addType.toString().toLowerCase();
			if (strAddType == 0 || strAddType == "year" || strAddType == "y" || strAddType == "yy") { // 加年
				d = new Date(d.getFullYear() + parseInt(add), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes(), d
								.getSeconds());
				h = d.valueOf();
			} else if (strAddType == 1 || strAddType == "month" || strAddType == "m" || strAddType == "mm") { // 加月
				d = new Date(d.getFullYear(), d.getMonth() + parseInt(add), d.getDate(), d.getHours(), d.getMinutes(), d
								.getSeconds());
				h = d.valueOf();
			} else if (strAddType == 3 || strAddType == "hour" || strAddType == "h" || strAddType == "hh") // 加小时
				h = h + add * 60 * 60 * 1000;
			else if (strAddType == 4 || strAddType == "minute" || strAddType == "mi" || strAddType == "nn") // 加分钟
				h = h + add * 60 * 1000;
			else if (strAddType == 5 || strAddType == "second" || strAddType == "s" || strAddType == "ss") // 加秒
				h = h + add * 1000;
			else if (strAddType == 6 || strAddType == "week" || strAddType == "w" || strAddType == "ww") // 加周
				h = h + add * 7 * 24 * 60 * 60 * 1000;
			else
				// 加日（缺省）
				h = h + add * 24 * 60 * 60 * 1000;
		} else
			h = h + add * 24 * 60 * 60 * 1000;
	}
	d = new Date(h);
	return d;
}

// --------------------------------------------
// 调式信息
// --------------------------------------------
Utils.log = function(e) {
	if (Utils.debug) {
		alert(e);
	}
};
// --------------------------------------------
// 获取记录字段值
// --------------------------------------------
Utils.getRecordValue = function(data, recordNo, fieldName) {
	if (data.fs && data.rs) {
		var index = $.inArray(fieldName, data.fs);
		if (index > -1 && recordNo < data.rs.length) {
			return data.rs[recordNo][index];
		}
	}
	return null;
};

// --------------------------------------------
// 设置记录字段值
// --------------------------------------------
Utils.setRecordValue = function(data, recordNo, fieldName, fieldValue) {
	if (Utils.getRecordValue(data, recordNo, fieldName) == fieldValue)
		return;
	if (data.fs && data.rs) {
		var index = $.inArray(fieldName, data.fs);
		if (index > -1 && recordNo < data.rs.length) {
			data.rs[recordNo][index] = fieldValue;
			return;
		}
	}
};

Utils.now = function() {
	return (new Date()).getTime();
};
var table = "00000000 77073096 EE0E612C 990951BA 076DC419 706AF48F E963A535 9E6495A3 0EDB8832 79DCB8A4 E0D5E91E 97D2D988 09B64C2B 7EB17CBD E7B82D07 90BF1D91 1DB71064 6AB020F2 F3B97148 84BE41DE 1ADAD47D 6DDDE4EB F4D4B551 83D385C7 136C9856 646BA8C0 FD62F97A 8A65C9EC 14015C4F 63066CD9 FA0F3D63 8D080DF5 3B6E20C8 4C69105E D56041E4 A2677172 3C03E4D1 4B04D447 D20D85FD A50AB56B 35B5A8FA 42B2986C DBBBC9D6 ACBCF940 32D86CE3 45DF5C75 DCD60DCF ABD13D59 26D930AC 51DE003A C8D75180 BFD06116 21B4F4B5 56B3C423 CFBA9599 B8BDA50F 2802B89E 5F058808 C60CD9B2 B10BE924 2F6F7C87 58684C11 C1611DAB B6662D3D 76DC4190 01DB7106 98D220BC EFD5102A 71B18589 06B6B51F 9FBFE4A5 E8B8D433 7807C9A2 0F00F934 9609A88E E10E9818 7F6A0DBB 086D3D2D 91646C97 E6635C01 6B6B51F4 1C6C6162 856530D8 F262004E 6C0695ED 1B01A57B 8208F4C1 F50FC457 65B0D9C6 12B7E950 8BBEB8EA FCB9887C 62DD1DDF 15DA2D49 8CD37CF3 FBD44C65 4DB26158 3AB551CE A3BC0074 D4BB30E2 4ADFA541 3DD895D7 A4D1C46D D3D6F4FB 4369E96A 346ED9FC AD678846 DA60B8D0 44042D73 33031DE5 AA0A4C5F DD0D7CC9 5005713C 270241AA BE0B1010 C90C2086 5768B525 206F85B3 B966D409 CE61E49F 5EDEF90E 29D9C998 B0D09822 C7D7A8B4 59B33D17 2EB40D81 B7BD5C3B C0BA6CAD EDB88320 9ABFB3B6 03B6E20C 74B1D29A EAD54739 9DD277AF 04DB2615 73DC1683 E3630B12 94643B84 0D6D6A3E 7A6A5AA8 E40ECF0B 9309FF9D 0A00AE27 7D079EB1 F00F9344 8708A3D2 1E01F268 6906C2FE F762575D 806567CB 196C3671 6E6B06E7 FED41B76 89D32BE0 10DA7A5A 67DD4ACC F9B9DF6F 8EBEEFF9 17B7BE43 60B08ED5 D6D6A3E8 A1D1937E 38D8C2C4 4FDFF252 D1BB67F1 A6BC5767 3FB506DD 48B2364B D80D2BDA AF0A1B4C 36034AF6 41047A60 DF60EFC3 A867DF55 316E8EEF 4669BE79 CB61B38C BC66831A 256FD2A0 5268E236 CC0C7795 BB0B4703 220216B9 5505262F C5BA3BBE B2BD0B28 2BB45A92 5CB36A04 C2D7FFA7 B5D0CF31 2CD99E8B 5BDEAE1D 9B64C2B0 EC63F226 756AA39C 026D930A 9C0906A9 EB0E363F 72076785 05005713 95BF4A82 E2B87A14 7BB12BAE 0CB61B38 92D28E9B E5D5BE0D 7CDCEFB7 0BDBDF21 86D3D2D4 F1D4E242 68DDB3F8 1FDA836E 81BE16CD F6B9265B 6FB077E1 18B74777 88085AE6 FF0F6A70 66063BCA 11010B5C 8F659EFF F862AE69 616BFFD3 166CCF45 A00AE278 D70DD2EE 4E048354 3903B3C2 A7672661 D06016F7 4969474D 3E6E77DB AED16A4A D9D65ADC 40DF0B66 37D83BF0 A9BCAE53 DEBB9EC5 47B2CF7F 30B5FFE9 BDBDF21C CABAC28A 53B39330 24B4A3A6 BAD03605 CDD70693 54DE5729 23D967BF B3667A2E C4614AB8 5D681B02 2A6F2B94 B40BBE37 C30C8EA1 5A05DF1B 2D02EF8D";    

Utils.crc32 = function(str, crc) {  
    if( crc == window.undefined ) crc = 0;  
    var n = 0; // a number between 0 and 255
    var x = 0; // an hex number
    crc = crc ^ (-1);  
    for( var i = 0, iTop = str.length; i < iTop; i++ ) {  
        n = ( crc ^ str.charCodeAt( i ) ) & 0xFF;  
        x = "0x" + table.substr( n * 9, 8 );  
        crc = ( crc >>> 8 ) ^ x;  
    }  
    crc = crc ^ (-1); 
    return crc;  
}

// --------------------------------------------
// 获取地址栏参数对象
// --------------------------------------------
Utils.getLocationParObj = function() {
	var str = location.href;
	var index = str.indexOf("?");
	var obj = {};
	if (index > 0) {
		str = str.substring(index + 1);
		var arr = str.split("&");
		for (var i = 0; i < arr.length; i++) {
			var index = arr[i].indexOf("=");
			var key = arr[i].substring(0, index);
			var value = arr[i].substring(index + 1);
			obj[key] = value;
		}
	}
	return obj;
};
// --------------------------------------------
// 获取地址栏参数
// --------------------------------------------
Utils.getLocationPara = function(param) {
	var obj = Utils.getLocationParObj();
	if (typeof(obj[param]) != "undefined") {
		return obj[param];
	} else {
		return null;
	}
};
Utils.event.getEvent = function(event) {
	return event || window.event;
};
Utils.event.getSrcEle = function(event) {
	var e = Utils.event.getEvent(event);
	return e.srcElement || e.target;
};
Utils.event.getKeyNum = function(event) {
	var e = Utils.event.getEvent(event);
	return e.charCode || e.keyCode;
};
Utils.event.endEvent = function(event) {
	try {
		var e = Utils.event.getEvent(event);
		e.keyCode = 0;
		e.cancelBubble = true;
		e.returnValue = false;
	} catch (e) {
	} finally {
		return false;
	}
};
Utils.event.keyHandler = function(event) {
	var e = Utils.event.getEvent(event);
	// if (e.ctrlKey || e.shiftKey || e.altKey) {
	// return Utils.event.endEvent(e);
	// }
	if (Utils.event.getKeyNum(event) == 116) {
		var b = Utils.event.endEvent(e);
		return b;
	}
};
Utils.event.mouseHandler = function(event) {
	var e = Utils.event.getEvent(event);
	if (e.button == 2) {
		document.oncontextmenu = new Function("return false;");
	}
};

// --------------------------------------------
// 获取url所有的参数
// --------------------------------------------
Utils.getUrlParams = function(url) {
	var result = null;
	if (url) {
		if (url.indexOf("?") != -1) {
			var urlParams = url.substr(url.indexOf("?") + 1);
			if (urlParams) {
				result = {};
				var urlParamsObj = Utils.getParams(urlParams, false, "=", "&");
				for(var key in urlParamsObj)
					result[key] = urlParamsObj[key];
			}
		}
	}
	return result;
}

// --------------------------------------------
// 获取指定URL中指定参数名的值
// --------------------------------------------
Utils.getUrlParamValue = function(url, paramName) {
	if (url)
		return Utils.getParamValue(url.substr(url.indexOf('?') + 1), paramName, true, '=', '&');
	else
		return null;
}

// --------------------------------------------
// 获取页面参数
// --------------------------------------------
Utils.getPageValue = function(paramName) {
	var paraValue = null;
	if (Utils.hasPageValue(paramName)) {
		if (Utils.urlHasParam(document.location.href, paramName))
			paraValue = Utils.getUrlParamValue(document.location.href, paramName);
		else if (window.dialogArguments && paramName in window.dialogArguments)
			paraValue = window.dialogArguments[paramName];
	}
	return paraValue;
}

// --------------------------------------------
// 获取当前页面所有的参数
// --------------------------------------------
Utils.getPageParams = function() {
	var result = Utils.getUrlParams(document.location.href);
	if (window.dialogArguments) {
		if (!result)
			result = {};
		for(var key in window.dialogArguments)
			result[key] = window.dialogArguments[key];
	}
	return result;
}

// --------------------------------------------
// 判断是否有页面参数
// --------------------------------------------
Utils.hasPageValue = function(paramName) {
	var result = false;
	if (Utils.urlHasParam(document.location.href, paramName))
		result = true;
	else if (window.dialogArguments && paramName in window.dialogArguments)
		result = true;
	return result;
}

// --------------------------------------------
// 判断url是否有指定参数
// --------------------------------------------
Utils.urlHasParam = function(url, paramName) {
	if (url)
		return Utils.paramExists(url.substr(url.indexOf("?") + 1), paramName, false, '=', '&');
	else
		return false;
}

// --------------------------------------------
// 为url添加参数，params格式：a=1&b=2&c=3...或者对象格式
// url为null或者undefined直接返回，为空则返回：?a=1&b=2&c=3...
// 如果params中的参数在url中存在，则将值覆盖。参数名区分大小写
// --------------------------------------------
Utils.urlAddParams = function (url, params) {
	if (params && !objIsEmpty(params)) {
		url = url.trim();
		var urlBase = null;
		var urlParams = "";
		if (url.indexOf("?") >= 0) {
			urlBase = url.substr(0, url.indexOf("?"));
			urlParams = url.substr(url.indexOf("?") + 1);
		} else {
			urlBase = url;
		}
		if (typeof(params) == "object") {
			for(var key in params) {
				var paramValue = params[key];
				//if (paramValue)
					if (Utils.paramExists(urlParams, key, false, "=", "&"))
						urlParams = Utils.setParamValue(urlParams, key, paramValue, false, "=", "&");
					else
						urlParams += "&" + key + "=" + paramValue;
			}
			if (urlParams.substr(0, 1) == "&")
				urlParams = urlParams.substr(1);
			url = urlBase + "?" + urlParams;
		} else {
			params = params.trim();
			if (params.substr(0, 1) == "&")
				params = params.substr(1);
			if (urlParams) {
				var paramsObj = Utils.getParams(params, false, "=", "&");
				return Utils.urlAddParams(url, paramsObj);
			} else
				url = urlBase + "?" + params;
		}
	}
	return url;
}

// --------------------------------------------
// 为url参数编码，params格式：a=1&b=2&c=3...或者对象格式
// 返回值同params格式
// --------------------------------------------
Utils.encodeURIParams = function (params) {
	var result = null;
	if (params) {
		result = {};
		if (typeof(params) == "object") {
			for(var key in params)
				result[key] = encodeURIComponent(params[key]);
		} else {
			var paramsObj = Utils.getParams(params, false, "=", "&");
			var resultObj = pageObj.encodeURIParams(paramsObj);
			for(var key in resultObj)
				if (result)
					result += "&" + key + "=" + resultObj[key];
				else
					result = key + "=" + resultObj[key];
		}
	}
	return result;
}

// --------------------------------------------
// 模仿oracle中的decode函数
// 示例：decode(x, 0, "a", 1, "b", 2, "c",
// "d")，x=0返回a，x=1返回b，类推。全部不匹配则返回d，如果没有d则返回null
// --------------------------------------------
Utils.decode = function () {
	if (arguments.length <= 3)
		return null;
	var expression = arguments[0];
	var compares = new Array();
	var values = new Array();
	for(var i = 1; i < arguments.length; i = i + 2) {
		if (i + 1 == arguments.length) {
			values.push(arguments[i]);
		} else {
			compares.push(arguments[i]);
			values.push(arguments[i + 1]);
		}
	}
	for(var i = 0; i < compares.length; i++) {
		if (expression == compares[i])
			return values[i];
	}
	if (values.length > compares.length)
		return values[values.length - 1];
	else
		return null;
// var compares = new Array();
// var values = new Array();
// for(var i = 1; i < arguments.length; i = i + 2) {
// if (i + 1 == arguments.length) {
// values.push(i);
// } else {
// compares.push(i);
// values.push(i + 1);
// }
// }
// for(var i = 0; i < compares.length; i++) {
// if (expression == arguments[compares[i]])
// return arguments[values[i]];
// }
// if (values.length > compares.length)
// return arguments[values[values.length - 1]];
// else
// return null;
}

// --------------------------------------------
// 模仿oracle中的case语句
// 示例：switchCase(a, 0, b, 1, c, 2, d, 3,
// 4)，a条件成立返回0，b返回1，类推。所有条件不匹配则返回4，如果没有4则返回null
// --------------------------------------------
Utils.switchCase = function () {
	if (arguments.length <= 1)
		return null;
	var expressions = new Array();
	var values = new Array();
	for(var i = 0; i < arguments.length; i = i + 2) {
		if (i + 1 == arguments.length) {
			values.push(arguments[i]);
		} else {
			expressions.push(arguments[i]);
			values.push(arguments[i + 1]);
		}
	}
	for(var i = 0; i < expressions.length; i++) {
		if (expressions[i])
			return values[i];
	}
	if (values.length > expressions.length)
		return values[values.length - 1];
	else
		return null;
}

// --------------------------------------------
// 转换父字符串中的参数名、参数值键值对对象
// 如"abc=123;xyz=666;mnq=888"字符串,得到"{abc: "123", xyz: "666", mnq: "888"}"
// 可自行指定分隔体等于符号和分隔符号，ignoreCase缺省为false，为true时则key全部转换为小写
// --------------------------------------------
Utils.getParams = function(parent, ignoreCase, equals, split) {
	if (parent == undefined || parent == null)
		return {};
	var strEquals = equals;
	if (strEquals == undefined || strEquals == null || strEquals == "" || strEquals.toLowerCase() == "null")
		strEquals = "=";
	var strSplit = split;
	if (strSplit == undefined || strSplit == null || strSplit == "" || strSplit.toLowerCase() == "null")
		strSplit = ";";
	var aryParams = parent.split(strSplit);
	var strParam;
	var paramsMap = {};
	for (var i = 0; i < aryParams.length; i++) {
		strParam = aryParams[i];
		if (strParam) {
			if (strParam.indexOf(strEquals) == -1) {
				paramsMap[strParam] = "";
			} else {
				if (ignoreCase == undefined || !ignoreCase)
					paramsMap[strParam.substring(0, strParam.indexOf(strEquals))] = strParam.substring(strParam
									.indexOf(strEquals)
									+ strEquals.length, strParam.length);
				else
					paramsMap[strParam.substring(0, strParam.indexOf(strEquals)).toLowerCase()] = strParam.substring(
							strParam.indexOf(strEquals) + strEquals.length, strParam.length);
			}
		}
	}
	return paramsMap;
}

// --------------------------------------------
// 获取父字符串中的指定参数名的参数值
// 如"abc=123;xyz=666;mnq=888"字符串中的某项的值,paramName="xyz"返回"666"。不存在时返回""，而非null或者undefined以便于调用
// 可自行指定分隔体等于符号和分隔符号，ignoreCase缺省为false
// --------------------------------------------
Utils.getParamValue = function(parent, paramName, ignoreCase, equals, split) {
	var paramsMap = Utils.getParams(parent, ignoreCase, equals, split);
	var returnValue;
	if (ignoreCase == undefined || !ignoreCase)
		returnValue = paramsMap[paramName];
	else
		returnValue = paramsMap[paramName.toLowerCase()];
	if (typeof(returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}

// --------------------------------------------
// 判断父字符串中的指定参数名的子参数是否存在
// 存在返回true
// --------------------------------------------
Utils.paramExists = function(parent, paramName, ignoreCase, equals, split) {
	var paramsMap = Utils.getParams(parent, ignoreCase, equals, split);
	if (ignoreCase == undefined || !ignoreCase)
		return paramName in paramsMap;
	else
		return paramName.toLowerCase() in paramsMap;
}

// --------------------------------------------
// 写入父字符串中的指定参数名的参数值
// 如写入"abc=123;xyz=666;mnq=888"字符串中的某项的值,如paramName="xyz",paramValue="XXX"
// 返回"abc=123;xyz=XXX;mnq=888"
// 可自行指定分隔体等于符号和分隔符号，ignoreCase缺省为false。本方法目前有BUG，即返回串参数顺序发生了变化，如果对此无要求可调用
// --------------------------------------------
Utils.setParamValue = function(parent, paramName, paramValue, ignoreCase, equals, split) {
	var paramsMap = Utils.getParams(parent, false, equals, split);
	var blnReplace = false;
	// 遍历替换，不存在则添加
	for (var key in paramsMap) {
		if ((ignoreCase != undefined && ignoreCase && key.toLowerCase() == paramName.toLowerCase()) || key == paramName) {
			paramsMap[key] = paramValue;
			blnReplace = true;
			break;
		}
	}
	if (!blnReplace)
		paramsMap[paramName] = paramValue;
	var returnValue = "";
	var strEquals = equals;
	if (strEquals == undefined || strEquals == null || strEquals == "" || strEquals.toLowerCase() == "null")
		strEquals = "=";
	var strSplit = split;
	if (strSplit == undefined || strSplit == null || strSplit == "" || strSplit.toLowerCase() == "null")
		strSplit = ";";
	for (var key in paramsMap) {
		if (returnValue == "")
			returnValue = key + strEquals + paramsMap[key];
		else
			returnValue = returnValue + strSplit + key + strEquals + paramsMap[key];
	}
	return returnValue;
}

// --------------------------------------------
// 子字符串出现次数
// --------------------------------------------
Utils.getSubStrCount = function(s, child, ignoreCase) {
	var count = 0;
	var start = 0;
	while (s.indexOfIgnoreCase(child, start, ignoreCase) >= 0 && start < s.length) {
		count++;
		start = s.indexOfIgnoreCase(child, start, ignoreCase) + child.length;
	}
	return count;
}

// --------------------------------------------
// 获取子字符串数组
// --------------------------------------------
Utils.getSubStrs = function(s, split, ignoreCase) {
	if (s == undefined || s == null || s == "") {
		return null;
	}
	var result = new Array();
	if (split == undefined || split == null || split == "") {
		result.push(s);
		return result;
	}
	// aaa###bbb###ccc###ddd###eee
	var strParent = s + split;
	var intPos;
	var intStart = 0;
	do {
		intPos = strParent.indexOfIgnoreCase(split, intStart, ignoreCase);
		if (intPos >= 0) {
			var strSub = strParent.substring(intStart, intPos);
			result.push(strSub);
		}
		intStart = intPos + split.length;
	} while (intPos >= 0);
	return result;
}

// --------------------------------------------
// 获取指定索引位置子字符串
// --------------------------------------------
Utils.getSubStr = function(s, split, splitIndex, ignoreCase) {
	if (s == undefined || s == null || s == "" || splitIndex < 0) {
		return "";
	}
	var subStrList = Utils.getSubStrs(s, split, ignoreCase);
	return subStrList.length > splitIndex ? subStrList[splitIndex] : "";
}

Utils.initTokens = function(responseObj) {
	if ("$ts" in responseObj) {
		var mainWin = getMainFrame(window);
		if (mainWin) {
			mainWin['$ts'] = responseObj.ts;
			$(mainWin['$usedTs']).each(function(index, ele) {
						var index2 = $.inArray(ele, mainWin['$ts']);
						if (index2 >= 0) {
							mainWin['$ts'].splice(index2, 1);
						}
					});
			delete responseObj.ts;
		}
	}
}
// --------------------------------------------
// 将请求字符串转换为对象
// --------------------------------------------
Utils.UrlToObj = function(url){
	var content = url+'';
	if(content.substr(0,1)=='?')
		content = content.substr(1);
	var arr = content.split('&');
	var obj={};
	for(var i=0;i<arr.length;i++){
		var index = arr[i].indexOf('=');
		if( index < 1 ) continue;
		var pro = arr[i].substr(0,index);
		var value = arr[i].substr(index+1);
		obj[pro]=value;
	}
	return obj;
}

// --------------------------------------------
// 不处理带数组的属性值
// --------------------------------------------
Utils.ObjToUrl = function(obj){
	if(!obj) return null;
	var content = '';
	for(var pro in obj){
		content += pro +'='+ obj[pro]+'&';
	}
	return content.substr(0,content.length-1);
}

Utils.initUrl = function(url) {
	var obj = Utils.UrlToObj(url);
	if(!('u' in obj)){
		var userId = getUserId();
		obj['u']=userId;
	}
	obj['ms']=Utils.now();
	var stag='';
	stag+=obj['module'];
	stag+=obj['service'];
	stag+=obj['method'];
	stag+=obj['ms'];
	var token = Utils.crc32(stag);
	if(token)
		obj['t']=token;
	var content = Utils.ObjToUrl(obj);
	return content;
}

// ------------------------------
// 只能输入数值
// ------------------------------
Utils.checkNumberKey = function(key, allowNegative) {
	return Utils.checkIntegerKey(key, allowNegative) || key == 110// 小键盘小数点
			|| key == 190;// 小数点
}

Utils.checkIntegerKey = function(key, allowNegative) {
	return (key >= 48 && key <= 57)// 0..9
			|| (key >= 96 && key <= 105)// 小键盘0..9
			|| (allowNegative && key == 189);// 负号
}

// 数字输入框允许的功能键
Utils.checkNumberFnKey = function(key) {
	return key == 8// 退格键backspace
			|| key == 9// tab键
			|| key == 35// end键
			|| key == 36// home键
			|| key == 37// 左方向键
			|| key == 39// 右方向键
			|| key == 45// 插入键insert
			|| key == 46;// 删除键delete
}

// 只能输入实数（浮点数），包括负数。如果不需要负数请自行参考实现，修改下面的Utils.checkNumberKey第2个参数为false
Utils.keyPressNumberOnly = function() {
	var key = window.event.keyCode;
	if (Utils.checkNumberKey(key, true) || Utils.checkNumberFnKey(key)) {
		window.event.returnValue = true;
	} else {
		window.event.returnValue = false;
	}
}

// 只能输入整数，包括负数。如果不需要负数请自行参考实现，修改下面的Utils.checkIntegerKey第2个参数为false
Utils.keyPressIntegerOnly = function() {
	var key = window.event.keyCode;
	if (Utils.checkIntegerKey(key, true) || Utils.checkNumberFnKey(key)) {
		window.event.returnValue = true;
	} else {
		window.event.returnValue = false;
	}
}

Utils.validata = function(sDouble) {
	var re = /^\d+(?=\.{0,1}\d+$|$)/
	return re.test(sDouble)
}

Utils.isInteger = function(str) {
	if (isNaN(str)) {
		return (false);
	} else {
		if (str.search("[.*]") != -1) {
			return (false);
		}
	}
	return (true);
}
// ---------------------------------
// 日期格式：2014-08-04 13:12:59.313
//
//
// ---------------------------------
Utils.diffTime = function(str1,str2){
	var d1 = str1.toDate();
	var d2 = str2.toDate();
	var dd = d2.getTime() - d1.getTime();
	return dd;
}

// ---------------------------------
// 文本框只能输入指定位正数
// $("#area").intInput();
// ---------------------------------
$.fn.intInput = function(len, allowLessThanZero) {
    var inputLen = len ? parseInt(len) : 0;
    $(this).css("ime-mode", "disabled");
    if (inputLen > 0) 
        $(this).attr("__inputLen", inputLen);
       if (allowLessThanZero) 
        $(this).attr("__allowLessThanZero", 1);
    this.bind("keypress", function(e){
        if (e.charCode === 0) 
            return true;
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code >= 48 && code <= 57) {
        	var selText = Utils.getSelectedText(this);
        	if (this.value && inputLen > 0 && ((!selText && this.value.length >= inputLen) || (selText && this.value.length - selText.length >= inputLen)))
        		return false;
            return true;
        }
        var allowLessThanZero = parseInt($(this).attr("__allowLessThanZero")) == 1 ? true : false;
        // 输入"-"
        if (allowLessThanZero && code == 45) {
            var selText = Utils.getSelectedText(this);
            if (selText.indexOf("-") > 0 || (this.value.indexOf("-") == -1 && Utils.getCursorPos(this) == 0)) 
                return true; // 选中文本包含"-"
        }
        return false;
    });
    this.bind("blur", function(){
        if (isNaN(this.value)) {
            this.value = "";
        }
        var inputLen = parseInt($(this).attr("__inputLen"));
        if (inputLen > 0 && this.value.length > inputLen) {
            this.value = this.value.substr(0, inputLen);
        }
        if (this.value) 
            this.value = parseFloat(this.value).toFixed(0);
        $(this).trigger("input");
    });
    this.bind("paste", function(){
        return false;
    });
    this.bind("dragenter", function(){
        return false;
    });
    this.bind("keyup", function(){
    
    });
    this.bind("propertychange", function(e){
        if (isNaN(this.value)) 
            this.value = this.value.replace(/[^0-9]/g, "");// /[^0-9\.]/g
    });
    this.bind("input", function(e){
        if (isNaN(this.value)) 
            this.value = this.value.replace(/[^0-9]/g, "");// /[^0-9\.]/g
    });
};

// ---------------------------------
// 文本框只能输入浮点数，仅2位小数
// $("#area").decimalInput();
// ---------------------------------
$.fn.decimalInput = function(allowLessThanZero) {
    $(this).css("ime-mode", "disabled");
    if (allowLessThanZero) 
        $(this).attr("__allowLessThanZero", 1);
    this.bind("keypress", function(e){
        if (e.charCode === 0) 
            return true; // 非字符键 for firefox
        var code = (e.keyCode ? e.keyCode : e.which); // 兼容火狐 IE
        if (code >= 48 && code <= 57) {
            var pos = Utils.getCursorPos(this);
            var selText = Utils.getSelectedText(this);
            var dotPos = this.value.indexOf(".");
            if (dotPos > 0 && pos > dotPos) {
                if (pos > dotPos + 2) 
                    return false;
                if (selText.length > 0 || this.value.substr(dotPos + 1).length < 2) 
                    return true;
                else 
                    return false;
            }
            return true;
        }
        var allowLessThanZero = parseInt($(this).attr("__allowLessThanZero")) == 1 ? true : false;
        // 输入"-"
        if (allowLessThanZero && code == 45) {
            var selText = Utils.getSelectedText(this);
            if (selText.indexOf("-") > 0 || (this.value.indexOf("-") == -1 && Utils.getCursorPos(this) == 0)) 
                return true; // 选中文本包含"-"
        }
        // 输入"."
        if (code == 46) {
			var selText = Utils.getSelectedText(this);
			if (selText.indexOf(".") > 0) 
				return true; // 选中文本包含"."
            else {
            	if (allowLessThanZero) {
            		if (/^-[0-9]+\.$/.test(this.value + String.fromCharCode(code))) 
						return true;
            	} else
					if (/^[0-9]+\.$/.test(this.value + String.fromCharCode(code))) 
						return true;
            }
        }
        return false;
    });
    this.bind("blur", function() {
        if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
            this.value = this.value.substr(0, this.value.length - 1);
        }
        else 
            if (isNaN(this.value)) {
                this.value = "";
            }
        if (this.value) {
        	var value = parseFloat(this.value).toFixed(2);
        	if (isNaN(value))
        		value = 0;
        	else {
        		// 去除最后没必要的0
        		value = value.toString();
        		if (value.substr(value.indexOf(".")) == ".00")
        			value = value.substr(0, value.indexOf("."));
        		else if (value.substr(value.length - 1) == "0")
        			value = value.substr(0, value.length - 1);
        	}
            this.value = value;
        }
        $(this).trigger("input");
    });
    this.bind("paste", function(){
        if (window.clipboardData) {
            var s = clipboardData.getData('text');
            if (!isNaN(s)) {
                value = parseFloat(s);
                return true;
            }
        }
        return false;
    });
    this.bind("dragenter", function(){
        return false;
    });
    this.bind("keyup", function(){
    
    });
    this.bind("propertychange", function(e){
        if (isNaN(this.value)) 
            this.value = this.value.replace(/[^0-9\.]/g, "");
    });
    this.bind("input", function(e){
        if (isNaN(this.value)) 
            this.value = this.value.replace(/[^0-9\.]/g, "");
    });
};

// ---------------------------------
// 获取当前光标在文本框的位置
// ---------------------------------
Utils.getCursorPos = function (domObj) {
    var position = 0;
    if (domObj.selectionStart || domObj.selectionStart == '0') {
        position = domObj.selectionStart;
    }
    else 
        if (document.selection) { // for IE
            domObj.focus();
            var currentRange = document.selection.createRange();
            var workRange = currentRange.duplicate();
            domObj.select();
            var allRange = document.selection.createRange();
            while (workRange.compareEndPoints("StartToStart", allRange) > 0) {
                workRange.moveStart("character", -1);
                position++;
            }
            currentRange.select();
        }
    
    return position;
}

// ---------------------------------
// 获取当前文本框选中的文本
// ---------------------------------
Utils.getSelectedText = function (domObj){
    if (domObj.selectionStart || domObj.selectionStart == '0') {
        return domObj.value.substring(domObj.selectionStart, domObj.selectionEnd);
    }
    else 
        if (document.selection) { // for IE
            domObj.focus();
            var sel = document.selection.createRange();
            return sel.text;
        }
        else 
            return '';
}

// ---------------------------------
// 设置input(input text、textarea元素)选中文字
// ---------------------------------
Utils.setSelectRange = function(input, start, end) { 
	if (!input || start < 0 || end < 0)
		return;
	if (typeof input.createTextRange != 'undefined') {// IE
		var range = input.createTextRange();
		// 先把相对起点移动到0处
		range.moveStart( "character", 0);
		range.moveEnd( "character", 0);
		range.collapse( true); // 移动插入光标到start处
		range.moveEnd( "character", end);
		range.moveStart( "character", start);
		range.select();
	} else if (typeof input.setSelectionRange != 'undefined') {
		input.setSelectionRange(start, end);
		input.focus();
	}
}

// ---------------------------------
// 插入内容
// ---------------------------------
Utils.insertText = function (input, s) {
    if (document.selection) {
        var sel = document.selection.createRange();
        sel.text = s;
    } else if (typeof input.selectionStart === 'number' && typeof input.selectionEnd === 'number') {
        var startPos = input.selectionStart,
            endPos = input.selectionEnd,
            cursorPos = startPos,
            tmpStr = input.value;
        input.value = tmpStr.substring(0, startPos) + s + tmpStr.substring(endPos, tmpStr.length);
        cursorPos += s.length;
        input.selectionStart = input.selectionEnd = cursorPos;
    } else {
        input.value += s;
    }
}

// ---------------------------------
// 光标移动到最后
// ---------------------------------
Utils.moveEnd = function (input) {
    input.focus();
    var len = input.value.length;
    if (document.selection) {
        var sel = input.createTextRange();
        sel.moveStart('character',len);
        sel.collapse();
        sel.select();
    } else if (typeof input.selectionStart == 'number' && typeof input.selectionEnd == 'number') {
        input.selectionStart = input.selectionEnd = len;
    }
}

// ------------------------------
// 返回元素坐标
// ------------------------------
Utils.getCoords = function(el) {
	var box = el.getBoundingClientRect(), isQuirk = (document.documentMode) ? (document.documentMode == 5)
			? true
			: false : ((document.compatMode == "CSS1Compat") ? false : true), doc = el.ownerDocument, body = doc.body, html = doc.documentElement, clientTop = html.clientTop
			|| body.clientTop || 0, clientLeft = html.clientLeft || body.clientLeft || 0, top = box.top
			+ (self.pageYOffset || !isQuirk && html.scrollTop || body.scrollTop) - clientTop, left = box.left
			+ (self.pageXOffset || !isQuirk && html.scrollLeft || body.scrollLeft) - clientLeft;
	return {
		top : top,
		left : left,
		height : box.height ? box.height : $(el).height(),
		width : box.width ? box.width : $(el).width()
	};
};


Utils.getIframe = function() {
	var eles = parent.document.getElementsByTagName('iframe');
	for (var i = 0; i < eles.length; i++) {
		if (eles[i].contentWindow == this) {
			return eles[i];
		}
	}
	return null;
}

// --------------------------------------------
// 获取select值，在部分IE8下jquery取值会异常返回对象
// --------------------------------------------
Utils.getSelectValue = function(selectId) {
	if (!selectId)
		return null;
	var selId = selectId.substr(0, 1) == "#" ? selectId : "#" + selectId;
	var result = $(selId).val();
	if (!result)
		return result;
	else if (typeof(result) == "string")
		return result;
	else if (Object.prototype.toString.call(result) === '[object Array]') {
		if (result.length > 0)
			return result[0];
		else
			return null;
	} else if (typeof(result) == "object") {
		for(var key in result) {
			return result[key];
		}
	} else
		return result; 
}

// --------------------------------------------
// 写select值，在部分IE8下jquery赋值异常而无法正确赋值
// --------------------------------------------
Utils.setSelectValue = function(selectId, value) {
	if (!selectId)
		return;
	var selId = selectId.substr(0, 1) == "#" ? selectId : "#" + selectId;
	$(selId).val(value);
	if (Utils.getSelectValue(selectId) != value) {
		var select = document.getElementById(selectId);
		for(i = 0; i < select.length; i++) {  
			if (value == select.options[i].value) {  
				select.options[i].selected = true;
				break;
			}  
		}
	}
}

Utils.clearFileInput = function(inputId) {
	var el = $("#" + inputId);
	if (el.length > 0) {
		if (Utils.isIE()) {
			el.select();
			document.execCommand("delete");
		} else
			el.val("");
	}
}

// --------------------------------------------
// 播放声音
// --------------------------------------------
Utils.playSound = function(id, soundFile, play) {
	var isIE = this.isIE();
	try {
		var player = $$('#' + id);
		if (!player) {
			var html = null;
			if (isIE) {
				html = '<object id="' + id + '" style="display:none" classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6">';
				html += '	<param NAME="AutoStart" VALUE="0">';
				html += '	<param NAME="url" value="' + soundFile + '">';
				html += '	<param NAME="url" value="">';
				html += '	<param NAME="PlayCount" VALUE="1">';
				html += '</object>';
			} else {
				html = '<audio id="' + id + '" src="" autoplay="autoplay"/>';
			}
			$(document.body).append(html);
			player = $$('#' + id);
		}
		if (player && (play == undefined || play)) {
			if (isIE) {
				player.url = soundFile;
				player.controls.stop();
				player.controls.play();
			} else {
				player.src = soundFile;
			}
		}
	} catch (e) {
	}
}

// ---------------------------------
// 转换浮点数
// ---------------------------------
Utils.convertFloat = function (value) {
	if (!value)
		return 0;
	var result = value.toString();
	result = result.replace(new RegExp("\,", "gm"), "");
	var result = parseFloat(result);
	if (isNaN(result))
		result = 0;
	return result;
}

// ---------------------------------
// 转换整数
// ---------------------------------
Utils.convertInt = function (value) {
	if (!value)
		return 0;
	var result = value.toString();
	result = result.replace(new RegExp("\,", "gm"), "");
	var result = parseInt(result);
	if (isNaN(result))
		result = 0;
	return result;
}


// --------------------------------------------
// 获取提示信息
// example: getLabelByKey('tip1',{s1:'value1',s2:'value2'});
// --------------------------------------------

function getLabelByKey(key, opt) {
	var tipObj = window['tipObj'];
	if (tipObj) {
		var value = tipObj[key];
		if (value) {
			for (var pro in opt) {
				value = value.replace(pro, opt[pro]);
			}
			return value;
		}
	}
	return null;
}
// --------------------------------------------
// 代替$.ajax
// --------------------------------------------
Utils.ajax = function(ajaxOptions) {
	var xhttp;
	try {
		if (window.XMLHttpRequest) {
			xhttp = new XMLHttpRequest()
		} else {
			xhttp = new ActiveXObject("Microsoft.XMLHTTP")
		}
		if (ajaxOptions.beforeSend)
			ajaxOptions.beforeSend(xhttp);
		var url = ajaxOptions.url;
		var content = '';
		if ($.isPlainObject(ajaxOptions.data)) {
			var arr = [];
			for (var pro in ajaxOptions.data) {
				arr.push(encodeURIComponent(pro) + '=' + encodeURIComponent(ajaxOptions.data[pro]));
			}
			content = arr.join('&');
		} else if ($.isArray(ajaxOptions.data)) {
			var arr = [];
			for (var i = 0; i < ajaxOptions.data.length; i++) {
				var obj = ajaxOptions.data[i];
				arr.push(encodeURIComponent(obj.name) + '=' + encodeURIComponent(obj.value));
			}
			content = arr.join('&');
		} else {
			content = ajaxOptions.data;
		}
		var index = url.indexOf('?');
		var url1 = url.substring(0,index);
		content = url.substring(index+1)+'&'+content;
		xhttp.open('post', url1, false);
		content = Utils.initUrl(content);
		xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhttp.send(content);
		if (ajaxOptions.success) {
			var data = JSON.parse(xhttp.responseText);
			if(window['pageObj'])
				pageObj.__data = data;
			Utils.initTokens(data);
			ajaxOptions.success(data);
		}
	} catch (ex) {
		Utils.log(ex);
		if (ajaxOptions.error) {
			var textStatus = "";
			var errorThrown = "";
			ajaxOptions.error(xhttp, textStatus, errorThrown)
		}
	} finally {
		if (ajaxOptions.complete) {
			ajaxOptions.complete(xhttp);
		}
	}
}

// --------------------------------------------
// 
// --------------------------------------------
function Command(module, service, method) {
	this.module = module;
	this.service = service;
	this.method = method;
}

// --------------------------------------------
// 向后端获取数据 异步
// --------------------------------------------
var fff = {};
Command.prototype.executeAsync = function() {
	var cmdObj = this;
	var xhttp;
	try {
		var arr = [];
		for (var pro in cmdObj) {
			var obj = cmdObj[pro];
			var value = obj;
			if ($.isArray(obj)) {
				value = obj.join(",");
			}
			if (obj && obj.type == "date") {
				value = obj.toString();
			}
			if ($.isPlainObject(obj) || $.isFunction(obj) || pro == "type") {
				continue;
			}
			arr.push(encodeURIComponent(pro) + "=" + encodeURIComponent(value));
		}
		if (window.XMLHttpRequest) {
			xhttp = new XMLHttpRequest()
		} else {
			xhttp = new ActiveXObject("Microsoft.XMLHTTP")
		}
		if (cmdObj.beforeSend)
			cmdObj.beforeSend(xhttp);
		var url = location.protocol + "//" + location.host + approot + "/data";
		var content = arr.join('&');
		content = Utils.initUrl(content);
		cmdObj.xhttp = xhttp;
		var idd = Math.random() + Math.random() + '';
		window[idd] = cmdObj;
		cmdObj.timeout = setTimeout("Utils.abort('" + idd + "')", 5000);
		xhttp.open('post', url, true);
		xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhttp.send(content);
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				if (cmdObj.success) {
					try {
						var data = JSON.parse(xhttp.responseText);
						Utils.initTokens(data);
						var key = cmdObj.module + "." + cmdObj.service + "." + cmdObj.method;
						if (!fff[key])
							fff[key] = -1;
						if (!(data.st && data.st < fff[key])) {
							cmdObj.success(data);
							fff[key] = data.st;
						}
					} catch (e) {
						Utils.log(e);
					}

				}

			}
		}
		if (cmdObj.complete) {
			cmdObj.complete(xhttp);
		}
	} catch (ex) {
		Utils.log(ex);
	}

}

Utils.abort = function(idd) {
	var cmdObj = window[idd];
	if (cmdObj && cmdObj.xhttp) {
		cmdObj.xhttp.abort();
		// DialogAlert("网速很慢!");
	}
}

// --------------------------------------------
// 向后端获取数据
// --------------------------------------------
Command.prototype.execute = function() {
	var cmdObj = this;
	var type = "/data";
	if (cmdObj.stype) {
		type = cmdObj.stype;
		delete cmdObj.stype;
	}
	var xhttp;
	try {
		var arr = [];
		for (var pro in cmdObj) {
			var obj = cmdObj[pro];
			var value = obj;
			if ($.isArray(obj)) {
				value = obj.join(",");
			}
			if (obj && obj.type == "date") {
				value = obj.toString();
			}
			if ($.isPlainObject(obj) || $.isFunction(obj) || pro == "type") {
				continue;
			}
			arr.push(encodeURIComponent(pro) + "=" + encodeURIComponent(value));
		}
		if (window.XMLHttpRequest) {
			xhttp = new XMLHttpRequest()
		} else {
			xhttp = new ActiveXObject("Microsoft.XMLHTTP")
		}
		if (cmdObj.beforeSend)
			cmdObj.beforeSend(xhttp);
		var url = location.protocol + "//" + location.host + approot + type;
		var content = arr.join('&');
		content = Utils.initUrl(content);
		switch (type) {
			case "/data" :
				xhttp.open('post', url, false);// 同步方式请求
				xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
// xhttp.setRequestHeader('ttt','zjl');
				xhttp.send(content);
				break;
			case "/download" :
				url += '?' + content;
				window.open(url);
				break;
		}
		if (cmdObj.success) {
			var data = JSON.parse(xhttp.responseText);
			Utils.initTokens(data);
			cmdObj.success(data);
		}
	} catch (ex) {
		Utils.log(ex);
		if (cmdObj.error) {
			var textStatus = "";
			var errorThrown = "";
			cmdObj.error(xhttp, textStatus, errorThrown)
		}
	} finally {
		if (cmdObj.complete) {
			cmdObj.complete(xhttp);
		}
	}
}

// --------------------------------------------
// 获取主框对象
// --------------------------------------------

function getMainFrame(win) {
	if (!win)
		return null;
	try {
		if (win.dialogArguments && "mainFrame" in win.dialogArguments)
			return win.dialogArguments['mainFrame'];
		if (typeof(win['pageName']) != 'undefined' && 'mainframe' == win['pageName'])
			return win;
		else if (win.parent == null || win.parent == win) {
			if (win.opener)
				return getMainFrame(win.opener);
			else
				return null;
		} else
		return getMainFrame(win.parent);
	} catch(e) {
		return null;
	}
}

// --------------------------------------------
// 获取Iframe window
// --------------------------------------------
function getIframeWin() {
	var mainFrame = getMainFrame(window);
	if (mainFrame && mainFrame.document)
		return mainFrame.document.getElementById('framePage').contentWindow;
	else
		return null;
}

// --------------------------------------------
// 获取userInfo对象
// --------------------------------------------
function getUserInfoObj() {
	var userInfo = null;
	var mainFrame = getMainFrame(window);
	if (mainFrame && '$userInfo' in mainFrame) {
		if (arguments.length > 0)
			userInfo = cloneObj(mainFrame['$userInfo'][arguments[0]]);
		else
			userInfo = cloneObj(mainFrame['$userInfo']);
	}
	if (!userInfo)
		userInfo = {}
	return userInfo;
}

function getSysInfoObj() {
	var mainFrame = getMainFrame(window);
	if (mainFrame && '$sysInfo' in mainFrame)
		return cloneObj(mainFrame['$sysInfo']);
	else
		return {};
}

// --------------------------------------------
// 获取userId
// --------------------------------------------

function getUserId() {
	return Utils.getPageValue('u') || getUserInfoObj().userId || getUserInfoObj().sid || getSId();
}

function getUserCakey() {
	return getUserInfoObj().caKey;
}

function getOrganId() {
	return getUserInfoObj().organId;
}

function getSId() {
	var mainFrame = getMainFrame(window);
	if (mainFrame && typeof(mainFrame['$sid']) != 'undefined')
		return mainFrame['$sid'];
	else
		return null;
}

function getTokenId() {
	var mainWin = getMainFrame(window);
	if (mainWin && "$ts" in mainWin) {
		var arr = mainWin['$ts'];
		var t = arr.shift();
		if (!("$usedTs" in mainWin))
			mainWin['$usedTs'] = [];
		mainWin['$usedTs'].push(t);
		if (mainWin['$usedTs'].length > 10) {
			mainWin['$usedTs'].splice(0, mainWin['$usedTs'].length - 10);
		}
		return t;
	}
	return null;
}


// --------------------------------------------
// 返回GUID，secure值为true则返回值中不包含“-”，不传同true
// --------------------------------------------
function newGUID(secure) {
	var guid = "";
	var splitChar = "";
	if (secure != undefined && secure == false)
		splitChar = "-";
	for (var i = 0; i < 8; i++) {
		var randomValue = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
		guid += randomValue;
		if (i == 1 || i == 2 || i == 3 || i == 4)
			guid += splitChar;
	}
	return guid.toUpperCase();
}

// --------------------------------------------
// 克隆对象
// --------------------------------------------
function cloneObj(obj) {
	if (!obj)
		return obj;
	var newObj = null;
	if (obj instanceof Array) {
		newObj = [];// 创建一个空的数组
		var i = obj.length;
		while (i--) {
			newObj[i] = cloneObj(obj[i]);
		}
		return newObj;
	} else if (obj instanceof Object) {
		newObj = {};// 创建一个空对象
		for (var k in obj) {
			newObj[k] = cloneObj(obj[k]);
		}
		return newObj;
	} else {
		return obj;
	}
}

// --------------------------------------------
// 判断对象有无属性，即是否是“{}”
// --------------------------------------------
function objIsEmpty(obj) {
	if (!obj)
		return true;
	for(var key in obj)
		return false;
	return true;
}

// --------------------------------------------
// 写全局属性
// --------------------------------------------
function setGlobalAttribute(key, value) {
	var mainFrame = getMainFrame(window);
	if (!mainFrame)
		// 无法找到公共的主界面就找当前界面的顶层父界面
		mainFrame = window.top;
	if (mainFrame) {
		if (mainFrame.__globalAttributes == undefined || mainFrame.__globalAttributes == null)
			mainFrame.__globalAttributes = {};
		mainFrame.__globalAttributes[key] = value;
		return true;
	} else
		return false;
}

// --------------------------------------------
// 读全局属性
// --------------------------------------------
function getGlobalAttribute(key) {
	var mainFrame = getMainFrame(window);
	if (!mainFrame)
		// 无法找到公共的主界面就找当前界面的顶层父界面
		mainFrame = window.top;
	if (mainFrame && mainFrame.__globalAttributes && key in mainFrame.__globalAttributes)
		return mainFrame.__globalAttributes[key];
	else {
		mainFrame = window.top;
		if (mainFrame && mainFrame.__globalAttributes && key in mainFrame.__globalAttributes)
			return mainFrame.__globalAttributes[key];
		else
			return undefined;
	}
}

// --------------------------------------------
// 移除全局属性
// --------------------------------------------
function removeGlobalAttribute(key) {
	var mainFrame = getMainFrame(window);
	if (!mainFrame)
		// 无法找到公共的主界面就找当前界面的顶层父界面
		mainFrame = window.top;
	if (mainFrame && mainFrame.__globalAttributes && key in mainFrame.__globalAttributes) {
		delete mainFrame.__globalAttributes[key];
		return true;
	} else
		return false;
}

// --------------------------------------------
// 动态 设置IFrame高度 由IFrame 调用
// --------------------------------------------
Utils.autoIframeSize = function(iframe) {
	var frame = null;
	var frameObj = null;
	if (iframe) {
		frame = iframe;
		frameObj = frame.contentWindow;
	} else {
		frameObj = window;
		var eles = parent.document.getElementsByTagName('iframe');
		for (var i = 0; i < eles.length; i++) {
			if (eles[i].contentWindow == frameObj) {
				frame = eles[i];
				break;
			}
		}
	}
	if (frame) {
		frame.style.display = "block";
		var h = 0;
		$(frameObj.document.body).children().each(function(i, ele) {
					var h1 = ele.scrollHeight;
					try {
						var v = $(ele).css('margin-top');
						v = parseFloat(v);
						if (!isNaN(v)) {
							h += v;
						}
						v = $(ele).css('margin-bottom');
						v = parseFloat(v);
						if (!isNaN(v)) {
							h += v;
						}
					} catch (e) {
					}
					h += h1;
				});
		$(frame).height(h + 10);
	}
}

// --------------------------------------------
// 判断当前浏览器是否IE
// --------------------------------------------
Utils.isIE = function () {
	return !!window.ActiveXObject || Utils.isIE11();// ||
													// navigator.userAgent.indexOf("MSIE")
													// > 0;
}

Utils.isIE11 = function () {
	return navigator.userAgent.indexOf("Trident") > 0;
}

Utils.ieVersion = function () {
	if (!Utils.isIE())
		return -1;
	var browser = navigator.appName;
	var browserVersion = navigator.appVersion;
	var version = browserVersion.split(";");
	var ver = version[1].replace(/[ ]/g,"");
	if (Utils.isIE11())
		return 11;
	else if (browser == "Microsoft Internet Explorer" && ver == "MSIE6.0")
		return 6;
	else if(browser=="Microsoft Internet Explorer" && ver=="MSIE7.0")
		return 7;
	else if(browser=="Microsoft Internet Explorer" && ver=="MSIE8.0")
		return 8;
	else if(browser=="Microsoft Internet Explorer" && ver=="MSIE9.0")
		return 9;
	else
		return 10;
}

Utils.checkBrowseVersion = function(requiredIE, errorMessage) {
	var result = true;
	var ieVer = Utils.isIE() ? Utils.ieVersion() : -1;
	if (requiredIE) {
		if (ieVer == -1)
			result = false;
		else if (ieVer < 8)
			result = false;
	} else {
		if (!(ieVer == -1 || ieVer >= 8))
			result = false;
	}
	if (!result) {
		if (errorMessage)
			alert(errorMessage);
		else if (requiredIE)
			alert('本系统必须运行IE8以上版本，否则无法正常显示页面。(请避免使用360、傲游等第三方浏览器)');
		else
			alert('本系统必须运行IE8以上版本或者Google Chrome、FireFox等浏览器，否则无法正常显示页面。(请避免使用360、傲游等第三方浏览器)');
	}
	return result; 
}

// --------------------------------------------
// 初始化下拉框
// --------------------------------------------
Utils.initSelect = function(selectId,obj,defaultMsg,excludeValues){
	var defaultStr='---所有---';
	if(defaultMsg)
		defaultStr=defaultMsg;
	var html='<option value="-1">'+defaultStr+'</option>';
	for(var pro in obj){
		if(excludeValues){
			if(excludeValues.indexOf(pro)<0)
				html+='<option value="'+pro+'">'+obj[pro]+'</option>';
		}
			else 
				html+='<option value="'+pro+'">'+obj[pro]+'</option>';
	}
	$('#'+selectId).html(html);
}

//------------------------------
//收集界面输入值
//------------------------------
Utils.getForm = function(dataObj,divId) {
	var texts;
	var selects;
	var textareas;
	var radios;
	var checkboxs;
	if(divId!=null){
		texts= $("#"+divId+" input[type=text]");
		selects = $("#"+divId+" select");
		textareas = $("#"+divId+" textarea");
		radios = $("#"+divId+" :radio[checked='checked']");
		checkboxs = $("#"+divId+" :checkbox[checked='checked']");
	}else{
		texts= $("input[type=text]");
		selects = $("select");
		textareas = $("textarea");
		radios = $(":radio[checked='checked']");
		checkboxs = $(":checkbox[checked='checked']");
	}
	texts.each(function(index, ele) {
		var id = $(ele).attr("id");
		var value = $(ele).val();
		if (id)
			dataObj[id] = value;
	});
	selects.each(function(index, ele) {
		var id = $(ele).attr("id");
		var value = $(ele).val();
		if (id)
			dataObj[id] = value;
	});
	textareas.each(function(index, ele) {
		var id = $(ele).attr("id");
		var value = $(ele).val();
		if (id)
			dataObj[id] = value;
	});
	radios.each(function(index, ele) {
		var name = $(ele).attr('name');
		var value = $(ele).val();
		if (name)
			dataObj[name] = value;
	});
	checkboxs.each(function(index, ele) {
		var id = $(ele).attr("id");
		var value = $(ele).val();
		if (id)
			dataObj[id] = value;
	});
}

Utils.setForm = function(dataObj) {
	var texts = $("input[type=text]");
	texts.each(function(index, ele) {
		var id = $(ele).attr("id");
		if (id){
			$(ele).val(dataObj[id]);
		}
	});
	var selects = $("select");
	selects.each(function(index, ele) {
		var id = $(ele).attr("id");
		 if (id)
			 $(ele).val(dataObj[id])
	});
	var textareas = $("textarea");
	textareas.each(function(index, ele) {
		var id = $(ele).attr("id");
		if (id)
			$(ele).val(dataObj[id])
	});
	var checkboxs = $(":checkbox");
	checkboxs.each(function(index, ele) {
		var id = $(ele).attr("id");
		var eleValue = $(ele).val();
		var value = dataObj[id];
		if (eleValue == value)
			$(ele).attr("checked", "checked");
	});
	var radios = $(":radio");
	radios.each(function(index, ele) {
		var name = $(ele).attr('name');
		var eleValue = $(ele).val();
		var value = dataObj[name];
		if (eleValue == value)
			$(ele).attr("checked", "checked");
	});
	
}

Utils.requiredCheck = function() {
	//
	var eles = $('*[requireValue]');
	for ( var i = 0; i < eles.length; i++) {
		var ele = $(eles[i]);
		var value = ele.val();
		if (!value) {
			var msg = ele.attr("requireValue");
			DialogAlert(msg);
			ele.focus();
			return false;
		}
	}
	//
	eles = $('*[requireNumber]');
	var re = /^\d+(?=\.{0,1}\d+$|$)/;
	for ( var i = 0; i < eles.length; i++) {
		var ele = $(eles[i]);
		var value = ele.val();
		if (value) {
			if (!value.isNumber()) {
				var msg = ele.attr("requireNumber");
				DialogAlert(msg);
				ele.focus();
				return false;
			}
		}
	}
	//
	eles=$('*[requireDigits]');
	for(var i=0;i<eles.length;i++){
		var ele = $(eles[i]);
		var value = ele.val().trim();
		value=parseFloat(value);
		var digits = ele.attr("requireDigits");
		var msg=ele.attr("requireDigitsMsg");
		digits=parseFloat(digits);
		if(!value.nDigits(digits)){
			DialogAlert(msg);
			ele.focus();
			return false;
		}
	}
	//
	eles = $('*[requireFloat]');
	for ( var i = 0; i < eles.length; i++) {
		var ele = $(eles[i]);
		DialogAlert(ele);
	}
	return true;
}

// --------------------------------------------
// 对话框
// --------------------------------------------
function getCurrentWin(win) {
	return win.top;
}

function DialogAlert(content, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.alert(content);
	return manager;
}

function DialogWarn(content, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.warn(content);
	return manager
}

function DialogError(content, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.error(content);
	return manager;
}

function DialogSuccess(content, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.success(content);
	return manager;
}

function DialogConfirm(content, func, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.confirm(content, func);
	return manager;
}

function DialogPrompt(content, initContent, func, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.prompt(content, initContent, func);
	return manager;
}

function DialogWaitting(content, parentWin) {
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.waitting(content);
	return manager;
}

function DialogOpen(opt, parentWin) {
	opt.showMax = false;
	opt.showToggle = false;
	opt.showMin = false;
	if (!("isResize" in opt))
		opt.isResize = true;
	if (!("modal" in opt))
		opt.modal = true;
	if (!("isHidden" in opt))
		opt.isHidden = false;
	var mainFrame = parentWin ? parentWin : getCurrentWin(window);
	var manager = mainFrame.$.ligerDialog.open(opt);
	mainFrame.$('.l-taskbar').hide();
	return manager;
}

// --------------------------------------------
// 模态弹出对话框
// var obj={};
// obj.url="";//地址
// obj.param={};//参数
// obj.feature="dialogWidth=200px;dialogHeight=100px";//窗口参数
// DialogModal(obj);
// --------------------------------------------
function DialogModal(opt) {
	var param = {};
	if ("param" in opt)
		param = opt.param;
	var win = getMainFrame(window);
	if (window.dialogArguments && "mainFrame" in window.dialogArguments)
		win = window.dialogArguments["mainFrame"];
	param.mainFrame = win;
	param.parentFrame = window;
	var isIE = !!window.ActiveXObject;
	var modelessDialog = isIE && "modeless" in param && (param.modeless == 1 || param.modeless.toLowerCase() == "true" || param.modeless.toLowerCase() == "yes")
	if (window.showModalDialog) {
		var returnValue = modelessDialog ? window.showModelessDialog(opt.url, param, opt.feature) : window.showModalDialog(opt.url, param, opt.feature);
		return returnValue;
	} else {
		//chrome37 不再支持showModalDialog方法，慎用。建议约定在调用页面添加setDialogReturnValue方法，由子页面回调
		//请参考selectGoodsStdUse.js中pageObj.selectStdUse方法示例
		var dialogHeight = 500;
		var dialogWidth = 650;
		var screenHeight = screen.height;//$(window).height();
		var screenWidth = screen.width;//$(window).width();
		if (opt.feature) {
			var inHeight =  Utils.getParamValue(opt.feature, "dialogHeight", true, "=", ";");
			if (inHeight && inHeight.indexOf("px") != -1)
				inHeight = inHeight.replace("px", "");
			inHeight = Utils.convertInt(inHeight);
			if (inHeight > 0)
				dialogHeight = inHeight;
			var inWidth =  Utils.getParamValue(opt.feature, "dialogWidth", true, "=", ";");
			if (inWidth && inWidth.indexOf("px") != -1)
				inWidth = inWidth.replace("px", "");
			inWidth = Utils.convertInt(inWidth);
			if (inWidth > 0)
				dialogWidth = inWidth;
		}
		var feature = "height=" + dialogHeight + ","
			+ "width=" + dialogWidth + ","
			+ "top=" + ((screenHeight - dialogHeight) / 2) + ","
			+ "left=" + ((screenWidth - dialogWidth) / 2) + ","
			+ "toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no";
		var userInfo = getUserInfoObj();
		var url = Utils.urlAddParams(opt.url, opt.param);
		if (userInfo && !objIsEmpty(userInfo))
			url = Utils.urlAddParams(url, "u=" + userInfo.userId + "&userInfo=" + encodeURIComponent(JSON.stringify(userInfo)));
		return window.open(url, null, feature);
	}
}
// --------------------------------------------
// 所有页面初始化事件
// --------------------------------------------
$(document).ready(function() {
			$(document).keydown(function(event) {
						var b = Utils.event.keyHandler(event);
						return b;
					});

			$(document).mousedown(function(event) {
						// return Utils.event.mouseHandler(event);
					});

			$(document).click(function() {
						var mainFrame = getMainFrame(window);
						if (typeof(pageName) == 'undefined' && mainFrame && "mainframeObj" in mainFrame) {
							if (mainFrame.mainframeObj.mainMenu && "actionMenu" in mainFrame.mainframeObj.mainMenu)
								mainFrame.mainframeObj.mainMenu.actionMenu.hide();
							if (mainFrame.mainframeObj.roleMenu && "actionMenu" in mainFrame.mainframeObj.roleMenu)
								mainFrame.mainframeObj.roleMenu.actionMenu.hide();
						}
					});

			var inputs = $('.i-input_off');
			inputs.each(function(index, input) {
						$(input).focusin(function() {
									input.className = "i-input_on";
								});
						$(input).focusout(function() {
									input.className = "i-input_off";
								});
					});
			var btns = $('.i-btn_off');
			btns.each(function(index, btn) {
						$(btn).hover(function() {
									btn.className = "i-btn_on";
								}, function() {
									btn.className = "i-btn_off";
								});
					});
			var trade_inputs = $('.o_btn');
			trade_inputs.each(function(index, input) {
						$(input).mousedown(function() {
									input.className = "c_btn";
								});
						$(input).mouseover(function() {
									input.className = "s_btn";
								});
						$(input).mouseout(function() {
									input.className = "o_btn";
								});
					});

			/**
			 * 刷新表格
			 * urlPath:/data?module=before&service=TransGoods&method=getTransTargetList
			 * queryObj：{u:'0001'}
			 */
			if ($.ligerui) {
				// 刷新表格
				$.ligerui.controls.Grid.prototype.refresh = function(urlPath, paraObj) {
					var url = this.options.url;
					if (urlPath)
						url = urlPath;
					var queryObj = {};
					if (paraObj) {
						for (var key in paraObj) {
							var value=paraObj[key];
							value=encodeURIComponent(value);
							url+='&'+key+'='+value;
						}
					}
					var url2=url;
					if (this.options.usePager) {
						var pagesizeName = this.options.pagesizeParmName;
						var pageName = this.options.pageParmName;
						queryObj[pagesizeName] = this.options['pageSize'];
						url+='&'+pageName+'=1';
					}
					this.options.url = url;
					this.options.qurl = url;
					var page=this.options['page'];
					if(page == 1)
						this.loadServerData(queryObj);
					else
						this.changePage("first");
					this.options.url=url2;
				};
				// 刷新本页
				$.ligerui.controls.Grid.prototype.refreshPage = function() {
					var oldUrl = this.options.url;
					var queryObj={};
					if(this.options.usePager){
						var pagesizeName = this.options.pagesizeParmName;
						var pageName = this.options.pageParmName;
						queryObj[pagesizeName] = this.options['pageSize'];
						queryObj[pageName] = this.options['page'];
					}
					this.loadServerData(queryObj);
					this.options.url=oldUrl;
				};
				$.ligerui.controls.Grid.prototype.getCheckedArr = function(flag) {
					var arr = [];
					var rows = this.getCheckedRows();
					var key = flag;
					if (!key)
						key = 'id';
					$(rows).each(function(index, row) {
								arr.push(row[key]);
							});
					return arr;
				};
			}
		});

// --------------------------------------------
// 页面检查数据是否修改组件begin
// 构造函数，owner为父控件，指定此控件则仅检查此父控件下属控件。initOwnerFields为true为创建时自动初始化修改状态
// --------------------------------------------
function FieldModifiedChecker(owner, initOwnerFields) {
	if (owner)
		this.owner = owner;
	else
		this.owner = document;
	this.modified = false;
	this.modifiedElements = null;
	if (initOwnerFields)
		this.initFileds();
}

// --------------------------------------------
// 初始化输入项修改状态
// --------------------------------------------
FieldModifiedChecker.prototype.initFileds = function() {
	this.modified = false;
	if (this.modifiedElements != null) {
		this.modifiedElements.splice(0, this.modifiedElements.length);
	}
	var inputs = $(this.owner).find("input");
	var textareas = $(this.owner).find("textarea");
	var selects = $(this.owner).find("select");
	for (var i = 0; i < inputs.length; i++) {
		if ("checked" in inputs[i]) {
			$(inputs[i]).data("oldChecked", inputs[i].checked);
		}
		if ("value" in inputs[i]) {
			$(inputs[i]).data("oldValue", inputs[i].value);
		}
		$(inputs[i]).data("modified", false);
	}
	for (var i = 0; i < textareas.length; i++) {
		$(textareas[i]).data("oldValue", textareas[i].value);
		$(textareas[i]).data("modified", false);
	}
	for (var i = 0; i < selects.length; i++) {
		$(selects[i]).data("oldValue", selects[i].value);
		$(selects[i]).data("modified", false);
	}
}

// --------------------------------------------
// 检查是否有数据修改。immediateReturn为true则不检查完，只要有修改立刻返回
// 有修改的控件在modifiedElements数组中，immediateReturn为true时无意义
// --------------------------------------------
FieldModifiedChecker.prototype.checkModified = function(immediateReturn) {
	this.modified = false;
	var inputs = $(this.owner).find("input");
	var textareas = $(this.owner).find("textarea");
	var selects = $(this.owner).find("select");
	this.modifiedElements = new Array();
	for (var i = 0; i < inputs.length; i++) {
		if ("checked" in inputs[i] && $.hasData(inputs[i]) && $(inputs[i]).data("oldChecked") != undefined) {
			$(inputs[i]).data("modified", $(inputs[i]).data("oldChecked") != inputs[i].checked);
		}
		if (!$(inputs[i]).data("modified") && "value" in inputs[i] && $.hasData(inputs[i])
				&& $(inputs[i]).data("oldValue") != undefined) {
			$(inputs[i]).data("modified", $(inputs[i]).data("oldValue") != inputs[i].value);
		}
		if ($(inputs[i]).data("modified")) {
			this.modified = true;
			if (immediateReturn)
				return this.modified;
			this.modifiedElements.push(inputs[i]);
		}
	}
	for (var i = 0; i < textareas.length; i++) {
		if ("value" in textareas[i] && $.hasData(textareas[i]) && $(textareas[i]).data("oldValue") != undefined) {
			$(textareas[i]).data("modified", $(textareas[i]).data("oldValue") != textareas[i].value);
		}
		if ($(textareas[i]).data("modified")) {
			this.modified = true;
			if (immediateReturn)
				return this.modified;
			this.modifiedElements.push(textareas[i]);
		}
	}
	for (var i = 0; i < selects.length; i++) {
		if ("value" in selects[i] && $.hasData(selects[i]) && $(selects[i]).data("oldValue") != undefined) {
			$(selects[i]).data("modified", $(selects[i]).data("oldValue") != selects[i].value);
		}
		if ($(selects[i]).data("modified")) {
			this.modified = true;
			if (immediateReturn)
				return this.modified;
			this.modifiedElements.push(selects[i]);
		}
	}
	return this.modified;
}

// --------------------------------------------
// 检查指定控件有无数据修改
// --------------------------------------------
FieldModifiedChecker.prototype.checkElementModified = function(element) {
	if (!element)
		return false;
	if ("checked" in element && $.hasData(element) && $(element).data("oldChecked") != undefined
			&& $(element).data("oldChecked") != element.checked)
		return true;
	if ("value" in element && $.hasData(element) && $(element).data("oldValue") != undefined
			&& $(element).data("oldValue") != element.value)
		return true;
	return false;
}

// --------------------------------------------
// 获取指定控件的原始值(即初始化时的旧值)
// --------------------------------------------
FieldModifiedChecker.prototype.getElementOldValue = function(element) {
	if (!element)
		return null;
	var oldValue = null;
	if ("checked" in element && $.hasData(element) && $(element).data("oldChecked") != undefined)
		oldValue = $(element).data("oldChecked");
	if ("value" in element && $.hasData(element) && $(element).data("oldValue") != undefined)
		oldValue = $(element).data("oldValue");
	return oldValue;
}

// --------------------------------------------
// 初始化需要验证的控件列表
// --------------------------------------------
FieldModifiedChecker.prototype.initVerifyFileds = function(owner, elements) {
	if (this.verifyElements != null) {
		this.verifyElements.splice(0, this.verifyElements.length);
	}
	var inputs = owner ? $(owner).find("input, textarea") : (elements ? elements : $(this.owner).find("input, textarea"));
	for ( var i = 0; i < inputs.length; i++) {
		if (typeof($(inputs[i]).attr("required")) != "undefined"
			|| typeof($(inputs[i]).attr("minLength")) != "undefined"
			|| typeof($(inputs[i]).attr("maxLength")) != "undefined"
			|| typeof($(inputs[i]).attr("minLen")) != "undefined"
			|| typeof($(inputs[i]).attr("maxLen")) != "undefined"
			|| typeof($(inputs[i]).attr("minValue")) != "undefined"
			|| typeof($(inputs[i]).attr("maxValue")) != "undefined"
			|| typeof($(inputs[i]).attr("minVal")) != "undefined"
			|| typeof($(inputs[i]).attr("maxVal")) != "undefined") {
			if (!this.verifyElements)
				this.verifyElements = new Array();
			this.verifyElements.push(inputs[i]);
		}
	}
}

// --------------------------------------------
// 验证控件，仅实现简单的。是否必须、最小/大长度、最小/大值
// --------------------------------------------
FieldModifiedChecker.prototype.verify = function(owner, elements) {
	this.initVerifyFileds(owner, elements);
	if (!this.verifyElements || this.verifyElements.length == 0)
		return true;
	var error = false;
	var errorMessages = new Array();
	for(var i = 0; i < this.verifyElements.length; i++) {
		var element = $(this.verifyElements[i]);
		var value = element.val();
		// 验证是否为空
		if (typeof(element.attr("required")) != "undefined") {
			if (value == null || value == undefined || value == "") {
				error = true;
				var message = element.attr("requiredMessage");
				if (!message)
					message = "必须输入值";
				if ($.inArray(message, errorMessages) == -1)
					errorMessages.push(message);
			}
		}
		// 验证最小长度
		if ((typeof(element.attr("minLength")) != "undefined" && element.attr("minLength") > 0)
			|| (typeof(element.attr("minLen")) != "undefined" && element.attr("minLen") > 0)) {
			var minLength = element.attr("minLength") || element.attr("minLen");
			if (value == null || value == undefined || value == "" || value.ansiLength() < minLength) {
				error = true;
				var message = element.attr("lengthMessage") || element.attr("lenMessage");
				if (!message)
					message = "长度不能小于" + minLength + "个字符";
				if ($.inArray(message, errorMessages) == -1)
					errorMessages.push(message);
			}
		}
		// 验证最大长度
		if ((typeof(element.attr("maxLength")) != "undefined" && element.attr("maxLength") > 0)
			|| (typeof(element.attr("maxLen")) != "undefined" && element.attr("maxLen") > 0)) {
			var maxLength = element.attr("maxLength") || element.attr("maxLen");
			if (value && value.ansiLength() > maxLength) {
				error = true;
				var message = element.attr("lengthMessage") || element.attr("lenMessage");
				if (!message)
					message = "长度不能大于" + maxLength + "个字符";
				if ($.inArray(message, errorMessages) == -1)
					errorMessages.push(message);
			}
		}
		// 验证最小值
		if (typeof(element.attr("minValue")) != "undefined" || typeof(element.attr("minVal")) != "undefined") {
			var minValue = element.attr("minValue") || element.attr("minVal");
			if (!value || value < minValue) {
				error = true;
				var message = element.attr("valueMessage") || element.attr("valMessage");
				if (!message)
					message = "值不能小于" + minValue;
				if ($.inArray(message, errorMessages) == -1)
					errorMessages.push(message);
			}
		}
		// 验证最大值
		if (typeof(element.attr("maxValue")) != "undefined" || typeof(element.attr("maxVal")) != "undefined") {
			var maxValue = element.attr("maxValue") || element.attr("maxVal");
			if (!value || value > maxValue) {
				error = true;
				var message = element.attr("valueMessage") || element.attr("valMessage");
				if (!message)
					message = "值不能大于" + maxValue;
				if ($.inArray(message, errorMessages) == -1)
					errorMessages.push(message);
			}
		}
	}
	if (error) {
		if (errorMessages.length > 0) {
			var showMessage = "";
			for(var i = 0; i < errorMessages.length; i++)
				showMessage += errorMessages[i] + "\n";
			alert(showMessage);
		}
		return false;
	} else
		return true;
}

// --------------------------------------------
// Fix输入控件无法获取焦点
// --------------------------------------------
$(document).ready(function() {
	try{
		var inputs=$('input[type="text"],input[type="radio"],input[type="checkbox"],radio,select,textarea');
		for(var i=0;i<inputs.length;i++){
			var input=inputs[i];
			var readonly=$(input).attr("readonly");
			var disabled=$(input).attr("disabled");
			if(!(readonly || disabled)){
				$(input).focus();
				break;
			}
		}
	}catch(e){}
});