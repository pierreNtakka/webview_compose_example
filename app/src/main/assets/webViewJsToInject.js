javascript: (function() {
    console.log("injecting android bridge...");
	var s = document.createElement('script');
	s.type = 'text/javascript';
	var code = 'CODE_PLACEHOLDER';
	try {
		s.appendChild(document.createTextNode(code));
		document.head.appendChild(s);
	} catch (e) {
		s.text = code;
		document.head.appendChild(s);
	}
	console.log("android bridge injected!");

    console.log("dispatch Event: EVENT_NAME_PLACEHOLDER");
    const bridgeEvent = new Event("EVENT_NAME_PLACEHOLDER");
    document.dispatchEvent(bridgeEvent);
})()