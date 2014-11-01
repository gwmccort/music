package tests
import groovy.xml.XmlUtil

import org.ccil.cowan.tagsoup.Parser

//System.setProperty("http.proxyHost", "usproxy")
//System.setProperty("http.proxyPort", "9090")

//url = 'http://www.jambase.com/Artists/default.aspx?pg='
//slurper = new XmlSlurper(new Parser())

// turn off tagsoup namespace
// http://stackoverflow.com/questions/5780225/extracting-parts-of-html-with-groovy/5781193#5781193
tsParser = new Parser()
tsParser.setFeature(tsParser.namespacesFeature, false)
parser = new XmlParser(tsParser)

html = parser.parse('test.html')

//println html.body.table.tr.size()
//println html.body.table.tr.td[1].text()
//println html.body.table.tr.td[1].getClass()

html.body.table.tr.each {
	println it.children().size()
}

println html.body.table.tr[1].td[1].text()