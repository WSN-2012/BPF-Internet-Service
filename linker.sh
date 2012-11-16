#!/bin/bash


function execute {
    echo "Running: ${1}"
    eval $1
    # Get the exit status to see if successful
    if [ $? -ne 0 ];then
        echo "Linking the libs went bad... :(" 
        exit 1
    fi
}

if [ $# -ne 1 ]; then
    echo "Usage: ./linker.sh /path/to/WSN-BPF.jar"
    exit
fi

jar_path=$(echo "${1}" | sed s/....$//)

echo $jar_path

execute "cd lib"
execute "ln -s ../${jar_path}.jar WSN-BPF.jar"
execute "ln -s ../${jar_path}-doc.jar WSN-BPF-doc.jar"
execute "cd .."

echo "Link successfully created, you can now compile the service"
