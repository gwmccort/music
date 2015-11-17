package bookmarks
import groovy.xml.XmlUtil

import org.ccil.cowan.tagsoup.Parser

slurper = new XmlSlurper(new Parser())

// need to declare namespace
//html = slurper.parse('delicious.html').declareNamespace('html':'http://www.w3.org/1999/xhtml')
html = slurper.parse('GoogleBookmarks.html').declareNamespace('html':'http://www.w3.org/1999/xhtml')

//// pretty print xml
//new File("tmp.xml").withWriter { out ->
//	out.println XmlUtil.serialize(html)
//}

tag = ""
html.'**'.findAll{ it.name()=='h3' || it.name()=='a'}.each {
	switch (it.name()) {
		case 'h3':
		tag = it.text()
		break

		case 'a':
		println tag + '\t' + it.@href
		break
	}
}

//html.'html:body'.'html:dl'.'html:dt'.'html:a'.each {
//	println "${it}\t${it.@href}"
//}

//// print tags
//html.'html:body'.'html:h3'.each {
//	println it
//}

//html.'html:body'.each {
//	println it.'html:dl'.size()
//}
//
//def printLinks(html) {
//	html.'html:body'.'html:dl'.'html:dt'.'html:a'.each { println "$it\t${it.@tags}\t${it.@href}" }
//}
//
//def getTags(html) {
//	results = [] as Set
//	html.'html:body'.'html:dl'.'html:dt'.'html:a'.@tags.each {
//		tags = it.toString().split(/,/)
//		tags.each { results << it }
//	}
//	results
//}