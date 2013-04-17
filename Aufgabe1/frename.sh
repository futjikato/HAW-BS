#!/bin/bash 
# Append a given string to all files in the current working directory
# Moritz Spindelhirn 
# 17.04.2013

# -- Show help
usage() {
	echo "
	$0 <string>
	Hängt für alle Dateien im aktuellen Verzeichnis die Zeichenkette string an den aktuellen Dateinamen an (Umbenennung).
	"
}

# check if one parameter is given
if [ $# -ne 1 ]; then
	# show usage if invalid parameter
	usage
	exit 1
fi

# check parameter length
#len=${#1}
#if [ $len -ge 30 ]; then
#	usage
#	exit 1
#fi


for file in * 
do
    mv $file $file$1
done