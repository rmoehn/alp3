CFLAGS = -Wall -std=c99

benchmark-times.ps: benchmark.gpl benchmark-times.dat
	gnuplot benchmark.gpl

pseudo-grep: pseudo-grep.c errors.h

test: pseudo-grep stringsearch-test deflated-moby-dick.txt
	./stringsearch-test deflated-moby-dick.txt

benchmark-times.dat: pseudo-grep stringsearch-benchmark deflated-moby-dick.txt
	./stringsearch-benchmark deflated-moby-dick.txt > /dev/null
