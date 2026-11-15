function toRegular()
{
	var input = document.getElementById("input")
	var inputVal = parseFloat(input.value)
	if (inputVal < 0) inputVal = 0
	else if (inputVal > 9.9999) inputVal = 9.9999
	input.value = inputVal
	document.getElementById("output").value = convertFromWT(inputVal)
}

function toWizlon()
{
	var val = document.getElementById("output").value
	var h = parseInt(val.substring(0, 2))
	var m = parseInt(val.substring(3, 5))
	var s = 0
	var s_str = val.substring(6, 8)
	if (s_str.length > 0) s = parseInt(s_str)
	document.getElementById("input").value = wizlonTime(h, m, s, 0)
}

function convertFromWT(w)
{
	var a = 8640.0 * w
	var h = Math.trunc(a / 3600.0);
	var m = Math.trunc((a - (3600.0 * h)) / 60.0);
	var s = Math.trunc((a - (3600.0 * h)) - (60.0 * m))
	return pad(h,2) + ":" + pad(m,2) + ":" + pad(s, 2)
}

function pad(str, max)
{
  str = str.toString()
  return str.length < max ? pad("0" + str, max) : str
}
