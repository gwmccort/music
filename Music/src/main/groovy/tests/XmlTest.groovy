package tests
import groovy.xml.XmlUtil

def xml = """
<langs type='current' count='3' mainstream='true'>
  <language flavor='static' version='1.5'>Java</language>
  <language flavor='dynamic' version='1.6.0'>Groovy</language>
  <language flavor='dynamic' version='1.9'>JavaScript</language>
</langs>
"""


langs = new XmlSlurper().parseText(xml)

println langs.getClass()

println "size: ${langs.size()}"

langs.children().each {
	println it
}
println langs.@count
langs.language.each{
  println it
}

println XmlUtil.serialize(langs)