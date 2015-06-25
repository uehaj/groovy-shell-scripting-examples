package cmd

import java.util.StringTokenizer
import static java.lang.ProcessBuilder.Redirect

abstract class Base0 extends Script {
    abstract Object runScript()

    Object run(){
        String.metaClass.exec0 = {
            def st = new StringTokenizer(delegate)
            new ProcessBuilder(st.collect()).inheritIO().start()
        }
        String.metaClass.or = { String rhs ->
            delegate.execute() | rhs.execute()
        }
        Process.metaClass.or = { String rhs ->
            delegate | rhs.execute()
        }
        String.metaClass.or = { Process rhs ->
            delegate.execute() | rhs
        }
        runScript()
    }
}
