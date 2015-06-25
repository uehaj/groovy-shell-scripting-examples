#!/bin/bash
//usr/bin/env groovy -cp "`dirname $0`" "$0" $@; exit $? # -*- mode: groovy -*-

@groovy.transform.BaseScript(cmd.Base1)
import java.lang.String

"sed -e s/[a-z]/A/g".exec(input:"input.txt",
                          output:"output.txt")

println new File("input.txt").text
println new File("output.txt").text
