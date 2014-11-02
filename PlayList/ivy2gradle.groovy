println("dependencies {")
 
def ivyModule = new XmlParser().parse(new File('ivy.xml'))
ivyModule.dependencies.dependency.each {
    def scope = it.@conf?.contains("test") ? "testCompile" : "compile"
    println("\t$scope '${it.@org}:${it.@name}:${it.@rev}'")
}
 
println("}")