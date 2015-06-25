package cmd

import java.util.StringTokenizer
import static java.lang.ProcessBuilder.Redirect

abstract class Base1 extends Script {
    abstract Object runScript()

    Redirect makeFrom(Object arg) {
        if (arg instanceof String) {
            return Redirect.from(new File(arg))
        }
        else if (arg instanceof File) {
            return Redirect.from(arg)
        }
        else if (arg instanceof Redirect) {
            return arg
        }
        throw new IllegalArgumentException("arg="+arg)
    }

    Redirect makeTo(Object arg) {
        if (arg instanceof String) {
            return Redirect.to(new File(arg))
        }
        else if (arg instanceof File) {
            return Redirect.to(arg)
        }
        else if (arg instanceof Redirect) {
            return arg
        }
        throw new IllegalArgumentException("arg="+arg)
    }
    
    Map env = [:]
    def export(key, value) {
        env[key] = value
    }

    Object run(){
        String.metaClass.exec = { Map origArg = [:] ->
            def arg = origArg.withDefault { Redirect.INHERIT }
            def st = new StringTokenizer(delegate)
            def pb = new ProcessBuilder(st.collect())
            pb.environment().putAll(env)
            pb.redirectInput(makeFrom(arg.input)).
               redirectOutput(makeTo(arg.output)).
               redirectError(makeTo(arg.error)).
               start()
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
