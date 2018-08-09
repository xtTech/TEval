#!/bin/bash

mvn clean compile -P sdk-adpt-basic

for i in `ls ./src/test/resources/`
do
  u1=`echo ${i} | cut -d "#" -f1 `
  u2=`echo ${i} | cut -d "#" -f2 `
  u3=`echo ${i} | cut -d "#" -f3 `

mvn clean test -P sdk-adpt-${u1}#${u2} -Dclient.verison=${u3}
done

mvn clean compile install