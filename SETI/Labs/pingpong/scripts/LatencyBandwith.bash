#! /bin/bash

set -e

readonly typeStr=("tcp" "udp")

for (( i=0 ; i<2 ; i++ )) do
    declare -a typeSel=${typeStr[$i]}
    declare -a firstLine=($(head -n 1 ../data/${typeSel}_throughput.dat))
    declare -a lastLine=($(tail -n 1 ../data/${typeSel}_throughput.dat))
  
    declare N1=${firstLine[0]}
    declare N2=${lastLine[0]}
    declare T1=${firstLine[2]}
    declare T2=${lastLine[2]}

    declare D1=$( echo "$N1 / $T1" | bc -l)
    declare D2=$( echo "$N2 / $T2" | bc -l)

    declare B=$( echo "($N2-$N1)/($D2-$D1)" | bc -l)
    if [ $B -lt 0]
    then
        B=-$B
    fi
    declare L0=$( echo "($D1*$N2-$D2*$N1)/($N2-$N1)" | bc -l)

    gnuplot << endGnuPlot
        set term png size 900, 700
        set output "../data/${typeSel}_LatBand.png"
        set logscale x 2
        set logscale y 10
        set xlabel "msg size (B)"
        set ylabel "throughput (KB/s)"
        lbf(x) = x / ($L0 + x / $B)
        plot "../data/${typeSel}_throughput.dat" using 1:3 title "${typeSel} ping-pong Throughput" \
             with linespoints, \
             lbf(x) title "Latency-Bandwidth model with L=$L0 and B=$B" with linespoints
        clear
endGnuPlot
done
