package music
import groovy.xml.XmlUtil

import org.ccil.cowan.tagsoup.Parser

/**
 * Get's a list of artists from XM Playlist site
 */

//set proxy for work
//System.setProperty("http.proxyHost", "usproxy")
//System.setProperty("http.proxyPort", "9090")

//channell numbers
JamOn=29
Bluegrass=61

//url = 'http://dogstarradio.com/search_xm_playlist.php?artist=&title=&channel=29&month=&date=&shour=&sampm=&stz=&ehour=&eampm='
//url = 'http://dogstarradio.com/search_xm_playlist.php?channel=61'
url = 'http://dogstarradio.com/search_xm_playlist.php?channel=' + Bluegrass

// turn off tagsoup namespace
// http://stackoverflow.com/questions/5780225/extracting-parts-of-html-with-groovy/5781193#5781193
tsParser = new Parser()
tsParser.setFeature(Parser.namespacesFeature, false)
slurper = new XmlSlurper(tsParser)

//html = slurper.parse('XmRadio.html')
html = slurper.parse(url)

//// pretty print xml
//new File("tmp.xml").withWriter { out ->
//	out.println XmlUtil.serialize(html)
//}

artists = [] as Set

// works with url
table = html.body.'**'.findAll{it.name()=='table'}[1]
table.tr.each {
	if (it.children().size()==5 && !(it.td[1].text()=='Artist' || it.td[1].text().startsWith('fb.com'))){

		artists << it.td[1].text()
	}
}

// works with file????
//html.body.table.tbody.tr.td.each {
//	// right number for track & not a search page
//	if (it.children().size()==12 && !it.text().contains("search_xm_playlist.php") && !it.text().contains("fb.com/siriusxmjamon")){
//
//		// read text as xml to get webkit stuff
//		tr = new XmlParser().parseText(it.text().replaceAll("&", "&amp;"))
//		artists << tr.td[1].text()
//	}
//}

for (a in artists.sort()) println a
