var toggle = true

function _main()
{
	toggleAbout()
	refresh()
}

function refresh()
{
	update()
	setTimeout(refresh, 100)
}

function update()
{
	var d = new Date()
	var str = (wizlonTime(d.getHours(), d.getMinutes(), d.getSeconds(), d.getMilliseconds()).toFixed(10)).toString()

	var dd = str.substring(0,1)
	var md = str.substring(2,4)
	var td = str.substring(4,6)

	if (toggle)
	{
		dd = '<span class="dd">' + dd + '</span>'
		md = '<span class="md">' + md + '</span>'
		td = '<span class="td">' + td + '</span>'
	}

	document.getElementById("t1").innerHTML = dd + '.' + md
	document.getElementById("t2").innerHTML = td
}

function toggleAbout()
{
	var butt = document.getElementById("what")
	var box = document.getElementById("about")
	if (toggle)
	{
		butt.innerHTML = "What's This?"
		box.hidden = true
	}
	else
	{
		butt.innerHTML = "I see..."
		box.hidden = false
	}
	toggle = !toggle
	update()
}
