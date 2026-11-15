// MAIN CONVERSION FUNCTION
function wizlonTime(hours, minutes, seconds, ms)
{
	return ((hours * 3600000.0) + (minutes * 60000.0) + (1000 * seconds) + ms) / 8640000.0
}
