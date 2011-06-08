package gwm.xm
import org.ccil.cowan.tagsoup.*;

class XmRadioPlayList {

	static main(args){
		System.properties.putAll( ["http.proxyHost":"rcproxy", "http.proxyPort":"80"] )
		def pl = getSongs()
//		def pl = getSongsFromFile("input/xmRadio.html")
		for (m in pl){
			println "${m.artist} : ${m.title} "
		}
	}

	static main3(args) {
		def slurper = new XmlSlurper(new Parser())
		def html = slurper.parse("input/xmRadio.html")
		def plTable = html.body.center[1].table[0]
		def tableSize = plTable.children().size()
		for ( i in 3..tableSize-2){
			def row = plTable.tr[i]
//			println row
			println " ${row.td[1]} ${row.td[2]}"
		}
//		html.body.center[1].table[0].children().each{
//			println it.name()
//			println it.children().each{ it2 -> println it2.name()}
//		}
//		println html.body
//		html.body.findAll{
//			println it.name()
//		}
//		def trs = html.body.center[1].br.table.findAll{
//			println it.name()
//			it.name() == 'tr'
//			 }
//		println trs.size()

	}

	static List getSongs(channel){
		List results = []
		def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
		def url = new URL("http://www.dogstarradio.com/search_xm_playlist.php?channel=61")
		url.withReader { reader ->
			def html = slurper.parse(reader)
			def plTable = html.body.center[1].table[0]
			def tableSize = plTable.children().size()
			for ( i in 3..tableSize-2){
				def row = plTable.tr[i]
				//println " ${row.td[1]} ${row.td[2]}"
				results << ['artist':row.td[1], 'title':row.td[2]]
			}

		}
		return results
	}

	static List getSongsFromFile(String fileName){
		List results = []
		def slurper = new XmlSlurper(new Parser())
		def html = slurper.parse(fileName)
		def plTable = html.body.center[1].table[0]
		def tableSize = plTable.children().size()
		for ( i in 3..tableSize-2){
			def row = plTable.tr[i]
			//println " ${row.td[1]} ${row.td[2]}"
			results << ['artist':row.td[1], 'title':row.td[2]]
		}
		return results
	}


	static main1(args) {
		System.properties.putAll( ["http.proxyHost":"rcproxy", "http.proxyPort":"80"] )
		def slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser())
		def url = new URL("http://www.dogstarradio.com/search_xm_playlist.php?channel=61")
		url.withReader { reader ->
			def html = slurper.parse(reader)
			println html
		}
	}
}
