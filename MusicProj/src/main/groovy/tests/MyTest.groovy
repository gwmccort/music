package tests
text =  '''
<foo>
	<bar/>
	<td>
		<span>span text</span>
		td text
		<span>span 2 text</span>
	</td>
</foo>
'''

xml = new XmlSlurper().parseText(text)

assert false == xml.bar.isEmpty()
assert true == xml.junk.isEmpty()


println xml.td.text()
//println xml.td.dump()

println xml.td.parent()