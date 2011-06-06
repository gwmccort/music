package gwm.itunes.json

//@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.0-RC2' )
import groovyx.net.http.*
import net.sf.json.groovy.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

println "starting script"

// http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/wa/wsSearch?term=jack+johnson

// issue json parser
// http://groovy.329449.n5.nabble.com/json-parser-issue-td3256046.html

def http = new HTTPBuilder( 'http://ax.itunes.apple.com' )
//http.setProxy('rcproxy', 80, 'http') // old proxy
http.setProxy('usproxy.rockwellcollins.com', 9090, 'http')// new proxy
//println "after setProxy"

http.parser.'text/javascript' = { resp ->
	println 'in custom parser'
	bufferedText = resp.entity.content.getText(
			ParserRegistry.getCharset( resp ) ).trim()
	println "bufferedText:$bufferedText"
	return new JsonSlurper().parseText( bufferedText )
}

println "after parse"

// http://groovy.329449.n5.nabble.com/json-parser-issue-td3256046.html
//http.parser.'text/javascript; charset=utf-8' = http.parser.'application/json'

// perform a GET request, expecting JSON response data
//http.request( GET, JSON ) {
//http.request( GET, ANY ) {
//http.request( GET, TEXT ) {
http.request( GET, 	'text/javascript' ) {
	//	println "inside request..."
//	uri.path = '/WebObjects/MZStoreServices.woa/wa/wsLookup' // when using an id
//	uri.query = [ id:'909253']

	// searches
	uri.path = '/WebObjects/MZStoreServices.woa/wa/wsSearch' // when using term
	uri.query = [ term:'jack johnson', enty:'album' , limit:75]
//	//uri.query = [ term:'bob+dylan', enty:'album']
//	//uri.query = [ term:'bob+dylan', media:'music']
//	//uri.query = [ term:'railroad+earth', media:'music']
////	uri.query = [ term:'railroad+earth', entity:'song']
//	uri.query = [  term:'railroad earth',]

	//lookups
//	uri.path = '/WebObjects/MZStoreServices.woa/wa/wsLookup' // when using term
//    uri.query = [ id:'5140074', entity:'song' , limit:'100']
    //	uri.query = [ amgArtistId:'5140074']

	headers.'User-Agent' = 'Mozilla/5.0 Ubuntu/8.10 Firefox/3.0.4'

	println "before handlers"
	println response.dump()

	// response handler for a success response code:
	response.success = { resp, json ->

//		println "inside success handler..."
//		println resp.statusLine
//
//		println json.dump()

		println json.resultCount
		println json.results

		// parse the JSON response object:
		json.results.each { println " ${it.wrapperType} : ${it.artistName} : ${it.trackName} : ${it.collectionName}" }
	}

	// handler for any failure status code:
	response.failure = { resp -> println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}" }
}