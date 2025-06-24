function isString(n) {
	return "" + n === n;
}

function callAppNative(functionName, ...args) {
	if (window.ANDROID_BRIDGE_PLACEHOLDER != null) {

		var parametersFunction = "(";
		for (let i = 0; i < args.length; i++) {
			let arg = args[i];
			if (isString(arg)) {
				parametersFunction += "\'" + arg + "\'";
			} else {
				parametersFunction += arg;
			}
			if (i < (args.length - 1)) {
				parametersFunction += ",";
			}
		}

		parametersFunction += ")";
		let bodyFunction = "window.ANDROID_BRIDGE_PLACEHOLDER." + functionName + parametersFunction;
		console.log("functionToCall "+bodyFunction);
		let functionToExecute = new Function(bodyFunction);
		functionToExecute();

	} else {
		console.error("The OS is not Android or the webview have not the JavaScriptInterface enabled!")
	}
};