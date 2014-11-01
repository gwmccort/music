package bookmarks
import groovy.xml.XmlUtil

import org.ccil.cowan.tagsoup.Parser

slurper = new XmlSlurper(new Parser())

// need to declare namespace
html = slurper.parse('delicious.html').declareNamespace('html':'http://www.w3.org/1999/xhtml')
//html = slurper.parse('delicious.html')
//html = slurper.parse('ril_export.html')

//// pretty print xml
//new File("tmp.xml").withWriter { out ->
//	out.println XmlUtil.serialize(html)
//}

//println "title:" +  html.'html:head'.'html:title'
//
//println "h1:" + html.'html:body'.'html:h1'
//
//println "dl size:" + html.'html:body'.'html:dl'.size()


//printLinks(html)

//println getTags(html)
getTags(html).sort().each {
	println it
}


def printLinks(html) {
	html.'html:body'.'html:dl'.'html:dt'.'html:a'.each { println "$it\t${it.@tags}\t${it.@href}" }
}

def getTags(html) {
	results = [] as Set
	html.'html:body'.'html:dl'.'html:dt'.'html:a'.@tags.each {
		tags = it.toString().split(/,/)
		tags.each { results << it }
	}
	results
}