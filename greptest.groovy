#!/bin/bash
//usr/bin/env groovy -cp "`dirname $0`" "$0" $@; exit $? # -*- mode: groovy -*-

@groovy.transform.BaseScript(cmd.Base1)
import static java.lang.ProcessBuilder.Redirect

"echo 123abc" |
  "tr 'a-z' 'A-Z'" |
  "grep B".exec(input:Redirect.PIPE)
