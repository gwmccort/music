package music
import org.ccil.cowan.tagsoup.Parser

//System.setProperty("http.proxyHost", "usproxy")
//System.setProperty("http.proxyPort", "9090")

url = 'http://www.jambase.com/Artists/default.aspx?pg='
slurper = new XmlSlurper(new Parser())

for (pageNum in 1..5) {
	html = slurper.parse("$url$pageNum")
	html.'**'.find{ it.name()=='div' && it.@class=="topSearches" }.ol.li.each {
		art = it.toString().replaceFirst(/\d+ - /, '')
		println art
	}
}
