#!/bin/bash 
# Append a given string to all files in the current working directory
# Moritz Spindelhirn 
# 17.04.2013

# -- Show help
usage() {
	echo "
	$0 [-h|-s <sec>] <hostname>|<IP-Address>
	"
}

interval=10
dest=""

# check what is the first parameter
case $1 in
	"-h")
	 	usage
	 	exit 0
	  ;;
	"-s")
	  interval=$2
	  dest=$3
	  ;;
	*)
	  dest=$1
esac

while true; do
    if ! ping -c 1 $dest > /dev/null; then
   	 echo $dest FAILED
    else
   	 echo $dest OK
    fi
    sleep $interval
done