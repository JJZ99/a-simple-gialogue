#!/bin/sh
##传入资源目录及工程目录，解析资源目录中的资源，并在所有工程目录中查找
#grep_resources.sh ${resource_path} ${project_path_1} ${project_path_2} ${project_path_3} ...
#e.g.
#grep_resouces.sh ${PWD}/app/src/main/res/ ${PWD}
#/bin/sh /Users/bytedance/myGit/a-simple-gialogue/grep.sh ${PWD}/app/src/main/res/ ${PWD}
#查找未使用的资源并输出，检测结果可使用grep、awk等操作解析为自需要的格式

resource_path=$1
all_project_paths=${@:2}

function find_commiter()
{
    local file_path=$1
    local line_number=$2
    echo $(git blame -c $file_path -L $line_number,$line_number |awk -F '\t' '{print $2}')
}

function find_typed_refrences()
{
    local type=$2
    local file_path=$3
    local line_number=$4
    # usages in resources and code
    local regex_key="@$type/$1|R.$type.$1"
    local result=($(grep -InrE "$regex_key" $all_project_paths -m 1 --include=*.kt --include=*.java --include=*.xml))
    if [ ${#result[@]} -eq 0 ];then
        echo "no reference found for $type: $1,\t commiter is $(find_commiter $file_path $line_number), \t ${file_path}:${line_number}"
    fi
}

function find_all_refrences()
{
    local type=$1
    local res_folder=$2
    local argc=$#
    if [ $argc -lt 3 ]; then
        echo "no resource file found for type $type, skip it"
        return
    fi
    # first arg as type, second arg as res_folder skip them
    let processing_index=0

    for arg in "$@"
    do
        let processing_index++
        if [ $processing_index -lt 3 ];then
            continue
        fi
        find_all_refrences_for_each_dir $type $res_folder $arg
    done
}

function find_all_refrences_for_each_dir()
{
    local resource_files=($(find $res_folder/$3 -type f))
    for ((i=0; i<${#resource_files[@]};i++)); do
        resource_file=${resource_files[$i]}
        resource_name=${resource_file##*/}
        resource_name=${resource_name%%.*}
        find_typed_refrences $resource_name $1 $resource_file 1
    done
}

function find_all_value_reference()
{
    if [ ! -f $1 ];then
        return
    fi
    local type=$2
    # fixme:
    # grep twice now because shell array use SPACE as array separater,
    # maybe a better way
    res_names=($(grep -nrEo "<$type +name *= *\".*>" $1 |cut -d '"' -f2))
    line_numbers=($(grep -nrEo "<$type +name *= *\".*>" $1 |cut -d ':' -f2))

    local count=${#res_names[@]}
    for ((i=0; i<${count};i++)); do
        local res_name=${res_names[$i]}
        local line_number=${line_numbers[$i]}
        find_typed_refrences $res_name $type $1 $line_number
    done
}

function handle_each_value_dir()
{
    local value_dir=$1
    find_all_value_reference ${value_dir}/strings.xml string
    find_all_value_reference ${value_dir}/dimens.xml dimen
    find_all_value_reference ${value_dir}/themes.xml style
    find_all_value_reference ${value_dir}/colors.xml color
}

function handle_all_value_dirs()
{
    local res_folders=$(find $resource_path -name res -type d|grep -v build)
    for res_folder in $res_folders
    do
        local values_dirs=$(find $res_folder -name values -type d)
        local values_dir_count=${#values_dirs[@]}
        echo $res_folder $values_dirs  $values_dir_count
        if [ $values_dir_count -eq 0 ];then
            echo "no values resource found in $res_folder"
        else
            for values_dir in ${values_dirs}
            do
                handle_each_value_dir "$values_dir"
            done
        fi
    done
}

function handle_all_resource_dirs()
{
    local res_folders=$(find $resource_path -name res -type d|grep -v build)
    echo res_folders are $res_folders
    for res_folder in $res_folders
    do
        find_all_refrences drawable $res_folder $(ls $res_folder |grep drawable)
        find_all_refrences layout $res_folder $(ls $res_folder |grep layout)
        find_all_refrences anim $res_folder $(ls $res_folder |grep anim)
        find_all_refrences mipmap $res_folder $(ls $res_folder |grep mipmap)
    done
}

cd $resource_path
date
handle_all_resource_dirs
handle_all_value_dirs
date