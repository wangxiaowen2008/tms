#!/bin/bash

# 文件目录
Lv_Path=$1
# 密码文件名称
Lv_pwd_fileName=$2
# 数据文件名称
Lv_data_fileName=$3
# 公钥文件地址
Lv_publicKey_filePath=$4

# 密码文件
pwdFile=$Lv_pwd_fileName

# 数据文件
dataFile=$Lv_data_fileName

# 数据文件文件名
dataFile_name=${Lv_data_fileName%.*}

# 数据文件压缩包
compression_dataFile=${dataFile_name}.tar.gz

# 明文密码文件名称
create_pwdFile=Pw_create.txt

cd $Lv_Path

# 生成密码文件
openssl rand -hex 16 > $create_pwdFile

# 压缩,加密
tar -zcf $dataFile | openssl des3 -salt -kfile $create_pwdFile | dd of=$compression_dataFile

# RSA加密密码文件
openssl pkeyutl -encrypt -inkey $Lv_publicKey_filePath -pubin -in $create_pwdFile -out $pwdFile

# 删除生成的密码文件
rm -rf $create_pwdFile

# 删除数据文件
rm -rf $dataFile