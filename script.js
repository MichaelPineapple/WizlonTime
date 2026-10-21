// MAIN CONVERSION FUNCTION
function wizlonTime(hours, minutes, seconds, ms)
{
	return ((hours*3600000.0)+(minutes*60000.0)+(1000*seconds)+ms)/8640000.0;
}


// INDEX
var toggle = true;

function _main()
{
	toggleAbout();
	refresh();
}

function refresh()
{
	update();
	setTimeout(refresh, 100);
}

function update()
{
	var d = new Date();
	var str = (wizlonTime(d.getHours(), d.getMinutes(), d.getSeconds(), d.getMilliseconds()).toFixed(10)).toString();

	var dd = str.substring(0,1);
	var md = str.substring(2,4);
	var td = str.substring(4,6);

	if (toggle)
	{
		dd = '<span class="dd">'+dd+'</span>';
		md = '<span class="md">'+md+'</span>';
		td = '<span class="td">'+td+'</span>';
	}

	document.getElementById("t1").innerHTML = dd+'.'+md;
	document.getElementById("t2").innerHTML = td;
}

function toggleAbout()
{
	var butt = document.getElementById("what");
	var box = document.getElementById("about");
	if (toggle)
	{
		butt.innerHTML = "What's This?";
		box.hidden = true;
	}
	else
	{
		butt.innerHTML = "I see...";
		box.hidden = false;
	}
	toggle = !toggle;
	update();
}

// CONVERTER

function toRegular()
{
	// get input
	var input = document.getElementById("input");
	var inputVal = parseFloat(input.value);
	
	// enforce bounds
	if (inputVal < 0) inputVal = 0;
	else if (inputVal > 9.9999) inputVal = 9.9999;
	input.value = inputVal;
	
	// convert and display output
	document.getElementById("output").value = convertFromWT(inputVal);
}


function toWizlon()
{
	var val = document.getElementById("output").value;
	
	var h = parseInt(val.substring(0,2));
	var m = parseInt(val.substring(3,5));
	var s = 0;
	
	var s_str = val.substring(6,8);
	if (s_str.length > 0) s = parseInt(s_str);
	
	document.getElementById("input").value = wizlonTime(h, m, s, 0);
}

function convertFromWT(w)
{
	var a = 8640.0*w;
	var h = Math.trunc(a/3600.0);
	var m = Math.trunc((a-(3600.0*h))/60.0);
	var s = Math.trunc((a - (3600.0*h))-(60.0*m))
	return pad(h,2)+":"+pad(m,2)+":"+pad(s,2);
}

function pad(str, max)
{
  str = str.toString();
  return str.length < max ? pad("0" + str, max) : str;
}






