package groovysite

def records = new XmlSlurper().parseText(XmlExamples.CAR_RECORDS)
def allRecords = records.car
assert 3 == allRecords.size()
def allNodes = records.depthFirst().collect{ it }
assert 10 == allNodes.size()
def firstRecord = records.car[0]
assert 'car' == firstRecord.name()
assert 'Holden' == firstRecord.@make.text()
assert 'Australia' == firstRecord.country.text()
def carsWith_e_InMake = records.car.findAll{ it.@make.text().contains('e') }
assert carsWith_e_InMake.size() == 2
// alternative way to find cars with 'e' in make
assert 2 == records.car.findAll{ it.@make =~ '.*e.*' }.size()
// makes of cars that have an 's' followed by an 'a' in the country
assert ['Holden', 'Peel'] == records.car.findAll{ it.country =~ '.*s.*a.*' }.@make.collect{ it.text() }
def expectedRecordTypes = ['speed', 'size', 'price']
assert expectedRecordTypes == records.depthFirst().grep{ it.@type != '' }.'@type'*.text()
assert expectedRecordTypes == records.'**'.grep{ it.@type != '' }.'@type'*.text()
def countryOne = records.car[1].country
assert 'Peel' == countryOne.parent().@make.text()
assert 'Peel' == countryOne.'..'.@make.text()
// names of cars with records sorted by year
def sortedNames = records.car.list().sort{ it.@year.toInteger() }.'@name'*.text()
assert ['Royale', 'P50', 'HSV Maloo'] == sortedNames
assert ['Australia', 'Isle of Man'] == records.'**'.grep{ it.@type =~ 's.*' }*.parent().country*.text()
assert 'co-re-co-re-co-re' == records.car.children().collect{ it.name()[0..1] }.join('-')
assert 'co-re-co-re-co-re' == records.car.'*'.collect{ it.name()[0..1] }.join('-')

// my tests
println records.car[1].text()
x = records.car[1]*.text()
println x.class
println x.size()
x.each {
	println it
}