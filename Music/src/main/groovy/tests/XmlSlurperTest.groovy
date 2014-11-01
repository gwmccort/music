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
slurper = new XmlSlurper(tsParser)

html = slurper.parse('test.html')

// namespace support in slurper
//slurper = new XmlSlurper(new Parser()/)
//html = slurper.parse('test.html').declareNamespace('html':'http://www.w3.org/1999/xhtml')

//// pretty print xml
//new File("tmp.xml").withWriter { out ->
//	out.println XmlUtil.serialize(html)
//}

//println new StreamingMarkupBuilder().bindNode(html.body)
//println XmlUtil.serialize(html.body)

body = html.body

//println body.table.tr.'**'.size()
//body.table.tr.children().each {
//	println "name: ${it.name()} class: ${it.getClass()} size:${it.children().size()}"
//	println "text: " + it.text()
//}

//println '------------'
//tr = body.table.tr.td[1].children()
//println tr.size()
//println tr.text()

println '------------'
tr2 = body.table.tr[1].td
println tr2.text()

println '-----------------'
trXml =  new XmlParser().parseText(tr2.text())
//println XmlUtil.serialize(trXml)
println trXml.td[1].text()


println '-----------------'
body.table.tr.each {
	tr = new XmlParser().parseText(it.text())
	println tr.td.size()
	println tr.td[1].text()
}






//body.table.tr.'**'.each{
//	println "name:" +it.name()
//	println "text:" + it.text()
//}

//
//html.'**'.findAll{it.name()=='h1' }.each{
//	println it.text()
//}
//
//html.'body'.'**'.findAll{it.name()=='td' && it.children().size()==2}.each{
//	println it.getClass()
//	println it.name()
//	println it.text()
//	println it.children().size()
//}