#!/usr/bin/env groovy

String.metaClass.exec0 = {
    def st = new StringTokenizer(delegate)
    new ProcessBuilder(st.collect()).inheritIO().start()
}

"login".exec0().waitFor()
println "done"
